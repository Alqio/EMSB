package com.mygdx.emsb

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType

import scala.collection.mutable.Map


/**
 * @author alkiok1
 */
abstract class Instance() {
  
  //Coords class is mutable and changes when the coordinate changes
  var coords = new Coords(100,480)
  
  var suunta = 1 //1 = oikea, -1 = vasen
  val solid = false
  var sprite: Sprite = new Sprite(new Texture("vihuy.png"))
  var target: Option[Instance] = this.instanceNearest()
  var projectile: Option[Projectile] = None
  var name = "sukka mehu"
  
  // 60 = 1 second, the lower the better. 120 = 2 seconds for example and 10 = 1/6 second.
  var attackSpeed: Int = 60
  val side = "enemy"
  
  var dmg: Double = 1.0
  var range: Int = 45
  
  //maxHp can also change with upgrades etc.
  var maxHp: Double = 5
  var hp: Double = maxHp
  
  
  val alarms = Array.fill(4)(new Alarm(0, this))
  var alarmActions = Map[Alarm, () => Unit]()
  
  def placeholder() = {}
  
  for (i <- alarms) {
  	this.alarmActions += i -> placeholder
  }
  //The alarm(0) represents attack for each instance
  // Set the alarm(0) to attack for each instance.
//  this.alarmActions(this.alarms(0)) = () => {
//  	attack()
//  	this.alarms(0).time += attackSpeed
//  }
  
  
  /** Draw the instance */
  def draw(batch: SpriteBatch) = {
    val pos = new Vector3(this.coords.x.toFloat, this.coords.y.toFloat, 0)
    this.sprite.setPosition(pos.x, pos.y)
		this.sprite.draw(batch)
	
  }
  
  /**
   * The attack method. 
   */
  def attack()
  
  /**
   * Return the center position of the instance
   */
  def position = new Coords(this.coords.x + this.sprite.getWidth()/2, this.coords.y + this.sprite.getHeight()/2)
  
  /**
   * Check whether a projectile is inside this instance's hit area.
   */
  def isHitBy(p: Projectile) = this.hitArea.isInside(p.coords)
  
  /**
   * Hit area
   */
  def hitArea = {
  	new Area(new Coords(this.coords.x, this.coords.y), new Coords(this.coords.x + this.sprite.getWidth(), this.coords.y + this.sprite.getHeight()))
  }
  
  
  /**
   * Check if a coordinate is inside this instance's collision box
   * Note to self: The actual coordinates are on the bottom left corner
   */
  def checkCollision(coords: Coords) = {
  	this.hitArea.isInside(coords)
  }
  
  /**
   * Returns the nearest instance (not self)
   */
  def instanceNearest(onlyEnemy: Boolean = true): Option[Instance] = {
    
    val enemies = if (onlyEnemy) World.instances.filter(x => x.side != this.side).toVector else World.instances.toVector
    
  	if (enemies.size < 1) {
  		None
  		
  	} else {
  		
  		var dist = this.coords.distanceToPoint(enemies(0).coords)
  		var nearest = enemies(0)
  		
  		for(ins <- enemies) {
  			if (ins != this && this.coords.distanceToPoint(ins.coords) < dist) {
  				nearest = ins
  			}
  		}
  		
  		Some(nearest)
  	}
  }
  
  /** 
   *  Execute alarms
   *  */
  def execute(funktio: () => Unit) = {
  	funktio()
  }
  
  
  /** Each instance's step event is called fps(60?) times a second. */
  def step()
  
  /**
   * Take dmg from projectiles for example
   */
  def takeDmg(dmg: Double) = {
  	this.hp -= dmg 
  	if (this.hp <= 0) {
  	  die()
  	}
  }
  
  def die() = {
  	if (this.isInstanceOf[EnemyUnit]) {
  		global.score += this.asInstanceOf[EnemyUnit].scoreGain
  		global.gold  += this.asInstanceOf[EnemyUnit].goldGain
  	}
		World.instances.remove(World.instances.indexOf(this))    
  }
  
  override def toString = name + ": " + this.coords.toString + " HP: " + hp + "/" + maxHp + " dmg: " + this.dmg
  
}
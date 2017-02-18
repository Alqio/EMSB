package com.mygdx.emsb

//import processing.core._
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch

import scala.collection.mutable.Map


/**
 * @author alkiok1
 */
abstract class Instance(ctrl: Controller) {
  val solid = false
  var sprite: Sprite
  var target: Option[Instance] = this.instanceNearest
  var projectile: Option[Projectile] = None
  
  var dmg: Double
  var range: Int = 45
  
  val maxHp: Double
  var hp: Double = maxHp
  
  
  //Coords class is mutable and changes when the coordinate changes
  var coords = new Coords(100,480)
  
  val alarms = Array.fill(3)(new Alarm(0, this))
  var alarmActions = Map[Alarm, () => Unit]()
  
  def placeholder() = {
    
  }
  
  for (i <- alarms) {
  	this.alarmActions += i -> placeholder
  }
  
  /** Draw the instance */
  def draw(batch: SpriteBatch) = {
    this.sprite.setPosition(this.coords.x.toFloat, this.coords.y.toFloat)
		this.sprite.draw(batch)
  }
  
  /**
   * Return the center position of the instance
   */
  def position = new Coords(this.coords.x, this.coords.y - this.sprite.getHeight()/2)
  
  /**
   * Check whether a target (usually a projectile?) hits this instance
   */
  def isHitBy(p: Projectile) = this.hitArea.isInside(p.coords)
  
  /**
   * Hit area
   */
  def hitArea = {
  	new Area(new Coords(this.coords.x - this.sprite.getWidth()/2, this.coords.y - this.sprite.getHeight()), new Coords(this.coords.x + this.sprite.getWidth()/2, this.coords.y))
  }
  
  
  /**
   * Check if a coordinate is inside this instance's collision box
   * Note to self: The actual coordinates are on the top left corner of the image, not in the center. 
   */
  def checkCollision(coords: Coords) = this.hitArea.isInside(coords)
  //coords.x >= this.coords.x - this.sprite.width/2 && coords.x <= this.coords.x + this.sprite.width/2 && coords.y >= this.coords.y  - this.sprite.height/2 && coords.y <= this.coords.y + this.sprite.height
  /**
   * Returns the nearest instance (not self)
   */
  def instanceNearest: Option[Instance] = {
  	if (World.instances.size == 0 || World.instances.size == 1) {
  		None
  		
  	} else {
  		
  		var dist = this.coords.distanceToPoint(World.instances(0).coords)
  		var nearest = if (World.instances(0) != this) World.instances(0) else World.instances(1)
  		
  		for(ins <- World.instances) {
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
  	println(this)
  	this.hp -= dmg 
  	if (this.hp <= 0) {
  		World.instances.remove(World.instances.indexOf(this))
  	}
  }
  
  
}
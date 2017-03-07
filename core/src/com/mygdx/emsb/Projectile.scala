package com.mygdx.emsb
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector3


import math._
/**
 * @author alkiok1
 * val coords: Coords, var direction: Int, var dmg: Int, val spritePath: String, var friendly: Boolean, ctrl: Controller
 */
 
class Projectile(val creator: Instance, val spritePath: String) {
	
  val sprite = new Sprite(new Texture(this.spritePath))
  var spd = 4.0
  val dmg = this.creator.dmg
  
  var coords = new Coords(this.creator.position.x, this.creator.position.y)
  //println(coords)
  var target = this.creator.target
  
  def move() = {
  	var alpha = atan(abs(this.target.get.position.y - this.coords.y)/abs(this.target.get.position.x - this.coords.x))
  	this.coords.x += (if (this.coords.x < this.target.get.position.x) cos(alpha)* spd else -1 * cos(alpha) * spd)
  	this.coords.y += (if (this.coords.y <= this.target.get.position.y) sin(alpha) * spd else -1 * sin(alpha) * spd)
  	
  }
  
  /**
   * Draw the projectile
   */
  def draw(batch: SpriteBatch) = {
    
    val pos = new Vector3(this.coords.x.toFloat, this.coords.y.toFloat, 0)
    
    this.sprite.setPosition(pos.x, pos.y)
		this.sprite.draw(batch)
  }
  
  /**
   * Step
   */
  def step() = {
  	if (World.projectiles.contains(this) && this.target.isDefined && World.instances.contains(this.target.get)) {
  		this.move()
  		if (this.target.get.isHitBy(this)) {
  			target.get.takeDmg(dmg)
  			this.sprite.getTexture().dispose()
  			World.projectiles.remove(World.projectiles.indexOf(this))
  			
  		}
  	}
  	
  }
  
  override def toString = "Projectile at: " + this.coords.toString  + ",    Index: " + World.projectiles.indexOf(this)
  
  
}

case class Snowball1(override val creator: Instance) extends Projectile(creator, "snowBall1.png") {
  spd = 5
  
}
case class Snowball2(override val creator: Instance) extends Projectile(creator, "snowBall2.png") {
  spd = 8
  
}

/**
image_angle
spd
step
 - hit or move, check collision on the edge of the sprite HOW?????
ala classit? 

TARGET.ISHIT TMS


*/
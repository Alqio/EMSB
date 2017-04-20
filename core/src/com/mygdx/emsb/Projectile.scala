package com.mygdx.emsb
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector3


import math._
import com.mygdx.instances.Instance

/**
 * @author alkiok1
 * val coords: Coords, var direction: Int, var dmg: Int, val spritePath: String, var friendly: Boolean, ctrl: Controller
 */
 
class Projectile(val creator: Instance, val spritePath: String) {
	
	//global.sprites(spritePath)
	
  val sprite = new Sprite(new Texture(spritePath))
  //sprite.set(global.sprites("snowBall1"))
  this.sprite.setOriginCenter()
  var spd = 4.0
  var rot = 0
  val dmg = this.creator.dmg
  var alpha: Double = 0
  var typeOf = "normal"
  
  var coords = new Coords(this.creator.position.x, this.creator.position.y)
  var target = this.creator.target
  
  def move() = {
  	alpha = atan(abs(this.target.get.position.y - this.coords.y)/abs(this.target.get.position.x - this.coords.x))
  	//println(this + ": " + alpha* 360 / math.Pi)
  	this.coords.x += (if (this.coords.x < this.target.get.position.x) cos(alpha)* spd else -1 * cos(alpha) * spd)
  	this.coords.y += (if (this.coords.y <= this.target.get.position.y) sin(alpha) * spd else -1 * sin(alpha) * spd)
  	
  }
  
  /**
   * Draw the projectile
   */
  def draw(batch: SpriteBatch) = {
    
    val pos = new Vector3(this.coords.x.toFloat, this.coords.y.toFloat, 0)
    if (rot == 0) {
	    if (this.target.isDefined && this.coords.x > this.target.get.position.x)
	    	this.sprite.setRotation((alpha* 360 / math.Pi).toFloat)
	    else 
	    	this.sprite.setRotation((alpha* 360 / math.Pi * -1).toFloat)
    } else {
    	this.sprite.rotate(rot)
    }
   // this.sprite.rotate((alpha* 360 / math.Pi).toFloat)
    this.sprite.setPosition(pos.x - global.camera.coords.x.toFloat, pos.y)
		this.sprite.draw(batch)
  }
  
  /**
   * Step
   */
  def step() = {
  	
  	if (this.target.isEmpty || !World.instances.contains(this.target.get)) {
  		World.projectiles.remove(World.projectiles.indexOf(this))
  	} else {
	  	if (World.projectiles.contains(this) && this.target.isDefined && World.instances.contains(this.target.get)) {
	  		this.move()
	  		if (this.target.get.isHitBy(this)) {
	  			
	  			target.get.takeDmg(this)
	  			World.projectiles.remove(World.projectiles.indexOf(this))
	  			this.sprite.getTexture().dispose()
	  		}
	  	}
  	}
  }
  
  override def toString = "Projectile at: " + this.coords.toString
  
  
}

case class Snowball1(override val creator: Instance) extends Projectile(creator, "images/snowBall1.png") {
  spd = 8
  
}
case class Snowball2(override val creator: Instance) extends Projectile(creator, "images/snowBall4.png") {
  spd = 16
  typeOf = "ice"
}
case class Snowball3(override val creator: Instance) extends Projectile(creator, "images/snowBall3.png") {
  spd = 8
  typeOf = "fire"
  
}
case class Snowball4(override val creator: Instance) extends Projectile(creator, "images/snowBall6.png") {
  spd = 10
  typeOf = "poison"
  
}
case class Fireball(override val creator: Instance) extends Projectile(creator, "images/fireBall.png") {
	spd = 10
	typeOf = "enemyFireball"
}
case class Bone(override val creator: Instance) extends Projectile(creator, "images/bone.png") {
	spd = 15
	typeOf = "bone"
	rot = 30
}

/**
image_angle
spd
step
 - hit or move, check collision on the edge of the sprite HOW?????
ala classit? 

TARGET.ISHIT TMS


*/
package com.mygdx.emsb
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector3

import Methods._

import math._
import com.mygdx.instances.Instance

/**
 * Projectile is created by an instance and tries to hit its target to deal damage.
 */
 
class Projectile(val creator: Instance, val spritePath: String) {
	
	//the sprite has to be loaded for each projectile, because otherwise rotating it would affect all sprites
  val sprite = new Sprite(new Texture(spritePath))

  
  this.sprite.setOriginCenter()
  var spd = 4.0
  var rot = 0
  val dmg = this.creator.dmg
  var alpha: Double = 0
  var typeOf = "normal"
  
  var coords = new Coords(this.creator.position.x, this.creator.position.y)
  var target = this.creator.target
  
  /**
   * Move the projectile
   */
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
    this.sprite.setPosition(pos.x - global.camera.coords.x.toFloat, pos.y)
		this.sprite.draw(batch)
  }
  
  /**
   * Destroy this projectile
   */
  def destroy() = {
		World.projectiles.remove(World.projectiles.indexOf(this))
		this.sprite.getTexture().dispose()  	
  }
  
  /**
   * Set new target if needed
   */
  def setTarget() = {}
  
  /**
   * Step event for the projectile. Projectile will try to move until it hits its target
   */
  def step() = {
  	
  	if (this.target.isEmpty || !World.instances.contains(this.target.get)) {
  		this.destroy()
  	} else {
	  	if (World.projectiles.contains(this) && this.target.isDefined && World.instances.contains(this.target.get)) {
	  		this.move()
	  		this.setTarget()
	  		if (this.target.get.isHitBy(this)) {
	  			target.get.takeDmg(this)
	  			this.destroy()
	  		}
	  	}
  	}
  }
  
  override def toString = "Projectile at: " + this.coords.toString
  
  
}

case class Snowball1(override val creator: Instance) extends Projectile(creator, "images/snowBall1.png") {
  spd = 8
  
}
case class Snowball7(override val creator: Instance) extends Projectile(creator, "images/snowBall7.png") {
  spd = 12
  
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

case class FallingFireballBig(override val creator: Instance) extends Projectile(creator, "images/fireBallBig.png") {
	spd = 5 * randomRange(0.8,1.2)
	coords = new Coords(this.creator.position.x, this.creator.position.y + irandomRange(-3,3))
	typeOf = "fire"
	var side = "enemy"
	override def setTarget() = {
		this.target = instanceNearest()
	}
	
  override def move() = {
  	this.coords.y -= spd
  	
  	if (this.coords.y <= 200) {
  		this.destroy()
  	}
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

			for (ins <- enemies) {
				if (ins != this && this.coords.distanceToPoint(ins.coords) < dist) {
					nearest = ins
					dist = this.coords.distanceToPoint(nearest.coords)
				}
			}

			Some(nearest)
		}
	}  
}
/**
 * Falling fireball falls from the sky
 */
case class FallingFireball(override val creator: Instance) extends Projectile(creator, "images/fireBall.png") {
	spd = 5 * randomRange(0.8,1.2)
	coords = new Coords(this.creator.position.x, this.creator.position.y + irandomRange(-3,3))
	typeOf = "fire"
	var side = "enemy"
	override def setTarget() = {
		this.target = instanceNearest()
	}
	
  override def move() = {
  	this.coords.y -= spd
  	
  	if (this.coords.y <= 200) {
  		this.destroy()
  	}
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

			for (ins <- enemies) {
				if (ins != this && this.coords.distanceToPoint(ins.coords) < dist) {
					nearest = ins
					dist = this.coords.distanceToPoint(nearest.coords)
				}
			}

			Some(nearest)
		}
	}  
}


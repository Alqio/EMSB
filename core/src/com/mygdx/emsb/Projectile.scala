package com.mygdx.emsb
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
/**
 * @author alkiok1
 * val coords: Coords, var direction: Int, var dmg: Int, val spritePath: String, var friendly: Boolean, ctrl: Controller
 */
 
class Projectile(val creator: Instance, val spritePath: String, ctrl: Controller) {
	
  val sprite = new Sprite(new Texture(this.spritePath))
  var spd = 4.0
  val dmg = this.creator.dmg
  
  var coords = new Coords(this.creator.coords.x, this.creator.position.y)
  var target = this.creator.target
  
  def move() = {
  	
  	this.coords.x += (if (this.coords.x < this.target.get.position.x) spd else -1 * spd)
  	this.coords.y += (if (this.coords.y < this.target.get.position.y) spd else -1 * spd)
  	
  }
  
  /**
   * Draw the projectile
   */
  def draw() = {}//ctrl.image(this.sprite, this.coords.x.toInt - this.sprite.width/2, this.coords.y.toInt - this.sprite.height/2);
  
  /**
   * Step
   */
  def step() = {
  	if (World.projectiles.contains(this) && this.target.isDefined && World.instances.contains(this.target.get)) {
  		this.move()
  		if (this.target.get.isHitBy(this)) {
  			target.get.takeDmg(dmg)
  			println("this.index : " + World.projectiles.indexOf(this))
  			println("size before removal: " + World.projectiles.size)
  			
  			World.projectiles(World.projectiles.indexOf(this)) = null
  			
  			//World.projectiles.remove(World.projectiles.indexOf(this))
  			
  			
  			println("size after removal: " + World.projectiles.size)
  			World.projectiles.foreach(println)
  			
  		}
  	}
  	
  }
  
  override def toString = "Projectile at: " + this.coords.toString  + ",    Index: " + World.projectiles.indexOf(this)
  
  
}
/**
image_angle
spd
step
 - hit or move, check collision on the edge of the sprite HOW?????
ala classit? 

TARGET.ISHIT TMS


*/
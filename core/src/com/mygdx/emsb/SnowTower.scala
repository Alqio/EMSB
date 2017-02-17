package com.mygdx.emsb
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite

class SnowTower(ctrl: Controller) extends Building(ctrl) {
  override val maxHp = 100.0
  var dmg = 1.0
  range = 250
  var attackSpeed = 45
  var sprite = new Sprite(new Texture("snowTower.png"))
  
  this.alarmActions(this.alarms(0)) = () => {
  	attack()
  	this.alarms(0).time += attackSpeed
  }
  
  def step() = {
  	/** Set target **/
  	this.target = this.instanceNearest
  	
  	/** Attack only if the target is defined **/
  	if (this.target != None) {
	  	if (this.alarms(0).time == -1) {
	  		this.alarms(0).time += attackSpeed
	  	}
  	}
    
  }
  
  def attack() = {
  	if (this.target.isDefined && this.coords.distanceToPoint(this.target.get.coords) <= this.range) {
  		var i = new Projectile(this, "towerProjectile.png", ctrl)
  		World.projectiles += i
  		println("nyt ammuttiin taas")
  	} else if (this.target.isEmpty){
  		this.alarms(0).time = -1
  	}
  }
  
  override def toString = "Snow Tower at: " + this.coords.toString
}
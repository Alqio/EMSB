package com.mygdx.emsb
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import collection.mutable.Map

import Methods._

class SnowTower(ctrl: Controller) extends Building(ctrl) {
  
  
  
  upgrades += "names"  -> Array("Normal", "Ice", "Fire", "Poison")
  upgrades += "damage" -> Array(1.0, 3.0, 4.5, 3.0)
  upgrades += "maxHp"  -> Array(100, 150, 150, 150)
  upgrades += "sprite" -> Array("snowTower", "snowTower", "snowTower", "snowTower")
  
  
  maxHp       = 100.0 * global.buildingHpMultiplier
  hp          = maxHp
  dmg         = 1.0   * global.buildingDmgMultiplier
  range       = 250
  attackSpeed = 45
  sprite      = global.sprites("snowTower")
  
  
  def step() = {
  	/** Set target */
  	this.target = this.instanceNearest()
  	
  	/** Attack only if the target is defined 
  	 *  Alarm(0) (and AlarmActions(0)) is for attack
  	 *  */
  	if (this.target.isDefined) {
	  	if (this.alarms(0).time == -1) {
	  		this.alarms(0).time += attackSpeed
	  	}
  	}
    
  }
  
  def attack() = {
  	if (this.target.isDefined && this.coords.distanceToPoint(this.target.get.coords) <= this.range) {
  		var i = choose(new Snowball1(this), new Snowball2(this))
  		World.projectiles += i
  	} else if (this.target.isEmpty){
  		this.alarms(0).time = -1
  	}
  }
  
  override def toString = "Snow Tower at: " + this.coords.toString + " HP: " + this.hp + "/" + this.maxHp
}
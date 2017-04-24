package com.mygdx.instances

import collection.mutable.Map
import com.mygdx.emsb.global

abstract class Building() extends Instance(){
  override val solid = true
  override val side = "friendly"
  
  val upgrades = Map[String, Array[Any]]()
  var level = 0
  var maxLevel = 3
  var sndShoot = global.sounds("towerShoot")
  
  /**
   * This method updates the building's hp and damage when they have been upgraded in researchCenter.
   */
  def update(str: String) = {
  	var level = global.upgrades(str)("level").asInstanceOf[Int] 
  	str match {
  		case "DmgUpgrade" => this.dmg *= global.buildingDmgMultiplier
  		case "HpUpgrade"  => {
  			this.maxHp *= global.buildingHpMultiplier
  			this.hp    *= global.buildingHpMultiplier
  		}
  		case "ASUpgrade"  => this.attackSpeed = (this.attackSpeed * global.buildingASMultiplier).toInt
  	}
  }
  /**
   * What happens when selecting this building
   */
  def onSelection()
  def unlock(typeOf: String, str: String)
  
  def setTarget() = this.target = this.instanceNearest()
  
  def step() = {
  	
  	if (this.hp < this.maxHp) {
  		this.hp += global.buildingRepairSpeed
  		if (this.hp > this.maxHp) {
  			this.hp = this.maxHp
  		}
  	}
  	
  	
  	/** Set target */
  	this.setTarget()
  	
  	/** Attack only if the target is defined 
  	 *  Alarm(0) (and AlarmActions(0)) is for attack
  	 *  */
  	if (this.target.isDefined && this.canAttack) {
		  this.alarms(0).time += attackSpeed
		  
		  attack()
  	}
    
  }
  
  def canAttack: Boolean = {
  	this.coords.distanceToPoint(this.target.get.coords) <= this.range && this.alarms(0).time == -1
  }
  
}
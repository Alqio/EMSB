package com.mygdx.emsb

import com.badlogic.gdx.graphics.g2d.Sprite
import collection.mutable.Map

abstract class Building() extends Instance(){
  override val solid = true
  override val side = "friendly"
  
  val upgrades = Map[String, Array[Any]]()
  var level = 0
  var maxLevel = 3
  
  
  /**
   * This method updates the building's hp and damage when they have been upgraded in researchCenter.
   */
  def update(str: String) = {
  	println("updated!")
  	var level = global.upgrades(str)("level").asInstanceOf[Int] 
  	str match {
  		case "DmgUpgrade" => this.dmg *= global.buildingDmgMultiplier
  		case "HpUpgrade"  => {
  			this.maxHp *= global.buildingHpMultiplier
  			this.hp    *= global.buildingHpMultiplier
  		}  		
  	}
  }
  
  def onSelection()
  def unlock(typeOf: String, str: String)
  
  def step() = {
  	/** Set target */
  	this.target = this.instanceNearest()
  	
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
package com.mygdx.emsb

import com.badlogic.gdx.graphics.g2d.Sprite
import collection.mutable.Map

abstract class Building(ctrl: Controller) extends Instance(ctrl){
  override val solid = true
  override val side = "friendly"
  
  val upgrades = Map[String, Array[Any]]()
  var level = 1
  var maxLevel = 4
  
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
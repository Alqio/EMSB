package com.mygdx.emsb

import com.badlogic.gdx.graphics.g2d.Sprite
import collection.mutable.Map

abstract class Building(ctrl: Controller) extends Instance(ctrl){
  override val solid = true
  override val side = "friendly"
  
  val upgrades = Map[String, Array[Any]]()
  var level = 0
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
  
    /** 
   *  Upgrade the tower to a level. Can also be used to downgrade if needed
   *  @param level     The level to upgrade/downgrade
   *  */
  def upgrade(level: Int) = {
    for (i <- upgrades.keys) {
      i match {
        case "damage" => this.dmg = upgrades("damage")(level).asInstanceOf[Double] * global.buildingDmgMultiplier
        case "maxHp"  => {
        	this.maxHp = upgrades("maxHp")(level).asInstanceOf[Int] * global.buildingHpMultiplier
        	this.hp    = this.maxHp
        }
        case "sprite" => this.sprite = upgrades("sprite")(level).asInstanceOf[Sprite]
        case _ =>
      }
      
    }
    this.level = level
  }
  
}
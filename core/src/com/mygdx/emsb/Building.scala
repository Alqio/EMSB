package com.mygdx.emsb

import com.badlogic.gdx.graphics.g2d.Sprite
import collection.mutable.Map

abstract class Building(ctrl: Controller) extends Instance(ctrl){
  override val solid = true
  override val side = "friendly"
  
  val upgrades = Map[String, Array[Any]]()
  var level = 0
  
  
    /** 
   *  Upgrade the tower to a level. Can also be used to downgrade if needed
   *  @param level     The level to upgrade/downgrade
   *  */
  def upgrade(level: Int) = {
    for (i <- upgrades.keys) {
      
      i match {
        case "damage" => this.dmg = upgrades("damage")(level).asInstanceOf[Double] * global.buildingDmgMultiplier
        case "maxHp"  => this.maxHp = upgrades("maxHp")(level).asInstanceOf[Int] * global.buildingHpMultiplier
        case "sprite" => this.sprite = upgrades("sprite")(level).asInstanceOf[Sprite]
      }
      
    }
  }
  
}
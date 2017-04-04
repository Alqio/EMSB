package com.mygdx.emsb
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import collection.mutable.Map

import Methods._

class SnowTower(ctrl: Controller) extends Building(ctrl) {
  
  
  
  upgrades += "names"  -> Array("Normal", "Ice", "Fire", "Poison")
  upgrades += "damage" -> Array(1.0, 3.0, 4.5, 3.0)
  upgrades += "maxHp"  -> Array(100, 150, 150, 150)
  upgrades += "sprite" -> Array(global.sprites("snowTower"), global.sprites("snowTower"), global.sprites("fireTower"), global.sprites("snowTower"))
  
  
  
  maxLevel    = this.upgrades("names").size
  maxHp       = 100.0 * global.buildingHpMultiplier
  hp          = maxHp
  dmg         = 1.0   * global.buildingDmgMultiplier
  range       = 250
  attackSpeed = 5
  sprite      = global.sprites("snowTower")
  name				= "Snow tower"
  
  val towerUnlocks = global.towerUnlocks.values.map(x => x("unlocked").asInstanceOf[Boolean])
  
  var unlocked = (Array(1,2,3,4) zip towerUnlocks).toMap
  println(unlocked)
  
  
  /**
   * This method will be called only if
   * 1) target exists
   * 2) the distance between this and target is <= range
   * 3) alarm(0) == -1
   * 
   * alarm(0) will be set to attack speed and then this method will be called
   */
  def attack() = {
		var i = choose(new Snowball1(this), new Snowball2(this))
		World.projectiles += i
  }
  
   /** 
   *  Upgrade the tower to a level. Can also be used to downgrade if needed
   *  @param level     The level to upgrade/downgrade
   *  */
  def upgrade(level: Int) = {
  	println("trying to upgrade to level: " + level)
  	
  	val realLevel = math.max(0, level - 1)
  	
  	if (unlocked(level)) {
  	
    for (i <- upgrades.keys) {
      i match {
        case "damage" => this.dmg = upgrades("damage")(realLevel).asInstanceOf[Double] * global.buildingDmgMultiplier
        case "maxHp"  => {
        	this.maxHp = upgrades("maxHp")(realLevel).asInstanceOf[Int] * global.buildingHpMultiplier
        	this.hp    = this.maxHp
        }
        case "sprite" => this.sprite = upgrades("sprite")(realLevel).asInstanceOf[Sprite]
        case _ =>
      }
      
    }
    this.level = level
  	}
  }  
  
  
}
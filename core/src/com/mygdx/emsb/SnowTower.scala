package com.mygdx.emsb
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import collection.mutable.Map

import Methods._

class SnowTower() extends Building() {
  
  
  upgrades += "names"  -> Array("Normal", "Ice", "Fire", "Poison")
  upgrades += "damage" -> Array(1.0, 3.0, 4.5, 3.0)
  upgrades += "maxHp"  -> Array(100, 150, 150, 150)
  upgrades += "sprite" -> Array(global.sprites("snowTower"), global.sprites("iceTower"), global.sprites("fireTower"), global.sprites("poisonTower"))
  
  
  
  maxLevel    = this.upgrades("names").size
  maxHp       = 100.0 * global.buildingHpMultiplier
  hp          = maxHp
  dmg         = 1.0   * global.buildingDmgMultiplier
  range       = 250
  attackSpeed = 5
  sprite      = global.sprites("snowTower")
  name				= "Snow tower"
  
  var buttons = Map[String, Button]( 
  	"FireTower"    -> new UnlockButton(this, "Fire", Area(Coords(420, 64), UpgradeButton.width, UpgradeButton.height), true),
  	"IceTower"     -> new UnlockButton(this, "Ice",  Area(Coords(484, 64), UpgradeButton.width, UpgradeButton.height), true),
  	"PoisonTower"  -> new UnlockButton(this, "Poison",  Area(Coords(548, 64), UpgradeButton.width, UpgradeButton.height), true)
  )
  
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
  
  def unlock(typeOf: String, str: String) = {
  	if (global.gold >= global.unlocks(str)("buildCost").asInstanceOf[Int]) {
	  	str match {
	  		case "Ice" 		=> this.upgrade(1)
	  		case "Fire"   => this.upgrade(2)
	  		case "Poison" => this.upgrade(3)
	  		case _ 				=> println("Unknown upgrade type: " + str)
	  	}
	  	World.buttons.clear()
  	}
  }

  
  def onSelection() = {
  	if (level == 0) {
	  	for (i <- buttons.values) {
	  		if (global.unlocks(i.target)("unlocked").asInstanceOf[Boolean])
	  			World.buttons += i
	  	}
  	}
  }
  
   /** 
   *  Upgrade the tower to a level. Can also be used to downgrade if needed
   *  @param level     The level to upgrade/downgrade
   *  */
  def upgrade(level: Int) = {
  	println("trying to upgrade to level: " + level)
  	
  	val realLevel = level
  	
  	if (global.unlocked(level)) {
  	
	    for (i <- upgrades.keys) {
	      i match {
	        case "damage" => this.dmg = upgrades("damage")(realLevel).asInstanceOf[Double] * math.pow(global.buildingDmgMultiplier, global.buildingDmgLevel)
	        case "maxHp"  => {
	        	this.maxHp = upgrades("maxHp")(realLevel).asInstanceOf[Int] * math.pow(global.buildingHpMultiplier, global.buildingHpLevel) 
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
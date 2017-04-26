package com.mygdx.instances

import com.badlogic.gdx.graphics.g2d.Sprite
import collection.mutable.Map
import Methods._
import com.mygdx.emsb._
import com.mygdx.emsb.World
import com.mygdx.emsb.global

class SnowTower() extends Building() {
  
  
  upgrades += "names"  -> Array("Normal", "Ice", "Fire", "Poison")
  upgrades += "damage" -> Array(1.0, 2.4, 3.9, 2.6)
  upgrades += "maxHp"  -> Array(20, 50, 40, 45)
  upgrades += "sprite" -> Array(global.sprites("snowTower"), global.sprites("iceTower"), global.sprites("fireTower"), global.sprites("poisonTower"))
  
  
  
  maxLevel    = this.upgrades("names").size
  maxHp       = 55.0 * math.pow(global.buildingHpMultiplier,global.buildingHpLevel)
  hp          = maxHp
  dmg         = 0.6  * math.pow(global.buildingDmgMultiplier, global.buildingDmgLevel)
  range       = 250
  attackSpeed = (55   * math.pow(global.buildingASMultiplier, global.buildingASLevel)).toInt
  sprite      = global.sprites("snowTower")
  name				= "Snow tower"
  
  var buttons = Map[String, Button]( 
  	"FireTower"    -> new UnlockButton(this, "Fire", Area(Coords(420, 92), UpgradeButton.width, UpgradeButton.height), true),
  	"IceTower"     -> new UnlockButton(this, "Ice",  Area(Coords(484 + 1, 92), UpgradeButton.width, UpgradeButton.height), true),
  	"PoisonTower"  -> new UnlockButton(this, "Poison",  Area(Coords(548 + 2, 92), UpgradeButton.width, UpgradeButton.height), true)
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
  	sndShoot.play(0.5f * global.volume)
		var i = level match {
			case 0 => new Snowball1(this)
			case 1 => new Snowball2(this)
			case 2 => new Snowball3(this)
			case 3 => new Snowball4(this)
		}
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
	  	global.gold -= global.unlocks(str)("buildCost").asInstanceOf[Int]
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
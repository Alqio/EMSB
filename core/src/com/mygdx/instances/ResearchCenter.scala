package com.mygdx.instances

import com.mygdx.emsb.Button
import com.mygdx.emsb.UnlockButton
import com.mygdx.emsb.UpgradeButton
import com.mygdx.emsb.World
import com.mygdx.emsb.global
import com.mygdx.emsb.Coords
import com.mygdx.emsb.Area

class ResearchCenter() extends Building() {
  
	dmg               = 0
  name              = "Research center"
  hp                = 50 * math.pow(global.buildingHpMultiplier,global.buildingHpLevel)
  maxHp             = hp
  sprite            = global.sprites("researchCenter")
  maxLevel          = 1 
  var targetUnlock  = "Fire"
  
  var buttons = Map[String, Button]( // new Area(Coords(420, 40), Coords(452, 72))
  	"DmgUpgrade"   -> UpgradeButton(this, "DmgUpgrade", Area(Coords(420, 92), UpgradeButton.width, UpgradeButton.height)),
  	"HpUpgrade"    -> UpgradeButton(this, "HpUpgrade",  Area(Coords(484 + 1, 92), UpgradeButton.width, UpgradeButton.height)),
  	"ASUpgrade"    -> UpgradeButton(this, "ASUpgrade",  Area(Coords(548 + 2, 92), UpgradeButton.width, UpgradeButton.height)),
  	"FireUnlock"   -> new UnlockButton(this, "Fire", Area(Coords(548 + 64 + 3, 92), UpgradeButton.width, UpgradeButton.height)),
  	"IceUnlock"    -> new UnlockButton(this, "Ice", Area(Coords(548 + 64 + 64 + 4, 92), UpgradeButton.width, UpgradeButton.height)),
  	"PoisonUnlock" -> new UnlockButton(this, "Poison", Area(Coords(548 + 128 + 64 + 5, 92), UpgradeButton.width, UpgradeButton.height)),
  	"AntiAirUnlock" -> new UnlockButton(this, "AntiAir", Area(Coords(420 - 64 - 1, 92), UpgradeButton.width, UpgradeButton.height))
  )
  
  /**
   * Research center cannot attack
   */
  def attack() = {}
  
	
	
	def onSelection() = {
  	for (i <- buttons.values) {
  		
  		if (!i.isInstanceOf[UnlockButton]) {
  			World.buttons += i
  		} else if (!global.unlocks(i.target)("unlocked").asInstanceOf[Boolean]) {
  			World.buttons += i
  		}
  		
  	}
	}
	
  /**
   * Unlock or upgrade
   * @param typeOf is either unlock or upgrade
   */
  def unlock(typeOf: String, str: String) = {
  	if (typeOf == "upgrade") {
	  	val upgrade = global.upgrades(str)
	  	
	  	if (global.gold >= upgrade("cost").asInstanceOf[Int]) {
	  		upgrade("level") = upgrade("level").asInstanceOf[Int] + 1
	  		global.gold -= upgrade("cost").asInstanceOf[Int]
	  		upgrade("cost") = (upgrade("cost").asInstanceOf[Int] * 1.5).asInstanceOf[Int]
	  		World.instances.foreach(x => if (x.isInstanceOf[Building]) x.asInstanceOf[Building].update(str))
	  	}
	  	
  	} else if (typeOf == "unlock") {
  		val unlock = global.unlocks(str)
  		
	  	if (global.gold >= unlock("cost").asInstanceOf[Int]) {
	  		unlock("unlocked") = true
	  		global.gold -= unlock("cost").asInstanceOf[Int]
	  		World.buttons.clear()
	  		this.onSelection()
	  		global.updateUnlocked()
	  	}  		
  		
  	}
  }
  
}
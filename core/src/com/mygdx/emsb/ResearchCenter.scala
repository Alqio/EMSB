package com.mygdx.emsb

class ResearchCenter() extends Building() {
  
	dmg               = 0
  name              = "Research center"
  hp                = 150
  maxHp             = hp
  sprite            = global.sprites("researchCenter")
  maxLevel          = 1 
  var targetUnlock  = "Fire"
  
  var buttons = Map[String, Button]( // new Area(Coords(420, 40), Coords(452, 72))
  	"DmgUpgrade"   -> UpgradeButton(this, "DmgUpgrade", Area(Coords(420, 64), UpgradeButton.width, UpgradeButton.height)),
  	"HpUpgrade"    -> UpgradeButton(this, "HpUpgrade",  Area(Coords(484, 64), UpgradeButton.width, UpgradeButton.height)),
  	"ASUpgrade"    -> UpgradeButton(this, "ASUpgrade",  Area(Coords(548, 64), UpgradeButton.width, UpgradeButton.height)),
  	"FireUnlock"   -> new UnlockButton(this, "Fire", Area(Coords(420, 128), UpgradeButton.width, UpgradeButton.height)),
  	"IceUnlock"    -> new UnlockButton(this, "Ice", Area(Coords(484, 128), UpgradeButton.width, UpgradeButton.height)),
  	"PoisonUnlock" -> new UnlockButton(this, "Poison", Area(Coords(548, 128), UpgradeButton.width, UpgradeButton.height))
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
   * typeOf is either unlock or upgrade
   */
  def unlock(typeOf: String, str: String) = {
  	if (typeOf == "upgrade") {
	  	val upgrade = global.upgrades(str)
	  	
	  	println("Upgrade: " + upgrade)
	  	
	  	if (global.gold >= upgrade("cost").asInstanceOf[Int]) {
	  		upgrade("level") = upgrade("level").asInstanceOf[Int] + 1
	  		global.gold -= upgrade("cost").asInstanceOf[Int]
	  		
	  		World.instances.foreach(x => if (x.isInstanceOf[Building]) x.asInstanceOf[Building].update(str))
	  	}
	  	
  	} else if (typeOf == "unlock") {
  		val unlock = global.unlocks(str)
  		println("Unlock: " + unlock)
  		
	  	if (global.gold >= unlock("cost").asInstanceOf[Int]) {
	  		unlock("unlocked") = true
	  		global.gold -= unlock("cost").asInstanceOf[Int]
	  		global.updateUnlocked()
	  		//World.buttons.toVector.foreach(x => if (x.isInstanceOf[UnlockButton] && x.target == str) World.buttons -= x)
	  	}  		
  		
  	}
  }
  
}
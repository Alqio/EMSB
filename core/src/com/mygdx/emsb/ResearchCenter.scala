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
  	"DmgUpgrade" -> UpgradeButton(this, "DmgUpgrade", Area(Coords(420, 64), UpgradeButton.width, UpgradeButton.height)),
  	"HpUpgrade"  -> UpgradeButton(this, "HpUpgrade",  Area(Coords(485, 64), UpgradeButton.width, UpgradeButton.height))
  )
  
  /**
   * Research center cannot attack
   */
  def attack() = {}
  
	
	
	def onSelection() = {
  	for (i <- buttons.values) {
  		World.buttons += i
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
  		
  	}
  }
  
}
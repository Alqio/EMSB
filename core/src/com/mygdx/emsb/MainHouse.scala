package com.mygdx.emsb

class MainHouse extends Building {
	
  maxHp       = 500.0 * global.buildingHpMultiplier
  hp          = maxHp
  dmg         = 0.5   * global.buildingDmgMultiplier
  range       = 300
  attackSpeed = 45
  sprite      = global.sprites("mainHouse")
  name				= "Main house"  

  var buttons = Map[String, Button]( // new Area(Coords(420, 40), Coords(452, 72))
  	"snowTower"  -> new BuildButton(this, "snowTower", Area(Coords(420, 64), UpgradeButton.width, UpgradeButton.height))
  )  
  
	def onSelection() = {
  	for (i <- buttons.values) {
  		World.buttons += i
  	}  	
  }
	def unlock(typeOf: String, str: String) = {}
	def attack() = {
		var i = new Snowball1(this)
		World.projectiles += i		
	}
	
}
package com.mygdx.emsb

class MainHouse extends Building {
	
  maxHp       = 200.0 * global.buildingHpMultiplier
  hp          = maxHp
  dmg         = 0.5   * global.buildingDmgMultiplier
  range       = 300
  attackSpeed = 45
  sprite      = global.sprites("mainHouse")
  name				= "Main house"  

  var buttons = Map[String, Button]( 
  	"snowTower"      -> new BuildButton(this, "snowTower", Area(Coords(420, 64), UpgradeButton.width, UpgradeButton.height)),
  	"researchCenter" -> new BuildButton(this, "researchCenter", Area(Coords(484, 64), UpgradeButton.width, UpgradeButton.height))
  )  
  
	def onSelection() = {
  	for (i <- buttons.values) {
  		World.buttons += i
  	}
  }
	def unlock(typeOf: String, str: String) = {}
	
	def attack() = {
		sndShoot.play(0.5f)
		var i = new Snowball1(this)
		World.projectiles += i		
	}
	
}
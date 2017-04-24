package com.mygdx.instances

import com.mygdx.emsb.BuildButton
import com.mygdx.emsb.Button
import com.mygdx.emsb.Snowball1
import com.mygdx.emsb.UpgradeButton
import com.mygdx.emsb.World
import com.mygdx.emsb.global
import com.mygdx.emsb.Coords
import com.mygdx.emsb.Area

import collection.mutable.Map

class MainHouse extends Building {
	
  maxHp       = 200.0 * math.pow(global.buildingHpMultiplier,global.buildingHpLevel)
  hp          = maxHp
  dmg         = 0.5   * math.pow(global.buildingDmgMultiplier, global.buildingDmgLevel)
  range       = 300
  attackSpeed = 45
  sprite      = global.sprites("mainHouse")
  name				= "Main house"  

  var buttons = Map[String, Button]( 
  	"snowTower"      -> new BuildButton(this, "snowTower", Area(Coords(420 + 64, 92), UpgradeButton.width, UpgradeButton.height)),
  	"researchCenter" -> new BuildButton(this, "researchCenter", Area(Coords(484 + 64 + 1, 92), UpgradeButton.width, UpgradeButton.height)),
  	"barracks"       -> new BuildButton(this, "barracks", Area(Coords(548 + 64 + 2, 92), UpgradeButton.width, UpgradeButton.height)),
  	"wall"   				 -> new BuildButton(this, "wall", Area(Coords(612 + 64 + 3, 92), UpgradeButton.width, UpgradeButton.height))
  )  
  
	def onSelection() = {
  	for (i <- buttons.values) {
  		World.buttons += i
  	}
  	if (global.unlocks("AntiAir")("unlocked").asInstanceOf[Boolean]) {
  		World.buttons += new BuildButton(this, "antiAir", Area(Coords(612 + 64 + 64 + 4, 92), UpgradeButton.width, UpgradeButton.height))
  	}
  }
	def unlock(typeOf: String, str: String) = {}
	
	def attack() = {
		sndShoot.play(0.5f)
		var i = new Snowball1(this)
		World.projectiles += i		
	}
	
}
package com.mygdx.instances


import com.mygdx.emsb.BarracksButton
import com.mygdx.emsb.Button
import com.mygdx.emsb.Coords
import com.mygdx.emsb.UpgradeButton
import com.mygdx.emsb.World
import com.mygdx.emsb.global
import com.mygdx.emsb.Area

class Barracks() extends Building(){
	
	dmg               = 0
  name              = "Barracks"
  hp                = 100 * math.pow(global.buildingHpMultiplier,global.buildingHpLevel)
  maxHp             = hp
  sprite            = global.sprites("barracks")
  maxLevel          = 1 
    
  var buttons = Map[String, Button]( 
  	"Infantry"   -> new BarracksButton(this, "Infantry", Area(Coords(420, 64), UpgradeButton.width, UpgradeButton.height))
  )
  	
	def spawn(what: String) = {
		for (i <- 0 until 3) {
			var j = new Infantry()
			j.coords = new Coords(this.coords.x - 12 + 12 * i, this.coords.y)
			World.instances += j
		}
	}
	
  def onSelection() = {
  	for (i <- buttons.values) {
  		World.buttons += i
  	}  	
  }
  def unlock(typeOf: String, str: String) = {
  	if (typeOf == "spawn") {
  		val upgrade = global.upgrades(str)
  		if (global.gold >= upgrade("cost").asInstanceOf[Int]) {
  			global.gold -= upgrade("cost").asInstanceOf[Int]
  			spawn(str)
  		}
  	}
  }
  
  /**
   * Barracks cannot attack
   */
  def attack() = {}
}
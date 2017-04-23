package com.mygdx.instances

import com.mygdx.emsb.BuildButton
import com.mygdx.emsb.Button
import com.mygdx.emsb.Snowball7
import com.mygdx.emsb.UpgradeButton
import com.mygdx.emsb.World
import com.mygdx.emsb.global
import com.mygdx.emsb.Coords
import com.mygdx.emsb.Area

class AntiAir extends Building {
	
  maxHp       = 30.0 * math.pow(global.buildingHpMultiplier,global.buildingHpLevel)
  hp          = maxHp
  dmg         = 3.2    * math.pow(global.buildingDmgMultiplier, global.buildingDmgLevel)
  range       = 400
  attackSpeed = 14
  sprite      = global.sprites("antiAir")
  name				= "Anti Air tower"  
  sndShoot    = global.sounds("antiAirShoot")
  
  override def setTarget() = {
  	this.target = this.instanceNearest(true, true)
  }
  
	def onSelection() = {}
	def unlock(typeOf: String, str: String) = {}
	
	def attack() = {
		sndShoot.play(0.5f)
		var i = new Snowball7(this)
		World.projectiles += i		
	}
	
}
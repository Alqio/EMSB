package com.mygdx.instances

import com.mygdx.emsb.global
import com.mygdx.emsb.Button
import com.mygdx.emsb.Snowball1
import com.mygdx.emsb.World

class Wall() extends Building(){
	maxHp 			= 120
	hp					= maxHp
  dmg         = 0
  range       = 3
  attackSpeed = 0
  sprite      = global.sprites("wall")
  name				= "Wall"

  
	def onSelection() = {}
	def unlock(typeOf: String, str: String) = {}
	def attack() = {}
	
}

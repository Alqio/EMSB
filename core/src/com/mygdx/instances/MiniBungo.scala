package com.mygdx.instances
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite

import com.mygdx.emsb.global
import com.mygdx.emsb.World

class MiniBungo() extends EnemyUnit() {
  
	maxHp      = 1   * global.enemyLevel
	hp         = maxHp
  spd        = 2.5
  realSpdX   = spd
  dmg        = 0.3 * global.enemyLevel
  range      = 4
  name       = "Mini bungo"
  goldGain   = 1
  
  //Normal enemies can't load the sprite from global.sprites because then the sprite couldn't be flipped depending on the direction.
  sprite   = new Sprite(new Texture("images/miniBungo.png"))
	deathSound = Some(global.sounds("bungoDeath"))
	
	override def setTarget() = {
		val j = World.instances.filter(x => x.isInstanceOf[MainHouse])
		if (j.size != 0)
			target = Some(j.last)
		else 
			target = None
	}
	override def move() = {
  	this.coords.x += this.realSpdX * suunta
  	true
	}
	
}
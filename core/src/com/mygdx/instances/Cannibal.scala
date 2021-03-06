package com.mygdx.instances
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import Methods.randomRange
import com.mygdx.emsb.global
import com.mygdx.emsb.World
import com.mygdx.emsb.Bone

class Cannibal() extends EnemyUnit(){
  
	maxHp       = 4 * global.enemyLevel
	hp          = maxHp
  spd         = 1.1
  realSpdX    = spd
  dmg         = 7 * global.enemyLevel
  attackSpeed = (20 * randomRange(0.8,1.2)).toInt
  range       = (200 * randomRange(0.8,1.2)).toInt
  name        = "Cannibal"
  goldGain    = 5
  scoreGain   = 3 
  var canThrow = true
  
  //Normal enemies can't load the sprite from global.sprites because then the sprite couldn't be flipped depending on the direction.
  sprite   = new Sprite(new Texture("images/cannibal.png"))
	//var sprite2 = new Sprite(new Texture("cannibal2.png"))
	deathSound = Some(global.sounds("enemyDeath"))
	
	override def attack() = {
		if (canThrow) {
	  	var i = new Bone(this)
	  	World.projectiles += i
	  	canThrow = false
	  	dmg = 3
	  	range = 15
	  	attackSpeed = 45
	  	//this.sprite.getTexture().dispose()
	  	//this.sprite = sprite2
		} else {
			target.get.takeDmg(this.dmg)
		}
  }

}
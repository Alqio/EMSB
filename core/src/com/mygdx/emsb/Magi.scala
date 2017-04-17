package com.mygdx.emsb
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import Methods.randomRange

class Magi() extends EnemyUnit() {
  
	maxHp      = 3
	hp         = maxHp
  spd        = 1.3
  realSpdX   = spd
  dmg        = 3
  attackSpeed = 20
  range      = (200 * randomRange(0.8,1.2)).toInt
  name       = "Magi"
  goldGain   = 15
  scoreGain  = 5
  
  //Normal enemies can't load the sprite from global.sprites because then the sprite couldn't be flipped depending on the direction.
  sprite   = new Sprite(new Texture("magi.png"))
	deathSound = Some(global.sounds("enemyDeath"))
	//global.sprites("vihuy")
	
	override def attack() = {
  	var i = new Fireball(this)
  	World.projectiles += i
  }
}
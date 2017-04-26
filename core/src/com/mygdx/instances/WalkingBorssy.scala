package com.mygdx.instances
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.mygdx.emsb.global

class WalkingBorssy() extends EnemyUnit() {
  
	maxHp      = 45 * global.enemyLevel 
	hp         = maxHp
  spd        = 1
  realSpdX   = spd
  dmg        = 2 * global.enemyLevel
  range      = 35
  name       = "Walking Börssy"
  goldGain   = 2
  attackSpeed = 60
  
  //Normal enemies can't load the sprite from global.sprites because then the sprite couldn't be flipped depending on the direction.
  sprite   = new Sprite(new Texture("images/walkingBorssy.png"))
	deathSound = Some(global.sounds("saksDeath"))
	//global.sprites("vihuy")

}
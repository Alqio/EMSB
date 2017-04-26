package com.mygdx.instances
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.mygdx.emsb.global

class Vihuy() extends EnemyUnit() {
  
	maxHp      = 0.6 * global.enemyLevel 
	hp         = maxHp
  spd        = 1
  realSpdX   = spd
  dmg        = 1 * global.enemyLevel
  range      = 20
  name       = "Vihuy"
  goldGain   = 2
  
  //Normal enemies can't load the sprite from global.sprites because then the sprite couldn't be flipped depending on the direction.
  sprite   = new Sprite(new Texture("images/vihuy.png"))
	deathSound = Some(global.sounds("saksDeath"))
	//global.sprites("vihuy")

}
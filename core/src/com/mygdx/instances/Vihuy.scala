package com.mygdx.instances
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.mygdx.emsb.global

class Vihuy() extends EnemyUnit() {
  
	maxHp      = 1
	hp         = maxHp
  spd        = 1
  realSpdX   = spd
  dmg        = 1.0
  range      = 20
  name       = "Vihuy"
  goldGain   = 2
  
  //Normal enemies can't load the sprite from global.sprites because then the sprite couldn't be flipped depending on the direction.
  sprite   = new Sprite(new Texture("vihuy.png"))
	deathSound = Some(global.sounds("saksDeath"))
	//global.sprites("vihuy")

}
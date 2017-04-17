package com.mygdx.emsb
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite

class Saks() extends EnemyUnit() {
  
	maxHp      = 5
	hp         = maxHp
  spd        = 0.6
  realSpdX   = spd
  dmg        = 1.8
  range      = 20
  name       = "Saks"
  goldGain   = 5
  scoreGain  = 2
  
  //Normal enemies can't load the sprite from global.sprites because then the sprite couldn't be flipped depending on the direction.
  sprite   = new Sprite(new Texture("saks.png"))
	deathSound = Some(global.sounds("saksDeath"))
	//global.sprites("vihuy")

}
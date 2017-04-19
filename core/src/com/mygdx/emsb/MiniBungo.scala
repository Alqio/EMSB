package com.mygdx.emsb
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite

class MiniBungo() extends EnemyUnit() {
  
	maxHp      = 1
	hp         = maxHp
  spd        = 2.5
  realSpdX   = spd
  dmg        = 1.0
  range      = 20
  name       = "Mini bungo"
  goldGain   = 1
  
  //Normal enemies can't load the sprite from global.sprites because then the sprite couldn't be flipped depending on the direction.
  sprite   = new Sprite(new Texture("miniBungo.png"))
	deathSound = Some(global.sounds("bungoDeath"))
	
	
}
package com.mygdx.emsb
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import Methods.randomRange

class Beafire() extends EnemyUnit() {
  
	maxHp       = 20
	hp          = maxHp
  spd         = 0.3
  realSpdX    = spd
  dmg         = 3
  attackSpeed = (60 * randomRange(0.8,1.2)).toInt
  range       = (20 * randomRange(0.8,1.2)).toInt
  name        = "Beafire"
  goldGain    = 25
  scoreGain   = 10
  
  //Normal enemies can't load the sprite from global.sprites because then the sprite couldn't be flipped depending on the direction.
  sprite   = new Sprite(new Texture("beafire.png"))
	deathSound = Some(global.sounds("saksDeath"))
}
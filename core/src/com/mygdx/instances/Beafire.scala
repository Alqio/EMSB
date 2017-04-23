package com.mygdx.instances
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import Methods.randomRange
import com.mygdx.emsb.global

class Beafire() extends EnemyUnit() {
  
	maxHp       = 16
	hp          = maxHp
  spd         = 0.6
  realSpdX    = spd
  dmg         = 3
  attackSpeed = (60 * randomRange(0.8,1.2)).toInt
  range       = 20
  name        = "Beafire"
  goldGain    = 15
  scoreGain   = 10
  
  //Normal enemies can't load the sprite from global.sprites because then the sprite couldn't be flipped depending on the direction.
  sprite   = new Sprite(new Texture("images/beafire.png"))
	deathSound = Some(global.sounds("saksDeath"))
}
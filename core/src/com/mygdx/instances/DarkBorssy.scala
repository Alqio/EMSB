package com.mygdx.instances
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.mygdx.emsb.global

class DarkBorssy() extends EnemyUnit() {
  
	maxHp      = 250 * global.enemyLevel 
	hp         = maxHp
  spd        = 1
  realSpdX   = spd
  dmg        = 3  * global.enemyLevel
  range      = 35
  name       = "Dark Börssy"
  goldGain   = 2
  attackSpeed = 40
  scoreGain  = 25
  //Normal enemies can't load the sprite from global.sprites because then the sprite couldn't be flipped depending on the direction.
  sprite   = new Sprite(new Texture("images/darkBorssy.png"))
	deathSound = Some(global.sounds("saksDeath"))

}
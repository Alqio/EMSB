package com.mygdx.instances
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.mygdx.emsb.global
import com.mygdx.emsb.World

class Saks() extends EnemyUnit() {
  
	maxHp      = 3   * global.enemyLevel
	hp         = maxHp
  spd        = 0.6
  realSpdX   = spd
  dmg        = 1.8 * global.enemyLevel
  range      = 20
  name       = "Saks"
  goldGain   = 5
  scoreGain  = 2
  
  //Normal enemies can't load the sprite from global.sprites because then the sprite couldn't be flipped depending on the direction.
  sprite   = new Sprite(new Texture("images/saks.png"))
	deathSound = Some(global.sounds("saksDeath"))
	//global.sprites("vihuy")

}
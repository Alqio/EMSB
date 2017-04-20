package com.mygdx.instances
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.mygdx.emsb.global


class Abaji() extends FriendlyUnit(){
  maxHp      = 8
	hp         = maxHp
  spd        = 0.6
  realSpdX   = spd
  dmg        = 0.5
  range      = 10
  name       = "Äbäji"
  attackSpeed = 120
  
  
  //Normal enemies can't load the sprite from global.sprites because then the sprite couldn't be flipped depending on the direction.
  sprite   = new Sprite(new Texture("images/abaji.png"))
	deathSound = Some(global.sounds("infantryDeath"))
	//global.sprites("vihuy")
	override def place_free(x: Int, y: Int) = true
}
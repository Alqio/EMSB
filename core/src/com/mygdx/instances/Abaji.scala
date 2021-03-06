package com.mygdx.instances
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.mygdx.emsb.global


class Abaji() extends FriendlyUnit(){
  maxHp      = 35
	hp         = maxHp
  spd        = 0.6
  realSpdX   = spd
  dmg        = 0.8
  range      = 5 + (this.sprite.getWidth()/2).toInt
  name       = "Äbäji"
  attackSpeed = 120
  
  
  //Normal enemies can't load the sprite from global.sprites because then the sprite couldn't be flipped depending on the direction.
  sprite   = new Sprite(new Texture("images/abaji.png"))
	deathSound = Some(global.sounds("infantryDeath"))

	override def place_free(x: Int, y: Int) = true
}
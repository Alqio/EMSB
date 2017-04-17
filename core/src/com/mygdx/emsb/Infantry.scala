package com.mygdx.emsb
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite

class Infantry() extends FriendlyUnit(){
  maxHp      = 1
	hp         = maxHp
  spd        = 1
  realSpdX   = spd
  dmg        = 0.3
  range      = 10
  name       = "Infantry"
  attackSpeed = 60
  
  //def setTarget() = this.target = instanceNearest()
  
  //Normal enemies can't load the sprite from global.sprites because then the sprite couldn't be flipped depending on the direction.
  sprite   = new Sprite(new Texture("infantry.png"))
	deathSound = Some(global.sounds("infantryDeath"))
	//global.sprites("vihuy")
	override def place_free(x: Int, y: Int) = true
}
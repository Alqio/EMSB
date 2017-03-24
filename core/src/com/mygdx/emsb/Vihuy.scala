package com.mygdx.emsb
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite

class Vihuy(ctrl: Controller) extends EnemyUnit(ctrl) {
  
	maxHp      = 10
	hp         = maxHp
  spd        = 1.0
  realSpdX   = spd
  dmg        = 1.0
  range      = 20
  name       = "Vihuy"

  sprite = global.sprites("vihuy")
  sprite.setSize(32f, 32f)

}
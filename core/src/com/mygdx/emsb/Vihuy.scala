package com.mygdx.emsb
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite

class Vihuy(ctrl: Controller) extends EnemyUnit(ctrl) {
  
	maxHp      = 50
	hp         = maxHp
  var spd    = 1.0
  realSpdX   = spd
  dmg        = 1.0
  range      = 20

  sprite = global.sprites("vihuy")
  sprite.setSize(32f, 32f)
  
  override def toString = "Vihuy at: " + this.coords.toString + " HP:" + hp + "/" + maxHp

}
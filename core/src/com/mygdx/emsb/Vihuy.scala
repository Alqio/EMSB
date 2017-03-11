package com.mygdx.emsb
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite

class Vihuy(ctrl: Controller) extends EnemyUnit(ctrl) {
  
	maxHp      = 153
	hp         = maxHp
  var spd    = 1.0
  realSpdX   = spd
  dmg        = 1.0
  range      = 20
  
  def attack() = {
  	if (this.target.isDefined && this.coords.distanceToPoint(this.target.get.coords) <= this.range) {
  	  target.get.takeDmg(this.dmg)
  	} else if (this.target.isEmpty){
  		this.alarms(0).time = -1
  	}
  }

  sprite = global.sprites("vihuy")
  sprite.setSize(32f, 32f)
  
  override def toString = "Vihuy at: " + this.coords.toString + " HP:" + hp + "/" + maxHp

}
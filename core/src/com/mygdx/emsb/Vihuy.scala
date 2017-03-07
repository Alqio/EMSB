package com.mygdx.emsb
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite

class Vihuy(ctrl: Controller) extends EnemyUnit(ctrl) {
  
	val maxHp = 212.0
	hp = maxHp
  var spd = 1.0
  realSpdX = spd
  var dmg = 1.0
  range = 20
  this.alarmActions(this.alarms(0)) = () => {
  	this.alarms(0).time += 60
  }
  this.alarmActions(this.alarms(1)) = () => {
  	this.alarms(0).time += 60
  }
  
  var sprite = new Sprite(new Texture("vihuy.png"))
  sprite.setSize(32f, 32f)
  
  override def toString = "Vihuy at: " + this.coords.toString + " with " + hp + "/" + maxHp + " hp, TARGET: " + this.target

}
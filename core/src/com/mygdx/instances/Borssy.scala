package com.mygdx.instances
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import Methods._
import com.mygdx.emsb.global
import com.mygdx.emsb.World 
import com.mygdx.emsb.Coords
import com.mygdx.emsb.FallingFireball


class Borssy() extends EnemyUnit() {
  
	maxHp      = 45  * global.enemyLevel
	hp         = maxHp
  spd        = 2.5
  realSpdX   = spd
  dmg        = 2.5   * global.enemyLevel
  range      = 240
  name       = "Börssy"
  attackSpeed = 10
  goldGain   = 4
  scoreGain  = 20
  flying		 = true
  var attacking = 5
  
  
  //Normal enemies can't load the sprite from global.sprites because then the sprite couldn't be flipped depending on the direction.
  sprite   = new Sprite(new Texture("images/borssy.png"))
	deathSound = Some(global.sounds("borssyDeath"))
	if (this.coords.x < this.target.get.position.x) suunta = 1 else suunta = -1
	
	override def canAttack: Boolean = this.target.get.hitArea.isInside(new Coords(this.attackPoint, this.position.y - range))	
	
	override def attack() = {
		if (attacking <= 0) {
			var i = new FallingFireball(this)
			World.projectiles += i
			attacking = 5
		}
  }	
	
	override def move() = {
		
		if ((suunta == 1 && this.coords.x > global.WIDTH + global.camera.camWidth) || (suunta == -1 && this.coords.x < 0 - global.camera.camWidth - 20))
			World.instances.remove(World.instances.indexOf(this))
			
		if (attacking > 0)
			attacking -= 1
  	this.coords.x += this.realSpdX * suunta
  	true
	}
	
}
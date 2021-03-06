package com.mygdx.instances
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import Methods._
import com.mygdx.emsb.global
import com.mygdx.emsb.World 
import com.mygdx.emsb.Coords
import com.mygdx.emsb.FallingFireball


class BorssyBungo() extends EnemyUnit() {
  
	maxHp      = 120  * global.enemyLevel
	hp         = maxHp
  spd        = 0.8
  realSpdX   = spd
  dmg        = 2.0 * global.enemyLevel
  range      = 240
  name       = "Börssy dropping Bungos"
  attackSpeed = 35
  goldGain   = 10
  scoreGain  = 22
  flying		 = true
  var attacking = 75
  
  
  //Normal enemies can't load the sprite from global.sprites because then the sprite couldn't be flipped depending on the direction.
  sprite   = new Sprite(new Texture("images/borssyBungo.png"))
	deathSound = Some(global.sounds("borssyDeath"))
	if (this.coords.x < this.target.get.position.x) suunta = 1 else suunta = -1
	
	override def canAttack: Boolean = this.target.get.hitArea.isInside(new Coords(this.attackPoint, this.position.y - range))	
	
	override def attack() = {
		if (attacking <= 0) {
			for (i <- 0 until irandomRange(2,6)) {
				var i = new Bungo()
				i.coords = new Coords(this.position.x + irandomRange(-15, 15), this.position.y + irandomRange(-10, 10))
				i.falling = true
				World.instances += i
			}
			attacking = 75
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
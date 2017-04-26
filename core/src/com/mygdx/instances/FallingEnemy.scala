package com.mygdx.instances
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import Methods._
import com.mygdx.emsb.global
import com.mygdx.emsb.World 
import com.mygdx.emsb.Coords



class FallingEnemy() extends EnemyUnit() {
  
	maxHp      = 2   * global.enemyLevel
	hp         = maxHp
  spd        = 1
  realSpdX   = spd
  dmg        = 1.0 * global.enemyLevel
  range      = 20
  name       = "Bungo"
  goldGain   = 4
  scoreGain  = 2
  var spawned = false
  var falling = false
  var fallingSpeed = 1.35
  
  //Normal enemies can't load the sprite from global.sprites because then the sprite couldn't be flipped depending on the direction.
  sprite   = if (choose(1,2) == 1) new Sprite(new Texture("images/liekkiOtus.png")) else new Sprite(new Texture("images/kieliOtus.png"))
	deathSound = Some(global.sounds("enemyDeath"))
	
	//These guys go straight for the main house
	override def setTarget() = {
		val j = World.instances.filter(x => x.isInstanceOf[MainHouse])
		if (j.size != 0)
			target = Some(j.last)
		else 
			target = None
	}
	
	override def place_free(x: Int, y: Int) = true
	
	
	override def move() = {
		if (!falling) {
  		this.coords.x += this.realSpdX * suunta
		} else {
			this.coords.y -= this.fallingSpeed
			this.fallingSpeed += 0.02
			
			if (this.coords.y <= global.spawnHeight) {
				falling = false
				this.coords.y = global.spawnHeight
			}
		}
  	true
	}
	
}
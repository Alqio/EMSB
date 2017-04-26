package com.mygdx.instances
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import Methods._
import com.mygdx.emsb.global
import com.mygdx.emsb.World 
import com.mygdx.emsb.Coords



class Bungo() extends EnemyUnit() {
  
	maxHp      = 2   * global.enemyLevel
	hp         = maxHp
  spd        = 3
  realSpdX   = spd
  dmg        = 4.0 * global.enemyLevel
  range      = 20
  name       = "Bungo"
  goldGain   = 8
  scoreGain  = 5
  var spawned = false
  var falling = false
  var fallingSpeed = 1.35
  
  //Normal enemies can't load the sprite from global.sprites because then the sprite couldn't be flipped depending on the direction.
  sprite   = new Sprite(new Texture("images/bungo.png"))
	deathSound = Some(global.sounds("bungoDeath"))
	
	//Bungos go straight for the main house
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
  	if (!falling & !spawned && util.Random.nextInt(300) < 2) {
  		for (i <- 0 until irandomRange(0, 5)) {
	  		val i = new MiniBungo()
	  		i.coords = new Coords(this.coords.x + irandomRange(-20,20), this.coords.y)
	  		spawned = true
	  		World.instances += i
  		}
  	}
  	true
	}
	
}
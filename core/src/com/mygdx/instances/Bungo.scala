package com.mygdx.instances
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import Methods._
import com.mygdx.emsb.global
import com.mygdx.emsb.World 
import com.mygdx.emsb.Coords



class Bungo() extends EnemyUnit() {
  
	maxHp      = 2
	hp         = maxHp
  spd        = 3
  realSpdX   = spd
  dmg        = 4.0
  range      = 20
  name       = "Bungo"
  goldGain   = 4
  var spawned = false
  var falling = false
  var fallingSpeed = 2.5
  
  //Normal enemies can't load the sprite from global.sprites because then the sprite couldn't be flipped depending on the direction.
  sprite   = new Sprite(new Texture("images/bungo.png"))
	deathSound = Some(global.sounds("bungoDeath"))
	
	override def setTarget() = {
		if (!falling) {
			val j = World.instances.filter(x => x.isInstanceOf[MainHouse])
			if (j.size != 0)
				target = Some(j.last)
			else 
				target = None
		} else {
			target = None
		}
	}
	
	override def move() = {
		if (!falling) {
  		this.coords.x += this.realSpdX * suunta
		} else {
			this.coords.y -= this.fallingSpeed
			this.fallingSpeed += 0.01
			println("jo")
			if (this.coords.y <= global.spawnHeight) {
				falling = false
			}
		}
  	if (!falling && !spawned && util.Random.nextInt(300) < 2) {
  		for (i <- 0 until irandomRange(0,5)) {
	  		val i = new MiniBungo()
	  		i.coords = new Coords(this.coords.x + irandomRange(-20,20), this.coords.y)
	  		spawned = true
	  		World.instances += i
  		}
  	}
  	true
	}
	
}
package com.mygdx.emsb
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import Methods._

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
  
  //Normal enemies can't load the sprite from global.sprites because then the sprite couldn't be flipped depending on the direction.
  sprite   = new Sprite(new Texture("bungo.png"))
	deathSound = Some(global.sounds("bungoDeath"))
	
	override def move() = {
  	this.coords.x += this.realSpdX * suunta
  	this.coords.y += (if (this.coords.y < this.target.get.coords.y) this.realSpdY else -1*this.realSpdY)
  	if (!spawned && util.Random.nextInt(600) < 2) {
  		for (i <- 0 until irandomRange(0,3)) {
	  		val i = new MiniBungo()
	  		i.coords = new Coords(this.coords.x + irandomRange(-20,20), this.coords.y)
	  		spawned = true
	  		World.instances += i
  		}
  	}
  	true
	}
	
}
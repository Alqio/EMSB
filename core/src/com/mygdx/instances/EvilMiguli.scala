package com.mygdx.instances
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import Methods._
import com.mygdx.emsb.global
import com.mygdx.emsb.World 
import com.mygdx.emsb.Coords
import com.mygdx.emsb.FallingFireballBig


class EvilMiguli() extends EnemyUnit() {
  
	maxHp      = math.pow(global.wave, 2.1) * 50 - 1000
	hp         = maxHp
  spd        = 2
  realSpdX   = spd
  dmg        = 2 * global.enemyLevel * global.enemyLevel
  range      = 220
  name       = "Evil Miguli"
  attackSpeed = 25
  goldGain   = 4
  flying		 = true
  scoreGain  = 100
  var attacking = 5
  val rand = util.Random
  var snd     = global.miguliSounds("miguli1")
  var index   = 1
  var hpReg   = 1.0
  alarms(3).time = irandomRange(120, 240)
  
  
  //Normal enemies can't load the sprite from global.sprites because then the sprite couldn't be flipped depending on the direction.
  sprite   = new Sprite(new Texture("images/evilMiguli.png"))
	deathSound = Some(global.sounds("miguliDeath"))
	
	if (this.coords.x < this.target.get.position.x) suunta = 1 else suunta = -1
	
	var changedDirection = false
	
	override def canAttack: Boolean = this.target.get.hitArea.isInside(new Coords(this.attackPoint, this.coords.y - range))	
	
	override def attack() = {

		var i = new FallingFireballBig(this)
		World.projectiles += i	
  }	

	override def die() = {
		if (this.deathSound.isDefined) deathSound.get.play(0.5f * global.volume)
		global.score += this.scoreGain
		global.gold += this.goldGain
		this.sprite.getTexture().dispose()
		if (global.ctrl.miguliMusic.isPlaying()) {
			global.ctrl.miguliMusic.stop()
		}
		if (!global.ctrl.backgroundMusic.isPlaying())
			global.ctrl.backgroundMusic.play()
		
		World.instances.remove(World.instances.indexOf(this))
	}	
	
	override def move() = {
		
		if ((suunta == 1 && this.coords.x > global.WIDTH + global.camera.camWidth) || (suunta == -1 && this.coords.x < 0 - global.camera.camWidth - 20)) {
			if (!changedDirection)
					changedDirection
					
			if (suunta == 1)
				suunta = -1
			else
				suunta = 1
		}
		if (this.alarms(3).time <= 0) {
			var newIndex = rand.nextInt(global.miguliSounds.values.size)
			while (newIndex == index) {
				newIndex = rand.nextInt(global.miguliSounds.values.size)
			}
			index = newIndex
			var list = global.miguliSounds.values.zipWithIndex
			snd = list.toList.filter(_._2 == index).last._1
			snd.play(0.9f)
			this.alarms(3).time += 320 + irandomRange(-40, 300)
		}
		
		if (attacking > 0)
			attacking -= 1
			
  	this.coords.x += this.realSpdX * suunta
  	
  	if (changedDirection) {
  		hpReg = hpReg * 0.95
  	}
  	
  	if (hp < maxHp)
  		hp += hpReg
  	
  	if (hp > maxHp)
  		hp = maxHp
  		
		if (attacking <= 0) {
			for (i <- 0 until irandomRange(1,2)) {
				var i = new FallingEnemy()
				i.coords = new Coords(this.position.x + irandomRange(-15, 15), this.position.y + irandomRange(-10, 10))
				i.falling = true
				World.instances += i
			}
			attacking = irandomRange(65, 300)
		}	  	
		true
	}
	
}
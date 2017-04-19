package com.mygdx.instances

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.audio._
import scala.collection.mutable.Map
import com.mygdx.emsb.Alarm
import com.mygdx.emsb.Area
import com.mygdx.emsb.Coords
import com.mygdx.emsb.Projectile
import com.mygdx.emsb.World
import com.mygdx.emsb.global


/**
 * @author alkiok1
 */
abstract class Instance() {

	//Coords class is mutable and changes when the coordinate changes
	var coords = new Coords(100, 480)

	var suunta = 1 //1 = oikea, -1 = vasen
	val solid = false
	var sprite: Sprite = global.sprites("vihuy")
	val healthBar: Sprite = global.sprites("healthBar")
	var target: Option[Instance] = this.instanceNearest()
	var projectile: Option[Projectile] = None
	var name = "sukka mehu"
	
  var spd: Double = 0
	var hitSound: Option[Sound] = None
	var deathSound: Option[Sound] = None

	// 60 = 1 second, the lower the better. 120 = 2 seconds for example and 10 = 1/6 second.
	var attackSpeed: Int = 60
	val side = "enemy"

	var dmg: Double = 1.0
	var range: Int = 45

	//maxHp can also change with upgrades etc.
	var maxHp: Double = 5
	var hp: Double = maxHp

	val alarms = Array.fill(4)(new Alarm(0, this))
	var alarmActions = Map[Alarm, () => Unit]()

	def placeholder() = {}

	for (i <- alarms) {
		this.alarmActions += i -> placeholder
	}
	//alarm 0 is for attacking
	//alarm 1 ice effect
	//alarm 2 poison

	/** Draw the instance */
	def draw(batch: SpriteBatch) = {
		val pos = new Vector3(this.coords.x.toFloat, this.coords.y.toFloat, 0)
		this.sprite.setPosition(pos.x - global.camera.coords.x.toFloat, pos.y)
		this.sprite.draw(batch)
		
		val hpRatio = this.hp / this.maxHp
		this.healthBar.setSize(this.sprite.getWidth() * hpRatio.toFloat, this.healthBar.getHeight())
		this.healthBar.setPosition(pos.x - global.camera.coords.x.toFloat, pos.y + this.sprite.getHeight())
		this.healthBar.draw(batch)
	}

	/**
	 * The attack method.
	 */
	def attack()

	/**
	 * Return the center position of the instance
	 */
	def position = new Coords(this.coords.x + this.sprite.getWidth() / 2, this.coords.y + this.sprite.getHeight() / 2)

	/**
	 * Check whether a projectile is inside this instance's hit area.
	 */
	def isHitBy(p: Projectile) = this.hitArea.isInside(p.coords)

	/**
	 * Hit area
	 */
	def hitArea = {
		new Area(new Coords(this.coords.x, this.coords.y), new Coords(this.coords.x + this.sprite.getWidth(), this.coords.y + this.sprite.getHeight()))
	}

	/**
	 * Check if a coordinate is inside this instance's collision box
	 * Note to self: The actual coordinates are on the bottom left corner
	 */
	def checkCollision(coords: Coords) = {
		this.hitArea.isInside(coords)
	}

	/**
	 * Returns the nearest instance (not self)
	 */
	def instanceNearest(onlyEnemy: Boolean = true): Option[Instance] = {

		val enemies = if (onlyEnemy) World.instances.filter(x => x.side != this.side).toVector else World.instances.toVector

		if (enemies.size < 1) {
			None

		} else {

			var dist = this.coords.distanceToPoint(enemies(0).coords)
			var nearest = enemies(0)

			for (ins <- enemies) {
				if (ins != this && this.coords.distanceToPoint(ins.coords) < dist) {
					nearest = ins
					dist = this.coords.distanceToPoint(nearest.coords)
				}
			}

			Some(nearest)
		}
	}

	/**
	 *  Execute alarms
	 */
	def execute(funktio: () => Unit) = {
		funktio()
	}

	/** Each instance's step event is called fps(60?) times a second. */
	def step()

	/**
	 * Take dmg from projectiles for example
	 */
	def takeDmg(p: Projectile) = {
		this.hp -= p.dmg
		if (hitSound.isDefined) {
			hitSound.get.play(0.2f)
		}
		if (this.hp <= 0) {
			die()
		}
		if (p.typeOf == "ice") {
			alarms(1).time = 60
		} else if (p.typeOf == "poison") {
			alarms(2).time = 60
		}
	}
	
	def takeDmg(dmg: Double) = {
		this.hp -= dmg
		if (this.hp <= 0) die()
	}

	def die() = {
		if (this.deathSound.isDefined) deathSound.get.play(0.5f)
		if (this.isInstanceOf[EnemyUnit]) {
			global.score += this.asInstanceOf[EnemyUnit].scoreGain
			global.gold += this.asInstanceOf[EnemyUnit].goldGain
			this.sprite.getTexture().dispose()
		
		} else if (this.isInstanceOf[MainHouse]) {
			global.death()
		}
		World.instances.remove(World.instances.indexOf(this))
	}

	override def toString = name + ": " + this.coords.toString + " HP: " + hp + "/" + maxHp + " dmg: " + this.dmg + ", AS: " + this.attackSpeed

}
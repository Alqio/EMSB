package com.mygdx.instances

import Methods._

import com.mygdx.emsb.Coords
import com.mygdx.emsb.World
import com.mygdx.emsb.global

/**
 * @author alkiok1
 */
abstract class Character() extends Instance() {

	//Includes directions
	var realSpdX: Double = 0
	var realSpdY: Double = spd

	//ks kuva http://files.1337upload.net/ohje-579ce0.png
	var attackPoint = this.coords.x

	var direction = 0 // 0 - 360
	
	//After freezing effect ends, set speed back to normal
	this.alarmActions(this.alarms(1)) = () => {
		this.realSpdX = spd
	}

	def setTarget() = this.target = this.instanceNearest()

	/**
	 * Try to attack the target or move if not in range yet
	 */
	def step() = {

		this.setTarget()

		if (this.alarms(1).time > 0) {
			this.realSpdX = spd * 0.5
		}
		if (this.alarms(2).time > 0) {
			this.hp -= global.poisonDamage
			if (this.hp <= 0)
				die()
		}

		if (target.isDefined) {

			if (!flying) {
				if (this.coords.x < this.target.get.position.x) suunta = 1 else suunta = -1
				this.attackPoint = this.position.x + suunta * range
			} else {
				this.attackPoint = this.position.x
			}

			if (suunta == 1 && direction != 0) {
				this.sprite.flip(true, false)
				direction = 0
			} else if (suunta == -1 && direction != 180) {
				this.sprite.flip(true, false)
				direction = 180
			}
			
			if (canAttack) {
				if (this.alarms(0).time <= 0) {
					this.alarms(0).time += attackSpeed
					attack()
				}
			} else if (!flying){
				this.move()
			}
			if (flying) {
				this.move()
			}

		}

	}

	def canAttack: Boolean = this.target.get.hitArea.isInside(new Coords(this.attackPoint, this.coords.y + 2))

	/**
	 * This method will be called only if
	 * 1) target exists
	 * 2) attackPoint is inside target's hitArea (= canAttack is true)
	 * 3) alarm(0) == -1
	 *
	 * alarm(0) will be set to attack speed and then this method will be called
	 */
	def attack() = {
		target.get.takeDmg(this.dmg)
	}

	/**
	 * Move the character if possible.
	 * Returns true if move was successfull, else false
	 */
	def move() = {
		
		if (place_free(this.position.x.toInt + (this.realSpdX.toInt + this.sprite.getWidth().toInt / 2) * suunta, this.position.y.toInt + this.realSpdY.toInt) && this.target.isDefined) {
			this.coords.x += this.realSpdX * suunta
			this.coords.y += (if (this.coords.y < this.target.get.coords.y) this.realSpdY else -1 * this.realSpdY)
			true
		} else {
			false
		}
	}

	/**
	 *  Checks if the place in coordinates (x,y) is free (it doesn't contain a SOLID object)
	 */
	def place_free(x: Int, y: Int): Boolean = {
		val newCoords = new Coords(x, y)

		if (World.instanceAt(newCoords).forall(!_.solid)) {
			true
		} else {
			false
		}
	}
}
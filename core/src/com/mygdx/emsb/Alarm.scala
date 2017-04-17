package com.mygdx.emsb

/**
 * @author alkiok1
 */
class Alarm (var time: Int, val actor: Instance) {
	
	/** Stops at -1 **/
	def move() = {
		if (this.time >= 0) {
			time -= 1
		}
		if (this.time == 0) {
			actor.execute(actor.alarmActions(this))
		}
		
	}
	override def toString = "Alarm time: " + this.time + ", creator: " + this.actor
}

class WaveAlarm (var time: Int) {
	
	/** Stops at -1 **/
	def move() = {
		if (this.time >= 0) {
			time -= 1
		}
	}	
}

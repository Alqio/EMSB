package com.mygdx.emsb

import com.mygdx.instances.Instance

/**
 * Alarm class represents time. When an alarm's time reaches 0, the alarm's action will be executed
 */
class Alarm (var time: Int, val actor: Instance) {
	
	/** Move the alarm's time forward. 
	 *  Stops at -1 */
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

/**
 * Very similiar to normal alarm, expect this class doesn't have an actor
 */
class WaveAlarm (var time: Int) {
	
	/** Move the alarm's time forward.
	 *  Stops at -1 */
	def move() = {
		if (this.time >= 0) {
			time -= 1
		}
	}	
}

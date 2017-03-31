package com.mygdx.emsb

/**
 * @author alkiok1
 */
abstract class Wave (val number: Int, val enemies: Map[String, Int], var duration: Int){
  
	val alarms = Array.fill(enemies.size)(new WaveAlarm(0))
	
}
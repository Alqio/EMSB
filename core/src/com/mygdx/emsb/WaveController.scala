package com.mygdx.emsb

import collection.mutable.Buffer
import Methods._
/**
 * @author alkiok1
 */
class WaveController {
  var wave = 0
  var enemyCount = 0
  var maxEnemyCount = 5 + wave * 5
  val rand = util.Random
  var waves = Buffer[Wave]()
  var finished = false
  var enemies = Array("sukka")
  
  waves += new Wave(1, Array("vihuy"), 60)
  waves += new Wave(2, Array("vihuy", "saks"), 60)
  
  val alarm = Array.fill(12)(new WaveAlarm(0))
  
  def startWave() = {
  	finished = false
  	wave += 1
  	enemies = waves(wave).enemies
  	maxEnemyCount = 5 + wave * 5
  }
  
  def spawn() = {
  	println("nyt spawnattiin")
  	val enemy: Instance = enemies(rand.nextInt(enemies.size)) match {
  		case "vihuy"  => new Vihuy()
  		case "saks" 	=> new Vihuy()
  		case _ 				=> new Vihuy()
  	}
  	enemy.coords = new Coords(choose(-30, global.WIDTH + 30), global.spawnHeight)
  	enemyCount += 1
  	World.instances += enemy
  }
  
  /**
   * Spawn enemies and move alarms
   */
  def step() = {
  	if (!finished) {
	  	alarm.foreach(_.move())
	  	
	  	if (alarm(0).time <= 0) {
	  		if (enemyCount < maxEnemyCount) {
	  			spawn()
	  			alarm(0).time = irandomRange(waves(wave).spawnSpeed, 60 + 5 * World.enemies.size)
	  		}
	  	}
	  	
	  	if (alarm(1).time <= 0) {
	  		if (enemyCount < maxEnemyCount) {
		  		for (i <- 0 until irandomRange(0, 3 + wave)) {
		  			spawn()
		  		}
	  			alarm(1).time = irandomRange(waves(wave).spawnSpeed, 240 + 5 * World.enemies.size)
	  		}
	  	}
	  	
	  	if (World.enemies.size == 0 && enemyCount >= maxEnemyCount) {
	  		finished = true
	  	}
  	}
  }
  
}

class Wave (val number: Int, val enemies: Array[String], var spawnSpeed: Int)
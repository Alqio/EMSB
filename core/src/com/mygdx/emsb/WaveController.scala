package com.mygdx.emsb

import collection.mutable.Buffer
import Methods._
import com.mygdx.instances.Vihuy
import com.mygdx.instances.Instance
import com.mygdx.instances.Bungo
import com.mygdx.instances.Cannibal
import com.mygdx.instances.Beafire
import com.mygdx.instances.Magi
import com.mygdx.instances.Saks
import com.mygdx.instances.Borssy
import com.mygdx.instances.BorssyBungo

/**
 * @author alkiok1
 */
class WaveController(val file: String = "") {
  var wave = 0
  var enemyCount = 0
  var maxEnemyCount = 15 + wave * 5
  val rand = util.Random
  var waves = Buffer[Wave]()
  var finished = true
  var enemies = Array("sukka")
  
  //If the waves are not loaded from file, use default waves.

  waves += new Wave(0, Array("-"), 10)
  waves += new Wave(1, Array("vihuy"), 30)
  waves += new Wave(2, Array("vihuy", "vihuy", "vihuy", "saks"), 60)
  waves += new Wave(3, Array("vihuy", "saks", "cannibal"), 40)
  waves += new Wave(4, Array("saks", "beafire", "magi"), 40)
  waves += new Wave(5, Array("vihuy", "saks", "cannibal", "beafire", "magi"), 60)
  waves += new Wave(6, Array("bungo"), 120)
  waves += new Wave(7, Array("beafire", "vihuy", "borssy"), 60)
  waves += new Wave(8, Array("borssyBungo", "cannibal", "vihuy", "magi"), 40)
  waves += new Wave(9, Array("borssyBungo", "saks", "vihuy", "magi", "borssy"), 20)
  waves += new Wave(10, Array("borssyBungo", "saks", "vihuy", "magi", "borssy", "cannibal", "beafire"), 20)
  
  
  if (file != "") {
  	val loader = new WaveLoader(file)
  	val loadedWaves = loader.getWaves()
  	if (loadedWaves.size > 1) {
  		waves = loadedWaves
  	}
  }
  println(waves.mkString("\n"))
  
  
  val alarm = Array.fill(12)(new WaveAlarm(0))
  
  /**
   * Start a new wave
   */
  def startWave() = {
  	finished = false
  	wave += 1
  	enemies = waves(wave).enemies
  	maxEnemyCount = 10 + wave * 5
  	enemyCount = 0
  	alarm(0).time = 60
  	alarm(1).time = math.max(300 - 10 * wave, 10)
  }
  
  /**
   * Spawn an random enemy unit at a random position. (~-30 or room width + ~30)
   */
  def spawn() = {
  	val enemy: Instance = enemies(rand.nextInt(enemies.size)) match {
  		case "vihuy"			 => new Vihuy()
  		case "saks" 			 => new Saks()
  		case "magi" 			 => new Magi()
  		case "cannibal"		 => new Cannibal()
  		case "beafire" 		 => new Beafire()
  		case "bungo"   		 => new Bungo()
  		case "borssy"  		 => new Borssy()
  		case "borssyBungo" => new BorssyBungo()
  		case _ 						 => new Vihuy()
  	}
  	if (!enemy.flying) {
  		enemy.coords = new Coords(choose(global.minX -30 + irandomRange(-30, 0), global.maxX + 30 + irandomRange(0, 30)), global.spawnHeight)
  	} else {
  		enemy.coords = new Coords(choose(global.minX -30 + irandomRange(-30, 0), global.maxX + 30 + irandomRange(0, 30)), global.spawnHeight + 250 + irandomRange(-20, 5))
  	}
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

class Wave (val number: Int, val enemies: Array[String], var spawnSpeed: Int) {
	
	override def toString = "Wave(" + number + "): " + enemies.mkString(", ") + " ... with spawnSpeed of " + spawnSpeed
}
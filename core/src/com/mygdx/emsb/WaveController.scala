package com.mygdx.emsb

import collection.mutable.Buffer
import Methods._

import com.mygdx.instances._

/**
 * WaveController handles waves and spawning enemies
 */
class WaveController(val file: String = "") {
  var wave = 0
  var enemyCount = 0
  var maxEnemyCount = 15 + wave * 5
  val rand = util.Random
  var waves = Buffer[Wave]()
  var finished = true
  var enemies = Array("sukka")
  var enemyLevelCounter = 0.03f
  var lastSpawn = -30
  //If the waves are not loaded from file, use default waves.

  waves += new Wave(0, Array("-"), 10)
  waves += new Wave(1, Array("vihuy"), 40)
  waves += new Wave(2, Array("vihuy", "vihuy", "vihuy", "saks"), 60)
  waves += new Wave(3, Array("vihuy", "saks", "cannibal"), 40)
  waves += new Wave(4, Array("saks", "beafire", "magi", "vihuy", "vihuy"), 70)
  waves += new Wave(5, Array("vihuy", "saks", "cannibal", "beafire","vihuy","saks"), 60)
  waves += new Wave(6, Array("bungo"), 120)
  waves += new Wave(7, Array("beafire", "vihuy", "flyingVihuy", "vihuy", "beafire", "saks"), 60)
  waves += new Wave(8, Array("flyingVihuy", "cannibal", "vihuy", "magi", "bungo"), 40)
  waves += new Wave(9, Array("flyingVihuy", "saks", "vihuy", "magi", "borssy", "bungo", "bungo"), 15)
  waves += new Wave(10, Array("borssyBungo", "saks", "vihuy", "magi", "borssy", "cannibal", "beafire", "bungo"), 10)
  waves += new Wave(11, Array("borssyBungo", "borssy"), 45)
  waves += new Wave(12, Array("magi", "borssy", "cannibal", "walkingBorssy"), 20)
  waves += new Wave(13, Array("bungo", "borssyBungo", "vihuy", "beafire"), 10)
  waves += new Wave(14, Array("borssyBungo", "saks", "cannibal", "beafire", "walkingBorssy"), 5)
  waves += new Wave(15, Array("borssyBungo", "saks", "vihuy", "magi", "borssy", "cannibal", "beafire", "bungo", "flyingVihuy", "darkBorssy"), 0)
  waves += new Wave(16, Array("borssy", "borssyBungo", "walkingBorssy", "darkBorssy"), 5)
  waves += new Wave(17, Array("borssyBungo", "saks", "cannibal", "magi", "bungo", "walkingBorssy", "darkBorssy"), 5)
  waves += new Wave(18, Array("borssyBungo", "saks", "beafire", "walkingBorssy", "beafire", "bungo", "flyingVihuy", "borssy", "magi", "vihuy"), 5)
  waves += new Wave(19, Array("borssyBungo", "saks", "cannibal", "beafire", "walkingBorssy", "magi", "bungo"), 5)
  waves += new Wave(20, Array("borssyBungo", "saks", "vihuy", "magi", "borssy", "cannibal", "beafire", "bungo", "flyingVihuy", "walkingBorssy", "vihuy", "darkBorssy"), 0)
  
  
  if (file != "") {
  	val loader = new WaveLoader(file)
  	val loadedWaves = loader.getWaves()
  	if (loadedWaves.size > 1) {
  		waves = loadedWaves
  	}
  }
  //println(waves.mkString("\n"))
  
  
  val alarm = Array.fill(3)(new WaveAlarm(0))
  
  /**
   * Start a new wave
   */
  def startWave() = {
  	finished = false
  	global.wave += 1
  	if (wave > 8) {
  		enemyLevelCounter += 0.02f
  	}
  	if (wave < waves.size - 1) {
  		wave += 1
  	} else {
  		enemyLevelCounter += 0.06f
  	}
  	global.enemyLevel += enemyLevelCounter
  	enemies = waves(wave).enemies
  	maxEnemyCount = 15 + wave * 2 * 5 + (if (global.wave >= 11) 5 * global.wave else 0)
  	enemyCount = 0
  	alarm(0).time = 60
  	alarm(1).time = math.max(300 - 10 * wave, 10)
  	if (wave >= 10 && global.wave % 5 == 0) {
  		val i = new EvilMiguli()
  		i.coords = new Coords(choose(global.minX -30 + irandomRange(-30, 0), global.maxX + 30 + irandomRange(0, 30)), global.spawnHeight + 250 + irandomRange(-20, 5))
  		World.instances += i
  		global.ctrl.miguli()
  	}
  	
  }
  
  /**
   * Spawn an random enemy unit at a random position. (~-30 or room width + ~30)
   */
  def spawn() = {
  	val enemy: Instance = enemies(rand.nextInt(enemies.size)).toLowerCase() match {
  		case "vihuy"			   => new Vihuy()
  		case "saks" 			   => new Saks()
  		case "magi" 			   => new Magi()
  		case "cannibal"		   => new Cannibal()
  		case "beafire" 		   => new Beafire()
  		case "bungo"   		   => new Bungo()
  		case "borssy"  		 	 => new Borssy()
  		case "borssybungo"   => new BorssyBungo()
  		case "darkborssy" 	 => new DarkBorssy()
  		case "walkingborssy" => new WalkingBorssy()
  		case "flyingvihuy"   => new FlyingVihuy()
  		case _ 						   => new Vihuy()
  	}
  	if (lastSpawn < 0)
  		lastSpawn = global.maxX + 30 + irandomRange(0, 30)
  	else
  		lastSpawn = global.minX -30 + irandomRange(-30, 0)
  	if (!enemy.flying) {
  		enemy.coords = new Coords(lastSpawn, global.spawnHeight)
  	} else {
  		enemy.coords = new Coords(lastSpawn, global.spawnHeight + 250 + irandomRange(-20, 5))
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
	  			alarm(0).time = irandomRange(waves(wave).spawnSpeed, math.max(10, 60 + 15 * World.enemies.size - 5*global.wave))
	  		}
	  	}
	  	
	  	if (alarm(1).time <= 0) {
	  		if (enemyCount < maxEnemyCount) {
		  		for (i <- 0 until irandomRange(0, 3 + wave)) {
		  			spawn()
		  		}
	  			alarm(1).time = irandomRange(waves(wave).spawnSpeed, math.max(10, 200 + 10 * World.enemies.size - 5*global.wave))
	  		}
	  	}
	  	
	  	if (World.enemies.size == 0 && enemyCount >= maxEnemyCount) {
	  		finished = true
	  	}
  	}
  }
  
}

/**
 * Wave has a number, spawn speed and a list of enemies
 */
class Wave (val number: Int, val enemies: Array[String], var spawnSpeed: Int) {
	
	override def toString = "Wave(" + number + "): " + enemies.mkString(", ") + " ... with spawnSpeed of " + spawnSpeed
}
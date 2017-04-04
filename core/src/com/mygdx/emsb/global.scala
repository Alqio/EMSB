package com.mygdx.emsb

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.Texture

import collection.mutable.LinkedHashMap
import collection.mutable.Map

object global{
  
  var buildingDmgMultiplier = 1.0
  var buildingDmgLevel      = 1
  var buildingHpMultiplier  = 1.0
  var buildingHpLevel       = 1
  var score                 = 0
  var gold                  = 0
  var playerName            = "Sukka Mehuttaja"
	val WIDTH                 = 1280
	val HEIGHT                = 720  
	
	/**
	 * Unlocked map represents unlocked upgrades.
	 * For example, if snowTower doesn't have any upgrades, it has only Array(1),
	 * and 1 representing the level of the upgrade (1 = initial level)
	 * 
	 * If it has unlocked for example fire tower (level 3), it is "snowTower" -> Array(1, 3)
	 * 
	 */
  
	val towerUnlocks = LinkedHashMap[String, Map[String, Any]](
		"Normal" -> Map[String, Any](
			"unlocked" -> true,
			"cost"     -> 0
		),
    "Ice"   -> Map[String, Any](
			"unlocked" -> true,
			"cost"     -> 10
		),
    "Fire"    -> Map[String, Any](
			"unlocked" -> false,
			"cost"     -> 20
		),
    "Poison" -> Map[String, Any](
			"unlocked" -> true,
			"cost"     -> 10
		)
  )
  
	
	
  /**
   * A list of all sprites in the game. They are all loaded when the game starts, and they can then be disposed of later. 
   */
  val sprites = Map[String, Sprite](
    "vihuy"           -> new Sprite(new Texture("vihuy.png")),
    "researchCenter"  -> new Sprite(new Texture("researchCenter.png")),
    "snowTower"       -> new Sprite(new Texture("snowTower.png")),
    "fireTower"       -> new Sprite(new Texture("fireTower.png")),
    "snowBall1"       -> new Sprite(new Texture("snowBall1.png")),
    "snowBall2"       -> new Sprite(new Texture("snowBall2.png"))
  )
  
}
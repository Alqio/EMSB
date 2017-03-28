package com.mygdx.emsb

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.Texture


object global{
  
  var buildingDmgMultiplier = 1.0
  var buildingHpMultiplier = 1.0
  var score = 0
  var gold = 0
  var playerName = "Sukka Mehuttaja"
	val WIDTH = 1280
	val HEIGHT = 720  
	
	/**
	 * Unlocked map represents unlocked upgrades.
	 * For example, if snowTower doesn't have any upgrades, it has only Array(1),
	 * and 1 representing the level of the upgrade (1 = initial level)
	 * 
	 * If it has unlocked for example fire tower (level 3), it is "snowTower" -> Array(1, 3)
	 * 
	 */
	var unlocked = Map("snowTower" -> Array(1), "researchCenter" -> Array(1))
  
	val unlocks = Map[String, Boolean](
    "Fire"   -> false,
    "Ice"    -> false,
    "Poison" -> false
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
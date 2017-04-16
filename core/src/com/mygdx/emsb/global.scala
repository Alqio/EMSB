package com.mygdx.emsb

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.Color


import collection.mutable.LinkedHashMap
import collection.mutable.Map

object global{
  
  var buildingDmgMultiplier = 1.2
  var buildingDmgLevel      = 1
  var buildingHpMultiplier  = 1.2
  var buildingHpLevel       = 1
  var score                 = 0
  var gold                  = 100
  var playerName            = "Sukka Mehuttaja"
	val WIDTH                 = 1280
	val HEIGHT                = 720  
	val font 									= new BitmapFont()
  var building: Option[String] = None
  var buildingSprite: Option[Sprite] = None
	font.setColor(Color.RED)
	
  /**
   * A list of all sprites in the game. They are all loaded when the game starts, and they can then be disposed of later. 
   */
  val sprites = Map[String, Sprite](
    "vihuy"          		 -> new Sprite(new Texture("vihuy.png")),
    "researchCenter"	   -> new Sprite(new Texture("researchCenter.png")),
    "mainHouse" 				 -> new Sprite(new Texture("mainHouse.png")),
    "snowTower"    		   -> new Sprite(new Texture("snowTower.png")),
    "fireTower"     	   -> new Sprite(new Texture("fireTower.png")),
    "snowBall1"     	   -> new Sprite(new Texture("snowBall1.png")),
    "snowBall2"    		   -> new Sprite(new Texture("snowBall2.png")),
    "squareButton" 	     -> new Sprite(new Texture("squareButtonEmpty.png")),
    "hpUp"  						 -> new Sprite(new Texture("hpUp.png")),
    "dmgUp"							 -> new Sprite(new Texture("dmgUp.png")),
    "snowTowerIcon"  		 -> new Sprite(new Texture("snowTowerIcon.png")),
    "researchCenterIcon" -> new Sprite(new Texture("researchCenterIcon.png"))
  )	
	
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
			"unlocked" -> true,
			"cost"     -> 20
		),
    "Poison" -> Map[String, Any](
			"unlocked" -> true,
			"cost"     -> 10
		)
  )
  val upgrades = LinkedHashMap[String, Map[String, Any]](
  	"HpUpgrade" -> Map[String, Any] (
  		"level" -> this.buildingHpLevel,
  		"cost"  -> 45,
  		"text"  -> "Upgrade the maximum HP of all buildings.",
  		"sprite"-> global.sprites("hpUp")
  	),
  	"DmgUpgrade" -> Map[String, Any] (
  		"level" -> this.buildingDmgLevel,
  		"cost"  -> 45,
  		"text"  -> "Upgrade the damage of all attack towers.",
  		"sprite"-> global.sprites("dmgUp")
  	)
  )
 
  val buildables = LinkedHashMap[String, Map[String, Any]] (
  	"snowTower" -> Map[String, Any] (
  		"cost"    -> 50,
  		"text" 		-> "An attack tower",
  		"sprite"  -> global.sprites("snowTowerIcon")
  	),
  	"researchCenter" -> Map[String, Any] (
  		"cost"    -> 75,
  		"text"    -> "A research center, that can upgrade your buildings",
  		"sprite"  -> global.sprites("researchCenterIcon")
  	)
  	
  )
  
  var unlocked = (Array(1,2,3,4) zip towerUnlocks.values.map(x => x("unlocked").asInstanceOf[Boolean])).toMap
  println(unlocked)  
  
}
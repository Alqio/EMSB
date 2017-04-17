package com.mygdx.emsb

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.audio._


import collection.mutable.LinkedHashMap
import collection.mutable.Map

object global {
  
  var buildingDmgMultiplier = 1.2
  var buildingDmgLevel      = 1
  var buildingHpMultiplier  = 1.2
  var buildingHpLevel       = 1
  var buildingASMultiplier  = 0.8
  var buildingASLevel  			= 1
  var buildingRepairSpeed   = 0.001
  var buildingRepairLevel	  = 1
  
  val spawnHeight						= 200
  val poisonDamage					= 0.02
  var score                 = 0
  var gold                  = 120
  var playerName            = "Sukka Mehuttaja"
	val WIDTH                 = 1280
	val HEIGHT                = 720  
  var building: Option[String] = None
  var buildingSprite: Option[Sprite] = None
  
  //Create fonts
	val generator: FreeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/04B_03__.TTF"))
	val parameter: FreeTypeFontParameter = new FreeTypeFontParameter()
	parameter.size = 24
	
  val font = generator.generateFont(parameter)
	generator.dispose()
	
	parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS
	
	
  /**
   * A list of all sprites in the game. They are all loaded when the game starts, and they can then be disposed of later. 
   */
  val sprites = Map[String, Sprite](
    "vihuy"          		 -> new Sprite(new Texture("vihuy.png")),
    "researchCenter"	   -> new Sprite(new Texture("researchCenter.png")),
    "mainHouse" 				 -> new Sprite(new Texture("mainHouse.png")),
    "snowTower"    		   -> new Sprite(new Texture("snowTower.png")),
    "fireTower"     	   -> new Sprite(new Texture("fireTower.png")),
    "iceTower"       	   -> new Sprite(new Texture("iceTower.png")),
    "poisonTower"    	   -> new Sprite(new Texture("poisonTower.png")),
    "snowBall1"     	   -> new Sprite(new Texture("testi.png")),
    "snowBall2"    		   -> new Sprite(new Texture("snowBall2.png")),
    "squareButton" 	     -> new Sprite(new Texture("squareButtonEmpty.png")),
    "hpUp"  						 -> new Sprite(new Texture("hpUp.png")),
    "dmgUp"							 -> new Sprite(new Texture("dmgUp.png")),
    "snowTowerIcon"  		 -> new Sprite(new Texture("snowTowerIcon.png")),
    "iceTowerIcon"  		 -> new Sprite(new Texture("iceTowerIcon.png")),
    "poisonTowerIcon"  	 -> new Sprite(new Texture("poisonTowerIcon.png")),
    "fireTowerIcon"  		 -> new Sprite(new Texture("fireTowerIcon.png")),
    "researchCenterIcon" -> new Sprite(new Texture("researchCenterIcon.png")),
    "asUp" 							 -> new Sprite(new Texture("attackSpeedIcon.png")),
    "healthBar" 				 -> new Sprite(new Texture("healthBar.png")),
    "barracks" 					 -> new Sprite(new Texture("barracks.png")),
    "infantryIcon"       -> new Sprite(new Texture("infantryIcon.png")),
    "barracksIcon"       -> new Sprite(new Texture("barracksIcon.png"))
  )	
  val musics = Map[String, Music](
  	"background" -> Gdx.audio.newMusic(Gdx.files.internal("sounds/sndBg.mp3"))
  )
  
  val sounds = Map[String, Sound](
  	"enemyDeath" -> Gdx.audio.newSound(Gdx.files.internal("sounds/sndHit.wav")),
  	"towerShoot" -> Gdx.audio.newSound(Gdx.files.internal("sounds/sndShoot.wav")),
  	"saksDeath"  -> Gdx.audio.newSound(Gdx.files.internal("sounds/sndSaksDeath.wav")),
  	"infantryDeath" -> Gdx.audio.newSound(Gdx.files.internal("sounds/sndInfantryDeath.wav"))
  )
	
	/**
	 * Unlocked map represents unlocked upgrades.
	 * For example, if snowTower doesn't have any upgrades, it has only Array(1),
	 * and 1 representing the level of the upgrade (1 = initial level)
	 * 
	 * If it has unlocked for example fire tower (level 3), it is "snowTower" -> Array(1, 3)
	 * 
	 */
	val unlocks = LinkedHashMap[String, Map[String, Any]](
		"Normal" -> Map[String, Any](
			"unlocked" -> true,
			"cost"     -> 0,
			"text"     -> "Normal snow tower",
			"sprite"   -> global.sprites("snowTowerIcon"),
			"buildCost"-> 0
		),
    "Ice"   -> Map[String, Any](
			"unlocked"  -> false,
			"cost"      -> 200,
			"text"  	  -> "An ice tower, that slows enemies",
			"sprite"    -> global.sprites("iceTowerIcon"),
			"buildCost" -> 60
		),
    "Fire"    -> Map[String, Any](
			"unlocked"  -> false,
			"cost"      -> 200,
			"text"		  -> "A fire tower that uses fire projectiles to deal extra damage",
			"sprite"    -> global.sprites("fireTowerIcon"),
			"buildCost" -> 60
		),
    "Poison" -> Map[String, Any](
			"unlocked"  -> false,
			"cost"      -> 200,
			"text" 		  -> "A poison tower that deals damage over time",
			"sprite"    -> global.sprites("poisonTowerIcon"),
			"buildCost" -> 60
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
  	),
  	"ASUpgrade" -> Map[String, Any] (
  		"level" -> this.buildingASLevel,
  		"cost"  -> 50,
  		"text"  -> "Upgrade the attack speed of all attack towers.",
  		"sprite"-> global.sprites("asUp")
  	),
  	"Infantry" -> Map[String, Any] (
  		"cost"  -> 20,
  		"text"  -> "Summon three (3) friendly infantry units.",
  		"sprite" -> global.sprites("infantryIcon")
  	)
  )
 
  val buildables = LinkedHashMap[String, Map[String, Any]] (
  	"snowTower" -> Map[String, Any] (
  		"cost"    -> 15,
  		"text" 		-> "An attack tower",
  		"sprite"  -> global.sprites("snowTowerIcon")
  	),
  	"researchCenter" -> Map[String, Any] (
  		"cost"    -> 25,
  		"text"    -> "A research center, that can upgrade your buildings",
  		"sprite"  -> global.sprites("researchCenterIcon")
  	),
  	"barracks" -> Map[String, Any] (
  		"cost"    -> 25,
  		"text"    -> "A barracks that can produce friendly units.",
  		"sprite"  -> global.sprites("barracksIcon")
  	)
  	
  )
  def updateUnlocked() = {
  	unlocked = (Array(0,1,2,3) zip unlocks.values.map(x => x("unlocked").asInstanceOf[Boolean])).toMap
  }
  
  var unlocked = (Array(0,1,2,3) zip unlocks.values.map(x => x("unlocked").asInstanceOf[Boolean])).toMap
  println(unlocked)  
  
  /**
   * Draw a text with an outline
	 * @param str the text
	 * @param x the x location
	 * @param y the y location
	 * @param width the width of the outline
	 * @param c color of the text
	 * @param font the font that will be used in the draw
	 * @param the batch that will be used
	 */
	def drawOutline(str: String, x: Int, y: Int, width: Int, c: Color, font: BitmapFont, batch: SpriteBatch): Unit = {
		font.setColor(Color.BLACK)
		font.draw(batch, str, x - width, y - width)
		font.draw(batch, str, x + width, y - width)
		font.draw(batch, str, x - width, y + width)
		font.draw(batch, str, x + width, y + width)
		font.draw(batch, str, x, y + width)
		font.draw(batch, str, x, y - width)
		font.draw(batch, str, x - width, y)
		font.draw(batch, str, x + width, y)
		font.setColor(c)
		font.draw(batch, str, x, y)

	}
  
}
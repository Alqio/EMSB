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
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.mygdx.instances.MainHouse

import collection.mutable.LinkedHashMap
import collection.mutable.Map

object global {
  
  var buildingDmgMultiplier = 1.2
  var buildingDmgLevel      = 0
  var buildingHpMultiplier  = 1.2
  var buildingHpLevel       = 0
  var buildingASMultiplier  = 0.8
  var buildingASLevel  			= 0
  var buildingRepairSpeed   = 0.001
  var buildingRepairLevel	  = 0
  
  val spawnHeight						= 200
  val poisonDamage					= 0.04
  var score                 = 0
  var gold                  = 125
  var playerName            = "Sukka Mehuttaja"
	val WIDTH                 = 1280
	val HEIGHT                = 720  
	val minX									= -800
	val maxX									= WIDTH + 800
  var building: Option[String] = None
  var buildingSprite: Option[Sprite] = None
  
  var ctrl: Controller			= null
  var state: State 					= null
  var camera: Camera 				= null
  var mouseX								= 0
  var mouseY								= 0
  var mouseViewX						= 0
  var mouseViewY						= 0
  //Volume is either 1f or 0f, 0f meaning the game is muted
  var volume								= 1f
  
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
    "vihuy"          		 -> new Sprite(new Texture("images/vihuy.png")),
    "researchCenter"	   -> new Sprite(new Texture("images/researchCenter.png")),
    "mainHouse" 				 -> new Sprite(new Texture("images/mainHouse.png")),
    "snowTower"    		   -> new Sprite(new Texture("images/snowTower.png")),
    "fireTower"     	   -> new Sprite(new Texture("images/fireTower.png")),
    "iceTower"       	   -> new Sprite(new Texture("images/iceTower.png")),
    "poisonTower"    	   -> new Sprite(new Texture("images/poisonTower.png")),
    "snowBall1"     	   -> new Sprite(new Texture("images/testi.png")),
    "snowBall2"    		   -> new Sprite(new Texture("images/snowBall2.png")),
    "squareButton" 	     -> new Sprite(new Texture("images/squareButtonEmpty.png")),
    "hpUp"  						 -> new Sprite(new Texture("images/hpUp.png")),
    "dmgUp"							 -> new Sprite(new Texture("images/dmgUp.png")),
    "snowTowerIcon"  		 -> new Sprite(new Texture("images/snowTowerIcon.png")),
    "iceTowerIcon"  		 -> new Sprite(new Texture("images/iceTowerIcon.png")),
    "poisonTowerIcon"  	 -> new Sprite(new Texture("images/poisonTowerIcon.png")),
    "fireTowerIcon"  		 -> new Sprite(new Texture("images/fireTowerIcon.png")),
    "researchCenterIcon" -> new Sprite(new Texture("images/researchCenterIcon.png")),
    "asUp" 							 -> new Sprite(new Texture("images/attackSpeedIcon.png")),
    "healthBar" 				 -> new Sprite(new Texture("images/healthBar.png")),
    "healthBarOutline"	 -> new Sprite(new Texture("images/healthBarOutline.png")),
    "barracks" 					 -> new Sprite(new Texture("images/barracks.png")),
    "infantryIcon"       -> new Sprite(new Texture("images/infantryIcon.png")),
    "barracksIcon"       -> new Sprite(new Texture("images/barracksIcon.png")),
    "wallIcon"       		 -> new Sprite(new Texture("images/wallIcon.png")),
    "wall"    					 -> new Sprite(new Texture("images/wall.png")),
    "menuButton" 				 -> new Sprite(new Texture("images/menuButton.png")),
    "menuButtonHover"    -> new Sprite(new Texture("images/menuButtonHover.png")),
    "logo" 							 -> new Sprite(new Texture("images/logo.png")),
    "abajiIcon" 				 -> new Sprite(new Texture("images/abajiIcon.png")),
    "antiAir" 					 -> new Sprite(new Texture("images/antiAir.png")),
    "antiAirIcon"				 -> new Sprite(new Texture("images/antiAirIcon.png"))
   )	
  val musics = Map[String, Music](
  	"background" -> Gdx.audio.newMusic(Gdx.files.internal("sounds/sndBg.mp3")),
  	"menu"       -> Gdx.audio.newMusic(Gdx.files.internal("sounds/sndMenu.wav"))
  )
  
  val sounds = Map[String, Sound](
  	"enemyDeath"    -> Gdx.audio.newSound(Gdx.files.internal("sounds/sndHit.wav")),
  	"towerShoot"    -> Gdx.audio.newSound(Gdx.files.internal("sounds/sndShoot.wav")),
  	"saksDeath"     -> Gdx.audio.newSound(Gdx.files.internal("sounds/sndSaksDeath.wav")),
  	"infantryDeath" -> Gdx.audio.newSound(Gdx.files.internal("sounds/sndInfantryDeath.wav")),
  	"bungoDeath"    -> Gdx.audio.newSound(Gdx.files.internal("sounds/sndBungoDeath.wav")),
  	"borssyDeath"   -> Gdx.audio.newSound(Gdx.files.internal("sounds/sndBorssyDeath.wav")),
  	"build"         -> Gdx.audio.newSound(Gdx.files.internal("sounds/sndBuild.wav")),
  	"antiAirShoot"  -> Gdx.audio.newSound(Gdx.files.internal("sounds/sndAntiAirShoot.wav")),
  	"death" 				-> Gdx.audio.newSound(Gdx.files.internal("sounds/sndDeath.wav"))
  )
	
	/**
	 * Unlocked map represents unlocked upgrades.
	 * For example, if snowTower doesn't have any upgrades, it has only Array(1),
	 * and 1 representing the level of the upgrade (1 = initial level)
	 * 
	 * If it has unlocked for example fire tower (level 3), it is "snowTower" -> Array(1, 3)
	 * 
	 */
	var unlocks = LinkedHashMap[String, Map[String, Any]](
		"Normal" -> Map[String, Any](
			"unlocked" -> true,
			"cost"     -> 0,
			"text"     -> "Normal snow tower",
			"sprite"   -> global.sprites("snowTowerIcon"),
			"buildCost"-> 0
		),
    "Ice"   -> Map[String, Any](
			"unlocked"  -> false,
			"cost"      -> 100,
			"text"  	  -> "An ice tower, that slows enemies",
			"sprite"    -> global.sprites("iceTowerIcon"),
			"buildCost" -> 60
		),
    "Fire"    -> Map[String, Any](
			"unlocked"  -> false,
			"cost"      -> 100,
			"text"		  -> "A fire tower that uses fire projectiles to deal extra damage",
			"sprite"    -> global.sprites("fireTowerIcon"),
			"buildCost" -> 60
		),
    "Poison" -> Map[String, Any](
			"unlocked"  -> false,
			"cost"      -> 100,
			"text" 		  -> "A poison tower that deals damage over time",
			"sprite"    -> global.sprites("poisonTowerIcon"),
			"buildCost" -> 60
		),
    "AntiAir" -> Map[String, Any](
			"unlocked"  -> false,
			"cost"      -> 75,
			"text" 		  -> "Unlock an anti-air tower",
			"sprite"    -> global.sprites("antiAirIcon"),
			"buildCost" -> 40
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
  	),
  	"Abaji" -> Map[String, Any] (
  		"cost"  -> 20,
  		"text"  -> "Summon one (1) Abaji to fight for you.",
  		"sprite" -> global.sprites("abajiIcon")
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
  	),
  	"wall" -> Map[String, Any] (
  		"cost"    -> 30,
  		"text"    -> "A wall that blocks enemies.",
  		"sprite"  -> global.sprites("wallIcon")
  	),
  	"antiAir" -> Map[String, Any] (
  		"cost"    -> 40,
  		"text"    -> "An anti-air tower",
  		"sprite"  -> global.sprites("antiAirIcon")
  	)
  	
  )
  
  /**
   * Update the unlocked list. (actually remake it)
   */
  def updateUnlocked() = {
  	unlocked = (Array(0,1,2,3, 4) zip unlocks.values.map(x => x("unlocked").asInstanceOf[Boolean])).toMap
  }
  
  var unlocked = (Array(0,1,2,3, 4) zip unlocks.values.map(x => x("unlocked").asInstanceOf[Boolean])).toMap
  
  
  
  /**
   * Reset the game stats (gold etc.)
   */
  def reset() = {
  	score 								= 0
  	gold  								= 120
	  buildingDmgLevel      = 0
	  buildingHpLevel       = 0
	  buildingASLevel  			= 0
	  buildingRepairLevel	  = 0
  	upgrades("HpUpgrade")("level") = buildingHpLevel
  	upgrades("ASUpgrade")("level") = buildingASLevel
  	upgrades("DmgUpgrade")("level") = buildingDmgLevel
  	updateUnlocked()
  	World.instances.clear()
  	World.buttons.clear()
  	World.projectiles.clear()
  	ctrl.init()

		unlocks.values.foreach(i => i("unlocked") = false)
  	
		if (ctrl.backgroundMusic.isPlaying()) {
			ctrl.backgroundMusic.stop()		
		}
		if (ctrl.menuMusic.isPlaying()) {
			ctrl.menuMusic.stop()		
		}  	
  	ctrl.menuMusic.play()
		val mainHouse = new MainHouse()
		mainHouse.coords = Coords(640 - 48, 200)
		World.instances += mainHouse  	
  }
  
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
  /**
   * Draw a text with an outline
	 * @param layout the GlyphLayout
	 * @param x the x location
	 * @param y the y location
	 * @param width the width of the outline
	 * @param font the font that will be used in the draw
	 * @param the batch that will be used
	 */  
	def drawOutline(layout: GlyphLayout, layoutBG: GlyphLayout, x: Int, y: Int, width: Int, font: BitmapFont, batch: SpriteBatch): Unit = {
		font.draw(batch, layoutBG, x - width, y - width)
		font.draw(batch, layoutBG, x + width, y - width)
		font.draw(batch, layoutBG, x - width, y + width)
		font.draw(batch, layoutBG, x + width, y + width)
		font.draw(batch, layoutBG, x, y + width)
		font.draw(batch, layoutBG, x, y - width)
		font.draw(batch, layoutBG, x - width, y)
		font.draw(batch, layoutBG, x + width, y)
		font.draw(batch, layout, x, y)
	}  
  /**
   * The death of the player
   */
  def death() = {
  	println("vituiks meni")
  	global.sounds("death").play(0.5f)
  	global.state = new DeathState(ctrl)
  	ctrl.selected = None
  }
  
}
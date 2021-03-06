package com.mygdx.emsb

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{SpriteBatch, Sprite, BitmapFont, GlyphLayout}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.FPSLogger
import com.badlogic.gdx.audio._
import com.badlogic.gdx.graphics.Pixmap


import collection.mutable.Buffer
import com.mygdx.instances._


/**
 * Controller is the main application class. It control the flow of the game, and for example
 * it renders the screen and has the main loop.
 */
class Controller extends ApplicationAdapter {
	//First a bunch of variables need to be initialised, most of them will be set in the create() method
	//They can't be created in the create() method, because otherwise their scope would be too small
	var batch: SpriteBatch            = _
	var font: BitmapFont              = _
	var selected: Option[Instance]    = None
	var shapeRenderer: ShapeRenderer  = _
	var fpsLogger: FPSLogger					= _
	var spawner: WaveController				= _
	
	var backgroundMusic: Music 				= _
	var menuMusic: Music 							= _
	var miguliMusic: Music 						= _
	var camera: Camera								= _
	
	var menuBg: Sprite								= _
	var logo: Sprite									= _
	var tausta: Sprite        				= _
	var mountains: Sprite 						= _
	var floor: Sprite									= _
	var cursor: Pixmap								= _
	
	var filePath: String 							= ""
	
	var textLayout1: GlyphLayout      = _
	var textLayout2: GlyphLayout      = _
	var fontCoordinates 							= (0f, 0f)
	
	/**
	 * All variables will be set in create() method. Create method initialises the game
	 */
	override def create() = {
		global.font.setColor(Color.WHITE)
		textLayout1 = new GlyphLayout(global.font, "Press 'SPACE' to continue");
		global.font.setColor(Color.BLACK)
		textLayout2 = new GlyphLayout(global.font, "Press 'SPACE' to continue")
		
		
		val fontX: Float = (640 - (textLayout1.width/2)).toFloat
		val fontY: Float = 440f
		
		
		fontCoordinates = (fontX, fontY)
		font 				  	= global.font
		shapeRenderer   = new ShapeRenderer()
		fpsLogger 	  	= new FPSLogger()
		spawner 		  	=	new WaveController(filePath)
		camera 			  	= new Camera()
		global.camera   = camera
		global.ctrl     = this
		global.state    = new MenuState(this)
		
		//Set cursor icon
		cursor 				  = new Pixmap(Gdx.files.internal("images/cursor.png"))
		Gdx.graphics.setCursor(Gdx.graphics.newCursor(cursor, 0,0))
		
		//Set background sprites
		tausta = new Sprite(new Texture(Gdx.files.internal("images/background.png")))
		tausta.setPosition(0,0)
		tausta.setSize(global.WIDTH, global.HEIGHT)
		
		menuBg = new Sprite(new Texture(Gdx.files.internal("images/bgMenu.png")))
		menuBg.setPosition(0, 0)
		menuBg.setSize(1280,720)
		
		mountains = new Sprite(new Texture(Gdx.files.internal("images/bgMountain.png")))
		mountains.setPosition(0,80)
		mountains.setSize(2560, 720)
		
		floor = new Sprite(new Texture(Gdx.files.internal("images/bgFloor.png")))
		floor.setPosition(0,-220)
		floor.setSize(1280,420)
		
		logo = global.sprites("logo")
		logo.setOriginCenter()
		logo.setPosition(0, 470)
		
		//Set and musics
		menuMusic = global.musics("menu")
		menuMusic.setLooping(true)
		menuMusic.setVolume(0.25f * global.volume)
		
		menuMusic.play()
		
		backgroundMusic = global.musics("background")
		backgroundMusic.setLooping(true)
		backgroundMusic.setVolume(0.5f * global.volume)
		
		miguliMusic    = global.musics("miguli")
		miguliMusic.setLooping(true)
		miguliMusic.setVolume(0.3f * global.volume)
		
		//Create a new sprite batch
		batch = new SpriteBatch()
		
		//Create the main house to the room
		val mainHouse = new MainHouse()
		mainHouse.coords = Coords(640 - 64, 200)
		World.instances += mainHouse
		
	}
	
	/**
	 * Load a new spawner (the wave values might be changed) and start musics
	 */
	def init() = {
		spawner =	new WaveController(filePath)
		
		if (menuMusic.isPlaying) {
			menuMusic.stop()
		}
		if (!backgroundMusic.isPlaying()) {
			backgroundMusic.play()		
		}
	}
	
	/**
	 * Draw the required things. The actual drawing is handled by current state
	 */
	def draw() = {
		global.state.draw(batch)
	}
	
	/**
	 * Draw shapes
	 */
	def drawShapes() = {
	  shapeRenderer.setColor(Color.RED);
		shapeRenderer.begin(ShapeType.Line)
		selected.foreach(i => shapeRenderer.rect(i.coords.x.toFloat - global.camera.x , i.coords.y.toFloat, i.sprite.getWidth(), i.sprite.getHeight()))
		selected.foreach(i => shapeRenderer.rect(i.coords.x.toFloat - 1 - global.camera.x, i.coords.y.toFloat - 1, i.sprite.getWidth() + 2, i.sprite.getHeight() + 2))
		shapeRenderer.end()		
	}
	
	
	/** 
	 *  The game loop
	 */
	override def render() = {
		//Clear the screen
	  Gdx.gl.glClearColor(0.5f, 0f, 0.3f, 1)
	  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
	  
	  if (selected.isEmpty) {
	  	global.building = None
	  }
	  
	  //Handle input and instances' events
	  spawner.step()
		global.state.step()
		camera.move()
		if (global.state.name == "FightState")
			handleFightInput()
		else if(global.state.name == "EscState") 
			handleEscInput()
	  handleInput()
		batch.begin()
		
		global.mouseX = Gdx.input.getX() + camera.coords.x.toInt
		global.mouseY = 720 - Gdx.input.getY()+ camera.coords.y.toInt
		
		global.mouseViewX = Gdx.input.getX()
		global.mouseViewY = 720 - Gdx.input.getY()
		
		draw()
		
		if (global.state.name == "FightState") {
			if (spawner.finished) {
				global.drawOutline(textLayout1, textLayout2, fontCoordinates._1.toInt, fontCoordinates._2.toInt, 1, font, batch)
			}
			
		}
		
		batch.end()
		if (global.state.name == "FightState")
			drawShapes()
	
	}
	
	/**
	 * What happens when miguli spawns?
	 */
	def miguli() = {
		if (menuMusic.isPlaying())
			menuMusic.pause()
		if (menuMusic.isPlaying())
			menuMusic.pause()
			
		miguliMusic.play()
	}
	
	/**
	 * Dispose of the resources that were used in the game.
	 */
	override def dispose() = {
		global.sprites.values.toVector.foreach(_.getTexture().dispose())
		global.sounds.values.toVector.foreach(_.dispose())
		global.musics.values.toVector.foreach(_.dispose())
		global.miguliSounds.values.toVector.foreach(_.dispose())
		global.font.dispose()
		cursor.dispose()
		tausta.getTexture().dispose()
		logo.getTexture().dispose()
		menuBg.getTexture().dispose()
		floor.getTexture().dispose()
		batch.dispose()
	} 
	
	/**
	 * Build a new building
	 */
	def buildNewBuilding() = {
		if (global.gold >= global.buildables(global.building.get)("cost").asInstanceOf[Int]) {
			
			val buildning = global.building match {
				case Some("snowTower")      => new SnowTower()
				case Some("researchCenter") => new ResearchCenter()
				case Some("barracks")       => new Barracks()
				case Some("wall")         	=> new Wall()
				case Some("antiAir")        => new AntiAir()
				case _ 											=> new SnowTower()
			}
			buildning.coords = Coords(global.mouseX, global.spawnHeight)
			World.instances += buildning
			global.gold -= global.buildables(global.building.get)("cost").asInstanceOf[Int]
			global.sounds("build").play(0.5f * global.volume)
		}
	}
	
	/**
	 * Handle normal input (such as muting)
	 */
	def handleInput() = {
		if (global.state.name == "DeathState") {
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				global.reset()
				global.state = new MenuState(this)
			}
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
			if (global.volume == 0f) 
				global.volume = 1f
			else
				global.volume = 0f
				
			menuMusic.setVolume(0.25f * global.volume)
			backgroundMusic.setVolume(0.5f * global.volume)
			miguliMusic.setVolume(0.3f * global.volume)
		}
	}
	
	/**
	 * Handle input when game is paused
	 */
	def handleEscInput() = {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.N)) {
			global.state = new FightState(this)
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.Y)) {
			global.reset()
			global.state = new MenuState(this)
		}
		
	}
	
	/**
	 * Handle input that is used in fight mode
	 */
	def handleFightInput() = {	
		
		//Start a new wave if the previous one was finished.
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			if (spawner.finished) {
				spawner.startWave()
			}
		}
		//Deselect if right click is pressed
		if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
			selected = None
			global.building = None
			World.buttons.clear()
		}
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			selected = None
			global.building = None
			global.state = new EscState(this)
		}
		
		if (Gdx.input.justTouched() && !Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
			
			/* If a button is pressed, don't change selection. 
			 * Otherwise, change selected to either None if an empty location is pressed or an instance at that location
			 */
			if (World.buttonAt(new Coords(Gdx.input.getX(), global.HEIGHT - Gdx.input.getY())).isEmpty) {
				
				//If not in build mode
				if (global.building.isEmpty) {
					var tempSelected = World.instanceAt(new Coords(global.mouseX, global.mouseY))
				
					//Delete all buttons
					if (tempSelected != selected && selected.isDefined && selected.get.isInstanceOf[Building]) {
						World.buttons.clear()
						global.building = None
					}
					
					selected = tempSelected
					
					if (selected.isDefined && !selected.get.isInstanceOf[Building]) {
						selected = None
						global.building = None
					}
						
					
					//println("Selected: " + selected)
					
					if (selected.isDefined) {
						selected.get.asInstanceOf[Building].onSelection()
					}
					 
				} else {
					if (World.areaIsFree(Area(Coords(global.mouseX, global.spawnHeight), 
							global.buildingSprite.get.getWidth().toInt, global.buildingSprite.get.getHeight().toInt))) {
						buildNewBuilding()
					} else {
						//play sound??
					}
					
				}
			}
		}
	}
}

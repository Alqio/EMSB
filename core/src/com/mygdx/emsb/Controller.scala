package com.mygdx.emsb

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.FPSLogger
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.audio._


import collection.mutable.Buffer
import com.mygdx.instances._

class Controller extends ApplicationAdapter {
	var batch: SpriteBatch            = _
	var font: BitmapFont              = null
	var selected: Option[Instance]    = None
	var shapeRenderer: ShapeRenderer  = null
	private var tausta: Sprite        = null
	private var mountains: Sprite 		= null
	private var floor: Sprite					= null
	var firstDraw: Boolean            = true
	private var stage: Stage					= null
	var squareButton: Texture 				= _
	var fpsLogger: FPSLogger					= null
	var spawner: WaveController				= null
	var backgroundMusic: Music 				= null
	var camera: Camera								= null
	
	override def create() = {
		font 					= global.font
		shapeRenderer = new ShapeRenderer()
		fpsLogger 		= new FPSLogger()
		spawner 			=	new WaveController()
		camera 				= new Camera()
		global.camera = camera
		
		tausta = new Sprite(new Texture(Gdx.files.internal("background.png")))
		tausta.setPosition(0,0)
		tausta.setSize(global.WIDTH, global.HEIGHT)
		mountains = new Sprite(new Texture(Gdx.files.internal("bgMountain.png")))
		mountains.setPosition(0,80)
		mountains.setSize(2560, 720)
		floor = new Sprite(new Texture(Gdx.files.internal("bgFloor.png")))
		floor.setPosition(0,-220)
		floor.setSize(1280,420)
		
		backgroundMusic = global.musics("background")
		backgroundMusic.setLooping(true)
		backgroundMusic.setVolume(0.5f)
		if (!backgroundMusic.isPlaying()) backgroundMusic.play()
		
		batch = new SpriteBatch()
		
		
		val mainHouse = new MainHouse()
		mainHouse.coords = Coords(640 - 32, 200)
		World.instances += mainHouse
		
		//Start the waves
		spawner.startWave()
		val yks = new Saks()
		yks.coords = new Coords(230, 200)
		//World.instances += yks
		
	}
	
	def draw() = {
	  tausta.draw(batch)
	  mountains.draw(batch)
	  floor.draw(batch)
	  
		World.instances.foreach(_.draw(batch))
		World.projectiles.foreach(_.draw(batch))
		World.buttons.foreach(_.draw(batch))
		
		
		
		//World.instances.foreach(println) 
		if (global.building.isDefined) {
			global.buildingSprite.get.setAlpha(0.5f)
			global.buildingSprite.get.setPosition(Gdx.input.getX(), 200)
	    global.buildingSprite.get.draw(batch)
			global.buildingSprite.get.setAlpha(1f)
		}
		
	}
	
	def drawShapes() = {
	  shapeRenderer.setColor(Color.RED);
		shapeRenderer.begin(ShapeType.Line)
		selected.foreach(i => shapeRenderer.rect(i.coords.x.toFloat, i.coords.y.toFloat, i.sprite.getWidth(), i.sprite.getHeight()))
		selected.foreach(i => shapeRenderer.rect(i.coords.x.toFloat - 1, i.coords.y.toFloat - 1, i.sprite.getWidth() + 2, i.sprite.getHeight() + 2))
		shapeRenderer.end()		
	}
	
	
	/** The game loop */
	override def render() = {
		/**
		 * Clear the screen
		 */
	  Gdx.gl.glClearColor(0.5f, 0f, 0.3f, 1)
	  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
	  
	  //fpsLogger.log()

	  if (selected.isEmpty) {
	  	global.building = None
	  }
	  
	  spawner.step()
		World.updateWorld()
		camera.move()
		handleInput()
	  
		batch.begin()
		
		global.mouseX = Gdx.input.getX() + camera.coords.x.toInt
		global.mouseY = 720 - Gdx.input.getY()+ camera.coords.y.toInt
		
		global.mouseViewX = Gdx.input.getX()
		global.mouseViewY = 720 - Gdx.input.getY()
		
		draw()
		
		val pos2 = new Vector3(20, global.HEIGHT - 20, 0)
		global.drawOutline("Score: " + global.score + "\nGold:   " + global.gold, pos2.x.toInt, pos2.y.toInt, 1, Color.RED, font, batch)
		
		//val pos3 = new Vector3(Gdx.input.getX(), 720 - Gdx.input.getY(), 0)
		val pos3 = new Vector3(global.mouseX, global.mouseY, 0)
	  val drawPos = new Vector3(global.mouseViewX, global.mouseViewY,0)
	  global.drawOutline("WorldX: " + pos3.x + "\nWorldY: " + pos3.y + "\nView X: " + drawPos.x + "\nView Y: " + drawPos.y, drawPos.x.toInt, drawPos.y.toInt, 1, Color.RED, font, batch)
		
	  
		if (spawner.finished) {
			val pos = new Vector3(520, 360, 0)
			global.drawOutline("Press 'SPACE' to continue", pos.x.toInt, pos.y.toInt, 1, Color.WHITE, font, batch)
		}
		
		batch.end()
		
		drawShapes()
	
	}
	
	/**
	 * Dispose of the resources that were used in the game.
	 */
	override def dispose() = {
		global.sprites.values.toVector.foreach(_.getTexture().dispose())
		global.sounds.values.toVector.foreach(_.dispose())
		global.musics.values.toVector.foreach(_.dispose())
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
				case _ 											=> new SnowTower()
			}
			buildning.coords = Coords(Gdx.input.getX(), global.spawnHeight)
			World.instances += buildning
			global.gold -= global.buildables(global.building.get)("cost").asInstanceOf[Int]
		}
	}
	
	
	def handleInput() = {
		
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
			var torni: Option[Building] = if (selected.isDefined && selected.get.isInstanceOf[SnowTower]) Some(selected.get.asInstanceOf[SnowTower]) else None
			
			if (torni.isDefined) {
				selected.get.asInstanceOf[SnowTower].upgrade(math.min(torni.get.level + 1, torni.get.maxLevel))
			}
		}
		/**
		 * Start a new wave if the previous one was finished.
		 */
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			if (spawner.finished) {
				spawner.startWave()
			}
		}
		
		if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
			selected = None
			global.building = None
			World.buttons.clear()
		}
		
		if (Gdx.input.justTouched() && !Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
			
			/* If a button is pressed, don't change selection. 
			 * Otherwise, change selected to either None if an empty location is pressed or an instance at that location
			 */
			if (World.buttonAt(new Coords(Gdx.input.getX(), global.HEIGHT - Gdx.input.getY())).isEmpty) {
								
				if (global.building.isEmpty) {
					var tempSelected = World.instanceAt(new Coords(Gdx.input.getX(), global.HEIGHT - Gdx.input.getY()))
				
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
						
					
					println("Selected: " + selected)
					
					if (selected.isDefined) {
						selected.get.asInstanceOf[Building].onSelection()
					}
					 
				} else {
					if (World.areaIsFree(Area(Coords(Gdx.input.getX(), global.spawnHeight), 
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


		/*
		stage = new Stage(new ScreenViewport())
		Gdx.input.setInputProcessor(stage)
		
		
		
		var button = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("squareButtonEmpty.png")))))
		button.setPosition(100f, 100f)
		
		button.addListener(new InputListener(){
			override def touchUp (event: InputEvent,  x: Float, y: Float, pointer: Int, button: Int) {
	    	println("press a button")
	   	}
	    override def touchDown (InputEvent event, float x, float y, int pointer, int button) {
	    	println("pressed a button")
	     	return true;
	    }
	  })
	  
		stage.addActor(button)
		
		
		
				val pos = new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0)
		drawOutline("x: " + pos.x + "\ny: " + (Gdx.graphics.getHeight() - 1 - pos.y.toInt), pos.x.toInt, Gdx.graphics.getHeight() - 1 - pos.y.toInt, 1,Color.RED, font, batch)
	
		
		
		*/


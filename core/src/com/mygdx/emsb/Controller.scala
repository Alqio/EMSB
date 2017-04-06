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



import collection.mutable.Buffer

class Controller extends ApplicationAdapter {
	var batch: SpriteBatch            = _
	var img: Texture                  = null
	var spr: Sprite                   = null
	var rot                           = 0
	var font: BitmapFont              = null
	var cam: OrthographicCamera       = null
	var rotationSpeed                 = 1f
	var selected: Option[Instance]    = None
	var shapeRenderer: ShapeRenderer  = null
	private var tausta: Sprite        = null
	var firstDraw: Boolean            = true
	private var stage: Stage					= null
	var skin: Skin										= null
	var squareButton: Texture 				= _
	
	override def create() = {
		
		font = new BitmapFont()
		font.setColor(Color.RED)
		
		shapeRenderer = new ShapeRenderer()
		
		//val generator: FreeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/04B_03__.TTF"))
		
		tausta = new Sprite(new Texture(Gdx.files.internal("bg1.png")))
		tausta.setPosition(0,0)
		tausta.setSize(global.WIDTH, global.HEIGHT)
		
		batch = new SpriteBatch()
		
		
		var yks = new Vihuy()
		var toka = new Vihuy()
		var torni = new SnowTower()
		var torniToka = new ResearchCenter()
		
		yks.coords = new Coords(120,200)
		torni.coords = new Coords(350, 200)
		torniToka.coords = new Coords(900, 200)
		toka.coords = new Coords(720, 200)
		
		
		World.instances += yks
		World.instances += toka
		World.instances += torni
		World.instances += torniToka
		
		
	}
	
	def draw() = {
	  tausta.draw(batch)
		World.instances.foreach(_.draw(batch))
		World.projectiles.foreach(_.draw(batch))
		World.buttons.foreach(_.draw(batch))
		
		//World.instances.foreach(println)
		
	}
	
	def drawShapes() = {
	  shapeRenderer.setColor(Color.RED);
		shapeRenderer.begin(ShapeType.Line)
		//selected.foreach(i => shapeRenderer.circle(i.position.x.toFloat, i.position.y.toFloat, i.range))
		selected.foreach(i => shapeRenderer.rect(i.coords.x.toFloat, i.coords.y.toFloat, i.sprite.getWidth(), i.sprite.getHeight()))
		selected.foreach(i => shapeRenderer.rect(i.coords.x.toFloat - 1, i.coords.y.toFloat - 1, i.sprite.getWidth() + 2, i.sprite.getHeight() + 2))
		shapeRenderer.end()		
	}
	
	
	/** The game loop */
	override def render() = {
	  Gdx.gl.glClearColor(0.5f, 0f, 0.3f, 1)
	  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
	  
	  //println(selected)
	  
	  if (selected.isDefined && selected.get.position.distanceToPoint(new Coords(Gdx.input.getX(), global.HEIGHT - Gdx.input.getY())) > 550) {
	  	World.buttons.clear()
	  	selected = None
	  }
	  
	  
		World.updateWorld()
		handleInput()
	  
	  
		batch.begin()
		
		draw()		
		val pos = new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0)
		drawOutline("x: " + pos.x + "\ny: " + (Gdx.graphics.getHeight() - 1 - pos.y.toInt), pos.x.toInt, Gdx.graphics.getHeight() - 1 - pos.y.toInt, 1,Color.RED, font, batch)
		
		val pos2 = new Vector3(20, global.HEIGHT - 20, 0)
		drawOutline("Score: " + global.score + "\nGold:   " + global.gold, pos2.x.toInt, pos2.y.toInt, 1, Color.RED, font, batch)
		
		batch.end()
		
		drawShapes()
	
	}
	
	override def dispose() = {
		batch.dispose()
	}
	
	
	def handleInput() = {
		
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
			var torni: Option[Building] = if (selected.isDefined && selected.get.isInstanceOf[SnowTower]) Some(selected.get.asInstanceOf[SnowTower]) else None
			
			if (torni.isDefined) {
				selected.get.asInstanceOf[SnowTower].upgrade(math.min(torni.get.level + 1, torni.get.maxLevel))
			}
		}
		
		if (Gdx.input.justTouched()) {
			
			/* If a button is pressed, don't change selection. 
			 * Otherwise, change selected to either None if an empty location is pressed or an instance at that location
			 */
			if (World.buttonAt(new Coords(Gdx.input.getX(), global.HEIGHT - Gdx.input.getY())).isEmpty) {
				
				var tempSelected = World.instanceAt(new Coords(Gdx.input.getX(), global.HEIGHT - Gdx.input.getY()))
				
				//Delete all buttons
				if (tempSelected != selected && selected.isDefined && selected.get.isInstanceOf[ResearchCenter]) {
					World.buttons.clear()
				}
				
				selected = tempSelected
				
				if (selected.isDefined && !selected.get.isInstanceOf[Building])
					selected = None
				
				println("Selected: " + selected)
				
				if (selected.isDefined && selected.get.isInstanceOf[ResearchCenter]) {
					var asCenter = selected.get.asInstanceOf[ResearchCenter]
					asCenter.onSelection()
				}
				
				
			}
		}
		
	}
	
	
	/**
	 * @param str String
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
		*/


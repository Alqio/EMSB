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

import collection.mutable.Buffer

class Controller extends ApplicationAdapter {
	var batch: SpriteBatch = null
	var img: Texture = null
	var spr: Sprite = null
	var rot = 0
	var font: BitmapFont = null
	val WIDTH = 1280
	val HEIGHT = 720
	var cam: OrthographicCamera = null
	var rotationSpeed = 1f
	private var tausta: Sprite = null

	override def create() = {

		font = new BitmapFont()
		font.setColor(Color.RED)
		
		//val generator: FreeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/04B_03__.TTF"))
		
		tausta = new Sprite(new Texture(Gdx.files.internal("bg1.png")))
		tausta.setPosition(0,0)
		tausta.setSize(WIDTH, HEIGHT)
		
		batch = new SpriteBatch()

		var yks = new Vihuy(this)
		var toka = new Vihuy(this)
		var torni = new SnowTower(this)
		yks.coords = new Coords(120,200)
		torni.coords = new Coords(350, 200)
		toka.coords = new Coords(720, 200)
		
		
		World.instances += yks
		World.instances += toka
		World.instances += torni
		
		
		val h: Float = Gdx.graphics.getHeight()
		val w: Float = Gdx.graphics.getWidth()
		
		
	}
	
	def draw() = {
	  tausta.draw(batch)
		World.instances.foreach(_.draw(batch))

		World.projectiles.foreach(x => if (x != null) x.sprite.setPosition(x.coords.x.toFloat, x.coords.y.toFloat))
		World.projectiles.foreach(x => if (x != null) x.sprite.draw(batch))	  
		
	}
	
	
	/** The game loop */
	override def render() = {
	  //println("x: " + Gdx.input.getX())
		//println("y: " + Gdx.input.getY())
	  Gdx.gl.glClearColor(0.5f, 0f, 0.3f, 1)
	  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
	  
		World.updateWorld()
		
	  //cam.update()
	  //batch.setProjectionMatrix(cam.combined)
	  
	  /** Clear the screen */

		batch.begin()
	  
		draw()
		
		val pos = new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0)
		//cam.unproject(pos)
		drawOutline("x: " + pos.x + "\ny: " + (pos.y), pos.x.toInt, Gdx.graphics.getHeight() - 1 - pos.y.toInt, 1,Color.RED, font, batch)
		//font.draw(batch, "x: " + pos.x + "\ny: " + (pos.y), pos.x.toInt,Gdx.graphics.getHeight() - 1 - pos.y.toInt)
		batch.end()
	}


	
	
	override def dispose() = {
		batch.dispose()
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
		//font.draw(batch, "Hello world", Gdx.input.getX(), HEIGHT - Gdx.input.getY());
		font.setColor(c)
		font.draw(batch, str, x, y)

	}
}

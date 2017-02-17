package com.mygdx.emsb;

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.Color

class Controller extends ApplicationAdapter {
	var batch: SpriteBatch = null
	var img: Texture = null
	var spr: Sprite = null
	var rot = 0
	var font: BitmapFont = null
	val HEIGHT = 720

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

	override def create() = {

		font = new BitmapFont()
		
		font.setColor(Color.RED)
		batch = new SpriteBatch()
		img = new Texture("badlogic.jpg")
		spr = new Sprite(img)
		spr.setRotation(45)
		spr.setPosition(10, 10)

		var yks = new Vihuy(this)
		var toka = new Vihuy(this)
		var torni = new SnowTower(this)
		torni.coords = new Coords(400, 200)
		toka.coords = new Coords(720, 200)

		World.instances += yks
		World.instances += toka
		World.instances += torni

	}

	/** The game loop **/
	override def render() = {
		rot += 1

		/** Execute the step event for all instances **/
		World.instances.foreach(_.step())
		/** Execute the step event for all projectiles **/
		World.projectiles.foreach(x => if (x != null) x.step())
		/** Move all alarms **/
		World.instances.foreach(_.alarms.foreach(_.move()))

		Gdx.gl.glClearColor(0.5f, 0.5f, 0.3f, 1)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

		spr.setRotation(rot)
		batch.begin()

		World.instances.foreach(x => x.sprite.setPosition(x.coords.x.toFloat, x.coords.y.toFloat))
		World.instances.foreach(_.sprite.draw(batch))

		World.projectiles.foreach(x => if (x != null) x.sprite.setPosition(x.coords.x.toFloat, x.coords.y.toFloat))
		World.projectiles.foreach(x => if (x != null) x.sprite.draw(batch))

		//spr.draw(batch)
		
		//font.draw(batch, "Hello world", Gdx.input.getX(), HEIGHT - Gdx.input.getY());
		drawOutline("jouu", Gdx.input.getX(), HEIGHT - Gdx.input.getY(), 1,Color.RED, font, batch)
		batch.end()
	}

	override def dispose() = {
		batch.dispose()
		img.dispose()
	}
}

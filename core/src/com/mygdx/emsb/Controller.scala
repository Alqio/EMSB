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
	val WIDTH = 100
	val HEIGHT = 100
	var cam: OrthographicCamera = null
	var rotationSpeed = 1f
	private var tausta: Sprite = null

	override def create() = {

		font = new BitmapFont()
		font.setColor(Color.RED)
		
		tausta = new Sprite(new Texture(Gdx.files.internal("testitausta.png")))
		tausta.setPosition(0,0)
		tausta.setSize(WIDTH, HEIGHT)
		
		batch = new SpriteBatch()

		var yks = new Vihuy(this)
		var toka = new Vihuy(this)
		var torni = new SnowTower(this)
		torni.coords = new Coords(50, 50)
		toka.coords = new Coords(720, 200)

		//World.instances += yks
		//World.instances += toka
		World.instances += torni

		val h: Float = Gdx.graphics.getHeight()
		val w: Float = Gdx.graphics.getWidth()
		
		cam = new OrthographicCamera(100, 100 * (h / w))
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0)
		cam.update()
		
	}

	def updateWorld() = {
		/** Execute the step event for all instances **/
		World.instances.toVector.foreach(_.step())
		
		/** Execute the step event for all projectiles **/
		World.projectiles.toVector.foreach(_.step())
		
		/** Move all alarms **/
		World.instances.foreach(_.alarms.foreach(_.move()))	  
	}
	
	def draw() = {
	  tausta.draw(batch)
		World.instances.foreach(_.draw(batch, cam))

		World.projectiles.foreach(x => if (x != null) x.sprite.setPosition(x.coords.x.toFloat, x.coords.y.toFloat))
		World.projectiles.foreach(x => if (x != null) x.sprite.draw(batch))	  
		
	}
	
	
	/** The game loop */
	override def render() = {
	  //println("x: " + Gdx.input.getX())
		//println("y: " + Gdx.input.getY())
		
		updateWorld()
		
	  handleInput()
	  cam.update()
	  batch.setProjectionMatrix(cam.combined)
	  
	  /** Clear the screen */
		//Gdx.gl.glClearColor(0.5f, 0f, 0.3f, 1)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

		batch.begin()
	  
		draw()
		
		val pos = new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0)
		//cam.unproject(pos)
		//drawOutline("x: " + pos.x + "\ny: " + (pos.y), pos.x.toInt, pos.y.toInt, 1,Color.RED, font, batch)
		font.draw(batch, "x: " + pos.x + "\ny: " + (pos.y), pos.x.toInt, pos.y.toInt)
		batch.end()
	}

	def handleInput() = {
	  if (Gdx.input.isKeyPressed(Input.Keys.A)) {
      cam.zoom += 0.02f;
      //If the A Key is pressed, add 0.02 to the Camera's Zoom
    }
    if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
      cam.zoom -= 0.02f;
        //If the Q Key is pressed, subtract 0.02 from the Camera's Zoom
    }
    if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
        cam.translate(-3, 0, 0);
        //If the LEFT Key is pressed, translate the camera -3 units in the X-Axis
    }
    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
        cam.translate(3, 0, 0);
        //If the RIGHT Key is pressed, translate the camera 3 units in the X-Axis
    }
    if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
        cam.translate(0, -3, 0);
        //If the DOWN Key is pressed, translate the camera -3 units in the Y-Axis
    }
    if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
        cam.translate(0, 3, 0);
        //If the UP Key is pressed, translate the camera 3 units in the Y-Axis
    }
    if (Gdx.input.isKeyPressed(Input.Keys.W)) {
        cam.rotate(-rotationSpeed, 0, 0, 1);
        //If the W Key is pressed, rotate the camera by -rotationSpeed around the Z-Axis
    }
    if (Gdx.input.isKeyPressed(Input.Keys.E)) {
        cam.rotate(rotationSpeed, 0, 0, 1);
        //If the E Key is pressed, rotate the camera by rotationSpeed around the Z-Axis
    }

    cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, 100/cam.viewportWidth);

    val effectiveViewportWidth: Float = cam.viewportWidth * cam.zoom;
    val effectiveViewportHeight: Float = cam.viewportHeight * cam.zoom;

    cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
    cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);
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

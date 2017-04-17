package com.mygdx.emsb

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
//import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils

class Camera {
	val WIDTH = 2560
	val HEIGHT = 720
	var movSpeed = 2

	def move() = {
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			World.instances.foreach(i => i.coords.x -= movSpeed)
			World.projectiles.foreach(_.coords.x -= movSpeed)
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			World.instances.foreach(i => i.coords.x += movSpeed)
			World.projectiles.foreach(_.coords.x += movSpeed)
		}
	}

}
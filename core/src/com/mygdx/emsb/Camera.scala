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
	var movSpeed = 4
	var coords = Coords(0,0)
	
	def move() = {
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			coords.x -= movSpeed
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			coords.x += movSpeed
		}
	}
	
	def x = this.coords.x.toFloat
	def y = this.coords.y.toFloat

}
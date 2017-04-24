package com.mygdx.emsb

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils


/**
 * Camera controls the area the player can see while in game
 */
class Camera {
	val camWidth = 1280
	val camHeight = 720
	var movSpeed = 8
	var coords = Coords(0,0)
	
	
	def move() = {
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && x > global.minX) {
			coords.x -= movSpeed
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && x < global.maxX - camWidth) {
			coords.x += movSpeed
		}
	}
	
	def x = this.coords.x.toFloat
	def y = this.coords.y.toFloat

}
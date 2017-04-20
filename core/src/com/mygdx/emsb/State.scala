package com.mygdx.emsb

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.Gdx

abstract class State(val name: String) {
	
  def draw()
  def step()
}

class FightState(val tausta: Sprite, val mountains: Sprite, val floor: Sprite, val batch: SpriteBatch) extends State("FightState") {
	
	def create() = {
		
	}
	
	def draw() = {
	  tausta.draw(batch)
	  mountains.draw(batch)
	  floor.draw(batch)	
		World.instances.foreach(x => if (x.coords.x >= global.camera.x - 20 && x.coords.x <= global.camera.x + global.camera.camWidth + 20) x.draw(batch))
		World.projectiles.foreach(_.draw(batch))
		World.buttons.foreach(_.draw(batch))
		
		if (global.building.isDefined) {
			global.buildingSprite.get.setAlpha(0.5f)
			global.buildingSprite.get.setPosition(Gdx.input.getX(), 200)
	    global.buildingSprite.get.draw(batch)
			global.buildingSprite.get.setAlpha(1f)
		}
	}
	
	def step() = {
		World.updateWorld()	
	}
}
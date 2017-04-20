package com.mygdx.emsb

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color

abstract class State(val name: String, val ctrl: Controller) {
	
  def draw(batch: SpriteBatch)
  def step()
}

class DeathState(override val ctrl: Controller) extends State("DeathState", ctrl) {
	def step() = {}
	
	def draw(batch: SpriteBatch) = {
		global.drawOutline("You lost!\nPress 'SPACE' to return to main menu", 320, 340, 1, Color.WHITE, global.font, batch)
	}
}

class MenuState(override val ctrl: Controller) extends State("MenuState", ctrl) {

	def draw(batch: SpriteBatch) = {
		ctrl.logo.draw(batch)
		Menu.buttons.foreach(_.draw(batch))
	}
	def step() = {
		Menu.updateMenu()
	}
}

class FightState(override val ctrl: Controller) extends State("FightState", ctrl) {
	
	def create() = {
		
	}
	
	def draw(batch: SpriteBatch) = {
	  ctrl.tausta.draw(batch)
	  ctrl.mountains.draw(batch)
	  ctrl.floor.draw(batch)	
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
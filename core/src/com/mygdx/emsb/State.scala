package com.mygdx.emsb

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector3

/**
 * States represent different game states (such as FightState or MenuState)
 * A state determines what will be drawn and whose step event will be executed during game loop
 */
abstract class State(val name: String, val ctrl: Controller) {
	
  def draw(batch: SpriteBatch)
  def step()
}

class DeathState(override val ctrl: Controller) extends State("DeathState", ctrl) {
	def step() = {}
	
	def draw(batch: SpriteBatch) = {
	  ctrl.tausta.draw(batch)
	  ctrl.mountains.draw(batch)
	  ctrl.floor.draw(batch)
		World.instances.foreach(x => if (x.coords.x >= global.camera.x - 20 && x.coords.x <= global.camera.x + global.camera.camWidth + 20) x.draw(batch))
		World.projectiles.foreach(_.draw(batch))
		global.drawOutline("You lost!\nPress 'SPACE' to return to main menu\nScore: " + global.score, 320, 340, 1, Color.WHITE, global.font, batch)
	}
}

class HelpState(override val ctrl: Controller) extends State("HelpState", ctrl) {
	
	def draw(batch: SpriteBatch) = {
		ctrl.menuBg.draw(batch)
		global.drawOutline("Hermo's Revenge - Evil Miguli Strikes Back\n\nYou need to defend Hermo's home from Evil Miguli's minions!\nControls:\nUse cursor to select and build buildnings\nPress 'M' to mute/unmute\nUse arrow keys to move camera\n\nYou can create custom waves in assets/waves/waves.txt\nThe format is:\nwave: wave number\nenemies: enemies separated by comma\nspeed: spawn speed (int)\n\nThis game is a sequel to\nFilemon's Revenge (https://gamedev.fi/keskustelu/index.php?topic=185)\nand Hermo's Revenge (https://gamedev.fi/keskustelu/index.php?topic=2036)\n\n\nGame by Kyosti Alkio", 64, global.HEIGHT - 64, 1, Color.RED, global.font, batch)
		Menu.helpButtons.foreach(_.draw(batch))
	}
	def step() = {
		Menu.updateHelpMenu()
	}
}

class MenuState(override val ctrl: Controller) extends State("MenuState", ctrl) {

	def draw(batch: SpriteBatch) = {
		ctrl.menuBg.draw(batch)
		ctrl.logo.draw(batch)
		Menu.buttons.foreach(_.draw(batch))
	}
	def step() = {
		Menu.updateMenu()
	}
}

class EscState(override val ctrl: Controller) extends State("EscState", ctrl) {
	
	def step() = {}
	
	def draw(batch: SpriteBatch) = {
	  ctrl.tausta.draw(batch)
	  ctrl.mountains.draw(batch)
	  ctrl.floor.draw(batch)	
		World.instances.foreach(x => if (x.coords.x >= global.camera.x - 50 && x.coords.x <= global.camera.x + global.camera.camWidth + 20) x.draw(batch))
		World.projectiles.foreach(_.draw(batch))
		World.buttons.foreach(_.draw(batch))
		
		if (global.building.isDefined) {
			global.buildingSprite.get.setAlpha(0.5f)
			global.buildingSprite.get.setPosition(Gdx.input.getX(), 200)
	    global.buildingSprite.get.draw(batch)
			global.buildingSprite.get.setAlpha(1f)
		}
	  val pos2 = new Vector3(20, global.HEIGHT - 20, 0)
		global.drawOutline("Score: " + global.score + "\nGold:   " + global.gold, pos2.x.toInt, pos2.y.toInt, 1, Color.WHITE, global.font, batch)
		global.drawOutline("Wave: " + global.wave, global.WIDTH - 128, global.HEIGHT - 32, 1, Color.WHITE, global.font, batch)
	  global.drawOutline("Return to menu? (Y/N))", 474, 430, 1, Color.WHITE, global.font, batch)
	}	
}

class FightState(override val ctrl: Controller) extends State("FightState", ctrl) {
	
	
	def draw(batch: SpriteBatch) = {
	  ctrl.tausta.draw(batch)
	  ctrl.mountains.draw(batch)
	  ctrl.floor.draw(batch)	
		World.instances.foreach(x => if (x.coords.x >= global.camera.x - 50 && x.coords.x <= global.camera.x + global.camera.camWidth + 20) x.draw(batch))
		World.projectiles.foreach(_.draw(batch))
		World.buttons.foreach(_.draw(batch))
		
		if (global.building.isDefined) {
			global.buildingSprite.get.setAlpha(0.5f)
			global.buildingSprite.get.setPosition(Gdx.input.getX(), 200)
	    global.buildingSprite.get.draw(batch)
			global.buildingSprite.get.setAlpha(1f)
		}
	  val pos2 = new Vector3(20, global.HEIGHT - 20, 0)
		global.drawOutline("Score: " + global.score + "\nGold:   " + global.gold, pos2.x.toInt, pos2.y.toInt, 1, Color.WHITE, global.font, batch)
		global.drawOutline("Wave: " + global.wave, global.WIDTH - 128, global.HEIGHT - 32, 1, Color.WHITE, global.font, batch)	  
	}
	
	def step() = {
		World.updateWorld()	
	}
}
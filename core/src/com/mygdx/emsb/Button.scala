package com.mygdx.emsb
import com.badlogic.gdx.Input
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3

/**
 * @author alkiok1
 */
abstract class Button(val area: Area, val target: String) {
  
	var sprite: Sprite 
	var icon: Sprite
	
	
	/**
	 * Check whether the button has been pressed this step
	 */
	def isPressed = {
		//println(new Coords(Gdx.input.getX(), global.HEIGHT - Gdx.input.getY()))
		Gdx.input.justTouched() && hover
	}
	/**
	 * Check whether the cursor is inside this button
	 */
	def hover = {
		this.area.isInside(new Coords(Gdx.input.getX(), global.HEIGHT - Gdx.input.getY()))
	}
	
	/**
	 * The action method will be called every step
	 */
	def action() 
	
	def width = this.area.width
	
	def height = this.area.height
	
	def drawText(batch: SpriteBatch)
	
  /** Draw the instance */
  def draw(batch: SpriteBatch) = {
    val pos = new Vector3(this.area.xy1.x.toFloat, this.area.xy1.y.toFloat, 0)
    this.sprite.setPosition(pos.x, pos.y)
		this.sprite.draw(batch)
		
		this.icon.setPosition(pos.x + icon.getWidth()/2, pos.y + icon.getHeight()/2)
		this.icon.draw(batch)
		
		if (hover) {
			drawText(batch)
		}
  }
	
}


class UpgradeButton (val creator: Building, target: String, area: Area) extends Button(area, target) {
	
	var sprite = global.sprites("squareButton")
	var icon = global.upgrades(target)("sprite").asInstanceOf[Sprite]
	
	def drawText(batch: SpriteBatch) = {
		val text = global.upgrades(target)("text").asInstanceOf[String]
		val cost = global.upgrades(target)("cost").asInstanceOf[Int]
		val pos = new Vector3(this.area.xy1.x.toFloat, this.area.xy2.y.toFloat - UpgradeButton.height - 24,0)
		global.font.draw(batch, text + "\nCost: " + cost, pos.x, pos.y)
	}
	
	 
	def action() = {
		if (isPressed) {
			creator.unlock("upgrade", target)
		}
	}
	
}

class UnlockButton (val creator: Building, target: String, area: Area, val snowTower: Boolean = false) extends Button(area, target) {
	
	var sprite = global.sprites("squareButton")
	var icon = global.unlocks(target)("sprite").asInstanceOf[Sprite]
	
	def drawText(batch: SpriteBatch) = {
		val text = global.unlocks(target)("text").asInstanceOf[String]
		val cost = if (!snowTower) global.unlocks(target)("cost").asInstanceOf[Int] else global.unlocks(target)("buildCost").asInstanceOf[Int]
		val pos = new Vector3(this.area.xy1.x.toFloat, this.area.xy2.y.toFloat + UpgradeButton.height,0)
		global.font.draw(batch, text + "\nCost: " + cost, pos.x, pos.y)
	}
	
	 
	def action() = {
		if (isPressed) {
			creator.unlock("unlock", target)
		}
	}
	
}

class BuildButton (val creator: Building, target: String, area: Area) extends Button(area, target) {
	var sprite = global.sprites("squareButton")
	var icon = global.buildables(target)("sprite").asInstanceOf[Sprite]
	
	def drawText(batch: SpriteBatch) = {
		val text = global.buildables(target)("text").asInstanceOf[String]
		val cost = global.buildables(target)("cost").asInstanceOf[Int]
		val pos = new Vector3(this.area.xy1.x.toFloat, this.area.xy2.y.toFloat - UpgradeButton.height - 24,0)
		global.font.draw(batch, text + "\nCost: " + cost , pos.x, pos.y)
	}	
	
	def action() = {
		if (isPressed) {
			global.building = Some(target)
			println(global.building)
			global.buildingSprite = Some(global.sprites(target))
		}
	}
}

object UpgradeButton {
	
	def width = global.sprites("squareButton").getWidth.toInt
	def height = global.sprites("squareButton").getHeight.toInt
	
	def apply(creator: Building, target: String, area: Area) = {
		new UpgradeButton(creator, target, area)
	}
	
}

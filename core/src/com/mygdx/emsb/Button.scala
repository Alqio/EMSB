package com.mygdx.emsb
import com.badlogic.gdx.Input
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.graphics.Color
import com.mygdx.instances.Building
import com.badlogic.gdx.graphics.g2d.GlyphLayout


/**
 * Button class represents buttons (surprisingly)
 * @param area This button's area (where it can be clicked/hovered)
 * @param target what is this button's target unlock/upgrade
 */
abstract class Button(val area: Area, val target: String) {
  
	var sprite: Sprite 
	var icon: Sprite
	
	
	/**
	 * Check whether the button has been pressed this step
	 */
	def isPressed = {
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
	
	/**
	 * Draw text (can be drawn inside or outside the button)
	 */
	def drawText(batch: SpriteBatch)
	
  /** 
   *  Draw the button, its text and its icon
   */
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

/**
 * Menu button represents the buttons in the menu. It draws its text centered and inside the button
 */

class MenuButton(text: String, area: Area) extends Button(area, text) {
	
	var sprite = global.sprites("menuButton")
	var icon   = global.sprites("menuButton")
	var hoverSprite = global.sprites("menuButtonHover")
	
	global.font.setColor(Color.RED)
	val layout: GlyphLayout = new GlyphLayout(global.font, text);
	global.font.setColor(Color.BLACK)
	val layoutBG: GlyphLayout = new GlyphLayout(global.font, text)
	
	
	val fontX: Float = (this.area.xy1.x + (MenuButton.width - layout.width) / 2).toFloat
	val fontY: Float = (this.area.xy1.y + (MenuButton.height + layout.height) / 2).toFloat
	
	def action() = {}
	
	def drawText(batch: SpriteBatch) = {}
	
	override def draw(batch: SpriteBatch) = {
    val pos = new Vector3(this.area.xy1.x.toFloat, this.area.xy1.y.toFloat, 0)
    this.sprite.setPosition(pos.x, pos.y)
		this.sprite.draw(batch)
		global.drawOutline(layout, layoutBG, fontX.toInt, fontY.toInt, 1, global.font, batch)
	}
}
class StartButton(area: Area) extends MenuButton("Normal", area) {
	override def action() = {
		if (hover) {
			sprite = hoverSprite
		} else {
			sprite = icon
		}
		if (isPressed) {
			global.ctrl.filePath = ""
			global.state = new FightState(global.ctrl)
			global.ctrl.init()
		}
	}
}
class BackButton(area: Area) extends MenuButton("Back", area) {
	override def action() = {
		if (hover) {
			sprite = hoverSprite
		} else {
			sprite = icon
		}
		if (isPressed) {
			global.state = new MenuState(global.ctrl)
		}
	}
}
class HelpButton(area: Area) extends MenuButton("Help", area) {
	override def action() = {
		if (hover) {
			sprite = hoverSprite
		} else {
			sprite = icon
		}
		if (isPressed) {
			global.state = new HelpState(global.ctrl)
		}
	}
}
class ExitButton(area: Area) extends MenuButton("Quit", area) {
	override def action() = {
		if (hover) {
			sprite = hoverSprite
		} else {
			sprite = icon
		}
		if (isPressed) {
			Gdx.app.exit()
		}
	}
}
class LoadButton(area: Area) extends MenuButton("Custom", area) {
	override def action() = {
		if (hover) {
			sprite = hoverSprite
		} else {
			sprite = icon
		}
		if (isPressed) {
			global.ctrl.filePath = "waves/waves.txt"
			global.state = new FightState(global.ctrl)
			global.ctrl.init()
		}
	}
}

/**
 * This button is used by research center to upgrade buildings
 */
class UpgradeButton (val creator: Building, target: String, area: Area) extends Button(area, target) {
	
	var sprite = global.sprites("squareButton")
	var icon = global.upgrades(target)("sprite").asInstanceOf[Sprite]
	
	def drawText(batch: SpriteBatch) = {
		val text = global.upgrades(target)("text").asInstanceOf[String]
		val cost = global.upgrades(target)("cost").asInstanceOf[Int]
		val pos = new Vector3(this.area.xy1.x.toFloat, this.area.xy2.y.toFloat - UpgradeButton.height - 12,0)
		global.drawOutline(text + "\nCost: " + cost,  pos.x.toInt, pos.y.toInt, 1, Color.RED, global.font, batch)
	}
	
	 
	def action() = {
		if (isPressed) {
			creator.unlock("upgrade", target)
		}
	}
	
}
/**
 * This button is used by research center to unlock new buildings
 */
class UnlockButton (val creator: Building, target: String, area: Area, val snowTower: Boolean = false) extends Button(area, target) {
	
	var sprite = global.sprites("squareButton")
	var icon = global.unlocks(target)("sprite").asInstanceOf[Sprite]
	
	def drawText(batch: SpriteBatch) = {
		val text = global.unlocks(target)("text").asInstanceOf[String]
		val cost = if (!snowTower) global.unlocks(target)("cost").asInstanceOf[Int] else global.unlocks(target)("buildCost").asInstanceOf[Int]
		val pos = new Vector3(this.area.xy1.x.toFloat, this.area.xy2.y.toFloat  - UpgradeButton.height - 12,0)
		global.drawOutline(text + "\nCost: " + cost,  pos.x.toInt, pos.y.toInt, 1, Color.RED, global.font, batch)
		
	}
	
	
	def action() = {
		if (isPressed) {
			creator.unlock("unlock", target)
		}
	}
	
}
/**
 * This buttons is used by Main House to create new buildnings
 */
class BuildButton (val creator: Building, target: String, area: Area) extends Button(area, target) {
	var sprite = global.sprites("squareButton")
	var icon = global.buildables(target)("sprite").asInstanceOf[Sprite]
	
	def drawText(batch: SpriteBatch) = {
		val text = global.buildables(target)("text").asInstanceOf[String]
		val cost = global.buildables(target)("cost").asInstanceOf[Int]
		val pos = new Vector3(this.area.xy1.x.toFloat, this.area.xy2.y.toFloat - UpgradeButton.height - 12,0)
		global.drawOutline(text + "\nCost: " + cost,  pos.x.toInt, pos.y.toInt, 1, Color.RED, global.font, batch)
	}	
	
	def action() = {
		if (isPressed) {
			global.building = Some(target)
			global.buildingSprite = Some(global.sprites(target))
		}
	}
}
/**
 * This button is used by barracks to create new units
 */
class BarracksButton (val creator: Building, target: String, area: Area) extends Button(area, target) {
	var sprite = global.sprites("squareButton")
	var icon   = global.upgrades(target)("sprite").asInstanceOf[Sprite]//global.buildables(target)("sprite").asInstanceOf[Sprite]
	
	def drawText(batch: SpriteBatch) = {
		val text = global.upgrades(target)("text").asInstanceOf[String]
		val cost = global.upgrades(target)("cost").asInstanceOf[Int]
		val pos = new Vector3(this.area.xy1.x.toFloat, this.area.xy2.y.toFloat - UpgradeButton.height - 12,0)
		global.drawOutline(text + "\nCost: " + cost,  pos.x.toInt, pos.y.toInt, 1, Color.RED, global.font, batch)
	}	
	
	def action() = {
		if (isPressed) {
			creator.unlock("spawn",target)
		}
	}
}
/**
 * This object is used to get the width and height of the button
 */

object UpgradeButton {
	
	def width = global.sprites("squareButton").getWidth.toInt
	def height = global.sprites("squareButton").getHeight.toInt
	
	def apply(creator: Building, target: String, area: Area) = {
		new UpgradeButton(creator, target, area)
	}
	
}
/**
 * This object is used to get the width and height of the button
 */
object MenuButton {
	def width = global.sprites("menuButton").getWidth.toInt
	def height = global.sprites("menuButton").getHeight.toInt
	
	def apply(target: String, area: Area) = {
		new MenuButton(target, area)
	}	
}
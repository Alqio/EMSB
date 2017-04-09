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
abstract class Button (val area: Area){
  
	var sprite: Sprite 
	var icon: Sprite
	
	def isPressed = {
		//println(new Coords(Gdx.input.getX(), global.HEIGHT - Gdx.input.getY()))
		Gdx.input.justTouched() && this.area.isInside(new Coords(Gdx.input.getX(), global.HEIGHT - Gdx.input.getY()))
	}
	
	/**
	 * The action method will be called every step
	 */
	def action() 
	
	def width = this.area.width
	
	def height = this.area.height
	
	
  /** Draw the instance */
  def draw(batch: SpriteBatch) = {
    val pos = new Vector3(this.area.xy1.x.toFloat, this.area.xy1.y.toFloat, 0)
    this.sprite.setPosition(pos.x, pos.y)
		this.sprite.draw(batch)
		this.icon.setPosition(pos.x, pos.y)
		this.icon.draw(batch)
  }
	
}
class UpgradeButton (val creator: ResearchCenter, val target: String, area: Area) extends Button(area) {
	
	var sprite = global.sprites("squareButton")
	var icon = global.sprites("hpUp")
	
	def action() = {
		if (isPressed) {
			creator.unlock("upgrade", target)
		}
	}
	
}
object UpgradeButton {
	
	def width = global.sprites("squareButton").getWidth.toInt
	def height = global.sprites("squareButton").getHeight.toInt
	
	def apply(creator: ResearchCenter, target: String, area: Area) = {
		new UpgradeButton(creator, target, area)
	}
	
}

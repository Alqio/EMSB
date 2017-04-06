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
	
	def isPressed = {
		//println(new Coords(Gdx.input.getX(), global.HEIGHT - Gdx.input.getY()))
		Gdx.input.justTouched() && this.area.isInside(new Coords(Gdx.input.getX(), global.HEIGHT - Gdx.input.getY()))
	}
	
	def action() 
	
	def width = this.area.width
	
	def height = this.area.height
	
	
  /** Draw the instance */
  def draw(batch: SpriteBatch) = {
    val pos = new Vector3(this.area.xy1.x.toFloat, this.area.xy1.y.toFloat, 0)
    this.sprite.setPosition(pos.x, pos.y)
		this.sprite.draw(batch)
  }
	
}
class UpgradeButton (val creator: ResearchCenter, val target: String, area: Area) extends Button(area) {
	
	var sprite = global.sprites("squareButton")
	
	def action() = {
		if (isPressed) {
			println("Nyt painettiin nappia2")
			creator.unlock("upgrade", target)
		}
	}
	
}
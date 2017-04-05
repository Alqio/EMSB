package com.mygdx.emsb
import com.badlogic.gdx.Input
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.Texture

/**
 * @author alkiok1
 */
abstract class Button (val width: Int, val height: Int, val area: Area){
  
	var sprite: Sprite 
	
	def isPressed = {
		Gdx.input.justTouched() && this.area.isInside(new Coords(Gdx.input.getX(), Gdx.input.getY()))
	}
	
	
}
class UpgradeButton (val creator: ResearchCenter, val target: String, width: Int, height: Int, area: Area) extends Button(width, height, area) {
	
	var sprite = global.sprites("upgradeButton")
	
	def action() = {
		if (isPressed) {
			creator.unlock("upgrade", target)
		}
	}
	
}
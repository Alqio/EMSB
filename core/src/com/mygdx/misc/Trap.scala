package com.mygdx.misc

import com.mygdx.emsb.Coords
import com.mygdx.emsb.global
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.mygdx.emsb.Area

class Trap {
  
	var dmg = 1.0
  var coords = Coords(0,0)
  var sprite: Sprite = global.sprites("spikeTrap")
  
  
  def hitArea = Area(this.coords, this.sprite.getWidth().toInt, this.sprite.getHeight().toInt)
  
  def step() = {
		
	}
  
}
package com.mygdx.emsb

import Math._
/**
 * Represents a rectangular area
 */
class Area(val xy1: Coords, val xy2: Coords) {
  
	def isInside(coords: Coords) = xy1.x <= coords.x && xy2.x >= coords.x && xy1.y <= coords.y && xy2.y >= coords.y
	
	def width = abs(xy1.x - xy2.x)
	
	def height = abs(xy1.y - xy2.y)
	
}
package com.mygdx.emsb

import math._
/**
 * Represents a rectangular area
 */
class Area(val xy1: Coords, val xy2: Coords) {
  
	def pienempiX = min(xy1.x, xy2.x)
	def pienempiY = min(xy1.y, xy2.y)
	def suurempiX = max(xy1.x, xy2.x)
	def suurempiY = max(xy1.y, xy2.y)
	
	/**
	 * Checks if a coordinate is inside this area
	 */
	def isInside(coords: Coords) = pienempiX <= coords.x && suurempiX >= coords.x && suurempiY >= coords.y && pienempiY <= coords.y
		
	def isInside(area: Area) = intersects(area)
	
	/**
	 * Checks whether this and another area intersect
	 */
	def intersects(area: Area) = {
		pienempiX <= area.pienempiX + area.width && pienempiX + width >= area.pienempiX && pienempiY <= area.pienempiY + area.height && pienempiY + height >= area.pienempiY
	}
														 
	def width = abs(xy1.x - xy2.x)
	
	def height = abs(xy1.y - xy2.y)
	
	override def toString = xy1.toString + "\n" + xy2.toString
}


object Area {
	
	def apply(coords: Coords, width: Int, height: Int) = {
		new Area(coords, Coords(coords.x + width, coords.y + height))
	}

}
package com.mygdx.emsb

import scala.math._
/**
 * Coords represents a point in the screen
 * @param x the x coordinate
 * @param y the y coordinate
 */
class Coords(var x: Double, var y: Double) {
  
	val error = 0.001
	
	/**
	 * Return the distance between two coords
	 */
	def distanceToPoint(another: Coords) = {
		sqrt( pow(another.x - this.x,2) + pow(another.y - this.y,2) )
	}
	
	override def toString = "x: " + this.x + ", y: " + this.y
	
	def ==(another: Coords) = abs(this.x - another.x) < error && abs(this.y - another.y) < error
	
}

object Coords {
	def apply(x: Double, y: Double) = new Coords(x,y)
}
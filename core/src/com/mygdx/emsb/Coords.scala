package com.mygdx.emsb

import scala.math._
/**
 * @author alkiok1
 */
class Coords(var x: Double, var y: Double) {
  
	def distanceToPoint(another: Coords) = {
		sqrt( pow(another.x - this.x,2) + pow(another.y - this.y,2) )
	}
	
	override def toString = "x: " + this.x + ", y: " + this.y
	
	def ==(another: Coords) = this.x == another.x && this.y == another.y
	
}

object Coords {
	def apply(x: Double, y: Double) = new Coords(x,y)
}
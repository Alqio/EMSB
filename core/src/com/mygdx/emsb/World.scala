package com.mygdx.emsb

import collection.mutable.ArrayBuffer

/**
 * @author alkiok1
 */
object World{
  
	val instances   = ArrayBuffer[Instance]()
  val projectiles = ArrayBuffer[Projectile]()
	val buttons     = ArrayBuffer[Button]()
  
	def instanceAt(koordinaatit: Coords): Option[Instance] = {
    var instance: Option[Instance] = None
		for (i <- instances) {
		  if (i.checkCollision(koordinaatit)) {
		    instance = Some(i) 
		  }
		}
    //println(instance)
    instance
	}
	
	def buttonAt(coordinates: Coords): Option[Button] = {
		var button: Option[Button] = None
		for (i <- buttons) {
		  if (i.area.isInside(coordinates)) {
		    button = Some(i) 
		  }
		}
    button
	}
	
	def updateWorld() = {
		/** Execute the step event for all instances **/
		instances.toVector.foreach(_.step())
		
		/** Execute the step event for all projectiles **/
		projectiles.toVector.foreach(_.step())
		
		/** Move all alarms **/
		instances.foreach(_.alarms.foreach(_.move()))	  
		
		/** Check all buttons if they are pressed **/
		buttons.foreach(_.action())
	}
	 
}
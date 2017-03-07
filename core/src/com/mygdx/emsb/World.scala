package com.mygdx.emsb

import collection.mutable.ArrayBuffer

/**
 * @author alkiok1
 */
object World{
  
	val instances = ArrayBuffer[Instance]()
  val projectiles = ArrayBuffer[Projectile]()
	
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
	
	
	def updateWorld() = {
		/** Execute the step event for all instances **/
		instances.toVector.foreach(_.step())
		
		/** Execute the step event for all projectiles **/
		projectiles.toVector.foreach(_.step())
		
		/** Move all alarms **/
		instances.foreach(_.alarms.foreach(_.move()))	  
	}
	 
}
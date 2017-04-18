package com.mygdx.emsb

import collection.mutable.ArrayBuffer

/**
 * @author alkiok1
 */
object World{
  
	val instances   = ArrayBuffer[Instance]()
  val projectiles = ArrayBuffer[Projectile]()
	val buttons     = ArrayBuffer[Button]()
  
	def enemies = {
		this.instances.filter(x => x.isInstanceOf[EnemyUnit])
	}
	
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
	
	def areaIsFree(area: Area): Boolean = {
		//val solidit = World.instances.filter(x => x.solid)
		World.instances.filter(x => area.isInside(x.hitArea) || x.hitArea.isInside(area)).size == 0
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
	
	/**
	 * Update the world. Move all instances and projectiles, execute their step events, move alarms and check button events.
	 */
	def updateWorld() = {
		/** Execute the step event for all instances **/
		instances.toVector.foreach(_.step())
		
		/** Execute the step event for all projectiles **/
		projectiles.toVector.foreach(_.step())
		
		/** Move all alarms **/
		instances.foreach(_.alarms.foreach(_.move()))	  
		
		/** Check all buttons if they are pressed **/
		buttons.toVector.foreach(_.action())
	}
	 
}
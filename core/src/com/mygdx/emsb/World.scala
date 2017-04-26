package com.mygdx.emsb

import collection.mutable.ArrayBuffer
import com.mygdx.instances.Instance
import com.mygdx.instances.EnemyUnit

/**
 * World contains lists of all current instances, projectiles and buttons in the World. 
 */
object World{
  
	val instances   = ArrayBuffer[Instance]()
  val projectiles = ArrayBuffer[Projectile]()
	val buttons     = ArrayBuffer[Button]()
  
	def enemies = {
		this.instances.filter(x => x.isInstanceOf[EnemyUnit])
	}
	
	/**
	 * Returns the instance at coordinates
	 */
	def instanceAt(koordinaatit: Coords): Option[Instance] = {
    var instance: Option[Instance] = None
		for (i <- instances) {
		  if (i.checkCollision(koordinaatit)) {
		    instance = Some(i) 
		    return instance
		  }
		}
    instance
	}
	/**
	 * Checks whether an area is free
	 */
	def areaIsFree(area: Area): Boolean = {
		World.instances.filter(x => area.isInside(x.hitArea) || x.hitArea.isInside(area)).size == 0
	}
	
	/**
	 * Return the button at coordinates
	 */
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
		/** Execute the step event for all instances */
		instances.toVector.foreach(_.step())
		
		/** Execute the step event for all projectiles */
		projectiles.toVector.foreach(_.step())
		
		/** Move all alarms */
		instances.foreach(_.alarms.foreach(_.move()))	  
		
		/** Check all buttons if they are pressed */
		buttons.toVector.foreach(_.action())
	}
	 
}
package com.mygdx.emsb

import collection.mutable.Buffer

/**
 * @author alkiok1
 */
object World{
  
	val instances = Buffer[Instance]()
  val projectiles = Buffer[Projectile]()
	
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

	 
}
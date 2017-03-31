package com.mygdx.emsb

import collection.mutable.Buffer

/**
 * @author alkiok1
 */
class WaveController {
  var wave = 1
  
  var waves = Buffer[Map[String, Int]]()
  
  var wave1 = Map[String, Int](
  	"vihuy" -> 10		
  )
  var wave2 = Map[String, Int](
  	"vihuy" -> 15,
  	"saks"  -> 2
  )
  
  
  
  waves += wave1
  
  
}
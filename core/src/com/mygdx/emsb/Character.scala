package com.mygdx.emsb

/**
 * @author alkiok1
 */
abstract class Character(ctrl: Controller) extends Instance(ctrl) {
  
  var spd: Double
  
  //Includes directions
  var realSpdX: Double = 0
  var realSpdY: Double = spd
  
  
  
  var direction = 0 // 0 - 360
  
  def step() = {
  	this.target = this.instanceNearest()
  	if (target.isDefined) {
    	if (this.coords.distanceToPoint(this.target.get.coords) <= range) {
    		
    	} else {
    		this.move()
    	}
  	}
  	//println(this.coords)
  	//println(this.realSpdX)
  	
  }
  
  /** Move the character if possible. 
   *  Returns true if move was successfull, else false
   *  */
  def move() = {
  	if (place_free(this.realSpdX.toInt, this.realSpdY.toInt) && this.target.isDefined) {
  		this.coords.x += (if (this.coords.x < this.target.get.coords.x) this.realSpdX else -1*this.realSpdX)
  		this.coords.y += (if (this.coords.y < this.target.get.coords.y) this.realSpdY else -1*this.realSpdX)
  		true
  	} else {
  	  false
  	}
  }
  
  /** checks if the place in coordinates (x,y) relative to this instance's position.
   *  For example, if the instance is	at coordinates (5,5) and the methdod is called with parameters
   *  place_free(3,4), the coordinates to be checked is (8,9). 
   *  **/
  def place_free(x: Int, y: Int): Boolean = {
  	val newCoords = new Coords(this.coords.x + x, this.coords.y + y)
  	if (World.instanceAt(newCoords).forall(!_.solid) ) {
  		true
  	} else {
  		false
  	}
  }
}
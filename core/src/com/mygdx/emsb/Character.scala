package com.mygdx.emsb

import Methods._

/**
 * @author alkiok1
 */
abstract class Character(ctrl: Controller) extends Instance(ctrl) {
  
  var spd: Double
  
  //Includes directions
  var realSpdX: Double = 0
  var realSpdY: Double = spd
  
  
  //ks kuva http://files.1337upload.net/ohje-579ce0.png
  var attackPoint = this.coords.x
  
  var direction = 0 // 0 - 360
  
  def step() = {
  	
  	this.target = this.instanceNearest()
  	
  	if (target.isDefined) {
  	  
  	  if (this.coords.x < this.target.get.position.x) suunta = 1 else suunta = -1
  	  
  	  //this.sprite.flip(intToBool(suunta), false)
  	  
    	if (this.coords.distanceToPoint(this.target.get.position) <= range) {
    		println("nyt ollaan rangen sisällä")
    	  if (this.alarms(0).time == -1) {
	  		  this.alarms(0).time += attackSpeed
	  		  attack()
	  	  }
    	  
    	} else {
    		this.move()

    	}
  	}
  	
  }

  /**
   * This method will be called only if
   * 1) the target is within range and it exists
   * 2) the alarm(0) == -1
   * 
   * alarm(0) will be set to attack speed and then this method will be called
   */
  def attack() = {
		println("jee")
  	target.get.takeDmg(this.dmg)
  }
  
  /** Move the character if possible. 
   *  Returns true if move was successfull, else false
   *  */
  def move() = {
    
  	if (place_free(this.position.x.toInt + (this.realSpdX.toInt + this.sprite.getWidth().toInt/2) * suunta, this.position.y.toInt + this.realSpdY.toInt + 5) && this.target.isDefined) {
  		this.coords.x += this.realSpdX * suunta
  		this.coords.y += (if (this.coords.y < this.target.get.coords.y) this.realSpdY else -1*this.realSpdX)
  		true
  	} else {
  	  false
  	}
  }
  
  
  /** checks if the place in coordinates (x,y) is free (it doesn't contain a SOLID object)
   *  **/
  def place_free(x: Int, y: Int): Boolean = {
  	val newCoords = new Coords(x, y)
  	
  	if (World.instanceAt(newCoords).forall(!_.solid) ) {
  		true
  	} else {
  		false
  	}
  }
}
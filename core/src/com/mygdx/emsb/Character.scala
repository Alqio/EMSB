package com.mygdx.emsb

import Methods._

/**
 * @author alkiok1
 */
abstract class Character(ctrl: Controller) extends Instance(ctrl) {
  
  var spd: Double = 0
  
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
  		this.attackPoint = this.position.x + suunta * range  
  	  //this.sprite.flip(intToBool(suunta), false)
  	  
    	if (canAttack) {
  		  this.alarms(0).time += attackSpeed
  		  attack()
    	} else {
    		this.move()
    	}
  	}
  }

  def canAttack: Boolean = this.alarms(0).time == -1 && this.target.get.hitArea.isInside(new Coords(this.attackPoint, this.position.y))
  
  /**
   * This method will be called only if
   * 1) target exists
   * 2) attackPoint is inside target's hitArea (= canAttack is true)
   * 3) alarm(0) == -1
   * 
   * alarm(0) will be set to attack speed and then this method will be called
   */
  def attack() = {
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
package com.mygdx.emsb

import collection.mutable.Buffer

object Menu {
  var inMenu = true
  
  val buttons = Buffer[Button]()
  
  buttons += new StartButton(Area(Coords(640 - 64, 360), MenuButton.width, MenuButton.height))
  buttons += new  LoadButton(Area(Coords(640 - 64, 360 - 72), MenuButton.width, MenuButton.height))
  buttons += new  ExitButton(Area(Coords(640 - 64, 360 - 72 - 72), MenuButton.width, MenuButton.height))
  
  def updateMenu() = {
		/** Check all buttons if they are pressed **/
		buttons.toVector.foreach(_.action())  	
  }
  
}
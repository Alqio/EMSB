package com.mygdx.emsb

import collection.mutable.Buffer

/**
 * Object Menu loads all the buttons that the menu needs
 */

object Menu {
  var inMenu = true
  
  val buttons		  = Buffer[Button]()
  val helpButtons = Buffer[Button]()
  
  buttons += new StartButton(Area(Coords(640 - 64, 360), MenuButton.width, MenuButton.height))
  buttons += new  LoadButton(Area(Coords(640 - 64, 360 - 72), MenuButton.width, MenuButton.height))
  buttons += new  HelpButton(Area(Coords(640 - 64, 360 - 72 - 72), MenuButton.width, MenuButton.height))
  buttons += new  ExitButton(Area(Coords(640 - 64, 360 - 72 - 72 - 72), MenuButton.width, MenuButton.height))
  
  helpButtons += new BackButton(Area(Coords(64, 64), MenuButton.width, MenuButton.height))
  
  def updateMenu() = {
		/** Check all buttons if they are pressed **/
		buttons.toVector.foreach(_.action())  	
  }
  def updateHelpMenu() = {
  	helpButtons.toVector.foreach(_.action())
  }
  
}
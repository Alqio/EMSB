package com.mygdx.emsb

class ResearchCenter(ctrl: Controller) extends Building(ctrl) {
  
  name = "Research center"
  hp = 150
  maxHp = hp
  sprite = global.sprites("researchCenter")
  maxLevel = 1 
  var targetUnlock = "Fire"
 
  /**
   * Research center cannot attack
   */
  
  def attack() = {}
  
}
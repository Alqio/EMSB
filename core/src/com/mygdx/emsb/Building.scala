package com.mygdx.emsb

abstract class Building(ctrl: Controller) extends Instance(ctrl){
  override val solid = true
  override val side = "friendly"
}
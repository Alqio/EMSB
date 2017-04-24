package com.mygdx.instances

/**
 * Enemy unit has scoreGain and goldGain
 */
abstract class EnemyUnit() extends Character() {
  override val side = "enemy"
  var scoreGain = 1
  var goldGain  = 1
}
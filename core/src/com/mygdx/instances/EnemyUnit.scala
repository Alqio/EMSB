package com.mygdx.instances

/**
 * @author alkiok1
 */
abstract class EnemyUnit() extends Character() {
  override val side = "enemy"
  var scoreGain = 1
  var goldGain  = 1
}
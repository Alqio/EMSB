package com.mygdx.emsb
/**
 * @author alkiok1
 */
abstract class EnemyUnit() extends Character() {
  override val side = "enemy"
  var scoreGain = 1
  var goldGain  = 1
}
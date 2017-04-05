package com.mygdx.emsb
/**
 * @author alkiok1
 */
abstract class EnemyUnit() extends Character() {
  override val side = "enemy"
  val scoreGain = 1
  val goldGain  = 1
}
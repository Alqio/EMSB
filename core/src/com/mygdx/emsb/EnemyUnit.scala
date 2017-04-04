package com.mygdx.emsb
/**
 * @author alkiok1
 */
abstract class EnemyUnit(ctrl: Controller) extends Character(ctrl) {
  override val side = "enemy"
  val scoreGain = 1
  val goldGain  = 1
}
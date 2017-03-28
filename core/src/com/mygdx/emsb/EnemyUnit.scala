package com.mygdx.emsb
/**
 * @author alkiok1
 */
abstract class EnemyUnit(ctrl: Controller) extends Character(ctrl) {
  override val side = "enemy"
  val score = 1
  val gold  = 1
}
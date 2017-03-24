package com.mygdx.emsb

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.Texture


object global{
  
  var buildingDmgMultiplier = 1.0
  var buildingHpMultiplier = 1.0
  var score = 0
  var playerName = "Sukka Mehuttaja"
  
  
  /**
   * A list of all sprites in the game. They are all loaded when the game starts, and they can then be disposed of later. 
   */
  val sprites = Map[String, Sprite](
    "vihuy"     -> new Sprite(new Texture("vihuy.png")),
    "snowTower" -> new Sprite(new Texture("snowTower.png")),
    "fireTower" -> new Sprite(new Texture("fireTower.png")),
    "snowBall1" -> new Sprite(new Texture("snowBall1.png")),
    "snowBall2" -> new Sprite(new Texture("snowBall2.png"))
  )
  
}
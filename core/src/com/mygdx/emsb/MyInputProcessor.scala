package com.mygdx.emsb

import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Input;

class MyInputProcessor extends InputProcessor {
  def keyDown(x$1: Int): Boolean = ???
  def keyTyped(x$1: Char): Boolean = ???
  def keyUp(x$1: Int): Boolean = ???
  def mouseMoved(x$1: Int,x$2: Int): Boolean = ???
  def scrolled(x$1: Int): Boolean = ???
  def touchDown(x: Int, y: Int, pointer: Int, button: Int): Boolean = button == Input.Buttons.LEFT
  def touchDragged(x$1: Int,x$2: Int,x$3: Int): Boolean = ???
  def touchUp(x$1: Int,x$2: Int,x$3: Int,x$4: Int): Boolean = ???

}
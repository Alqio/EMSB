package com.mygdx.emsb
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3

class CameraDemo extends ApplicationAdapter {

  var batch: SpriteBatch = null
  var img: Texture = null
  var camera: OrthographicCamera = null

  override def create() = {
    batch = new SpriteBatch()
    img = new Texture("bg1.png")
    camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())
    camera.translate(camera.viewportWidth/2,camera.viewportHeight/2);
  }

  override def render() = {
    Gdx.gl.glClearColor(1, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    camera.update();
    batch.setProjectionMatrix(camera.combined);
    batch.begin();
    batch.draw(img, 0, 0);
    batch.end();
  }

}
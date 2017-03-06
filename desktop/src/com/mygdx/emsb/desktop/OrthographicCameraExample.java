package com.mygdx.emsb.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;

public class OrthographicCameraExample implements ApplicationListener {

    static final int WORLD_WIDTH = 100;
    static final int WORLD_HEIGHT = 100;

    private OrthographicCamera cam;
    private SpriteBatch batch;
    
    private BitmapFont font;
    
    private Sprite mapSprite;
    private float rotationSpeed;

    @Override
    public void create() {
        rotationSpeed = 0.5f;
		font = new BitmapFont();
		font.setColor(Color.RED);
        mapSprite = new Sprite(new Texture(Gdx.files.internal("tausta.png")));
        mapSprite.setPosition(0, 0);
        mapSprite.setSize(WORLD_WIDTH, WORLD_HEIGHT);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio.
        cam = new OrthographicCamera(30, 30 * (h / w));

        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();

        batch = new SpriteBatch();
    }

    @Override
    public void render() {
        handleInput();
        cam.update();
        batch.setProjectionMatrix(cam.combined);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        mapSprite.draw(batch);
        
        font.draw(batch, "moi", Gdx.input.getX(),Gdx.input.getY());
        batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            cam.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            cam.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            cam.translate(-3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            cam.translate(3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            cam.translate(0, -3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            cam.translate(0, 3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            cam.rotate(-rotationSpeed, 0, 0, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            cam.rotate(rotationSpeed, 0, 0, 1);
        }

        cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, 100/cam.viewportWidth);

        float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
        float effectiveViewportHeight = cam.viewportHeight * cam.zoom;

        cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
        cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);
    }

    @Override
    public void resize(int width, int height) {
        cam.viewportWidth = 30f;
        cam.viewportHeight = 30f * height/width;
        cam.update();
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        mapSprite.getTexture().dispose();
        batch.dispose();
    }

    @Override
    public void pause() {
    }

    public static void main(String[] args) {
        new LwjglApplication(new OrthographicCameraExample());
    }
}
/*
	def handleInput() = {
	  if (Gdx.input.isKeyPressed(Input.Keys.A)) {
      cam.zoom += 0.02f;
      //If the A Key is pressed, add 0.02 to the Camera's Zoom
    }
    if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
      cam.zoom -= 0.02f;
        //If the Q Key is pressed, subtract 0.02 from the Camera's Zoom
    }
    if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
        cam.translate(-3, 0, 0);
        //If the LEFT Key is pressed, translate the camera -3 units in the X-Axis
    }
    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
        cam.translate(3, 0, 0);
        //If the RIGHT Key is pressed, translate the camera 3 units in the X-Axis
    }
    if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
        cam.translate(0, -3, 0);
        //If the DOWN Key is pressed, translate the camera -3 units in the Y-Axis
    }
    if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
        cam.translate(0, 3, 0);
        //If the UP Key is pressed, translate the camera 3 units in the Y-Axis
    }
    if (Gdx.input.isKeyPressed(Input.Keys.W)) {
        cam.rotate(-rotationSpeed, 0, 0, 1);
        //If the W Key is pressed, rotate the camera by -rotationSpeed around the Z-Axis
    }
    if (Gdx.input.isKeyPressed(Input.Keys.E)) {
        cam.rotate(rotationSpeed, 0, 0, 1);
        //If the E Key is pressed, rotate the camera by rotationSpeed around the Z-Axis
    }

    cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, 100/cam.viewportWidth);

    val effectiveViewportWidth: Float = cam.viewportWidth * cam.zoom;
    val effectiveViewportHeight: Float = cam.viewportHeight * cam.zoom;

    cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
    cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);
	}
*
*/
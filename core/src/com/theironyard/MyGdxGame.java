package com.theironyard;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static javax.xml.stream.XMLStreamConstants.SPACE;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	TextureRegion down, up, right;
	float x, y, xv, yv;
	boolean faceRight = true, faceUp;
	Animation walk, walkUp, walkDown;
	float time;

	static final float SPEED_INCREASE = 500;
	static final float MAX_VELOCITY = 300;
	static final float DECELERATION = 0.95f;

	static final int WIDTH = 18;
	static final int HEIGHT = 26;



	@Override
	public void create () {
		batch = new SpriteBatch();
		Texture tiles = new Texture("tiles.png");
		TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
		down = grid [6][0];
		up = grid[6][1];
		right = grid[6][3];
		walk = new Animation(0.2f, grid[6][2], grid[6][3]);
		walkUp = new Animation(0.2f, grid[6][1], grid [7][1]);
		walkDown = new Animation(0.2f, grid[6][0], grid [7][0]);

	}

	@Override
	public void render () {
		move();

		time += Gdx.graphics.getDeltaTime();

		TextureRegion img;

		if (x > 0) {
			img = walk.getKeyFrame(time, true);
		}

		else if (yv != 0) {
			img = walkUp.getKeyFrame(time, true);

		}

		else if (yv > 0) {
			img = walkDown.getKeyFrame(time, true);
		}

		else {
			img = down;
		}

		Gdx.gl.glClearColor(0.5f, 0.5f, 6, 3);    //window color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		if (faceRight) {
			batch.draw(img, x, y, WIDTH * 3, HEIGHT * 3);  //dimensions of character
		}


		else {
			batch.draw(img, x + WIDTH * 3, y, WIDTH * -3, HEIGHT * 3);
		}
		batch.end();
	}

	public void move() {

			//while (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			//	SPEED_INCREASE = MAX_VELOCITY;
			//}

			if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
				yv = MAX_VELOCITY;
				faceUp = true;
			}
			else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
				yv = -MAX_VELOCITY;
				faceUp = false;
			}
			else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				xv = MAX_VELOCITY;
				faceRight = true;
			} else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				xv = -MAX_VELOCITY;
				faceRight = false;
			}






		float delta = Gdx.graphics.getDeltaTime();
		y += yv * delta;
		x += xv * delta;

		yv = decelerate(yv);
		xv = decelerate(xv);


	}

	public float decelerate(float velocity) {
		velocity = DECELERATION;
		if (Math.abs(velocity) < 1) {
			velocity = 0;
		}
		return velocity;
	}
}
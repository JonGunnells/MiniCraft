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
	TextureRegion curDir, up, down, left, right, stand;
	float x, y, xv, yv;
	float time;
	Animation walk;
	static final float SPEED_INCREASE = 3.5f;
	static final float MAX_VELOCITY = 150;
	static final float DECELERATION = 0.5f;


	static final int WIDTH = 16;
	static final int HEIGHT = 16;
	static final int DRAW_WIDTH = WIDTH*4;
	static final int DRAW_HEIGHT = HEIGHT*4;


	@Override
	public void create () {
		batch = new SpriteBatch();
		Texture tiles = new Texture("tiles.png");
		TextureRegion[][] grid = TextureRegion.split(tiles, WIDTH, HEIGHT);
		down = grid[6][0];
		up = grid[6][1];
		stand = grid[6][2];
		right = grid[6][3];
		left = new TextureRegion(right);
		left.flip(true, false);
		walk = new Animation(0.2f, grid[6][0],grid[6][1], grid[6][2], grid[6][3]);

	}

	@Override
	public void render () {
		move();

		time += Gdx.graphics.getDeltaTime();

		if (x > 800) {
			x = 0;
		}

		else if (x < -0) {
			x = 800;
		}
		else if (y > 600) {
			y = 0;
		}

		else if (y < -0) {
			y = 600;
		}

		Gdx.gl.glClearColor(0.5f, 0.5f, 6, 3);		//window color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(curDir, x, y, DRAW_WIDTH, DRAW_HEIGHT);	//dimensions of character
		batch.end();
	}

	public void move() {
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			yv = MAX_VELOCITY;
			curDir = up;

			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				yv = speedIncrease(yv);
			}
		}

		else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			yv = -MAX_VELOCITY;
			curDir = down;

			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				yv = speedIncrease(yv);
			}
		}

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			xv = -MAX_VELOCITY;
			curDir = left;

			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				xv = speedIncrease(xv);
			}
		}

		else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			xv = MAX_VELOCITY;
			curDir = right;

			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				xv = speedIncrease(xv);
			}
		}
		if (xv == 0 && yv == 0) {
			curDir = stand;
		}

		float delta = Gdx.graphics.getDeltaTime();
		y += yv * delta;
		x += xv * delta;

		yv = decelerate(yv);
		xv = decelerate(xv);
	}

	public float decelerate(float velocity) {
		velocity *= DECELERATION;
		if (Math.abs(velocity) < 1) {
			velocity = 0;
		}
		return velocity;
	}

	public float speedIncrease (float velocity) {
		velocity *= SPEED_INCREASE;
		return velocity;
	}
}

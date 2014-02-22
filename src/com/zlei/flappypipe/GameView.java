/**
 * GameView
 * Probably the most important class for the game
 * 
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package com.zlei.flappypipe;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

import com.zlei.flappypipe.R;

public class GameView extends SurfaceView implements Runnable, OnTouchListener {
	public static final long UPDATE_INTERVAL = 1;
	public int numOfPigs = 10, numOfLearners = 500;
	private Thread thread;
	private SurfaceHolder holder;
	private int obstacleY = -1;
	private boolean deleteAll = false;
	volatile private boolean shouldRun = false;
	private int points = 0;

	private Game game;
	private Background bg;
	private Frontground fg;
	private List<PlayableCharacter> players = new ArrayList<PlayableCharacter>();
	private List<Obstacle> obstacles = new ArrayList<Obstacle>();
	private List<PowerUp> powerUps = new ArrayList<PowerUp>();

	public GameView(Context context, boolean playable) {
		super(context);
		this.game = (Game) context;

		holder = getHolder();

		for (int i = 0; i < numOfLearners; i++)
			players.add(new Chrome(this, game));

		bg = new Background(this, game);
		fg = new Frontground(this, game);

		setOnTouchListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (shouldRun == false) {
				shouldRun = true;
				points = 0;
				deleteAll = true;
			}
			obstacleY = (int) event.getY();
			// this.player.onTap();
		}

		return true;
	}

	/**
	 * The thread runs this method
	 */
	public void run() {
		draw();

		while (true) {
			if (deleteAll) {
				obstacles.removeAll(obstacles);
				players.removeAll(players);

				for (int i = 0; i < numOfPigs; i++) {
					players.add(new Chrome(this, game));
				}

				deleteAll = false;
			}

			checkPasses();
			checkOutOfRange();
			checkCollision();
			createObstacle();
			QLearning.learnAndPerform(players, obstacles);
			move();

			if (shouldRun) {
				draw();

				try {
					Thread.sleep(UPDATE_INTERVAL);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Joins the thread
	 */
	public void pause() {
		while (thread != null) {
			try {
				thread.join();
				break;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		thread = null;
	}

	/**
	 * Activates the thread. But shouldRun will be false. This means the canvas
	 * will be drawn once. If this is the first start of a game, the tutorial
	 * will be drawn.
	 */
	public void resume() {
		pause(); // make sure the old thread isn't running
		thread = new Thread(this);
		thread.start();
	}

	/**
	 * Start the thread and let it run.
	 */
	public void resumeAndKeepRunning() {
		thread = new Thread(this);
		thread.start();
	}

	/**
	 * Draws all gameobjects on the surface
	 */
	private void draw() {
		while (!holder.getSurface().isValid()) {/* wait */
		}
		Canvas canvas = holder.lockCanvas();
		drawCanvas(canvas);
		holder.unlockCanvasAndPost(canvas);
	}

	/**
	 * Draws all gameobjects on the canvas
	 * 
	 * @param canvas
	 */
	private void drawCanvas(Canvas canvas) {
		bg.draw(canvas);

		for (Obstacle r : obstacles)
			r.draw(canvas);

		for (PlayableCharacter player : players)
			player.draw(canvas);

		fg.draw(canvas);

		// Score Text
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(getScoreTextMetrics());
		canvas.drawText(
				game.getResources().getString(R.string.onscreen_score_text)
						+ points, getScoreTextMetrics(), getScoreTextMetrics(),
				paint);
	}

	/**
	 * Let the player fall to the ground
	 */
	private void playerDeadFall(PlayableCharacter player) {
		player.dead();
	}

	/**
	 * Checks whether a obstacle is passed.
	 */
	private void checkPasses() {
		for (Obstacle o : obstacles) {
			if (o.isPassed()) {
				if (!o.isAlreadyPassed) {
					o.onPass();
					points++;
				}
			}
		}
	}

	/**
	 * Checks whether the obstacles or powerUps are out of range and deletes
	 * them
	 */
	private void checkOutOfRange() {
		for (int i = 0; i < obstacles.size(); i++) {
			if (this.obstacles.get(i).isOutOfRange()) {
				this.obstacles.remove(i);
				i--;
			}
		}
		for (int i = 0; i < powerUps.size(); i++) {
			if (this.powerUps.get(i).isOutOfRange()) {
				this.powerUps.remove(i);
				i--;
			}
		}
	}

	/**
	 * Checks collisions and performs the action
	 */
	private void checkCollision() {
		boolean allDead = true;

		for (Obstacle o : obstacles) {
			for (int i = players.size() - 1; i >= 0; i--) {
				PlayableCharacter player = players.get(i);
				if (!player.isDead && o.isColliding(player)) {
					o.onCollision();
					player.isDead = true;
					playerDeadFall(player);
				}
			}
		}

		for (int i = players.size() - 1; i >= 0; i--) {
			PlayableCharacter player = players.get(i);

			if (!player.isTouchingGround())
				allDead = false;

			if (!player.isDead) {
				if (player.isTouchingEdge()) {
					player.isDead = true;
					playerDeadFall(player);
				}
			}
		}

		if (allDead) {
			if (true) {
				obstacles.removeAll(obstacles);
				players.removeAll(players);

				for (int i = 0; i < numOfLearners; i++) {
					players.add(new Chrome(this, game));
				}

				shouldRun = false;
			}
		}
	}

	/**
	 * if no obstacle is present a new one is created
	 */
	private void createObstacle() {
		if (obstacles.size() < 1) {
			obstacles.add(new Obstacle(this, game, obstacleY));
			obstacleY = -1;
		}

		else if (obstacles.size() == 1) {
			Obstacle obs = obstacles.get(0);

			if (obs.pipe_up.getX() < 5 * getWidth() / 11) {
				obstacles.add(new Obstacle(this, game, obstacleY));
				obstacleY = -1;
			}
		}
	}

	/**
	 * Update sprite movements
	 */
	private void move() {
		for (Obstacle o : obstacles) {
			o.setSpeedX(-getSpeedX());
			o.move();
		}

		bg.setSpeedX(-getSpeedX() / 2);
		bg.move();

		fg.setSpeedX(-getSpeedX() * 4 / 3);
		fg.move();

		for (PlayableCharacter player : players) {
			if (!player.isTouchingGround())
				player.move();
		}
	}

	/**
	 * return the speed of the obstacles/cow
	 */
	public int getSpeedX() {
		// 16 @ 720x1280 px
		int speedDefault = this.getWidth() / 45;
		// 1,2 every 4 points @ 720x1280 px
		// int speedIncrease = (int) (this.getWidth() / 600f *
		// (game.accomplishmentBox.points / 4));

		int speed = speedDefault;

		if (speed > 2 * speedDefault) {
			return 2 * speedDefault;
		} else {
			return speed;
		}
	}

	/**
	 * Let's the player fall down dead, makes sure the runcycle stops and
	 * invokes the next method for the dialog and stuff.
	 */
	public void gameOver() {
		this.shouldRun = false;
		game.gameOver();
	}

	/**
	 * A value for the position and size of the onScreen score Text
	 */
	public int getScoreTextMetrics() {
		// 64 @ 720x1280 px
		return this.getHeight() / 20;
	}

	public PlayableCharacter getPlayer() {
		return this.players.get(0);
	}

	public Game getGame() {
		return this.game;
	}
}
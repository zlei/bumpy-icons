/**
 * The Game
 * 
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package com.zlei.flappypipe;

import com.zlei.flappypipe.R;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;

public class Game extends Activity {
	public static SoundPool soundPool = new SoundPool(5,
			AudioManager.STREAM_MUSIC, 0);
	public static MediaPlayer musicPlayer = null;
	public boolean musicShouldPlay = false;
	private MyHandler handler;
	GameView view;
	GameOverDialog gameOverDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = new GameView(this, true);
		gameOverDialog = new GameOverDialog(this);
		handler = new MyHandler(this);
		setLayouts();
	}

	public void initMusicPlayer() {
		if (musicPlayer == null) {
			// to avoid unnecessary reinitialisation
			// musicPlayer = MediaPlayer.create(this, R.raw.nyan_cat_theme);
			// musicPlayer.setLooping(true);
			// musicPlayer.setVolume(MainActivity.volume, MainActivity.volume);
		}
		// musicPlayer.seekTo(0); // Reset song to position 0
	}

	/**
	 * Creates the layout containing a layout for ads and the GameView
	 */
	private void setLayouts() {
		LinearLayout mainLayout = new LinearLayout(this);
		mainLayout.setOrientation(LinearLayout.VERTICAL);
		mainLayout.addView(view);
		setContentView(mainLayout);
	}

	@Override
	protected void onPause() {
		view.pause();
		if (musicPlayer.isPlaying()) {
			musicPlayer.pause();
		}
		super.onPause();
	}

	/**
	 * Resumes the view (but waits the view waits for a tap) and starts the
	 * music if it should be running. Also checks whether the Google Play
	 * Services are available.
	 */
	@Override
	protected void onResume() {
		view.resumeAndKeepRunning();
		super.onResume();
	}

	/**
	 * Sends the handler the command to show the GameOverDialog. Because it
	 * needs an UI thread.
	 */
	public void gameOver() {
		handler.sendMessage(Message.obtain(handler, 0));
	}

	/**
	 * Shows the GameOverDialog when a message with code 0 is received.
	 */
	static class MyHandler extends Handler {
		private Game game;

		public MyHandler(Game game) {
			this.game = game;
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				game.gameOverDialog.init();
				game.gameOverDialog.show();
				break;
			}
		}
	}
}

/**
 * Main Activity / Splashscreen with buttons.
 * 
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package com.zlei.flappypipe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends Activity {

	/** Name of the SharedPreference that saves the medals */
	public static final String medaille_save = "medaille_save";

	/** Key that saves the medal */
	public static final String medaille_key = "medaille_key";

	public static final float DEFAULT_VOLUME = 0.3f;

	/** Volume for sound and music */
	public static float volume = DEFAULT_VOLUME;

	private String PATH;
	// read from file
	public static String[] s;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		PATH = Environment.getExternalStorageDirectory().getPath() + "/flappy";
		this.loadLearnFile();
		ImageView image_logo = (ImageView) findViewById(R.id.image_logo);
		((ImageButton) findViewById(R.id.play_button))
				.setImageBitmap(Sprite
						.createBitmap(
								getResources().getDrawable(
										R.drawable.play_flyer), this));
		((ImageButton) findViewById(R.id.play_button))
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent i = new Intent(MainActivity.this, Game.class);
						i.putExtra("mode", "play");
						startActivity(i);
					}
				});

		((ImageButton) findViewById(R.id.learn_button)).setImageBitmap(Sprite
				.createBitmap(getResources().getDrawable(R.drawable.play_pipe),
						this));
		((ImageButton) findViewById(R.id.learn_button))
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent i = new Intent(MainActivity.this, Game.class);
						i.putExtra("mode", "learn");
						startActivity(i);
					}
				});
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Intent i = new Intent(MainActivity.this, Game.class);
		startActivity(i);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private boolean loadLearnFile() {
		try {
			File f = new File(PATH + "/learn");
			FileInputStream is = new FileInputStream(f);
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			String str = new String(buffer);
			// each cell
			String s[] = str.split("\n");
			s[0].replace("\"", "");
			s[s.length - 1].replace("\"", "");
			// System.out.println("finished!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	private boolean updateLearnFile() {
		File logFile = new File(PATH);
		logFile.mkdirs();

		File learnFile = new File(PATH + "/learn");
		try {
			// learnFile.delete();
			learnFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			FileWriter file = new FileWriter(PATH + "/learn");
			for (int i = 0; i < 100; i++) {
				for (int j = 0; j < 100; j++) {
					for (int k = 0; k < 2; k++) {
						// System.out.println(QLearning.Q[i][j][k]);
						file.write(String.valueOf(QLearning.Q[i][j][k]) + "\n");
					}
				}

			}
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
}
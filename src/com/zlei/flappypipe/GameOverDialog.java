package com.zlei.flappypipe;

import com.zlei.flappypipe.R;

import android.app.Dialog; 
import android.view.View;
import android.widget.Button;

public class GameOverDialog extends Dialog {
	private Game game;

	public GameOverDialog(Game game) {
		super(game);
		this.game = game;
		this.setContentView(R.layout.gameover);
		this.setCancelable(false);
	}

	public void init() { 
		Button okButton = (Button) findViewById(R.id.b_ok);
		okButton.setOnClickListener(new View.OnClickListener() {

			@Override public void onClick(View v) { 
				dismiss(); 
				game.finish(); 
			}
		});
	}
}

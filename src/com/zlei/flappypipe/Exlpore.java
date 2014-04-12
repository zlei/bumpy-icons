package com.zlei.flappypipe;

import android.graphics.Bitmap;

public class Exlpore extends PlayableCharacter {
	public static Bitmap globalBitmap;

	public Exlpore(GameView view, Game game) {
		super(view, game);

		bumprate = 0.5;
		gravity = 400;

		if (globalBitmap == null) {
			globalBitmap = createBitmap(game.getResources().getDrawable(
<<<<<<< HEAD
					R.drawable.ieie));
		}

		this.bitmap = globalBitmap;
=======
					R.drawable.ieie));  

		if (null == Q)
			Q = new double[Q_witdh][Q_height][2];
		this.bitmap = globalBitmap; 
>>>>>>> FETCH_HEAD
		this.width = this.bitmap.getWidth();
		this.height = this.bitmap.getHeight();
		int rand = (int) (Math.random() * height * 2);
		this.x = this.view.getWidth() / 6 + rand;
		rand = (int) (Math.random() * height * 2);
		this.y = game.getResources().getDisplayMetrics().heightPixels / 2
				+ rand;
	}

<<<<<<< HEAD
=======
	public double[][][] getQ() {
		return Q;
	} 
	
>>>>>>> FETCH_HEAD
	@Override
	public void onTap() {
		super.onTap();
	}
}

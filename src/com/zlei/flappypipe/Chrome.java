package com.zlei.flappypipe;

import com.zlei.flappypipe.R;

import android.graphics.Bitmap;

public class Chrome extends PlayableCharacter {
<<<<<<< HEAD
	public static Bitmap globalBitmap;
=======
	public static Bitmap globalBitmap = null;
	public static double [][][] Q;
>>>>>>> FETCH_HEAD

	public Chrome(GameView view, Game game) {
		super(view, game);

		if (globalBitmap == null) {
			globalBitmap = createBitmap(game.getResources().getDrawable(
<<<<<<< HEAD
					R.drawable.chrome));
		}

		this.bitmap = globalBitmap;
=======
					R.drawable.chrome));  
		if (null == Q)
			Q = new double[Q_witdh][Q_height][2];
		this.bitmap = globalBitmap; 
>>>>>>> FETCH_HEAD
		this.width = this.bitmap.getWidth();
		this.height = this.bitmap.getHeight();
		int rand = (int) (Math.random() * height * 2);
		this.x = this.view.getWidth() / 6 + rand;
		rand = (int) (Math.random() * height * 2);
<<<<<<< HEAD
		this.y = game.getResources().getDisplayMetrics().heightPixels / 2
				+ rand;
	}
=======
		this.y = game.getResources().getDisplayMetrics().heightPixels / 2 + rand;
	}

	public double[][][] getQ() {
		return Q;
	} 
>>>>>>> FETCH_HEAD

	@Override
	public void onTap() {
		super.onTap();
	}
}

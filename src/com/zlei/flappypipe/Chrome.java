package com.zlei.flappypipe;

import com.zlei.flappypipe.R;

import android.graphics.Bitmap;

public class Chrome extends PlayableCharacter {
	public static Bitmap globalBitmap = null;
	public static double[][][] Q = null;

	public Chrome(GameView view, Game game) {
		super(view, game);  

		if (globalBitmap == null) 
			globalBitmap = createBitmap(game.getResources().getDrawable(
					R.drawable.chrome)); 
		
		if (Q == null) 
			initQ();

		this.bitmap = globalBitmap; 
		this.width = this.bitmap.getWidth();
		this.height = this.bitmap.getHeight();
		int rand = (int) (Math.random() * height * 2);
		this.x = this.view.getWidth() / 6 + rand;
		rand = (int) (Math.random() * height * 2);
		this.y = game.getResources().getDisplayMetrics().heightPixels / 2 + rand;
	}

	public double[][][] getQ() {
		return Q;
	}
	
	public static void initQ() { 
		int width = 500; 
		Q = new double[width][width][2];
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < width; j++) {
				for (int k = 0; k < 2; k++) {
					Q[i][j][k] = 0.0;
				}
			}
		}
	}

	@Override
	public void onTap() {
		super.onTap();
	}
}

package com.zlei.flappypipe;

import android.graphics.Bitmap;

public class Exlpore extends PlayableCharacter {
	public static Bitmap globalBitmap;
	public static double[][][] Q;

	public Exlpore(GameView view, Game game) {
		super(view, game);   
 
		bumprate = 0.5; 
		gravity = 400;

		if (globalBitmap == null) 
			globalBitmap = createBitmap(game.getResources().getDrawable(
					R.drawable.ieie)); 
		
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

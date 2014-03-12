package com.zlei.flappypipe;

import android.graphics.Bitmap;

public class Safari extends PlayableCharacter {
	public static Bitmap globalBitmap;
	public static double[][][] Q;

	public Safari(GameView view, Game game) {
		super(view, game);  
	
		bumprate = 0.77;

		if (globalBitmap == null) 
			globalBitmap = createBitmap(game.getResources().getDrawable(
					R.drawable.safari));  

		if (null == Q)
			Q = new double[Q_witdh][Q_height][2];
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
	 
	@Override
	public void onTap() {
		super.onTap();
	}
}
package com.example.model;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class ScoreRunnable implements Runnable {
	private float score;
	TextView scoreText;
	private static Handler visibilityHandler = new Handler();
	private Runnable visibilityRunnable = new Runnable(){
		@Override
    	public void run() {
    		removeText();
    	}
	};
	public ScoreRunnable(TextView _scoreText)
	{
		this.scoreText = _scoreText;
	}
	public void setScore(float _score) { score = _score; }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		scoreText.setVisibility(View.VISIBLE);
		scoreText.setText("Score: " + String.valueOf(score));
		visibilityHandler.postDelayed(visibilityRunnable, 1000);
		
	}
	private void removeText()
	{
		if(scoreText.getVisibility() != View.INVISIBLE)
		{
			scoreText.setVisibility(View.INVISIBLE);
		}
	}

}

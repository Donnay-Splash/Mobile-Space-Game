package com.example.controller;


import org.json.JSONException;

import com.example.model.HighScore;
import com.example.model.HighScoreList;
import com.example.model.Player;
import com.example.model.ScoreRunnable;
import com.example.view.BounceView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameActivity extends Activity implements GameEventListener{
	private Player player;
	private int mScrWidth;
	private int mScrHeight;
	private SensorManager mSensorManager;
	private HighScoreList scoreList;
	private static Handler visibilityHandler;
	private ScoreRunnable setTextRunnable;
	BounceView bounceView;
	FrameLayout game;
	RelativeLayout gameWidgets;
	RelativeLayout gameText;
	
	private final Button.OnClickListener OnClick = new Button.OnClickListener()
	{
		@Override
		public void onClick(View v) 
		{
			switch(v.getId())
			{
			case 1:
				bounceView.reset();
				break;
			case 2:
				finish();
				break;				
			}
			
			
		}
	};
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        DisplayMetrics displaymetrics = new DisplayMetrics();
		super.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		mScrWidth = displaymetrics.widthPixels;
		mScrHeight = displaymetrics.heightPixels;
        
		//Create sensor manager
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
		
		scoreList = new HighScoreList();
		player = new Player(mScrWidth/2, mScrHeight - (mScrHeight / 5), Color.GREEN, 35, this);
        // set out the seperate layouts
		game = new FrameLayout(this);
        bounceView = new BounceView(this, player, this);
        gameWidgets = new RelativeLayout(this);
        gameText = new RelativeLayout(this);
        Button restartGameButton = new Button(this);
        Button quitGameButton = new Button(this);
        TextView scoreText = new TextView(this);
        
        visibilityHandler = new Handler();
        setTextRunnable = new ScoreRunnable(scoreText);
        
        
        gameWidgets.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        gameText.setGravity(Gravity.CENTER);
       
        restartGameButton.setText("Retry");
        restartGameButton.setId(1);
        restartGameButton.setOnClickListener(OnClick);
        
        quitGameButton.setText("Quit");
        quitGameButton.setId(2);
        quitGameButton.setOnClickListener(OnClick);
        
        scoreText.setTextSize(40.0f);
        scoreText.setTextColor(Color.WHITE);
        
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.RIGHT_OF, restartGameButton.getId());
        
        
        gameWidgets.addView(restartGameButton);
        gameWidgets.addView(quitGameButton, layoutParams);
        gameText.addView(scoreText);
        game.addView(bounceView);
        game.addView(gameText);
        game.addView(gameWidgets);
             
        
        setContentView(game);
    }
	
	private final SensorEventListener mSensorListener = new SensorEventListener() {
	   	 
	   	 public void onSensorChanged(SensorEvent event)
	   	 {
	   		//set ball velocity based on phone tilt (ignore Z axis)
	        player.setVelocity(-event.values[0], 0.0f);
	   	 }
	   	 
	   	 public void onAccuracyChanged(Sensor sensor, int accuracy) {
	   	    }
	   		 
	};
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				player.setFired(true);
		}
		return super.onTouchEvent(event);
	}
  	
  	@Override
  	public void onPause()
  	{
  		super.onPause();
  		bounceView.pauseGame();
  	}
  	
  	@Override
  	public void onResume()
  	{
  		super.onResume();
  		bounceView.resumeGame();
  	}

	@Override
	public void onDestroy() {
		
		super.onDestroy();
	}

	@Override
	public void onStop() {
	
		super.onStop();
	}
	

	@Override
	public void onPlayerDeath(Player player) {
		//Takes in the values for score, total time survived and number of asteroids destroyed to add to the highScore list
		HighScore highScore = new HighScore(player.getScore(), player.getTime(), player.getDestroyed());
		try {
			scoreList.addScore(this, highScore);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setTextRunnable.setScore(player.getScore());
		visibilityHandler.post(setTextRunnable);
	}

}

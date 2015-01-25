package com.example.controller;

import com.example.bouncesurface.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private Button startGameBtn;
	private Button highScoreBtn;
	private Button optionsBtn;
	private final Button.OnClickListener btnOnClick = new Button.OnClickListener()
	{
		@Override
		public void onClick(View v) 
		{
			switch(v.getId())
			{
				case R.id.start_button:
					Intent startGame = new Intent(MainActivity.this, GameActivity.class);
					startActivity(startGame);
					break;
				case R.id.highscore_button:
					Intent openHighScores = new Intent(MainActivity.this, HighScoreActivity.class);
					startActivity(openHighScores);
					break;
				case R.id.options_button:
					Intent openOptions = new Intent(MainActivity.this, OptionsActivity.class);
					startActivity(openOptions);
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
        setContentView(R.layout.activity_main);
        startGameBtn = (Button) findViewById(R.id.start_button);
        startGameBtn.setOnClickListener(btnOnClick);
        
        highScoreBtn = (Button) findViewById(R.id.highscore_button);
        highScoreBtn.setOnClickListener(btnOnClick);
        
        optionsBtn = (Button) findViewById(R.id.options_button);
        optionsBtn.setOnClickListener(btnOnClick);
        
        checkSharedPrefs();
    }
  	
  	@Override
  	public void onPause()
  	{
  		super.onPause();
  	}
  	
  	@Override
  	public void onResume()
  	{
  		super.onResume();
  	}

	@Override
	public void onDestroy() {
		
		super.onDestroy();
	}

	@Override
	public void onStop() {
	
		super.onStop();
	}
	private void checkSharedPrefs()
	{
		SharedPreferences sp = getSharedPreferences("ASTEROID_DRAWABLE", Activity.MODE_PRIVATE);
	    int resource = sp.getInt("current_colour", -1);
	    if(resource == -1)
	    {
	    	SharedPreferences.Editor editor = sp.edit();
	    	editor.putInt("current_colour", R.drawable.asteroid_red);
	    	resource = R.drawable.asteroid_red;
	    	editor.commit();
	    }
	}


}

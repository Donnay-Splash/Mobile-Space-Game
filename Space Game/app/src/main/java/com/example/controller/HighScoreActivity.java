package com.example.controller;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONException;

import com.example.bouncesurface.R;
import com.example.model.HighScoreArrayAdapter;
import com.example.model.HighScoreList;
import com.example.model.HighScore;

import android.app.ActionBar;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class HighScoreActivity extends ListActivity 
{
	HighScoreList highScoreList = new HighScoreList();
	ArrayList<HighScore> highScores = null;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.list_activity_view);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        try 
        {
        	highScores = highScoreList.getScores(this);
		} 
        catch (JSONException e) 
        {
			e.printStackTrace();
		}
        if(highScores == null) // If the 
        {
        	HighScore emptyScore = new HighScore(0,0,0);
        	try
        	{
				highScoreList.addScore(this, emptyScore);
			}
        	catch (JSONException e) {
				e.printStackTrace();
			}
        }
        Collections.sort(highScores, HighScore.DESCENDING_COMPARATOR);
        final HighScoreArrayAdapter adapter = new HighScoreArrayAdapter(this, R.layout.highscore_adapter, highScores);
        setListAdapter(adapter);
    }
    
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	finish();
    	return true;
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
}

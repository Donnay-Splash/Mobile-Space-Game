package com.example.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class HighScoreList{
	public static final String PREFS_NAME = "HIGHSCORE_LIST";
	public static final String HIGHSCORES = "HighScoreData";
	
	private final int maxScores = 10;
	public HighScoreList()
	{
	}
	
	public void saveScores(Context context, List<HighScore> highScores) throws JSONException
	{
		SharedPreferences  settings;
		Editor editor;
		
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		editor = settings.edit();
		
		JSONArray jsonArr = new JSONArray();
		for(HighScore hs : highScores)
		{
			JSONObject hsObj = new JSONObject();
			hsObj.put("score", hs.getScore());
			hsObj.put("time", hs.getTime());
			hsObj.put("destroyed", hs.getAsteroidsDestroyed());
			jsonArr.put(hsObj);
		}
		
		editor.putString(HIGHSCORES, jsonArr.toString());
		editor.commit();
	}
	
	public void addScore(Context context, HighScore score) throws JSONException
	{
		List<HighScore> highScores = getScores(context);
		
		if(highScores == null)
		{
			highScores = new ArrayList<HighScore>();
		}
		if(highScores.size() < maxScores)
		{
			highScores.add(score);
		}
		else if(checkScores(context, score))
		{
			highScores = dropLowest(context);
			highScores.add(score);
		}
		saveScores(context, highScores);
	}
	
	public void removeScore(Context context, HighScore score) throws JSONException
	{
		ArrayList<HighScore> highScores = getScores(context);
		if(highScores != null)
		{
			highScores.remove(score);
			saveScores(context, highScores);
		}
	}
	
	public ArrayList<HighScore> getScores(Context context) throws JSONException
	{
		SharedPreferences settings;
		ArrayList<HighScore> highScores = new ArrayList<HighScore>();
		
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		
		if(settings.contains(HIGHSCORES));
		{
			String jsonHighScores = settings.getString(HIGHSCORES, null);
			if(jsonHighScores == null)
			{
				Log.d("Json error", "jsonHighScores is null"); // if jsonHighscores is null no shared prefs have yet been allocated
				return highScores; // or an error has occured return empty list
			}
			JSONArray jsonArr = new JSONArray(jsonHighScores);
			for(int i = 0; i < jsonArr.length(); i++)
			{
				JSONObject highScoreObj = jsonArr.getJSONObject(i);
				
				int score = highScoreObj.getInt("score");
				double time = highScoreObj.getDouble("time");
				int destroyed = highScoreObj.getInt("destroyed");
				
				HighScore highScore = new HighScore(score, time, destroyed);
				highScores.add(highScore);
			}
		}
		return highScores;
	}
	
	public ArrayList<HighScore> dropLowest(Context context) throws JSONException
	{
		
		ArrayList<HighScore> highScores = getScores(context);
		
		if(highScores != null)
		{
			Collections.sort(highScores, HighScore.DESCENDING_COMPARATOR); // High scores are ordered highest to lowest
			while(highScores.size() >= maxScores) // just incase an error has occurred and there are more scores than there should be
			{
				highScores.remove(highScores.size()-1); // lowest score in the list is removed the index is found by taking 1 away from the size
				//since indexes start at 0 and not 1
			}
				
		}
		return highScores; // returns the new list of scores
	}
	public boolean checkScores(Context context, HighScore newScore) throws JSONException
	{
		ArrayList<HighScore> highScores = getScores(context);
		for(int i = 0; i < highScores.size(); i++)
		{
			if(newScore.getScore() > highScores.get(i).getScore())
			{
				return true;
			}
		}
		return false;
	}

}

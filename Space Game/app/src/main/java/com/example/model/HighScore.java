package com.example.model;

import java.util.Comparator;

public class HighScore implements Comparable<HighScore>{
	
	private int score;
	private double timeSurvived;
	private int asteroidsDestroyed;
	
	public static final Comparator<HighScore> DESCENDING_COMPARATOR = new Comparator<HighScore>(){
			public int compare(HighScore score1, HighScore score2)
			{
				return score2.score - score1.score;
			}
		
	};
	public HighScore(int _score, double _timeSurvived, int _asteroidsDestroyed)
	{
		this.score = _score;
		this.timeSurvived = _timeSurvived;
		this.asteroidsDestroyed = _asteroidsDestroyed;
	}
	
	public int getScore(){ return score; }
	public double getTime() { return timeSurvived; }
	public int getAsteroidsDestroyed(){ return asteroidsDestroyed; }

	@Override
	public int compareTo(HighScore another) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}

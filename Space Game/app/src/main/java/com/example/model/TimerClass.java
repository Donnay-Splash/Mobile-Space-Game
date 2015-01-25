package com.example.model;

public class TimerClass {
	private long spawnWait;
	private long startWait;
	private long minWait;
	private long scoreWait;
	private long scoreTimer;
	private long prevSpawn;
	private long prevShot;
	private long startTime;
	
	private boolean takeShot;
	private boolean updateScore;
	private boolean spawnHazard;
	
	public TimerClass()
	{
	}
	
	public void init(long _waitTime, long _minWait, long _scoreWait)
	{
		spawnWait = _waitTime;
		startWait = spawnWait;
		minWait = _minWait;
		scoreWait = _scoreWait;
	}
	
	public void start()
	{
		startTime = System.currentTimeMillis();
		scoreTimer = System.currentTimeMillis() + scoreWait;
		prevSpawn = 0;
		prevShot = 0;
		spawnWait = startWait;
		takeShot = false;
		updateScore = false;
		spawnHazard = false;
	}
	
	public void update()
	{
		if(prevShot < System.currentTimeMillis())
		{
			takeShot = true;
		}
		if(prevSpawn < System.currentTimeMillis())
		{
			spawnHazard = true;
		}
		if(scoreTimer < System.currentTimeMillis())
		{
			updateScore = true;
		}
	}
	
	public void resetScore()
	{
		updateScore = false;
		scoreTimer = System.currentTimeMillis() + scoreWait;
	}
	public void resetShot(long fireRate)
	{
		takeShot = false;
		prevShot = System.currentTimeMillis() + fireRate;
	}
	public void resetSpawn()
	{
		spawnHazard = false;
		prevSpawn = System.currentTimeMillis() + spawnWait;
		if(spawnWait > minWait)
		{
			spawnWait -= 20;
		}
	}
	public double getTotalTime()
	{
		return (System.currentTimeMillis() - startTime) / 1000;
	}
	
	public boolean TakeShot(){return takeShot;}
	public boolean UpdateScore(){return updateScore;}
	public boolean SpawnHazard(){return spawnHazard;}

}

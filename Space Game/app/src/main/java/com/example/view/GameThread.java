package com.example.view;

import java.util.ArrayList;
import java.util.List;

import com.example.bouncesurface.R;
import com.example.controller.GameEventListener;
import com.example.model.Bullet;
import com.example.model.Hazard;
import com.example.model.Player;
import com.example.model.TimerClass;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;



public class GameThread extends Thread {
	
	// Surface holder that can access the physical surface
	private SurfaceHolder surfaceHolder;
	// The actual view that handles inputs
	// and draws to the surface
	private GameEventListener gameEventListener;
	private Player player;
	private Bitmap background;
	private Bitmap[] asteroidMaps;
	private Paint paint;
	private float textSize;
	private float textX, textY;
	private DisplayMetrics metrics;
	
	//Holds all of the bullets in the scene
	private List<Bullet> bullets = new ArrayList<Bullet>();
	private List<Hazard> hazards = new ArrayList<Hazard>();
			
	// flag to hold game state 
	private boolean running;
	private Context context;
	
	private TimerClass timer;
	//current canvas we are using
	private Canvas canvas;
	public void setRunning(boolean running) {
		this.running = running;
	}

	//constructor for the thread
	public GameThread(SurfaceHolder surfaceHolder, Player _player, Context _context, GameEventListener _eventListener) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.player = _player;
		this.context = _context;
		this.gameEventListener = _eventListener;
		timer = new TimerClass();
		timer.init(2000, 500, 1000);
		asteroidMaps = new Bitmap[5];
		metrics = context.getResources().getDisplayMetrics();
		textSize = 16.0f * metrics.density;
		textX = 10.0f * metrics.density;
		textY = 450.0f * metrics.density;
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(textSize);
		initBitmaps();
	}

	//run method where the magic happens 
	@Override
	public void run() {
		timer.start();
		while (running) {
			timer.update();
			//retrieve the canvas
			canvas = surfaceHolder.lockCanvas();
			//Clear the screen to white
			canvas.drawBitmap(background, 0.0f, 0.0f, null);
			//if our model exists
			if(player != null){
				//use our model to update its self
				player.update(canvas.getWidth(), canvas.getHeight(), hazards);
				if(player.getFired() && timer.TakeShot())
				{
					addBullet(player.getPos());
					player.setFired(false);
					timer.resetShot(player.getRateOfFire());
				}
				if(!player.isAlive())
				{
					player.setTime(timer.getTotalTime());
					gameEventListener.onPlayerDeath(player);
					reset();
				}
				player.draw(canvas);
			}
			for(int bullet = bullets.size() - 1; bullet >= 0; bullet--)
			{
				Bullet tempBull = bullets.get(bullet);
				tempBull.update(canvas.getWidth(), canvas.getHeight());
				if(tempBull.NeedsDeleted())
					removeBullet(tempBull);
				tempBull.draw(canvas);
			}
			for(int hazard = hazards.size() - 1; hazard >= 0; hazard--)
			{
				Hazard tempHaz = hazards.get(hazard);
				tempHaz.update(bullets);
				if(tempHaz.NeedsDeleted())
					removeHazard(tempHaz);
				tempHaz.draw(canvas, asteroidMaps[tempHaz.calcSize()]);
			}
			if(timer.SpawnHazard())
			{
				addHazard();
				timer.resetSpawn();
			}
			if(timer.UpdateScore())
			{
				player.incrementScore(10);//add 10 to the player score for every second he survives
				timer.resetScore();
			}
			canvas.drawText("Score: " + String.valueOf(player.getScore()), textX, textY, paint);
			//commit to the canvas
			surfaceHolder.unlockCanvasAndPost(canvas);
		}
	}
	
	public void addBullet(android.graphics.PointF pos)
	{
		bullets.add(new Bullet(pos.x, pos.y));
	}
	
	private void removeBullet(Bullet bullet)
	{
		bullets.remove(bullet);
	}
	
	public void addHazard()
	{
		hazards.add(new Hazard(canvas.getWidth(), canvas.getHeight()));
	}
	
	private void removeHazard(Hazard hazard)
	{
		if(hazard.KilledByPlayer())
		{
			player.asteroidKilled(10);
		}
		hazards.remove(hazard);
	}
	private void initBitmaps()
	{
		SharedPreferences sp = context.getSharedPreferences("ASTEROID_DRAWABLE", Activity.MODE_PRIVATE);
	    int resource = sp.getInt("current_colour", -1);
		Bitmap standard = BitmapFactory.decodeResource(context.getResources(), resource);
		asteroidMaps[0] = Bitmap.createScaledBitmap(standard, 100, 100, true);
		asteroidMaps[1]  = Bitmap.createScaledBitmap(standard, 80, 80, true);
		asteroidMaps[2]  = Bitmap.createScaledBitmap(standard, 60, 60, true);
		asteroidMaps[3]  = Bitmap.createScaledBitmap(standard, 40, 40, true);
		asteroidMaps[4] = null;
		background = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
	}
	
	public void reset()
	{
		bullets.clear();
		hazards.clear();
		player.reset();
		timer.start();
	}
}
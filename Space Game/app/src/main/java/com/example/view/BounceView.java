package com.example.view;

import com.example.controller.GameEventListener;
import com.example.model.Player;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;



public class BounceView extends SurfaceView implements SurfaceHolder.Callback {

private GameThread thread;
private Player player;
private Context context;
private GameEventListener gameEventListener;

public BounceView(Context _context, Player _player, GameEventListener _eventListener) {
		super(_context);
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);
		
		//instance of model to hold our game logic
		this.player = _player;
		this.context = _context;
		this.gameEventListener = _eventListener;
		// create the game loop thread
		thread = new GameThread(getHolder(), player, context, gameEventListener);
		
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// at this point the surface is created and
		// we can safely start the game loop
		if(thread.getState() == Thread.State.TERMINATED)
		{
			thread = new GameThread(getHolder(), player, context, gameEventListener);
		}
		thread.setRunning(true);
		thread.start();
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
	
	}
	
	public void pauseGame()
	{
		thread.setRunning(false);
	}
	
	public void resumeGame()
	{
		if(thread.isAlive())
		{
			thread.setRunning(true);
		}
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		//we are using a surface so we don't do anything in the on draw
	}
	
	public void reset() // resets the game
	{
		thread.reset();
	}
	

}

package com.example.model;

import java.util.List;

import com.example.bouncesurface.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

public class Player extends Drawable{

	private android.graphics.PointF position, vel, startPos;
	private int opacity;
	private final int diameter;
	private int colour;
	private ColorFilter filter;
	private boolean fired;
	private boolean alive;
	private int score;
	private double survivedTime;
	private int asteroidsDestroyed;
	private long fireRate;
	private Bitmap bmp;
    
    //construct new ball object
	public Player(float _x, float _y, int _color, int _diameter, Context context) {
		alive = true;
		position = new android.graphics.PointF();
		vel = new android.graphics.PointF();
		startPos = new android.graphics.PointF();
	    position.set(_x, _y);
	    startPos.set(_x, _y);
        colour = _color;
        this.diameter = _diameter;
        bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.flea_sprite);
        fireRate = 300;
        
    }            
        
    /** @return the horizontal coordinate. */
    public android.graphics.PointF getPos() { return position; }
    
    /** @set the current position. */
    public void setPos(float newX, float newY) { position.set(newX, newY); }
    
    public android.graphics.PointF getVelocity() { return vel; }
    
    public void setVelocity(float newX, float newY) 
    { 
    	vel.set(newX, newY); 
    }

    /** @return the colour. */
    public int getColor() { return colour; }

    /** @return the dot diameter. */
    public int getDiameter() { return diameter; }
    
    public boolean isAlive() {return alive;}
    
    public int getScore() { return score; }
    public int getDestroyed() {return asteroidsDestroyed;}
    public double getTime() { return survivedTime; }
    public void incrementScore(int value) { score+=value; }
    public void asteroidKilled(int value) { score+=value; asteroidsDestroyed++;}
    public void setTime(double newTime){ survivedTime = newTime;}
    
    public void update(int width, int height, List<Hazard> hazards)
    {
    	updatePos(width, height);
    	for (int hazard = hazards.size() - 1; hazard >= 0; hazard--)
		{
			collisionDetect(hazards.get(hazard));
		}
    }
    
    private void updatePos(int width, int height)
    {
    	if(position.x + (vel.x + diameter / 2) > width || position.x + (vel.x - diameter / 2) < 0)
    	{
    		vel.x = 0.0f;
    	}
    	position.x += vel.x;
    	position.y += vel.y;
    }
    
    private void collisionDetect(Hazard hazard)
	{
		android.graphics.PointF dist = new android.graphics.PointF();
		dist.set(position.x - hazard.getPosition().x, position.y - hazard.getPosition().y);
		if(dist.length() < (diameter / 2) + (hazard.getDiameter() / 2))
		{
			alive = false;
		}
	}

    //Getters and setters for fired
    public boolean getFired(){return fired;}
    public void setFired(boolean val){fired = val;}
    public long getRateOfFire(){return fireRate;}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		Paint paint = new Paint();
		paint.setColorFilter(filter);
		paint.setAlpha(opacity);
		paint.setColor(colour);
		canvas.drawBitmap(bmp, position.x - bmp.getWidth() / 2, position.y, paint);
	}
	
	public void reset()
	{
		score = 0;
		asteroidsDestroyed = 0;
		survivedTime = 0;
		position.set(startPos);
		alive = true;
	}

	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return PixelFormat.TRANSLUCENT;
	}

	@Override
	public void setAlpha(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		// TODO Auto-generated method stub
		filter = cf;
	}
}

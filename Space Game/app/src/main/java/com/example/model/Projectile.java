package com.example.model;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class Projectile extends Drawable{
	
	protected android.graphics.PointF position = new android.graphics.PointF();
	protected android.graphics.PointF vel = new android.graphics.PointF();
	protected int opacity;
	protected float diameter;
	protected ColorFilter filter;
	protected int colour;
	protected boolean needsDeleted = false;
	
	public Projectile(){}

	@Override
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColorFilter(filter);
		paint.setAlpha(opacity);
		paint.setColor(colour);
		canvas.drawCircle(position.x, position.y, diameter/2, paint);
	}
	
	protected boolean checkBounds(float xMax, float yMax)
	{
		if(position.x < 0.0f || position.x > xMax || position.y < 0.0f || position.y > yMax)
		{
			return true;
		}
		return false;
	}
	
	public boolean NeedsDeleted(){return needsDeleted;}
	public void setNeedsDeleted(boolean val){needsDeleted = val;}
	
	public android.graphics.PointF getPosition(){return position;}
	
	public float getDiameter(){return diameter;}

	@Override
	public void setAlpha(int alpha) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return 0;
	}


}

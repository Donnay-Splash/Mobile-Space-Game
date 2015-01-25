package com.example.model;

import java.util.List;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Hazard extends Projectile {
	Random myRand = new Random();
	private int maxDiameter;
	private int minDiameter;
	private float minVelocity;
	private float health;
	private float rotation;
	private float rotationVel;
	private boolean killedByPlayer;
	private android.graphics.PointF canvasSize = new android.graphics.PointF();

	public Hazard(float width, float height) {
		maxDiameter = 100;
		minDiameter = 20;
		minVelocity = 1.0f;
		canvasSize.set(width, height);
		diameter = minDiameter + myRand.nextInt(maxDiameter - minDiameter);
		position.set((diameter * 0.25f) + myRand.nextInt((int) (width - diameter * 0.5f)), -maxDiameter);
		float velMultiplyer = maxDiameter / diameter;
		health = diameter;
		vel.set(0.0f, minVelocity*velMultiplyer);
		rotation = 0.0f;
		//colour = Color.RED;
		rotationVel = myRand.nextFloat() * 4 - 2;
		killedByPlayer = false;
	}
	
	public void update(List<Bullet> bullets)
	{
		position.offset(vel.x, vel.y);
		for (int bullet = bullets.size() - 1; bullet >= 0; bullet--)
		{
			collisionDetect(bullets.get(bullet));
		}
		if(checkBounds(canvasSize.x, canvasSize.y))
		{
			needsDeleted = true;
		}
		rotation += rotationVel;
	}
	public boolean KilledByPlayer() { return killedByPlayer; }
	
	@Override
	protected boolean checkBounds(float xMax, float yMax)
	{
		if(position.y > (yMax + diameter))
		{
			return true;
		}
		return false;
	}
	
	public void draw(Canvas canvas, Bitmap bmp)
	{
		if(bmp != null)
		{
			canvas.save();
			canvas.rotate(rotation, position.x, position.y);
			canvas.drawBitmap(bmp, position.x - bmp.getWidth() / 2, position.y - bmp.getHeight() / 2, null);
			canvas.restore();
		}
	}
	
	private void collisionDetect(Bullet bullet)
	{
		android.graphics.PointF dist = new android.graphics.PointF();
		dist.set(position.x - bullet.getPosition().x, position.y - bullet.getPosition().y);
		float dir = dist.x / dist.length();
		if(dist.length() < (diameter / 2) + (bullet.getDiameter() / 2))
		{
			float dot = dot(dist, vel);
			rotationVel += 5 * dot * - dir;
			applyDamage(bullet.getDamage());
			bullet.setNeedsDeleted(true);
		}
		
	}
	
	private void applyDamage(float damage)
	{
		health -= damage;
		if(health <= minDiameter)
		{
			needsDeleted = true;
			killedByPlayer = true;
		}
	}
	
	public int calcSize()
	{
		if(health > 80)
		{
			diameter = 100;
			return 0;
		}
		else if(health > 60)
		{
			diameter = 80;
			return 1;
		}
		else if(health > 40)
		{
			diameter = 60;
			return 2;
		}
		else if (health > 20)
		{
			diameter = 40;
			return 3;
		}
		else
		{
			needsDeleted = true;
		}
		return 4;
	}
	
	private float dot(android.graphics.PointF vec1, android.graphics.PointF vec2)
	{
		vec1 = normalise(vec1);
		vec2 = normalise(vec2);
		float dotProduct = (vec1.x * vec2.x) + (vec1.y * vec2.y);
		return dotProduct;
	}
	
	private android.graphics.PointF normalise (android.graphics.PointF vec)
	{
		vec.x/=vec.length();
		vec.y/=vec.length();
		return vec;
	}
}

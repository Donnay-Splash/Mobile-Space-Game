package com.example.model;

import android.graphics.Color;

public class Bullet extends Projectile {
	private float damage;
	
	//Construct new Bullet object
	public Bullet(float _x, float _y)
	{
		position.set(_x, _y);
		damage = 20.0f;
		vel.set(0.0f, -5.0f);
		diameter = 10.0f;
		colour = Color.BLUE;
		opacity = 80;
	}
	
	public void update(int width, int height)
	{
		position.offset(vel.x, vel.y);
		if(checkBounds(width, height))
		{
			needsDeleted = true;
		}
	}
	
	public float getDamage(){return damage;}

}

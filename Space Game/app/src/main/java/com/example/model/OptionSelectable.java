package com.example.model;

public class OptionSelectable {
	
	private String name;
	private int resource;
	
	public OptionSelectable(String _name, int _resource)
	{
		name = _name;
		resource = _resource;
	}
	
	public String getName(){ return name; }
	public int getResource(){ return resource; }
	
	@Override
	public String toString()
	{
		return getName();
	}

}

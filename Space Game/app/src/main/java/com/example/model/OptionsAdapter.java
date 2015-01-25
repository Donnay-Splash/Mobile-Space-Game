package com.example.model;

import java.util.ArrayList;

import com.example.bouncesurface.R;
import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OptionsAdapter extends ArrayAdapter<OptionSelectable>{
	
	ArrayList<OptionSelectable> data;
	Activity context;

	public OptionsAdapter(Activity _context, int resource, ArrayList<OptionSelectable> objects) {
		super(_context, resource, objects);
		this.context = _context;
		this.data = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		return super.getView(position, convertView, parent);
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent)
	{
		View row = convertView;
		if(row == null)
		{
			LayoutInflater inflater = context.getLayoutInflater();
			row = inflater.inflate(R.layout.options_spinner_layout, parent, false);
		}
		
		OptionSelectable info = data.get(position);
		
		if(info != null)
		{
			ImageView icon = (ImageView) row.findViewById(R.id.asteroid_icon);
			TextView name = (TextView) row.findViewById(R.id.asteroid_name);
			
			if(icon != null)
			{
				icon.setBackgroundResource(info.getResource());
			}
			if(name != null)
			{
				name.setText(info.getName());
				name.setGravity(Gravity.CENTER);
			}
		}
		return row;
	}

	

}

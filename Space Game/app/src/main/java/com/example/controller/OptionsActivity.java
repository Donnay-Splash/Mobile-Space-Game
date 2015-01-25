package com.example.controller;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

import com.example.bouncesurface.R;
import com.example.model.OptionSelectable;
import com.example.model.OptionsAdapter;

public class OptionsActivity extends Activity{
	
	ArrayList<OptionSelectable> selectables;
	Spinner spinner;
	SharedPreferences sp;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.options_layout);
	    ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    sp = getSharedPreferences("ASTEROID_DRAWABLE", Activity.MODE_PRIVATE);
	    int resource = sp.getInt("current_colour", -1);
	    if(resource == -1)
	    {
	    	SharedPreferences.Editor editor = sp.edit();
	    	editor.putInt("current_colour", R.drawable.asteroid_red);
	    	resource = R.drawable.asteroid_red;
	    	editor.commit();
	    }
	    selectables = fillSelectables();
	    int position = getCurrentColour(resource);
	    spinner = (Spinner) findViewById(R.id.options_spinner);
	    OptionsAdapter adapter = new OptionsAdapter(this, android.R.layout.simple_spinner_item, selectables);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);
	    spinner.setSelection(position);
	    
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		    	SharedPreferences.Editor editor = sp.edit();
		    	editor.putInt("current_colour", selectables.get(position).getResource());
		    	editor.commit();
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    }

		});

	}
	
	public boolean onOptionsItemSelected(MenuItem item)
    {
    	finish();
    	return true;
    }
	
	private ArrayList<OptionSelectable> fillSelectables()
	{
		ArrayList<OptionSelectable> selectables = new ArrayList<OptionSelectable>();
		Resources res = getResources();
		
		String[] names = res.getStringArray(R.array.asteroids_array);
		TypedArray icons = res.obtainTypedArray(R.array.icons);
		
		for(int i = 0; i < names.length; i++)
		{
			OptionSelectable tempSelectable = new OptionSelectable(names[i], icons.getResourceId(i, -1));
			selectables.add(tempSelectable);
		}
		icons.recycle();
		return selectables;
	}
	
	private int getCurrentColour(int id)
	{
		int pos = -1;
		for(int i = 0; i < selectables.size(); ++i)
		{
			if(selectables.get(i).getResource() == id)
			{
				pos = i;
			}
		}
		return pos;
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
	}

	@Override
	public void onDestroy() {
		
		super.onDestroy();
	}
	
	@Override
	public void onStop() {
	
		super.onStop();
	}
}


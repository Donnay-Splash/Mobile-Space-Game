package com.example.model;

import java.util.ArrayList;

import com.example.bouncesurface.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HighScoreArrayAdapter extends BaseAdapter {
	private final Context context;
	private final ArrayList<HighScore> values;
	
	public HighScoreArrayAdapter(Context _context, int layoutResourceId, ArrayList<HighScore> _values)
	{
		super();
		this.context = _context;
		this.values = _values;
	}
	static class ViewHolder{
		private TextView scoreText;
		private TextView asteroidsText;
		private TextView timeText;
	}
	 @Override
    public int getCount () {
        return values.size();
    }

    @Override
    public long getItemId (int position) {
        return position;
    }

    @Override
    public Object getItem (int position) {
        return values.get(position);
    }
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder mViewHolder = null;
		
		if(convertView == null)
		{
			mViewHolder = new ViewHolder();
			
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.highscore_adapter, parent, false);
			
			mViewHolder.scoreText = (TextView) convertView.findViewById(R.id.score_text);
			mViewHolder.asteroidsText = (TextView) convertView.findViewById(R.id.asteroids_text);
			mViewHolder.timeText = (TextView) convertView.findViewById(R.id.time_text);
		
			convertView.setTag(mViewHolder);
		}
		else
		{
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		HighScore tempScore = values.get(position);
		mViewHolder.scoreText.setText("Score: " + String.valueOf(tempScore.getScore()));
		mViewHolder.asteroidsText.setText("Asteroids " + String.valueOf(tempScore.getAsteroidsDestroyed()));
		mViewHolder.timeText.setText("Time: " + String.valueOf(tempScore.getTime()));
		Log.d("score", mViewHolder.scoreText.toString());
		return convertView;
	}

}

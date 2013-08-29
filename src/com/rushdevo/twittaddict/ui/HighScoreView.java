package com.rushdevo.twittaddict.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rushdevo.twittaddict.R;
import com.rushdevo.twittaddict.db.model.HighScore;

public class HighScoreView extends LinearLayout {
	private HighScore highScore;
	private Integer place;
	private Boolean firstMatch;
	
	public HighScoreView(Context context) {
		this(context, null, null, null);
	}
	
	public HighScoreView(Context context, HighScore highScore, Integer position, Boolean firstMatch) {
		super(context);
		LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.game_over_high_score_list_item, this);
		setViewData(highScore, position, firstMatch);
	}
	
	public void setViewData(HighScore highScore, Integer position, Boolean firstMatch) {
		this.highScore = highScore;
		this.place = position+1;
		this.firstMatch = firstMatch;
		createView();		
	}
	
	public HighScore getHighScore() {
		return this.highScore;
	}
	
	public Integer getPosition() {
		return this.place;
	}
	
	public Boolean isFirstMatch() {
		return this.firstMatch;
	}
	
	private void createView() {
		TextView placeContainer = (TextView)findViewById(R.id.place_container);
		TextView placeScoreContainer = (TextView)findViewById(R.id.place_score_container);

		placeContainer.setText(place.toString() + ".");
		Integer nextScore = highScore.getScore();
		placeScoreContainer.setText(nextScore.toString());
		
		int color;
		if (firstMatch) color = R.color.medium_blue;
		else color = R.color.grey;
		
		placeContainer.setTextColor(getResources().getColor(color));
		placeScoreContainer.setTextColor(getResources().getColor(color));
	}
}

package com.rushdevo.twittaddict.ui;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.rushdevo.twittaddict.db.model.HighScore;

public class HighScoreAdapter extends ArrayAdapter<HighScore> {
	private List<HighScore> highScores;
	private int firstMatch;
	
	public HighScoreAdapter(Context context, int textViewResourceId, List<HighScore> highScores, Integer currentScore) {
		super(context, textViewResourceId, highScores);
		this.highScores = highScores;
		this.firstMatch = determineFirstMatch(currentScore);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HighScoreView view;
		HighScore highScore = highScores.get(position);
		if (convertView == null) {
			view = new HighScoreView(getContext(), highScore, position, (position == firstMatch));
		} else {
			view = (HighScoreView)convertView;
			if (!view.getHighScore().equals(highScore)) {
				view.setViewData(highScore, position, (position == firstMatch));
				view.invalidate();
			}
		}
		return view;
	};
	
	private int determineFirstMatch(Integer currentScore) {
		HighScore highScore;
		for (int i=0; i<highScores.size(); i++) {
			highScore = highScores.get(i);
			if (highScore.getScore().equals(currentScore)) return i;
		}
		return -1;
	}
}

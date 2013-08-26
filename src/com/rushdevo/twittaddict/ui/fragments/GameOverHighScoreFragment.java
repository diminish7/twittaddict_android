package com.rushdevo.twittaddict.ui.fragments;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rushdevo.twittaddict.R;
import com.rushdevo.twittaddict.db.HighScoreDataSource;
import com.rushdevo.twittaddict.db.model.HighScore;

public class GameOverHighScoreFragment extends Fragment {
	private HighScoreDataSource highScoreDataSource;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.game_over_high_score, container, false);
		if (highScoreDataSource != null) addHighScoresToView(view);
		return view;
	}
	
	public void setDataSource(HighScoreDataSource highScoreDataSource) {
		this.highScoreDataSource = highScoreDataSource;
		if (getView() != null) addHighScoresToView(getView());
	}
	
	private void addHighScoresToView(View view) {
		LinearLayout highScoreContainer = (LinearLayout)view.findViewById(R.id.high_score_container);
		List<HighScore> highScores = highScoreDataSource.getHighScores();
		
		Bundle bundle = getActivity().getIntent().getExtras();
		Integer currentScore = bundle.getInt("score");
		
		int count = 0;
		boolean matched = false;
		TextView nextScoreContainer;
		for (HighScore highScore : highScores) {
			count++;
			int nextScore = highScore.getScore();
			nextScoreContainer = new TextView(getActivity());
			nextScoreContainer.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
			if (currentScore == nextScore && !matched) {
				// Color the first instance of a matched score in blue
				matched = true;
				nextScoreContainer.setTextColor(getResources().getColor(R.color.medium_blue));
			} else {
				nextScoreContainer.setTextColor(getResources().getColor(R.color.grey));
			}
			StringBuilder builder = new StringBuilder();
			builder.append(count);
			builder.append(". ");
			builder.append(nextScore);
			nextScoreContainer.setText(builder.toString());
			highScoreContainer.addView(nextScoreContainer);
		}
	}
}

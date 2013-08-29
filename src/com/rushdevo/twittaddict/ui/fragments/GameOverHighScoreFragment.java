package com.rushdevo.twittaddict.ui.fragments;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.rushdevo.twittaddict.R;
import com.rushdevo.twittaddict.db.HighScoreDataSource;
import com.rushdevo.twittaddict.db.model.HighScore;
import com.rushdevo.twittaddict.ui.HighScoreAdapter;

public class GameOverHighScoreFragment extends Fragment {
	private HighScoreDataSource highScoreDataSource;
	private ListView listView;
	private Integer currentScore;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.game_over_high_score, container, false);
		listView = (ListView)view.findViewById(android.R.id.list);
		Bundle bundle = getActivity().getIntent().getExtras();
		currentScore = bundle.getInt("score");
		if (highScoreDataSource != null) addHighScoresToView(view);
		return view;
	}
	
	public void setDataSource(HighScoreDataSource highScoreDataSource) {
		this.highScoreDataSource = highScoreDataSource;
		if (getView() != null) addHighScoresToView(getView());
	}
	
	private void addHighScoresToView(View view) {
		List<HighScore> highScores = highScoreDataSource.getHighScores();
		HighScoreAdapter adapter = new HighScoreAdapter(getActivity(), android.R.layout.simple_list_item_1, highScores, currentScore);
		listView.setAdapter(adapter);
	}
}

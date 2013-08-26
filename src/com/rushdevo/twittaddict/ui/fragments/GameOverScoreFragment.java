package com.rushdevo.twittaddict.ui.fragments;

import com.rushdevo.twittaddict.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GameOverScoreFragment extends Fragment {
	private TextView scoreContainer;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.game_over_score, container, false);
		this.scoreContainer = (TextView)view.findViewById(R.id.game_over_score_container);
		Bundle bundle = getActivity().getIntent().getExtras();
		Integer score = bundle.getInt("score");
		scoreContainer.setText(score.toString());
		return view;
	}
}

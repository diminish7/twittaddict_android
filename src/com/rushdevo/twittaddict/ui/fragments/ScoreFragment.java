package com.rushdevo.twittaddict.ui.fragments;

import com.rushdevo.twittaddict.GameChangeListener;
import com.rushdevo.twittaddict.R;
import com.rushdevo.twittaddict.Twittaddict;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Fragment that contains the game's score
 */
public class ScoreFragment extends Fragment implements GameChangeListener {
	private TextView scoreContainer;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.score, container, false);
		this.scoreContainer = (TextView)view.findViewById(R.id.score_container);
		return view;
	}
	
	@Override
	public void onGameChange(Twittaddict game) {
		scoreContainer.setText(game.getScore().toString());
	}
	
	public void setGame(Twittaddict game) {
		game.registerGameChangeListener(this);
	}
}

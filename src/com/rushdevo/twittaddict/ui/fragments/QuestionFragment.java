package com.rushdevo.twittaddict.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rushdevo.twittaddict.GameChangeListener;
import com.rushdevo.twittaddict.Question;
import com.rushdevo.twittaddict.R;
import com.rushdevo.twittaddict.Twittaddict;

/**
 * Main question container
 */
public class QuestionFragment extends Fragment implements GameChangeListener {
	private Twittaddict game;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.question, container, false);
		return view;
	}

	@Override
	public void onGameChange(Twittaddict game) {
		final Question question = game.getNextQuestion();
		// Make sure we're always running this on the UI thread, because
		// sometimes the game is changed in a background thread.
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				displayQuestion(question);
			}
		});
	}
	
	public void setGame(Twittaddict game) {
		this.game = game;
		game.registerGameChangeListener(this);
	}
	
	public Twittaddict getGame() {
		return this.game;
	}
	
	//// PRIVATE HELPERS ///////
	/**
	 * Display a question form
	 * @param question Question to display
	 */
	private void displayQuestion(Question question) {
		if (question != null) {
			BaseQuestionFragment fragment = question.getFragment();
			fragment.setQuestion(question);
			fragment.setGame(getGame());
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(R.id.question_container, fragment);
			transaction.commit();
		}
	}
	}

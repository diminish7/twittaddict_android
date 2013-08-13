package com.rushdevo.twittaddict.ui.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rushdevo.twittaddict.Question;
import com.rushdevo.twittaddict.R;
import com.rushdevo.twittaddict.twitter.model.TwitterUser;

/**
 * Base class for the different types of question fragments (tweet question or user question)
 */
public abstract class BaseQuestionFragment extends Fragment {
	private Question question;
	private View containerView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		containerView = inflater.inflate(getLayoutId(), container, false);
		initializeViews();
		refreshViewWithQuestionIfPossible();
		return containerView;
	}
	
	public Question getQuestion() {
		return this.question;
	}
	
	public void setQuestion(Question question) {
		this.question = question;
		refreshViewWithQuestionIfPossible();
	}
	
	public View getContainerView() {
		return this.containerView;
	}
	
	/**
	 * Refresh the containerView with the details of the question if both are set
	 */
	public void refreshViewWithQuestionIfPossible() {
		if (this.question != null && this.containerView != null) refreshViewWithQuestion();
	}
	
	/**
	 * Get the ID of the layout for this fragment
	 * @return The resource ID of the fragment
	 */
	public abstract int getLayoutId();
	
	/**
	 * Refresh the containerView with the details of the question
	 * Callers of this should guarantee that question and containerView are not null
	 * If unsure, use refreshViewWithQuestionIfPossible()
	 */
	public abstract void refreshViewWithQuestion();
	
	/**
	 * Initialize all the views
	 */
	public abstract void initializeViews();
	
	/**
	 * Given a user, either return its avatar, or a default placeholder avatar as a Drawable
	 * @param user The user whose avatar we want to display
	 * @return The drawable of either the user's avatar or a default
	 */
	public Drawable getAvatarOrDefault(TwitterUser user) {
		if (user == null || user.getProfileImage() == null) {
			return getResources().getDrawable(R.drawable.default_avatar);
		} else {
			return user.getProfileImage();
		}
	}
}

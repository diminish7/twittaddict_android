package com.rushdevo.twittaddict;

import java.util.List;

import com.rushdevo.twittaddict.twitter.model.TwitterUser;
import com.rushdevo.twittaddict.ui.fragments.BaseQuestionFragment;

public interface Question {
	/**
	 * 
	 * Set the user's choice for an answer
	 * @param index
	 */
	public void setChoice(int index);
	/**
	 * 
	 * @return true if the user has set a choice and it matches the correct answer, false otherwise
	 */
	public boolean isCorrect();
	/**
	 * 
	 * @return true if the question has already been answered
	 */
	public boolean isAnswered();
	/**
	 * 
	 * @return TwitterUser: the user associated with the correct answer to the question
	 */
	public TwitterUser getCorrectUser();
	/**
	 * 
	 * @return List<TwitterUser> the list of all users associated with this question
	 */
	public List<TwitterUser> getApplicableUsers();
	/**
	 * 
	 * @return Fragment to display the given question
	 */
	public BaseQuestionFragment getFragment();
}

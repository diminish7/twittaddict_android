package com.rushdevo.twittaddict;

import java.util.ArrayList;
import java.util.List;

import com.rushdevo.twittaddict.twitter.model.TwitterStatus;
import com.rushdevo.twittaddict.twitter.model.TwitterUser;
import com.rushdevo.twittaddict.ui.fragments.BaseQuestionFragment;
import com.rushdevo.twittaddict.ui.fragments.TweetQuestionFragment;

/**
 * A tweet question in the game
 * The user is presented with a tweet and multiple users,
 * and must determine which user tweeted the tweet 
 */
public class TweetQuestion implements Question {
	private TwitterStatus status;
	private List<TwitterUser> users;
	private TwitterUser answer;
	
	public TweetQuestion(TwitterStatus status, List<TwitterUser> users) {
		this.status = status;
		this.users = users;
		this.answer = null;
	}
	
	public TwitterStatus getStatus() {
		return this.status;
	}
	
	public List<TwitterUser> getUsers() {
		return this.users;
	}
	
	@Override
	public void setChoice(int index) {
		this.answer = (users.size() > index) ? users.get(index) : null;
	}

	@Override
	public boolean isCorrect() {
		return isAnswered() && this.answer.equals(getCorrectUser());
	}

	@Override
	public boolean isAnswered() {
		return this.answer != null;
	}

	@Override
	public TwitterUser getCorrectUser() {
		return this.status.getUser();
	}
	
	@Override
	public List<TwitterUser> getApplicableUsers() {
		List<TwitterUser> allUsers = new ArrayList<TwitterUser>();
		allUsers.addAll(users);
		allUsers.add(status.getUser());
		return allUsers;
	}

	@Override
	public BaseQuestionFragment getFragment() {
		return new TweetQuestionFragment();
	}

}

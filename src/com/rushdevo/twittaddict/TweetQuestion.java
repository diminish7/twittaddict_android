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
	
	public TweetQuestion(TwitterStatus status, List<TwitterUser> users) {
		this.status = status;
		this.users = users;
	}
	
	public TwitterStatus getStatus() {
		return this.status;
	}
	
	public List<TwitterUser> getUsers() {
		return this.users;
	}
	
	@Override
	public void setChoice(int index) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isCorrect() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAnswered() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TwitterUser getCorrectUser() {
		// TODO Auto-generated method stub
		return null;
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

package com.rushdevo.twittaddict;

import java.util.ArrayList;
import java.util.List;

import com.rushdevo.twittaddict.twitter.model.TwitterStatus;
import com.rushdevo.twittaddict.twitter.model.TwitterUser;
import com.rushdevo.twittaddict.ui.fragments.BaseQuestionFragment;
import com.rushdevo.twittaddict.ui.fragments.UserQuestionFragment;

/**
 * A User question in the game
 * A user is presented with multiple tweets, and the user must decide which
 * tweet belongs to the user
 */
public class UserQuestion implements Question {
	private TwitterUser user;
	private List<TwitterStatus> statuses;

	public UserQuestion(TwitterUser user, List<TwitterStatus> statuses) {
		this.user = user;
		this.statuses = statuses;
	}
	
	public TwitterUser getUser() {
		return this.user;
	}
	
	public List<TwitterStatus> getStatuses() {
		return this.statuses;
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
		allUsers.add(user);
		for (TwitterStatus status : statuses) {
			allUsers.add(status.getUser());
		}
		return allUsers;
	}

	@Override
	public BaseQuestionFragment getFragment() {
		return new UserQuestionFragment();
	}

}

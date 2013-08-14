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
	private TwitterStatus answer;

	public UserQuestion(TwitterUser user, List<TwitterStatus> statuses) {
		this.user = user;
		this.statuses = statuses;
		this.answer = null;
	}
	
	public TwitterUser getUser() {
		return this.user;
	}
	
	public List<TwitterStatus> getStatuses() {
		return this.statuses;
	}
	
	@Override
	public void setChoice(int index) {
		this.answer = (statuses.size() > index) ? statuses.get(index) : null;
	}

	@Override
	public boolean isCorrect() {
		return isAnswered() && this.user.equals(this.answer.getUser());
	}

	@Override
	public boolean isAnswered() {
		return this.answer != null;
	}

	@Override
	public TwitterUser getCorrectUser() {
		return getUser();
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

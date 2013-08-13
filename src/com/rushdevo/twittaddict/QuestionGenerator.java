package com.rushdevo.twittaddict;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.rushdevo.twittaddict.twitter.model.TwitterStatus;
import com.rushdevo.twittaddict.twitter.model.TwitterUser;

/**
 * Responsible for generating questions in the game, and for maintaining
 * a cache of future questions
 */
public class QuestionGenerator {
	private Twittaddict game;
	private List<TwitterUser> friends;
	private Set<TwitterUser> uniqueStatusUsers;
	private List<TwitterStatus> statuses;
	private List<TwitterStatus> backupStatuses;
	private Queue<Question> questions;
	
	// Cache of avatars as drawables, keyed on user id
	private Map<Long, Drawable> avatarCache;
	
	public QuestionGenerator(Twittaddict game) {
		this.game = game;
		questions = new LinkedList<Question>();
		avatarCache = new HashMap<Long, Drawable>();
	}
	
	public void setFriends(List<TwitterUser> friends) {
		this.friends = friends;
	}
	
	public void setStatuses(List<TwitterStatus> statuses) {
		this.statuses = statuses;
		// Keep a dup list to refill from if we go through all statuses
		this.backupStatuses = new ArrayList<TwitterStatus>();
		this.backupStatuses.addAll(statuses);
		// Keep a set of unique users associated with these statuses
		this.uniqueStatusUsers = new HashSet<TwitterUser>();
		for (TwitterStatus status : statuses) {
			uniqueStatusUsers.add(status.getUser());
		}
	}
	
	/**
	 * Get the next question in the game
	 * @return The question
	 */
	public Question getNextQuestion() {
		if (questions.isEmpty()) generateNextQuestion();
		return questions.poll();
	}
	
	/**
	 * Initialize the first five questions of the game. This is
	 * used before the game starts to cache a few questions to
	 * stay ahead of the user. Each question requires a call to
	 * twitter to grab the user's avatar.
	 */
	public void initFirstFiveQuestions() {
		for(int i=0; i<5; i++) generateNextQuestion();
		// Now start the background worker to continue to queue up questions while the game runs
		new QuestionInitializerTask().execute();
	}
	
	///// PRIVATE HELPERS //////
	
	private void generateNextQuestion() {
		// Keep a cache of at most 10 questions in memory
		if (questions.size() < 10) {
			// Randomly generate either a tweet question or a user question
			Question question = new Random().nextBoolean() ? generateTweetQuestion() : generateUserQuestion();
			getAvatarDrawablesForQuestion(question);
			questions.offer(question);
		}
	}
	
	/**
	 * Generate a TweetQuestion - a question where you are given a tweet
	 * and you pick which user tweeted it (from three possible users)
	 * @return TweetQuestion
	 */
	private TweetQuestion generateTweetQuestion() {
		TwitterStatus status = getRandomStatus();
		List<TwitterUser> users = getThreeRandomUsers(status.getUser());
		return new TweetQuestion(status, users);
	}
	/**
	 * Generate a UserQuestion - a question where you are given a user
	 * and you pick which tweet belongs to them (from three tweets)
	 * @return UserQuestion
	 */
	private UserQuestion generateUserQuestion() {
		TwitterStatus answer = getRandomStatus();
		TwitterUser user = answer.getUser();
		List<TwitterStatus> statuses = getThreeRandomStatuses(answer);
		return new UserQuestion(user, statuses);
	}
	private TwitterStatus getRandomStatus() {
		// If we've run through all the statuses, reload them from backup and start over
		if (this.statuses.isEmpty()) this.statuses.addAll(this.backupStatuses);
		// Return a random status from the list
		int index = new Random().nextInt(this.statuses.size());
		return this.statuses.remove(index);
	}
	private TwitterUser getRandomUser() {
		int index = new Random().nextInt(this.friends.size());
		return this.friends.get(index);
	}
	private List<TwitterStatus> getThreeRandomStatuses(TwitterStatus status) {
		TwitterUser user = status.getUser();
		List<TwitterStatus> statuses = new ArrayList<TwitterStatus>();
		statuses.add(status);
		int size = uniqueStatusUsers.size();
		if (size > 3) size = 3;
		TwitterStatus nextStatus;
		while (statuses.size() < size) {
			nextStatus = getRandomStatus();
			if (nextStatus != status && nextStatus.getUser() != user) {
				statuses.add(nextStatus);
			}
		}
		Collections.shuffle(statuses);
		return statuses;
	}
	/**
	 * 
	 * @param user
	 * @return List of up to three unique users including the passed in user
	 */
	private List<TwitterUser> getThreeRandomUsers(TwitterUser user) {
		List<TwitterUser> users = new ArrayList<TwitterUser>();
		users.add(user);
		int size = this.friends.size();
		if (size > 3) size = 3;
		// Find three random users
		TwitterUser nextUser;
		while (users.size() < 3) {
			nextUser = getRandomUser();
			if (!users.contains(nextUser)) {
				users.add(nextUser);
			}
		}
		Collections.shuffle(users);
		return users;
	}
	
	/**
	 * Get the avatar Drawable from cache, or from Twitter and then cache it
	 * @param question The question whose users' avatars need checking
	 */
	private void getAvatarDrawablesForQuestion(Question question) {
		for (TwitterUser user : question.getApplicableUsers()) {
			if (user.hasProfileImage()) continue;
			else {
				Drawable avatar = avatarCache.get(user.getId());
				if (avatar == null) user.setProfileImage(avatar);
				else avatarCache.put(user.getId(), user.initializeDrawable());
			}
		}
	}

	/**
	 * Background task for generating the question queue in the game. 
	 * Runs in the background because part of the task is to generate the Drawable
	 * for the users' twitter avatars.
	 */
	class QuestionInitializerTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			while(!game.isComplete()) generateNextQuestion();
			return null;
		}
	}
}

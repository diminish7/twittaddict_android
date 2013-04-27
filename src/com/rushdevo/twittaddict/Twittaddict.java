package com.rushdevo.twittaddict;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.rushdevo.twittaddict.db.UserDataSource;
import com.rushdevo.twittaddict.db.model.User;
import com.rushdevo.twittaddict.twitter.Authenticator;
import com.rushdevo.twittaddict.twitter.Twitter;
import com.rushdevo.twittaddict.ui.GameView;

public class Twittaddict {
	private static final String ERROR = "error";
	private static final String NEW = "new";
	private static final String AUTHENTICATING = "authenticating";
	private static final String IN_PROGRESS = "in progress";
	private static final String COMPLETE = "complete";
	private static final String PAUSED = "paused";
	
	private String state;
	private User user;
	private Twitter twitter;
	private GameView gameView;
	private Context context;
	private Authenticator authenticator;
	private UserDataSource userDataSource;
	
	private List<String> errors;
	
	public Twittaddict(GameView gameView) {
		this.gameView = gameView;
		this.errors = new ArrayList<String>();
		this.userDataSource = gameView.getUserDataSource();
		this.context = this.gameView.getApplicationContext();
		this.authenticator = new Authenticator(context, gameView);
		this.twitter = new Twitter(context);
		initialize();
	}
	
	public boolean isErrored() {
		return this.state.equals(ERROR);
	}
	
	public boolean isNew() {
		return this.state.equals(NEW);
	}
	
	public boolean isAuthenticating() {
		return this.state.equals(AUTHENTICATING);
	}
	
	public boolean isAuthenticated() {
		return user != null;
	}
	
	public boolean isInProgress() {
		return this.state.equals(IN_PROGRESS);
	}
	
	public boolean isComplete() {
		return this.state.equals(COMPLETE);
	}
	
	public boolean isPaused() {
		return this.state.equals(PAUSED);
	}
	
	public void error(String message) {
		this.state = ERROR;
		this.errors.add(message);
	}
	
	public void start() {
		if (isAuthenticated()) {
			this.state = IN_PROGRESS;
		} else {
			error(context.getString(R.string.oauth_failure));
		}
	}
	
	public void initialize() {
		this.errors.clear();
		this.state = NEW;
	}
	
	public void complete() {
		this.state = COMPLETE;
	}
	
	public void pause() {
		this.state = PAUSED;
	}
	
	public void resume() {
		this.state = IN_PROGRESS;
	}
	
	public Twitter getTwitter() {
		return this.twitter;
	}
	
	public List<String> getErrors() {
		return this.errors;
	}
	
	public boolean authenticate() {
		this.errors.clear();
		this.state = AUTHENTICATING;
		if (user == null) user = authenticator.authenticateFromDatabase();
		if (user == null) user = authenticator.authenticateFromTwitterResponse(gameView.getCurrentUri());
		if (user == null) authenticator.authenticateFromTwitterRequest();
		if (user != null) {
			if (user.isNewRecord()) userDataSource.createUser(user);
			return true;
		} else {
			return false;
		}
	}
}

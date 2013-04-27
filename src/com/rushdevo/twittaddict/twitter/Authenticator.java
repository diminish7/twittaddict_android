package com.rushdevo.twittaddict.twitter;

import java.io.IOException;
import java.util.Properties;

import oauth.signpost.AbstractOAuthConsumer;
import oauth.signpost.OAuth;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import com.rushdevo.twittaddict.db.HasDataSources;
import com.rushdevo.twittaddict.db.UserDataSource;
import com.rushdevo.twittaddict.db.model.User;

public class Authenticator {
	private static final String TAG = Authenticator.class.getSimpleName();
	
	private static final String REQUEST_TOKEN_URL =  "https://api.twitter.com/oauth/request_token";
	private static final String ACCESS_TOKEN_URL =  "https://api.twitter.com/oauth/access_token";
	private static final String AUTHORIZATION_URL =  "https://api.twitter.com/oauth/authorize";
	
	public static final String CALLBACK_URL = "twittaddict://twitterauth";
	
	private static final String AUTHENTICATION_PREFERENCES = "AUTHENTICATION_PREFERENCES";
	private static final String REQUEST_TOKEN = "REQUEST_TOKEN";
	private static final String REQUEST_TOKEN_SECRET = "REQUEST_TOKEN_SECRET";
	
	private Context context;
	private AbstractOAuthConsumer consumer;
	private OAuthProvider provider;
	
	private UserDataSource userDataSource;
	
	public Authenticator(Context context, HasDataSources dataSourceHaver) {
		this.context = context;
		this.userDataSource = dataSourceHaver.getUserDataSource();
		this.consumer = initializeConsumer();
		this.provider = new CommonsHttpOAuthProvider(REQUEST_TOKEN_URL, ACCESS_TOKEN_URL, AUTHORIZATION_URL);
		provider.setOAuth10a(true);
	}
	
	/**
	 * Determines if we are already authenticated
	 * 
	 * @return True if we are auth'd and false if not
	 */
	public boolean isAuthenticated() {
		return consumer != null && consumer.getToken() != null && consumer.getTokenSecret() != null;
	}
		
	/**
	 * Instantiates a user from the current intent Uri
	 * 
	 * @param uri The current intent Uri
	 * @return The authenticated User, or null if none exists
	 * @throws OAuthMessageSignerException
	 * @throws OAuthNotAuthorizedException
	 * @throws OAuthExpectationFailedException
	 * @throws OAuthCommunicationException
	 */
	public User getUserFromAccessToken(Uri uri) throws OAuthMessageSignerException, OAuthNotAuthorizedException, OAuthExpectationFailedException, OAuthCommunicationException {
		Log.d(TAG, "Trying to get authenticated user from URI");
		if (uri == null) return null;
		String verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);
		popRequestToken();
		provider.retrieveAccessToken(consumer, verifier);
		if (consumer.getToken() != null && consumer.getTokenSecret() != null) {
			return new User(consumer.getToken(), consumer.getTokenSecret());
		} else {
			return null;
		}
	}
	
	/**
	 * Attempt to query a previously saved user from the database
	 * 
	 * @return the authenticated user or null if none exists
	 */
	public User authenticateFromDatabase() {
		Log.d(TAG, "Trying to get authenticated user from DB");
		User user = userDataSource.getUser();
		if (user != null) consumer.setTokenWithSecret(user.getToken(), user.getTokenSecret());
		return user;
	}
	
	/**
	 * Attempt to create a user from a twitter auth response
	 * 
	 * @param uri The Uri data from the current intent
	 * @return The authenticated user or null if none exists
	 */
	public User authenticateFromTwitterResponse(Uri uri) {
		try {
	    	return getUserFromAccessToken(uri);
		} catch (OAuthException e) {
			Log.d(TAG, e.getMessage());
			return null;
		}
	}
	
	/**
	 * Given there is no authenticated user, redirect to the twitter auth url
	 */
	public void authenticateFromTwitterRequest() {
		Log.d(TAG, "Redirecting to twitter for auth");
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getAuthenticationUrl()));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
	////////PRIVATE HELPER METHODS ////////////
	
	/**
	 * Constructs the OAuth authentication URL
	 * 
	 * @return The auth URL
	 */
	private String getAuthenticationUrl() {
		String authUrl = null;
		try {
			authUrl = provider.retrieveRequestToken(consumer, CALLBACK_URL);
			// Set aside the request token and secret for when we get the response
			stashRequestToken();
		} catch (OAuthException e) {
			Log.d(TAG, e.getMessage());
		}
		return authUrl;
	}
	
	/**
	 * Initializes an OAuth consumer
	 * 
	 * @return The consumer
	 */
	private CommonsHttpOAuthConsumer initializeConsumer() {
		Properties props = new Properties();
		try {
			props.load(context.getResources().getAssets().open("twitter.properties"));
			String key = props.getProperty("consumerKey");
			String secret = props.getProperty("consumerSecret");
			return new CommonsHttpOAuthConsumer(key, secret);
		} catch (IOException e) {
			Log.d(TAG, e.getMessage());
			return null;
		}
	}
	
	/**
	 * Set the request token and secret aside to be used to get the access token and secret upon response
	 */
	private void stashRequestToken() {
		SharedPreferences prefs = context.getSharedPreferences(AUTHENTICATION_PREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(REQUEST_TOKEN, consumer.getToken());
		editor.putString(REQUEST_TOKEN_SECRET, consumer.getTokenSecret());
		editor.commit();
	}
	
	/**
	 * Setup the consumer with the saved request token and secret, then clear those values
	 * under the assumption that we're about to retrieve the access token and secret
	 */
	private void popRequestToken() {
		SharedPreferences prefs = context.getSharedPreferences(AUTHENTICATION_PREFERENCES, Context.MODE_PRIVATE);
		String token = prefs.getString(REQUEST_TOKEN, null);
		String tokenSecret = prefs.getString(REQUEST_TOKEN_SECRET, null);
		if (token != null && tokenSecret != null) {
			consumer.setTokenWithSecret(token, tokenSecret);
			SharedPreferences.Editor editor = prefs.edit();
			editor.remove(REQUEST_TOKEN);
			editor.remove(REQUEST_TOKEN_SECRET);
			editor.commit();
		}
	}
}

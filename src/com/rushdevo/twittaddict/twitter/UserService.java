package com.rushdevo.twittaddict.twitter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.http.client.HttpClient;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.rushdevo.twittaddict.Twittaddict;
import com.rushdevo.twittaddict.twitter.model.TwitterIdsCollection;
import com.rushdevo.twittaddict.twitter.model.TwitterUser;

public class UserService extends AbstractTwitterService {
	
	private static final String BASE_ACCOUNT_URL = BASE_URL + "account/";
	private static final String CREDENTIALS_URL = BASE_ACCOUNT_URL + "verify_credentials.json";
	
	private static final String BASE_USERS_URL = BASE_URL + "users/";
	private static final String USERS_LOOKUP_URL = BASE_USERS_URL + "lookup.json";
	
	private static final String BASE_FRIENDS_URL = BASE_URL + "friends/";
	private static final String FRIEND_IDS_URL = BASE_FRIENDS_URL + "ids.json";
	
	private static final int MAX_USER_LOOKUP = 100;
	
	private static final Type TWITTER_USER_COLLECTION_TYPE = new TypeToken<Collection<TwitterUser>>(){}.getType();
	
	public UserService(Context context, Twittaddict game, Authenticator authenticator) {
		super(context, game, authenticator);
	}
	
	public UserService(Context context, Twittaddict game, Authenticator authenticator, HttpClient client) {
		super(context, game, authenticator, client);
	}
	
	/**
	 * Calls Twitter API to find the authenticated user
	 * @return The authenticated user
	 */
	public TwitterUser getAuthenticatedUser() {
		return getGson().fromJson(getResponseBody(CREDENTIALS_URL), TwitterUser.class);
	}
	
	/**
	 * Calls Twitter API to find the IDs of the user's friends
	 * @param user The user whose friends we want to find
	 * @return The list of IDs of all the user's friends
	 */
	public List<Long> getFriendIds(TwitterUser user) {
		List<Long> friendIds = new ArrayList<Long>();
		TwitterIdsCollection collection;
		Long cursor = -1l;
		while (cursor != 0) {
			// Setup the URL
			StringBuilder urlBuilder = new StringBuilder(FRIEND_IDS_URL);
			urlBuilder.append("?");
			urlBuilder.append("user_id=");
			urlBuilder.append(user.getId());
			urlBuilder.append("&");
			urlBuilder.append("cursor=");
			urlBuilder.append(cursor);
			// Query the API
			collection = getGson().fromJson(getResponseBody(urlBuilder.toString()), TwitterIdsCollection.class);
			// Add the ids to the friend IDs collection
			friendIds.addAll(collection.getIds());
			// Set aside the next cursor
			cursor = collection.getNextCursor();
		}
		return friendIds;
	}
	
	/**
	 * Calls Twitter API to find the user's friends
	 * @param user The user whose friends we want to find
	 * @return The list of TwitterUsers of the user's friends
	 */
	public List<TwitterUser> getFriends(TwitterUser user) {
		List<Long> ids = getFriendIds(user);
		List<TwitterUser> friends = new ArrayList<TwitterUser>();
		
		int counter = 0;
		StringBuilder builder = new StringBuilder(USERS_LOOKUP_URL);
		builder.append("?user_id=");
		
		String baseURLString = builder.toString();
		
		Long lastId = ids.get(ids.size()-1);
		
		for (Long id : ids) {
			if (counter != 0) builder.append(",");
			builder.append(id);
			
			counter += 1;
			
			if (counter >= MAX_USER_LOOKUP || id == lastId) {
				// Make API call
				Collection<TwitterUser> tempFriends = getGson().fromJson(getResponseBody(builder.toString()), TWITTER_USER_COLLECTION_TYPE);
				friends.addAll(tempFriends);
				// Start next set of IDs
				builder = new StringBuilder(baseURLString);
				counter = 0;
			}
			
		}
		return friends;
	}
}

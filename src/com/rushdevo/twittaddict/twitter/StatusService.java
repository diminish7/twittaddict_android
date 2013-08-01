package com.rushdevo.twittaddict.twitter;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import org.apache.http.client.HttpClient;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.rushdevo.twittaddict.Twittaddict;
import com.rushdevo.twittaddict.twitter.model.TwitterStatus;

public class StatusService extends AbstractTwitterService {
	private static final String BASE_STATUSES_URL = BASE_URL + "statuses/";
	private static final String HOME_TIMELINE_URL = BASE_STATUSES_URL + "home_timeline.json?count=200";
	
	private static final Type TWITTER_STATUS_COLLECTION_TYPE = new TypeToken<Collection<TwitterStatus>>(){}.getType();
	
	public StatusService(Context context, Twittaddict game, Authenticator authenticator) {
		super(context, game, authenticator);
	}
	
	public StatusService(Context context, Twittaddict game, Authenticator authenticator, HttpClient client) {
		super(context, game, authenticator, client);
	}
	
	public List<TwitterStatus> getHomeTimeline() {
		return getGson().fromJson(getResponseBody(HOME_TIMELINE_URL), TWITTER_STATUS_COLLECTION_TYPE);
	}
}

package com.rushdevo.twittaddict.twitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.rushdevo.twittaddict.Twittaddict;
import com.rushdevo.twittaddict.utils.GsonUtils;

public abstract class AbstractTwitterService {
	protected static final String BASE_URL = "https://api.twitter.com/1.1/";
	
	protected static final Integer SUCCESS = 200;
	
	protected Context context;
	protected Twittaddict game;
	protected Authenticator authenticator;
	protected HttpClient client;
	
	public AbstractTwitterService(Context context, Twittaddict game, Authenticator authenticator) {
		this(context, game, authenticator, new DefaultHttpClient());
	}
	
	public AbstractTwitterService(Context context, Twittaddict game, Authenticator authenticator, HttpClient client) {
		this.context = context;
		this.game = game;
		this.authenticator = authenticator;
		this.client = client;
	}
	
	/**
	 * Get an instance of a Gson parser
	 * @return The gson object
	 */
	protected Gson getGson() {
		return GsonUtils.getGsonInstance();
	}
	
	/**
	 * Get the instance's HttpClient 
	 * @return The client
	 */
	protected HttpClient getClient() {
		return this.client;
	}
	
	protected String getResponseBody(String url) {
		try {
			HttpGet get = new HttpGet(url);
			authenticator.signRequest(get);
			final HttpResponse response = getClient().execute(get);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				applyError("Failed to connect to Twitter (" + statusCode + ")");
				return null;
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer buffer = new StringBuffer();
			String line;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			return buffer.toString();
		} catch (OAuthMessageSignerException e) {
			applyError(e);
		} catch (OAuthExpectationFailedException e) {
			applyError(e);
		} catch (OAuthCommunicationException e) {
			applyError(e);
		} catch (ClientProtocolException e) {
			applyError(e);
		} catch (IOException e) {
			applyError(e);
		}
		return null;
	}
	
	/**
	 * Apply exception error to the game
	 * @param error An exception
	 */
	protected void applyError(Exception error) {
		applyError(error.getMessage());
	}
	
	/**
	 * Apply an error to the game
	 * @param error A string error
	 */
	protected void applyError(String error) {
		Log.d(getTag(), error);
		game.error(error);
	}
	
	/**
	 * Get the tag for logging. Use the classes simple name
	 * @return The tag
	 */
	protected String getTag() {
		return getClass().getSimpleName();
	}
	
}

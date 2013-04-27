package com.rushdevo.twittaddict.twitter;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;

public class Twitter {
	private Context context;
	private HttpClient client;
	
	//////// CONSTRUCTORS /////////////
	public Twitter(Context context) {
		this.context = context.getApplicationContext();
		this.client = new DefaultHttpClient();
	}
}

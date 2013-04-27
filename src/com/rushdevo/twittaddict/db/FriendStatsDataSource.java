package com.rushdevo.twittaddict.db;

import static android.provider.BaseColumns._ID;
import android.content.Context;

public class FriendStatsDataSource {
	public static final String FRIEND_STATS_TABLE_NAME = "friend_stats";

	public static final String FRIEND_STATS_USER = "user_id";
	public static final String FRIEND_STATS_FRIEND = "friend_id";
	public static final String FRIEND_STATS_CORRECT = "correct";
	public static final String FRIEND_STATS_ATTEMPTS = "attempts";
	public static final String FRIEND_STATS_PERCENT = "percent_correct";
	
	public static final String[] FRIEND_STAT_COLUMNS = { _ID, FRIEND_STATS_USER, FRIEND_STATS_FRIEND, FRIEND_STATS_ATTEMPTS, FRIEND_STATS_CORRECT, FRIEND_STATS_PERCENT };
	
	public FriendStatsDataSource(Context context) {
		// TODO
	}
	
	public void open() {
		// TODO
	}
	
	public void close() {
		// TODO
	}
}

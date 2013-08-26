package com.rushdevo.twittaddict.db;

import static android.provider.BaseColumns._ID;

import java.util.Random;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rushdevo.twittaddict.db.model.FriendStats;
import com.rushdevo.twittaddict.twitter.model.TwitterUser;

public class FriendStatsDataSource {
	public static final String FRIEND_STATS_TABLE_NAME = "friend_stats";

	public static final String FRIEND_STATS_USER = "user_id";
	public static final String FRIEND_STATS_FRIEND = "friend_id";
	public static final String FRIEND_STATS_CORRECT = "correct";
	public static final String FRIEND_STATS_ATTEMPTS = "attempts";
	public static final String FRIEND_STATS_PERCENT = "percent_correct";
	
	public static final String[] FRIEND_STAT_COLUMNS = { _ID, FRIEND_STATS_USER, FRIEND_STATS_FRIEND, FRIEND_STATS_ATTEMPTS, FRIEND_STATS_CORRECT, FRIEND_STATS_PERCENT };
	
	public static final int ID_INDEX = 0;
	public static final int FRIEND_STATS_USER_INDEX = 1;
	public static final int FRIEND_STATS_FRIEND_INDEX = 2;
	public static final int FRIEND_STATS_ATTEMPTS_INDEX = 3;
	public static final int FRIEND_STATS_CORRECT_INDEX = 4;
	public static final int FRIEND_STATS_PERCENT_INDEX = 5;
	
	private TwittaddictDatabase dbHelper;
	private SQLiteDatabase database;
	
	public FriendStatsDataSource(Context context) {
		dbHelper = new TwittaddictDatabase(context);
	}
	
	public void open() {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public FriendStats getFriendStats(TwitterUser friend, Integer userId) {
		String selection = FRIEND_STATS_FRIEND + " = ? AND " + FRIEND_STATS_USER + " = ?";
		String[] selectionArgs = { friend.getId().toString(), userId.toString() }; 
		Cursor cursor = database.query(FRIEND_STATS_TABLE_NAME, FRIEND_STAT_COLUMNS, selection, selectionArgs, null, null, null);
		FriendStats stats = cursor.moveToFirst() ? cursorToFriendStats(cursor) : null;
		cursor.close();
		return stats;
	}
	
	public Long getBffId() {
		Long id = null;
		Cursor cursor = database.query(FRIEND_STATS_TABLE_NAME, FRIEND_STAT_COLUMNS, null, null, null, null, (FRIEND_STATS_PERCENT + " DESC"), "1");
		if (cursor.moveToNext()) {
			FriendStats topStats = cursorToFriendStats(cursor);
			cursor.close();
			String selection = FRIEND_STATS_PERCENT + " = ?";
			String[] selectionArgs = { topStats.getPercent().toString() };
			cursor = database.query(FRIEND_STATS_TABLE_NAME, FRIEND_STAT_COLUMNS, selection, selectionArgs, null, null, null);
			cursor.moveToPosition(new Random().nextInt(cursor.getCount()));
			id = cursorToFriendStats(cursor).getFriendId();
		}
		cursor.close();
		return id;
	}
	
	public void createFriendStats(TwitterUser friend, Integer userId, boolean isCorrectAnswer) {
		ContentValues values = new ContentValues();
		values.put(FRIEND_STATS_USER, userId);
		values.put(FRIEND_STATS_FRIEND, friend.getId());
		values.put(FRIEND_STATS_ATTEMPTS, 1);
		if (isCorrectAnswer) {
			values.put(FRIEND_STATS_CORRECT, 1);
			values.put(FRIEND_STATS_PERCENT, 1.0);
		} else {
			values.put(FRIEND_STATS_CORRECT, 0);
			values.put(FRIEND_STATS_PERCENT, 0.0);
		}
		try {
			database.insertOrThrow(FRIEND_STATS_TABLE_NAME, null, values);
		} catch (SQLException e) {
			Log.d(getClass().getSimpleName(), e.getMessage());
		}
	}
	
	public void updateFriendStats(FriendStats friendStats, boolean isCorrectAnswer) {
		friendStats.setAttempts(friendStats.getAttempts() + 1);
		if (isCorrectAnswer) friendStats.setCorrect(friendStats.getCorrect() + 1);
		friendStats.setPercent(friendStats.getCorrect().floatValue() / friendStats.getAttempts().floatValue());
		saveFriendStats(friendStats);
	}
	
	public void saveFriendStats(FriendStats friendStats) {
		ContentValues values = new ContentValues();
		values.put(FRIEND_STATS_ATTEMPTS, friendStats.getAttempts());
		values.put(FRIEND_STATS_CORRECT, friendStats.getCorrect());
		values.put(FRIEND_STATS_PERCENT, friendStats.getPercent());
		try {
			String whereClause = _ID + " = ?";
			String[] whereArgs = { friendStats.getId().toString() };
			database.update(FRIEND_STATS_TABLE_NAME, values, whereClause, whereArgs);
		} catch (SQLException e) {
			Log.d(getClass().getSimpleName(), e.getMessage());
		}
	}
	
	private FriendStats cursorToFriendStats(Cursor cursor) {
		if (cursor.isAfterLast()) return null;
		Integer id = cursor.getInt(ID_INDEX);
		Long userId = cursor.getLong(FRIEND_STATS_USER_INDEX);
		Long friendId = cursor.getLong(FRIEND_STATS_FRIEND_INDEX);
		Integer attempts = cursor.getInt(FRIEND_STATS_ATTEMPTS_INDEX);
		Integer correct = cursor.getInt(FRIEND_STATS_CORRECT_INDEX);
		Float percent = cursor.getFloat(FRIEND_STATS_PERCENT_INDEX);
		return new FriendStats(id, userId, friendId, attempts, correct, percent);
	}
}

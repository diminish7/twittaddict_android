package com.rushdevo.twittaddict.db;

import static android.provider.BaseColumns._ID;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rushdevo.twittaddict.db.model.HighScore;

public class HighScoreDataSource {
	public static final String HIGH_SCORES_TABLE_NAME = "high_scores";

	public static final String HIGH_SCORES_USER = "user_id";
	public static final String HIGH_SCORES_MODE = "mode";
	public static final String HIGH_SCORES_SCORE = "score";
	public static final String HIGH_SCORES_TIMESTAMP = "score_timestamp";
	
	public static final String[] HIGH_SCORE_COLUMNS = { _ID, HIGH_SCORES_USER, HIGH_SCORES_MODE, HIGH_SCORES_SCORE, HIGH_SCORES_TIMESTAMP };
	
	public static final int ID_INDEX = 0;
	public static final int HIGH_SCORES_USER_INDEX = 1;
	public static final int HIGH_SCORES_MODE_INDEX = 2;
	public static final int HIGH_SCORES_SCORE_INDEX = 3;
	public static final int HIGH_SCORES_TIMESTAMP_INDEX = 4;
	
	private TwittaddictDatabase dbHelper;
	private SQLiteDatabase database;
	
	public HighScoreDataSource(Context context) {
		dbHelper = new TwittaddictDatabase(context);
	}
	
	public void open() {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		database.close();
	}
	
	public void saveHighScore(Integer userId, Integer score) {
		ContentValues values = new ContentValues();
		values.put(HIGH_SCORES_MODE, "MATCH");
		values.put(HIGH_SCORES_SCORE, score);
		values.put(HIGH_SCORES_USER, userId);
		values.put(HIGH_SCORES_TIMESTAMP, System.currentTimeMillis());
		try {
			database.insertOrThrow(HIGH_SCORES_TABLE_NAME, null, values);
		} catch (SQLException e) {
			Log.d(getClass().getSimpleName(), e.getMessage());
		}
	}
	
	public List<HighScore> getHighScores() {
		List<HighScore> results = new ArrayList<HighScore>();
		
		Cursor cursor = database.query(HIGH_SCORES_TABLE_NAME, HIGH_SCORE_COLUMNS, null, null, null, null, "score DESC", "10");
		while (cursor.moveToNext()) {
			results.add(cursorToHighScore(cursor));
		}
		cursor.close();
		
		return results;
	}
	
	private HighScore cursorToHighScore(Cursor cursor) {
		if (cursor.isAfterLast()) return null;
		Integer id = cursor.getInt(ID_INDEX);
		Integer userId = cursor.getInt(HIGH_SCORES_USER_INDEX);
		String mode = cursor.getString(HIGH_SCORES_MODE_INDEX);
		Integer score = cursor.getInt(HIGH_SCORES_SCORE_INDEX);
		String timestamp = cursor.getString(HIGH_SCORES_TIMESTAMP_INDEX);
		return new HighScore(id, userId, mode, score, timestamp);
	}
}

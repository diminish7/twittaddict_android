package com.rushdevo.twittaddict.db;

import static android.provider.BaseColumns._ID;
import android.content.Context;

public class HighScoreDataSource {
	public static final String HIGH_SCORES_TABLE_NAME = "high_scores";

	public static final String HIGH_SCORES_USER = "user_id";
	public static final String HIGH_SCORES_MODE = "mode";
	public static final String HIGH_SCORES_SCORE = "score";
	public static final String HIGH_SCORES_TIMESTAMP = "score_timestamp";
	
	public static final String[] HIGH_SCORE_COLUMNS = { _ID, HIGH_SCORES_USER, HIGH_SCORES_MODE, HIGH_SCORES_SCORE, HIGH_SCORES_TIMESTAMP };
	
	public HighScoreDataSource(Context context) {
		// TODO
	}
	
	public void open() {
		// TODO
	}
	
	public void close() {
		// TODO
	}
}

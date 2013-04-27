package com.rushdevo.twittaddict.db;

import static android.provider.BaseColumns._ID;
import static com.rushdevo.twittaddict.db.FriendStatsDataSource.FRIEND_STATS_ATTEMPTS;
import static com.rushdevo.twittaddict.db.FriendStatsDataSource.FRIEND_STATS_CORRECT;
import static com.rushdevo.twittaddict.db.FriendStatsDataSource.FRIEND_STATS_FRIEND;
import static com.rushdevo.twittaddict.db.FriendStatsDataSource.FRIEND_STATS_PERCENT;
import static com.rushdevo.twittaddict.db.FriendStatsDataSource.FRIEND_STATS_TABLE_NAME;
import static com.rushdevo.twittaddict.db.FriendStatsDataSource.FRIEND_STATS_USER;
import static com.rushdevo.twittaddict.db.HighScoreDataSource.HIGH_SCORES_MODE;
import static com.rushdevo.twittaddict.db.HighScoreDataSource.HIGH_SCORES_SCORE;
import static com.rushdevo.twittaddict.db.HighScoreDataSource.HIGH_SCORES_TABLE_NAME;
import static com.rushdevo.twittaddict.db.HighScoreDataSource.HIGH_SCORES_TIMESTAMP;
import static com.rushdevo.twittaddict.db.HighScoreDataSource.HIGH_SCORES_USER;
import static com.rushdevo.twittaddict.db.UserDataSource.USERS_TABLE_NAME;
import static com.rushdevo.twittaddict.db.UserDataSource.USERS_TOKEN;
import static com.rushdevo.twittaddict.db.UserDataSource.USERS_TOKEN_SECRET;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TwittaddictDatabase extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "twittaddict.db";
	private static final int DATABASE_VERSION = 2;
	
	public TwittaddictDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Users Table
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE " + USERS_TABLE_NAME + " (");
		sql.append(_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
		sql.append(USERS_TOKEN_SECRET + " TEXT, ");
		sql.append(USERS_TOKEN + " TEXT");
		sql.append(");");
		db.execSQL(sql.toString());
		// High Scores Table
		sql = new StringBuilder();
		sql.append("CREATE TABLE " + HIGH_SCORES_TABLE_NAME + " (");
		sql.append(_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
		sql.append(HIGH_SCORES_USER + " INTEGER, ");
		sql.append(HIGH_SCORES_MODE + " TEXT, ");
		sql.append(HIGH_SCORES_SCORE + " INTEGER, ");
		sql.append(HIGH_SCORES_TIMESTAMP + " INTEGER");
		sql.append(");");
		db.execSQL(sql.toString());
		// Friend Stats Table
		sql = new StringBuilder();
		sql.append("CREATE TABLE " + FRIEND_STATS_TABLE_NAME + " (");
		sql.append(_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
		sql.append(FRIEND_STATS_USER + " INTEGER, ");
		sql.append(FRIEND_STATS_FRIEND + " INTEGER, ");
		sql.append(FRIEND_STATS_ATTEMPTS + " INTEGER, ");
		sql.append(FRIEND_STATS_CORRECT + " INTEGER, ");
		sql.append(FRIEND_STATS_PERCENT + " REAL");
		sql.append(");");
		db.execSQL(sql.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int old_version, int new_version) {
		// TODO: This should be smarter about migrations
		db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + HIGH_SCORES_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + FRIEND_STATS_TABLE_NAME);
		onCreate(db);
	}	
}

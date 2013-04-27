package com.rushdevo.twittaddict.db;

public interface HasDataSources {
	/**
	 * Get the DAO for Users
	 * 
	 * @return The User DAO
	 */
	public abstract UserDataSource getUserDataSource();
	
	/**
	 * Get the DAO for FriendStats
	 * 
	 * @return The FriendStats DAO
	 */
	public abstract FriendStatsDataSource getFriendStatsDataSource();
	
	/**
	 * Get the DAO for HighScore
	 * 
	 * @return The HighScore DAO
	 */
	public abstract HighScoreDataSource getHighScoreDataSource();
}

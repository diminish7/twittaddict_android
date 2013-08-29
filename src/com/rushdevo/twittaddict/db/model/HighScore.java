package com.rushdevo.twittaddict.db.model;


public class HighScore extends AbstractModel {
	private Integer id;
	private Integer userId;
	private String mode;
	private Integer score;
	private String timestamp;
	
	public HighScore(Integer id, Integer userId, String mode, Integer score, String timestamp) {
		this.id = id;
		this.userId = userId;
		this.mode = mode;
		this.score = score;
		this.timestamp = timestamp;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public boolean equals(Object other) {
		if (other == null) return false;
		if (!(other instanceof HighScore)) return false;
		return ((HighScore)other).getId().equals(getId());
	}
}

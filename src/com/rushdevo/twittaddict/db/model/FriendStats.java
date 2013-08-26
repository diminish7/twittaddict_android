package com.rushdevo.twittaddict.db.model;

public class FriendStats extends AbstractModel {
	private Integer id;
	private Long userId;
	private Long friendId;
	private Integer attempts;
	private Integer correct;
	private Float percent;
	
	public FriendStats(Integer id, Long userId, Long friendId, Integer attempts, Integer correct, Float percent) {
		this.id = id;
		this.userId = userId;
		this.friendId = friendId;
		this.attempts = attempts;
		this.correct = correct;
		this.percent = percent;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getFriendId() {
		return friendId;
	}

	public void setFriendId(Long friendId) {
		this.friendId = friendId;
	}

	public Integer getAttempts() {
		return attempts;
	}

	public void setAttempts(Integer attempts) {
		this.attempts = attempts;
	}

	public Integer getCorrect() {
		return correct;
	}

	public void setCorrect(Integer correct) {
		this.correct = correct;
	}

	public Float getPercent() {
		return percent;
	}

	public void setPercent(Float percent) {
		this.percent = percent;
	}
}

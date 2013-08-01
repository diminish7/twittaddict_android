package com.rushdevo.twittaddict.twitter.model;

public class TwitterStatus {
	private Long id;
	private String text;
	private TwitterUser user;
	private boolean valid;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public TwitterUser getUser() {
		return user;
	}
	
	public void setUser(TwitterUser user) {
		this.user = user;
	}
	
	public boolean isValid() {
		return valid;
	}
	
	public void setValid(boolean valid) {
		this.valid = valid;
	}
}

package com.rushdevo.twittaddict.twitter.model;

public class TwitterStatus extends BaseTwitterModel {
	private String text;
	private TwitterUser user;
	
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
}

package com.rushdevo.twittaddict.twitter.model;

import java.io.InputStream;
import java.net.URL;

import android.graphics.drawable.Drawable;

public class TwitterUser {
	
	/////// PROPERTIES ////////
	
	private String screenName;
	private String name;
	private String avatar;
	private Drawable avatarImage;
	private String url;
	private Long id;
	private String description;
	private Integer friendsCount;
	private Integer statusesCount;
	private boolean valid;
	
	/////// GETTERS AND SETTERS /////
	
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public Drawable getAvatarImage() {
		if (this.avatarImage == null) {
			initializeDrawable();
		}
		return this.avatarImage;
	}
	public void setAvatarImage(Drawable avatarImage) {
		this.avatarImage = avatarImage;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getFriendsCount() {
		return friendsCount;
	}
	public void setFriendsCount(Integer friendsCount) {
		this.friendsCount = friendsCount;
	}
	public Integer getStatusesCount() {
		return statusesCount;
	}
	public void setStatusesCount(Integer statusesCount) {
		this.statusesCount = statusesCount;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	//////// PUBLIC HELPERS ////////
	
	public void initializeDrawable() {
		try {
    		URL url = new URL(this.avatar);
    		InputStream is = (InputStream)url.getContent();
    		this.avatarImage = Drawable.createFromStream(is, this.name);
    	} catch (Exception e) {
    		this.avatarImage = null;
    	}
	}
}

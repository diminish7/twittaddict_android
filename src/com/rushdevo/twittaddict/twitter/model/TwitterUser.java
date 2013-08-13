package com.rushdevo.twittaddict.twitter.model;

import java.io.InputStream;
import java.net.URL;

import android.graphics.drawable.Drawable;

public class TwitterUser {
	
	/////// PROPERTIES ////////
	
	private String screen_name;
	private String name;
	private String profile_image_url;
	private Drawable profileImage;
	private String url;
	private Long id;
	private String description;
	private Integer friends_count;
	private Integer statuses_count;
	
	/////// GETTERS AND SETTERS /////
	
	public String getScreenName() {
		return screen_name;
	}
	public void setScreenName(String screenName) {
		this.screen_name = screenName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProfileImageUrl() {
		return profile_image_url;
	}
	public void setProfileImageUrl(String profileImageUrl) {
		this.profile_image_url = profileImageUrl;
	}
	public boolean hasProfileImage() {
		return this.profileImage != null;
	}
	public Drawable getProfileImage() {
		if (!hasProfileImage()) initializeDrawable();
		return this.profileImage;
	}
	public void setProfileImage(Drawable profileImage) {
		this.profileImage = profileImage;
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
		return friends_count;
	}
	public void setFriendsCount(Integer friendsCount) {
		this.friends_count = friendsCount;
	}
	public Integer getStatusesCount() {
		return statuses_count;
	}
	public void setStatusesCount(Integer statusesCount) {
		this.statuses_count = statusesCount;
	}
	
	//////// PUBLIC HELPERS ////////
	
	public Drawable initializeDrawable() {
		try {
    		URL url = new URL(this.profile_image_url);
    		InputStream is = (InputStream)url.getContent();
    		this.profileImage = Drawable.createFromStream(is, this.name);
    	} catch (Exception e) {
    		this.profileImage = null;
    	}
		return this.profileImage;
	}
}

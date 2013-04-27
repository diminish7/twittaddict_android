package com.rushdevo.twittaddict.db.model;

public class User extends AbstractModel {
	private Integer id;
	private String token;
	private String tokenSecret;
	
	public User (Integer id, String token, String tokenSecret) {
		this.id = id;
		this.token = token;
		this.tokenSecret = tokenSecret;
	}
	
	public User(String token, String tokenSecret) {
		this(null, token, tokenSecret);
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getToken() {
		return this.token;
	}
	
	public String getTokenSecret() {
		return this.tokenSecret;
	}
}

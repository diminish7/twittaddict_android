package com.rushdevo.twittaddict.twitter.model;

public class TwitterCollectionWithCursor {
	private Long previousCursor;
	private Long nextCursor;
	private String previousCursorStr;
	private String nextCursorStr;
	
	////// GETTERS AND SETTERS ////////
	public Long getPreviousCursor() {
		return previousCursor;
	}
	
	public void setPreviousCursor(Long previousCursor) {
		this.previousCursor = previousCursor;
	}
	
	public Long getNextCursor() {
		return nextCursor;
	}
	
	public void setNextCursor(Long nextCursor) {
		this.nextCursor = nextCursor;
	}
	
	public String getPreviousCursorStr() {
		return previousCursorStr;
	}
	
	public void setPreviousCursorStr(String previousCursorStr) {
		this.previousCursorStr = previousCursorStr;
	}
	
	public String getNextCursorStr() {
		return nextCursorStr;
	}
	
	public void setNextCursorStr(String nextCursorStr) {
		this.nextCursorStr = nextCursorStr;
	}
}

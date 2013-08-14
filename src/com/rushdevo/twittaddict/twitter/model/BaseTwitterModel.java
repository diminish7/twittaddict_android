package com.rushdevo.twittaddict.twitter.model;

public abstract class BaseTwitterModel {
	private Long id;
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public boolean equals(Object other) {
		if (other == null) return false;
		else if (other == this) return true;
		else if (!getClass().isInstance(other)) return false;
		else return ((BaseTwitterModel)other).getId().equals(getId());
	}
}

package com.rushdevo.twittaddict.db.model;

public abstract class AbstractModel {

	public abstract Integer getId();
	public abstract void setId(Integer id);
	
	public boolean isNewRecord() {
		return getId() == null;
	}
}

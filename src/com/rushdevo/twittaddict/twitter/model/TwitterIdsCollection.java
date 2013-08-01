package com.rushdevo.twittaddict.twitter.model;

import java.util.List;

public class TwitterIdsCollection extends TwitterCollectionWithCursor {
	private List<Long> ids;

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
}

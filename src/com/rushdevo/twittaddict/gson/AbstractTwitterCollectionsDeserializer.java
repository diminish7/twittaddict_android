package com.rushdevo.twittaddict.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rushdevo.twittaddict.twitter.model.TwitterCollectionWithCursor;


public abstract class AbstractTwitterCollectionsDeserializer {
	
	protected TwitterCollectionWithCursor parseCursorInformation(TwitterCollectionWithCursor collection, JsonObject jsonObj) {
		JsonElement el;
		
		el = jsonObj.get("previous_cursor");
		collection.setPreviousCursor(el.getAsLong());
		
		el = jsonObj.get("next_cursor");
		collection.setNextCursor(el.getAsLong());
		
		el = jsonObj.get("previous_cursor_str");
		collection.setPreviousCursorStr(el.getAsString());
		
		el = jsonObj.get("next_cursor_str");
		collection.setNextCursorStr(el.getAsString());
		
		return collection;
	}
}

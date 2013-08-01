package com.rushdevo.twittaddict.gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.rushdevo.twittaddict.twitter.model.TwitterIdsCollection;

/**
 * @author jasonrush
 * JSON deserializer for Twitter ID collections. Includes the collection of IDs, as well as cursor info
 */
public class TwitterIdsCollectionDeserializer extends AbstractTwitterCollectionsDeserializer implements JsonDeserializer<TwitterIdsCollection> {

	@Override
	public TwitterIdsCollection deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx) throws JsonParseException {
		TwitterIdsCollection collection = new TwitterIdsCollection();
		JsonObject obj = json.getAsJsonObject();
		
		collection = (TwitterIdsCollection)parseCursorInformation(collection, obj);
		
		List<Long> ids = new ArrayList<Long>();
		
		JsonElement el;
		el = obj.get("ids");
		
		if (el.isJsonArray()) {
			JsonArray twitterIds = el.getAsJsonArray();
			Iterator<JsonElement> iterator = twitterIds.iterator();
		
			while (iterator.hasNext()) {
				el = iterator.next();
				ids.add(el.getAsLong());
			}
		}
		
		collection.setIds(ids);
		
		return collection;
	}

}

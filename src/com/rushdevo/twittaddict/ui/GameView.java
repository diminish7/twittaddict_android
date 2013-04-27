package com.rushdevo.twittaddict.ui;

import com.rushdevo.twittaddict.db.HasDataSources;

import android.content.Context;
import android.net.Uri;

public interface GameView extends HasDataSources{
	/**
	 * Gets the current application context
	 *  
	 * @return The application context
	 */
	public abstract Context getApplicationContext();
	
	/**
	 * Gets the current intent uri
	 * 
	 * @return The Uri
	 */
	public abstract Uri getCurrentUri();
}

package com.rushdevo.twittaddict;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class ErrorDisplayer {

	public static void displayError(Context context, String tag, String message) {
		displayError(context, tag, message, null);
	}
	
	public static void displayError(Context context, String tag, String message, Exception e) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		Log.d(tag, message);
		if (e != null) Log.d(tag, e.getMessage());
	}
}

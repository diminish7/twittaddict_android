package com.rushdevo.twittaddict.ui.activities;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.rushdevo.twittaddict.R;
import com.rushdevo.twittaddict.Twittaddict;
import com.rushdevo.twittaddict.db.FriendStatsDataSource;
import com.rushdevo.twittaddict.db.HighScoreDataSource;
import com.rushdevo.twittaddict.db.UserDataSource;
import com.rushdevo.twittaddict.ui.GameView;

public class TwittaddictActivity extends SherlockFragmentActivity implements GameView {	
	private Twittaddict game;
	private UserDataSource userDataSource;
	private HighScoreDataSource highScoreDataSource;
	private FriendStatsDataSource friendStatsDataSource;

	/////////// Activity Overrides ////////////////
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);		

		userDataSource = new UserDataSource(this);
		userDataSource.open();
		
		highScoreDataSource = new HighScoreDataSource(this);
		highScoreDataSource.open();
		
		friendStatsDataSource = new FriendStatsDataSource(this);
		friendStatsDataSource.open();
		
		game = new Twittaddict(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		userDataSource.close();
		highScoreDataSource.close();
		friendStatsDataSource.close();
		
		if (game.isInProgress()) game.pause();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		userDataSource.open();
		highScoreDataSource.open();
		friendStatsDataSource.open();
		
		if (game.isPaused()) {
			game.resume();
		} else if (game.isNew()) {
			new GameStartTask().execute(game);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.exit:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	///////// GameView API ////////////
	@Override
	public Uri getCurrentUri() {
		return this.getIntent().getData();
	}

	@Override
	public UserDataSource getUserDataSource() {
		return userDataSource;
	}

	@Override
	public FriendStatsDataSource getFriendStatsDataSource() {
		return friendStatsDataSource;
	}

	@Override
	public HighScoreDataSource getHighScoreDataSource() {
		return highScoreDataSource;
	}
	
	/////////// INNER CLASSES ////////////
	
	/**
	 * Initializes the game in the background to get the
	 * twitter OAuth API calls out of the UI thread
	 */
	private class GameStartTask extends AsyncTask<Twittaddict, Void, Void> {

		private Twittaddict game;
		
		@Override
		protected Void doInBackground(Twittaddict... games) {
			this.game = games[0];
			game.authenticate();
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			if (game.isErrored()) {
				StringBuilder builder = new StringBuilder();
				builder.append("Something went wrong: ");
				for (String error : game.getErrors()) {
					builder.append(error);
				}
				Toast.makeText(getBaseContext(), builder.toString(), Toast.LENGTH_LONG).show();
			} else {
				game.start();
			}
		}
	}
}

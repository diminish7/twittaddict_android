package com.rushdevo.twittaddict.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.rushdevo.twittaddict.GameChangeListener;
import com.rushdevo.twittaddict.R;
import com.rushdevo.twittaddict.Twittaddict;
import com.rushdevo.twittaddict.db.FriendStatsDataSource;
import com.rushdevo.twittaddict.db.HighScoreDataSource;
import com.rushdevo.twittaddict.db.UserDataSource;
import com.rushdevo.twittaddict.twitter.model.TwitterUser;
import com.rushdevo.twittaddict.ui.GameView;
import com.rushdevo.twittaddict.ui.fragments.ClockFragment;
import com.rushdevo.twittaddict.ui.fragments.QuestionFragment;
import com.rushdevo.twittaddict.ui.fragments.ScoreFragment;

public class TwittaddictActivity extends SherlockFragmentActivity implements GameView, GameChangeListener {	
	private Twittaddict game;
	private UserDataSource userDataSource;
	private HighScoreDataSource highScoreDataSource;
	private FriendStatsDataSource friendStatsDataSource;
	
	private ProgressDialog progressDialog;
	
	private ClockFragment clockFragment;
	private ScoreFragment scoreFragment;
	private QuestionFragment questionFragment;

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
		game.registerGameChangeListener(this);
		
		clockFragment = (ClockFragment)getSupportFragmentManager().findFragmentById(R.id.clock_fragment);
		clockFragment.setGame(game);
		
		scoreFragment = (ScoreFragment)getSupportFragmentManager().findFragmentById(R.id.score_fragment);
		scoreFragment.setGame(game);
		
		questionFragment = (QuestionFragment)getSupportFragmentManager().findFragmentById(R.id.question_fragment);
		questionFragment.setGame(game);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		userDataSource.close();
		highScoreDataSource.close();
		friendStatsDataSource.close();
		
		if (game.isInProgress()) game.pause();
		if (progressDialog != null) progressDialog.dismiss();
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
	
	/////////// GameChangeListener API ///
	@Override
	public void onGameChange(Twittaddict game) {
		if (game.isComplete()) new GameCompleteTask(game, this).execute();
	}
	
	/////////// INNER CLASSES ////////////
	
	/**
	 * Initializes the game in the background to get the
	 * twitter OAuth API calls out of the UI thread
	 */
	private class GameStartTask extends AsyncTask<Twittaddict, Void, Void> {

		private Twittaddict game;
		
		@Override
		protected void onPreExecute() {
			String title = getResources().getString(R.string.loading_title);
	    	String message = getResources().getString(R.string.loading_message);
	    	progressDialog = ProgressDialog.show(TwittaddictActivity.this, title, message, true);
		}
		
		@Override
		protected Void doInBackground(Twittaddict... games) {
			this.game = games[0];
			game.authenticate();
			if (game.isAuthenticated()) {
				game.start();
			}
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
			}
			progressDialog.dismiss();
			progressDialog = null;
		}
	}
	
	/**
	 * Sets up data for the Game Over screen 
	 */
	private class GameCompleteTask extends AsyncTask<Void, Void, TwitterUser> {
		private Twittaddict game;
		private Context context;
		
		public GameCompleteTask(Twittaddict game, Context context) {
			this.game = game;
			this.context = context;
		}
		
		@Override
		protected void onPreExecute() {
			String title = getResources().getString(R.string.loading_game_over_title);
	    	String message = getResources().getString(R.string.loading_game_over_message);
	    	progressDialog = ProgressDialog.show(TwittaddictActivity.this, title, message, true);
		}
		
		@Override
		protected TwitterUser doInBackground(Void... params) {
    		Long id = friendStatsDataSource.getBffId();
    		return game.getUserService().getUser(id);
		}
		
		@Override
		protected void onPostExecute(TwitterUser bff) {
			Intent intent = new Intent(context, GameOverActivity.class);
    		intent.putExtra("score", game.getScore());
    		intent.putExtra("bffScreenName", (bff == null) ? "" : bff.getScreenName());
    		intent.putExtra("bffProfileImageUrl", (bff == null) ? "" : bff.getProfileImageUrl());
			startActivity(intent);
			progressDialog.dismiss();
			progressDialog = null;
			finish();
		}
	}
}

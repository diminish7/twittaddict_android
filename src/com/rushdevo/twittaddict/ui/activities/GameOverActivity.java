package com.rushdevo.twittaddict.ui.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.rushdevo.twittaddict.R;
import com.rushdevo.twittaddict.db.FriendStatsDataSource;
import com.rushdevo.twittaddict.db.HighScoreDataSource;
import com.rushdevo.twittaddict.ui.fragments.GameOverHighScoreFragment;

public class GameOverActivity extends SherlockFragmentActivity implements OnClickListener {
	private Drawable selectedTab;
	private Drawable deselectedTab;
	private TabHost tabHost;
	
	private GameOverHighScoreFragment highScoreFragment;
	
	private HighScoreDataSource highScoreDataSource;
	private FriendStatsDataSource friendStatsDataSource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_over);
		
		highScoreDataSource = new HighScoreDataSource(this);
		highScoreDataSource.open();
		
		highScoreFragment = (GameOverHighScoreFragment)getSupportFragmentManager().findFragmentById(R.id.game_over_high_score_fragment);
		highScoreFragment.setDataSource(highScoreDataSource);
		
		friendStatsDataSource = new FriendStatsDataSource(this);
		friendStatsDataSource.open();
		
		Button newGameButton = (Button)findViewById(R.id.play_again_button);
		newGameButton.setOnClickListener(this);
		
		setupTabs();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		highScoreDataSource.close();
		friendStatsDataSource.close();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		highScoreDataSource.open();
		friendStatsDataSource.open();
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

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.play_again_button) {
			Intent intent = new Intent(this, TwittaddictActivity.class);
    		startActivity(intent);
			finish();
		}
	}

	private void setupTabs() {
		selectedTab = getResources().getDrawable(R.drawable.selected_tab);
		deselectedTab = getResources().getDrawable(R.drawable.deselected_tab);
		
		tabHost = (TabHost)findViewById(R.id.tab_host);
        tabHost.setup();
        tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				for (int i=0;i < tabHost.getTabWidget().getChildCount(); i++)
			    {
					tabHost.getTabWidget().getChildAt(i).setBackgroundDrawable(deselectedTab); //unselected
			    }
				tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundDrawable(selectedTab); // selected
			}
		});
        
        String highScoreLabel = getString(R.string.high_score_tab);
        TabSpec highScoreSpec = tabHost.newTabSpec(highScoreLabel);
        highScoreSpec.setContent(R.id.high_score_tab);
        TextView tab = new TextView(this);
        tab.setHeight(20);
        tab.setBackgroundDrawable(selectedTab);
        tab.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
        tab.setText(getString(R.string.high_score_tab));
        highScoreSpec.setIndicator(tab);
        
        String bffLabel= getString(R.string.bff_tab);
        TabSpec bffSpec = tabHost.newTabSpec(bffLabel);
        bffSpec.setContent(R.id.bff_tab);
        tab = new TextView(this);
        tab.setHeight(20);
        tab.setBackgroundDrawable(deselectedTab);
        tab.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
        tab.setText(getString(R.string.bff_tab));
        bffSpec.setIndicator(tab);
        
        tabHost.addTab(highScoreSpec);
        tabHost.addTab(bffSpec);
        tabHost.setCurrentTab(0);
	}
}

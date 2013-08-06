package com.rushdevo.twittaddict.ui.fragments;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rushdevo.twittaddict.R;
import com.rushdevo.twittaddict.Twittaddict;

/**
 * Fragment that contains the clock, displaying the game countdown
 */
@SuppressLint("HandlerLeak")
public class ClockFragment extends Fragment {
	private TextView clockContainer;
	private Timer timer;
	private Twittaddict game;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.clock, container, false);
		this.clockContainer = (TextView)view.findViewById(R.id.clock_container);
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		resetTimer();
	}
	
	@Override
	public void onPause() {
		if (timer != null) timer.cancel();
	}
	
	public void setGame(Twittaddict game) {
		this.game = game;
	}
	
	/**
	 * Creates or clears the timer, and sets it to run every 1000ms to update the clock
	 */
	private void resetTimer() {
		if (timer == null) {
			timer = new Timer();
		} else {
			timer.cancel();
		}
		UpdateClockTask timerTask = new UpdateClockTask(game, updateClockHandler);
		timer.schedule(timerTask, 0, 1000);
	}
	
	/**
	 * The periodic task to update the clock countdown on the screen
	 */
	private class UpdateClockTask extends TimerTask {
		private Twittaddict game;
		private Handler handler;
		
		public UpdateClockTask(Twittaddict game, Handler handler) {
			this.game = game;
			this.handler = handler;
		}
		
		@Override
		public void run() {
			handler.sendEmptyMessage(game.getSecondsRemaining());
		}
	}
	
	/**
	 * The handler used by the periodic timer to update the clock countdown on the screen
	 */
	private Handler updateClockHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Integer seconds = msg.what;
			clockContainer.setText(seconds.toString());
			if (seconds == 0) timer.cancel();
			
		}
	};
	
}

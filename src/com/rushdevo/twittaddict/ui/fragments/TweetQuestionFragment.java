package com.rushdevo.twittaddict.ui.fragments;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.rushdevo.twittaddict.R;
import com.rushdevo.twittaddict.TweetQuestion;
import com.rushdevo.twittaddict.twitter.model.TwitterStatus;
import com.rushdevo.twittaddict.twitter.model.TwitterUser;

/**
 * Container for Tweet questions
 */
public class TweetQuestionFragment extends BaseQuestionFragment {
	private TextView tweetContainer;
	private ImageView user1;
	private TextView user1Name;
	private ImageView user2;
	private TextView user2Name;
	private ImageView user3;
	private TextView user3Name;
	
	@Override
	public void initializeViews() {
		tweetContainer = (TextView)getContainerView().findViewById(R.id.tweet_container);
		user1 = (ImageView)getContainerView().findViewById(R.id.user1);
		user1Name = (TextView)getContainerView().findViewById(R.id.user1Name);
		user2 = (ImageView)getContainerView().findViewById(R.id.user2);
		user2Name = (TextView)getContainerView().findViewById(R.id.user2Name);
		user3 = (ImageView)getContainerView().findViewById(R.id.user3);
		user3Name = (TextView)getContainerView().findViewById(R.id.user3Name);
	}
	
	@Override
	public int getLayoutId() {
		return R.layout.tweet_question;
	}
	
	/**
	 * Get the fragment's question, cast as a TweetQuestion
	 * @return The question
	 */
	public TweetQuestion getTweetQuestion() {
		if (getQuestion() == null) return null;
		return (TweetQuestion)getQuestion();
	}
	
	@Override
	public void refreshViewWithQuestion() {
		TweetQuestion q = getTweetQuestion();
		setStatus(q.getStatus());
		setUser(q.getUsers(), 0, user1, user1Name);
		setUser(q.getUsers(), 1, user2, user2Name);
		setUser(q.getUsers(), 2, user3, user3Name);
	}
	
	private void setStatus(TwitterStatus status) {
		String text;
		if (status == null) text = "";
		else text = status.getText();
		tweetContainer.setText(text);
	}
		
	private void setUser(List<TwitterUser> users, int index, ImageView imageView, TextView userNameView) {
		String name;
		Drawable avatar = null;
		if (users == null || users.size() <= index) {
			name = "";
		} else {
			TwitterUser user = users.get(index);
			if (user == null) name = "";
			else {
				name = user.getName();
				avatar = getAvatarOrDefault(user);
			}
		}
		userNameView.setText(name);
		imageView.setImageDrawable(avatar);
	}
}

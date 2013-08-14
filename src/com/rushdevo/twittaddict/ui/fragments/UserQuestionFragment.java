package com.rushdevo.twittaddict.ui.fragments;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.rushdevo.twittaddict.R;
import com.rushdevo.twittaddict.UserQuestion;
import com.rushdevo.twittaddict.twitter.model.TwitterStatus;
import com.rushdevo.twittaddict.twitter.model.TwitterUser;

/**
 * Container for User questions
 */
public class UserQuestionFragment extends BaseQuestionFragment implements OnClickListener {
	private ImageView userImage;
	private TextView userName;
	private TextView tweet1Container;
	private TextView tweet2Container;
	private TextView tweet3Container;

	@Override
	public void initializeViews() {
		// Set aside views
		userImage = (ImageView)getContainerView().findViewById(R.id.user);
		userName = (TextView)getContainerView().findViewById(R.id.userName);
		tweet1Container = (TextView)getContainerView().findViewById(R.id.tweet1Container);
		tweet2Container = (TextView)getContainerView().findViewById(R.id.tweet2Container);
		tweet3Container = (TextView)getContainerView().findViewById(R.id.tweet3Container);
		// Setup click listeners for answers
		getContainerView().findViewById(R.id.tweet1Container).setOnClickListener(this);
		getContainerView().findViewById(R.id.tweet2Container).setOnClickListener(this);
		getContainerView().findViewById(R.id.tweet3Container).setOnClickListener(this);
	}
	
	@Override
	public int getLayoutId() {
		return R.layout.user_question;
	}
	
	/**
	 * Get the fragment's question, cast as a UserQuestion
	 * @return The question
	 */
	public UserQuestion getUserQuestion() {
		if (getQuestion() == null) return null;
		return (UserQuestion)getQuestion();
	}
	
	@Override
	public void refreshViewWithQuestion() {
		UserQuestion q = getUserQuestion();
		setUser(q.getUser());
		setStatus(q.getStatuses(), 0, tweet1Container);
		setStatus(q.getStatuses(), 1, tweet2Container);
		setStatus(q.getStatuses(), 2, tweet3Container);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tweet1Container:
			selectAnswer(0);
			break;
		case R.id.tweet2Container:
			selectAnswer(1);
			break;
		case R.id.tweet3Container:
			selectAnswer(2);
			break;
		}
	}
	
	private void setUser(TwitterUser user) {
		String name;
		Drawable avatar = null;
		if (user == null) name = "";
		else {
			name = user.getName();
			avatar = getAvatarOrDefault(user);
		}
		userName.setText(name);
		userImage.setImageDrawable(avatar);
	}
	
	private void setStatus(List<TwitterStatus> statuses, int index, TextView tweetContainer) {
		String text;
		if (statuses == null || statuses.size() <= index) text = "";
		else {
			TwitterStatus status = statuses.get(index);
			if (status == null) text = "";
			else text = status.getText();
		}
		tweetContainer.setText(text);
	}
}

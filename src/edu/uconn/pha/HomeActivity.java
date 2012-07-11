package edu.uconn.pha;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class HomeActivity extends FragmentActivity {
	private static final String TAG = HomeActivity.class.getName();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
		setTitle(R.string.home);
		
		Log.v(TAG, "Started Home Activity.");
	}
}
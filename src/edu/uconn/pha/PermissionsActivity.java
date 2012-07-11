package edu.uconn.pha;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class PermissionsActivity extends FragmentActivity {
	private static final String TAG = PermissionsActivity.class.getName();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.permissions);
		setContentView(R.layout.permissions_activity);

		Log.d(TAG, "Start Permissions Activity." );
	}
}

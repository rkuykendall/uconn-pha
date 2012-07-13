package edu.uconn.pha;

import org.json.JSONException;

import edu.uconn.serverclient.ServerConnection;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends Activity {

	private static final String TAG = SplashActivity.class.getName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_activity);
	}

	@Override
	public void onResume() {
		super.onResume();
		new Thread(new Runnable() {
			@Override
			public void run() {
				// load HealthVault data
				Log.i(TAG, "Loading :: HealthVault Data");

				try {
					// load person data
					ServerConnection.getPersonInfo(true);
					Log.i(TAG, "Loaded :: Person Data");

					// load medication data
					ServerConnection.getMedicationsJSON(true);
					Log.i(TAG, "Loaded :: Medication Data");

					// load ODL data
					ServerConnection.getODLList(true);
					Log.i(TAG, "Loaded :: ODL Data");

					// loading complete
					Log.i(TAG, "Loading Complete :: HealthVault Data");

					// start the tabs activity and finish the splash activity
					startActivity(new Intent(SplashActivity.this, TabsActivity.class));
					finish();

				} catch (JSONException e) {
					Log.e(TAG, "Error :: JSON Exception", e);
					finish();
				}
			}
		}).start();
	}
}

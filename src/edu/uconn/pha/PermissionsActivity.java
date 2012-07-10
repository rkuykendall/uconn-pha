package edu.uconn.pha;

import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import edu.uconn.model.Policy;
import edu.uconn.serverclient.ServerConnection;

public class PermissionsActivity extends Activity {
	private static final String TAG = PermissionsActivity.class.getName();
	private PermissionsList adapter;
	Policy policy;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.permissions);
		setContentView(R.layout.permissions_activity);
		
        final ListView roles = (ListView) findViewById(R.id.roles);        

		Log.d(TAG, "Start Permissions Activity." );

		try {
			policy = ServerConnection.getPolicy();
		} catch (JSONException e) {
			// TODO: Catch JSON exception properly.
			policy = new Policy();
		}

		// use a data adapter to map data to the list view layout
		adapter = new PermissionsList(this, policy);
		roles.setAdapter(adapter);

		final Button save = (Button) findViewById(R.id.save);        
        save.setOnClickListener(new OnClickListener()  {
            @Override
            public void onClick(View v)  {
                ServerConnection.setPolicy();
            }
        });		
	}
}

package edu.uconn.pha;

import org.json.JSONException;

import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import edu.uconn.model.Policy;
import edu.uconn.serverclient.ServerConnection;

public class PermissionsActivity extends ExpandableListActivity {
	private static final String TAG = PermissionsActivity.class.getName();
	private PermissionsList adapter;
	Policy policy;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO: Dynamic title
		setTitle("Permissions Page");  


		try {
			policy = ServerConnection.getPolicy();
		} catch (JSONException e) {
			policy = new Policy();
		}
		
		Log.d(TAG, "Testing Logs" );

		// use a data adapter to map data to the list view layout
		adapter = new PermissionsList(this, policy);
		setListAdapter(adapter);

		// Expand Groups
		getExpandableListView().setGroupIndicator(null);
		for(int i = 0; i < getExpandableListAdapter().getGroupCount(); i++)
		{
			getExpandableListView().expandGroup(i);
		}
	}

	public void onContentChanged() {
		super.onContentChanged();
		Log.d(TAG, "onContentChanged");
	}

	public boolean onChildClick(ExpandableListView parent, View v, 
			int groupPosition, int childPosition,long id) {

		Log.e(TAG, "onChildClick: v.getTag: " + v.getTag()
				+ " groupPos: " + groupPosition
				+ " childPosition: " + childPosition
				+ " rowId: " + id);
		return false;
	}

	public void setPermissionRead(int groupPosition, int childPosition,
			boolean isChecked) {
		policy.setPermissionRead(groupPosition, childPosition, isChecked);		
	}

	public void setPermissionWrite(int groupPosition, int childPosition,
			boolean isChecked) {
		policy.setPermissionWrite(groupPosition, childPosition, isChecked);
	}

	public Policy getPolicy() {
		return policy;
	}
}

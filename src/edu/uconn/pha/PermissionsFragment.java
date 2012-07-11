package edu.uconn.pha;

import org.json.JSONException;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import edu.uconn.model.Policy;
import edu.uconn.serverclient.ServerConnection;

public class PermissionsFragment extends Fragment {
	private static final String TAG = PermissionsFragment.class.getName();
	private PermissionsList adapter;
	Policy policy;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
	 
	    View view = inflater.inflate(R.layout.permissions_fragment, container, false);
        final ListView roles = (ListView) view.findViewById(R.id.roles);        

		Log.d(TAG, "Create Permissions Fragment View." );

		try {
			policy = ServerConnection.getPolicy();
		} catch (JSONException e) {
			// TODO: Catch JSON exception properly.
			policy = new Policy();
		}

		// use a data adapter to map data to the list view layout
		adapter = new PermissionsList(getActivity(), policy);
		roles.setAdapter(adapter);

		final Button save = (Button) view.findViewById(R.id.save);        
        save.setOnClickListener(new OnClickListener()  {
            @Override
            public void onClick(View v)  {
                ServerConnection.setPolicy();
            }
        });		
		
	    return view;
	}

}

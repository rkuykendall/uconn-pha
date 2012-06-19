package edu.uconn.pha;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

public class PermissionsActivity extends ExpandableListActivity {
	private ExpandableListView listView;
	private static final String TAG = PermissionsActivity.class.getName();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO: Dynamic title
		setTitle("Permissions Page");  

		// retrieve an instance of the list view control
		listView = (ExpandableListView) findViewById(R.id.permissions_activity_list_view);

		// use a data adapter to map data to the list view layout
		setListAdapter(new PermissionsList());
		
		// Expand Groups
		getExpandableListView().setGroupIndicator(null);
		for(int i = 0; i < getExpandableListAdapter().getGroupCount(); i++)
		{
			getExpandableListView().expandGroup(i);
		}
	}
	
    public void onContentChanged  () {
        super.onContentChanged();
        Log.d(TAG, "onContentChanged");
    }

	public class PermissionsList extends BaseExpandableListAdapter
	{		
		@Override
		public Object getChild(int groupPosition, int childPosition) 
		{   
			return childPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) 
		{
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) 
		{
			// Initialize 
			View view = null;
			if( convertView != null ) {
				view = convertView;
			} else {
				view = getLayoutInflater().inflate(R.layout.permissions_object, parent, false); 
			}

			// Permission label
			TextView permission = (TextView) view.findViewById( R.id.permission );
			String[] permissions = new String[] {"Wellness", "Medications", "Allergies"};
			permission.setText( permissions[childPosition % 3] );

			// Read Label
			TextView read = (TextView) view.findViewById( R.id.read );
			read.setText( "View" );

			// Read Checkbox
			CheckBox cbr = (CheckBox) view.findViewById( R.id.checkread );
			cbr.setChecked( true );
//			cbr.setOnCheckedChangeListener(new OnCheckedChangeListener()
//			{
//				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
//				{
//					Toast.makeText(getApplicationContext(), "Check box.", Toast.LENGTH_SHORT).show();
//				}
//			});

			// Write Label
			TextView write = (TextView) view.findViewById( R.id.write );
			write.setText( "Update" );

			// Write Checkbox
			CheckBox cbw = (CheckBox) view.findViewById( R.id.checkwrite );
			cbw.setChecked( true );

			return view;
		}

		@Override
		public int getChildrenCount(int groupPosition) 
		{   
			return 3; // Guaranteed random by dice roll.
		}

		@Override
		public Object getGroup(int groupPosition) {
			return groupPosition;
		}

		@Override
		public int getGroupCount() 
		{   
			return 3;
		}

		@Override
		public long getGroupId(int groupPosition) 
		{   
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) 
		{			
			// New TextView
			TextView tv = new TextView(PermissionsActivity.this);

			// Design
			tv.setBackgroundColor(Color.rgb(20,20,20));
			tv.setTextColor(Color.WHITE);
			tv.setPadding(10, 10, 10, 10); 

			// Content
			String[] doctors = new String[] {"Dr. Yaira", "Dr. Alberto", "Dr. Who"};
			tv.setText(doctors[groupPosition % 3]);

			// Return
			return tv;
		}

		@Override
		public boolean hasStableIds()
		{
			return true;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) 
		{
			return false;
		}     
	}
}

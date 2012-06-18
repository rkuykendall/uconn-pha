package edu.uconn.pha;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

public class PermissionsActivity extends Activity {
	private static final String TAG = PermissionsActivity.class.getName();

	private Intent intent;
	private ExpandableListView listView;
    private LayoutInflater inflater;
	private Resources resources;
	private View view;
	private TextView textView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.permissions_activity);
		// TODO: String
		setTitle("Permissions Page");  
		
		// retrieve an instance of the list view control
		listView = (ExpandableListView) findViewById(R.id.permissions_activity_list_view);

//		// initialize the list view header
//		view = (View) getLayoutInflater().inflate(R.layout.permissions_activity_header, null);
//
//		// set the categories header text
//		textView = (TextView) view.findViewById(R.id.permissions_activity_header_categories);
//		textView.setText("Health Care Providors");
//
//		// add the header view to the list view
//		listView.addHeaderView(view, null, false);

		// resource object to get strings
		resources = getResources();

		// retrieve the permissions list (string-array) from the strings XML file
//		String[] categories = new String[] {"One", "Two", "Three", "Four"};

		// use a data adapter to map data to the list view layout
//		AfarrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_provider , R.id.list_item_one_arrow_text, categories);
//		listView.setAdapter(adapter);
		listView.setAdapter(new ParentLevel());

		// initialize the listener for the list view
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				// switch to a new activity based on item clicked (manual matching required)
//				switch(position) {
//				case 1:
//					// start the prescription drugs activity
//					intent = new Intent(PermissionsActivity.this, RxOtcActivity.class);
//					intent.putExtra("Prescribed", true);
//					startActivity(intent);
//					break;
//				case 2:
//					// start the over the counter drugs
//					intent = new Intent(PermissionsActivity.this, RxOtcActivity.class);
//					intent.putExtra("Prescribed", false);
//					startActivity(intent);
//					break;
//				case 3:
//					// TODO implement nutritional supplements activity
//					Toast.makeText(getApplicationContext(), "Feature not available at this time", Toast.LENGTH_SHORT).show();
//					break;
//				case 4:
//					// TODO implement herbal supplements activity
//					Toast.makeText(getApplicationContext(), "Feature not available at this time", Toast.LENGTH_SHORT).show();
//					break;
//				default:
//					Log.e(TAG, "Error :: Invalid Medication Category Selection");
//					break;
//				}
				Toast.makeText(getApplicationContext(), "Stop touching things.", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public class ParentLevel extends BaseExpandableListAdapter
	{		
		@Override
		public Object getChild(int arg0, int arg1) 
		{   
			return arg1;
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
//			TextView tv = new TextView(PermissionsActivity.this);
//			String[] permissions = {
//					"Read Medications", "Write Medications",
//					"Read Allergies", "Write Allergies",
//					"Read General Information", "Write General Information"
//				};
//			tv.setText(permissions[childPosition % 6]);
//			tv.setPadding(15, 5, 5, 5);
//			tv.setBackgroundColor(Color.YELLOW);
//			tv.setLayoutParams(new ListView.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
//			return tv;

			View v = null;
			
			if( convertView != null ) {
				v = convertView;
			} else {
				v = getLayoutInflater().inflate(R.layout.permissions_object, parent, false); 
			}

			TextView permission = (TextView) v.findViewById( R.id.permission );
			permission.setText( "Medical Records" );
			
			TextView read = (TextView)v.findViewById( R.id.read );
			read.setText( "View" );

			TextView write = (TextView)v.findViewById( R.id.write );
			write.setText( "Update" );

			CheckBox cbr = (CheckBox) v.findViewById( R.id.checkread );
			cbr.setChecked( true );
			
			CheckBox cbw = (CheckBox) v.findViewById( R.id.checkwrite );
			cbw.setChecked( true );

			return v;
		}

		@Override
		public int getChildrenCount(int groupPosition) 
		{   
			return 6;
		}

		@Override
		public Object getGroup(int groupPosition) 
		{
			return groupPosition;
		}

		@Override
		public int getGroupCount() 
		{   
			return 7;
		}

		@Override
		public long getGroupId(int groupPosition) 
		{   
			return groupPosition;
		}

//		String[] doctors = {"Dr. Yaira", "Dr. Alberto", "Dr. Steve"}; doctors[groupPosition % 3]

		
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) 
		{			
//			View row = (View) getLayoutInflater().inflate(R.layout.permissions_activity_header, parent, false);
//            TextView label = (TextView) row.findViewById(R.id.label);
//            label.setText("Health Care Providors");
//            label.setBackgroundColor(Color.GRAY);   


//			View view2 = (View) getLayoutInflater().inflate(R.layout.permissions_activity_header, null);
//			TextView tv2 = (TextView) view2.findViewById(R.id.permissions_activity_header_categories);
//
			TextView tv = new TextView(PermissionsActivity.this);
			tv.setBackgroundColor(Color.DKGRAY);
			tv.setTextColor(Color.WHITE);
//			tv.setTypeface(null, Typeface.BOLD);
			tv.setPadding(10, 7, 7, 7); 

			tv.setText("Health Care Providors");
			return tv;
			
//			return label;
		}

		@Override
		public boolean hasStableIds() 
		{
			return true;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) 
		{
			return true;
		}     
	}

	public class CustExpListview extends ExpandableListView
	{

		int intGroupPosition, intChildPosition, intGroupid;

		public CustExpListview(Context context) 
		{
			super(context);     
		}

		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
		{
			widthMeasureSpec = MeasureSpec.makeMeasureSpec(960, MeasureSpec.AT_MOST);
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(600, MeasureSpec.AT_MOST);
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}  
	}
}

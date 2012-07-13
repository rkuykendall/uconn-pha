package edu.uconn.pha;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

import edu.uconn.model.ODL;
import edu.uconn.serverclient.ServerConnection;

public class WellnessFragment extends SherlockFragment {

	private static final String TAG = WellnessFragment.class.getName();

	private static final int EDITABLE_REQUEST_CODE = 0;
	private static final int FRESH_FORM_REQUEST_CODE = 1;

	private ArrayList<String> odlDateList;
	private ImageView imageView;
	private Intent intent;
	private ListView listView;
	private RelativeLayout relativeLayout;
	private TextView textView;
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.wellness_activity, container, false);

		// retrieve an instance of the list view control
		listView = (ListView) view.findViewById(R.id.wellness_activity_list_view);

		// initialize the list view header
		view = inflater.inflate(R.layout.wellness_activity_header, null);

		// set the create entry header text
		textView = (TextView) view.findViewById(R.id.wellness_activity_header_create_entry);
		textView.setText(R.string.create_entry);

		// initialize the add new entry "button" and listener
		relativeLayout = (RelativeLayout) view.findViewById(R.id.wellness_activity_header_add_button);
		imageView = (ImageView) relativeLayout.findViewById(R.id.included_list_item_button_image);
		imageView.setImageResource(R.drawable.ic_add);
		textView = (TextView) relativeLayout.findViewById(R.id.included_list_item_button_text);
		textView.setText(R.string.add_new_dots);
		relativeLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// start the wellness form activity
				startActivityForResult(new Intent(getActivity(), WellnessFormActivity.class), FRESH_FORM_REQUEST_CODE);
			}
		});

		// set the create report header text
		textView = (TextView) view.findViewById(R.id.wellness_activity_header_create_report);
		textView.setText(R.string.create_report);

		// initialize the generate report "button" and listener
		relativeLayout = (RelativeLayout) view.findViewById(R.id.wellness_activity_header_generate_button);
		imageView = (ImageView) relativeLayout.findViewById(R.id.included_list_item_button_image);
		imageView.setImageResource(R.drawable.ic_graphs);
		textView = (TextView) relativeLayout.findViewById(R.id.included_list_item_button_text);
		textView.setText(R.string.generate_dots);
		relativeLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// start the wellness report activity
				startActivity(new Intent(getActivity(), WellnessReportActivity.class));
			}
		});

		// set the diary entries header text
		textView = (TextView) view.findViewById(R.id.wellness_activity_header_diary_entries);
		textView.setText(R.string.my_entries);

		// add the header view to the list view
		listView.addHeaderView(view, null, false);

		// populate and display the wellness diary entry list
		generateODLList();
		
		return view;
	}

	private void generateODLList() {
		try {
			// generate the ODL list
			odlDateList = new ArrayList<String>();
			ArrayList<ODL> odlList = ServerConnection.getODLList();
			Iterator<ODL> i = odlList.iterator();

			// add the entries the the list
			while(i.hasNext()) {
				ODL odl = i.next();				
				odlDateList.add("Diary Entry: " + odl.getDate());
			}
		} 
		catch (JSONException e) {			
			Log.e(TAG, "Error :: JSON Exception", e);
		}

		// use a data adapter to map data to the list view layout
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_one_arrow, R.id.list_item_one_arrow_text, odlDateList);
		listView.setAdapter(adapter);
		
		// initialize the listener for the list view
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				intent = new Intent(getActivity(), WellnessFormActivity.class);
				intent.putExtra("Index", position);
				startActivityForResult(intent, EDITABLE_REQUEST_CODE);
			}
		});
	}

	// TODO: Fix recording ODLs
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {		
//		if(resultCode == RESULT_OK) {
//			switch(requestCode) {
//			case EDITABLE_REQUEST_CODE:
//				// regenerate ODL list and display successful delete message
//				generateODLList();
//				Toast.makeText(getApplicationContext(), getText(R.string.wellness_entry_deleted), Toast.LENGTH_SHORT).show();
//				break;
//			case FRESH_FORM_REQUEST_CODE:
//				// regenerate ODL list and display successful save message
//				generateODLList();
//				Toast.makeText(getApplicationContext(), getText(R.string.wellness_entry_saved), Toast.LENGTH_SHORT).show();
//				break;
//			default:
//				Log.e(TAG, "Error :: Request Code Not Recognized");
//			}
//		}
//		super.onActivityResult(requestCode, resultCode, data);
//	}
}

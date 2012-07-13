package edu.uconn.pha;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

import edu.uconn.model.Medication;
import edu.uconn.serverclient.ServerConnection;

public class RxOtcActivity extends SherlockFragmentActivity {

	private static final String TAG = RxOtcActivity.class.getName();

	private static final int RX_OTC_ADD = 0;
	private static final int RX_OTC_EDIT = 1;

	private ArrayList<HashMap<String, String>> itemList;
	private ArrayList<Medication> rxOtcList;
	private Boolean prescribed;
	private Bundle extras;
	private HashMap<String, String> map;
	private ImageView imageView;
	private int index;
	private Intent intent;
	private ListView listView;
	private Medication medication;
	private RelativeLayout relativeLayout;
	private TextView textView;
	private View view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rx_otc_activity);
		
		// determine if we are loading prescriptions or OTC drugs
		prescribed = null;
		extras = getIntent().getExtras();
		if(extras != null) {
			prescribed = extras.getBoolean("Prescribed");
		}

		if(prescribed.booleanValue()) {
			setTitle(R.string.prescription_drugs);
		} else {
			setTitle(R.string.over_the_counter_drugs);
		}

		ActionBar bar = getSupportActionBar();
		bar.setDisplayHomeAsUpEnabled(true);

		// retrieve an instance of the list view control
		listView = (ListView) findViewById(R.id.rx_otc_activity_list_view);

		// initialize the list view header
		view = (View) getLayoutInflater().inflate(R.layout.rx_otc_activity_header, null);

		// set the add RxOTC header text
		textView = (TextView) view.findViewById(R.id.rx_otc_activity_header_add_rx_otc);
		if(prescribed.booleanValue()) {
			textView.setText(R.string.add_prescription_drug);
		} else {
			textView.setText(R.string.add_otc_drug);
		}

		// initialize the add new "button" and listener
		relativeLayout = (RelativeLayout) view.findViewById(R.id.rx_otc_activity_header_add_button);
		imageView = (ImageView) relativeLayout.findViewById(R.id.included_list_item_button_image);
		imageView.setImageResource(R.drawable.ic_add);
		textView = (TextView) relativeLayout.findViewById(R.id.included_list_item_button_text);
		textView.setText(R.string.add_new_dots);
		relativeLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// start the RxOtc form activity
				intent = new Intent(RxOtcActivity.this, RxOtcFormActivity.class);
				if(prescribed != null) {
					intent.putExtra("Prescribed", prescribed.booleanValue());
				}
				startActivityForResult(intent, RX_OTC_ADD);
			}
		});

		// set the my prescriptions header text
		textView = (TextView) view.findViewById(R.id.rx_otc_activity_header_my_rx_otc);
		if(prescribed.booleanValue()) {
			textView.setText(R.string.my_prescription_drugs);
		} else {
			textView.setText(R.string.my_otc_drugs);
		}

		// add the header view to the list view
		listView.addHeaderView(view, null, false);

		// populate and display the RxOtc list
		getRxOtc();
	}

	private void getRxOtc() {
		// generate the medications list
		rxOtcList = null;
		try {
			rxOtcList = ServerConnection.getMedicationsJSON();
		} catch (JSONException e) {
			Log.e(TAG, "Error :: JSON Exception", e);
		}

		// create data objects to store and map the data to the multi-column list view
		itemList = new ArrayList<HashMap<String, String>>();
		Iterator<Medication> i = rxOtcList.iterator();
		index = 0;

		while(i.hasNext()) {
			// create the medication object
			medication = i.next();
			medication.getName();
			medication.getDateStarted();

			// add the values to the hashmap
			map = new HashMap<String, String>();
			map.put("column1", medication.getName());
			map.put("column2", medication.getDateStarted());
			map.put("column3", Integer.toString(index));

			// both prescribed and not are added to the list
			if(prescribed == null) {
				itemList.add(map);
			}
			else if(prescribed.booleanValue() == medication.isPrescribed()) {
				// Only 1 or the other is added
				itemList.add(map);
			}
			index++;
		}

		// use a simple data adapter to map data to the list view layout
		SimpleAdapter adapter = new SimpleAdapter(this, itemList, R.layout.list_item_two_arrow, new String[] {"column1", "column2", "column3"}, new int[] {R.id.list_item_two_arrow_col1, R.id.list_item_two_arrow_col2, R.id.list_item_two_arrow_index});
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener(){
			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				intent = new Intent(RxOtcActivity.this, RxOtcFormActivity.class);
				HashMap<String, String> map2 = (HashMap<String, String>)listView.getItemAtPosition(position);
				intent.putExtra("GlobalIndex", Integer.parseInt(map2.get("column3")));
				if(prescribed != null) {
					intent.putExtra("Prescribed", prescribed.booleanValue());
				}
				startActivityForResult(intent, RX_OTC_EDIT);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {		
		if(resultCode == RESULT_OK) {
			switch(requestCode) {
			case RX_OTC_ADD:
				// regenerate medication list and display successful add message
				getRxOtc();
				Toast.makeText(getApplicationContext(), "Drug successfully added", Toast.LENGTH_SHORT).show();
				break;
			case RX_OTC_EDIT:
				// regenerate medication list and display successful modify message
				getRxOtc();
				Toast.makeText(getApplicationContext(), "Changes successful", Toast.LENGTH_SHORT).show();
				break;
			default:
				Log.e(TAG, "Error :: Request Code Not Recognized");
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) 
	{
		switch (item.getItemId()) {
		case android.R.id.home:
			// // app icon in action bar clicked; go home
			// Intent intent = new Intent(this, HomeActivity.class);
			// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			// startActivity(intent);
			// return true;
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}

package edu.uconn.pha;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MedicationsActivity extends Activity {

	private static final String TAG = MedicationsActivity.class.getName();

	private Intent intent;
	private ListView listView;
	private Resources resources;
	private View view;
	private TextView textView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.medications_activity);
		setTitle(R.string.medications);

		// retrieve an instance of the list view control
		listView = (ListView) findViewById(R.id.medications_activity_list_view);

		// initialize the list view header
		view = (View) getLayoutInflater().inflate(R.layout.medications_activity_header, null);

		// set the categories header text
		textView = (TextView) view.findViewById(R.id.medications_activity_header_categories);
		textView.setText(R.string.categories);

		// add the header view to the list view
		listView.addHeaderView(view, null, false);

		// resource object to get strings
		resources = getResources();

		// retrieve the medications list (string-array) from the strings XML file
		String[] categories = resources.getStringArray(R.array.medication_categories_list);

		// use a data adapter to map data to the list view layout
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_one_arrow, R.id.list_item_one_arrow_text, categories);
		listView.setAdapter(adapter);

		// initialize the listener for the list view
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				// switch to a new activity based on item clicked (manual matching required)
				switch(position) {
				case 1:
					// start the prescription drugs activity
					intent = new Intent(MedicationsActivity.this, RxOtcActivity.class);
					intent.putExtra("Prescribed", true);
					startActivity(intent);
					break;
				case 2:
					// start the over the counter drugs
					intent = new Intent(MedicationsActivity.this, RxOtcActivity.class);
					intent.putExtra("Prescribed", false);
					startActivity(intent);
					break;
				case 3:
					// TODO Implement Nutritional Supplements Activity
					Toast.makeText(getApplicationContext(), getString(R.string.missing_feature), Toast.LENGTH_SHORT).show();
					break;
				case 4:
					// TODO Implement Herbal Supplements Activity
					Toast.makeText(getApplicationContext(), getString(R.string.missing_feature), Toast.LENGTH_SHORT).show();
					break;
				default:
					Log.e(TAG, "Error :: Invalid Medication Category Selection");
					break;
				}
			}
		});
	}
}

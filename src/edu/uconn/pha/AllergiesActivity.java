package edu.uconn.pha;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AllergiesActivity extends Activity {

	private ImageView imageView;
	private ListView listView;
	private RelativeLayout relativeLayout;
	private TextView textView;
	private View view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.allergies_activity);
		setTitle(R.string.allergies);

		// retrieve an instance of the list view control
		listView = (ListView) findViewById(R.id.allergies_activity_list_view);

		// initialize the list view header
		view = (View) getLayoutInflater().inflate(R.layout.allergies_activity_header, null);

		// set the add allergies header text
		textView = (TextView) view.findViewById(R.id.allergies_activity_header_add_allergy);
		textView.setText(R.string.add_allergy);

		// initialize the add new allergy "button" and listener
		relativeLayout = (RelativeLayout) view.findViewById(R.id.allergies_activity_header_add_button);
		imageView = (ImageView) relativeLayout.findViewById(R.id.included_list_item_button_image);
		imageView.setImageResource(R.drawable.ic_add);
		textView = (TextView) relativeLayout.findViewById(R.id.included_list_item_button_text);
		textView.setText(R.string.add_new_dots);
		relativeLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Implement Allergy Add Form
				Toast.makeText(getApplicationContext(), getString(R.string.missing_feature), Toast.LENGTH_SHORT).show();
			}
		});

		// set the my allergies header text
		textView = (TextView) view.findViewById(R.id.allergies_activity_header_my_allergies);
		textView.setText(R.string.my_allergies);

		// add the header view to the list view
		listView.addHeaderView(view, null, false);

		// TODO Implement Real Allergy Data
		String[] fakeItems = {"Allergy 1", "Allergy 2", "Allergy 3", "Allergy 4"};

		// use a data adapter to map data to the list view layout
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_one_arrow, R.id.list_item_one_arrow_text, fakeItems);
		listView.setAdapter(adapter);

		// initialize the listener for the list view
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				// TODO Implement Allergy Edit Form
				Toast.makeText(getApplicationContext(), getString(R.string.missing_feature), Toast.LENGTH_SHORT).show();
			}
		});
	}
}

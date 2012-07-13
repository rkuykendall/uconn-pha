package edu.uconn.pha;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class AllergiesFragment extends SherlockFragment {

	private ImageView imageView;
	private ListView listView;
	private RelativeLayout relativeLayout;
	private TextView textView;
	private View header;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.allergies_activity, container, false);

		// retrieve an instance of the list view control
		listView = (ListView) view.findViewById(R.id.allergies_activity_list_view);

		// initialize the list view header
		header = (View) inflater.inflate(R.layout.allergies_activity_header, null);

		// set the add allergies header text
		textView = (TextView) header.findViewById(R.id.allergies_activity_header_add_allergy);
		textView.setText(R.string.add_allergy);

		// initialize the add new allergy "button" and listener
		relativeLayout = (RelativeLayout) header.findViewById(R.id.allergies_activity_header_add_button);
		imageView = (ImageView) relativeLayout.findViewById(R.id.included_list_item_button_image);
		imageView.setImageResource(R.drawable.ic_add);
		textView = (TextView) relativeLayout.findViewById(R.id.included_list_item_button_text);
		textView.setText(R.string.add_new_dots);
		relativeLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Implement Allergy Add Form
				Toast.makeText(getActivity().getApplicationContext(), getString(R.string.missing_feature), Toast.LENGTH_SHORT).show();
			}
		});

		// set the my allergies header text
		textView = (TextView) header.findViewById(R.id.allergies_activity_header_my_allergies);
		textView.setText(R.string.my_allergies);

		// add the header view to the list view
		listView.addHeaderView(header, null, false);

		// TODO Implement Real Allergy Data
		String[] fakeItems = {"Allergy 1", "Allergy 2", "Allergy 3", "Allergy 4"};

		// use a data adapter to map data to the list view layout
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_one_arrow, R.id.list_item_one_arrow_text, fakeItems);
		listView.setAdapter(adapter);

		// initialize the listener for the list view
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				// TODO Implement Allergy Edit Form
				Toast.makeText(getActivity().getApplicationContext(), getString(R.string.missing_feature), Toast.LENGTH_SHORT).show();
			}
		});
		
		return view;
	}
}

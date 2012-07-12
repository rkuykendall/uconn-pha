package edu.uconn.pha;

import org.json.JSONException;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

import edu.uconn.model.DemographicInfo;
import edu.uconn.model.Height;
import edu.uconn.model.Person;
import edu.uconn.model.Weight;
import edu.uconn.serverclient.ServerConnection;

public class HomeFragment extends SherlockFragment {
	private static final String TAG = HomeFragment.class.getName();

	private DemographicInfo basicDemographicInfo;
	private Height height;
	private ImageView imageView;
	private Person person;
	private String fullName;
	private TextView textView;
	private Weight weight;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
	 
	    View view = inflater.inflate(R.layout.home_fragment, container, false);

		// retrieve person data
		try {
			person = ServerConnection.getPersonInfo();
			basicDemographicInfo = person.getBasicInfo();
			height = person.getHeight();
			weight = person.getWeight();
		} catch (JSONException e) {
			Log.e(TAG, "Error :: JSON Exception", e);
		}

		// set and display the user's profile image (generic by default)
		imageView = (ImageView) view.findViewById(R.id.home_activity_user_pic);
		if(basicDemographicInfo.getGender().equalsIgnoreCase("male")) {
			imageView.setBackgroundResource(R.drawable.profile_male);
		} else if(basicDemographicInfo.getGender().equalsIgnoreCase("female")) {
			imageView.setBackgroundResource(R.drawable.profile_female);
		} else {
			imageView.setBackgroundResource(R.drawable.profile_unknown);
		}

		// set and display the user's full name
		textView = (TextView) view.findViewById(R.id.home_activity_user_name);
		fullName = person.getFirstName() + " " + person.getLastName();	
		textView.setText(fullName);

		// set and display the user's birthdate
		textView = (TextView) view.findViewById(R.id.home_activity_user_birthdate);			
		textView.setText(person.getBirthDate());

		// set and display the user's gender
		textView = (TextView) view.findViewById(R.id.home_activity_user_gender);
		textView.setText(basicDemographicInfo.getGender());

		// set and display the user's race
		textView = (TextView) view.findViewById(R.id.home_activity_user_race);		
		textView.setText(person.getRace());

		// set and display the user's blood type
		textView = (TextView) view.findViewById(R.id.home_activity_user_blood_type);			
		textView.setText(person.getBloodType());

		// set and display the user's height
		textView = (TextView) view.findViewById(R.id.home_activity_user_height);		
		textView.setText(height.getHeightValue() + " inches");

		// set and display the user's weight
		textView = (TextView) view.findViewById(R.id.home_activity_user_weight);
		textView.setText(weight.getLbsValue() + " lbs");
		
	    return view;
	}

}

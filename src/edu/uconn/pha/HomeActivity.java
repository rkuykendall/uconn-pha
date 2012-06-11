package edu.uconn.pha;

import org.json.JSONException;

import edu.uconn.model.DemographicInfo;
import edu.uconn.model.Height;
import edu.uconn.model.Person;
import edu.uconn.model.Weight;
import edu.uconn.serverclient.ServerConnection;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends Activity {

	private static final String TAG = HomeActivity.class.getName();

	private DemographicInfo basicDemographicInfo;
	private Height height;
	private ImageView imageView;
	private Person person;
	private String fullName;
	private TextView textView;
	private Weight weight;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
		setTitle(R.string.home);

		// set the HealthVault info header text
		textView = (TextView) findViewById(R.id.home_activity_healthvault_header);
		textView.setText(R.string.my_healthvault_info);

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
		imageView = (ImageView) findViewById(R.id.home_activity_user_pic);
		if(basicDemographicInfo.getGender().equalsIgnoreCase("male")) {
			imageView.setBackgroundResource(R.drawable.profile_male);
		} else if(basicDemographicInfo.getGender().equalsIgnoreCase("female")) {
			imageView.setBackgroundResource(R.drawable.profile_female);
		} else {
			imageView.setBackgroundResource(R.drawable.profile_unknown);
		}

		// set and display the user's full name
		textView = (TextView) findViewById(R.id.home_activity_user_name);
		fullName = person.getFirstName() + " " + person.getLastName();	
		textView.setText(fullName);

		// set and display the user's birthdate
		textView = (TextView) findViewById(R.id.home_activity_user_birthdate);			
		textView.setText(person.getBirthDate());

		// set and display the user's gender
		textView = (TextView) findViewById(R.id.home_activity_user_gender);
		textView.setText(basicDemographicInfo.getGender());

		// set and display the user's race
		textView = (TextView) findViewById(R.id.home_activity_user_race);		
		textView.setText(person.getRace());

		// set and display the user's blood type
		textView = (TextView) findViewById(R.id.home_activity_user_blood_type);			
		textView.setText(person.getBloodType());

		// set and display the user's height
		textView = (TextView) findViewById(R.id.home_activity_user_height);		
		textView.setText(height.getHeightValue() + " inches");

		// set and display the user's weight
		textView = (TextView) findViewById(R.id.home_activity_user_weight);
		textView.setText(weight.getLbsValue() + " lbs");
	}
}

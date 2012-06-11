package edu.uconn.pha;

import org.json.JSONException;

import edu.uconn.model.ODL;
import edu.uconn.serverclient.ServerConnection;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class WellnessFormActivity extends Activity implements SeekBar.OnSeekBarChangeListener {

	private static final String TAG = WellnessFormActivity.class.getName();

	private Bundle extras;
	private Button cancelButton, saveDeleteButton;
	private ImageView energyImage, happinessImage, comfortImage, mobilityImage, appetiteImage;
	private int listIndex;
	private ODL odl;
	private RelativeLayout relativeLayout;
	private SeekBar energySeekBar, happinessSeekBar, comfortSeekBar, mobilitySeekBar, appetiteSeekBar;
	private TextView energyValue, happinessValue, comfortValue, mobilityValue, appetiteValue, textView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wellness_activity_form);
		setTitle(R.string.wellness_diary);

		// set the energy seek bar label text
		textView = (TextView) findViewById(R.id.wellness_activity_form_energy_label);
		textView.setText(R.string.energy);

		// initialize the energy seek bar and listener
		relativeLayout = (RelativeLayout) findViewById(R.id.wellness_activity_form_energy_seekbar);
		energyValue = (TextView) relativeLayout.findViewById(R.id.included_seek_progress);
		energyImage = (ImageView) relativeLayout.findViewById(R.id.included_seek_bar_image);
		energySeekBar = (SeekBar) relativeLayout.findViewById(R.id.included_seek_bar);
		energySeekBar.setOnSeekBarChangeListener(this);

		// set the happiness seek bar label text
		textView = (TextView) findViewById(R.id.wellness_activity_form_happiness_label);
		textView.setText(R.string.happiness);

		// initialize the happiness seek bar and listener
		relativeLayout = (RelativeLayout) findViewById(R.id.wellness_activity_form_happiness_seekbar);
		happinessValue = (TextView) relativeLayout.findViewById(R.id.included_seek_progress);
		happinessImage = (ImageView) relativeLayout.findViewById(R.id.included_seek_bar_image);
		happinessSeekBar = (SeekBar) relativeLayout.findViewById(R.id.included_seek_bar);
		happinessSeekBar.setOnSeekBarChangeListener(this);

		// set the comfort seek bar label text
		textView = (TextView) findViewById(R.id.wellness_activity_form_comfort_label);
		textView.setText(R.string.comfort);

		// initialize the comfort seek bar and listener
		relativeLayout = (RelativeLayout) findViewById(R.id.wellness_activity_form_comfort_seekbar);
		comfortValue = (TextView) relativeLayout.findViewById(R.id.included_seek_progress);
		comfortImage = (ImageView) relativeLayout.findViewById(R.id.included_seek_bar_image);
		comfortSeekBar = (SeekBar) relativeLayout.findViewById(R.id.included_seek_bar);
		comfortSeekBar.setOnSeekBarChangeListener(this);

		// set the mobility seek bar label text
		textView = (TextView) findViewById(R.id.wellness_activity_form_mobility_label);
		textView.setText(R.string.mobility);

		// initialize the mobility seek bar and listener
		relativeLayout = (RelativeLayout) findViewById(R.id.wellness_activity_form_mobility_seekbar);
		mobilityValue = (TextView) relativeLayout.findViewById(R.id.included_seek_progress);
		mobilityImage = (ImageView) relativeLayout.findViewById(R.id.included_seek_bar_image);
		mobilitySeekBar = (SeekBar) relativeLayout.findViewById(R.id.included_seek_bar);
		mobilitySeekBar.setOnSeekBarChangeListener(this);

		// set the appetite seek bar label text
		textView = (TextView) findViewById(R.id.wellness_activity_form_appetite_label);
		textView.setText(R.string.appetite);

		// initialize the appetite seek bar and listener
		relativeLayout = (RelativeLayout) findViewById(R.id.wellness_activity_form_appetite_seekbar);
		appetiteValue = (TextView) relativeLayout.findViewById(R.id.included_seek_progress);
		appetiteImage = (ImageView) relativeLayout.findViewById(R.id.included_seek_bar_image);
		appetiteSeekBar = (SeekBar) relativeLayout.findViewById(R.id.included_seek_bar);
		appetiteSeekBar.setOnSeekBarChangeListener(this);

		// determine if we are loading existing ODL data or display a fresh form
		extras = getIntent().getExtras();
		if(extras != null) {
			listIndex = extras.getInt("Index", -1);
		}

		// if we are loading existing data...
		if(listIndex > 0) {
			// disable the seek bars
			energySeekBar.setEnabled(false);
			happinessSeekBar.setEnabled(false);
			comfortSeekBar.setEnabled(false);
			mobilitySeekBar.setEnabled(false);
			appetiteSeekBar.setEnabled(false);

			// retrieve the existing data (listIndex is 1 based)
			try {
				odl = ServerConnection.getODLList().get(listIndex - 1 );
			} catch (JSONException e) {
				Log.e(TAG, "Error :: JSON Exception", e);
			}

			// set the seek bar values to the existing data
			if(odl != null) {
				energySeekBar.setProgress(odl.getEnergy());
				happinessSeekBar.setProgress(odl.getHappiness());
				comfortSeekBar.setProgress(odl.getComfort());
				mobilitySeekBar.setProgress(odl.getMobility());
				appetiteSeekBar.setProgress(odl.getAppetite());
			}
		}

		// initialize the save / delete button
		saveDeleteButton = (Button) findViewById(R.id.wellness_activity_form_save_button);
		if(listIndex > 0) {
			// set the button text to "Delete"
			saveDeleteButton.setText(getText(R.string.delete));
		}
		saveDeleteButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// deleting an entry
				if(listIndex > 0) {
					// disable the buttons
					saveDeleteButton.setEnabled(false);
					cancelButton.setEnabled(false);

					// delete the ODL entry from HealthVault and finish current activity
					boolean result = false;
					try {
						result = ServerConnection.deleteHealthItem(odl);
						if(result) {
							Log.i(TAG, "Deleted :: ODL Successfully");
							setResult(RESULT_OK);
							finish();
						}
					} catch (JSONException e) {
						Log.e(TAG, "Error :: JSON Exception", e);
					}
				} else {
					// saving an entry, perform input validation for each seek bar
					if(energySeekBar.getProgress() == 0) {
						Toast.makeText(getApplicationContext(), getText(R.string.energy_required), Toast.LENGTH_SHORT).show();
						return;
					}
					if(happinessSeekBar.getProgress() == 0) {
						Toast.makeText(getApplicationContext(), getText(R.string.happiness_required), Toast.LENGTH_SHORT).show();
						return;
					}
					if(comfortSeekBar.getProgress() == 0) {
						Toast.makeText(getApplicationContext(), getText(R.string.comfort_required), Toast.LENGTH_SHORT).show();
						return;
					}
					if(mobilitySeekBar.getProgress() == 0) {
						Toast.makeText(getApplicationContext(), getText(R.string.mobility_required), Toast.LENGTH_SHORT).show();
						return;
					}
					if(appetiteSeekBar.getProgress() == 0) {
						Toast.makeText(getApplicationContext(), getText(R.string.appetite_required), Toast.LENGTH_SHORT).show();
						return;
					}

					// disable the buttons
					saveDeleteButton.setEnabled(false);
					cancelButton.setEnabled(false);

					// save the wellness entry to HealthVault
					Log.i(TAG, "Saving :: New Wellness Diary Entry");

					// store the seek bar progress values into an ODL object
					odl = new ODL();
					odl.setAppetite(appetiteSeekBar.getProgress());
					odl.setMobility(mobilitySeekBar.getProgress());
					odl.setComfort(comfortSeekBar.getProgress());
					odl.setHappiness(happinessSeekBar.getProgress());
					odl.setEnergy(energySeekBar.getProgress());

					// save the ODL entry to HealthVault
					boolean result = true;
					try {
						result = ServerConnection.sendODLJSON(odl);
						if(result) {
							Log.i(TAG, "Saved :: ODL Successfully");
							setResult(RESULT_OK);
							finish();
						}
					} catch (JSONException e) {
						Log.e(TAG, "Error :: JSON Exception", e);
					}
				}
			}
		});

		// initialize the cancel button
		cancelButton = (Button) findViewById(R.id.wellness_activity_form_cancel_button);
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// close this activity
				finish();
			}
		});
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		// determine the current seek bar and update its image
		if(seekBar == energySeekBar) {
			setIndicator(energyImage, energyValue, progress);
		}
		if(seekBar == happinessSeekBar) {
			setIndicator(happinessImage, happinessValue, progress);
		}
		if(seekBar == comfortSeekBar) {
			setIndicator(comfortImage, comfortValue, progress);
		}
		if(seekBar == mobilitySeekBar) {
			setIndicator(mobilityImage, mobilityValue, progress);
		}
		if(seekBar == appetiteSeekBar) {
			setIndicator(appetiteImage, appetiteValue, progress);
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {}

	private void setIndicator(ImageView imageView, TextView textView, int progress) {
		// set image resource based on progress
		switch(progress) {
		case 1:
			imageView.setImageResource(R.drawable.ic_wellness_1);
			break;
		case 2:
			imageView.setImageResource(R.drawable.ic_wellness_2);
			break;
		case 3:
			imageView.setImageResource(R.drawable.ic_wellness_3);
			break;
		case 4:
			imageView.setImageResource(R.drawable.ic_wellness_4);
			break;
		case 5:
			imageView.setImageResource(R.drawable.ic_wellness_5);
			break;
		case 6:
			imageView.setImageResource(R.drawable.ic_wellness_6);
			break;
		case 7:
			imageView.setImageResource(R.drawable.ic_wellness_7);
			break;
		case 8:
			imageView.setImageResource(R.drawable.ic_wellness_8);
			break;
		case 9:
			imageView.setImageResource(R.drawable.ic_wellness_9);
			break;
		case 10:
			imageView.setImageResource(R.drawable.ic_wellness_10);
			break;
		case 0:
		default:
			imageView.setImageResource(R.drawable.ic_wellness_0);
			break;
		}
		// set the seek bar number based on progress
		textView.setText(Integer.toString(progress));
	}
}

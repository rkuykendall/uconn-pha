package edu.uconn.pha;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

import edu.uconn.model.ODLQuery;
import edu.uconn.serverclient.ServerConnection;

public class WellnessReportActivity extends SherlockFragmentActivity {

	private static final String TAG = WellnessReportActivity.class.getName();

	private static final int START_DATE_DIALOG_ID = 0;
	private static final int END_DATE_DIALOG_ID = 1;
	private static final String DATE_FORMAT = "MM-dd-yy";

	private Bundle bundle;
	private Button generateButton, cancelButton, startDateButton, endDateButton;
	private Calendar calendar;
	private CheckBox energyBox, happinessBox, comfortBox, mobilityBox, appetiteBox;
	private Date startDate, endDate;
	private DatePickerDialog.OnDateSetListener startDateListener, endDateListener;
	private int startDay, endDay, startMonth, endMonth, startYear, endYear;
	private Intent intent;
	private ODLQuery odlQuery;
	private SimpleDateFormat simpleDateFormat;
	private TextView textView;
	private URL url;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wellness_activity_report);
		setTitle(R.string.wellness_diary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// set the ODL selection header text
		textView = (TextView) findViewById(R.id.wellness_activity_report_odls_header);
		textView.setText(R.string.select_odls);

		// set the timeframe selection header text
		textView = (TextView) findViewById(R.id.wellness_activity_report_timeframe_header);
		textView.setText(R.string.select_timeframe);

		// initialize the check boxes
		energyBox = (CheckBox) findViewById(R.id.wellness_activity_report_energy_checkbox);
		happinessBox = (CheckBox) findViewById(R.id.wellness_activity_report_happiness_checkbox);
		comfortBox = (CheckBox) findViewById(R.id.wellness_activity_report_comfort_checkbox);
		mobilityBox = (CheckBox) findViewById(R.id.wellness_activity_report_mobility_checkbox);
		appetiteBox = (CheckBox) findViewById(R.id.wellness_activity_report_appetite_checkbox);

		// set the date format
		simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);

		// get the current date for each date listener
		calendar = Calendar.getInstance();
		startYear = calendar.get(Calendar.YEAR);
		endYear = calendar.get(Calendar.YEAR);
		startMonth = calendar.get(Calendar.MONTH);
		endMonth = calendar.get(Calendar.MONTH);
		startDay = calendar.get(Calendar.DAY_OF_MONTH);
		endDay = calendar.get(Calendar.DAY_OF_MONTH);

		// initialize the start date (from) button and listener
		startDateButton = (Button) findViewById(R.id.wellness_activity_report_from_button);
		startDateButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// display the date picker
				showDialog(START_DATE_DIALOG_ID);
			}
		});

		// initialize the start date (from) date dialog picker listener
		startDateListener = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				// set the button value after user chooses date and hits "set" button in date picker dialog
				startYear = year;
				startMonth = monthOfYear + 1;
				startDay = dayOfMonth;
				updateDateButton(startDateButton);
			}
		};

		// initialize the end date (to) button and listeners
		endDateButton = (Button) findViewById(R.id.wellness_activity_report_to_button);
		endDateButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// display the date picker
				showDialog(END_DATE_DIALOG_ID);
			}
		});
		endDateListener = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				// set the button value after user chooses date and hits "set" button in date picker dialog
				endYear = year;
				endMonth = monthOfYear + 1;
				endDay = dayOfMonth;
				updateDateButton(endDateButton);
			}
		};

		// initialize the generate button
		generateButton = (Button) findViewById(R.id.wellness_activity_report_generate_button);
		generateButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ensure that at least one ODL checkbox is checked before proceeding
				if(!energyBox.isChecked() && !happinessBox.isChecked() && !comfortBox.isChecked() && !mobilityBox.isChecked() && !appetiteBox.isChecked()) {
					Toast.makeText(getApplicationContext(), getText(R.string.select_odls_required), Toast.LENGTH_SHORT).show();
					return;
				}
				// ensure that a start date has been selected
				if(startDateButton.getText().equals("--/--/----")) {
					Toast.makeText(getApplicationContext(), getText(R.string.start_date_required), Toast.LENGTH_SHORT).show();
					return;
				}
				// ensure that an end date has been selected
				if(endDateButton.getText().equals("--/--/----")) {
					Toast.makeText(getApplicationContext(), getText(R.string.end_date_required), Toast.LENGTH_SHORT).show();
					return;
				}
				// ensure that start date is < end date
				try {
					startDate = simpleDateFormat.parse(startDateButton.getText().toString());
					endDate = simpleDateFormat.parse(endDateButton.getText().toString());
					if(startDate.after(endDate)) {
						Toast.makeText(getApplicationContext(), getText(R.string.incorrect_date_range), Toast.LENGTH_SHORT).show();
						return;
					}
				} catch (ParseException e) {
					Log.e(TAG, "Error :: Parsing Date Format", e);
				}
				
				// disable the buttons
				generateButton.setEnabled(false);
				cancelButton.setEnabled(false);

				// validation passed, generate the image
				Log.i(TAG, "Generating :: ODL Report Image");

				// initialize the ODL Query with start and end times
				odlQuery = new ODLQuery();
				odlQuery.setStartTime(startDateButton.getText().toString());
				odlQuery.setEndTime(endDateButton.getText().toString());

				// add items to ODL list
				addValueToList(energyBox);
				addValueToList(happinessBox);
				addValueToList(comfortBox);
				addValueToList(mobilityBox);
				addValueToList(appetiteBox);

				// generate ODL URL and pass it to ImageViewer Activity using a Bundle
				try {
					url = ServerConnection.generateReportJSON(odlQuery);
					Log.i(TAG, "Generated :: ODL Report Image");
					bundle = new Bundle();
					bundle.putString("url", url.toString());
					intent = new Intent(WellnessReportActivity.this, ImageViewerActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
					generateButton.setEnabled(true);
					cancelButton.setEnabled(true);
				} catch (MalformedURLException e) {
					Log.e(TAG, "Error :: Malformed URL");
				} catch (JSONException e) {
					Log.e(TAG, "Error :: JSON Exception", e);
				}
			}
		});

		// initialize the cancel button
		cancelButton = (Button) findViewById(R.id.wellness_activity_report_cancel_button);
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// close this activity
				finish();
			}
		});
	}

	// adds the check box item to the ODL list if checked
	protected void addValueToList(CheckBox checkBox) {
		if(checkBox.isChecked()) {
			odlQuery.addOdl(checkBox.getText().toString());
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		// creates a date picker dialog for each button
		switch (id) {
		case START_DATE_DIALOG_ID:
			return new DatePickerDialog(this, startDateListener, startYear, startMonth, startDay);
		case END_DATE_DIALOG_ID:
			return new DatePickerDialog(this, endDateListener, endYear, endMonth, endDay);
		}
		return null;
	}

	// formats the date values to be compatible with HealthVault
	private String formatDateValue(int i) {
		if(i < 10) {
			return "0" + i;
		} else {
			return Integer.toString(i);
		}
	}

	// updates the specified button with the selected (and HealthValut compatible) date value
	private void updateDateButton(Button button) {
		if(button == startDateButton) {
			button.setText(formatDateValue(startMonth) + "-" + formatDateValue(startDay) + "-" + formatDateValue(startYear));
		}
		if(button == endDateButton) {
			button.setText(formatDateValue(endMonth) + "-" + formatDateValue(endDay) + "-" + formatDateValue(endYear));
		}
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) 
	{
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}

package edu.uconn.pha;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONException;

import edu.uconn.listeners.CustomOnItemSelectedListener;
import edu.uconn.model.Medication;
import edu.uconn.serverclient.ServerConnection;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class RxOtcFormActivity extends Activity {
	
	private static final String TAG = RxOtcFormActivity.class.getName();
	
	private static final int START_DATE_DIALOG_ID = 0;
	private static final int END_DATE_DIALOG_ID = 1;
	private static final String DATE_FORMAT = "MM-dd-yy";
	
	private ArrayAdapter<CharSequence> adapter;
	private Button startButton, endButton;
	private Spinner frequencySpinner, howTakenSpinner, strengthSpinner;
	private TextView textView;
	private Boolean prescribed = null;
	private int listIndex = -1;
	private Medication med = null;
	private Button cancelButton, saveButton;
	
	private Calendar calendar;
	private DatePickerDialog.OnDateSetListener startDateListener, endDateListener;
	private int startDay, endDay, startMonth, endMonth, startYear, endYear;
	private SimpleDateFormat simpleDateFormat;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prescriptions_activity_form);
        setTitle(R.string.prescription_drugs);
        
        //TODO CLEAN UP THIS FILE, REMOVE THE PRESCRIPTIONS_ACTIVITY_FORM XML (USED FOR DEMO), AND STREAMLINE INTO NEW CODE
        
        Bundle extras = getIntent().getExtras();
        if(extras != null);
        {
        	prescribed = new Boolean(extras.getBoolean("Prescribed"));
        	listIndex = extras.getInt("GlobalIndex", -1);
        }
        
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
		startButton = (Button) findViewById(R.id.prescription_activity_form_date_started_button);
		startButton.setOnClickListener(new OnClickListener() {
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
				updateDateButton(startButton);
			}
		};

		// initialize the end date (to) button and listeners
		endButton = (Button) findViewById(R.id.prescription_activity_form_date_discontinued_button);
		endButton.setOnClickListener(new OnClickListener() {
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
				updateDateButton(endButton);
			}
		};
        
        // set the prescription details label text
		textView = (TextView) findViewById(R.id.prescription_activity_form_details_label);
		textView.setText(R.string.medication_details);
		
		// initialize the medication strength spinner
		strengthSpinner = (Spinner) findViewById(R.id.prescription_activity_form_strength_spinner);
		adapter = ArrayAdapter.createFromResource(this, R.array.medication_strength_units, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		strengthSpinner.setAdapter(adapter);
		strengthSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(strengthSpinner));

		// initialize the how taken spinner
		howTakenSpinner = (Spinner) findViewById(R.id.prescription_activity_form_how_taken_spinner);
		adapter = ArrayAdapter.createFromResource(this, R.array.medication_how_taken, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		howTakenSpinner.setAdapter(adapter);
		howTakenSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(howTakenSpinner));
		
		// initialize the frequency spinner
		frequencySpinner = (Spinner) findViewById(R.id.prescription_activity_form_frequency_spinner);
		adapter = ArrayAdapter.createFromResource(this, R.array.medication_frequencies, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		frequencySpinner.setAdapter(adapter);
		frequencySpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(frequencySpinner));
		
		if(prescribed != null && prescribed.booleanValue() == false)
		{
			textView = (TextView) findViewById(R.id.prescription_activity_form_prescribed_by_label);
			textView.setVisibility(TextView.GONE);
			textView = (TextView) findViewById(R.id.prescription_activity_form_prescribed_by_field);
			textView.setVisibility(TextView.GONE);
		}
		
		saveButton = (Button) findViewById(R.id.prescription_activity_form_save_button);
        saveButton.setOnClickListener(
        new OnClickListener() 
        {
        	
        	public void onClick(View v) 
        	{
        		// do nothing for now
        		
        		Medication newMed = new Medication();
        	    newMed.setName(getFieldText(R.id.prescription_activity_form_name_field));
        	    newMed.setNote(getFieldText(R.id.prescription_activity_form_reason_field));
        	    newMed.setStrength(getFieldText(R.id.prescription_activity_form_strength_field) + ";" + getSpinnerText(R.id.prescription_activity_form_strength_spinner)); 
        	    newMed.setHowTaken(getSpinnerText(R.id.prescription_activity_form_how_taken_spinner));
        	    newMed.setFrequency(getFieldText(R.id.prescription_activity_form_frequency_field) + ";" + getSpinnerText(R.id.prescription_activity_form_frequency_spinner));
        	    
    		 	String start = (String) startButton.getText();
    		 	String end = (String) endButton.getText();
        	    newMed.setDateStarted(start);        	    
        	    newMed.setDateDiscontinued(end);
        	    
        	    if(prescribed != null)
        	    {
        	    	if(prescribed.booleanValue() == true)
        	    	{
        	    		newMed.setPrescribed(getFieldText(R.id.prescription_activity_form_prescribed_by_field));
        	    	}
        	    }        	         	    

        	    try
        	    {
        	    	saveButton.setEnabled(false);
        	    	cancelButton.setEnabled(false);
					ServerConnection.addMedicationInfo(newMed);					
					setResult(RESULT_OK);
					finish();
				} 
        	    catch (JSONException e) 
        	    {				
					Log.e(TAG, e.toString());
				}
        		
        		Log.v(TAG, newMed.toString());
        		Log.i(TAG, "Saving :: New Prescription");
        	}
        	
        	private String getFieldText(int i)
    	    {
    	    	 EditText et = (EditText) findViewById(i);
    	    	 return et.getText().toString();
    	    }
        	
        	private String getSpinnerText(int i)
        	{
        		Spinner spinner = (Spinner) findViewById(i);
        		if(spinner.getOnItemSelectedListener() instanceof CustomOnItemSelectedListener)
        		{
        			CustomOnItemSelectedListener listener = (CustomOnItemSelectedListener) spinner.getOnItemSelectedListener();
        			return listener.getValue();
        		}
        		else
        		{
        			// do nothing
        			 return "";
        		}
        		
        	}
        	
        }); 
        
     // initialize the cancel button
		cancelButton = (Button) findViewById(R.id.prescription_activity_form_cancel_button);
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// close this activity
				finish();
			}
		});
        
        // Zero based list.....
        if(listIndex > -1)
        {
        	// Get the medication from the global index.
        	try 
    		{
        		// real index 0 based.
				med = ServerConnection.getMedicationsJSON().get(listIndex);
				
				textView = (TextView) findViewById(R.id.prescription_activity_form_name_field);
				textView.setText(med.getName());
				
				textView = (TextView) findViewById(R.id.prescription_activity_form_reason_field);
				textView.setText(med.getNote());
				
				textView = (TextView) findViewById(R.id.prescription_activity_form_strength_field);
				String[] strengthArray = med.getStrength().split(";");				
				textView.setText(strengthArray[0]);
				
				textView = (TextView) findViewById(R.id.prescription_activity_form_frequency_field);
				String[] freqArray = med.getFrequency().split(";");				
				textView.setText(freqArray[0]);
				
				startButton = (Button) findViewById(R.id.prescription_activity_form_date_started_button);
				startButton.setText(med.getDateStarted());
				
				endButton = (Button) findViewById(R.id.prescription_activity_form_date_discontinued_button);
				endButton.setText(med.getDateDiscontinued());
				
				ArrayAdapter arrayAdapter;
				int pos;
				// "If" statements cover for old data.
				if(strengthArray.length > 1)
				{
					arrayAdapter = (ArrayAdapter)strengthSpinner.getAdapter();
					pos = arrayAdapter.getPosition(strengthArray[1]);
					strengthSpinner.setSelection(pos);
				}
				
				if(freqArray.length > 1)
				{
					arrayAdapter = (ArrayAdapter)frequencySpinner.getAdapter();
					pos = arrayAdapter.getPosition(freqArray[1]);
					frequencySpinner.setSelection(pos);
				}
				
			} 
    		catch (JSONException e1) 
    		{
    			Log.e(TAG, e1.toString());
			}
        	
        	 saveButton.setText("Update");
        	 saveButton.setOnClickListener(
		        new OnClickListener() 
		        {
		        	
		        	public void onClick(View v) 
		        	{		        	
		        		if(med != null)
		        		{
			        		med.setName(getFieldText(R.id.prescription_activity_form_name_field));
			        		med.setNote(getFieldText(R.id.prescription_activity_form_reason_field));
			        		med.setStrength(getFieldText(R.id.prescription_activity_form_strength_field) + ";" + getSpinnerText(R.id.prescription_activity_form_strength_spinner)); 
			        		med.setHowTaken(getSpinnerText(R.id.prescription_activity_form_how_taken_spinner));
			        		med.setFrequency(getFieldText(R.id.prescription_activity_form_frequency_field) + ";" + getSpinnerText(R.id.prescription_activity_form_frequency_spinner));
			        		
			        		String start = (String) startButton.getText();
			    		 	String end = (String) endButton.getText();
			        	    med.setDateStarted(start);        	    
			        	    med.setDateDiscontinued(end);
			        		if(prescribed != null)
			        	    {
			        	    	if(prescribed.booleanValue() == true)
			        	    	{
			        	    		med.setPrescribed(getFieldText(R.id.prescription_activity_form_prescribed_by_field));
			        	    	}
			        	    }     
			        		try
			        		{
			        			saveButton.setEnabled(false);
			        	    	cancelButton.setEnabled(false);
			        			ServerConnection.updateMedicationInfo(med);
			        			Log.v(TAG, "Medication updated successfully. Returning.");
			        			setResult(RESULT_OK);
			        			finish();
			        		}
			        		catch(JSONException e)
			        		{
			        			Log.e(TAG, e.toString());
			        		}
		        		}
		        	}
		        });
        	 
        	 cancelButton.setText("Delete");
        	 cancelButton.setOnClickListener(new OnClickListener() {
     			@Override
     			public void onClick(View v) 
     			{
     				if(med != null)
     				{
     					boolean result = false;
     					try 
     					{
     						saveButton.setEnabled(false);
     	        	    	cancelButton.setEnabled(false);
							result = ServerConnection.deleteHealthItem(med);
						} 
     					catch (JSONException e) 
     					{
     						Log.e(TAG, e.toString());
						}
     					
     					if(result)
     					{
     						// 	close this activity
     						Log.v(TAG, "Medication deleted successfully. Returning.");
     						setResult(RESULT_OK);
     						finish();
     						
     					}
     				}
     				
     			}
     		});
        }
		
		
    }
    
    private String getFieldText(int i)
    {
    	 EditText et = (EditText) findViewById(i);
    	 return et.getText().toString();
    }
	
	private String getSpinnerText(int i)
	{
		Spinner spinner = (Spinner) findViewById(i);
		if(spinner.getOnItemSelectedListener() instanceof CustomOnItemSelectedListener)
		{
			CustomOnItemSelectedListener listener = (CustomOnItemSelectedListener) spinner.getOnItemSelectedListener();
			return listener.getValue();
		}
		else
		{
			// do nothing
			 return "";
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
		if(button == startButton) {
			button.setText(formatDateValue(startMonth) + "-" + formatDateValue(startDay) + "-" + formatDateValue(startYear));
		}
		if(button == endButton) {
			button.setText(formatDateValue(endMonth) + "-" + formatDateValue(endDay) + "-" + formatDateValue(endYear));
		}
	}
}

package edu.uconn.listeners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

public class CustomOnItemSelectedListener implements OnItemSelectedListener {

	private String value = "";
	
	public CustomOnItemSelectedListener(Spinner spinner)
	{
		super();
		// TODO REMOVE THIS FILE AND INTEGRATE DIRECTLY WITH ACTIVITY CLASSES
		
		// Get the default value in case they don't click anything.
		value = spinner.getItemAtPosition(0).toString();
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) 
	{
		value = parent.getItemAtPosition(pos).toString();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// Do nothing

	}

	public String getValue() {
		return value;
	}

}

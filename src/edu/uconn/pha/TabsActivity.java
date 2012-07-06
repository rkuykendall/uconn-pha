package edu.uconn.pha;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class TabsActivity extends TabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs_activity);
		setTitle(R.string.app_name);

		// resource object to get drawables
		Resources res = getResources();
		// the activity TabHost
		TabHost tabHost = getTabHost();
		// reusable TabSpec for each tab
		TabHost.TabSpec spec;
		// reusable intent for each tab
		Intent intent;

		// === Creating Tabs
		// 1) Create an intent to launch an activity for the tab (to be reused)
		// 2) Initialize a TabSpec for each tab and add it to the TabHost
		// 3) Add the tabSpec to the tabHost

		// Home
		intent = new Intent().setClass(this, HomeActivity.class);
		spec = tabHost.newTabSpec("home").setIndicator(getString(R.string.home), res.getDrawable(R.drawable.ic_tab_home)).setContent(intent);
		tabHost.addTab(spec);
		
		// Wellness
		intent = new Intent().setClass(this, WellnessActivity.class);
		spec = tabHost.newTabSpec("wellness").setIndicator(getString(R.string.wellness_diary), res.getDrawable(R.drawable.ic_tab_wellness)).setContent(intent);
		tabHost.addTab(spec);
		
		// Permissions
		intent = new Intent().setClass(this, PermissionsActivity.class);
		spec = tabHost.newTabSpec("permissions").setIndicator(getString(R.string.permissions), res.getDrawable(R.drawable.ic_tab_permissions)).setContent(intent);
		tabHost.addTab(spec);

		// Alarms
//		intent = new Intent().setClass(this, AlarmsActivity.class);
//		spec = tabHost.newTabSpec("alarms").setIndicator(getString(R.string.alarms), res.getDrawable(R.drawable.ic_tab_alarms)).setContent(intent);
//		tabHost.addTab(spec);

		// Medications
		intent = new Intent().setClass(this, MedicationsActivity.class);
		spec = tabHost.newTabSpec("medications").setIndicator(getString(R.string.medications), res.getDrawable(R.drawable.ic_tab_medications)).setContent(intent);
		tabHost.addTab(spec);
		
		// Allergies
//		intent = new Intent().setClass(this, AllergiesActivity.class);
//		spec = tabHost.newTabSpec("allergies").setIndicator(getString(R.string.allergies), res.getDrawable(R.drawable.ic_tab_allergies)).setContent(intent);
//		tabHost.addTab(spec);

		// set the current tab ( default to  Home )
		tabHost.setCurrentTab(0);
	}
}

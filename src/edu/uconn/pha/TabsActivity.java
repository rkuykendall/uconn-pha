package edu.uconn.pha;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

public class TabsActivity extends SherlockFragmentActivity {
	private static final String TAG = TabsActivity.class.getName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs_activity);
		setTitle(R.string.app_name);

		Log.v(TAG, "Started Home Activity.");

		ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
		// bar.setDisplayHomeAsUpEnabled(true);
		bar.setDisplayShowHomeEnabled(true);
		bar.setDisplayShowTitleEnabled(true);
		bar.addTab(bar
				.newTab()
				.setText(R.string.home)
				.setTabListener(
						new TabListener<HomeFragment>(this, this.getString(R.string.home),
								HomeFragment.class, null)));
		bar.addTab(bar
				.newTab()
				.setText(R.string.wellness_diary)
				.setTabListener(
						new TabListener<WellnessFragment>(this, this.getString(R.string.wellness_diary),
								WellnessFragment.class, null)));
		bar.addTab(bar
				.newTab()
				.setText(R.string.alarms)
				.setTabListener(
						new TabListener<AlarmsFragment>(this, this.getString(R.string.alarms),
								AlarmsFragment.class, null)));

		bar.addTab(bar
				.newTab()
				.setText(R.string.permissions)
				.setTabListener(
						new TabListener<PermissionsFragment>(this, this.getString(R.string.permissions),
								PermissionsFragment.class, null)));
		bar.addTab(bar
				.newTab()
				.setText(R.string.medications)
				.setTabListener(
						new TabListener<MedicationsFragment>(this, this.getString(R.string.medications),
								MedicationsFragment.class, null)));
		bar.addTab(bar
				.newTab()
				.setText(R.string.allergies)
				.setTabListener(
						new TabListener<AllergiesFragment>(this, this.getString(R.string.allergies),
								AllergiesFragment.class, null)));


		if (savedInstanceState != null) {
			bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
		}
	}

	public class TabListener<T extends Fragment> implements
	ActionBar.TabListener {
		private final FragmentActivity mActivity;
		private final String mTag;
		private final Class<T> mClass;
		private final Bundle mArgs;
		private Fragment mFragment;

		public TabListener(FragmentActivity activity, String tag, Class<T> clz, Bundle args) {
			mActivity = activity;
			mTag = tag;
			mClass = clz;
			mArgs = args;
			FragmentTransaction ft = mActivity.getSupportFragmentManager()
					.beginTransaction();

			// Check to see if we already have a fragment for this tab, probably
			// from a previously saved state. If so, deactivate it, because our
			// initial state is that a tab isn't shown.
			mFragment = mActivity.getSupportFragmentManager().findFragmentByTag(mTag);
			if (mFragment != null && !mFragment.isDetached()) {
				ft.detach(mFragment);
			}
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			ft = mActivity.getSupportFragmentManager().beginTransaction();

			if (mFragment == null) {
				mFragment = Fragment.instantiate(mActivity, mClass.getName(), mArgs);
				ft.add(android.R.id.content, mFragment, mTag);
				ft.commit();
			} else {
				ft.attach(mFragment);
				ft.commit();
			}
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			ft = mActivity.getSupportFragmentManager()
					.beginTransaction();

			if (mFragment != null) {
				ft.detach(mFragment);
				ft.commitAllowingStateLoss();
			}

		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
		}
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) 
	{
		switch (item.getItemId()) {
		case android.R.id.home:
			getActionBar().setSelectedNavigationItem(0);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
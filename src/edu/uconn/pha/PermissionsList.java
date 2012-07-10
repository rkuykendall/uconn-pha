package edu.uconn.pha;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import edu.uconn.model.Permission;
import edu.uconn.model.Policy;

public class PermissionsList extends BaseAdapter {
	private static final String TAG = PermissionsList.class.getName();
	private Policy policy;
	private PermissionsActivity context;

	/**
	 * @param context
	 * @param policy
	 */
	public PermissionsList(Context context, Policy policy) {
		this.policy = policy;
		this.context = (PermissionsActivity) context;
		Log.v(TAG, "PermissionsList Constructed.");
	}

	@Override
	public Object getItem(int position) {
		return policy.getRole(position);
	}

	@Override
	public int getCount() 
	{   
		return policy.numRoles();
	}

	@Override
	public long getItemId(int position) 
	{   
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) 
	{			
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(R.layout.permissions_role, parent, false);
		TextView role = (TextView) view.findViewById(R.id.role);
		role.setText(policy.getRole(position).getName());


		CheckBox cb;
		Permission permission;

		permission = policy.getRole(position).getPermission(0);
		cb = (CheckBox) view.findViewById( R.id.wellness_read );
		cb.setChecked( permission.canRead() );
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
              Log.d(TAG, "--- Read position: " +position+" "+isChecked+" ---");
				policy.setPermissionRead(position, 0, isChecked);
			}
		});
		cb = (CheckBox) view.findViewById( R.id.wellness_write );
		cb.setChecked( permission.canWrite() );
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
              Log.d(TAG, "--- Write position: " +position+" "+isChecked+" ---");
				policy.setPermissionWrite(position, 0, isChecked);
			}
		});


		permission = policy.getRole(position).getPermission(1);
		cb = (CheckBox) view.findViewById( R.id.medications_read );
		cb.setChecked( permission.canRead() );
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
              Log.d(TAG, "--- Read position: " +position+" "+isChecked+" ---");
				policy.setPermissionRead(position, 1, isChecked);
			}
		});
		cb = (CheckBox) view.findViewById( R.id.medications_write );
		cb.setChecked( permission.canWrite() );
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
              Log.d(TAG, "--- Write position: " +position+" "+isChecked+" ---");
				policy.setPermissionWrite(position, 1, isChecked);
			}
		});



		permission = policy.getRole(position).getPermission(2);
		cb = (CheckBox) view.findViewById( R.id.allergies_read );
		cb.setChecked( permission.canRead() );
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
              Log.d(TAG, "--- Read position: " +position+" "+isChecked+" ---");
				policy.setPermissionRead(position, 2, isChecked);
			}
		});
		cb = (CheckBox) view.findViewById( R.id.allergies_write );
		cb.setChecked( permission.canWrite() );
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
              Log.d(TAG, "--- Write position: " +position+" "+isChecked+" ---");
				policy.setPermissionWrite(position, 2, isChecked);
			}
		});

		return view;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}
	
	@Override
	public boolean hasStableIds()
	{
		return true;
	}
}

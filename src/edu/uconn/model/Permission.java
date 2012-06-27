package edu.uconn.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Permission extends HealthItem {
	private static final String TAG = Permission.class.getName();
	private String name;
	private boolean read;
	private boolean write;
	
	public JSONObject toJSONObject() throws JSONException {
		JSONObject jo = super.toJSONObject();
		jo.put("Name", name);
		jo.put("Read", read);
		jo.put("Write", write);
		return jo;
	}

	public Permission(String name, boolean read, boolean write) {
		this.name = name;
		this.read = read;
		this.write = write;
	}
	public Permission(JSONObject jo) throws JSONException {
		this.name = jo.getString("Name");
		this.read = jo.getBoolean("Read");
		this.write = jo.getBoolean("Write");
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return read privilege
	 */
	public boolean canRead() {
		return read;
	}
	
	/**
	 * @param read the read privilege to set
	 */
	public void setRead(boolean read) {
		this.read = read;
        Log.d(TAG, "Read set on " + name+" to "+read+".");
	}
	
	/**
	 * @return write privilege
	 */
	public boolean canWrite() {
		return write;
	}
	
	/**
	 * @param write the write privilege to set
	 */
	public void setWrite(boolean write) {
		this.write = write;
        Log.d(TAG, "Read set on " + name+" to "+write+".");
	}
	

}

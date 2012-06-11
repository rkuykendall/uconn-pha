package edu.uconn.model;

import org.json.JSONException;
import org.json.JSONObject;

public class HealthItem {

	private static final String BLANK = "";

	protected String key = "";

	public HealthItem() {};

	public HealthItem(JSONObject jo) throws JSONException {
		key = jo.getString("Key");
	}

	protected String jsonNull(String input) {
		if((input == null) || (input.trim().equals(BLANK))) {
			return null;
		} else {
			return input;
		}
	}

	public void setKey(JSONObject jo) throws JSONException {
		this.key = jo.getString("Key");
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public JSONObject toJSONObject() throws JSONException {
		JSONObject jo = new JSONObject();
		jo.put("Key", jsonNull(key));

		return jo;
	}
}

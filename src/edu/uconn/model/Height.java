package edu.uconn.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Height extends HealthItem {

	private String heightValue = "";
	private String when = "";

	public Height() {};

	public Height(JSONObject jo) throws JSONException {
		super(jo);
		heightValue = jo.optString("HeightValue");
		when = jo.optString("When");
	}

	public JSONObject toJSONObject() throws JSONException {
		JSONObject jo = super.toJSONObject();

		return jo;
	}

	public String getHeightValue() {
		return heightValue;
	}

	public void setHeightValue(String heightValue) {
		this.heightValue = heightValue;
	}

	public String getWhen() {
		return when;
	}

	public void setWhen(String when) {
		this.when = when;
	}	

	@Override
	public String toString() {
		return "Height [heightValue=" + heightValue + ", when=" + when + ", key=" + key + "]";
	}
}

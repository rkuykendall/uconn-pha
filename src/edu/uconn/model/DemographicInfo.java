package edu.uconn.model;

import org.json.JSONException;
import org.json.JSONObject;

public class DemographicInfo extends HealthItem {

	private String gender = "Unknown";

	public DemographicInfo(JSONObject json) throws JSONException {
		super(json);		
		this.gender = json.optString("gender");
	}

	public JSONObject toJSONObject() throws JSONException {
		JSONObject jo = super.toJSONObject();
		jo.put("gender", jsonNull(gender));

		return jo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "DemographicInfo [gender=" + gender + ", key=" + key + "]";
	}
}

package edu.uconn.model;

import java.text.NumberFormat;

import org.json.JSONException;
import org.json.JSONObject;

public class Weight extends HealthItem {

	private static final double CONVERSION_VALUE = 2.20462262185;

	private String weightValue = "";

	public Weight() {};

	public Weight(JSONObject jo) throws JSONException {
		super(jo);
		weightValue = jo.optString("WeightValue");		
	}

	public JSONObject toJSONObject() throws JSONException {
		JSONObject jo = super.toJSONObject();

		return jo;
	}

	public String getWeightValue() {
		return weightValue;
	}

	public void setWeightValue(String weightValue) {
		this.weightValue = weightValue;
	}

	@Override
	public String toString() {
		return "Weight [weightValue=" + weightValue + ", key=" + key + "]";
	}

	public String getLbsValue() {
		if(weightValue == null || weightValue.equals("")) {
			return "";
		} else {
			Double weightKg = Double.parseDouble(weightValue);
			double weightLb = weightKg * CONVERSION_VALUE;
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(0);
			return nf.format(weightLb);			
		}
	}
}

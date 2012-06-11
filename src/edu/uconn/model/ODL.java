package edu.uconn.model;

import org.json.JSONException;
import org.json.JSONObject;

public class ODL extends HealthItem {

	private int energy = 0;
	private int happiness = 0;
	private int comfort = 0;
	private int mobility = 0;
	private int appetite = 0;
	private String date = "No Date Specified.";

	public ODL() {};

	public ODL(JSONObject jo) throws JSONException {
		super(jo);
		energy = jo.optInt("Energy", 0);
		happiness = jo.optInt("Happiness", 0);
		comfort = jo.optInt("Comfort", 0);
		mobility = jo.optInt("Mobility", 0);
		appetite = jo.optInt("Appetite", 0);
		date = jo.optString("dateString");
	}

	public JSONObject toJSONObject() throws JSONException {
		JSONObject jo = super.toJSONObject();
		jo.put("Energy", energy);
		jo.put("Happiness", happiness);
		jo.put("Comfort", comfort);
		jo.put("Mobility", mobility);
		jo.put("Appetite", appetite);

		return jo;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public int getEnergy() {
		return energy;
	}

	public void setHappiness(int happiness) {
		this.happiness = happiness;
	}

	public int getHappiness() {
		return happiness;
	}

	public void setComfort(int comfort) {
		this.comfort = comfort;
	}

	public int getComfort() {
		return comfort;
	}

	public void setMobility(int mobility) {
		this.mobility = mobility;
	}

	public int getMobility() {
		return mobility;
	}

	public void setAppetite(int appetite) {
		this.appetite = appetite;
	}

	public int getAppetite() {
		return appetite;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "ODL [energy=" + energy + ", happiness=" + happiness + ", comfort=" + comfort + ", mobility=" + mobility + ", appetite=" + appetite + ", date=" + date + ", key=" + key + "]";
	}
}

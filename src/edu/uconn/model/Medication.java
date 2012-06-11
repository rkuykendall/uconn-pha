package edu.uconn.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Medication extends HealthItem {

	private String name = "";
	private String strength = "";
	private String dose = "";
	private String howTaken = "";
	private String frequency = "";
	private String prescribed = ""; // is prescription or not
	private String dateStarted = "";
	private String dateDiscontinued = "";
	private String note = ""; // reason for taking medication

	public Medication() {};

	public Medication(JSONObject jo) throws JSONException {
		super(jo);
		name = jo.optString("Name");
		strength = jo.optString("Strength");
		dose = jo.optString("Dose");
		howTaken = jo.optString("HowTaken");
		frequency = jo.optString("Frequency");
		prescribed = jo.optString("Prescribed");
		dateStarted = jo.optString("DateStarted");
		dateDiscontinued = jo.optString("DateDiscontinued");
		note = jo.optString("Note");
	}

	public JSONObject toJSONObject() throws JSONException {
		JSONObject jo = super.toJSONObject();
		jo.put("Name", jsonNull(name));
		jo.put("Strength", jsonNull(strength));
		jo.put("Dose", jsonNull(dose));
		jo.put("HowTaken", jsonNull(howTaken));
		jo.put("Frequency", jsonNull(frequency));
		jo.put("Prescribed", jsonNull(prescribed));
		jo.put("DateStarted", jsonNull(dateStarted));
		jo.put("DateDiscontinued", jsonNull(dateDiscontinued));
		jo.put("Note", jsonNull(note));

		return jo;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

	public String getStrength() {
		return strength;
	}

	public void setDose(String dose) {
		this.dose = dose;
	}

	public String getDose() {
		return dose;
	}

	public void setHowTaken(String howTaken) {
		this.howTaken = howTaken;
	}

	public String getHowTaken() {
		return howTaken;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setPrescribed(String prescribed) {
		this.prescribed = prescribed;
	}

	public String getPrescribed() {
		return prescribed;
	}

	public void setDateStarted(String dateStarted) {
		this.dateStarted = dateStarted;
	}

	public String getDateStarted() {
		return dateStarted;
	}

	public void setDateDiscontinued(String dateDiscontinued) {
		this.dateDiscontinued = dateDiscontinued;
	}

	public String getDateDiscontinued() {
		return dateDiscontinued;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNote() {
		return note;
	}

	@Override
	public String toString() {
		return "Medication [dateDiscontinued=" + dateDiscontinued + ", dateStarted=" + dateStarted + ", dose=" + dose + ", frequency=" + frequency + ", howTaken=" + howTaken + ", name=" + name + ", note=" + note + ", prescribed=" + prescribed + ", strength=" + strength + ", key=" + key + "]";
	}

	public boolean isPrescribed() {
		if(prescribed != null && prescribed.length() > 0 && !prescribed.equals("null")) {
			return true;
		}
		else {
			return false;
		}
	}
}

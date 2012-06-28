package edu.uconn.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Person extends HealthItem {

	private String firstName = "";
	private String lastName = "";
	private String birthDate = "";
	private String bloodType = "";
	private String race = "";
	private Height height = null;
	private Weight weight = null;
	private DemographicInfo basicInfo = null;

	public Person() {};

	public Person(JSONObject jo) throws JSONException {
		super(jo);
		firstName = jo.optString("firstName");
		lastName = jo.optString("lastName");
		birthDate = jo.optString("birthDate");
		bloodType = jo.optString("bloodType");
		race = jo.optString("race");
		height = new Height(jo.getJSONObject("height"));
		weight = new Weight(jo.getJSONObject("weight"));
		basicInfo = new DemographicInfo(jo.getJSONObject("basicInfo"));		
	}

	public JSONObject toJSONObject() throws JSONException {
		JSONObject jo = super.toJSONObject();
		
		jo.put("firstName", firstName);
		jo.put("lastName", lastName);
		jo.put("birthDate", birthDate);
		jo.put("bloodType", bloodType);
		jo.put("race", race);
		jo.put("height", height.toJSONObject());
		jo.put("weight", weight.toJSONObject());
		jo.put("basicInfo", basicInfo.toJSONObject());

		return jo;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public String getRace() {
		return race;
	}

	public void setHeight(Height height) {
		this.height = height;
	}

	public Height getHeight() {
		return height;
	}

	public void setWeight(Weight weight) {
		this.weight = weight;
	}

	public Weight getWeight() {
		return weight;
	}

	public DemographicInfo getBasicInfo() {
		return basicInfo;
	}

	public void setBasicInfo(DemographicInfo basicInfo) {
		this.basicInfo = basicInfo;
	}

	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", lastName=" + lastName + ", birthDate=" + birthDate + ", bloodType=" + bloodType + ", race=" + race + ", height=" + height.toString() + ", weight=" + weight.toString() + ", basicInfo=" + basicInfo.toString() + ", key=" + key + "]";
	}
}

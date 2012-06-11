package edu.uconn.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ODLQuery {

	private static final String BLANK = "";

	public List<String> ODL_List = new ArrayList<String>();
	public String startTime = "";
	public String endTime = "";
	public String url = "";

	public JSONObject toJSONObject() throws JSONException {
		JSONObject jo = new JSONObject();
		jo.put("startTime", jsonNull(startTime));
		jo.put("endTime", jsonNull(endTime));
		jo.put("url", jsonNull(url));
		JSONArray ja = new JSONArray(ODL_List);
		jo.put("ODL_List", ja);	

		return jo;
	}

	protected String jsonNull(String input) {
		if((input == null) || (input.trim().equals(BLANK))) {
			return null;
		} else {
			return input;
		}
	}

	public void addOdl(String input) {
		ODL_List.add(input);
	}

	public void setODL_List(List<String> oDL_List) {
		ODL_List = oDL_List;
	}

	public List<String> getODL_List() {
		return ODL_List;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return "ODLQuery [startTime=" + startTime + ", endTime=" + endTime + ", url=" + url + ", ODL_List=" + ODL_List + "]";
	}
}

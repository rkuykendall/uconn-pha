package edu.uconn.serverclient;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import edu.uconn.model.HealthItem;
import edu.uconn.model.Medication;
import edu.uconn.model.ODL;
import edu.uconn.model.ODLQuery;
import edu.uconn.model.Person;
import edu.uconn.model.Policy;

public class ServerConnection {              
	// Allows for printing to Log
	private static final String TAG = "ServerConnection";

	private static final String publicId = "52f7e842-af54-4a64-9218-917e34190857";
	private static final String recordId = "917fd71d-6d7c-4ae7-a3a6-ef953e06bbdb";

	private static ArrayList<ODL> odlList = null;
	private static ArrayList<Medication> medicationList = null;
	private static Person person = null;
	private static Policy policy = null;

	public static synchronized ArrayList<ODL> getODLList(boolean forceUpdate) 
	throws JSONException
	{
		ODLQuery odlQuery = new ODLQuery();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -30);

		Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.DAY_OF_MONTH, 1);	 

		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		String startTime = sdf.format(calendar.getTime());
		String endTime = sdf.format(calendar2.getTime());


		odlQuery.setStartTime(startTime);
		odlQuery.setEndTime(endTime);

		return getODLList(odlQuery, forceUpdate);		
	}

	public static synchronized ArrayList<ODL> getODLList() 
	throws JSONException
	{
		return getODLList(false);
	}

	public static synchronized ArrayList<ODL> getODLList(ODLQuery odlQuery, boolean forceUpdate)//String publicId, String recordId)
	throws JSONException 
	{
		if(odlList == null || forceUpdate)
		{
			String action = "/ODL/getODLList/?";	
			String params = generateGetPubRecParams();
			Log.v(TAG, odlQuery.toString());
			String response = ServerConnectionHelper.serverRequestPost((action + params), odlQuery.toJSONObject());
			JSONArray json = ServerConnectionHelper.toJSONArray(response);
			ArrayList<ODL> list = new ArrayList<ODL>();

			for(int i = 0; i < json.length(); i++)
			{
				JSONObject jo = json.getJSONObject(i);
				list.add(new ODL(jo));
			}
			odlList = list;
		}
		return odlList;

	}

	public static synchronized Policy getPolicy() throws JSONException {
//		String response = "{ \"Roles\":[ { \"Name\":\"Primary care physician\"," +
//				" \"Permissions\":[ { \"Name\":\"Wellness\", \"Write\":true, " +
//				"\"Read\":true }, { \"Name\":\"Medications\", \"Write\":true, " +
//				"\"Read\":true }, { \"Name\":\"Allergies\", \"Write\":true, " +
//				"'\"Read\":true } ] }, { \"Name\":\"Clinical Pharmacist\", " +
//				"\"Permissions\":[ { \"Name\":\"Wellness\", \"Write\":true, " +
//				"\"Read\":true }, { \"Name\":\"Medications\", \"Write\":true, " +
//				"\"Read\":true }, { \"Name\":\"Allergies\", \"Write\":true, " +
//				"\"Read\":true } ] }, { \"Name\":\"Psychiatrist\", " +
//				"\"Permissions\":[ { \"Name\":\"Wellness\", \"Write\":true, " +
//				"\"Read\":true }, { \"Name\":\"Medications\", \"Write\":true, " +
//				"\"Read\":true }, { \"Name\":\"Allergies\", \"Write\":true, " +
//				"\"Read\":true } ] } ],\"Key\":\"KEYKEYKEYKEY\" }";
		
		String response = "{\"Roles\":[{" +
				"\"Name\":\"Primary care physician\"," +
				"\"Permissions\":[" +
				"{\"Name\":\"Wellness\",\"Write\":true,\"Read\":true}," +
				"{\"Name\":\"Medications\",\"Write\":true,\"Read\":true}," +
				"{\"Name\":\"Allergies\",\"Write\":true,\"Read\":true}]}," +
				"{\"Name\":\"Clinical Pharmacist\",\"Permissions\":[" +
				"{\"Name\":\"Wellness\",\"Write\":true,\"Read\":true}," +
				"{\"Name\":\"Medications\",\"Write\":true,\"Read\":true}," +
				"{\"Name\":\"Allergies\",\"Write\":true,\"Read\":true}]}," +
				"{\"Name\":\"Psychiatrist\",\"Permissions\":[" +
				"{\"Name\":\"Wellness\",\"Write\":true,\"Read\":true}," +
				"{\"Name\":\"Medications\",\"Write\":true,\"Read\":true}," +
				"{\"Name\":\"Allergies\",\"Write\":true,\"Read\":true}]}" +
				"],\"Key\":\"KEYKEYKE-YKEY-KEYK-EYKE-YKEYKEYKEYKE\"}";
		
		JSONObject json = ServerConnectionHelper.toJSONObject(response);
		policy = new Policy(json);
		
//		Policy policy = new Policy();
//		policy.setKey("KEYKEYKE-YKEY-KEYK-EYKE-YKEYKEYKEYKE");
//
//		int pid = policy.addProvider("Primary care physician");
//		policy.addPermission(pid, "Wellness", true, true);
//		policy.addPermission(pid, "Medications", true, true);
//		policy.addPermission(pid, "Allergies", true, true);
//
//		pid = policy.addProvider("Clinical Pharmacist");
//		policy.addPermission(pid, "Wellness", true, true);
//		policy.addPermission(pid, "Medications", true, true);
//		policy.addPermission(pid, "Allergies", true, true);
//
//		pid = policy.addProvider("Psychiatrist");
//		policy.addPermission(pid, "Wellness", true, true);
//		policy.addPermission(pid, "Medications", true, true);
//		policy.addPermission(pid, "Allergies", true, true);
//		
//		
//		try {
//			Log.v(TAG, policy.toJSONObject().toString());
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}

		return policy;
	}


	public static boolean sendODLJSON(ODL odl)//String publicId, String recordId)
	throws JSONException 
	{
		String action = "/ODL/Add/?";	
		String params = generateGetPubRecParams();
		Log.v(TAG, odl.toString());
		String response = ServerConnectionHelper.serverRequestPost((action + params), odl.toJSONObject());
		JSONObject json = ServerConnectionHelper.toJSONObject(response);

		ODL odlNew = new ODL(json);		
		odlList.add(odlNew);

		return true;

	}

	public static URL generateReportJSON(edu.uconn.model.ODLQuery query)//String publicId, String recordId)
	throws JSONException, MalformedURLException 
	{		
		String action = "/ODL/getGoogleChart/?";	
		String params = generateGetPubRecParams();
		Log.v(TAG, query.toString());
		String response = ServerConnectionHelper.serverRequestPost((action + params), query.toJSONObject());
		JSONObject jo = ServerConnectionHelper.toJSONObject(response);
		String url = jo.getString("url");
		Log.v(TAG, "URL Response: " + url);
		URL reportURL = new URL(url);

		return reportURL;

	}	

	public static ArrayList<Medication> getMedicationsJSON()//String publicId, String recordId)
	throws JSONException 
	{
		return getMedicationsJSON(false);
	}

	/**
	 * Method for getting JSON Medications from health vault.
	 * @return
	 * @throws JSONException
	 */
	public static ArrayList<Medication> getMedicationsJSON(boolean forceUpdate)//String publicId, String recordId)
	throws JSONException 
	{		

		//HashMap<String, String> medMap = new HashMap<String, String>();
		if(medicationList == null || forceUpdate)
		{
			String action = "/Medications/?";	
			String params = generateGetPubRecParams();

			JSONArray json = ServerConnectionHelper.toJSONArray(ServerConnectionHelper.serverRequestGet(action + params));

			ArrayList<Medication> medList = new ArrayList<Medication>();

			/**
			 * This is completely unnecessary but wanted to match the call parameters.
			 */
			for(int i = 0; i < json.length(); i++)
			{
				// New available object names.

				JSONObject jo = json.getJSONObject(i);
				Medication med = new Medication(jo);
				medList.add(med);
				Log.v(TAG, "JSONMED: medications" + med.toString());		
			}
			medicationList = medList;
		}

		return medicationList;			
	}

	public static Person getPersonInfo()
	throws JSONException
	{
		return getPersonInfo(false);
	}


	/**
	 * Method for getting JSON Medications from health vault.
	 * @return
	 * @throws JSONException
	 */
	public static Person getPersonInfo(boolean forceUpdate)//String publicId, String recordId)
	throws JSONException 
	{		

		if(person == null || forceUpdate)
		{
			String action = "/Person/getPersonalInfo/?";	
			String params = generateGetPubRecParams();

			JSONObject json = ServerConnectionHelper.toJSONObject(ServerConnectionHelper.serverRequestGet(action + params));

			person = new Person(json);
			Log.v(TAG, person.toString());
		}

		return person;			
	}

	/**
	 * Adds the specified medication.
	 * Inserts the key into the medication added.
	 * @param med
	 * @throws JSONException
	 */
	public static void addMedicationInfo(Medication med)//String publicId, String recordId)
	throws JSONException
	{
		String action = "/Medications/Add/?";	
		String params = generateGetPubRecParams();
		Log.v(TAG, med.toString());
		String response = ServerConnectionHelper.serverRequestPost(action + params, med.toJSONObject());
		med.setKey(ServerConnectionHelper.toJSONObject(response));

		medicationList.add(med);
	}

	/**
	 * Updates the medication information for a given med.
	 * No return type.
	 * @param med
	 * @throws JSONException
	 */
	public static void updateMedicationInfo(Medication med)//String publicId, String recordId)
	throws JSONException
	{
		String action = "/Medications/Update/?";	
		String params = generateGetPubRecParams();
		Log.v(TAG, med.toString());		
		ServerConnectionHelper.serverRequestPost(action + params, med.toJSONObject());		
	}

	/**
	 * Deletes a health item.
	 * @param item
	 * @return
	 * @throws JSONException
	 */
	public static boolean deleteHealthItem(HealthItem item)//String publicId, String recordId)
	throws JSONException
	{
		String action = "/Delete/?";	
		String params = generateGetPubRecParams();

		String response = ServerConnectionHelper.serverRequestPost(action + params, item.toJSONObject());

		boolean returnVar = ServerConnectionHelper.toBool(response);

		if(returnVar)
		{
			deleteFromArrayList(item.getKey());
		}

		return returnVar;
	}

	private static void deleteFromArrayList(String key)
	{
		deleteFromArrayList(odlList, key);
		deleteFromArrayList(medicationList, key);
	}

	private static void deleteFromArrayList(ArrayList<?> list, String key)
	{
		Iterator<HealthItem> i = (Iterator<HealthItem>) list.iterator();
		HealthItem hi = null;
		Log.v(TAG, "Removing Key: " + key);
		boolean flag = false;
		while(i.hasNext())
		{
			hi = i.next();
			if(hi.getKey().equals(key))
			{
				flag = true;
				break;
			}
		}

		if(hi != null && flag)
		{
			Log.v(TAG, "Removing from in memory: " + hi.toString());
			list.remove(hi);
		}
	}



	/**
	 * Generates the Parameters for a get request based on public and record id's.
	 * @return
	 */
	private static String generateGetPubRecParams()//String publicId, String recordId)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("publicId=");
		sb.append(publicId);
		sb.append("&");
		sb.append("recordId=");
		sb.append(recordId);

		return sb.toString();

	}
}


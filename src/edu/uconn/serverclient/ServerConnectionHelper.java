package edu.uconn.serverclient;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ServerConnectionHelper 
{
	private static final String TAG = ServerConnection.class.getName();
	private static final String VERB_TAG = "JSON_Server_VERBOSE";
	private final static String SERVER_URL = "http://137.99.11.66/Health"; 
	private static final String TRUE = "TRUE";

	/**
	 * Makes an http request given the string parameters.
	 * "?" is necessary at start of parameters
	 * @param params
	 * @return 
	 * @return string object.
	 */
	public static String serverRequestGet(String params)
	{
		HttpClient httpclient = new DefaultHttpClient();
		Log.v(VERB_TAG, SERVER_URL + params);	   	
		HttpGet httpget = new HttpGet(SERVER_URL + params);
		String response = null;

		try
		{
			ResponseHandler<String> rh = new BasicResponseHandler();
			response = httpclient.execute(httpget, rh);

			if(response != null)
			{    			
				Log.v(VERB_TAG, response);    			
			}
			else
			{
				Log.e(TAG, "null returned from request serverRequestGet");
			}
		}
		catch (ClientProtocolException e)
		{
			Log.e(TAG, e.toString());
		}
		catch (IOException e)
		{
			Log.e(TAG, e.toString());
		}    	  	

		return response;   	
	}  

	/**
	 * Used to send a JSON object to the server.
	 * "?" is necessary at start of params 
	 * @param jsonObj
	 * @return
	 */
	public static String serverRequestPost(String params, JSONObject jsonObj)
	{
		HttpClient httpclient = new DefaultHttpClient();

		HttpPost httpPost = new HttpPost(SERVER_URL + params);    	

		String response = null;

		try
		{
			StringEntity se = new StringEntity(jsonObj.toString());
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			httpPost.setEntity(se);

			ResponseHandler<String> rh = new BasicResponseHandler();
			response = httpclient.execute(httpPost, rh);

			if(response != null)
			{
				Log.v(VERB_TAG, response);   			
			}
			else
			{
				Log.e(TAG, "null returned from request serverRequestGet");
			}
		}
		catch (ClientProtocolException e)
		{
			Log.e(TAG, e.toString());
		}
		catch (IOException e)
		{
			Log.e(TAG, e.toString());
		}

		return response;   	
	}

	/**
	 * Convience method.
	 * @param request
	 * @return
	 */
	public static JSONArray toJSONArray(String response)
	{
		JSONArray json = null;

		try 
		{
			json = new JSONArray(response);
		} 
		catch (JSONException e) 
		{
			Log.e(TAG, e.toString());
		}

		return json;

	}

	/**
	 * Convience method.
	 * @param request
	 * @return
	 */
	public static JSONObject toJSONObject(String response)
	{
		JSONObject json = null;

		try 
		{
			json = new JSONObject(response);
		} 
		catch (JSONException e) 
		{
			Log.e(TAG, e.toString());
		}

		return json;   
	}

	public static boolean toBool(String response)
	{
		if(response != null && response.equalsIgnoreCase(TRUE))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}

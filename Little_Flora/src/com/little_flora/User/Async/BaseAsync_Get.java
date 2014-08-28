package com.little_flora.User.Async;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.little_flora.Activity.Utils.HTTP_POST_parameters.HTTP_MainCategory;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.Activity.Utils.Toasts_;

public abstract class BaseAsync_Get extends AsyncTask<String, String, InputStream>
{
	String[] data ;
	Context context ;
	private Logs_ mLog;
	public ProgressDialog pDialog;
	private Toasts_ mToast;
	private HttpPost httppost;
	String loadingText ;
	String url ;
	public BaseAsync_Get(String url , String[] data, Context context , String loadingText ) {
		this.url = url ;
		this.context = context;
		this.data = data;
		this.loadingText = loadingText ;
		
		mLog = new Logs_("BaseAsync");
		mToast = new Toasts_(context) ;
		
		for (int i = 0; i < data.length; i++) {
			mLog.showTempLog("data[" + i + "] :" + data[i]);
		}
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		initProgressDialog(loadingText);
	}
	
	public void initProgressDialog(String loadingText) {
		pDialog = new ProgressDialog(context);
		pDialog.setMessage(loadingText);
		pDialog.setCancelable(false);
		pDialog.show();
	}	
	
	@Override
	protected InputStream doInBackground(String... params) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		InputStream is = null;

		HttpClient httpclient = new DefaultHttpClient();
			
		httppost = new HttpPost(url);
		mLog.showTempLog(url);

		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);

			nameValuePairs.add(new BasicNameValuePair(
					HTTP_MainCategory.formsubmitted, "TRUE"));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse response = httpclient.execute(httppost);

			HttpEntity entity1 = response.getEntity();

			BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity1);

			is = bufHttpEntity.getContent();

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			mLog.showTempLog("ClientProtocolException");
		} catch (IOException e) {
			e.printStackTrace();
			mLog.showTempLog("IOException");
		}

		return is;
	}
	
	public String getString(JSONObject json, String TAG) {
		String returnParseData = "blankValuePresent";

		try {
			Object aObj = json.get(TAG);
			if(aObj instanceof Boolean){
				returnParseData = Boolean.toString(json.getBoolean(TAG));
			}
			else
			{
				returnParseData = json.getString(TAG);
			}
		} catch (Exception err) {
			err.printStackTrace();
			returnParseData = "blankValuePresent";
		}

		return returnParseData;
	}
	
	public String getIntintoString(JSONObject json, String TAG) {
		int returnParseData = -1;

		try {
			returnParseData = json.getInt(TAG) ;
		} catch (Exception err) {
			err.printStackTrace();
			returnParseData = -1;
		}

		return Integer.toString(returnParseData) ;
	}
	
	public JSONArray getJSONArrayData (JSONObject json, String TAG)
	{
		JSONArray jsonArray ;
		
		try {
			jsonArray = json.getJSONArray(TAG) ;
		} catch (Exception err) {
			err.printStackTrace();
			jsonArray = new JSONArray() ;
		}
		
		return jsonArray ;  
	}

}

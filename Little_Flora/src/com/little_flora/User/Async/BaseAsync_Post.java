package com.little_flora.User.Async;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.Activity.Utils.Toasts_;

public class BaseAsync_Post extends AsyncTask<String, String, InputStream>
{

	Context context ;
	private Logs_ mLog;
	public ProgressDialog pDialog;
	private Toasts_ mToast;
	private HttpPost httppost;
	String loadingText ;
	String url ;
	Dictionary<String, String> dictionary ;
	String fromWhere ;
	String [][] arrayMultidimensional ;
	JSONObject jsonObject ;
	
	public BaseAsync_Post(JSONObject jsonObject , String [][] arrayMultidimensional , String url , Dictionary<String, String> dictionary, Context context , String loadingText , String fromWhere) {
		this.url = url ;
		this.jsonObject = jsonObject ;
		this.context = context;
		this.dictionary = dictionary;
		this.loadingText = loadingText ;
		this.fromWhere = fromWhere ;
		this.arrayMultidimensional = arrayMultidimensional ;
		
		mLog = new Logs_("BaseAsync");
		mToast = new Toasts_(context) ;
		
		Enumeration<String> enumeration = dictionary.keys() ;
		
		while ( enumeration.hasMoreElements() )
		{
			mLog.showTempLog( enumeration.nextElement() ) ;
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
			
			if ( fromWhere.equals("Login") || fromWhere.equals("Registration") || fromWhere.equals("ForgotPassword") )
			{
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(50);

				Enumeration<String> enumeration = dictionary.keys() ;

				while ( enumeration.hasMoreElements() )
				{
					String key = enumeration.nextElement() ;
					nameValuePairs.add(new BasicNameValuePair(key,dictionary.get(key)));
				}
			
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			}
			else if ( fromWhere.equals("Order") )
			{
				mLog.showTempLog("inSide : fromWhere.equals(Order) ") ;
				
				StringEntity se = new StringEntity( jsonObject.toString()); 
				
			    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			    httppost.setHeader("Accept", "application/json");
			    httppost.setHeader("Content-type", "application/json");
			    httppost.setEntity(se);
			    
			}
			else if (  fromWhere.equals("AddCart_SecondTime") )
			{
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(50);

				Enumeration<String> enumeration = dictionary.keys() ;

				while ( enumeration.hasMoreElements() )
				{
					String key = enumeration.nextElement() ;
					nameValuePairs.add(new BasicNameValuePair(key,dictionary.get(key)));
				}		
				
				for (int i = 0; i < arrayMultidimensional.length; i++) 
				{
					for ( int j = 0 ; j < arrayMultidimensional[i].length ; j++ )
					{
						nameValuePairs.add(new BasicNameValuePair("productarr[][]",arrayMultidimensional[i][j]));
					}
				}
				
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			}
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
			returnParseData = json.getString(TAG);
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

}

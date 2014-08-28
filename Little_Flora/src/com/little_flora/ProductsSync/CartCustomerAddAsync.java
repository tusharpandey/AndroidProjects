package com.little_flora.ProductsSync;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Dictionary;
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
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.little_flora.Activity.ProductDescription;
import com.little_flora.Activity.Utils.ConvertStreamIntoString;
import com.little_flora.Activity.Utils.HTTP_POST_urls;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.Activity.Utils.HTTP_POST_parameters.HTTP_MainCategory;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_AddToCart;
import com.little_flora.Activity.Utils.LF_Constants.Con_SpalshScreen;
import com.little_flora.User.Async.BaseAsync_Get;

public class CartCustomerAddAsync extends AsyncTask<String, String, InputStream>
{
	String loadingText;
	ProductDescription context  ;
	private ProgressDialog pDialog;
	private Logs_ mLog;
	private HttpPost httppost;
	private InputStream is;
	String urlPost ;
	String urlPre ;
	Dictionary<String, String> dictionary ;
	String userStatus ;
	
	public CartCustomerAddAsync (String userStatus , Dictionary<String, String> dictionary , String urlPost , String urlPre , ProductDescription context , String loadingText)
	{
		this.userStatus = userStatus ;
		this.dictionary = dictionary ;
		this.urlPost = urlPost ;
		this.urlPre = urlPre ;
		this.loadingText = loadingText ;
		this.context = context ;
		mLog = new Logs_("CartCustomerAddAsync") ;
		
		mLog.showTempLog(dictionary.get("firstname"));
		mLog.showTempLog(dictionary.get("lastname"));
		mLog.showTempLog(dictionary.get("website_id"));
		mLog.showTempLog(dictionary.get("mode"));
		mLog.showTempLog(dictionary.get("email"));
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
	protected InputStream doInBackground(String... params) 
	{
		HttpClient httpclient = new DefaultHttpClient();
		
		httppost = new HttpPost(urlPre);
		mLog.showTempLog(urlPre);

		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);

			nameValuePairs.add(new BasicNameValuePair(
					HTTP_MainCategory.formsubmitted, "TRUE"));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse response = httpclient.execute(httppost);

			HttpEntity entity1 = response.getEntity();

			BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity1);

			is = bufHttpEntity.getContent();
			
			String s = null ;
			
			try {
				s = ConvertStreamIntoString.convertStreamToString(is);
				System.out.println(s);
			} catch (Exception err) {
				err.printStackTrace();
				mLog.showTempLog("1-Error in fetching data");
			}
			
			JSONObject jsonObject = null ;
			try {
				jsonObject = new JSONObject(s);
				
				if (jsonObject == null) {
					mLog.showTempLog("1-invalid json");
				}
			} catch (Exception err) {
				err.printStackTrace();
				mLog.showTempLog("1-Error in Converting stream into Json Object");
			}
			try
			{
				String buynow_quoteId = getString(jsonObject,"quoteid");
				MySharedPref.getInstance(context).setString(HTTP_RESPONSE_AddToCart.AddToCart_+"buynow_quoteId", buynow_quoteId);
			}
			catch ( Exception err)
			{
				err.printStackTrace();
				mLog.showTempLog("1-Error in Parsing");
			}
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			mLog.showTempLog("ClientProtocolException");
		} catch (IOException e) {
			e.printStackTrace();
			mLog.showTempLog("IOException");
		}
		
		httpclient = new DefaultHttpClient();

		String quoteId = MySharedPref.getInstance(context).getString(HTTP_RESPONSE_AddToCart.AddToCart_+"buynow_quoteId");

//		if ( userStatus.equals("customer"))
//		{
//			return null ;
//		}
//		else if ( userStatus.equals("guest"))
//		{
		
//		dictionarySend.put("sku", sku);
//		dictionarySend.put("qty", qty);
//		dictionarySend.put("customer_id", customer_id);
		
			urlPost = HTTP_POST_urls.byNow_cart_create_customer+
					"firstname="+dictionary.get("firstname")+
					"&lastname="+dictionary.get("lastname")+
					"&website_id="+dictionary.get("website_id")+
					"&mode="+dictionary.get("mode")+
					"&quoteid="+quoteId+
					"&sku="+dictionary.get("sku")+
					"&qty="+dictionary.get("qty")+
					"&customer_id="+dictionary.get("customer_id")+
					"&email="+dictionary.get("email") ;
			
			mLog.showTempLog(urlPost);

			httppost = new HttpPost(urlPost);

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
//		}
//		else
//		{
//			return null ;
//		}
	}
	
	@Override
	protected void onPostExecute(InputStream result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		// TODO Auto-generated method stub

		JSONObject jsonObject = null ;
		String s = null ;
		String message = null ;
		String status = null ;
		String quoteId = null ;
		String resultCustomerAddresses = null ;
		pDialog.dismiss();
		
//		if ( userStatus.equals("customer"))
//		{
//			
//		}
//		else if ( userStatus.equals("guest"))
//		{
			try {
				s = ConvertStreamIntoString.convertStreamToString(result);
				System.out.println(s);
			} catch (Exception err) {
				err.printStackTrace();
				mLog.showTempLog("Error in fetching data");
				message = "Error in Response";
			}
			
			try {
				jsonObject = new JSONObject(s);
				
				if (jsonObject == null) {
					message = "invalidJson";
				}
			} 
			catch (Exception err) {
				err.printStackTrace();
				mLog.showTempLog("Error in Converting stream into Json Object");
				message = "Error in Response";
			}
			
			//System.out(13473): {"status":"1","message":"Data set successfully","quoteid":"1238",
			//"arrProducts":[{"sku":"LF-1077","quantity":"47"}],"resultCustomerAddresses":true}
			//{"status":"1","message":"Product added successfully","resultCustomerSet":true}

			try
			{
				status = getString(jsonObject, "status");
				message = getString(jsonObject, "message");
				resultCustomerAddresses = getString(jsonObject, "resultCustomerAddresses");
				
			}
			catch (Exception err) {
				err.printStackTrace();
				mLog.showTempLog("Error in Parsing");
				message = "Error in Response";
			}	
//		}
		
		context.responseCartCreateCustomer(message, status, resultCustomerAddresses) ;
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
}

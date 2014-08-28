package com.little_flora.Activity.Async;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
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
import android.os.AsyncTask;
import android.util.Log;

import com.little_flora.R;
import com.little_flora.Activity.SplashScreen;
import com.little_flora.Activity.Utils.ConvertStreamIntoString;
import com.little_flora.Activity.Utils.HTTP_POST_parameters.HTTP_MainCategory;
import com.little_flora.Activity.Utils.HTTP_POST_urls;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_MainCategory;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.Activity.Utils.Toasts_;

public class SplachScreenSync extends AsyncTask<String, String, InputStream> {
	String[] data;
	private InputStream is;
	private JSONArray jsonArray;
	SplashScreen context;
	private ProgressDialog pDialog;
	private String message;
	private String status;
	private String s;
	Logs_ mLog;

	ArrayList<Dictionary<String, String>> listDictionary = new ArrayList<Dictionary<String, String>>();
	private Toasts_ mToast;
	String whichLanguage ;
	private HttpPost httppost;
	
	public SplachScreenSync(String[] data, SplashScreen context , String whichLanguage) {
		this.context = context;
		this.data = data;
		this.whichLanguage = whichLanguage ;
		
		mLog = new Logs_("MainCategory_Async");
		for (int i = 0; i < data.length; i++) {
			showLog("data[" + i + "] :" + data[i]);
			
			mToast = new Toasts_(context) ;
		}
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		initProgressDialog(context.getResources().getString(
				R.string.loading_splashScreen));
	}

	@Override
	protected InputStream doInBackground(String... params) {
		// TODO Auto-generated method stub
		HttpClient httpclient = new DefaultHttpClient();
		if ( whichLanguage.equals("Arabic") )
		{
			httppost = new HttpPost(HTTP_POST_urls.mainCategoryURL_AR);
			showLog(HTTP_POST_urls.mainCategoryURL_AR);
		}
		else if ( whichLanguage.equals("English") )
		{
			httppost = new HttpPost(HTTP_POST_urls.mainCategoryURL_EN);
			showLog(HTTP_POST_urls.mainCategoryURL_EN);
		}

		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);

			// nameValuePairs.add(new
			// BasicNameValuePair(HTTP_MainCategory.value, data[0]));

			nameValuePairs.add(new BasicNameValuePair(
					HTTP_MainCategory.formsubmitted, "TRUE"));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse response = httpclient.execute(httppost);

			HttpEntity entity1 = response.getEntity();

			BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity1);

			is = bufHttpEntity.getContent();

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			showLog("ClientProtocolException");
		} catch (IOException e) {
			e.printStackTrace();
			showLog("IOException");
		}

		return is;
	}

	public void showLog(String msg) {
		Log.e("FavoriteSync", msg);
	}

	public void initProgressDialog(String loadingText) {
		pDialog = new ProgressDialog(context);
		pDialog.setMessage(loadingText);
		pDialog.setCancelable(false);
		pDialog.show();
	}

	@Override
	protected void onPostExecute(InputStream result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		pDialog.dismiss();
		try {
			s = ConvertStreamIntoString.convertStreamToString(result);
			System.out.println(s);
		} catch (Exception err) {
			err.printStackTrace();
			showLog("Error in Creating Json Object");
			message = "Error in fetching data";
		}
		try {
			jsonArray = new JSONArray(s);
			
			if (jsonArray == null) {
				message = "invalidJson";
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			showLog("Error in Creating Json Object");
			message = "Error in fetching data";
		}

		try 
		{
			
			Dictionary<String, Dictionary<String, Dictionary<String, String>>> wholeDictData = new Hashtable<String, Dictionary<String, Dictionary<String, String>>>();
			Dictionary<String, Dictionary<String, String>> mainCategoryDataList = new Hashtable<String, Dictionary<String, String>>();
			
			for ( int i = 0 ; i < jsonArray.length() ; i++ )
			{
				JSONObject ithJsonObject = jsonArray.getJSONObject(i) ;

				Dictionary<String, String> dictList_main = new Hashtable<String, String>();
				Dictionary<String, Dictionary<String, String>> subCategoryDataList = new Hashtable<String, Dictionary<String, String>>();
				
				dictList_main.put(HTTP_RESPONSE_MainCategory.id, getString(ithJsonObject, HTTP_RESPONSE_MainCategory.id));
				dictList_main.put(HTTP_RESPONSE_MainCategory.title, getString(ithJsonObject, HTTP_RESPONSE_MainCategory.title));
				dictList_main.put(HTTP_RESPONSE_MainCategory.InMenu, getString(ithJsonObject, HTTP_RESPONSE_MainCategory.InMenu));
				dictList_main.put(HTTP_RESPONSE_MainCategory.Active, getString(ithJsonObject, HTTP_RESPONSE_MainCategory.Active));
				
				/*
				 * Here we are storing our data in MainCategory with : i 
				 * 
				 * 		index from where we can fetch id of mainMenu .
				 */
				mainCategoryDataList.put(Integer.toString(i), dictList_main) ;
				
				JSONArray childern = ithJsonObject.getJSONArray(HTTP_RESPONSE_MainCategory.children) ;
				
				for ( int j = 0 ; j < childern.length() ; j++ )
				{
					Dictionary<String, String> dictList_sub = new Hashtable<String, String>();

					JSONObject jthJsonObject = childern.getJSONObject(j) ;
					
					dictList_sub.put(HTTP_RESPONSE_MainCategory.subcatid, getString(jthJsonObject, HTTP_RESPONSE_MainCategory.subcatid));
					dictList_sub.put(HTTP_RESPONSE_MainCategory.subcatitle, getString(jthJsonObject, HTTP_RESPONSE_MainCategory.subcatitle));
					dictList_sub.put(HTTP_RESPONSE_MainCategory.productcount, getString(jthJsonObject, HTTP_RESPONSE_MainCategory.productcount));
					
					subCategoryDataList.put(Integer.toString(j), dictList_sub) ;
				}
				
				/*
				 * Here we are storing our data in another dictionary with ID , because from the MainCategory we can fetch id , 
				 * 		and by the id we can fetch any element .
				 */
				wholeDictData.put(dictList_main.get(HTTP_RESPONSE_MainCategory.id), subCategoryDataList) ;
			}
			
			context.responseOfMainCategoryCallBack(mainCategoryDataList, wholeDictData) ;
			
		} catch (Exception err) {
			err.printStackTrace();
			showLog("Error in Sending Response to MainCategory");
			message = "Error in fetching data";
		}
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

package com.little_flora.Activity.Async;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.little_flora.Activity.SearchResult;
import com.little_flora.Activity.Utils.ConvertStreamIntoString;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_Search;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.User.Async.BaseAsync_Get;

public class SearchResultAsync extends BaseAsync_Get {

	private Logs_ mLogs;
	private String s;
	private String message;
	SearchResult context ;
	private String status;

	public SearchResultAsync(String url, String[] data, SearchResult context,
			String loadingText) {
		super(url, data, context, loadingText);
		this.context = context ;
		mLogs = new Logs_("SearchResultAsync") ;
	}

	@Override
	protected void onPostExecute(InputStream result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		JSONObject jsonObject = null;
		super.onPostExecute(result);
		pDialog.dismiss();
		try {
			s = ConvertStreamIntoString.convertStreamToString(result);
			System.out.println(s);
		} catch (Exception err) {
			err.printStackTrace();
			mLogs.showTempLog("Error in fetching data");
			message = "Error in fetching data";
		}

		try {
			jsonObject = new JSONObject(s);

			if (jsonObject == null) {
				message = "invalidJson";
			}
		} catch (Exception err) {
			err.printStackTrace();
			mLogs.showTempLog("Error in Converting stream into Json Object");
			message = "Error in Converting stream into Json Object";
		}
		try {
			status = getString(jsonObject, HTTP_RESPONSE_Search.status) ;
			message = getString(jsonObject, HTTP_RESPONSE_Search.message) ;
			
			ArrayList<Dictionary<String, String>> searchDataList = new ArrayList<Dictionary<String,String>>() ;
			Dictionary<String, Dictionary<String, String>> searchDataDictionary = new Hashtable<String, Dictionary<String,String>>() ;
			
			int j = 0 ;
			if ( status.equals("1"))
			{
				JSONArray searchresultArray = new JSONArray() ;
				
				if (!jsonObject.isNull(HTTP_RESPONSE_Search.searchresult))
					searchresultArray = jsonObject.getJSONArray(HTTP_RESPONSE_Search.searchresult);
			
				for ( int i = 0 ; i < searchresultArray.length() ; i++ )
				{
					JSONObject ith_jsonObject = searchresultArray.getJSONObject(i) ;
					Dictionary<String, String> dictionary = new Hashtable<String, String>() ;
				
					Iterator<String> keysOfCustomerData = ith_jsonObject.keys() ; 
				
					String status = null ;
				
					while ( keysOfCustomerData.hasNext() )
					{
						String keyOfJsonObject = keysOfCustomerData.next();
						status = getString(ith_jsonObject, "status");
						dictionary.put(keyOfJsonObject, getString(ith_jsonObject, keyOfJsonObject)) ;
					}
				
					//if ( status != null && status.equals("1") )
					//{
						searchDataDictionary.put(Integer.toString(j),dictionary);
						searchDataList.add(dictionary) ;
					
						j++ ;
					//}
				}
			}
			context.searchResponse(searchDataDictionary,message,status);
			
		} catch (Exception err) {
			err.printStackTrace();
			mLogs.showTempLog("Error in Parsing");
			message = "Error in Parsing";
		}
		
	}

}

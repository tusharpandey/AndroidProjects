package com.little_flora.User.Async;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.little_flora.Activity.Utils.ConvertStreamIntoString;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_CustomerAddressList;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.User.CustomerAddressList;
import com.little_flora.User.OrderDetails;

public class CustomerAddressListAsync extends BaseAsync_Get
{
	OrderDetails context ;
	private Logs_ mLogs;
	public CustomerAddressListAsync(String url, String[] data, OrderDetails context,
			String loadingText) {
		super(url, data, context, loadingText);
		this.context = context ;
		
		mLogs = new Logs_("CustomerAddressAsync") ;
		
	}
	
	@Override
	protected void onPostExecute(InputStream result) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = null;
		JSONArray jsonListAdrressArray = null ;
		String s = null ;
		String message = null ;
		String status = null ;
		ArrayList<Dictionary<String, String>> listAddressDictionary = new ArrayList<Dictionary<String,String>>() ;
		super.onPostExecute(result);
		pDialog.dismiss();
		try {
			s = ConvertStreamIntoString.convertStreamToString(result);
			System.out.println(s);
		} catch (Exception err) {
			err.printStackTrace();
			mLogs.showTempLog("Error in fetching data");
			message = "Error in Response";
		}

		try {
			jsonObject = new JSONObject(s);

			if (jsonObject == null) {
				message = "invalidJson";
			}
		} catch (Exception err) {
			err.printStackTrace();
			mLogs.showTempLog("Error in Converting stream into Json Object");
			message = "Error in Response";
		}
		
		try
		{
			status = getString(jsonObject, HTTP_RESPONSE_CustomerAddressList.status);
			message = getString(jsonObject, HTTP_RESPONSE_CustomerAddressList.message);
			jsonListAdrressArray = getJSONArrayData(jsonObject,HTTP_RESPONSE_CustomerAddressList.liststatus);
			
			for ( int i = 0 ; i < jsonListAdrressArray.length() ; i++ )
			{
				Dictionary<String, String> dictionary = new Hashtable<String, String>() ;
				
				JSONObject jsonSingleAddressFromList = jsonListAdrressArray.getJSONObject(i) ;
				
				Iterator<String> keysOfCustomerData = jsonSingleAddressFromList.keys() ; 
				
				while ( keysOfCustomerData.hasNext() )
				{
					String keyOfJsonObject = keysOfCustomerData.next();
					dictionary.put(keyOfJsonObject, getString(jsonSingleAddressFromList, keyOfJsonObject));
				}
				
				listAddressDictionary.add(dictionary);
			}
		}
		catch (Exception err)
		{
			err.printStackTrace();
			mLogs.showTempLog("Error in Parsing Data");
			message = "Error in Response";
		}
		
		context.customerAddressListCallbackResponse(message, status, listAddressDictionary) ;
	}

}

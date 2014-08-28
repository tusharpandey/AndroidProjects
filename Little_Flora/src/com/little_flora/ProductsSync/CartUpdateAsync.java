package com.little_flora.ProductsSync;

import java.io.InputStream;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

import com.little_flora.Activity.Cart;
import com.little_flora.Activity.Utils.ConvertStreamIntoString;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_Cart;
import com.little_flora.User.Async.BaseAsync_Get;

public class CartUpdateAsync extends BaseAsync_Get
{
	private Logs_ mLogs;
	Cart context ;
	
	public CartUpdateAsync(String url, String[] data, Cart context,
			String loadingText) {
		super(url, data, context, loadingText);
		
		this.context = context ;
		
		mLogs = new Logs_("CartUpdateAsync");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onPostExecute(InputStream result) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = null ;
		JSONObject customerdata = null ;
		String message = null ;
		String s = null ;
		String status = null ;
		
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
		Dictionary<String, Dictionary<String, String>> dictionaryData = new Hashtable<String, Dictionary<String,String>>() ;

		try 
		{
			status = getString(jsonObject, HTTP_RESPONSE_Cart.status);
			message = getString(jsonObject, HTTP_RESPONSE_Cart.message);
			
			JSONArray jsonArrayData = jsonObject.getJSONArray("productdata") ;
			
			for ( int i = 0 ; i < jsonArrayData.length() ; i++ )
			{
				Dictionary<String, String> dictionary = new Hashtable<String, String>() ;
				
				JSONObject ithJsonObject = jsonArrayData.getJSONObject(i);
				
				Iterator<String> iterator = ithJsonObject.keys() ;
				
				while ( iterator.hasNext() )
				{
					String key = iterator.next() ;
					
					dictionary.put(key, ithJsonObject.getString(key) ) ;
				}
				
				dictionaryData.put(Integer.toString(i) , dictionary) ;
			}
			
		}
		catch (Exception err) {
			err.printStackTrace();
			mLogs.showTempLog("Error in Parsing");
			message = "Error in Response";
		}
		if ( !message.equals("Error in Parsing") )
		{
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}
		
		context.responseCartCallBack(status, message, dictionaryData);
		
	}
}

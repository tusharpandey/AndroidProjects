package com.little_flora.User.Async;

import java.io.InputStream;
import java.util.Iterator;

import org.json.JSONObject;

import com.little_flora.Activity.Utils.ConvertStreamIntoString;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.User.Profile;

import android.content.Context;

public class ProfileSync extends BaseAsync_Get {

	private Logs_ mLogs;
	Profile context ;
	private String message;
	public ProfileSync(String url, String[] data, Profile context,
			String loadingText) {
		super(url, data, context, loadingText);
		// TODO Auto-generated constructor stub
		// TODO Auto-generated constructor stub
		
		mLogs = new Logs_("LoginAsync") ;
		this.context = context ;

	}
	@Override
	protected void onPostExecute(InputStream result) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = null ;
		super.onPostExecute(result);
		pDialog.dismiss();
		String s = null;
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
			message = "Success" ;
			if (jsonObject == null) {
				message = "invalidJson";
			}
		} catch (Exception err) {
			err.printStackTrace();
			mLogs.showTempLog("Error in Converting stream into Json Object");
			message = "Error in Converting stream into Json Object";
		}
		
		try
		{
			Iterator<String> keysOfCustomerData = jsonObject.keys() ; 
			
			while ( keysOfCustomerData.hasNext() )
			{
				String keyOfJsonObject = keysOfCustomerData.next();
				//mLogs.showTempLog(keyOfJsonObject);
				MySharedPref.getInstance(context).setString("UserProfile_"+keyOfJsonObject,getString(jsonObject,keyOfJsonObject ));
			}
			message = "Success" ;
		}
		catch ( Exception err )
		{
			err.printStackTrace();
			mLogs.showTempLog("Error in Parsing Data");
			message = "Error in Parsing Data";
		}
		
		context.profileCallback(message);
		
		
	}

}

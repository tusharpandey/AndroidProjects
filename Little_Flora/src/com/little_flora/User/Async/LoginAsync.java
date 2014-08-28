package com.little_flora.User.Async;

import java.io.InputStream;
import java.util.Iterator;

import org.json.JSONObject;

import com.little_flora.Activity.Utils.ConvertStreamIntoString;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_User;
import com.little_flora.Activity.Utils.LF_Constants.Con_UserDetails;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.User.Login;

public class LoginAsync extends BaseAsync_Get
{

	private String s;
	private Logs_ mLogs;
	private String message;
	Login context ;
	private String status="";
	
	
	public LoginAsync(String url , String[] data, Login context, 
			String loadingText) {
		super(url,data, context, loadingText);
		// TODO Auto-generated constructor stub
		
		mLogs = new Logs_("LoginAsync") ;
		this.context = context ;
	}
	
	@Override
	protected void onPostExecute(InputStream result) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = null ;
		JSONObject customerdata = null ;
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
		try {
			
			message = getString(jsonObject,HTTP_RESPONSE_User.message) ;
			status = getString(jsonObject,HTTP_RESPONSE_User.status) ;
			customerdata = jsonObject.getJSONObject("customerdata") ;
			
			if ( getString(jsonObject,HTTP_RESPONSE_User.message).equals("Success") && 
					getIntintoString(jsonObject, HTTP_RESPONSE_User.status).equals("1") )
			{
				
				MySharedPref.getInstance(context).setString(Con_UserDetails.userId,getString(customerdata, "customer_id")) ; 

				mLogs.showTempLog(MySharedPref.getInstance(context).getString(Con_UserDetails.userId));

				Iterator<String> keysOfCustomerData = customerdata.keys() ; 
				
				while ( keysOfCustomerData.hasNext() )
				{
					String keyOfJsonObject = keysOfCustomerData.next();
					MySharedPref.getInstance(context).setString(HTTP_RESPONSE_User.UserLogined_+keyOfJsonObject,getString(customerdata,keyOfJsonObject ));
				}
			}
			
		} catch (Exception err) {
			err.printStackTrace();
			mLogs.showTempLog("Error in Parsing Json");
			message = "Error in Response";
		}

		context.loginResponse( status, message ) ;
		
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

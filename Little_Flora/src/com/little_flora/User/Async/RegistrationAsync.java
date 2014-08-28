package com.little_flora.User.Async;

import java.io.InputStream;
import java.util.Iterator;

import org.json.JSONObject;

import android.content.Context;

import com.little_flora.Activity.Utils.ConvertStreamIntoString;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_Registration;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_User;
import com.little_flora.User.Registration;

public class RegistrationAsync extends BaseAsync_Get {


	private String s;
	private Logs_ mLogs;
	private String message;
	private JSONObject jsonObject;
	Registration context ;
	public RegistrationAsync(String url , String[] data, Registration context, 
			String loadingText) {
		super(url,data, context, loadingText);
		
		mLogs = new Logs_("RegistrationAsync") ;
		this.context = context ;
	}
	
	String customer_id ;
	String messageResponse ;
	String status ;
	
	
	@Override
	protected void onPostExecute(InputStream result) {
		super.onPostExecute(result);
		pDialog.dismiss();
		try {
			s = ConvertStreamIntoString.convertStreamToString(result);
			System.out.println(s);
		} catch (Exception err) {
			err.printStackTrace();
			mLogs.showTempLog("Error in Creating Json Object");
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
			customer_id = getString(jsonObject,HTTP_RESPONSE_Registration.customer_id);
			messageResponse = getString(jsonObject,HTTP_RESPONSE_Registration.message);
			status = getIntintoString(jsonObject,HTTP_RESPONSE_Registration.status);
			
			mLogs.showTempLog(customer_id);
			mLogs.showTempLog(messageResponse);
			mLogs.showTempLog(status);
			
			message = messageResponse ;

		} catch (Exception err) {
			err.printStackTrace();
			mLogs.showTempLog("Error in Parsing Json");
			message = "Error in Parsing";
		}		
		context.registrationResponse(customer_id, messageResponse, status) ;
	}
}

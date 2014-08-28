package com.little_flora.User.Async;

import java.io.InputStream;

import org.json.JSONObject;

import com.little_flora.Activity.Utils.ConvertStreamIntoString;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.User.CustomerAddress;

public class CustomerAddressAsync extends BaseAsync_Get
{
	CustomerAddress context;
	private Logs_ mLogs;
	
	public CustomerAddressAsync(String url, String[] data, CustomerAddress context,
			String loadingText) {
		super(url, data, context, loadingText);
		
		this.context = context ;
		
		mLogs = new Logs_("CustomerAddressAsync");
	}
	
	@Override
	protected void onPostExecute(InputStream result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		JSONObject jsonObject = null ;
		JSONObject customerdata = null ;
		String s = null ;
		String message = null ;
		String addressId = null ;
		String status = null ;
		
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
		
		//{"addressid":"125","message":"Success","status":1}
		try 
		{
			message = getString(jsonObject, "message") ;
			addressId = getString(jsonObject, "addressid") ;
			status = getString(jsonObject, "status") ;			
		} 
		catch (Exception err) {
			err.printStackTrace();
			mLogs.showTempLog("Error in Parsing");
			message = "Error in Response";
		}
		
		context.customerAddressCallbackResponse(   message ,  status ,  addressId );

	}

}

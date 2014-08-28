package com.little_flora.User.Async;

import java.io.InputStream;

import org.json.JSONObject;

import com.little_flora.Activity.Utils.ConvertStreamIntoString;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.User.Profile;

public class UpdateProfileSync extends BaseAsync_Get {
	private Logs_ mLogs;
	Profile context;
	private String message;

	public UpdateProfileSync(String url, String[] data, Profile context,
			String loadingText) {
		super(url, data, context, loadingText);
		this.context = context;
		mLogs = new Logs_("LoginAsync");
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
	}

}

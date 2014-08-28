package com.little_flora.ProductsSync;

import java.io.InputStream;
import java.util.Dictionary;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.little_flora.Activity.Utils.ConvertStreamIntoString;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.User.Async.BaseAsync_Post;

public class OrderSync extends BaseAsync_Post
{

	private Logs_ mLogs;


	public OrderSync(JSONObject jsonObject, String[][] arrayMultidimensional,
			String url, Dictionary<String, String> dictionary, Context context,
			String loadingText, String fromWhere) {
		super(jsonObject, arrayMultidimensional, url, dictionary, context, loadingText,
				fromWhere);
		
		mLogs = new Logs_("OrderSync");
	}

	
	@Override
	protected void onPostExecute(InputStream result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		JSONObject jsonObject = null ;
		JSONObject customerdata = null ;
		String s = null ;
		String message = null ;
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
	}

}

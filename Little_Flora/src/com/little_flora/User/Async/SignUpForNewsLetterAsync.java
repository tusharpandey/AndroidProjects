package com.little_flora.User.Async;

import java.io.InputStream;

import org.json.JSONObject;

import com.little_flora.Activity.BaseActivity;
import com.little_flora.Activity.Utils.ConvertStreamIntoString;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.Dialogs.DialogNewsletter;

import android.content.Context;

public class SignUpForNewsLetterAsync extends BaseAsync_Get
{
	private Logs_ mLogs;
	BaseActivity context ;
	DialogNewsletter dialogForNewsLetter ; 
	
	public SignUpForNewsLetterAsync(String url, String[] data, BaseActivity context,
			String loadingText , DialogNewsletter dialogForNewsLetter ) {
		super(url, data, context, loadingText);
		// TODO Auto-generated constructor stub
		mLogs = new Logs_("SignUpForNewsLetterAsync") ;
		
		this.context= context ;
		this.dialogForNewsLetter = dialogForNewsLetter ;
	}

	@Override
	protected void onPostExecute(InputStream result) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = null ;
		String s = null ;
		String message = null ;
		String status = null ;
		String quoteId = null ;
		
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
		
		if ( message.contains("Error in Response") )
		{
			context.newsLetterSubscribed(false,message);
			dialogForNewsLetter.dismiss() ;
		}
		else
		{
			context.newsLetterSubscribed(true,message);
			dialogForNewsLetter.dismiss() ;
		}
	}
}

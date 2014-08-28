package com.little_flora.ProductsSync;

import java.io.InputStream;

import org.json.JSONObject;

import com.little_flora.Activity.ProductDescription;
import com.little_flora.Activity.ProductList_Grid;
import com.little_flora.Activity.SearchResult;
import com.little_flora.Activity.Utils.ConvertStreamIntoString;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_AddToCart;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_User;
import com.little_flora.User.Async.BaseAsync_Get;

import android.content.Context;
import android.widget.Toast;

public class AddToCartAsync extends BaseAsync_Get
{
	private Logs_ mLogs;
	String url ;
	String[] data ;
	String loadingText ;
	String fromWhere ;
	
	ProductList_Grid context_dialog_pl ;
	SearchResult context_dialog_SR ;
	ProductDescription context_pd ;
	
	
	public AddToCartAsync(String url, String[] data, Context context,
			String loadingText , String fromWhere ) {
		super(url, data, context, loadingText);
		mLogs = new Logs_("AddToCart");
		this.fromWhere = fromWhere ;
		
		if ( fromWhere.equals("DialogAsFlipKart_productList") )
		{
			context_dialog_pl = (ProductList_Grid) context ;
		}
		else if ( fromWhere.equals("ProductDescription") )
		{
			context_pd = (ProductDescription) context ;
		}
		else if ( fromWhere.equals("DialogAsFlipKart_searchResult") )
		{
			context_dialog_SR = (SearchResult) context ;
		}
		//ProductDescription
		// TODO Auto-generated constructor stub
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
		
		try {
			
			message = getString(jsonObject,HTTP_RESPONSE_AddToCart.message) ;
			status = getString(jsonObject,HTTP_RESPONSE_AddToCart.status) ;
			quoteId = getIntintoString(jsonObject,HTTP_RESPONSE_AddToCart.quoteid) ;
			
			if ( fromWhere.equals("DialogAsFlipKart_productList"))
			{
				if ( quoteId.equals("-1"))
				{
					
				}
				else
				{
					MySharedPref.getInstance(context_dialog_pl).setString(HTTP_RESPONSE_AddToCart.AddToCart_+HTTP_RESPONSE_AddToCart.quoteid, quoteId);
				}
			}
			else if( fromWhere.equals("ProductDescription"))
			{
				if( quoteId.equals("-1") )
				{
					
				}
				else
				{
					MySharedPref.getInstance(context_pd).setString(HTTP_RESPONSE_AddToCart.AddToCart_+HTTP_RESPONSE_AddToCart.quoteid, quoteId);
				}
			}
			else if ( fromWhere.equals("DialogAsFlipKart_searchResult") )
			{
				if( quoteId.equals("-1") )
				{
					
				}
				else
				{
					MySharedPref.getInstance(context_dialog_SR).setString(HTTP_RESPONSE_AddToCart.AddToCart_+HTTP_RESPONSE_AddToCart.quoteid, quoteId);
				}
			}
			if( status != null )
			{
				if ( message != null )
				{
					if ( fromWhere.equals("DialogAsFlipKart_productList"))
						Toast.makeText(context_dialog_pl, message, Toast.LENGTH_LONG).show() ;
					else if( fromWhere.equals("ProductDescription"))
						Toast.makeText(context_pd, message, Toast.LENGTH_LONG).show() ;
					else if( fromWhere.equals("DialogAsFlipKart_searchResult"))
						Toast.makeText(context_dialog_SR, message, Toast.LENGTH_LONG).show() ;
				}
			}
		}
		catch ( Exception err )
		{
			err.printStackTrace();
			mLogs.showTempLog("Error in Parsing Json Stream");
			message = "Error in Response";
		}
	}
}

package com.little_flora.Activity.Async;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.util.Log;

import com.little_flora.R;
import com.little_flora.Activity.ProductList_Grid;
import com.little_flora.Activity.Utils.ConvertStreamIntoString;
import com.little_flora.Activity.Utils.HTTP_POST_parameters.HTTP_MainCategory;
import com.little_flora.Activity.Utils.HTTP_POST_urls;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_Products;
import com.little_flora.Activity.Utils.LF_Constants.Con_SpalshScreen;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.Activity.Utils.Toasts_;

public class ProductsInfo_Async extends AsyncTask<String, String, InputStream>{
	
	private ProductList_Grid context;
	private String[] data;
	private Logs_ mLog;
	private Toasts_ mToast;
	private HttpPost httppost;
	private InputStream is;
	private ProgressDialog pDialog;
	String productId ;
	private String s;
	private String message;
	private JSONArray jsonArray;
	private String language;
	
	public ProductsInfo_Async(String[] data, ProductList_Grid context ,String productId) {
		this.context = context;
		this.data = data;
		this.productId = productId ;
		
		mLog = new Logs_("MainCategory_Async");
		for (int i = 0; i < data.length; i++) {
			showLog("data[" + i + "] :" + data[i]);
			
			mToast = new Toasts_(context) ;
		}
		
		language = MySharedPref.getInstance(context).getString(Con_SpalshScreen.GlobalStoredLanguageForApp);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		initProgressDialog(context.getResources().getString(
				R.string.loading_Products));
	}

	@Override
	protected InputStream doInBackground(String... params) {
		// TODO Auto-generated method stub
		HttpClient httpclient = new DefaultHttpClient();
		
		if ( language.equals("English"))
		{
			httppost = new HttpPost(HTTP_POST_urls.productsInfo_EN_1+productId+HTTP_POST_urls.productsInfo_EN_2);
			showLog(HTTP_POST_urls.productsInfo_EN_1+productId+HTTP_POST_urls.productsInfo_EN_2);
		}
		else
		{
			httppost = new HttpPost(HTTP_POST_urls.productsInfo_AR_1+productId+HTTP_POST_urls.productsInfo_AR_2);
			showLog(HTTP_POST_urls.productsInfo_AR_1+productId+HTTP_POST_urls.productsInfo_AR_2);
		}
		
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);

			// nameValuePairs.add(new
			// BasicNameValuePair(HTTP_MainCategory.value, data[0]));

			nameValuePairs.add(new BasicNameValuePair(
					HTTP_MainCategory.formsubmitted, "TRUE"));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse response = httpclient.execute(httppost);

			HttpEntity entity1 = response.getEntity();

			BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity1);

			is = bufHttpEntity.getContent();

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			showLog("ClientProtocolException");
		} catch (IOException e) {
			e.printStackTrace();
			showLog("IOException");
		}

		return is;
	}

	public void showLog(String msg) {
		Log.e("FavoriteSync", msg);
	}

	public void initProgressDialog(String loadingText) {
		pDialog = new ProgressDialog(context);
		pDialog.setMessage(loadingText);
		pDialog.setCancelable(false);
		pDialog.show();
		
		pDialog.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				mToast.showImpToast(context.getResources().getString(R.string.loading_isCanceled)) ;
			}
		});
	}
	
	@Override
	protected void onPostExecute(InputStream result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		pDialog.dismiss();
		try {
			s = ConvertStreamIntoString.convertStreamToString(result);
			System.out.println(s);
		} catch (Exception err) {
			err.printStackTrace();
			showLog("Error in Creating Json Object");
			message = "Error in fetching data";
		}
		try {
			jsonArray = new JSONArray(s);
			
			if (jsonArray == null) {
				message = "invalidJson";
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			showLog("Error in Creating Json Object");
			message = "Error in fetching data";
		}	
		
		Dictionary<String,Dictionary<String, String>> productList = new Hashtable<String,Dictionary<String, String>>() ;
		
		try
		{
			for ( int i = 0 ; i < jsonArray.length() ; i ++ )
			{
				JSONObject json = jsonArray.getJSONObject(i) ;
				
				Dictionary<String, String> detailsOfSingleProject = new Hashtable<String, String>() ;
				detailsOfSingleProject.put(HTTP_RESPONSE_Products.name, getString(json, HTTP_RESPONSE_Products.name));
				detailsOfSingleProject.put(HTTP_RESPONSE_Products.description, getString(json, HTTP_RESPONSE_Products.description));
				detailsOfSingleProject.put(HTTP_RESPONSE_Products.small_image, getString(json, HTTP_RESPONSE_Products.small_image));
				detailsOfSingleProject.put(HTTP_RESPONSE_Products.is_salable, getString(json, HTTP_RESPONSE_Products.is_salable));
				detailsOfSingleProject.put(HTTP_RESPONSE_Products.qty, getString(json, HTTP_RESPONSE_Products.qty));
				detailsOfSingleProject.put(HTTP_RESPONSE_Products.id, getString(json, HTTP_RESPONSE_Products.id));
				detailsOfSingleProject.put(HTTP_RESPONSE_Products.sku, getString(json, HTTP_RESPONSE_Products.sku));
				detailsOfSingleProject.put(HTTP_RESPONSE_Products.price, getString(json, HTTP_RESPONSE_Products.price));
				detailsOfSingleProject.put(HTTP_RESPONSE_Products.image, getString(json, HTTP_RESPONSE_Products.image));
				detailsOfSingleProject.put(HTTP_RESPONSE_Products.ShortDescription, getString(json, HTTP_RESPONSE_Products.ShortDescription));
				
				productList.put(detailsOfSingleProject.get(HTTP_RESPONSE_Products.id), detailsOfSingleProject) ;
				
			}
		}
		catch ( Exception err )
		{
			err.printStackTrace();
			showLog("Error in Parsing Data");
			message = "Error in Parsing Data";
		}
		
		context.getProductList(productList) ;
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
}

package com.little_flora.Activity;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.little_flora.R;
import com.little_flora.Activity.Utils.CheckNetwork;
import com.little_flora.Activity.Utils.HTTP_POST_urls;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.Activity.Utils.Toasts_;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_AddToCart;
import com.little_flora.Activity.Utils.LF_Constants.Con_UserDetails;
import com.little_flora.ProductsSync.OrderSync;

public class PaymentPage extends BaseActivity implements OnClickListener {
	private Toasts_ mToast;
	private Logs_ mLogs;
	private Button btn_payment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paymentpage);
		
		ArrayList<String> navigationDrawerList = getIntent()
				.getStringArrayListExtra("navigationDrawerList");
		if (navigationDrawerList == null) {
			Log.e("NullPointerException : ", "navigationDrawerList is null");
		}
		listToSlideDrawerNavigation = navigationDrawerList;

		String[] navigationArray = navigationDrawerList
				.toArray(new String[navigationDrawerList.size()]);

		navigationDrawerSetup(navigationArray);

		mToast = new Toasts_(this);
		mLogs = new Logs_("CustomerAddress");
		
		initView();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		MySharedPref.getInstance(this).setString("MyCurrentActivityOnPage","PaymentPage") ;

	}
	
	

	public JSONObject createData(String customerId , String quoteId , Dictionary<String, Dictionary<String, String>> dictionarySend) 
	{
		//[{"345":[{"qty":"1"}]},{"346":[{"qty":"1"}]}]
		
		//{"customer_id":"205","quoteid":"1057","prodarr":[{"345":[{"qty":"1"}]},{"346":[{"qty":"1"}]}]}
		
		JSONObject jsonObject = null ;
		
		try
		{
			jsonObject = new JSONObject();
			
			jsonObject.put("customer_id",customerId);
			jsonObject.put("quoteid",quoteId);
			
			JSONArray jsonArray = new JSONArray() ;
			
			for ( int i = 0 ; i < dictionarySend.size() ; i++ )
			{
				Dictionary<String, String> dictonary = dictionarySend.get(Integer.toString(i)) ;
				
				JSONObject ithJsonObject = new JSONObject() ;
				
				JSONArray ithJsonArray = new JSONArray() ;
				JSONObject ithJsonArray_ithJsonObject = new JSONObject() ;
				
				ithJsonArray_ithJsonObject.putOpt("qty", dictonary.get("qty"));
				ithJsonArray.put(0, ithJsonArray_ithJsonObject);
				
				ithJsonObject.put(dictonary.get("productId"), ithJsonArray) ;
				
				jsonArray.put(i, ithJsonObject);
			}
			
			jsonObject.putOpt("prodarr", jsonArray) ;
		}
		catch ( Exception err )
		{
			err.printStackTrace() ;
		}

		return jsonObject ;
	}
	
	public void initView()
	{
		btn_payment = ( Button ) findViewById(R.id.btn_payment) ;
		btn_payment.setOnClickListener(this) ;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) 
		{
		case R.id.btn_payment:
			callOrder_Page();
			break;

		default:
			break;
		}
	}
	
	public void callOrder_Page()
	{
		if ( CheckNetwork.isNetworkAvailable(this))
		{
			String url = HTTP_POST_urls.payment_order_new ;
//			String quoteid = MySharedPref.getInstance(this).getString(HTTP_RESPONSE_AddToCart.AddToCart_+"buynow_quoteId");
//			String customer_id = MySharedPref.getInstance(this).getString(Con_UserDetails.userId) ;
//			JSONObject jsonObject = createData(customer_id,quoteid,dictionaryToSend) ;
			
			Dictionary<String, String> dictionary = new Hashtable<String, String>() ;

			String loadingText = getResources().getString(R.string.loading_PaymentPageInProcess) ;
			
			OrderSync objectOrder = new OrderSync(null, null, url, dictionary, this, loadingText, "ForgotPassword");
			objectOrder.execute("") ;
		}
		else
		{
			getResources().getString(R.string.networkNotPresent) ;
		}
	}
	
	public void callPaymentPage()
	{
		
		Dictionary<String, Dictionary<String, String>> dictionaryToSend = new Hashtable<String, Dictionary<String,String>>() ;
		
		if ( CheckNetwork.isNetworkAvailable(this))
		{
			String url = HTTP_POST_urls.payment_order ;
			String quoteid = MySharedPref.getInstance(this).getString(HTTP_RESPONSE_AddToCart.AddToCart_+"buynow_quoteId");
			String customer_id = MySharedPref.getInstance(this).getString(Con_UserDetails.userId) ;
			JSONObject jsonObject = createData(customer_id,quoteid,dictionaryToSend) ;
			
			Dictionary<String, String> dictionary = new Hashtable<String, String>() ;
			dictionary.put("quoteid", quoteid);
			dictionary.put("customer_id", customer_id);

			String loadingText = getResources().getString(R.string.loading_PaymentPageInProcess) ;
			
			OrderSync objectOrder = new OrderSync(jsonObject, null, url, dictionary, this, loadingText, "Order");
			objectOrder.execute("") ;
		}
		else
		{
			getResources().getString(R.string.networkNotPresent) ;
		}
	}
	
	
}

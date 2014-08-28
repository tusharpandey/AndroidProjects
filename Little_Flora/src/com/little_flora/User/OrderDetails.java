package com.little_flora.User;


import java.util.ArrayList;
import java.util.Dictionary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.little_flora.R;
import com.little_flora.Activity.BaseActivity;
import com.little_flora.Activity.Utils.CheckNetwork;
import com.little_flora.Activity.Utils.HTTP_POST_urls;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.Activity.Utils.LF_Constants.Con_UserDetails;
import com.little_flora.Activity.Utils.Toasts_;
import com.little_flora.Dialogs.DialogForStoredAddress;
import com.little_flora.User.Adapter.CustomerListAdapter;
import com.little_flora.User.Async.CustomerAddressListAsync;
import com.little_flora.User.Callback.CustomerAddressListCallback;
import com.little_flora.User.orderdetails.fragment.BillingAddress;

public class OrderDetails extends BaseActivity implements CustomerAddressListCallback
{


	public TextView txt_orderdetailtitle;
	public ProgressBar pgb_orderdetailprogress;
	private Logs_ mLogs;
	private Toasts_ mToast;
	private ArrayList<Dictionary<String, String>> listOfCustomerAddress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orderdetails);
		

		
		initView() ;
		
		mLogs = new Logs_("OrderDetails") ;
		mToast = new Toasts_(this) ;
		
		clearWholeMySharedPreferenceRelatedwith_OrderDetails();
		

		ArrayList<String> navigationDrawerList = getIntent().getStringArrayListExtra("navigationDrawerList");
		
    	if ( navigationDrawerList == null )
    	{
    		Log.e("NullPointerException : ", "navigationDrawerList is null") ;
    	}
        
    	listToSlideDrawerNavigation = navigationDrawerList ;
        
        String [] navigationArray = navigationDrawerList.toArray(new String[navigationDrawerList.size()]);
        
		navigationDrawerSetup(navigationArray);
		
		if ( CheckNetwork.isNetworkAvailable(this) )
		{
			String [] data = new String [1] ;
			String loadingText = getResources().getString(R.string.loading_customerAddresslist);
			String url = HTTP_POST_urls.customerAddressList+MySharedPref.getInstance(this).getString(
					Con_UserDetails.userId) ;
			
			//String url = HTTP_POST_urls.customerAddressList+205;

			mLogs.showTempLog(url) ;
			
			CustomerAddressListAsync searchResultAsync = new CustomerAddressListAsync(url, data, this, loadingText) ;
			searchResultAsync.execute("");
		}
		else
		{
			mToast.showImpToast(getResources().getString(R.string.networkNotPresent));
		}
	}
	
	public void initView()
	{
		txt_orderdetailtitle = ( TextView ) findViewById(R.id.txt_orderdetailtitle) ;
		pgb_orderdetailprogress = ( ProgressBar ) findViewById(R.id.pgb_orderdetailprogress) ;	
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MySharedPref.getInstance(this).setString("MyCurrentActivityOnPage","OrderDetails") ;

	}
	
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		
		switchContent(new BillingAddress(), addToBack, add, tag);
	}
	
	public void clearWholeMySharedPreferenceRelatedwith_OrderDetails()
	{
		MySharedPref.getInstance(this).setString("OrderDetails"+"BillingAddress"+"edt_firstName", "");
		MySharedPref.getInstance(this).setString("OrderDetails"+"BillingAddress"+"edt_lastName", "");
		MySharedPref.getInstance(this).setString("OrderDetails"+"BillingAddress"+"edt_street1", "");
		MySharedPref.getInstance(this).setString("OrderDetails"+"BillingAddress"+"edt_street2", "");
		MySharedPref.getInstance(this).setString("OrderDetails"+"BillingAddress"+"edt_city", "");
		MySharedPref.getInstance(this).setString("OrderDetails"+"BillingAddress"+"edt_postcode", "");
		MySharedPref.getInstance(this).setString("OrderDetails"+"BillingAddress"+"edt_telephone", "");
		
		MySharedPref.getInstance(this).setString("OrderDetails"+"ShippingAddress"+"edt_firstName", "");
		MySharedPref.getInstance(this).setString("OrderDetails"+"ShippingAddress"+"edt_lastName", "");
		MySharedPref.getInstance(this).setString("OrderDetails"+"ShippingAddress"+"edt_street1", "");
		MySharedPref.getInstance(this).setString("OrderDetails"+"ShippingAddress"+"edt_street2", "");
		MySharedPref.getInstance(this).setString("OrderDetails"+"ShippingAddress"+"edt_city", "");
		MySharedPref.getInstance(this).setString("OrderDetails"+"ShippingAddress"+"edt_postcode", "");
		MySharedPref.getInstance(this).setString("OrderDetails"+"ShippingAddress"+"edt_telephone", "");
		
		MySharedPref.getInstance(this).setString("OrderDetails"+"ShippingMethod"+"shippingselection", "");

		MySharedPref.getInstance(this).setString("OrderDetails"+"PaymentInfo"+"paymentselection", "");

		MySharedPref.getInstance(this).setString("OrderDetails"+"DeliveryInfo"+"Message", ""+"");
		MySharedPref.getInstance(this).setString("OrderDetails"+"DeliveryInfo"+"Date", "");
	}

	@Override
	public void customerAddressListCallbackResponse(String message,
			String status,
			ArrayList<Dictionary<String, String>> listOfCustomerAddress) {

		// TODO Auto-generated method stub
		if ( status !=null && status.equals("1"))
		{
			if ( listOfCustomerAddress.size() > 0 )
			{
				this.listOfCustomerAddress = listOfCustomerAddress ;
				
				mToast.showImpToast(getResources().getString(R.string.CustomerAddressList_addressFound)) ;
				
				DialogForStoredAddress dialofForStoredAddress = new DialogForStoredAddress(this, listOfCustomerAddress);
				dialofForStoredAddress.setCancelable(true);
				dialofForStoredAddress.show();
			}
			else
			{
				mToast.showImpToast(getResources().getString(R.string.CustomerAddressList_noAddressFound)) ;
			}
		}
		else if ( status ==null || message == null )
		{
			mToast.showImpToast(getResources().getString(R.string.errorInResponse)) ;
		}
		else if ( status.equals("") || message.equals("") )
		{
			mToast.showImpToast(getResources().getString(R.string.errorInResponse)) ;
		}
		else if ( message.contains("Error") )
		{
			mToast.showImpToast(getResources().getString(R.string.errorInResponse)) ;
		}
		else
		{
			mToast.showImpToast(message);
		}
	}
}

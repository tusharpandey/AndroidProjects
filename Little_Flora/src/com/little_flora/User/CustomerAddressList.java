package com.little_flora.User;

import java.util.ArrayList;
import java.util.Dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.little_flora.R;
import com.little_flora.Activity.BaseActivity;
import com.little_flora.Activity.MainCategory;
import com.little_flora.Activity.Utils.CheckNetwork;
import com.little_flora.Activity.Utils.HTTP_POST_urls;
import com.little_flora.Activity.Utils.LF_Constants.Con_UserDetails;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.Activity.Utils.Toasts_;
import com.little_flora.User.Adapter.CustomerListAdapter;
import com.little_flora.User.Async.CustomerAddressListAsync;
import com.little_flora.User.Callback.CustomerAddressListCallback;

public class CustomerAddressList extends BaseActivity implements CustomerAddressListCallback , OnItemClickListener , OnClickListener
{
	private ListView lst_customerAddressList;
	private Toasts_ mToast;
	private Logs_ mLogs;
	private Button btn_addANewAddress;
	private ArrayList<Dictionary<String, String>> listOfCustomerAddress;

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MySharedPref.getInstance(this).setString("MyCurrentActivityOnPage","CustomerAddressList") ;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customeraddresslist);
		
		
		initView();
		
		ArrayList<String> navigationDrawerList = getIntent().getStringArrayListExtra("navigationDrawerList");
		
    	if ( navigationDrawerList == null )
    	{
    		Log.e("NullPointerException : ", "navigationDrawerList is null") ;
    	}
        
    	listToSlideDrawerNavigation = navigationDrawerList ;
        
        String [] navigationArray = navigationDrawerList.toArray(new String[navigationDrawerList.size()]);
        
		navigationDrawerSetup(navigationArray);
		
		mToast = new Toasts_(this);
		mLogs = new Logs_("CustomerAddressList");
		
		if ( CheckNetwork.isNetworkAvailable(this) )
		{
			String [] data = new String [1] ;
			String loadingText = getResources().getString(R.string.loading_customerAddresslist);
			String url = HTTP_POST_urls.customerAddressList+MySharedPref.getInstance(this).getString(
					Con_UserDetails.userId) ;
			
			//String url = HTTP_POST_urls.customerAddressList+205;

			mLogs.showTempLog(url) ;
			
//			CustomerAddressListAsync searchResultAsync = new CustomerAddressListAsync(url, data, this, loadingText) ;
//			searchResultAsync.execute("");
		}
		else
		{
			mToast.showImpToast(getResources().getString(R.string.networkNotPresent));
		}
		
	}
	
	public void initView()
	{
		lst_customerAddressList = ( ListView ) findViewById(R.id.lst_customerAddressList) ;
		lst_customerAddressList.setOnItemClickListener(this);

		btn_addANewAddress =  ( Button ) findViewById(R.id.btn_addANewAddress) ;
		btn_addANewAddress.setOnClickListener(this);
		
	}
	
	@Override
	public void customerAddressListCallbackResponse(String message, String status,
			ArrayList<Dictionary<String, String>> listOfCustomerAddress) {
		// TODO Auto-generated method stub
		if ( status !=null && status.equals("1"))
		{
			if ( listOfCustomerAddress.size() > 0 )
			{
				this.listOfCustomerAddress = listOfCustomerAddress ;
				
				mToast.showImpToast(getResources().getString(R.string.CustomerAddressList_addressFound)) ;
				
				CustomerListAdapter adapter = new CustomerListAdapter(this, listOfCustomerAddress);
				lst_customerAddressList.setAdapter(adapter) ;
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_addANewAddress:
			Intent intent = new Intent(this, CustomerAddress.class);
			intent.putStringArrayListExtra("navigationDrawerList",
					listToSlideDrawerNavigation);
			startActivity(intent);
			
			hideKeyboardEveryTime();
			
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(this, CustomerAddress.class);
		
		
		intent.putExtra("firstname", listOfCustomerAddress.get(position).get("firstname"));
		intent.putExtra("lastname", listOfCustomerAddress.get(position).get("lastname"));
		
		String street = listOfCustomerAddress.get(position).get("street") ;
		
		String street1 = street.substring(0, street.indexOf("\n")-1 );
		String street2 = street.substring(street.indexOf("\n"),street.length() );
		
		mLogs.showTempLog(street1);
		mLogs.showTempLog(street2);
		
		intent.putExtra("street1", street1);
		intent.putExtra("street2",street2 );
		intent.putExtra("city", listOfCustomerAddress.get(position).get("city"));
		intent.putExtra("postcode", listOfCustomerAddress.get(position).get("postcode"));
		intent.putExtra("telephone", listOfCustomerAddress.get(position).get("telephone"));
		
		intent.putStringArrayListExtra("navigationDrawerList",
				listToSlideDrawerNavigation);
		startActivity(intent);
		
		hideKeyboardEveryTime();
		
	}
}

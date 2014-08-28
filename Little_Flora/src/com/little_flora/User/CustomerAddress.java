package com.little_flora.User;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.little_flora.R;
import com.little_flora.Activity.BaseActivity;
import com.little_flora.Activity.PaymentPage;
import com.little_flora.Activity.Utils.CheckNetwork;
import com.little_flora.Activity.Utils.HTTP_POST_urls;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_User;
import com.little_flora.Activity.Utils.LF_Constants.Con_UserDetails;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.Activity.Utils.Toasts_;
import com.little_flora.Activity.Utils.TypeFace_TextView;
import com.little_flora.User.Async.CustomerAddressAsync;
import com.little_flora.User.Callback.CustomerAddressCallback;

public class CustomerAddress extends BaseActivity implements OnClickListener , CustomerAddressCallback {
	private Toasts_ mToast;
	private Logs_ mLogs;
	private EditText edt_firstName;
	private EditText edt_lastName;
	private EditText edt_street1;
	private EditText edt_street2;
	private EditText edt_city;
	private EditText edt_postcode;
	private EditText edt_telephone;
	private Button btn_addNewAddress;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customeraddress);
		


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
		MySharedPref.getInstance(this).setString("MyCurrentActivityOnPage","CustomerAddress") ;
	}

	public void initView() {
		edt_firstName = (EditText) findViewById(R.id.edt_firstName);
		if (getIntent().getStringExtra("firstname") != null)
			edt_firstName.setText(getIntent().getStringExtra("firstname"));

		edt_lastName = (EditText) findViewById(R.id.edt_lastName);
		if (getIntent().getStringExtra("lastname") != null)
			edt_lastName.setText(getIntent().getStringExtra("lastname"));

		edt_street1 = (EditText) findViewById(R.id.edt_street1);
		if (getIntent().getStringExtra("street1") != null)
			edt_street1.setText(getIntent().getStringExtra("street1"));

		edt_street2 = (EditText) findViewById(R.id.edt_street2);
		if (getIntent().getStringExtra("street2") != null)
			edt_street2.setText(getIntent().getStringExtra("street2"));

		edt_city = (EditText) findViewById(R.id.edt_city);
		if (getIntent().getStringExtra("city") != null)
			edt_city.setText(getIntent().getStringExtra("city"));

		edt_postcode = (EditText) findViewById(R.id.edt_postcode);
		if (getIntent().getStringExtra("postcode") != null)
			edt_postcode.setText(getIntent().getStringExtra("postcode"));

		edt_telephone = (EditText) findViewById(R.id.edt_telephone);
		if (getIntent().getStringExtra("telephone") != null)
		{
			edt_telephone.setText(getIntent().getStringExtra("telephone"));
		}
		btn_addNewAddress = (Button) findViewById(R.id.btn_addNewAddress);
		TypeFace_TextView.setTypeFace(this, btn_addNewAddress);
		btn_addNewAddress.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_addNewAddress:

			if ( CheckNetwork.isNetworkAvailable(this)) 
			{
				if ( areValidInputs() )
				{
					String data [] = new String [1] ;
					
					mLogs.showTempLog(HTTP_POST_urls.customerAddress+
							"customer_id="+MySharedPref.getInstance(this).getString(Con_UserDetails.userId)+
							//"customer_id="+205+
							"&email="+MySharedPref.getInstance(this).getString(HTTP_RESPONSE_User.UserLogined_+"email")+
							"&firstname="+edt_firstName.getText().toString().trim()+
							"&lastname="+edt_lastName.getText().toString().trim()+
							"&street1="+edt_street1.getText().toString().trim()+
							"&street2="+edt_street2.getText().toString().trim()+
							"&city="+edt_city.getText().toString().trim()+
							"&postcode="+edt_postcode.getText().toString().trim()+
							"&telephone="+edt_telephone.getText().toString().trim());
					try
					{
					url = HTTP_POST_urls.customerAddress + 
							"customer_id="+MySharedPref.getInstance(this).getString(Con_UserDetails.userId)+
							//"customer_id="+205+
							"&email="+MySharedPref.getInstance(this).getString(HTTP_RESPONSE_User.UserLogined_+"email")+
							"&firstname="+edt_firstName.getText().toString().trim()+
							"&lastname="+edt_lastName.getText().toString().trim()+
							"&street1="+edt_street1.getText().toString().trim()+
							"&street2="+edt_street2.getText().toString().trim()+
							"&city="+edt_city.getText().toString().trim()+
							"&postcode="+edt_postcode.getText().toString().trim()+
							"&telephone="+edt_telephone.getText().toString().trim()+"&is_default_shipping=TRUE&is_default_billing=TRUE";
					}
					catch (Exception err )
					{
						err.printStackTrace();
						mLogs.showTempLog("Error is encoding URL") ;
					}
					
					String newURL = url.replaceAll(" ", "%20");
					
					String loadingText = getResources().getString(R.string.loading_submittingAddress) ;
					
					CustomerAddressAsync objectAsync = new CustomerAddressAsync(newURL, data, this, loadingText);
					objectAsync.execute("") ;
				}
			}
			else {
				Toast.makeText(this,
						getResources().getString(R.string.networkNotPresent),
						Toast.LENGTH_LONG).show();
			}
			break;

		default:
			break;
		}
	}

	public boolean areValidInputs() {
		if (edt_firstName.getText().toString().trim().length() <= 0) {
			mToast.showImpToast(getResources().getString(
					R.string.Registration_invalidFirstName));
			edt_firstName.requestFocus();
			return false;
		} else if (edt_lastName.getText().toString().trim().length() <= 0) {
			mToast.showImpToast(getResources().getString(
					R.string.Registration_invalidLastName));
			edt_lastName.requestFocus();
			return false;
		} else if (edt_street1.getText().toString().trim().length() <= 0) {
			mToast.showImpToast(getResources().getString(
					R.string.CustomerAddress_invalidStreet1));
			edt_street1.requestFocus();
			return false;
		} else if (edt_street2.getText().toString().trim().length() <= 0) {
			mToast.showImpToast(getResources().getString(
					R.string.CustomerAddress_invalidStreet2));
			edt_street2.requestFocus();
			return false;
		} else if (edt_city.getText().toString().trim().length() <= 0) {
			mToast.showImpToast(getResources().getString(
					R.string.CustomerAddress_invalidCity));
			edt_city.requestFocus();
			return false;
		} else if (edt_postcode.getText().toString().trim().length() <= 0) {
			mToast.showImpToast(getResources().getString(
					R.string.CustomerAddress_invalidpostcode));
			edt_postcode.requestFocus();
			return false;
		} else if (edt_telephone.getText().toString().trim().length() < 10
				|| edt_telephone.getText().toString().trim().length() > 10) {
			mToast.showImpToast(getResources().getString(
					R.string.CustomerAddress_invalidtelephoneno));
			edt_telephone.requestFocus();
			return false;
		}
		return true;
	}

	@Override
	public void customerAddressCallbackResponse(  String message , String status , String addressId ) 
	{
		if ( message != null && status != null )
		{
			mToast.showImpToast(message);
			
			Intent intent = new Intent ( this , PaymentPage.class);
			intent.putStringArrayListExtra("navigationDrawerList",
					listToSlideDrawerNavigation);
			startActivity(intent);

		}
	}
}

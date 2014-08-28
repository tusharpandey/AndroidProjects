package com.little_flora.User.orderdetails.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.little_flora.R;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.User.OrderDetails;

public class BillingAddress extends BaseFragment implements OnClickListener,OnItemClickListener{

	
	private Button btn_addNewAddress;
	private OrderDetails orderDetails;
	private EditText edt_firstName;
	private EditText edt_lastName;
	private EditText edt_street1;
	private EditText edt_street2;
	private EditText edt_city;
	private EditText edt_postcode;
	private EditText edt_telephone;

	public BillingAddress() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_billingaddress, container,
				false);
		
		orderDetails = (OrderDetails)getActivity() ;
		
		initView(rootView) ;
		

		return rootView;
	}
	
	private void initView(View v) 
	{
		btn_addNewAddress = ( Button ) v.findViewById(R.id.btn_addNewAddress) ;
		btn_addNewAddress.setOnClickListener(this);
		
		edt_firstName = ( EditText )  v.findViewById(R.id.edt_firstName) ;
		edt_lastName = ( EditText )  v.findViewById(R.id.edt_lastName) ;
		edt_street1 = ( EditText )  v.findViewById(R.id.edt_street1) ;
		edt_street2 = ( EditText )  v.findViewById(R.id.edt_street2) ;
		edt_city = ( EditText )  v.findViewById(R.id.edt_city) ;
		edt_postcode = ( EditText )  v.findViewById(R.id.edt_postcode) ;
		edt_telephone = ( EditText )  v.findViewById(R.id.edt_telephone) ;
		
		orderDetails.txt_orderdetailtitle.setText("Billing Address");
		orderDetails.pgb_orderdetailprogress.setProgress(0);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setDataInEditText(edt_firstName,"edt_firstName");
		setDataInEditText(edt_lastName,"edt_lastName");
		setDataInEditText(edt_street1,"edt_street1");
		setDataInEditText(edt_street2,"edt_street2");
		setDataInEditText(edt_city,"edt_city");
		setDataInEditText(edt_postcode,"edt_postcode");
		setDataInEditText(edt_telephone,"edt_telephone");
	}
	
	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) {
		case R.id.btn_addNewAddress:
			if ( isValidInputs() )
			{
				saveAllData();
				orderDetails.switchContent(new ShippingAddress(), orderDetails.addToBack, orderDetails.add, orderDetails.tag) ;
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
	{
		
	}
	
	public boolean isValidInputs ()
	{
		if ( edt_firstName.getText().toString().trim().length() <= 0 || edt_firstName.getText().toString().trim().equals("") )
		{
			showToast(getStringFromResources(R.string.BillingAddress_invalidFirstName,orderDetails),orderDetails) ;
			edt_firstName.requestFocus();
			return false ;
		}
		else if ( edt_lastName.getText().toString().trim().length() <= 0 || edt_lastName.getText().toString().trim().equals(""))
		{
			showToast(getStringFromResources(R.string.BillingAddress_invalidLastName,orderDetails),orderDetails) ;
			edt_lastName.requestFocus();
			return false ;
		}
		else if ( edt_street1.getText().toString().trim().length() <= 0 || edt_street1.getText().toString().trim().equals(""))
		{
			showToast(getStringFromResources(R.string.BillingAddress_invalidStreet1,orderDetails),orderDetails) ;
			edt_street1.requestFocus();
			return false ;
		}
		else if ( edt_street2.getText().toString().trim().length() <= 0 || edt_street2.getText().toString().trim().equals(""))
		{
			showToast(getStringFromResources(R.string.BillingAddress_invalidStreet2,orderDetails),orderDetails) ;
			edt_street2.requestFocus();
			return false ;
		}
		else if ( edt_city.getText().toString().trim().length() <= 0 || edt_city.getText().toString().trim().equals(""))
		{
			showToast(getStringFromResources(R.string.BillingAddress_invalidCity,orderDetails),orderDetails) ;
			edt_city.requestFocus();
			return false ;
		}
		else if ( edt_postcode.getText().toString().trim().length() <= 0 || edt_postcode.getText().toString().trim().equals(""))
		{
			showToast(getStringFromResources(R.string.BillingAddress_invalidPostCode,orderDetails),orderDetails) ;
			edt_postcode.requestFocus();
			return false ;
		}	
		else if ( edt_telephone.getText().toString().trim().length() <= 0 || edt_telephone.getText().toString().trim().equals(""))
		{
			showToast(getStringFromResources(R.string.BillingAddress_invalidTelephone,orderDetails),orderDetails) ;
			edt_telephone.requestFocus();
			return false ;
		}		
		return true ;
	}

	public void saveAllData ()
	{
		String str_edt_firstName = edt_firstName.getText().toString().trim() ;
		String str_edt_lastName = edt_lastName.getText().toString().trim() ;
		String str_edt_street1 = edt_street1.getText().toString().trim() ;
		String str_edt_street2 = edt_street2.getText().toString().trim() ;
		String str_edt_city = edt_city.getText().toString().trim() ;
		String str_edt_postcode = edt_postcode.getText().toString().trim() ;
		String str_edt_telephone = edt_telephone.getText().toString().trim() ;
		
		
		save_clear_DataInSahredPreference(str_edt_firstName,"edt_firstName");
		save_clear_DataInSahredPreference(str_edt_lastName,"edt_lastName");
		save_clear_DataInSahredPreference(str_edt_street1,"edt_street1");
		save_clear_DataInSahredPreference(str_edt_street2,"edt_street2");
		save_clear_DataInSahredPreference(str_edt_city,"edt_city");
		save_clear_DataInSahredPreference(str_edt_postcode,"edt_postcode");
		save_clear_DataInSahredPreference(str_edt_telephone,"edt_telephone");	
	}

	public void save_clear_DataInSahredPreference(String string,String tag)
	{
		MySharedPref.getInstance(orderDetails).setString("OrderDetails"+"BillingAddress"+tag, "");
		MySharedPref.getInstance(orderDetails).setString("OrderDetails"+"BillingAddress"+tag, string);
	}
	
	public void setDataInEditText(EditText edtText , String tag)
	{
		String value = MySharedPref.getInstance(orderDetails).getString("OrderDetails"+"BillingAddress"+tag);
		
		if ( value != null && ! value.equals(""))
		{
			edtText.setText(value);
		}
	}
	
	public void printLogs(String str)
	{
		Log.e("BillingAddresss", str);
	}
}

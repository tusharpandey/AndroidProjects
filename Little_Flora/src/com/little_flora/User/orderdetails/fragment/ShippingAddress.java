package com.little_flora.User.orderdetails.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.little_flora.R;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.User.OrderDetails;

public class ShippingAddress extends BaseFragment implements OnClickListener,OnItemClickListener{

	
	private Button btn_addNewAddress;
	private OrderDetails orderDetails;
	private Button btn_back;
	private EditText edt_firstName;
	private EditText edt_lastName;
	private EditText edt_street1;
	private EditText edt_street2;
	private EditText edt_city;
	private EditText edt_postcode;
	private EditText edt_telephone;

	public ShippingAddress() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_shippingaddress, container,
				false);
		
		orderDetails = (OrderDetails)getActivity() ;

		initView(rootView) ;

		return rootView;
	}
	
	private void initView(View v) 
	{
		btn_addNewAddress = ( Button ) v.findViewById(R.id.btn_addNewAddress) ;
		btn_addNewAddress.setOnClickListener(this);
		
		btn_back = ( Button ) v.findViewById(R.id.btn_back) ;
		btn_back.setOnClickListener(this);
		
		orderDetails.txt_orderdetailtitle.setText("Shipping Address");
		orderDetails.pgb_orderdetailprogress.setProgress(20);
		
		edt_firstName = ( EditText )  v.findViewById(R.id.edt_firstName) ;
		setDataInEditText(edt_firstName,"edt_firstName");
		edt_lastName = ( EditText )  v.findViewById(R.id.edt_lastName) ;
		setDataInEditText(edt_lastName,"edt_lastName");
		edt_street1 = ( EditText )  v.findViewById(R.id.edt_street1) ;
		setDataInEditText(edt_street1,"edt_street1");
		edt_street2 = ( EditText )  v.findViewById(R.id.edt_street2) ;
		setDataInEditText(edt_street2,"edt_street2");
		edt_city = ( EditText )  v.findViewById(R.id.edt_city) ;
		setDataInEditText(edt_city,"edt_city");
		edt_postcode = ( EditText )  v.findViewById(R.id.edt_postcode) ;
		setDataInEditText(edt_postcode,"edt_postcode");
		edt_telephone = ( EditText )  v.findViewById(R.id.edt_telephone) ;
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
				orderDetails.switchContent(new ShippingMethod(), orderDetails.addToBack, orderDetails.add, orderDetails.tag) ;
			}
			break;
		case R.id.btn_back:
			orderDetails.switchContent(new BillingAddress(), orderDetails.addToBack, orderDetails.add, orderDetails.tag) ;
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {}
	
	public boolean isValidInputs ()
	{
		if ( edt_firstName.getText().toString().trim().length() <= 0 )
		{
			showToast(getStringFromResources(R.string.BillingAddress_invalidFirstName,orderDetails),orderDetails) ;
			edt_firstName.requestFocus();
			return false ;
		}
		else if ( edt_lastName.getText().toString().trim().length() <= 0 )
		{
			showToast(getStringFromResources(R.string.BillingAddress_invalidLastName,orderDetails),orderDetails) ;
			edt_lastName.requestFocus();
			return false ;
		}
		else if ( edt_street1.getText().toString().trim().length() <= 0 )
		{
			showToast(getStringFromResources(R.string.BillingAddress_invalidStreet1,orderDetails),orderDetails) ;
			edt_street1.requestFocus();
			return false ;
		}
		else if ( edt_street2.getText().toString().trim().length() <= 0 )
		{
			showToast(getStringFromResources(R.string.BillingAddress_invalidStreet2,orderDetails),orderDetails) ;
			edt_street2.requestFocus();
			return false ;
		}
		else if ( edt_city.getText().toString().trim().length() <= 0 )
		{
			showToast(getStringFromResources(R.string.BillingAddress_invalidCity,orderDetails),orderDetails) ;
			edt_city.requestFocus();
			return false ;
		}
		else if ( edt_postcode.getText().toString().trim().length() <= 0 )
		{
			showToast(getStringFromResources(R.string.BillingAddress_invalidPostCode,orderDetails),orderDetails) ;
			edt_postcode.requestFocus();
			return false ;
		}	
		else if ( edt_telephone.getText().toString().trim().length() <= 0 )
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
		MySharedPref.getInstance(orderDetails).setString("OrderDetails"+"ShippingAddress"+tag, "");
		MySharedPref.getInstance(orderDetails).setString("OrderDetails"+"ShippingAddress"+tag, string);
	}
	
	public void setDataInEditText(EditText edtText , String tag)
	{
		String value = MySharedPref.getInstance(orderDetails).getString("OrderDetails"+"ShippingAddress"+tag);
		
		if ( value != null && !value.equals(""))
		{
			edtText.setText(value);
		}
	}
}

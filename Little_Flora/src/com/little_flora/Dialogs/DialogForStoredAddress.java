
package com.little_flora.Dialogs;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import org.json.JSONObject;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.little_flora.R;
import com.little_flora.Activity.Adapter.StoredAddress_SpinnerAdapter;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.Activity.Utils.Toasts_;
import com.little_flora.Activity.Utils.TypeFace_TextView;
import com.little_flora.User.OrderDetails;

public class DialogForStoredAddress extends Dialog implements
android.view.View.OnClickListener 
{
	private Logs_ mLogs;
	OrderDetails context ;

	private TextView txt_Dialog_storedAddress;
	private TextView txt_Dialog_useAs;
	private CheckBox chk_useAsShipping;
	private CheckBox chk_useAsBilling;
	
	private Toasts_ mToast;
	ArrayList<Dictionary<String, String>> listDictionary ;
	private Button btn_Dialog_continue;
	private Spinner spi_SelectAddress_Billing;
	private Spinner spi_SelectAddress_Shipping ;
	
	public DialogForStoredAddress(OrderDetails context ,ArrayList<Dictionary<String, String>> listDictionary) 
	{
		super(context);
		this.context = context ;
		this.listDictionary = listDictionary ;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.dialog_stored_previous_address);
		
		mLogs = new Logs_("DialogAsFlipkartSharing") ;
		mToast = new Toasts_(context) ;
		
		initView();
	}

	public void initView()
	{
		txt_Dialog_storedAddress = ( TextView ) findViewById(R.id.txt_Dialog_storedAddress);
		TypeFace_TextView.setTypeFace(context, txt_Dialog_storedAddress);

		txt_Dialog_useAs = ( TextView ) findViewById(R.id.txt_Dialog_useAs);
		TypeFace_TextView.setTypeFace(context, txt_Dialog_useAs);

		chk_useAsShipping = ( CheckBox ) findViewById(R.id.chk_useAsShipping);
		chk_useAsShipping.setOnClickListener(this);
		chk_useAsBilling = ( CheckBox ) findViewById(R.id.chk_useAsBilling);
		chk_useAsBilling.setOnClickListener(this);

		btn_Dialog_continue = ( Button ) findViewById(R.id.btn_Dialog_continue);
		TypeFace_TextView.setTypeFace(context, btn_Dialog_continue);
		btn_Dialog_continue.setOnClickListener(this) ;
		
		spi_SelectAddress_Billing = ( Spinner) findViewById(R.id.spi_SelectAddress_Billing) ;
		spi_SelectAddress_Shipping = ( Spinner) findViewById(R.id.spi_SelectAddress_Shipping) ;
		setDataInSpinnerAdapter();
	}
	
	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) {

		case R.id.btn_Dialog_continue:
			
			setDataIn_SharedPreference();
			
			this.dismiss();
			break;

		default:
			break;
		}
	}
	
	public void setDataInSpinnerAdapter ()
	{
		ArrayList<String> listOfData = new ArrayList<String>();
		
		for ( int i = 0 ; i < listDictionary.size() ; i ++ )
		{
			Dictionary<String, String> dictionary = listDictionary.get(i) ;
			
			String addressLine = "" ;
			Enumeration<String> enumeration = dictionary.keys();
			
			while ( enumeration.hasMoreElements() )
			{
				String keyOfJsonObject = enumeration.nextElement();
				addressLine = addressLine+" "+dictionary.get(keyOfJsonObject);
			}
			
			listOfData.add(addressLine);
		}
		
		String [] countries = listOfData.toArray(new String[listOfData.size()]);
		
//		String colors[] = {"Red","Blue","White","Yellow","Black", "Green","Purple","Orange","Grey"};

		StoredAddress_SpinnerAdapter spinnerArrayAdapter = new StoredAddress_SpinnerAdapter(context, countries);
		
		spi_SelectAddress_Billing.setAdapter(spinnerArrayAdapter);
		spi_SelectAddress_Shipping.setAdapter(spinnerArrayAdapter);
	}
	
	public void setDataIn_SharedPreference ()
	{
		if ( !chk_useAsBilling.isChecked() )
		{
			int SelectedPosition = spi_SelectAddress_Shipping.getSelectedItemPosition();
			Dictionary<String, String> dictionary = listDictionary.get(SelectedPosition) ;
			
			String fisrtName = dictionary.get("firstname");
			mLogs.showTempLog(fisrtName);
			
			String lastName = dictionary.get("lastname");
			mLogs.showTempLog(lastName);
			
			String street = dictionary.get("street") ;
			if (street == null )
				street = "" ;
			
			mLogs.showTempLog(street);
			
			String street1 = "" ;
			String street2 = "" ;
			
			if ( street.length() > 0 )
			{
				if( street.contains("\n"))
				{
					street1 = street.substring(0, street.indexOf("\n")-1 );
					street2 = street.substring(street.indexOf("\n"),street.length() );
				}
				else
				{
					street1 = street ;
					street2 = street ;
				}
			}
			
			mLogs.showTempLog(street1);
			mLogs.showTempLog(street2);
			
			
			String city = dictionary.get("city");
			if (city == null )
				city = "" ;
			mLogs.showTempLog(city);
			
			String postcode = dictionary.get("postcode");
			if (postcode == null )
				postcode = "" ;
			mLogs.showTempLog(postcode);
			
			String telephone = dictionary.get("telephone");
			if (telephone == null )
				telephone = "" ;
			mLogs.showTempLog(telephone);
			
			MySharedPref.getInstance(context).setString("OrderDetails"+"BillingAddress"+"edt_firstName", fisrtName);
			MySharedPref.getInstance(context).setString("OrderDetails"+"BillingAddress"+"edt_lastName", lastName);
			MySharedPref.getInstance(context).setString("OrderDetails"+"BillingAddress"+"edt_street1", street1);
			MySharedPref.getInstance(context).setString("OrderDetails"+"BillingAddress"+"edt_street2", street2);
			MySharedPref.getInstance(context).setString("OrderDetails"+"BillingAddress"+"edt_city", city);
			MySharedPref.getInstance(context).setString("OrderDetails"+"BillingAddress"+"edt_postcode", postcode);
			MySharedPref.getInstance(context).setString("OrderDetails"+"BillingAddress"+"edt_telephone", telephone);
		}
		else if ( chk_useAsBilling.isChecked()  )
		{
			MySharedPref.getInstance(context).setString("OrderDetails"+"BillingAddress"+"edt_firstName", "");
			MySharedPref.getInstance(context).setString("OrderDetails"+"BillingAddress"+"edt_lastName", "");
			MySharedPref.getInstance(context).setString("OrderDetails"+"BillingAddress"+"edt_street1", "");
			MySharedPref.getInstance(context).setString("OrderDetails"+"BillingAddress"+"edt_street2", "");
			MySharedPref.getInstance(context).setString("OrderDetails"+"BillingAddress"+"edt_city", "");
			MySharedPref.getInstance(context).setString("OrderDetails"+"BillingAddress"+"edt_postcode", "");
			MySharedPref.getInstance(context).setString("OrderDetails"+"BillingAddress"+"edt_telephone", "");
		}			

		if ( !chk_useAsShipping.isChecked() )
		{
			int SelectedPosition = spi_SelectAddress_Shipping.getSelectedItemPosition();
			Dictionary<String, String> dictionary = listDictionary.get(SelectedPosition) ;
			
			String fisrtName = dictionary.get("firstname");
			mLogs.showTempLog(fisrtName);
			
			String lastName = dictionary.get("lastname");
			mLogs.showTempLog(lastName);
			
			String street = dictionary.get("street") ;
			if (street == null )
				street = "" ;
			
			mLogs.showTempLog(street);
			
			String street1 = "" ;
			String street2 = "" ;
			
			if ( street.length() > 0 )
			{
				if( street.contains("\n"))
				{
					street1 = street.substring(0, street.indexOf("\n")-1 );
					street2 = street.substring(street.indexOf("\n"),street.length() );
				}
				else
				{
					street1 = street ;
					street2 = street ;
				}
			}
			
			mLogs.showTempLog(street1);
			mLogs.showTempLog(street2);
			
			String city = dictionary.get("city");
			if (city == null )
				city = "" ;
			mLogs.showTempLog(city);
			
			String postcode = dictionary.get("postcode");
			if (postcode == null )
				postcode = "" ;
			mLogs.showTempLog(postcode);
			
			String telephone = dictionary.get("telephone");
			if (telephone == null )
				telephone = "" ;
			mLogs.showTempLog(telephone);
			
			MySharedPref.getInstance(context).setString("OrderDetails"+"ShippingAddress"+"edt_firstName", fisrtName);
			MySharedPref.getInstance(context).setString("OrderDetails"+"ShippingAddress"+"edt_lastName", lastName);
			MySharedPref.getInstance(context).setString("OrderDetails"+"ShippingAddress"+"edt_street1", street1);
			MySharedPref.getInstance(context).setString("OrderDetails"+"ShippingAddress"+"edt_street2", street2);
			MySharedPref.getInstance(context).setString("OrderDetails"+"ShippingAddress"+"edt_city", city);
			MySharedPref.getInstance(context).setString("OrderDetails"+"ShippingAddress"+"edt_postcode", postcode);
			MySharedPref.getInstance(context).setString("OrderDetails"+"ShippingAddress"+"edt_telephone", telephone);
		}
		else if ( chk_useAsShipping.isChecked()  )
		{
			MySharedPref.getInstance(context).setString("OrderDetails"+"ShippingAddress"+"edt_firstName", "");
			MySharedPref.getInstance(context).setString("OrderDetails"+"ShippingAddress"+"edt_lastName", "");
			MySharedPref.getInstance(context).setString("OrderDetails"+"ShippingAddress"+"edt_street1", "");
			MySharedPref.getInstance(context).setString("OrderDetails"+"ShippingAddress"+"edt_street2", "");
			MySharedPref.getInstance(context).setString("OrderDetails"+"ShippingAddress"+"edt_city", "");
			MySharedPref.getInstance(context).setString("OrderDetails"+"ShippingAddress"+"edt_postcode", "");
			MySharedPref.getInstance(context).setString("OrderDetails"+"ShippingAddress"+"edt_telephone", "");
		}

		
	}
}

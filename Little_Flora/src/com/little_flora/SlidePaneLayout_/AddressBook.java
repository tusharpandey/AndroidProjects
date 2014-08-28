package com.little_flora.SlidePaneLayout_;

import java.util.Dictionary;
import java.util.Hashtable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.little_flora.R;
import com.little_flora.Dialogs.DialogFor_EditAddress;
import com.little_flora.SlidePaneLayout_.Adapter.AddressListAdapter;
import com.little_flora.User.orderdetails.fragment.BaseFragment;

public class AddressBook extends BaseFragment implements OnClickListener {

	View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_addressbook,
				container, false);
		myAccount = (MyAccount) getActivity();

		this.rootView = rootView;

		initView();

		return rootView;
	}

	private MyAccount myAccount;
	private LinearLayout lay_allEntries;
	private LinearLayout lay_addNewAddress;
	private LinearLayout lay_billingAddress;
	private LinearLayout lay_shippingAddress;
	private ImageView img_defaultshippingAddress;
	private ImageView img_defaultBillingAddress;
	private ImageView img_addNewAddress;
	private ImageView img_additionalEntries;

	public void initView() {
		myAccount.txt_accountTitle.setText("Address Book");

		lay_allEntries = (LinearLayout) rootView
				.findViewById(R.id.lay_allEntries);
		lay_addNewAddress = (LinearLayout) rootView
				.findViewById(R.id.lay_addNewAddress);
		lay_billingAddress = (LinearLayout) rootView
				.findViewById(R.id.lay_billingAddress);
		lay_shippingAddress = (LinearLayout) rootView
				.findViewById(R.id.lay_shippingAddress);

		img_defaultshippingAddress = (ImageView) rootView
				.findViewById(R.id.img_defaultshippingAddress);
		img_defaultshippingAddress.setOnClickListener(this);

		img_defaultBillingAddress = (ImageView) rootView
				.findViewById(R.id.img_defaultBillingAddress);
		img_defaultBillingAddress.setOnClickListener(this);

		img_addNewAddress = (ImageView) rootView
				.findViewById(R.id.img_addNewAddress);
		img_addNewAddress.setOnClickListener(this);

		img_additionalEntries = (ImageView) rootView
				.findViewById(R.id.img_additionalEntries);
		img_additionalEntries.setOnClickListener(this);
		
		handleLinearLayoutView_show_hide(lay_shippingAddress);
		
		Dictionary<String, String> dictionary = new Hashtable<String, String>() ;
		dictionary.put("txt_defaultShippingHeading", "Default Shipping Address");
		dictionary.put("txt_defaultShippingHeading_text", "Default Shipping Address text");
		dictionary.put("txt_changeDefaultShippingHeading", "Change Default Shipping Address");
		
		workingWithShippingAddress(dictionary);
	
	}

	public void handleLinearLayoutView_show_hide(LinearLayout linearLayout) {
		lay_allEntries = (LinearLayout) rootView
				.findViewById(R.id.lay_allEntries);
		lay_addNewAddress = (LinearLayout) rootView
				.findViewById(R.id.lay_addNewAddress);
		lay_billingAddress = (LinearLayout) rootView
				.findViewById(R.id.lay_billingAddress);
		lay_shippingAddress = (LinearLayout) rootView
				.findViewById(R.id.lay_shippingAddress);

		LinearLayout[] array = new LinearLayout[4];
		array[0] = lay_allEntries;
		array[1] = lay_addNewAddress;
		array[2] = lay_billingAddress;
		array[3] = lay_shippingAddress;

		for (int i = 0; i < array.length; i++) {
			if (linearLayout.equals(array[i])) {
				array[i].setVisibility(View.VISIBLE);
			} else {
				array[i].setVisibility(View.GONE);
			}
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.img_additionalEntries:
			handleLinearLayoutView_show_hide(lay_allEntries);
			
			Dictionary<Integer, Dictionary<String, String>> addList = new Hashtable<Integer, Dictionary<String,String>>() ;
			//addDict.get(Integer.valueOf(position)).get("AddressText")
			
			Dictionary<String, String> dict = new Hashtable<String, String>() ;
			dict.put("AddressText", "Address 1");
			addList.put(Integer.valueOf(0),dict );
			
			dict = new Hashtable<String, String>() ;
			dict.put("AddressText", "Address 2");
			addList.put(Integer.valueOf(1),dict );

			dict = new Hashtable<String, String>() ;
			dict.put("AddressText", "Address 3");
			addList.put(Integer.valueOf(2),dict );
			
			workingWithAddressList(addList) ;
			
			break;
		
		case R.id.img_addNewAddress:
			handleLinearLayoutView_show_hide(lay_addNewAddress);
			
			break;
			
		case R.id.img_defaultBillingAddress:
			handleLinearLayoutView_show_hide(lay_billingAddress);
			
			Dictionary<String, String> dictionary1 = new Hashtable<String, String>() ;
			dictionary1.put("txt_defaultBillingHeading", "Default Billing Address");
			dictionary1.put("txt_defaultBillingHeading_text", "Default Billing Address text");
			dictionary1.put("txt_changeDefaultBillingHeading", "Change Default Billing Address");
			
			workingWithBillingAddress(dictionary1);

			break;

		case R.id.img_defaultshippingAddress:
			handleLinearLayoutView_show_hide(lay_shippingAddress);
			
			Dictionary<String, String> dictionary = new Hashtable<String, String>() ;
			dictionary.put("txt_defaultShippingHeading", "Default Shipping Address");
			dictionary.put("txt_defaultShippingHeading_text", "Default Shipping Address text");
			dictionary.put("txt_changeDefaultShippingHeading", "Change Default Shipping Address");
			
			workingWithShippingAddress(dictionary);
			
			break;

		case R.id.txt_changeDefaultShippingHeading:
			showDialog_editAddress("Edit Shipping Address");
			break;

		case R.id.txt_changeDefaultBillingHeading:
			showDialog_editAddress("Edit Billing Address");
			break;

		default:
			break;
		}
	}

	ListView lst_addressList;

	public void workingWithAddressList(
			Dictionary<Integer, Dictionary<String, String>> dictionaryOfAddressList) {
		lst_addressList = (ListView) rootView
				.findViewById(R.id.lst_addressList);

		AddressListAdapter adapter = new AddressListAdapter(myAccount,
				dictionaryOfAddressList);
		
		lst_addressList.setAdapter(adapter);
	}
	
	public void workingWithShippingAddress(Dictionary<String, String> dictionaryOfShippingAddress)
	{
		TextView txt_defaultShippingHeading ;
		TextView txt_defaultShippingHeading_text ;
		TextView txt_changeDefaultShippingHeading ;
		
		txt_defaultShippingHeading = ( TextView ) rootView.findViewById(R.id.txt_defaultShippingHeading) ;
		txt_defaultShippingHeading_text = ( TextView ) rootView.findViewById(R.id.txt_defaultShippingHeading_text) ;
		txt_changeDefaultShippingHeading = ( TextView ) rootView.findViewById(R.id.txt_changeDefaultShippingHeading) ;
		
		txt_defaultShippingHeading.setText(dictionaryOfShippingAddress.get("txt_defaultShippingHeading"));
		txt_defaultShippingHeading_text.setText(dictionaryOfShippingAddress.get("txt_defaultShippingHeading_text"));
		
		txt_changeDefaultShippingHeading.setText(dictionaryOfShippingAddress.get("txt_changeDefaultShippingHeading"));
		txt_changeDefaultShippingHeading.setOnClickListener(this);
	}
	
	public void workingWithBillingAddress(Dictionary<String, String> dictionaryOfBillingAddress)
	{
		TextView txt_defaultBillingHeading ;
		TextView txt_defaultBillingHeading_text ;
		TextView txt_changeDefaultBillingHeading ;
		
		txt_defaultBillingHeading = ( TextView ) rootView.findViewById(R.id.txt_defaultBillingHeading) ;
		txt_defaultBillingHeading_text = ( TextView ) rootView.findViewById(R.id.txt_defaultBillingHeading_text) ;
		txt_changeDefaultBillingHeading = ( TextView ) rootView.findViewById(R.id.txt_changeDefaultBillingHeading) ;
		
		txt_defaultBillingHeading.setText(dictionaryOfBillingAddress.get("txt_defaultBillingHeading"));
		txt_defaultBillingHeading_text.setText(dictionaryOfBillingAddress.get("txt_defaultBillingHeading_text"));
		
		txt_changeDefaultBillingHeading.setText(dictionaryOfBillingAddress.get("txt_changeDefaultBillingHeading"));		
		txt_changeDefaultBillingHeading.setOnClickListener(this);
	}
	
	public void showDialog_editAddress ( String fromWhere )
	{
		Dictionary<String, String> dictionaryToSend = new Hashtable<String, String>() ;
		
		DialogFor_EditAddress dialogEditAddress = new DialogFor_EditAddress(fromWhere, myAccount, dictionaryToSend);
		dialogEditAddress.setCancelable(true);
		dialogEditAddress.show();
	}
}

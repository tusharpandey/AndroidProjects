package com.little_flora.SlidePaneLayout_.Adapter;

import java.util.Dictionary;
import java.util.Hashtable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.little_flora.R;
import com.little_flora.Dialogs.DialogFor_EditAddress;
import com.little_flora.SlidePaneLayout_.MyAccount;

public class AddressListAdapter extends BaseAdapter
{
	MyAccount context  ;
	Dictionary<Integer, Dictionary<String, String>> addDict ;
	public AddressListAdapter ( MyAccount context , Dictionary<Integer, Dictionary<String, String>> addDict )
	{
		this.addDict = addDict ;
		this.context = context ;
		
		printLogs("Size of Dictionary : "+addDict.size());
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return addDict.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.adapter_addresslist, parent, false) ;
		
		TextView txt_addressText = (TextView) view.findViewById(R.id.txt_addressText) ;
		txt_addressText.setText(addDict.get(Integer.valueOf(position)).get("AddressText")) ;
		
		ImageView img_editAddress = (ImageView) view.findViewById(R.id.img_editAddress) ;
		img_editAddress.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "Edit Address", Toast.LENGTH_SHORT).show();
				
				Dictionary<String, String> dictionaryToSend = new Hashtable<String, String>() ;
				
				DialogFor_EditAddress dialogEditAddress = new DialogFor_EditAddress("Edit Address List's Address", context, dictionaryToSend);
				dialogEditAddress.setCancelable(true);
				dialogEditAddress.show();
			}
		});
		ImageView img_deleteAddress = (ImageView) view.findViewById(R.id.img_deleteAddress) ;
		img_deleteAddress.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "Delete Address", Toast.LENGTH_SHORT).show();
			}
		});
		
		return view;
	}
	
	public void printLogs (String message )
	{
		Log.e("AddressListAdapter", message) ;
	}

}

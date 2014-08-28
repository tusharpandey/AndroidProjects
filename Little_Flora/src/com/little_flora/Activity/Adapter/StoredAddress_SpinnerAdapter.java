package com.little_flora.Activity.Adapter;

import com.little_flora.R;
import com.little_flora.User.OrderDetails;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StoredAddress_SpinnerAdapter extends BaseAdapter 
{
	String [] addressList ;
	OrderDetails context ;
	
	public StoredAddress_SpinnerAdapter ( OrderDetails context , String [] addressList )
	{
		this.addressList = addressList ;
		this.context = context ;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return addressList.length ;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position ;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater = LayoutInflater.from(context) ;
		View view = inflater.inflate(R.layout.adapter_presentaddress, parent, false) ;
		
		TextView txt_address = ( TextView ) view.findViewById(R.id.txt_address) ;
		txt_address.setText(addressList[position]) ;
		
		return view;
	}

}

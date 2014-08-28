package com.little_flora.Activity.Adapter;

import java.util.ArrayList;

import com.little_flora.R;
import com.little_flora.SlidePaneLayout_.MyAccount;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAccountSlidePanelAdapter extends BaseAdapter {

	ArrayList<String> accountSection;
	MyAccount context ;
	
	public MyAccountSlidePanelAdapter(ArrayList<String> accountSection , MyAccount context ) {
		this.accountSection = accountSection ;
		this.context = context ;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return accountSection.size() ;
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
		View view = inflater.inflate(R.layout.adapter_accountsection, parent, false) ;
		
		TextView lblListItem = ( TextView ) view.findViewById(R.id.lblListItem) ;
		lblListItem.setText(accountSection.get(position)) ;
		
		return view;
	}

}

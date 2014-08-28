package com.little_flora.SlidePaneLayout_.Adapter;

import java.util.Dictionary;
import java.util.Hashtable;

import com.little_flora.R;
import com.little_flora.Dialogs.Dialog_myOrdersMore;
import com.little_flora.SlidePaneLayout_.MyAccount;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyOrdersAdapter extends BaseAdapter{

	MyAccount context ;
	Dictionary<Integer, Dictionary<String, String>> myOrdersDict ;
	private ImageView img_orderMore;
	private TextView txt_orderShipTo;
	private TextView txt_orderDate;
	private TextView txt_orderID;
	
	public MyOrdersAdapter ( MyAccount context , Dictionary<Integer, Dictionary<String, String>> myOrdersDict ) 
	{
		this.context = context ;
		this.myOrdersDict = myOrdersDict ;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return myOrdersDict.size() ;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.adapter_myorders, parent, false) ;
		
		img_orderMore = ( ImageView ) view.findViewById(R.id.img_orderMore) ;
		txt_orderShipTo = ( TextView ) view.findViewById(R.id.txt_orderShipTo) ;
		txt_orderDate = ( TextView ) view.findViewById(R.id.txt_orderDate) ;
		txt_orderID = ( TextView ) view.findViewById(R.id.txt_orderID) ;
		
		txt_orderID.setText(myOrdersDict.get(Integer.valueOf(position)).get("txt_orderID"));
		txt_orderDate.setText(myOrdersDict.get(Integer.valueOf(position)).get("txt_orderDate"));
		txt_orderShipTo.setText(myOrdersDict.get(Integer.valueOf(position)).get("txt_orderShipTo"));
		
		img_orderMore.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Dictionary<String, String> dictionary = new Hashtable<String, String>() ;
				
				dictionary.put("txt_orderStatus", myOrdersDict.get(Integer.valueOf(position)).get("txt_orderStatus"));
				dictionary.put("txt_orderOrderTotal", myOrdersDict.get(Integer.valueOf(position)).get("txt_orderOrderTotal"));
				dictionary.put("txt_orderOrderviewOrder", myOrdersDict.get(Integer.valueOf(position)).get("txt_orderOrderviewOrder"));
				
				Dialog_myOrdersMore dialog = new Dialog_myOrdersMore("MyOrdersAdapter", context, dictionary);
				dialog.setCancelable(true);
				dialog.show() ;
			}
		});
		
		return view ;
	}

}

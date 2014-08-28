
package com.little_flora.SlidePaneLayout_;

import java.util.Dictionary;
import java.util.Hashtable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.little_flora.R;
import com.little_flora.SlidePaneLayout_.Adapter.MyOrdersAdapter;
import com.little_flora.User.orderdetails.fragment.BaseFragment;

public class MyOrders extends BaseFragment{
	
	View rootView ;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_myorders, container,
				false);
		
		myAccount = (MyAccount) getActivity();
		this.rootView = rootView ;

		initView();
		

		return rootView;
	}

	private MyAccount myAccount;
	private ListView lst_orders;

	public void initView() {
		myAccount.txt_accountTitle.setText("My Orders");
		
		lst_orders = ( ListView ) rootView.findViewById(R.id.lst_orders) ;
		
		Dictionary<Integer, Dictionary<String, String>> myOrdersDict = new Hashtable<Integer, Dictionary<String,String>>() ;
		
		Dictionary<String, String> dicitonary = new Hashtable<String, String>() ;
		dicitonary.put("txt_orderID", "1");
		dicitonary.put("txt_orderDate", "12-03-2014");
		dicitonary.put("txt_orderShipTo", "tushar");
		dicitonary.put("txt_orderStatus", "Pending");
		dicitonary.put("txt_orderOrderTotal", "200");
		dicitonary.put("txt_orderOrderviewOrder", "View Order");
		
		
		myOrdersDict.put(Integer.valueOf(0), dicitonary);
		
		dicitonary = new Hashtable<String, String>() ;
		dicitonary.put("txt_orderID", "2");
		dicitonary.put("txt_orderDate", "12-03-2014");
		dicitonary.put("txt_orderShipTo", "Ravi");
		dicitonary.put("txt_orderStatus", "Pending");
		dicitonary.put("txt_orderOrderTotal", "200");
		dicitonary.put("txt_orderOrderviewOrder", "View Order");

		myOrdersDict.put(Integer.valueOf(1), dicitonary);

		dicitonary = new Hashtable<String, String>() ;
		dicitonary.put("txt_orderID", "1");
		dicitonary.put("txt_orderDate", "12-03-2014");
		dicitonary.put("txt_orderShipTo", "shekhar");
		dicitonary.put("txt_orderStatus", "Pending");
		dicitonary.put("txt_orderOrderTotal", "200");
		dicitonary.put("txt_orderOrderviewOrder", "View Order");

		myOrdersDict.put(Integer.valueOf(2), dicitonary);

		MyOrdersAdapter adapter = new MyOrdersAdapter (myAccount,myOrdersDict) ;
		lst_orders.setAdapter(adapter) ;
	}
}

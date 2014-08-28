package com.little_flora.User.orderdetails.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.little_flora.R;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.User.OrderDetails;

public class ShippingMethod extends BaseFragment implements OnClickListener,OnItemClickListener{

	private Button btn_addNewAddress;
	private OrderDetails orderDetails;
	private Button btn_back;
	private RadioButton rad_creditcard_bankpayment;
	private RadioButton rad_cashonDelivery;

	public ShippingMethod() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_shippingmethod, container,
				false);
		
		orderDetails = (OrderDetails)getActivity() ;

		initView(rootView) ;

		return rootView;
	}
	
	private void initView(View v) 
	{
		btn_addNewAddress = ( Button ) v.findViewById(R.id.btn_addNewAddress) ;
		btn_addNewAddress.setOnClickListener(this);
		
		orderDetails.txt_orderdetailtitle.setText("Shipping Method");
		orderDetails.pgb_orderdetailprogress.setProgress(40);

		btn_back = ( Button ) v.findViewById(R.id.btn_back) ;
		btn_back.setOnClickListener(this);
		
		String value = MySharedPref.getInstance(orderDetails).getString("OrderDetails"+"ShippingMethod"+"shippingselection");

		rad_creditcard_bankpayment = ( RadioButton ) v.findViewById(R.id.rad_creditcard_bankpayment) ;
		rad_creditcard_bankpayment.setOnClickListener(this);
		rad_cashonDelivery = ( RadioButton ) v.findViewById(R.id.rad_cashonDelivery) ;
		rad_cashonDelivery.setOnClickListener(this);
		
		if (value.equals("1"))
		{
			rad_cashonDelivery.setChecked(true);
		}
		else if (value.equals("2"))
		{
			rad_cashonDelivery.setChecked(true);
		}
	}

	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) {
		case R.id.btn_addNewAddress:
			orderDetails.switchContent(new PaymentInfo(), orderDetails.addToBack, orderDetails.add, orderDetails.tag) ;
			break;
		case R.id.btn_back:
			orderDetails.switchContent(new ShippingAddress(), orderDetails.addToBack, orderDetails.add, orderDetails.tag) ;
			break;
		case R.id.rad_creditcard_bankpayment:
			MySharedPref.getInstance(orderDetails).setString("OrderDetails"+"ShippingMethod"+"shippingselection", "1");
			break;
		case R.id.rad_cashonDelivery:
			MySharedPref.getInstance(orderDetails).setString("OrderDetails"+"ShippingMethod"+"shippingselection", "2");
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {}
	

}

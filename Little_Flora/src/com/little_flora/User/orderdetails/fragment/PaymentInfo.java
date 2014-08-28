package com.little_flora.User.orderdetails.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.little_flora.R;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.User.OrderDetails;

public class PaymentInfo extends BaseFragment implements OnClickListener,OnItemClickListener{

	
	private Button btn_addNewAddress;
	private OrderDetails orderDetails;
	private Button btn_back;
	private RadioButton rad_creditcard_bankpayment;
	private RadioButton rad_cashondeliver;
	private RadioButton rad_creditcardpayment;

	public PaymentInfo() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_paymentinfo, container,
				false);
		
		orderDetails = (OrderDetails)getActivity() ;

		initView(rootView) ;

		return rootView;
	}
	
	private void initView(View v) 
	{
		btn_addNewAddress = ( Button ) v.findViewById(R.id.btn_addNewAddress) ;
		btn_addNewAddress.setOnClickListener(this);
		
		orderDetails.txt_orderdetailtitle.setText("Payment Info");
		orderDetails.pgb_orderdetailprogress.setProgress(60);
		
		btn_back = ( Button ) v.findViewById(R.id.btn_back) ;
		btn_back.setOnClickListener(this);
		
		rad_creditcard_bankpayment = ( RadioButton ) v.findViewById(R.id.rad_creditcard_bankpayment);
		rad_creditcard_bankpayment.setOnClickListener(this);
		
		rad_cashondeliver = ( RadioButton ) v.findViewById(R.id.rad_cashondeliver);
		rad_cashondeliver.setOnClickListener(this);
		
		rad_creditcardpayment = ( RadioButton ) v.findViewById(R.id.rad_creditcardpayment);
		rad_creditcardpayment.setOnClickListener(this);
		
		String value = MySharedPref.getInstance(orderDetails).getString("OrderDetails"+"PaymentInfo"+"paymentselection");
		if (value.equals("1"))
		{
			rad_creditcard_bankpayment.setChecked(true);
		}
		else if (value.equals("2"))
		{
			rad_cashondeliver.setChecked(true);
		}
		else if (value.equals("3"))
		{
			rad_creditcardpayment.setChecked(true);
		}
	}

	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) {
		case R.id.btn_addNewAddress:
			orderDetails.switchContent(new DeliveryInfo(), orderDetails.addToBack, orderDetails.add, orderDetails.tag) ;
			break;
		case R.id.btn_back:
			orderDetails.switchContent(new ShippingMethod(), orderDetails.addToBack, orderDetails.add, orderDetails.tag) ;
			break;
		case R.id.rad_creditcard_bankpayment:
			MySharedPref.getInstance(orderDetails).setString("OrderDetails"+"PaymentInfo"+"paymentselection", "1");
			break;
		case R.id.rad_cashondeliver:
			MySharedPref.getInstance(orderDetails).setString("OrderDetails"+"PaymentInfo"+"paymentselection", "2");
			break;
		case R.id.rad_creditcardpayment:
			MySharedPref.getInstance(orderDetails).setString("OrderDetails"+"PaymentInfo"+"paymentselection", "3");
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {}
	

}

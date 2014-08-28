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
import android.widget.TextView;

import com.little_flora.R;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.User.OrderDetails;

public class OrderInfo extends BaseFragment implements OnClickListener,OnItemClickListener{

	
	private OrderDetails orderDetails;
	private Button btn_addNewAddress;
	private Button btn_back;
	private TextView txt_billingAddress;
	private TextView txt_shippingAddress;
	private TextView txt_shippingMethod;
	private TextView txt_paymentInfo;
	private TextView txt_deliveryInfo;

	public OrderInfo() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_orderreview, container,
				false);
		
		
		orderDetails = (OrderDetails)getActivity() ;
		
		initView(rootView) ;

		return rootView;
	}
	
	private void initView(View v) {
		orderDetails.txt_orderdetailtitle.setText("Order Info");
		orderDetails.pgb_orderdetailprogress.setProgress(100);
		
		btn_addNewAddress = ( Button ) v.findViewById(R.id.btn_addNewAddress) ;
		btn_addNewAddress.setOnClickListener(this);
		
		btn_back = ( Button ) v.findViewById(R.id.btn_back) ;
		btn_back.setOnClickListener(this);
		
		txt_billingAddress = ( TextView ) v.findViewById(R.id.txt_billingAddress) ;
		txt_billingAddress.setText(createDataFor_txt_billingAddress());
		
		txt_shippingAddress = ( TextView ) v.findViewById(R.id.txt_shippingAddress) ;
		txt_shippingAddress.setText(createDataFor_txt_shippingAddress());
		
		txt_shippingMethod = ( TextView ) v.findViewById(R.id.txt_shippingMethod) ;
		txt_shippingMethod.setText(createDataFor_txt_shippingMethod());
		
		txt_paymentInfo = ( TextView ) v.findViewById(R.id.txt_paymentInfo) ;
		txt_paymentInfo.setText(createDataFor_txt_paymentInfo());
		
		txt_deliveryInfo = ( TextView ) v.findViewById(R.id.txt_deliveryInfo) ;
		txt_deliveryInfo.setText(createDataFor_txt_deliveryInfo());
	}

	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) {
		case R.id.btn_addNewAddress:
			//orderDetails.switchContent(new ShippingMethod(), orderDetails.addToBack, orderDetails.add, orderDetails.tag) ;
			break;
		case R.id.btn_back:
			orderDetails.switchContent(new DeliveryInfo(), orderDetails.addToBack, orderDetails.add, orderDetails.tag) ;
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {}
	

	public String createDataFor_txt_billingAddress()
	{
		String string = MySharedPref.getInstance(orderDetails).getString("OrderDetails"+"BillingAddress"+"edt_firstName")+" "+
		MySharedPref.getInstance(orderDetails).getString("OrderDetails"+"BillingAddress"+"edt_lastName")+" "+
		MySharedPref.getInstance(orderDetails).getString("OrderDetails"+"BillingAddress"+"edt_street1")+" "+
		MySharedPref.getInstance(orderDetails).getString("OrderDetails"+"BillingAddress"+"edt_street2")+" "+
		MySharedPref.getInstance(orderDetails).getString("OrderDetails"+"BillingAddress"+"edt_city")+" "+
		MySharedPref.getInstance(orderDetails).getString("OrderDetails"+"BillingAddress"+"edt_postcode")+" "+
		MySharedPref.getInstance(orderDetails).getString("OrderDetails"+"BillingAddress"+"edt_telephone");
		return string ;
	}
	
	public String createDataFor_txt_shippingAddress()
	{
		String string = MySharedPref.getInstance(orderDetails).getString("OrderDetails"+"ShippingAddress"+"edt_firstName")+" "+
		MySharedPref.getInstance(orderDetails).getString("OrderDetails"+"ShippingAddress"+"edt_lastName")+" "+
		MySharedPref.getInstance(orderDetails).getString("OrderDetails"+"ShippingAddress"+"edt_street1")+" "+
		MySharedPref.getInstance(orderDetails).getString("OrderDetails"+"ShippingAddress"+"edt_street2")+" "+
		MySharedPref.getInstance(orderDetails).getString("OrderDetails"+"ShippingAddress"+"edt_city")+" "+
		MySharedPref.getInstance(orderDetails).getString("OrderDetails"+"ShippingAddress"+"edt_postcode")+" "+
		MySharedPref.getInstance(orderDetails).getString("OrderDetails"+"ShippingAddress"+"edt_telephone");
		return string ;
	}
	public String createDataFor_txt_shippingMethod()
	{
		String string = MySharedPref.getInstance(orderDetails).getString("OrderDetails"+"ShippingMethod"+"shippingselection") ;
		String newString = null ;
		if (string.equals("1"))
		{
			newString = "Credit Card / Bank Payment" ;
		}
		else if (string.equals("2"))
		{
			newString = "Cash on Delivery" ;
		}
		return newString ;
	}
	public String createDataFor_txt_paymentInfo()
	{
		String string = MySharedPref.getInstance(orderDetails).getString("OrderDetails"+"PaymentInfo"+"paymentselection") ;
		String newString = null ;
		if (string.equals("1"))
		{
			newString = "Credit Card / Bank Payment" ;
		}
		else if (string.equals("2"))
		{
			newString = "Cash on Delivery" ;
		}
		else if (string.equals("3"))
		{
			newString = "Credit Card Payment" ;
		}
		return newString ;
	}
	public String createDataFor_txt_deliveryInfo()
	{
		String string = MySharedPref.getInstance(orderDetails).getString("OrderDetails"+"DeliveryInfo"+"Message")+" "+
		MySharedPref.getInstance(orderDetails).getString("OrderDetails"+"DeliveryInfo"+"Date");
		return string ;
	}
}

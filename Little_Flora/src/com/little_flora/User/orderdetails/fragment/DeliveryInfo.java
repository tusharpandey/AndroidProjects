package com.little_flora.User.orderdetails.fragment;

import java.util.Calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.little_flora.R;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.User.OrderDetails;

public class DeliveryInfo extends BaseFragment implements OnClickListener,
		OnItemClickListener {

	private OrderDetails orderDetails;
	private Button btn_back;
	private Button btn_addNewAddress;
	private EditText edt_message;
	private DatePicker dpResult;
	private StringBuilder sb;

	public DeliveryInfo() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_deliveryinfo,
				container, false);

		orderDetails = (OrderDetails) getActivity();

		initView(rootView);

		return rootView;
	}

	private void initView(View v) {

		btn_addNewAddress = (Button) v.findViewById(R.id.btn_addNewAddress);
		btn_addNewAddress.setOnClickListener(this);

		orderDetails.txt_orderdetailtitle.setText("Delivery Info");
		orderDetails.pgb_orderdetailprogress.setProgress(80);

		btn_back = (Button) v.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);

		edt_message = (EditText) v.findViewById(R.id.edt_message);

		String value = MySharedPref.getInstance(orderDetails).getString("OrderDetails"+"DeliveryInfo"+"Message");
		
		if ( value != null && !value.equals("") )
		{
			edt_message.setText(value);
		}
		
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		sb = new StringBuilder()
		// Month is 0 based, just add 1
		.append(month + 1).append("-").append(day).append("-")
		.append(year).append(" ") ;
		
		dpResult = (DatePicker) v.findViewById(R.id.dpResult);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_addNewAddress:
			String message = edt_message.getText().toString().trim() ;

			MySharedPref.getInstance(orderDetails).setString("OrderDetails"+"DeliveryInfo"+"Message", ""+message);
			MySharedPref.getInstance(orderDetails).setString("OrderDetails"+"DeliveryInfo"+"Date", ""+sb.toString());

			orderDetails.switchContent(new OrderInfo(), orderDetails.addToBack,
					orderDetails.add, orderDetails.tag);

			break;
		case R.id.btn_back:
			orderDetails.switchContent(new PaymentInfo(),
					orderDetails.addToBack, orderDetails.add, orderDetails.tag);
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
}

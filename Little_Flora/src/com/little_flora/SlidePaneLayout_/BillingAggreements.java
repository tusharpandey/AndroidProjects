
package com.little_flora.SlidePaneLayout_;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.little_flora.R;
import com.little_flora.User.orderdetails.fragment.BaseFragment;

public class BillingAggreements extends BaseFragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_account_dashboard, container,
				false);
		myAccount = (MyAccount) getActivity();
		initView();

		return rootView;
	}

	private MyAccount myAccount;

	public void initView() {
		myAccount.txt_accountTitle.setText("Billing Aggreements");
	}
}

package com.little_flora.User.orderdetails.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.little_flora.R;
import com.little_flora.User.OrderDetails;

public class BaseFragment extends Fragment 
{
	public void showToast(String message,OrderDetails context)
	{
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
	
	public String getStringFromResources ( int id , OrderDetails context)
	{
		return context.getResources().getString(id);
	}
}

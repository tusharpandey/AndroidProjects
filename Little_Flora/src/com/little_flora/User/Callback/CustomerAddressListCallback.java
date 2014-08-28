package com.little_flora.User.Callback;

import java.util.ArrayList;
import java.util.Dictionary;

public interface CustomerAddressListCallback {
	public void customerAddressListCallbackResponse ( String message , String status , ArrayList<Dictionary<String, String>> listOfCustomerAddress ) ;
}

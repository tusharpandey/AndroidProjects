package com.little_flora.User.Callback;

import java.util.Dictionary;

public interface MyAccountCallback {
	public void responseOfMyAccount ( String message , String status , Dictionary<String, Dictionary<String, String>> accountData ) ;
}

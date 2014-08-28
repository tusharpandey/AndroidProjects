package com.little_flora.User.Callback;

import java.util.ArrayList;
import java.util.Dictionary;

public interface CartCallback {
	public void responseCartCallBack ( String status , String message , Dictionary<String, Dictionary<String, String>> cartDictionary ) ;
}

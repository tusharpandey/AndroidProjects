package com.little_flora.Activity.CallBacks;

import java.util.ArrayList;
import java.util.Dictionary;

public interface SearchCallback {
	public void searchResponse ( Dictionary<String, Dictionary<String, String>> searchDataDictionary , String message , String status);
}

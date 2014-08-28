package com.little_flora.Activity.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MySharedPref {

	public static final String KEY_SELECTION_FROM_VIEWCART = "selection_using_viewcart";

	private static final String APP_SHARED_PREFS = MySharedPref.class
			.getSimpleName(); // Name of the file -.xml
	private SharedPreferences _sharedPrefs;
	private Editor _prefsEditor;
	private static MySharedPref Instance;

	public static MySharedPref getInstance(Context context) {
		if (Instance == null)
			Instance = new MySharedPref(context);
		return Instance;
	}

	public MySharedPref(Context context) {

		this._sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS,
				Activity.MODE_PRIVATE); // shared preference name with a mode
		this._prefsEditor = _sharedPrefs.edit();
	}

	public String getString(String getDataString) {
		return _sharedPrefs.getString(getDataString, "");
	}

	public void setString(String setDataString , String type) {
		_prefsEditor.putString(setDataString, type);
		_prefsEditor.commit();
	}
	
	
}
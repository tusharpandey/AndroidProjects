package com.little_flora.Activity.Utils;

import android.util.Log;

public class Logs_ {
	
	String className ;
	
	public Logs_ ( String className )
	{
		this.className = className ;
	}
	
	public void showTempLog ( String msg )
	{
		Log.i(className, msg) ;
	}
	
	public void showImpLog ( String msg )
	{
		Log.e(className, msg) ;
	}
	
}

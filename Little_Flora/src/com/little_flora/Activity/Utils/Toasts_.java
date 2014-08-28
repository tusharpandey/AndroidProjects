package com.little_flora.Activity.Utils;

import android.content.Context;
import android.widget.Toast;

public class Toasts_ {
	
	Context context ;
	
	public Toasts_(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context ;
	}
	
	public void showTempToast (String msg)
	{
		Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT).show() ;
	}

	public void showImpToast (String msg)
	{
		Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG).show() ;
	}

}

package com.little_flora.Activity.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.TextView;

public class TypeFace_TextView {
	public static void setTypeFace (Context context,TextView txtView )
	{
		Typeface face = Typeface.createFromAsset(context.getAssets(),"Raavi.ttf");
		txtView.setTypeface(face) ;
	}
	public static void setTypeFace (Context context,Button btn )
	{
		Typeface face = Typeface.createFromAsset(context.getAssets(),"Raavi.ttf");
		btn.setTypeface(face) ;
	}
}

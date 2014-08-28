package com.little_flora.ViewTextView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class GauthamRoundedTextView extends TextView {

	private Typeface customTypeFace;
	Context context;

	public GauthamRoundedTextView(Context context) 
	{
		super(context);
		Typeface face = Typeface.createFromAsset(context.getAssets(),"GothamRoundedMedium_21022.ttf");
		this.setTypeface(face);
	}

	public GauthamRoundedTextView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		Typeface face = Typeface.createFromAsset(context.getAssets(),"GothamRoundedMedium_21022.ttf");
		this.setTypeface(face);
	}

	public GauthamRoundedTextView(Context context, AttributeSet attrs,int defStyle) {
		super(context, attrs, defStyle);
		Typeface face = Typeface.createFromAsset(context.getAssets(),"GothamRoundedMedium_21022.ttf");
		this.setTypeface(face);
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

	}
}

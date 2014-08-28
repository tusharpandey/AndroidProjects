package com.little_flora.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.little_flora.R;
import com.little_flora.Activity.Utils.TypeFace_TextView;
import com.little_flora.User.Registration;

public class DialogMessage extends Dialog implements
android.view.View.OnClickListener 
{
	Registration context ;
	String message ;
	private TextView txt_dialog_message;
	private Button dialog_ok;
	String fromWhere ;
	public DialogMessage(Registration context,String message) {
		
		super(context);
		this.message = message ;
		this.context = context ;
		// TODO Auto-generated constructor stub
	}
	
	public void initView()
	{
		txt_dialog_message =  (TextView) this.findViewById(R.id.txt_dialog_message) ;
		txt_dialog_message.setText(message);
		TypeFace_TextView.setTypeFace(context, txt_dialog_message);
		
		dialog_ok =  (Button ) this.findViewById(R.id.dialog_ok);
		dialog_ok.setOnClickListener(this) ;
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_message);
		
		initView();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.dialog_ok:
			this.dismiss() ;
			context.onBackPressed();
			break;

		default:
			break;
		}
	}
	
	
}

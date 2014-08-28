package com.little_flora.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.little_flora.R;
import com.little_flora.Activity.BaseActivity;
import com.little_flora.Activity.Utils.TypeFace_TextView;

public class DialogPolicy extends Dialog implements
android.view.View.OnClickListener{

	private TextView txt_sharing_sharewith;
	private TextView txt_sharing_sharewith_facebook;
	private EditText edt_newsletter_emailSection;
	private Button btn_newsletter_go;

	BaseActivity context ;

	
	public DialogPolicy(BaseActivity context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context ;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {


		default:
			break;
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_newsletter);
		

		initView();
	}
	
	public void initView()
	{
		txt_sharing_sharewith = ( TextView ) findViewById(R.id.txt_sharing_sharewith);
		TypeFace_TextView.setTypeFace(context, txt_sharing_sharewith);
		
		txt_sharing_sharewith_facebook = ( TextView ) findViewById(R.id.txt_sharing_sharewith_facebook);
		TypeFace_TextView.setTypeFace(context, txt_sharing_sharewith_facebook);

		edt_newsletter_emailSection = ( EditText ) findViewById(R.id.edt_newsletter_emailSection);

		btn_newsletter_go = ( Button ) findViewById(R.id.btn_newsletter_go);
		TypeFace_TextView.setTypeFace(context, btn_newsletter_go);

	}

	
}

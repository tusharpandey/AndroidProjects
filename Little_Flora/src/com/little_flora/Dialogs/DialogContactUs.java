package com.little_flora.Dialogs;

import java.io.File;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import com.little_flora.R;
import com.little_flora.Activity.BaseActivity;
import com.little_flora.Activity.ProductDescription;
import com.little_flora.Activity.Utils.TypeFace_TextView;

public class DialogContactUs extends Dialog implements
android.view.View.OnClickListener{

	private TextView txt_sharing_sharewith;
	private TextView txt_sharing_sharewith_facebook;
	private TextView txt_sharing_sharewith_twitter;
	private TextView txt_sharing_sharewith_instagram;
	private TextView txt_sharing_sharewith_pintrest;

	BaseActivity context ;

	
	public DialogContactUs(BaseActivity context) {
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
		setContentView(R.layout.dialog_contactus);
		

		initView();
	}
	
	public void initView()
	{
		txt_sharing_sharewith = ( TextView ) findViewById(R.id.txt_sharing_sharewith);
		TypeFace_TextView.setTypeFace(context, txt_sharing_sharewith);
		
		txt_sharing_sharewith_facebook = ( TextView ) findViewById(R.id.txt_sharing_sharewith_facebook);
		TypeFace_TextView.setTypeFace(context, txt_sharing_sharewith_facebook);

		txt_sharing_sharewith_twitter = ( TextView ) findViewById(R.id.txt_sharing_sharewith_twitter);
		TypeFace_TextView.setTypeFace(context, txt_sharing_sharewith_twitter);

		txt_sharing_sharewith_instagram = ( TextView ) findViewById(R.id.txt_sharing_sharewith_instagram);
		TypeFace_TextView.setTypeFace(context, txt_sharing_sharewith_instagram);

		txt_sharing_sharewith_pintrest = ( TextView ) findViewById(R.id.txt_sharing_sharewith_pintrest);
		TypeFace_TextView.setTypeFace(context, txt_sharing_sharewith_pintrest);
	}

	
}

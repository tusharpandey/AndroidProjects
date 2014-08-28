package com.little_flora.Dialogs;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.little_flora.R;
import com.little_flora.Activity.BaseActivity;
import com.little_flora.Activity.WebViewActivity;
import com.little_flora.Activity.Utils.Toasts_;
import com.little_flora.Activity.Utils.TypeFace_TextView;

public class DialogFollowUs extends Dialog implements android.view.View.OnClickListener{


	BaseActivity context ;

	private TextView txt_sharing_sharewith;
	private TextView txt_sharing_sharewith_facebook;
	private TextView txt_sharing_sharewith_twitter;
	private TextView txt_sharing_sharewith_instagram;
	private TextView txt_sharing_sharewith_pinterest;
	private TextView txt_sharing_sharewith_linkedin ;
	private TextView txt_sharing_sharewith_youtube ;

	private Toasts_ mToast;
	
	public DialogFollowUs(BaseActivity context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context ;
		
		mToast = new Toasts_(context) ;
	}


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_followus);
		
		initView();
	}
	
	
	public void initView ()
	{
		txt_sharing_sharewith = ( TextView ) this.findViewById(R.id.txt_sharing_sharewith);
		TypeFace_TextView.setTypeFace(context, txt_sharing_sharewith);

		txt_sharing_sharewith_facebook = ( TextView ) this.findViewById(R.id.txt_sharing_sharewith_facebook);
		TypeFace_TextView.setTypeFace(context, txt_sharing_sharewith_facebook);
		txt_sharing_sharewith_facebook.setOnClickListener(this);

		txt_sharing_sharewith_twitter = ( TextView ) this.findViewById(R.id.txt_sharing_sharewith_twitter);
		TypeFace_TextView.setTypeFace(context, txt_sharing_sharewith_twitter);
		txt_sharing_sharewith_twitter.setOnClickListener(this);

		txt_sharing_sharewith_instagram = ( TextView ) this.findViewById(R.id.txt_sharing_sharewith_instagram);
		TypeFace_TextView.setTypeFace(context, txt_sharing_sharewith_instagram);
		txt_sharing_sharewith_instagram.setOnClickListener(this);

		txt_sharing_sharewith_pinterest = ( TextView ) this.findViewById(R.id.txt_sharing_sharewith_pinterest);
		TypeFace_TextView.setTypeFace(context, txt_sharing_sharewith_pinterest);
		txt_sharing_sharewith_pinterest.setOnClickListener(this);

		txt_sharing_sharewith_linkedin  = ( TextView ) this.findViewById(R.id.txt_sharing_sharewith_linkedin);
		TypeFace_TextView.setTypeFace(context, txt_sharing_sharewith_linkedin);
		txt_sharing_sharewith_linkedin.setOnClickListener(this);

		txt_sharing_sharewith_youtube  = ( TextView ) this.findViewById(R.id.txt_sharing_sharewith_youtube);
		TypeFace_TextView.setTypeFace(context, txt_sharing_sharewith_youtube);
		txt_sharing_sharewith_youtube.setOnClickListener(this);
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_sharing_sharewith_facebook:
			
			mToast.showImpToast("Please wait ...") ;
			
			Intent i1 = new Intent (context,WebViewActivity.class);
			i1.putExtra("URL", "https://www.facebook.com/LittleFloraSA");
			context.startActivity(i1);
			break;
		case R.id.txt_sharing_sharewith_twitter:
			mToast.showImpToast("Please wait ...") ;
			Intent i2 = new Intent (context,WebViewActivity.class);
			i2.putExtra("URL", "https://twitter.com/LittleFloraSA");
			context.startActivity(i2);
			break;
		case R.id.txt_sharing_sharewith_instagram:
			mToast.showImpToast("Please wait ...") ;
			Intent i3 = new Intent (context,WebViewActivity.class);
			i3.putExtra("URL", "https://instagram.com/littleflora_");
			context.startActivity(i3);
			break;
		case R.id.txt_sharing_sharewith_pintrest:
			mToast.showImpToast("Please wait ...") ;
			Intent i4 = new Intent (context,WebViewActivity.class);
			i4.putExtra("URL", "https://www.pinterest.com/LittleFloraSA/");
			context.startActivity(i4);
			break;
		case R.id.txt_sharing_sharewith_linkedin:
			mToast.showImpToast("Please wait ...") ;
			Intent i5 = new Intent (context,WebViewActivity.class);
			i5.putExtra("URL", "https://www.linkedin.com/pub/little-flora/88/aaa/302");
			context.startActivity(i5);
			break;
		case R.id.txt_sharing_sharewith_youtube:
			mToast.showImpToast("Please wait ...") ;
			Intent i6 = new Intent (context,WebViewActivity.class);
			i6.putExtra("URL", "https://www.youtube.com/channel/UCv8aaa12GXBu4EidpeefQjw/");
			context.startActivity(i6);
			break;

		default:
			break;
		}
	}
}


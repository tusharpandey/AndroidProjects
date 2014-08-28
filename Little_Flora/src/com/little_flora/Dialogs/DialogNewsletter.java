
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
import com.little_flora.Activity.Utils.CheckEmail;
import com.little_flora.Activity.Utils.CheckNetwork;
import com.little_flora.Activity.Utils.HTTP_POST_urls;
import com.little_flora.Activity.Utils.Toasts_;
import com.little_flora.Activity.Utils.TypeFace_TextView;
import com.little_flora.User.Async.SignUpForNewsLetterAsync;
import com.little_flora.User.Callback.NewsLetterSubscribedCallback;

public class DialogNewsletter extends Dialog implements
android.view.View.OnClickListener {

	private TextView txt_sharing_sharewith;
	private TextView txt_sharing_sharewith_facebook;
	private EditText edt_newsletter_emailSection;
	private Button btn_newsletter_go;

	BaseActivity context ;
	private Toasts_ mToast;

	public DialogNewsletter(BaseActivity context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context ;
		
		mToast = new Toasts_(context) ;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
	
		case R.id.btn_newsletter_go:
			
			if ( CheckNetwork.isNetworkAvailable(context) )
			{
				if ( CheckEmail.isEmailValid(edt_newsletter_emailSection.getText().toString().trim()))
				{
					String url = HTTP_POST_urls.signUpForNewsLetter+edt_newsletter_emailSection.getText().toString().trim() ;
					String loadingText = context.getResources().getString(R.string.loading_SigningupForTheNewsLetter);
					String data [] = new String [1] ;
					
					SignUpForNewsLetterAsync signUpForNewsLetter = new SignUpForNewsLetterAsync(url, data, context, loadingText,this);
					signUpForNewsLetter.execute("");
				}
				else
				{
					mToast.showImpToast(context.getResources().getString(R.string.Login_invalidEmail));
				}
			}
			else
			{
				mToast.showImpToast(context.getResources().getString(R.string.networkNotPresent));
			}
			
			break ;

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
		btn_newsletter_go.setOnClickListener(this) ;
	}
}

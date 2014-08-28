package com.little_flora.User;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.little_flora.R;
import com.little_flora.Activity.BaseActivity;
import com.little_flora.Activity.Utils.CheckEmail;
import com.little_flora.Activity.Utils.CheckNetwork;
import com.little_flora.Activity.Utils.HTTP_POST_urls;
import com.little_flora.Activity.Utils.LF_Constants.Con_SpalshScreen;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.Activity.Utils.Toasts_;
import com.little_flora.Activity.Utils.TypeFace_TextView;
import com.little_flora.Dialogs.DialogMessage;
import com.little_flora.User.Async.RegistrationAsync;
import com.little_flora.User.Callback.RegistrationCallback;

public class Registration extends BaseActivity implements OnClickListener , RegistrationCallback
{
	private EditText edt_emailAddress;
	private EditText edt_firstName;
	private EditText edt_lastName;
	private EditText edt_password;
	private EditText edt_confPassword;
	private EditText edt_telephone;
	
	private Button btn_Register;
	private Toasts_ mToasts;
	private Logs_ mLogs;
	private ArrayList<String> navigationDrawerList;


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_registration);
		

		
		mToasts = new Toasts_(this);
		mLogs = new Logs_("Registration") ;
		
		initView();
		
        navigationDrawerList = getIntent().getStringArrayListExtra("navigationDrawerList");
        
        listToSlideDrawerNavigation = navigationDrawerList ;
        
        String [] navigationArray = navigationDrawerList.toArray(new String[navigationDrawerList.size()]);
        
		navigationDrawerSetup(navigationArray);

	}
	
	public void initView()
	{
		edt_emailAddress = ( EditText ) findViewById(R.id.edt_emailAddress);
		edt_firstName = ( EditText ) findViewById(R.id.edt_firstName);
		edt_lastName = ( EditText ) findViewById(R.id.edt_lastName);
		edt_password = ( EditText ) findViewById(R.id.edt_password);
		edt_confPassword = ( EditText ) findViewById(R.id.edt_confPassword);
		edt_telephone = ( EditText ) findViewById(R.id.edt_telephone);
//edt_telephone		
		btn_Register = ( Button ) findViewById(R.id.btn_Register);
		btn_Register.setOnClickListener(this) ;
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MySharedPref.getInstance(this).setString("MyCurrentActivityOnPage","Registration") ;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_Register :
			if ( CheckNetwork.isNetworkAvailable(this) )
			{
				if ( isValidInput() )
				{
					String data [] = new String[1] ;
		
					 		
					String store_id = MySharedPref.getInstance(this).getString(Con_SpalshScreen.GlobalStored_StoreId);
					String website_id = MySharedPref.getInstance(this).getString(Con_SpalshScreen.GlobalStored_WebsiteId);
					String group_id = MySharedPref.getInstance(this).getString(Con_SpalshScreen.GlobalStored_GroupId);		

					String userLogin = HTTP_POST_urls.userRegistration 
											+ "email="+edt_emailAddress.getText().toString().trim()
											+"&firstname="+edt_firstName.getText().toString().trim()
											+"&lastname="+edt_lastName.getText().toString().trim()
											+"&password="+edt_password.getText().toString().trim()
											+"&website_id="+website_id.trim()
											+"&store_id="+store_id.trim()
											+"&group_id="+group_id.trim()
										;
				
					RegistrationAsync asyncObject = new RegistrationAsync(userLogin,data, this,getResources().getString(R.string.loading_createAccount));
					asyncObject.execute("");
				}
			}
			else
			{
				mToasts.showImpToast(getResources().getString(R.string.networkNotPresent)) ;
			}
			break;

		default:
			break;
		}
	}
	
	public boolean isValidInput() {
		if (edt_firstName.getText().toString().trim().length() <= 0 ) {
			mToasts.showImpToast(getResources().getString(R.string.Registration_invalidFirstName));
			edt_firstName.requestFocus();
			return false;
		} 
		else if (edt_lastName.getText().toString().trim().length() <= 0 ) {
			mToasts.showImpToast(getResources().getString(R.string.Registration_invalidLastName));
			edt_lastName.requestFocus();
			return false;
		} 
		else if (!CheckEmail.isEmailValid(edt_emailAddress.getText().toString().trim())) {
			mToasts.showImpToast(getResources().getString(R.string.Registration_invalidEmail));
			edt_emailAddress.requestFocus();
			return false;
		} 
		else if (edt_password.getText().toString().trim().length() <= 7
				|| edt_confPassword.getText().toString().trim().length() <= 7) {
			mToasts.showImpToast(getResources().getString(R.string.Registration_invalidPasswordLength));
			edt_password.requestFocus();
			
			edt_password.setText("");
			edt_confPassword.setText("");
			
			return false;
		}
		else if (!edt_password.getText().toString().trim().equals(edt_confPassword.getText().toString().trim())) 
		{
			mToasts.showImpToast(getResources().getString(R.string.Registration_invalidPassword));
			edt_password.requestFocus();
			
			edt_password.setText("");
			edt_confPassword.setText("");
			
			return false;
		}
		else if (edt_telephone.getText().toString().trim().length() < 10 && edt_telephone.getText().toString().trim().length() > 10) {
			mToasts.showImpToast(getResources().getString(R.string.Registration_invalidMobileNumber));
			edt_telephone.requestFocus();
			return false;
		} 
		return true;
	}	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	@Override
	public void registrationResponse(String customerId , String message , String status) {
		// TODO Auto-generated method stub
		
		mLogs.showTempLog(message);
		mLogs.showTempLog(status);
		
		if ( message != null && status != null )
		{
			DialogMessage dialogMessage = new DialogMessage(this,message);
			dialogMessage.setCancelable(false);
			dialogMessage.show() ;
		}
		else
		{
			mToasts.showImpToast(getResources().getString(R.string.errorInResponse)) ;
		}
	}
}

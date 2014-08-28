package com.little_flora.User;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.little_flora.R;
import com.little_flora.Activity.BaseActivity;
import com.little_flora.Activity.Utils.CheckEmail;
import com.little_flora.Activity.Utils.CheckNetwork;
import com.little_flora.Activity.Utils.HTTP_POST_urls;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.Activity.Utils.Toasts_;
import com.little_flora.Activity.Utils.LF_Constants.Con_UserDetails;
import com.little_flora.User.Async.ProfileSync;
import com.little_flora.User.Async.UpdateProfileSync;
import com.little_flora.User.Callback.ProfileCallback;
/*
 * Profile update is not working with : password update .
 * 
 */


public class Profile extends BaseActivity implements ProfileCallback , OnClickListener{
	private ArrayList<String> navigationDrawerList;
	private Toasts_ mToast;

	TextView txt_useremail;
	TextView txt_useremail_field;
	EditText edt_useremail_field;
	TextView txt_userfirstname;
	TextView txt_userfirstname_field;
	EditText edt_userfirstname_field;
	TextView txt_userlastname;
	TextView txt_userlastname_field;
	EditText edt_userlastname_field;
	CheckBox chk_password;
	LinearLayout lay_assword;
	TextView txt_userpassword;
	EditText edt_userpassword_field;
	LinearLayout lay_confPassword;
	TextView txt_userconfpassword;
	EditText edt_userConfpassword_field;
	Button btn_updateProfile;
	private CheckBox chk_updateProfile;
	private Logs_ mLogs;
	private String consumedEmailString;
	private String consumedFirstNameString;
	private String consumedLastNameString;


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userprofile);
		


		mToast = new Toasts_(this);
		mLogs = new Logs_("Profile");

		navigationDrawerList = getIntent().getStringArrayListExtra(
				"navigationDrawerList");
		listToSlideDrawerNavigation = navigationDrawerList;
		String[] navigationArray = navigationDrawerList
				.toArray(new String[navigationDrawerList.size()]);
		navigationDrawerSetup(navigationArray);

		String userId = getIntent().getStringExtra("userId");

		String userProfile = HTTP_POST_urls.userProfile + "customer_id="
				+ userId;

		if (CheckNetwork.isNetworkAvailable(this)) {
			String data[] = new String[1];

			ProfileSync asyncObject = new ProfileSync(userProfile, data, this,
					getResources().getString(R.string.loading_profileData));
			asyncObject.execute("");
		} else {
			mToast.showImpToast(getResources().getString(
					R.string.networkNotPresent));
		}
		
		initView_Profile();
		initView_Profile_txt();
		initView_updateProfile();
	}
	
	public void initView_updateProfile()
	{
		txt_userpassword = ( TextView ) findViewById(R.id.txt_userpassword);
		edt_userpassword_field = ( EditText ) findViewById(R.id.edt_userpassword_field);
		txt_userconfpassword = ( TextView ) findViewById(R.id.txt_userconfpassword);
		edt_userConfpassword_field = ( EditText ) findViewById(R.id.edt_userConfpassword_field);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MySharedPref.getInstance(this).setString("MyCurrentActivityOnPage","Profile") ;
	}

	public void init_passwordLayouts()
	{
		lay_assword  = ( LinearLayout ) findViewById(R.id.lay_assword);
		lay_assword.setVisibility(View.VISIBLE);
		
		lay_confPassword = ( LinearLayout ) findViewById(R.id.lay_confPassword);
		lay_confPassword.setVisibility(View.VISIBLE);
	}
	
	public void initView_Profile()
	{
		txt_useremail = ( TextView ) findViewById(R.id.txt_useremail);
		txt_userfirstname = ( TextView ) findViewById(R.id.txt_userfirstname);
		txt_userlastname = ( TextView ) findViewById(R.id.txt_userlastname);
		
		chk_password = ( CheckBox ) findViewById(R.id.chk_password);
		chk_password.setOnClickListener(this);
		
		chk_updateProfile = ( CheckBox ) findViewById(R.id.chk_updateProfile);
		chk_updateProfile.setOnClickListener(this);		
	}
	
	public void init_UpdateProfileButton()
	{
		btn_updateProfile = ( Button ) findViewById(R.id.btn_updateProfile) ;
		btn_updateProfile.setOnClickListener(this) ;
	}
	
	public void initView_Profile_txt()
	{
		txt_useremail_field = ( TextView ) findViewById(R.id.txt_useremail_field);
		txt_useremail_field.setVisibility(View.VISIBLE);
		txt_userlastname_field = ( TextView ) findViewById(R.id.txt_userlastname_field);
		txt_userlastname_field.setVisibility(View.VISIBLE);
		txt_userfirstname_field = ( TextView ) findViewById(R.id.txt_userfirstname_field);
		txt_userfirstname_field.setVisibility(View.VISIBLE);
		
		txt_useremail_field.setText(consumedEmailString);
		txt_userlastname_field.setText(consumedLastNameString);
		txt_userfirstname_field.setText(consumedFirstNameString);	
	}
	
	public void initView_Profile_txtHide()
	{
		txt_useremail_field.setVisibility(View.GONE);
		txt_userlastname_field.setVisibility(View.GONE);
		txt_userfirstname_field.setVisibility(View.GONE);
	}


	public void initView_Profile_edt()
	{
		edt_useremail_field = ( EditText ) findViewById(R.id.edt_useremail_field);
		edt_useremail_field.setVisibility(View.VISIBLE);
		edt_userfirstname_field = ( EditText ) findViewById(R.id.edt_userfirstname_field);
		edt_userfirstname_field.setVisibility(View.VISIBLE);
		edt_userlastname_field = ( EditText ) findViewById(R.id.edt_userlastname_field);
		edt_userlastname_field.setVisibility(View.VISIBLE);
		
		edt_useremail_field.setText(consumedEmailString);
		edt_userfirstname_field.setText(consumedFirstNameString);
		edt_userlastname_field.setText(consumedLastNameString);
	}
	
	public void initView_Profile_edtHide()
	{
		edt_useremail_field.setVisibility(View.GONE);
		edt_userfirstname_field.setVisibility(View.GONE);
		edt_userlastname_field.setVisibility(View.GONE);
	}
	

	@Override
	public void profileCallback(String message) {
		// TODO Auto-generated method stub
		if ( message.contains("Success") )
		{
			consumedEmailString = MySharedPref.getInstance(this).getString("UserProfile_"+"email");
			consumedFirstNameString = MySharedPref.getInstance(this).getString("UserProfile_"+"firstname");
			consumedLastNameString = MySharedPref.getInstance(this).getString("UserProfile_"+"lastname");
			
			if ( txt_useremail_field != null )
			{
				txt_useremail_field.setText(consumedEmailString);
				txt_userlastname_field.setText(consumedLastNameString);
				txt_userfirstname_field.setText(consumedFirstNameString);			
			}
			else if ( edt_useremail_field != null )
			{
				edt_useremail_field.setText(consumedEmailString);
				edt_userfirstname_field.setText(consumedFirstNameString);
				edt_userlastname_field.setText(consumedLastNameString);
			}
		}
		else
		{
			mToast.showImpToast(getResources().getString(
					R.string.errorInResponse));
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.chk_password:
			if ( chk_password.isChecked() )
			{
				init_passwordLayouts();

				if (  btn_updateProfile == null )
				{
					init_UpdateProfileButton() ;
					btn_updateProfile.setVisibility(View.VISIBLE);
				}
			}
			else
			{
				if ( lay_assword != null || lay_confPassword != null )
				{
					lay_assword.setVisibility(View.GONE);
					lay_confPassword.setVisibility(View.GONE);
				}
				if ( chk_updateProfile != null && !chk_updateProfile.isChecked() )
				{
					btn_updateProfile.setVisibility(View.GONE);
					btn_updateProfile = null ;
				}
			}
			
			
			break;
		case R.id.chk_updateProfile:
			if ( chk_updateProfile.isChecked() )
			{
				initView_Profile_edt();
				if ( txt_useremail_field !=null )
				{
					initView_Profile_txtHide();
				}
				if (  btn_updateProfile == null )
				{
					init_UpdateProfileButton() ;
					btn_updateProfile.setVisibility(View.VISIBLE);
				}
			}
			else
			{
				initView_Profile_txt();
				if ( edt_useremail_field !=null )
				{
					initView_Profile_edtHide();
				}
				
				if ( chk_password != null && !chk_password.isChecked() )
				{
					btn_updateProfile.setVisibility(View.GONE);
					btn_updateProfile = null ;
				}
			}
			break;
		case R.id.btn_updateProfile:
			
			String customerId = null ;
			String firstname = null ;
			String lastname = null ;
			String email = null ;
			String password = null ;
			
			if ( btn_updateProfile != null )
			{
				if (CheckNetwork.isNetworkAvailable(this)) 
				{
					if ( isValidInputs() )
					{
						customerId = getIntent().getStringExtra("userId"); ;
						
						if ( edt_useremail_field != null )
						{
							email = edt_useremail_field.getText().toString().trim();
							firstname = edt_userfirstname_field.getText().toString().trim();
							lastname = edt_userlastname_field.getText().toString().trim();
						}
						else
						{
							email = txt_useremail_field.getText().toString().trim();
							firstname = txt_userfirstname_field.getText().toString().trim();
							lastname = txt_userlastname_field.getText().toString().trim();
						}
						
						if ( lay_assword != null )
						{
							password = edt_userpassword_field.getText().toString().trim() ;
						}
						else
						{
							password = MySharedPref.getInstance(this).getString(Con_UserDetails.userPassword) ;
						}
						
						String url = HTTP_POST_urls.userProfileUpdate + 
								"&customerId="+customerId+
								"&firstname="+firstname+
								"&lastname="+lastname+
								"&email="+email+
								"&password="+password;
						
						String data [] = new String [1] ;
						String loadingText = getResources().getString(R.string.loading_updatingProfileData);
						UpdateProfileSync updateProfileSync = new UpdateProfileSync(url, data, this, loadingText) ;
						updateProfileSync.execute("");
					}
				} 
				else {
					mToast.showImpToast(getResources().getString(
							R.string.networkNotPresent));
				}

			}
			
			break;
		default:
			break;
		}
	}
	
	public boolean isValidInputs ()
	{
		if ( edt_useremail_field != null )
		{
			if (!CheckEmail.isEmailValid(edt_useremail_field.getText().toString().trim())) {
				mToast.showImpToast(getResources().getString(R.string.Login_invalidEmail));
				edt_useremail_field.requestFocus();
				return false;
			}
			else if ( edt_userfirstname_field.getText().length() <= 0  )
			{
				mToast.showImpToast(getResources().getString(R.string.Registration_invalidFirstName));
				edt_userfirstname_field.requestFocus();
				return false;
			}
			else if ( edt_userlastname_field.getText().length() <= 0  )
			{
				mToast.showImpToast(getResources().getString(R.string.Registration_invalidLastName));
				edt_userlastname_field.requestFocus();
				return false;
			}
		}
		if ( lay_assword != null )
		{
			if ( edt_userConfpassword_field.getText().length() < 8 ||  edt_userpassword_field.getText().length() < 8 )
			{
				mToast.showImpToast(getResources().getString(R.string.Registration_invalidPasswordLength));
				edt_userConfpassword_field.requestFocus();
				
				edt_userConfpassword_field.setText("");
				edt_userpassword_field.setText("");	
				return false;
			}
			else if ( ! edt_userConfpassword_field.getText().toString().equals(edt_userpassword_field.getText().toString()) )
			{
				mToast.showImpToast(getResources().getString(R.string.Registration_invalidPassword));
				edt_userConfpassword_field.requestFocus();
				
				edt_userConfpassword_field.setText("");
				edt_userpassword_field.setText("");	
				return false;
			}
		}
		return true ;
	}
}

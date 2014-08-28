package com.little_flora.SlidePaneLayout_;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.little_flora.R;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.Activity.Utils.LF_Constants.Con_UserDetails;
import com.little_flora.User.orderdetails.fragment.BaseFragment;

public class AccountInformation extends BaseFragment implements OnClickListener {

	View rootView;
	String savedPassword ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_accountinformation,
				container, false);

		this.rootView = rootView;

		myAccount = (MyAccount) getActivity();
		
		savedPassword = MySharedPref.getInstance(myAccount).getString(Con_UserDetails.userPassword) ;

		initView();

		return rootView;
	}

	private MyAccount myAccount;
	private CheckBox chk_changePassword;
	private LinearLayout lay_passwordLayout;
	private EditText edt_changePassword;

	public void initView() {
		myAccount.txt_accountTitle.setText("Account Information");

		chk_changePassword = (CheckBox) rootView
				.findViewById(R.id.chk_changePassword);
		chk_changePassword.setOnClickListener(this);

		if (lay_passwordLayout != null)
			lay_passwordLayout = (LinearLayout) rootView
					.findViewById(R.id.lay_passwordLayout);

		edt_changePassword = (EditText) rootView
				.findViewById(R.id.edt_changePassword);

		edt_changePassword.addTextChangedListener(new TextWatcher() {

			private ImageView img_correct;
			private LinearLayout layout_addNewPassword;

			public void afterTextChanged(Editable s) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				if ( layout_addNewPassword == null )
					layout_addNewPassword = ( LinearLayout ) rootView.findViewById(R.id.layout_addNewPassword) ;

				String newPassword = edt_changePassword.getText().toString().trim() ;
				Bitmap image ;
				
				if ( img_correct == null )
					img_correct = ( ImageView ) rootView.findViewById(R.id.img_correct) ;

				if ( savedPassword.equals(newPassword) )
				{	
					image = BitmapFactory.decodeResource(getResources(), R.drawable.correct);
					
					layout_addNewPassword.setVisibility(View.VISIBLE);
				}
				else
				{	
					image = BitmapFactory.decodeResource(getResources(), R.drawable.wrong);

					layout_addNewPassword.setVisibility(View.INVISIBLE);
				}

				img_correct.setVisibility(View.VISIBLE) ;
				img_correct.setImageBitmap(image);
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.chk_changePassword:
			if (chk_changePassword.isChecked()) {
				if (lay_passwordLayout != null)
					lay_passwordLayout.setVisibility(View.VISIBLE);
				else {
					lay_passwordLayout = (LinearLayout) rootView
							.findViewById(R.id.lay_passwordLayout);
					lay_passwordLayout.setVisibility(View.VISIBLE);
				}

			} else if (!chk_changePassword.isChecked()) {
				if (lay_passwordLayout != null)
					lay_passwordLayout.setVisibility(View.INVISIBLE);
				else {
					lay_passwordLayout = (LinearLayout) rootView
							.findViewById(R.id.lay_passwordLayout);
					lay_passwordLayout.setVisibility(View.INVISIBLE);
				}
			}
			break;

		default:
			break;
		}
	}

}

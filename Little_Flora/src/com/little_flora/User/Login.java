package com.little_flora.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.OpenRequest;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.little_flora.R;
import com.little_flora.Activity.BaseActivity;
import com.little_flora.Activity.MainCategory;
import com.little_flora.Activity.Utils.CheckEmail;
import com.little_flora.Activity.Utils.CheckNetwork;
import com.little_flora.Activity.Utils.HTTP_POST_urls;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_AddToCart;
import com.little_flora.Activity.Utils.LF_Constants.Con_SpalshScreen;
import com.little_flora.Activity.Utils.LF_Constants.Con_UserDetails;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.Activity.Utils.Toasts_;
import com.little_flora.User.Async.LoginAsync;
import com.little_flora.User.Callback.LoginCallback;

public class Login extends BaseActivity implements OnClickListener,
		LoginCallback {
	private TextView txt_pageTitle;
	private EditText edt_emailAddress;
	private EditText edt_password;
	private Button btn_login;
	private TextView txt_forgotpassword;
	private TextView txt_newHereRegisterNow;
	private Button btn_loginWithFacebook;
	private Button btn_loginWithTwitter;
	private Toasts_ mToast;
	private Logs_ mLogs;
	private ArrayList<String> navigationDrawerList;
	private TextView txt_skipDown;
	private UiLifecycleHelper uiHelper;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);
		

		mToast = new Toasts_(this);
		mLogs = new Logs_("Login");

		initView();

		navigationDrawerList = getIntent().getStringArrayListExtra(
				"navigationDrawerList");

		listToSlideDrawerNavigation = navigationDrawerList;

		String[] navigationArray = navigationDrawerList
				.toArray(new String[navigationDrawerList.size()]);

		navigationDrawerSetup(navigationArray);

		// Add code to print out the key hash
		// try
		// {
		// PackageInfo info = getPackageManager().getPackageInfo(
		// "com.messageapp", PackageManager.GET_SIGNATURES);
		// for (Signature signature : info.signatures) {
		// MessageDigest md = MessageDigest.getInstance("SHA");
		// md.update(signature.toByteArray());
		// Log.e("KeyHash:",
		// Base64.encodeToString(md.digest(), Base64.DEFAULT));
		// }
		// } catch (NameNotFoundException e) {
		//
		// } catch (NoSuchAlgorithmException e) {
		//
		// }

		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);

		final LinearLayout layout_linearLayout = (LinearLayout) findViewById(R.id.layout_linearLayout);
		layout_linearLayout.post(new Runnable() {
			public void run() {
				int height = layout_linearLayout.getHeight();
				Log.e("Height", height+"");
				MySharedPref.getInstance(Login.this).setString(
						"sendScreenHeight_SPLASHSCREEN", height + "");
			}
		});

	}

	public void initView() {
		edt_emailAddress = (EditText) findViewById(R.id.edt_emailAddress);
		edt_password = (EditText) findViewById(R.id.edt_password);

		btn_login = (Button) findViewById(R.id.btn_login);
		btn_login.setOnClickListener(this);

		txt_forgotpassword = (TextView) findViewById(R.id.txt_forgotpassword);
		txt_forgotpassword.setOnClickListener(this);

		txt_newHereRegisterNow = (TextView) findViewById(R.id.txt_newHereRegisterNow);
		txt_newHereRegisterNow.setOnClickListener(this);

		btn_loginWithFacebook = (Button) findViewById(R.id.btn_loginWithFacebook);
		btn_loginWithFacebook.setOnClickListener(this);

		btn_loginWithTwitter = (Button) findViewById(R.id.btn_loginWithTwitter);
		btn_loginWithTwitter.setOnClickListener(this);

		String language = MySharedPref.getInstance(this).getString(
				Con_SpalshScreen.GlobalStoredLanguageForApp);

		if (language.equals("English")) {
			edt_emailAddress.setGravity(Gravity.LEFT);
			edt_password.setGravity(Gravity.LEFT);
			txt_forgotpassword.setGravity(Gravity.LEFT);
		} else {
			edt_emailAddress.setGravity(Gravity.RIGHT);
			edt_password.setGravity(Gravity.RIGHT);
			txt_forgotpassword.setGravity(Gravity.RIGHT);
		}

		txt_skipDown = (TextView) findViewById(R.id.txt_skipDown);
		txt_skipDown.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_login:

			if (CheckNetwork.isNetworkAvailable(this)) {
				if (isValidInput()) {
					String data[] = new String[1];

					String userLogin = HTTP_POST_urls.userLogin
							+ "email="
							+ edt_emailAddress.getText().toString().trim()
							+ "&password="
							+ edt_password.getText().toString().trim()
							+ "&websiteid="
							+ MySharedPref.getInstance(this).getString(
									Con_SpalshScreen.GlobalStored_WebsiteId);

					LoginAsync asyncObject = new LoginAsync(userLogin, data,
							this, getResources().getString(
									R.string.loading_loging));
					asyncObject.execute("");
				}
			} else {
				mToast.showImpToast(getResources().getString(
						R.string.networkNotPresent));
			}

			break;

		case R.id.txt_newHereRegisterNow:
			Intent intent = new Intent(this, Registration.class);
			intent.putStringArrayListExtra("navigationDrawerList",
					navigationDrawerList);
			startActivity(intent);
			break;

		case R.id.txt_skipDown:

			Intent intent1 = new Intent(this, MainCategory.class);
			intent1.putStringArrayListExtra("navigationDrawerList",
					navigationDrawerList);
			startActivity(intent1);

			InputMethodManager inputManager = (InputMethodManager) this
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(this.getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

			break;
		case R.id.btn_loginWithFacebook:
			if (CheckNetwork.isNetworkAvailable(this)) {
				facebookButtonClicked();
			} else {
				mToast.showImpToast(getResources().getString(
						R.string.networkNotPresent));
			}

			break;
		default:
			break;
		}
	}

	public boolean isValidInput() {

		if (!CheckEmail.isEmailValid(edt_emailAddress.getText().toString()
				.trim())) {
			mToast.showImpToast(getResources().getString(
					R.string.Login_invalidEmail));
			edt_emailAddress.requestFocus();
			return false;
		} else if (edt_password.getText().toString().trim().length() <= 5
				|| edt_password.getText().toString().trim().equals("")) {
			mToast.showImpToast(getResources().getString(
					R.string.Login_invalidPassword));
			edt_password.requestFocus();
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
	public void loginResponse(String status, String message) {
		if (status.equals("1")) {
			MySharedPref.getInstance(this).setString(
					Con_UserDetails.isUserLogined, "true");
			MySharedPref.getInstance(this).setString(
					Con_UserDetails.userPassword,
					edt_password.getText().toString().trim());
			MySharedPref.getInstance(this).setString(
					Con_SpalshScreen.GlobalStored_USERtype, "customer");

			mToast.showImpToast(message);

			// mToast.showImpToast("Size : "+navigationDrawerList.size()+"") ;
			MySharedPref.getInstance(this).setString(
					HTTP_RESPONSE_AddToCart.AddToCart_ + "buynow_quoteId", "");
			MySharedPref.getInstance(this).setString(
					HTTP_RESPONSE_AddToCart.AddToCart_
							+ HTTP_RESPONSE_AddToCart.quoteid, "");

			Intent intent = new Intent(this, MainCategory.class);
			if (MySharedPref.getInstance(this)
					.getString(Con_UserDetails.isUserLogined).equals("true")) {
				navigationDrawerList.add("LOGOUT");
			}

			intent.putStringArrayListExtra("navigationDrawerList",
					navigationDrawerList);
			startActivity(intent);
			this.finish();

			hideTheKeyBoard();

			InputMethodManager inputManager = (InputMethodManager) this
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(this.getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		} else if (status == null || message == null) {
			mToast.showImpToast(getResources().getString(
					R.string.errorInResponse));
		} else if (status.equals("") || message.equals("")) {
			mToast.showImpToast(getResources().getString(
					R.string.errorInResponse));
		} else {
			mToast.showImpToast(message);
		}
	}

	public void facebookButtonClicked() {
		List<String> permission = Arrays.asList("user_friends", "email");

		Session session = openActiveSession(this, true, callback, permission);

		if (session.isOpened()) {
			Request.newMeRequest(session, new Request.GraphUserCallback() {

				@Override
				public void onCompleted(GraphUser user, Response response) {
					// TODO Auto-generated method stub
					Log.i("onCompleted : session status", "Opened : "
							+ response.toString());

					GraphObject graphObject = response.getGraphObject();
					JSONObject innerJsonObject = graphObject
							.getInnerJSONObject();

					Iterator<String> iterator = innerJsonObject.keys();
					ArrayList<String> data = new ArrayList<String>();
					while (iterator.hasNext()) {
						String key = iterator.next();
						data.add(getString(innerJsonObject, key));
					}
				}

			}).executeAsync();
		}
	}

	public String getString(JSONObject json, String TAG) {
		String returnParseData = "blankValuePresent";

		try {
			Object aObj = json.get(TAG);
			if (aObj instanceof Double) {
				returnParseData = Double.toString(json.getDouble(TAG));
			} else {
				returnParseData = json.getString(TAG);
			}
		} catch (Exception err) {
			err.printStackTrace();
			returnParseData = "blankValuePresent";
		}

		return returnParseData;
	}

	private static Session openActiveSession(Activity activity,
			boolean allowLoginUI, StatusCallback callback,
			List<String> permissions) {
		OpenRequest openRequest = new OpenRequest(activity).setPermissions(
				permissions).setCallback(callback);
		Session session = new Session.Builder(activity).build();
		if (SessionState.CREATED_TOKEN_LOADED.equals(session.getState())
				|| allowLoginUI) {
			Session.setActiveSession(session);
			session.openForRead(openRequest);
			return session;
		}
		return null;
	}

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	boolean isAccessed = false;

	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		if (state.isOpened()) {
			Log.i("session status", "Logged in...");
			// if ( isAccessed == false )
			// {
			// facebookButtonClicked();
			// isAccessed = true ;
			// }
		} else if (state.isClosed()) {
			Log.i("session status", "Logged out...");
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
		MySharedPref.getInstance(this).setString("MyCurrentActivityOnPage","Login") ;

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);

		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

}

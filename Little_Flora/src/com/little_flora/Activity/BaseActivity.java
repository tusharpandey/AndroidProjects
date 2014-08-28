package com.little_flora.Activity;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Contacts.People;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.PhoneLookup;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.Toast;

import com.little_flora.R;
import com.little_flora.Activity.Adapter.SlideDrawerAdapter;
import com.little_flora.Activity.Async.SearchResultAsync;
import com.little_flora.Activity.Utils.HTTP_POST_urls;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_AddToCart;
import com.little_flora.Activity.Utils.LF_Constants.Con_SpalshScreen;
import com.little_flora.Activity.Utils.LF_Constants.Con_UserDetails;
import com.little_flora.Activity.Utils.CheckNetwork;
import com.little_flora.Activity.Utils.LF_Constants.Con_UserSearch;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.Dialogs.DialogContactUs;
import com.little_flora.Dialogs.DialogFollowUs;
import com.little_flora.Dialogs.DialogForSharing;
import com.little_flora.Dialogs.DialogNewsletter;
import com.little_flora.Dialogs.DialogShareTheApp;
import com.little_flora.SlidePaneLayout_.MyAccount;
import com.little_flora.User.CustomerAddressList;
import com.little_flora.User.Login;
import com.little_flora.User.Profile;
import com.little_flora.User.Callback.NewsLetterSubscribedCallback;

public class BaseActivity extends FragmentActivity implements
		NewsLetterSubscribedCallback {

	public DrawerLayout mDrawerLayout;
	public ListView mDrawerList;
	public ActionBarDrawerToggle mDrawerToggle;
	public CharSequence mDrawerTitle;
	public CharSequence mTitle;
	public String[] mPlanetTitles;
	private MenuItem myActionMenuItem;
	private EditText myActionEditText;
	private ImageView myActionImageView;
	private ImageView myActionImageView1;

	public boolean isAlreadyRun;

	public BaseActivity() {
		isAlreadyRun = false;
	}

	public ArrayList<String> listToSlideDrawerNavigation = new ArrayList<String>();

	public void hideKeyboardEveryTime() {
		InputMethodManager inputManager = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(this.getCurrentFocus()
				.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	public static float convertDpToPixel(float dp, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return px;
	}

	public class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	public void selectItem(int position) {
		for (int i = 0; i < listToSlideDrawerNavigation.size(); i++) {
			Log.e("Data", listToSlideDrawerNavigation.get(i));
		}

		for (int i = 0; i < listToSlideDrawerNavigation.size(); i++) {
			if (i == position) {
				callMainCategory(position);
			}
		}
	}

	// Con_UserSearch.productSearchString
	public void callMainCategory(int openThisSection) {
		if (openThisSection == 0 || openThisSection == 5) {// Toast
			if (openThisSection == 5) {
				Intent intent = new Intent(this, Login.class);
				if (MySharedPref.getInstance(this)
						.getString(Con_UserDetails.isUserLogined)
						.equals("true")) {
					listToSlideDrawerNavigation.remove(5);
				}

				MySharedPref.getInstance(this).setString(
						Con_UserDetails.isUserLogined, "false");
				MySharedPref.getInstance(this).setString(
						HTTP_RESPONSE_AddToCart.AddToCart_ + "buynow_quoteId",
						"");
				MySharedPref.getInstance(this).setString(
						HTTP_RESPONSE_AddToCart.AddToCart_
								+ HTTP_RESPONSE_AddToCart.quoteid, "");

				intent.putStringArrayListExtra("navigationDrawerList",
						listToSlideDrawerNavigation);

				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

				MySharedPref.getInstance(this).setString(
						Con_UserDetails.userId, Integer.toString(-1));

				hideKeyboardEveryTime();

				// MySharedPref.getInstance(this).

				startActivity(intent);
			} else {
				/*
				 * Open this section = 50 for opening default menu page .
				 */
				if (CheckNetwork.isNetworkAvailable(this)) {

					Intent intent = new Intent(this, MainCategory.class);
					intent.putStringArrayListExtra("navigationDrawerList",
							listToSlideDrawerNavigation);
					intent.putExtra(
							"navigationDrawerList_openParticularSection", 50);
					startActivity(intent);

					MySharedPref
							.getInstance(this)
							.setString(
									"navigationDrawerList_openParticularSection_currentPosition",
									"50");

					mDrawerLayout.closeDrawers();

					hideKeyboardEveryTime();
				} else {
					Toast.makeText(
							this,
							getResources()
									.getString(R.string.networkNotPresent),
							Toast.LENGTH_LONG).show();
				}
			}
		} else {
			/*
			 * openThisSection = openThisSection - 1 ;
			 * 
			 * this is because in top we have : home and we are expanding group
			 * according to position .
			 */

			openThisSection = openThisSection - 1;
			if (CheckNetwork.isNetworkAvailable(this)) {
				Intent intent = new Intent(this, MainCategory.class);
				intent.putStringArrayListExtra("navigationDrawerList",
						listToSlideDrawerNavigation);
				intent.putExtra("navigationDrawerList_openParticularSection",
						openThisSection);

				MySharedPref
						.getInstance(this)
						.setString(
								"navigationDrawerList_openParticularSection_currentPosition",
								openThisSection + "");

				mDrawerLayout.closeDrawers();

				startActivity(intent);

				hideKeyboardEveryTime();
			} else {
				Toast.makeText(this,
						getResources().getString(R.string.networkNotPresent),
						Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		Log.e("onNewIntent", "onNewIntent");

		// String currentActivity =
		// MySharedPref.getInstance(this).getString("MyCurrentActivityOnPage") ;
		// if ( currentActivity != null &&
		// currentActivity.equals("SearchActivity"))
		// if ( isAlreadyRun == false )
		// {
		handleIntent(intent);
		// isAlreadyRun = true ;
		// }
	}

	// public int count = 0 ;

	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);

			Toast.makeText(this, "Search Query : " + query, Toast.LENGTH_SHORT)
					.show();
			searchView.setQuery(query, false);

			if (!query.equals("") || query != null) {
				hideTheKeyBoard();

				String newQuery = query.replace(" ", "%20");
				MySharedPref.getInstance(this).setString(
						Con_UserSearch.productSearchString, newQuery);

				String currentActivity = MySharedPref.getInstance(this)
						.getString("MyCurrentActivityOnPage");
				if (currentActivity != null) {
					if (!currentActivity.equals("SearchActivity")) {

						Intent searchIntent = new Intent(this,
								SearchResult.class);
						searchIntent.putStringArrayListExtra(
								"navigationDrawerList",
								listToSlideDrawerNavigation);
						searchIntent.putExtra("query", newQuery);
						startActivity(searchIntent);
					}
				}

				InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
			} else {
				Toast.makeText(
						this,
						getResources().getString(
								R.string.Search_nothingToSearch),
						Toast.LENGTH_LONG).show();
			}
		}
	}

	public void navigationDrawerSetup(String[] navigationArray) {
		mTitle = mDrawerTitle = getTitle();
		mPlanetTitles = navigationArray;
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// set up the drawer's list view with items and click listener

		SlideDrawerAdapter adapter = new SlideDrawerAdapter(navigationArray,
				this);

		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' carete */
		R.string.drawer_open, /* open drawer" description for accessibility */
		R.string.drawer_close /* close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	MenuItem[] menuItemsArrayToHide = new MenuItem[3];
	private SearchView searchView;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		/*
		 * Toast.makeText(this, "Search Clicked", Toast.LENGTH_LONG).show();
		 */
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		searchView = (SearchView) menu.findItem(R.id.action_search)
				.getActionView();

		// menu.findItem(R.id.action_search).sett ;

		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));

		MenuItem item = menu.findItem(R.id.action_account);

		String language = MySharedPref.getInstance(this).getString(
				Con_SpalshScreen.GlobalStoredLanguageForApp);

		if (MySharedPref.getInstance(this)
				.getString(Con_UserDetails.isUserLogined).equals("true")) {
			if (language.equals("English")) {
				item.setTitle(getResources().getString(
						R.string.Overflow_myaccount_en));
			} else if (language.equals("Arabic")) {
				item.setTitle(getResources().getString(
						R.string.Overflow_myaccount_ar));
			}
		} else {
			if (language.equals("English")) {
				item.setTitle(getResources().getString(
						R.string.Overflow_Login_en));
			} else if (language.equals("Arabic")) {
				item.setTitle(getResources().getString(
						R.string.Overflow_Login_ar));
			}
		}

		/*
		 * Home - Logout option we have to add , in a custom way , so for
		 * Home-Logout we have to add arabic and english for it .
		 */
		if (language.equals("English")) {
			menu.findItem(R.id.action_carticon).setTitle(
					getResources().getString(R.string.Overflow_Cart_en));
			menu.findItem(R.id.action_whatsapp).setTitle(
					getResources().getString(R.string.Overflow_whatsApp_en));
			menu.findItem(R.id.action_call).setTitle(
					getResources().getString(R.string.Overflow_call_en));
			menu.findItem(R.id.action_search).setTitle(
					getResources().getString(R.string.Overflow_Search_en));
			menu.findItem(R.id.action_wishlist).setTitle(
					getResources().getString(R.string.Overflow_WishList_en));
			menu.findItem(R.id.action_order).setTitle(
					getResources().getString(R.string.Overflow_Order_en));
			menu.findItem(R.id.action_share).setTitle(
					getResources().getString(R.string.Overflow_Share_en));
			menu.findItem(R.id.action_newsletter).setTitle(
					getResources().getString(R.string.Overflow_NewsLetter_en));
			menu.findItem(R.id.action_contactus).setTitle(
					getResources().getString(R.string.Overflow_ContactUs_en));
			menu.findItem(R.id.action_followus).setTitle(
					getResources().getString(R.string.Overflow_Followus_en));
			menu.findItem(R.id.action_policies).setTitle(
					getResources().getString(R.string.Overflow_Policies_en));
		} else if (language.equals("Arabic")) {
			menu.findItem(R.id.action_carticon).setTitle(
					getResources().getString(R.string.Overflow_Cart_ar));
			menu.findItem(R.id.action_whatsapp).setTitle(
					getResources().getString(R.string.Overflow_whatsApp_ar));
			menu.findItem(R.id.action_call).setTitle(
					getResources().getString(R.string.Overflow_call_ar));
			menu.findItem(R.id.action_search).setTitle(
					getResources().getString(R.string.Overflow_Search_ar));
			menu.findItem(R.id.action_wishlist).setTitle(
					getResources().getString(R.string.Overflow_WishList_ar));
			menu.findItem(R.id.action_order).setTitle(
					getResources().getString(R.string.Overflow_Order_ar));
			menu.findItem(R.id.action_share).setTitle(
					getResources().getString(R.string.Overflow_Share_ar));
			menu.findItem(R.id.action_newsletter).setTitle(
					getResources().getString(R.string.Overflow_NewsLetter_ar));
			menu.findItem(R.id.action_contactus).setTitle(
					getResources().getString(R.string.Overflow_ContactUs_ar));
			menu.findItem(R.id.action_followus).setTitle(
					getResources().getString(R.string.Overflow_Followus_ar));
			menu.findItem(R.id.action_policies).setTitle(
					getResources().getString(R.string.Overflow_Policies_ar));
		}

		/*
		 * searchView.setOnQueryTextFocusChangeListener(new
		 * View.OnFocusChangeListener() {
		 * 
		 * @Override public void onFocusChange(View view, boolean
		 * queryTextFocused) { if(!queryTextFocused) { invalidateOptionsMenu();
		 * } } });
		 */

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		switch (item.getItemId()) {
		case R.id.action_wishlist:
			if (CheckNetwork.isNetworkAvailable(this)) {
				Intent intentWishlist = new Intent(this,
						CustomerAddressList.class);
				intentWishlist.putStringArrayListExtra("navigationDrawerList",
						listToSlideDrawerNavigation);
				startActivity(intentWishlist);

				hideKeyboardEveryTime();
			} else {
				Toast.makeText(this,
						getResources().getString(R.string.networkNotPresent),
						Toast.LENGTH_LONG).show();

			}
			return true;
		case R.id.action_carticon:
			Intent intentWishlist = new Intent(this, Cart.class);
			intentWishlist.putStringArrayListExtra("navigationDrawerList",
					listToSlideDrawerNavigation);
			startActivity(intentWishlist);

			return true;
		case R.id.action_call:
			callingToLittleFlora();
			return true;
		case R.id.action_search:
			getActionBar().setIcon(android.R.color.transparent);

			/*
			 * for ( int i = 0 ; i < menuItemsArrayToHide.length ; i++ ) {
			 * menuItemsArrayToHide[i].setVisible(false); }
			 */
			return true;
		case R.id.action_account:

			if (MySharedPref.getInstance(this)
					.getString(Con_UserDetails.isUserLogined).equals("true")) {
				if (CheckNetwork.isNetworkAvailable(this)) {
					Intent intent = new Intent(this, MyAccount.class);

					String userId = MySharedPref.getInstance(this).getString(
							Con_UserDetails.userId);

					intent.putExtra("userId", userId);
					intent.putStringArrayListExtra("navigationDrawerList",
							listToSlideDrawerNavigation);
					startActivity(intent);

					hideKeyboardEveryTime();
				} else {
					Toast.makeText(
							this,
							getResources()
									.getString(R.string.networkNotPresent),
							Toast.LENGTH_LONG).show();

				}

			} else {
				Intent intent = new Intent(this, Login.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putStringArrayListExtra("navigationDrawerList",
						listToSlideDrawerNavigation);
				startActivity(intent);

				hideKeyboardEveryTime();
			}

			return true;
		case R.id.action_whatsapp:
			// shareWhatsApp(this,"919654309293@s.whatsapp.net") ;
			addContact("Little flora",
					getResources()
							.getString(R.string.LittleFloraWhatsAppNumber));
			return true;
		case R.id.action_followus:
			DialogFollowUs followusDialog = new DialogFollowUs(this);
			followusDialog.setCancelable(true);
			followusDialog.show();
			return true;
		case R.id.action_share:
			DialogShareTheApp sharetheappDialog = new DialogShareTheApp(this);
			sharetheappDialog.setCancelable(true);
			sharetheappDialog.show();
			return true;
		case R.id.action_contactus:
			DialogContactUs contactusDialog = new DialogContactUs(this);
			contactusDialog.setCancelable(true);
			contactusDialog.show();
			return true;
		case R.id.action_newsletter:
			DialogNewsletter newsletterDialog = new DialogNewsletter(this);
			newsletterDialog.setCancelable(true);
			newsletterDialog.show();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (NoSuchMethodException e) {
					Log.e("BaseActivity", "onMenuOpened", e);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}

	private void addContact(String name, String phone) {

		boolean contactAlreadyPresent = false;
		Uri contactUri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI,
				Uri.encode(phone));
		Cursor cur = this.getContentResolver().query(contactUri, null, null,
				null, null);
		try {
			if (cur.moveToFirst()) {
				do {
					if (cur.getString(
							cur.getColumnIndex(PhoneLookup.DISPLAY_NAME))
							.equalsIgnoreCase(name)) {
						/*
						 * Toast.makeText(this, "Already present",
						 * Toast.LENGTH_SHORT).show();
						 */contactAlreadyPresent = true;
						break;
					}
				} while (cur.moveToNext());
			}
		} catch (Exception err) {
			// Toast.makeText(this, "Exception", Toast.LENGTH_SHORT).show();
		}

		if (contactAlreadyPresent == false) {
			ContentValues values = new ContentValues();
			values.put(People.NUMBER, phone);
			values.put(People.TYPE, Phone.TYPE_CUSTOM);
			values.put(People.LABEL, name);
			values.put(People.NAME, name);
			Uri dataUri = getContentResolver().insert(People.CONTENT_URI,
					values);
			Uri updateUri = Uri.withAppendedPath(dataUri,
					People.Phones.CONTENT_DIRECTORY);
			values.clear();
			values.put(People.Phones.TYPE, People.TYPE_MOBILE);
			values.put(People.NUMBER, phone);
			updateUri = getContentResolver().insert(updateUri, values);
		}

		sharewhatsAppMessage();
	}

	public void sharewhatsAppMessage() {
		try {
			Cursor c = this.getContentResolver().query(
					ContactsContract.Data.CONTENT_URI,
					new String[] { ContactsContract.Contacts.Data._ID },
					ContactsContract.Data.DATA1 + "=?",
					new String[] { getResources().getString(
							R.string.LittleFloraWhatsAppNumber)
							+ "@s.whatsapp.net" }, null);
			c.moveToFirst();
			Intent i = new Intent(Intent.ACTION_VIEW,
					Uri.parse("content://com.android.contacts/data/"
							+ c.getString(0)));
			// i.setType("text/plain");
			i.putExtra(Intent.EXTRA_TEXT, "ki haal");
			startActivity(i);
			c.close();
		} catch (Exception err) {
			Toast.makeText(this, "WhatsApp is not installed in your device",
					Toast.LENGTH_LONG).show();
		}
	}

	void openWhatsappContact(String number) {
		Uri uri = Uri.parse("smsto:" + number);
		Intent i = new Intent(Intent.ACTION_SENDTO, uri);
		i.setPackage("com.whatsapp");
		startActivity(Intent.createChooser(i, ""));
	}

	public static void shareWhatsApp(Activity appActivity, String texto) {

		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		sendIntent.setType("text/plain");
		sendIntent.putExtra(android.content.Intent.EXTRA_TEXT, texto);

		PackageManager pm = appActivity.getApplicationContext()
				.getPackageManager();
		final List<ResolveInfo> matches = pm.queryIntentActivities(sendIntent,
				0);
		boolean temWhatsApp = false;
		for (final ResolveInfo info : matches) {
			if (info.activityInfo.packageName.startsWith("com.whatsapp")) {
				final ComponentName name = new ComponentName(
						info.activityInfo.applicationInfo.packageName,
						info.activityInfo.name);
				sendIntent.addCategory(Intent.CATEGORY_LAUNCHER);
				sendIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				sendIntent.setComponent(name);
				temWhatsApp = true;
				break;
			}
		}

		if (temWhatsApp) {
			// abre whatsapp
			appActivity.startActivity(sendIntent);
		} else {
			// alerta - você deve ter o whatsapp instalado
			Toast.makeText(appActivity, "WhatsApp", Toast.LENGTH_SHORT).show();
		}
	}

	public void callingToLittleFlora() {
		try {
			EndCallListener phoneListener = new EndCallListener();
			TelephonyManager telephonyManager = (TelephonyManager) this
					.getSystemService(Context.TELEPHONY_SERVICE);
			telephonyManager.listen(phoneListener,
					PhoneStateListener.LISTEN_CALL_STATE);

			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri
					.parse("tel:"
							+ getResources().getString(
									R.string.LittleFloraPhoneNumber)));
			startActivity(callIntent);
		} catch (Exception err) {
			Toast.makeText(this,
					"Sorry , Calling is not present in your device",
					Toast.LENGTH_SHORT).show();
		}
	}

	private class EndCallListener extends PhoneStateListener {
		private boolean isPhoneCalling = false;

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			if (TelephonyManager.CALL_STATE_RINGING == state) {
				Log.i("EndCallListener", "RINGING, number: " + incomingNumber);
			}
			if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
				// wait for phone to go offhook (probably set a boolean flag) so
				// you know your app initiated the call.
				Log.i("EndCallListener", "OFFHOOK");

				isPhoneCalling = true;
			}
			if (TelephonyManager.CALL_STATE_IDLE == state) {
				// when this state occurs, and your flag is set, restart your
				// app
				Log.i("EndCallListener", "IDLE");

				if (isPhoneCalling) {

					Log.i("EndCallListener", "restart app");

					// restart app
					Intent i = getBaseContext().getPackageManager()
							.getLaunchIntentForPackage(
									getBaseContext().getPackageName());
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);

					isPhoneCalling = false;
				}
			}
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		hideTheKeyBoard();
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	public void saveBitmapIntoStorage(Bitmap bitmap) {
		FileOutputStream out = null;

		File f = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "test.jpg");
		try {
			out = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (Throwable ignore) {
			}
		}
	}

	public void hideTheKeyBoard() {
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	@Override
	public void newsLetterSubscribed(boolean result, String message) {
		// TODO Auto-generated method stub
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

	}

	public ArrayList<String> sortAccordingToPrice(ArrayList<String> arrayList,
			int according) {

		if (according == 1 || according == 2 || according == 3
				|| according == 4) {
			ArrayList<String> newSorted_assec = new ArrayList<String>();
			ArrayList<String> newSortedPositions = new ArrayList<String>();

			String[] prices = arrayList.toArray(new String[arrayList.size()]);

			int[] old_pricesInt = new int[prices.length];

			int multi_NewPrice [][] = new int [old_pricesInt.length][2];
			
			for (int i = 0; i < old_pricesInt.length; i++) {
				old_pricesInt[i] = (int) Float.parseFloat(prices[i]);
			}

			int[] pricesInt = new int[prices.length];

			for (int i = 0; i < pricesInt.length; i++) {
				pricesInt[i] = (int) Float.parseFloat(prices[i]);
			}

			switch (according) {
			case 1:
				Arrays.sort(pricesInt);

//				for (int i = 0; i < old_pricesInt.length; i++) {
////					Log.e("Old Price : ", old_pricesInt[i] + "");
//				}
//				for (int i = 0; i < pricesInt.length; i++) 
//				{
////					Log.e("Price sort : ", pricesInt[i] + "");
//				}

				for (int i = pricesInt.length - 1; i >= 0; i--) {
					
					int j = 0;
					
					multi_NewPrice[i][0] = pricesInt[i] ;

					if ( i < pricesInt.length-1 && multi_NewPrice[i][0] == multi_NewPrice[i+1][0])
					{
						j = multi_NewPrice[i+1][1]+1 ;
					}
					
					while (pricesInt[i] != old_pricesInt[j]) {
						j++;
					}

					multi_NewPrice[i][1] = j ;
					
					newSortedPositions.add(Integer.toString(j));
				}
				
//				for ( int i = 0 ; i < multi_NewPrice.length ; i++ )
//				{
//					Log.e("multi_NewPrice  : ", multi_NewPrice[i][0] + " : "+multi_NewPrice[i][1]);
//				}

				return newSortedPositions;

			case 2:
				Arrays.sort(pricesInt);

//				for (int i = 0; i < old_pricesInt.length; i++) {
//					Log.e("Old Price : ", old_pricesInt[i] + "");
//				}
//				for (int i = 0; i < pricesInt.length; i++) {
//					Log.e("Price sort : ", pricesInt[i] + "");
//				}
				for (int i = 0; i < pricesInt.length; i++) {
					int j = 0;

					multi_NewPrice[i][0] = pricesInt[i] ;

					if ( i >= 1 && multi_NewPrice[i][0] == multi_NewPrice[i-1][0])
					{
						j = multi_NewPrice[i-1][1]+1 ;
					}

					while (old_pricesInt[j] != pricesInt[i]) {
						j++;
					}
					
					multi_NewPrice[i][1] = j ;

					newSortedPositions.add(Integer.toString(j));
				}

				return newSortedPositions;
			case 3:
				for (int i = 0; i < pricesInt.length; i++) {
					newSorted_assec.add(pricesInt[i] + "");

					Log.e("Price sort : ", pricesInt[i] + "");
				}
				for (int i = 0; i < newSorted_assec.size(); i++) {
					newSortedPositions.add(Integer.toString(i));
				}
				return newSortedPositions;
			case 4:
				for (int i = 0; i < pricesInt.length; i++) {
					newSorted_assec.add(pricesInt[i] + "");

					Log.e("Price sort : ", pricesInt[i] + "");
				}
				for (int i = newSorted_assec.size() - 1; i >= 0; i--) {
					newSortedPositions.add(Integer.toString(i));
				}
				return newSortedPositions;

			default:

				break;
			}
		}
		else if ( according == 5 || according == 6 )
		{
			ArrayList<String> newSortedPositions = new ArrayList<String>();

			switch (according) {
			case 5:
				String[] names = arrayList.toArray(new String[arrayList.size()]);
				
				for (int i = 0; i < arrayList.size(); i++) {
					Log.e("Old Name : ", arrayList.get(i) + "");
				}

				Arrays.sort(names);

				for (int i = 0; i < names.length; i++) {
					Log.e("Name sort : ", names[i] + "");
				}

				for (int i = 0 ; i < names.length ; i++ ) {
					int j = 0;
					while (!names[i].equals(arrayList.get(j))) {
						j++;
					}
					newSortedPositions.add(Integer.toString(j));
				}

				return newSortedPositions;
			case 6:
				String[] names_sku = arrayList.toArray(new String[arrayList.size()]);
				
				for (int i = 0; i < arrayList.size(); i++) {
					Log.e("Old Sku : ", arrayList.get(i) + "");
				}

				Arrays.sort(names_sku);

				for (int i = 0; i < names_sku.length; i++) {
					Log.e("Sku sort : ", names_sku[i] + "");
				}

				for (int i = 0 ; i < names_sku.length ; i++ ) {
					int j = 0;
					while (!names_sku[i].equals(arrayList.get(j))) {
						j++;
					}
					newSortedPositions.add(Integer.toString(j));
				}

				return newSortedPositions;

			default:
				break;
			}
		}

		return null;
	}

	public void switchContent(Fragment frag, boolean addToBack, boolean add,
			String tag) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		if (add)
			ft.add(R.id.container, frag);
		else
			ft.replace(R.id.container, frag);
		if (addToBack)
			ft.addToBackStack(tag);
		ft.commit();
	}

	public boolean addToBack = false, add = false;
	public String tag = null;

}

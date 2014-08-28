package com.little_flora.Activity;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.little_flora.R;
import com.little_flora.Activity.Async.SplachScreenSync;
import com.little_flora.Activity.CallBacks.MainCategory_CallBack;
import com.little_flora.Activity.Utils.CheckNetwork;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_MainCategory;
import com.little_flora.Activity.Utils.LF_Constants.Con_SpalshScreen;
import com.little_flora.Activity.Utils.LF_Constants.Con_UserDetails;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.Activity.Utils.Toasts_;
import com.little_flora.Activity.Utils.TypeFace_TextView;
import com.little_flora.User.Login;

public class SplashScreen extends Activity implements android.view.View.OnClickListener , MainCategory_CallBack  {
	
	private TextView txt_English;
	private TextView txt_Arabic;
	private Toasts_ mToasts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.activity_splashscreen);

        initView() ;
        mToasts = new Toasts_(this);
        

	}
	
	public void initView()
	{
		txt_English = ( TextView ) findViewById(R.id.txt_English) ;
		txt_English.setOnClickListener(this);
		TypeFace_TextView.setTypeFace(this, txt_English);
		
		txt_Arabic = ( TextView ) findViewById(R.id.txt_Arabic) ;
		txt_Arabic.setOnClickListener(this);
		TypeFace_TextView.setTypeFace(this, txt_Arabic);	
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_English:
			MySharedPref.getInstance(this).setString(Con_SpalshScreen.GlobalStoredLanguageForApp, "English");
			setStoreIdGroupIdWebsiteId ("1","1","1");
			
			if ( MySharedPref.getInstance(this).getString(Con_UserDetails.isUserLogined) != null )
			{
				callActivity();
			}
			
			MySharedPref.getInstance(this).setString("navigationDrawerList_openParticularSection_currentPosition", "");
			
			break;
			
		case R.id.txt_Arabic:
			MySharedPref.getInstance(this).setString(Con_SpalshScreen.GlobalStoredLanguageForApp, "Arabic");
			setStoreIdGroupIdWebsiteId ("1","10","10");
			if ( MySharedPref.getInstance(this).getString(Con_UserDetails.isUserLogined) != null )
			{
				callActivity() ;
			}

			MySharedPref.getInstance(this).setString("navigationDrawerList_openParticularSection_currentPosition", "");
			
			break;
			
		default:
			break;
		}
	}
	
	public void callActivity()
	{
		if ( MySharedPref.getInstance(this).getString(Con_UserDetails.isUserLogined).equals("true") )
		{
			String data[] = new String[1];
			String language = MySharedPref.getInstance(this).getString(Con_SpalshScreen.GlobalStoredLanguageForApp) ;
			
			if ( CheckNetwork.isNetworkAvailable(this) )
			{
				SplachScreenSync syncObject = new SplachScreenSync(data, this, language);
				syncObject.execute("");
			}
			else
			{
				mToasts.showImpToast(getResources().getString(R.string.networkNotPresent)) ;
			}

		}
		else
		{
			String data[] = new String[1];
			String language = MySharedPref.getInstance(this).getString(Con_SpalshScreen.GlobalStoredLanguageForApp) ;
			
			if ( CheckNetwork.isNetworkAvailable(this) )
			{
				SplachScreenSync syncObject = new SplachScreenSync(data, this, language);
				syncObject.execute("");
			}
			else
			{
				mToasts.showImpToast(getResources().getString(R.string.networkNotPresent)) ;
			}

		}
	}
	
	public void setStoreIdGroupIdWebsiteId ( String groupId , String websiteId , String storeId )
	{
		MySharedPref.getInstance(this).setString(Con_SpalshScreen.GlobalStored_StoreId, storeId);
		MySharedPref.getInstance(this).setString(Con_SpalshScreen.GlobalStored_WebsiteId, websiteId);
		MySharedPref.getInstance(this).setString(Con_SpalshScreen.GlobalStored_GroupId, groupId);		
	}
	
	Dictionary<String, Dictionary<String, String>> mainCategoryDataList ;
	Dictionary<String, Dictionary<String, Dictionary<String, String>>> subCategoryDataWholeDict ;
	ArrayList<String> keyToShare ;
	private Hashtable<String, Dictionary<String, String>> mainCategoryDataList_inMenu_Active;
	private ArrayList<String> navigationDrawerList;
	
	
	@Override
	public void responseOfMainCategoryCallBack(
			Dictionary<String, Dictionary<String, String>> mainCategoryDataList,
			Dictionary<String, Dictionary<String, Dictionary<String, String>>> subCategoryDataWholeDict) {
		// TODO Auto-generated method stub
		
		/*
		 * Create Data list for Navigation Drawer .
		 */
				
		mainCategoryDataList_inMenu_Active = new Hashtable<String, Dictionary<String,String>>();
		
		int j = 0 ; 
		for ( int i = 0 ; i < mainCategoryDataList.size() ; i++ )
		{
			if ( mainCategoryDataList.get(Integer.toString(i)).get(HTTP_RESPONSE_MainCategory.InMenu).equals("1") 
					&&  
					mainCategoryDataList.get(Integer.toString(i)).get(HTTP_RESPONSE_MainCategory.Active).equals("1") )	
			{
				mainCategoryDataList_inMenu_Active.put(Integer.toString(j), mainCategoryDataList.get(Integer.toString(i))) ;
				j++ ;
			}
		}
		
		this.subCategoryDataWholeDict = subCategoryDataWholeDict ;
		
		navigationDrawerList = new ArrayList<String> () ;
		
		String language = MySharedPref.getInstance(this).getString(Con_SpalshScreen.GlobalStoredLanguageForApp) ;

		/*
		 * Home - Logout option we have to add , in a custom way , so for Home-Logout we have to add arabic and english for it .
		 */
		if ( language.equals("English"))
		{
			navigationDrawerList.add("HOME");
		}
		else if ( language.equals("Arabic"))
		{
			navigationDrawerList.add(getResources().getString(R.string.NavigationDrawer_Home));
		}
		for ( int i = 0 ; i < mainCategoryDataList_inMenu_Active.size() ; i ++ )
		{
			navigationDrawerList.add(mainCategoryDataList_inMenu_Active.get(Integer.toString(i)).get(HTTP_RESPONSE_MainCategory.title)) ;
		}
		if ( MySharedPref.getInstance(this).getString(Con_UserDetails.isUserLogined).equals("true") )
		{
			if ( language.equals("English"))
			{
				navigationDrawerList.add("LOGOUT");
			}
			else if ( language.equals("Arabic"))
			{
				navigationDrawerList.add(getResources().getString(R.string.NavigationDrawer_Logout));
			}
		}
		if ( MySharedPref.getInstance(this).getString(Con_UserDetails.isUserLogined).equals("true") )
		{
			MySharedPref.getInstance(this).setString(Con_SpalshScreen.GlobalStored_USERtype, "customer");
			
			Intent intent =  new Intent (this,MainCategory.class) ;
			intent.putStringArrayListExtra("navigationDrawerList", navigationDrawerList);
			startActivity(intent) ;
		}
		else
		{
			MySharedPref.getInstance(this).setString(Con_SpalshScreen.GlobalStored_USERtype, "guest");

			Intent intent =  new Intent (this,Login.class) ;
			intent.putStringArrayListExtra("navigationDrawerList", navigationDrawerList);
			startActivity(intent) ;
		}

		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}

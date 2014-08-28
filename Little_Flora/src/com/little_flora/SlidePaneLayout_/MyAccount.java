package com.little_flora.SlidePaneLayout_;

import java.util.ArrayList;
import java.util.Dictionary;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.little_flora.R;
import com.little_flora.Activity.BaseActivity;
import com.little_flora.Activity.Adapter.MyAccountSlidePanelAdapter;
import com.little_flora.User.Callback.MyAccountCallback;

public class MyAccount extends BaseActivity implements MyAccountCallback , OnItemClickListener , OnClickListener{
	private SlidingPaneLayout pane;
	private ListView lst_myAccountSection;
	public TextView txt_accountTitle;
	private ImageView img_arrowSlider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myaccount);

		ArrayList<String> navigationDrawerList = getIntent().getStringArrayListExtra("navigationDrawerList");
		
    	if ( navigationDrawerList == null )
    	{
    		Log.e("NullPointerException : ", "navigationDrawerList is null") ;
    	}
        
    	listToSlideDrawerNavigation = navigationDrawerList ;
        
        String [] navigationArray = navigationDrawerList.toArray(new String[navigationDrawerList.size()]);
        
		navigationDrawerSetup(navigationArray);
		
		pane = (SlidingPaneLayout) findViewById(R.id.sp);
		pane.setPanelSlideListener(new PaneListener());
		
		//pane.setShadowResource(R.drawable.slideoane_drawerbackground);
		pane.setSliderFadeColor(Color.parseColor("#00000000"));		
		
		initView();
	}
	
	public void initView()
	{
		txt_accountTitle =  (TextView  ) findViewById(R.id.txt_accountTitle) ;
		
		lst_myAccountSection = ( ListView ) findViewById(R.id.lst_myAccountSection) ;
		lst_myAccountSection.setOnItemClickListener(this) ;
		
		ArrayList<String> arrayList = new ArrayList<String>() ;
//		arrayList.add("Account Dashboard");
		arrayList.add("Account Information");
		arrayList.add("Address Book");
		arrayList.add("My Orders");
//		arrayList.add("Billing Agreements");
//		arrayList.add("Recurring Profiles");
//		arrayList.add("My Product Reviews");
//		arrayList.add("My Tags");
//		arrayList.add("Wishlist");
//		arrayList.add("My Applications");
//		arrayList.add("Newsletter Subscriptions");
//		arrayList.add("My Downloadable Products");
		
		MyAccountSlidePanelAdapter adapter = new MyAccountSlidePanelAdapter ( arrayList,this) ;
		lst_myAccountSection.setAdapter(adapter) ;
		
		img_arrowSlider = ( ImageView ) findViewById(R.id.img_arrowSlider) ;
		img_arrowSlider.setOnClickListener(this) ;
	}

	@Override
	public void responseOfMyAccount(String message, String status,
			Dictionary<String, Dictionary<String, String>> accountData) {
		// TODO Auto-generated method stub
		
		
	}
	
	
	public boolean addToBack = false, add = false;
	public String tag = null;
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		switchContent(new AccountInformation(), addToBack, add, tag);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
		boolean clicked = false ;
		
		switch (position) {
//		case 0:
//			switchContent(new AccountDashboard(), addToBack, add, tag);
//			clicked = true ;
//			break;
		case 0:
			switchContent(new AccountInformation(), addToBack, add, tag);
			clicked = true ;
			break;
		case 1:
			switchContent(new AddressBook(), addToBack, add, tag);
			clicked = true ;
			break;
		case 2:
			switchContent(new MyOrders(), addToBack, add, tag);
			clicked = true ;
			break;
		case 4:
			switchContent(new BillingAggreements(), addToBack, add, tag);
			clicked = true ;
			break;
		case 5:
			switchContent(new RecurringProfiles(), addToBack, add, tag);
			clicked = true ;
			break;
		case 6:
			switchContent(new MyProductReviews(), addToBack, add, tag);
			clicked = true ;
			break;
		case 7:
			switchContent(new MyTags(), addToBack, add, tag);
			clicked = true ;
			break;
		case 8:
			switchContent(new Wishlist(), addToBack, add, tag);
			clicked = true ;
			break;
		case 9:
			switchContent(new MyApplications(), addToBack, add, tag);
			clicked = true ;
			break;
		case 10:
			switchContent(new NewsletterSubscriptions(), addToBack, add, tag);
			clicked = true ;
			
			break;
		case 11:
			switchContent(new MyDownloadableProducts(), addToBack, add, tag);
			clicked = true ;
			
			break;

		default:
			break;
		}
		
		if ( clicked == true )
		{
			pane.closePane();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.img_arrowSlider:
			if( pane != null && !pane.isOpen() )
			{
				pane.openPane();
			}
			break;

		default:
			break;
		}
	}
	
	
}

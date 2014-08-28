package com.little_flora.Activity;

import java.util.ArrayList;
import java.util.Dictionary;

import android.os.Bundle;
import android.widget.ListView;

import com.little_flora.R;
import com.little_flora.Activity.Adapter.CartAdapter;
import com.little_flora.Activity.Utils.HTTP_POST_urls;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.Activity.Utils.Toasts_;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_AddToCart;
import com.little_flora.ProductsSync.CartSync;
import com.little_flora.User.Callback.CartCallback;
import com.little_flora.User.Callback.CartUpdateCallback;

public class Cart extends BaseActivity implements CartCallback , CartUpdateCallback {
	private ArrayList<String> navigationDrawerList;
	private ListView lst_cart;
	private Toasts_ mToast;
	private Logs_ mLogs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        
        
        mToast = new Toasts_(this) ;
        mLogs = new Logs_("Cart");
		
        navigationDrawerList = getIntent().getStringArrayListExtra("navigationDrawerList");
        
        listToSlideDrawerNavigation = navigationDrawerList ;
        
        String [] navigationArray = navigationDrawerList.toArray(new String[navigationDrawerList.size()]);
        
		navigationDrawerSetup(navigationArray);
		
		initView() ;
		
		try
		{
			String [] data = new String [1] ;
			
			String quoteId = MySharedPref.getInstance(this).getString(HTTP_RESPONSE_AddToCart.AddToCart_+HTTP_RESPONSE_AddToCart.quoteid);
			if ( quoteId == null || quoteId.length() <= 0 )
			{
				mToast.showImpToast(getResources().getString(R.string.cartIsBlank)) ;
			}
			else
			{
				String url = HTTP_POST_urls.userCartData + quoteId ;
				String loadingText = getResources().getString(R.string.loading_cartDataoading) ;
				CartSync syncCart = new CartSync(url, data, this, loadingText);
				syncCart.execute("");
			}
		}
		catch  ( Exception err )
		{
			mToast.showImpToast(getResources().getString(R.string.networkNotPresent)) ;
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MySharedPref.getInstance(this).setString("MyCurrentActivityOnPage","Cart") ;
	}
	
	public void initView()
	{
		lst_cart = ( ListView ) findViewById(R.id.lst_cart) ;
	}

	@Override
	public void responseCartCallBack(String status , String message ,
			Dictionary<String, Dictionary<String, String>> cartDictionary) {
		// TODO Auto-generated method stub
		
		mLogs.showTempLog("Status : "+status);
		mLogs.showTempLog("Message : "+message);
		mLogs.showTempLog("Size : "+cartDictionary.size());
		
		if ( status != null && message != null )
		{
			if ( message.contains("Error in Response"))
			{
				mToast.showImpToast(message) ;
			}
			else
			{
				CartAdapter cartAdapter = new CartAdapter(this, cartDictionary);
				lst_cart.setAdapter(cartAdapter) ;
			}
		}
	}

	@Override
	public void responseCartUpdateCallBack(boolean status, String message) {
		// TODO Auto-generated method stub
		if ( message != null )
		{
			mToast.showImpToast(message) ;
		}
	}

}

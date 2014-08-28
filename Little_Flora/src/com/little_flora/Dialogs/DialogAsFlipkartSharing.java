package com.little_flora.Dialogs;

import java.util.Dictionary;
import java.util.Hashtable;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.Window;
import android.widget.ImageView;

import com.little_flora.R;
import com.little_flora.Activity.ProductList_Grid;
import com.little_flora.Activity.SearchResult;
import com.little_flora.Activity.Utils.HTTP_POST_urls;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_AddToCart;
import com.little_flora.ProductsSync.AddToCartAsync;
import com.little_flora.ProductsSync.AddToCart_SecondTime;

public class DialogAsFlipkartSharing extends Dialog implements
android.view.View.OnClickListener 
{
	private ImageView img_addtoCart;
	private ImageView img_share;
	ProductList_Grid context_PL ;
	SearchResult context_SR ;
	CharSequence skuSend ;
	CharSequence product_idSend ; 
	CharSequence qtySend ;
	CharSequence store_idSend ;
	Dictionary<String, CharSequence> dictionaryToSend ;
	private Logs_ mLogs;
	String fromWhere ;
	
	public DialogAsFlipkartSharing(String fromWhere , Context context , Dictionary<String, CharSequence> dictionaryToSend ) 
	{
		super(context);
		
		this.fromWhere = fromWhere ;
		if ( fromWhere.equals("ProductList_GRID") )
		{
			this.context_PL = (ProductList_Grid)context ;
		}
		else if( fromWhere.equals("SearchResult") )
		{
			this.context_SR = (SearchResult)context ;
		}
		this.dictionaryToSend = dictionaryToSend ;
		
		this.skuSend = dictionaryToSend.get("skuSend")  ;
		this.product_idSend = dictionaryToSend.get("product_idSend")  ; 
		this.qtySend = dictionaryToSend.get("qtySend")  ;
		this.store_idSend = dictionaryToSend.get("store_idSend")  ;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.dialog_asflipkart_sharing);
		
		mLogs = new Logs_("DialogAsFlipkartSharing") ;
		
		initView();
	}

	public void initView()
	{
		img_addtoCart = ( ImageView ) findViewById(R.id.img_addtoCart);
		img_addtoCart.setOnClickListener(this) ;
		
		img_share = ( ImageView ) findViewById(R.id.img_share);
		img_share.setOnClickListener(this) ;
	}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) 
		{
		case R.id.img_addtoCart:
				String loadingText = null ;
				String quoteId = null ;
			
				if ( fromWhere.equals("ProductList_GRID") )
				{
					loadingText = context_PL.getResources().getString(R.string.loading_addingIntoTheCart) ;
					quoteId = MySharedPref.getInstance(context_PL).getString(HTTP_RESPONSE_AddToCart.AddToCart_+HTTP_RESPONSE_AddToCart.quoteid);
				}
				else if( fromWhere.equals("SearchResult") )
				{
					loadingText = context_SR.getResources().getString(R.string.loading_addingIntoTheCart) ;
					quoteId = MySharedPref.getInstance(context_SR).getString(HTTP_RESPONSE_AddToCart.AddToCart_+HTTP_RESPONSE_AddToCart.quoteid);
				}
				String [] data = new String [1] ;
				
				String url = "" ;
				
				if ( quoteId != null && quoteId.length() > 0 )
				{
					//quoteid,qty,sku
					url = HTTP_POST_urls.addToCart_secondtime +
							"sku="+skuSend+
							"&qty="+1+
							"&quoteid="+quoteId;
				}
				else
				{
					url = HTTP_POST_urls.addToCart +
						"sku="+skuSend+
						"&product_id="+product_idSend+
						"&qty="+1+
						"&store_id="+store_idSend;
				}

				mLogs.showImpLog(url) ;
				
				AddToCartAsync syncAddToCart = null ;
				if ( fromWhere.equals("ProductList_GRID") )
				{
					syncAddToCart = new AddToCartAsync(url, data, context_PL, loadingText,"DialogAsFlipKart_productList");
				}
				else if( fromWhere.equals("SearchResult") )
				{
					syncAddToCart = new AddToCartAsync(url, data, context_SR, loadingText,"DialogAsFlipKart_searchResult");
				}				
				syncAddToCart.execute("");
				
				this.dismiss();
			break;
		case R.id.img_share:
			if ( fromWhere.equals("ProductList_GRID") )
			{
				context_PL.onDialogAsFlipKartSharing_ShareClicked();
			}
			else if( fromWhere.equals("SearchResult") )
			{
				context_SR.onDialogAsFlipKartSharing_ShareClicked();
			}		
			this.dismiss();				
			break;

		default:
			break;
		}
		
	}


}

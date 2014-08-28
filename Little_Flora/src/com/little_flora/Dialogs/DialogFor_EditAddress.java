package com.little_flora.Dialogs;

import java.util.Dictionary;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.little_flora.R;
import com.little_flora.Activity.ProductList_Grid;
import com.little_flora.Activity.SearchResult;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.SlidePaneLayout_.MyAccount;

public class DialogFor_EditAddress extends Dialog implements
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
	Dictionary<String, String> dictionaryToSend ;
	private Logs_ mLogs;
	String fromWhere ;
	private TextView txt_edtAddress_title;
	private CheckBox chk_useAsShippingAddress;
	private CheckBox chk_useAsBillingAddress;
	private TextView btn_addNewAddress;
	
	public DialogFor_EditAddress(String fromWhere , MyAccount context , Dictionary<String, String> dictionaryToSend ) 
	{
		super(context);
		
		this.fromWhere = fromWhere ;
		this.dictionaryToSend = dictionaryToSend ;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.dialog_editaddress);
		
		mLogs = new Logs_("DialogAsFlipkartSharing") ;
		
		initView();
		
		txt_edtAddress_title.setText(fromWhere);
	}

	public void initView()
	{
		txt_edtAddress_title = ( TextView ) findViewById(R.id.txt_edtAddress_title) ;
		chk_useAsShippingAddress = ( CheckBox ) findViewById(R.id.chk_useAsShippingAddress) ;
		chk_useAsBillingAddress = ( CheckBox ) findViewById(R.id.chk_useAsBillingAddress) ;
		btn_addNewAddress =  ( TextView ) findViewById(R.id.btn_addNewAddress) ;
		
		if( fromWhere.equals("Edit Shipping Address"))
		{
			chk_useAsShippingAddress.setVisibility(View.GONE);
			btn_addNewAddress.setText("Submit Shipping Address");
		}
		else if( fromWhere.equals("Edit Billing Address"))
		{
			chk_useAsBillingAddress.setVisibility(View.GONE);
			btn_addNewAddress.setText("Submit Billing Address");
		}
		else if ( fromWhere.equals("Edit Address List's Address") )
		{
			btn_addNewAddress.setText("Submit Changed Address");
		}
	}
	
	@Override
	public void onClick(View v) {}

	public void printLogs ( String message )
	{
		Log.e("DialogFor_EditAddress", message) ;
	}
}

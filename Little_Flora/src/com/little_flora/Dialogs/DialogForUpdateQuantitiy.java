package com.little_flora.Dialogs;


import java.util.Dictionary;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.little_flora.R;
import com.little_flora.Activity.Cart;
import com.little_flora.Activity.Utils.CheckNetwork;
import com.little_flora.Activity.Utils.HTTP_POST_urls;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.Activity.Utils.Toasts_;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_AddToCart;
import com.little_flora.ProductsSync.CartUpdateAsync;

public class DialogForUpdateQuantitiy extends Dialog implements
android.view.View.OnClickListener 
{
	Cart context ;
	private Logs_ mLogs;
	String quantity ;
	private ImageView img_minus;
	private ImageView img_plus;
	private EditText edt_quantity;
	private Button btn_cancel;
	private Button btn_ok;
	private Toasts_ mToast;
	String sku ;
	String qty ;
	
	public DialogForUpdateQuantitiy(String sku ,String qty , Cart context , String quantity ) {
		super(context);
		
		this.context = context ;
		
		this.quantity = quantity ;
		this.context = context ;
		this.sku = sku ;
		this.qty = qty ;
		
		mToast = new Toasts_(context) ;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.dialog_quantityupdate);
		
		mLogs = new Logs_("DialogForUpdateQuantitiy") ;
		
		initView();
	}

	public void initView()
	{
		img_minus = ( ImageView ) findViewById(R.id.img_minus);
		img_minus.setOnClickListener(this) ;
		
		img_plus = ( ImageView ) findViewById(R.id.img_plus);
		img_plus.setOnClickListener(this) ;
		
		edt_quantity = ( EditText ) findViewById(R.id.edt_quantity);
		edt_quantity.setText(quantity) ;
		
		btn_cancel = ( Button ) findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(this) ;

		btn_ok = ( Button ) findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(this) ;
	}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) 
		{
		
		case R.id.btn_cancel:
			this.dismiss() ;
			break;
		
		case R.id.img_plus:
			
			int quantityInt = Integer.parseInt(edt_quantity.getText().toString().trim()) ;
			int newQuantiry = quantityInt+1 ;
			edt_quantity.setText(newQuantiry+"");
			
			break;
		
		case R.id.img_minus:
			
			int quantityInt1 = Integer.parseInt(edt_quantity.getText().toString().trim()) ;
			int newQuantiry1 = quantityInt1-1 ;
			edt_quantity.setText(newQuantiry1+"");

			break;
		
		case R.id.btn_ok:
			
			if ( CheckNetwork.isNetworkAvailable(context) )
			{
				String [] data = new String [1] ;
//				String url = HTTP_POST_urls.addToCart_secondtime ;
				
				String quoteId = MySharedPref.getInstance(context).getString(HTTP_RESPONSE_AddToCart.AddToCart_+HTTP_RESPONSE_AddToCart.quoteid);

				String url = HTTP_POST_urls.addToCart_secondtime +
						"sku="+sku+
						"&qty="+4+
						"&quoteid="+quoteId;
				
				String loadingText = context.getResources().getString(R.string.loading_cartUpdate) ;
				
				CartUpdateAsync asyncUpdate = new CartUpdateAsync(url, data, context, loadingText);
				asyncUpdate.execute("") ;
				
				this.dismiss() ;
			}
			else
			{
				mToast.showImpToast(context.getResources().getString(R.string.networkNotPresent)) ;
			}
			
			break;
			
		default:
			break;
		}
		
	}


}

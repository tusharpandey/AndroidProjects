package com.little_flora.Dialogs;

import java.util.Dictionary;
import java.util.Hashtable;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.little_flora.R;
import com.little_flora.Activity.ProductDescription;
import com.little_flora.Activity.ProductList_Grid;
import com.little_flora.Activity.SearchResult;
import com.little_flora.Activity.Utils.CheckEmail;
import com.little_flora.Activity.Utils.HTTP_POST_urls;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_AddToCart;
import com.little_flora.Activity.Utils.LF_Constants.Con_SpalshScreen;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.Activity.Utils.Toasts_;
import com.little_flora.Activity.Utils.TypeFace_TextView;
import com.little_flora.ProductsSync.AddToCartAsync;
import com.little_flora.ProductsSync.CartCustomerAddAsync;

public class DialogBuyNow extends Dialog implements
android.view.View.OnClickListener 
{
	private Logs_ mLogs;
	ProductDescription context ;
	private TextView txt_Dialog_sortBy;
	private EditText edt_Dialog_firstName;
	private EditText edt_Dialog_lastname;
	private EditText edt_Dialog_email;
	private Button btn_Dialog_continue;
	private Toasts_ mToast;
	Dictionary<String, String> dictionary ;
	
	public DialogBuyNow(ProductDescription context , Dictionary<String, String> dictionary) 
	{
		super(context);
		this.context = context ;
		this.dictionary = dictionary ;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.dialog_buynow);
		
		mLogs = new Logs_("DialogAsFlipkartSharing") ;
		mToast = new Toasts_(context) ;
		
		initView();
	}

	public void initView()
	{
		txt_Dialog_sortBy = ( TextView ) findViewById(R.id.txt_Dialog_sortBy);
		TypeFace_TextView.setTypeFace(context, txt_Dialog_sortBy);

		edt_Dialog_firstName = ( EditText ) findViewById(R.id.edt_Dialog_firstName);
		edt_Dialog_lastname = ( EditText ) findViewById(R.id.edt_Dialog_lastname);
		edt_Dialog_email = ( EditText ) findViewById(R.id.edt_Dialog_email);

		btn_Dialog_continue = ( Button ) findViewById(R.id.btn_Dialog_continue);
		TypeFace_TextView.setTypeFace(context, btn_Dialog_continue);
		btn_Dialog_continue.setOnClickListener(this) ;
	}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) 
		{
		case R.id.btn_Dialog_continue:
			
			String firstName = edt_Dialog_firstName.getText().toString().trim() ;
			String lastname = edt_Dialog_lastname.getText().toString().trim() ;
			String email = edt_Dialog_email.getText().toString().trim() ;
			String website_id = MySharedPref.getInstance(context).getString(Con_SpalshScreen.GlobalStored_WebsiteId);
			String store_id = MySharedPref.getInstance(context).getString(Con_SpalshScreen.GlobalStored_StoreId);
			String quoteId = MySharedPref.getInstance(context).getString(HTTP_RESPONSE_AddToCart.AddToCart_+"buynow_quoteId");
			String mode = MySharedPref.getInstance(context).getString(Con_SpalshScreen.GlobalStored_USERtype) ;

			if ( isValidInputs(firstName,lastname,email) )
			{
				String urlPost = HTTP_POST_urls.byNow_cart_create_customer+
						"firstname="+firstName+
						"&lastname="+lastname+
						"&website_id="+website_id+
						"&mode="+mode+
						"&quoteid="+quoteId+
						"&email="+email ;
				
				String urlPre = HTTP_POST_urls.addToCart +
						"sku="+dictionary.get("sku")+
						"&product_id="+dictionary.get("product_id")+
						"&qty="+1+
						"&store_id="+store_id;	
				
				Dictionary<String, String> dictionary = new Hashtable<String, String>() ;
				dictionary.put("firstname", firstName);
				dictionary.put("lastname", lastname);
				dictionary.put("website_id", website_id);
				dictionary.put("mode", mode);
				dictionary.put("email", email);
				
				
				String [] data = new String [1] ;
				String loadingText = context.getResources().getString(R.string.loading_cartCustomerAdd) ;
				
				CartCustomerAddAsync asyncCartCustomerAdd = new CartCustomerAddAsync("guest",dictionary,urlPost, urlPre, context, loadingText) ;
				asyncCartCustomerAdd.execute("");
			}
			
			break;
		default:
			break;
		}
		
	}
	
	public boolean isValidInputs(String firstName,String lastname,String email)
	{
		if ( firstName.length() <= 0 )
		{
			mToast.showImpToast(context.getResources().getString(R.string.Registration_invalidFirstName));
			edt_Dialog_firstName.requestFocus();
		}
		else if ( lastname.length() <= 0 )
		{
			mToast.showImpToast(context.getResources().getString(R.string.Registration_invalidLastName));
			edt_Dialog_lastname.requestFocus();
		}
		else if ( !CheckEmail.isEmailValid(email) )
		{
			mToast.showImpToast(context.getResources().getString(R.string.Registration_invalidEmail));
			edt_Dialog_email.requestFocus();
		}
		
		return true ;
	}


}

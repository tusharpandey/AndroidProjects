package com.little_flora.Dialogs;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.little_flora.R;
import com.little_flora.Activity.BaseActivity;
import com.little_flora.Activity.ProductList_Grid;
import com.little_flora.Activity.SearchResult;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_Products;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_Search;

public class DialogFilterBy extends Dialog implements
		android.view.View.OnClickListener {
	String a = "a";

	ProductList_Grid context_pgl;
	SearchResult context_sr;

	private ProgressBar pgb_orderdetailprogress;
	private Button btn_filter;
	Dictionary<String, Dictionary<String, String>> productListDict ;

	private EditText edt_startLimit;

	private EditText edt_endLimit;
	Dictionary<String, Dictionary<String, String>> new_productListDict = new Hashtable<String, Dictionary<String,String>>() ;
	String fromWhere ;
	
	public DialogFilterBy(Dictionary<String, Dictionary<String, String>> productListDict , BaseActivity context, float basePrice,
			float finalPrice , String fromWhere) {
		super(context);
		// TODO Auto-generated constructor stub
		
		this.fromWhere = fromWhere ;
		
		if ( fromWhere.equals("ProductList_Grid"))
		{
			this.context_pgl = (ProductList_Grid) context;
		}
		else if ( fromWhere.equals("SearchResult"))
		{
			this.context_sr = (SearchResult) context;
		}
		
		this.productListDict = productListDict ;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.btn_filter:
			sortproductListDict();
			this.dismiss();
			break;

		default:
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_filterby);

		initView();
	}

	public void initView() {
		btn_filter = (Button) findViewById(R.id.btn_filter);
		btn_filter.setOnClickListener(this);
		
		edt_startLimit = ( EditText ) findViewById(R.id.edt_startLimit) ;
		edt_endLimit = ( EditText ) findViewById(R.id.edt_endLimit) ;
	}

	public void showLogs(String msg) {
		Log.e("DialogFilterBy", msg);
	}
	
	public void sortproductListDict( )
	{
		showLogs("productListDict size : "+productListDict.size());
		
		String str_startLimit = edt_startLimit.getText().toString() ;
		String str_endLimit = edt_endLimit.getText().toString() ;
		
		if ( str_startLimit.length() > 0 && str_endLimit.length() > 0 )
		{
			int startLimit = Integer.parseInt(str_startLimit);
			int endLimit = Integer.parseInt(str_endLimit);
			
			showLogs("Price startLimit : "+startLimit) ;
			showLogs("Price endLimit : "+endLimit) ;
			
			Enumeration<String> enumeration = productListDict.keys() ;
			ArrayList<String> keys = new ArrayList<String>() ;
			ArrayList<String> postionList = new ArrayList<String>() ;
			
			while (enumeration.hasMoreElements()) {
				String string = (String) enumeration.nextElement();
				keys.add(string) ;
			}
			
			//dictionaryForProductInfo.get(HTTP_RESPONSE_Products.price)
			
			for ( int i = 0 ; i < productListDict.size() ; i++ )
			{
				Dictionary<String , String> dictionary = productListDict.get(keys.get(i)) ;
				
				float f_price = Float.parseFloat(dictionary.get(HTTP_RESPONSE_Products.price));
				
				int price = (int)f_price ;
				showLogs("Price : "+price) ;
				
				if ( price > startLimit && price < endLimit )
				{
					//dictionaryForProductInfo.get(HTTP_RESPONSE_Search.entity_id)
					if ( fromWhere.equals("ProductList_Grid"))
					{
						new_productListDict.put(dictionary.get(HTTP_RESPONSE_Products.id), dictionary);	
					}
					else if ( fromWhere.equals("SearchResult"))
					{
						new_productListDict.put(Integer.toString(i), dictionary);	
					}
				}
			}
			
			
			productListDict = new_productListDict; 
		}
		else
		{
			
		}
		if ( fromWhere.equals("ProductList_Grid"))
		{
			showLogs("productListDict size : "+productListDict.size());
			context_pgl.getProductList(productListDict);
		}
		else if ( fromWhere.equals("SearchResult"))
		{
			showLogs("productListDict size : "+productListDict.size());
			
			Enumeration<String> enumeration_ = new_productListDict.keys() ;
			
			while (enumeration_.hasMoreElements())
			{
				String keys = enumeration_.nextElement() ;
				showLogs("productListDict keys : "+keys);
			}
			
			context_sr.createDataForSearchAdapter ( productListDict ) ;
		}

	}
}

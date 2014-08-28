
package com.little_flora.Dialogs;

import java.util.Dictionary;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.little_flora.R;
import com.little_flora.Activity.ProductList_Grid;
import com.little_flora.Activity.SearchResult;
import com.little_flora.Activity.Utils.Logs_;

public class Dialog_myOrdersMore extends Dialog implements
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
	private TextView txt_myorder_more_ordermoney;
	private TextView txt_myorder_more_status;
	private TextView txt_myorder_more_vieworder;
	
	public Dialog_myOrdersMore(String fromWhere , Context context , Dictionary<String, String> dictionaryToSend ) 
	{
		super(context);
		
		this.dictionaryToSend = dictionaryToSend ;
		this.fromWhere = fromWhere ;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.dialog_myorders_more);
		
		mLogs = new Logs_("DialogAsFlipkartSharing") ;
		
		initView();
	}

	public void initView()
	{
		txt_myorder_more_ordermoney = ( TextView ) findViewById(R.id.txt_myorder_more_ordermoney) ;
		txt_myorder_more_status = ( TextView ) findViewById(R.id.txt_myorder_more_status) ;
		txt_myorder_more_vieworder = ( TextView ) findViewById(R.id.txt_myorder_more_vieworder) ;
		
		txt_myorder_more_ordermoney.setText(dictionaryToSend.get("txt_orderStatus"));
		txt_myorder_more_status.setText(dictionaryToSend.get("txt_orderOrderTotal"));
		txt_myorder_more_vieworder.setText(dictionaryToSend.get("txt_orderOrderviewOrder"));
		
	}
	
	@Override
	public void onClick(View v) {}


}

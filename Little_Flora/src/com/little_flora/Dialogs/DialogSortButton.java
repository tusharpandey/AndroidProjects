package com.little_flora.Dialogs;

import java.util.Dictionary;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.little_flora.R;
import com.little_flora.Activity.BaseActivity;
import com.little_flora.Activity.ProductList_Grid;
import com.little_flora.Activity.SearchResult;
import com.little_flora.Activity.Utils.TypeFace_TextView;

public class DialogSortButton extends Dialog implements
		android.view.View.OnClickListener {

	private TextView txt_SortDialog_sku;
	private TextView txt_SortDialog_name;
	private TextView txt_SortDialog_positionlowtohigh;
	private TextView txt_SortDialog_positionhightolow;
	private TextView txt_SortDialog_pricelowtohigh;
	private TextView txt_SortDialog_pricehightolow;
	private TextView txt_SortDialog_sortBy;
	
	ProductList_Grid context_pgl ;
	SearchResult context_sr ;
	BaseActivity context ;
	
	Dictionary<String, Dictionary<String, String>> productListDict ;
	String fromWhere ;
	
	public DialogSortButton(BaseActivity context,Dictionary<String, Dictionary<String, String>> productListDict ,String fromWhere) {
		
		super(context);
		this.fromWhere = fromWhere ;
		this.context = context ;
		this.productListDict = productListDict ;
		
		if ( fromWhere.equals("ProductList_Grid"))
		{
			this.context_pgl = (ProductList_Grid) context;
		}
		else if ( fromWhere.equals("SearchResult"))
		{
			this.context_sr = (SearchResult) context;
		}

		
		// TODO Auto-generated constructor stub
	}
	
	public void initView()
	{
		txt_SortDialog_sku =  (TextView ) this.findViewById(R.id.txt_SortDialog_sku);
		TypeFace_TextView.setTypeFace(context, txt_SortDialog_sku);
		txt_SortDialog_sku.setOnClickListener(this);
		
		txt_SortDialog_name =  (TextView ) this.findViewById(R.id.txt_SortDialog_name);
		TypeFace_TextView.setTypeFace(context, txt_SortDialog_name);
		txt_SortDialog_name.setOnClickListener(this);

		txt_SortDialog_positionlowtohigh =  (TextView ) this.findViewById(R.id.txt_SortDialog_positionlowtohigh);
		TypeFace_TextView.setTypeFace(context, txt_SortDialog_positionlowtohigh);
		txt_SortDialog_positionlowtohigh.setOnClickListener(this);

		txt_SortDialog_positionhightolow =  (TextView ) this.findViewById(R.id.txt_SortDialog_positionhightolow);
		TypeFace_TextView.setTypeFace(context, txt_SortDialog_positionhightolow);
		txt_SortDialog_positionhightolow.setOnClickListener(this);

		txt_SortDialog_pricelowtohigh =  (TextView ) this.findViewById(R.id.txt_SortDialog_pricelowtohigh);
		TypeFace_TextView.setTypeFace(context, txt_SortDialog_pricelowtohigh);
		txt_SortDialog_pricelowtohigh.setOnClickListener(this);

		txt_SortDialog_pricehightolow =  (TextView ) this.findViewById(R.id.txt_SortDialog_pricehightolow);
		TypeFace_TextView.setTypeFace(context, txt_SortDialog_pricehightolow);
		txt_SortDialog_pricehightolow.setOnClickListener(this);

		txt_SortDialog_sortBy =  (TextView ) this.findViewById(R.id.txt_SortDialog_sortBy);
		TypeFace_TextView.setTypeFace(context, txt_SortDialog_sortBy);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_sortbutton);
		
		initView();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
	
		case R.id.txt_SortDialog_pricehightolow:
			
			if ( fromWhere.equals("ProductList_Grid"))
			{
				context_pgl.sortAdapter(1);
			}
			else if ( fromWhere.equals("SearchResult"))
			{
				context_sr.sortAdapter(1);			
			}

			this.dismiss();
			break;
		case R.id.txt_SortDialog_pricelowtohigh:
			if ( fromWhere.equals("ProductList_Grid"))
			{
				context_pgl.sortAdapter(2);
			}
			else if ( fromWhere.equals("SearchResult"))
			{
				context_sr.sortAdapter(2);			
			}
			this.dismiss();
			break;
		case R.id.txt_SortDialog_positionhightolow:
			if ( fromWhere.equals("ProductList_Grid"))
			{
				context_pgl.sortAdapter(3);
			}
			else if ( fromWhere.equals("SearchResult"))
			{
				context_sr.sortAdapter(3);			
			}
			this.dismiss();
			break;
		case R.id.txt_SortDialog_positionlowtohigh:
			if ( fromWhere.equals("ProductList_Grid"))
			{
				context_pgl.sortAdapter(4);
			}
			else if ( fromWhere.equals("SearchResult"))
			{
				context_sr.sortAdapter(4);			
			}
			this.dismiss();
			break;
		case R.id.txt_SortDialog_name:
			if ( fromWhere.equals("ProductList_Grid"))
			{
				context_pgl.sortAdapter(5);
			}
			else if ( fromWhere.equals("SearchResult"))
			{
				context_sr.sortAdapter(5);			
			}
			this.dismiss();
			break;
		case R.id.txt_SortDialog_sku:
			if ( fromWhere.equals("ProductList_Grid"))
			{
				context_pgl.sortAdapter(6);
			}
			else if ( fromWhere.equals("SearchResult"))
			{
				context_sr.sortAdapter(6);			
			}
			this.dismiss();
			break;
		default:
			break;
		}
		
	}

}

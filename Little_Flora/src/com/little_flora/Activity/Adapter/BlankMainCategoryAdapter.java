package com.little_flora.Activity.Adapter;

import java.util.ArrayList;

import com.little_flora.R;
import com.little_flora.Activity.MainCategory;
import com.little_flora.Activity.ProductList_Grid;
import com.little_flora.Activity.SearchResult;
import com.little_flora.Activity.Utils.MySharedPref;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class BlankMainCategoryAdapter extends BaseAdapter
{
	ArrayList<String> list ;
	
	MainCategory context_m  ;
	ProductList_Grid context_p  ;
	SearchResult context_s  ;

	int sendScreenHeight ;
	String fromWhere ;
	
	public BlankMainCategoryAdapter ( ArrayList<String> list ,  Context context , int ScreenHeight , String fromWhere )
	{
		this.fromWhere = fromWhere ;
		this.sendScreenHeight = sendScreenHeight ;
		this.list = list ;
		
		if ( fromWhere.equals("MainCategory") )
		{
			this.context_m = (MainCategory)context ;
		}
		else if ( fromWhere.equals("ProductList_Grid") )
		{
			this.context_p = (ProductList_Grid)context ;
		}
		else if ( fromWhere.equals("SearchResult") )
		{
			this.context_s = (SearchResult)context ;
		}	
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater = null ;
		String sendStringHeight = null ;
		
		View view = null ;
		
		if ( fromWhere.equals("MainCategory") )
		{
			inflater = LayoutInflater.from(context_m) ;
			sendStringHeight = MySharedPref.getInstance(context_m).getString("sendScreenHeight_SPLASHSCREEN");
			view = inflater.inflate(R.layout.adapter_expandable_group, parent, false);
		}
		else if ( fromWhere.equals("ProductList_Grid") )
		{
			inflater = LayoutInflater.from(context_p) ;
			sendStringHeight = MySharedPref.getInstance(context_p).getString("sendScreenHeight_SPLASHSCREEN");
			view = inflater.inflate(R.layout.adapter_grid_productlisting, parent, false);
		}
		else if ( fromWhere.equals("SearchResult") )
		{
			inflater = LayoutInflater.from(context_s) ;
			sendStringHeight = MySharedPref.getInstance(context_s).getString("sendScreenHeight_SPLASHSCREEN");
			view = inflater.inflate(R.layout.adapter_grid_productlisting, parent, false);
		}
		
		Log.e("Adapter-Height", sendStringHeight+"");
		int sendStringHeight_int = Integer.valueOf(sendStringHeight) ;
		
		view.setMinimumHeight(sendStringHeight_int/2);
		Log.e("SendScreenHeight", sendStringHeight_int/2+"");
		
		return view;
	}
	
}

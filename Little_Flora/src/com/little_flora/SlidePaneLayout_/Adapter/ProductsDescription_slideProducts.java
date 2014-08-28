package com.little_flora.SlidePaneLayout_.Adapter;

import java.util.Dictionary;

import com.little_flora.R;
import com.little_flora.Activity.ProductDescription;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ProductsDescription_slideProducts extends BaseAdapter{

	Dictionary<String, Dictionary<String, String>> fullProducts ;
	ProductDescription context ;
	public ProductsDescription_slideProducts (ProductDescription context , Dictionary<String, Dictionary<String, String>> fullProducts)
	{
		this.fullProducts = fullProducts ;
		this.context = context ;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fullProducts.size();
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
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.adapter_slideproductdescription, parent, false) ;

		return view;
	}

}

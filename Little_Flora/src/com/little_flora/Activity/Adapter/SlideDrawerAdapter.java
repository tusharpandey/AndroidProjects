package com.little_flora.Activity.Adapter;

import com.little_flora.R;
import com.little_flora.Activity.BaseActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SlideDrawerAdapter extends BaseAdapter
{
	String [] navigationArray ;
	BaseActivity context ;
	
	public SlideDrawerAdapter ( String [] navigationArray , BaseActivity context)
	{
		this.navigationArray = navigationArray ;
		this.context = context ;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return navigationArray.length ;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position ;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.adapter_slidingdrawer, parent, false);
		
		TextView txt_itemName = ( TextView ) view.findViewById(R.id.txt_itemName) ;
		txt_itemName.setText(navigationArray[position]);
		
		ImageView img_itemIcon = ( ImageView ) view.findViewById(R.id.img_itemIcon) ;
		Bitmap icon = null;

		switch (position) {
		case 0:
			icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.drawer_home);			
			break;
		case 1:
			icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.drawer_product);			
			
			break;
		case 2:
			icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.drawer_occasion);			
			
			break;
		case 3:
			icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.drawer_color);			
			
			break;
		case 4:
			icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.drawer_luxury_collection);			
			
			break;
		case 5:
			icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.drawer_logout);			
			
			break;

		default:
			break;
		}
		
		img_itemIcon.setImageBitmap(icon);
		
		return view;
	}

}

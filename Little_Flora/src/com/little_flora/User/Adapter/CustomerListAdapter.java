package com.little_flora.User.Adapter;

import java.util.ArrayList;
import java.util.Dictionary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.little_flora.R;
import com.little_flora.Activity.Utils.TypeFace_TextView;
import com.little_flora.User.CustomerAddressList;

public class CustomerListAdapter extends BaseAdapter
{
	CustomerAddressList context ;
	ArrayList<Dictionary<String, String>> listOfAddress ;
	public CustomerListAdapter ( CustomerAddressList context , ArrayList<Dictionary<String, String>> listOfAddress )
	{
		this.context = context ;
		this.listOfAddress = listOfAddress ;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listOfAddress.size() ;
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
		
		ViewHolder viewHolder_group;
		if (convertView == null) {
			viewHolder_group = new ViewHolder();

			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.adapter_customeraddresslist, null);
			
/*	        viewHolder_group.txt_email  = (TextView) convertView.findViewById(R.id.txt_email);
			TypeFace_TextView.setTypeFace(context, viewHolder_group.txt_email);

			viewHolder_group.txt_email_field  = (TextView) convertView.findViewById(R.id.txt_email_field);
			TypeFace_TextView.setTypeFace(context, viewHolder_group.txt_email_field);
*/
			viewHolder_group.txt_firstname  = (TextView) convertView.findViewById(R.id.txt_firstname);
			TypeFace_TextView.setTypeFace(context, viewHolder_group.txt_firstname);

			viewHolder_group.txt_firstname_field  = (TextView) convertView.findViewById(R.id.txt_firstname_field);
			TypeFace_TextView.setTypeFace(context, viewHolder_group.txt_firstname_field);

			viewHolder_group.txt_lastname  = (TextView) convertView.findViewById(R.id.txt_lastname);
			TypeFace_TextView.setTypeFace(context, viewHolder_group.txt_lastname);

			viewHolder_group.txt_lastname_field  = (TextView) convertView.findViewById(R.id.txt_lastname_field);
			TypeFace_TextView.setTypeFace(context, viewHolder_group.txt_lastname_field);

			viewHolder_group.txt_street1  = (TextView) convertView.findViewById(R.id.txt_street1);
			TypeFace_TextView.setTypeFace(context, viewHolder_group.txt_street1);

			viewHolder_group.txt_street1_field  = (TextView) convertView.findViewById(R.id.txt_street1_field);
			TypeFace_TextView.setTypeFace(context, viewHolder_group.txt_street1_field);

/*			viewHolder_group.txt_street2  = (TextView) convertView.findViewById(R.id.txt_street2);
			TypeFace_TextView.setTypeFace(context, viewHolder_group.txt_street2);

			viewHolder_group.txt_street2_field  = (TextView) convertView.findViewById(R.id.txt_street2_field);
			TypeFace_TextView.setTypeFace(context, viewHolder_group.txt_street2_field);*/

			viewHolder_group.txt_city  = (TextView) convertView.findViewById(R.id.txt_city);
			TypeFace_TextView.setTypeFace(context, viewHolder_group.txt_city);

			viewHolder_group.txt_city_field  = (TextView) convertView.findViewById(R.id.txt_city_field);
			TypeFace_TextView.setTypeFace(context, viewHolder_group.txt_city_field);

			viewHolder_group.txt_postcode  = (TextView) convertView.findViewById(R.id.txt_postcode);
			TypeFace_TextView.setTypeFace(context, viewHolder_group.txt_postcode);

			viewHolder_group.txt_postcode_field  = (TextView) convertView.findViewById(R.id.txt_postcode_field);
			TypeFace_TextView.setTypeFace(context, viewHolder_group.txt_postcode_field);

			viewHolder_group.txt_telephone  = (TextView) convertView.findViewById(R.id.txt_telephone);
			TypeFace_TextView.setTypeFace(context, viewHolder_group.txt_telephone);

			viewHolder_group.txt_telephone_field  = (TextView) convertView.findViewById(R.id.txt_telephone_field);
			TypeFace_TextView.setTypeFace(context, viewHolder_group.txt_telephone_field);
			
			
			
			convertView.setTag(viewHolder_group);
		}
		else {
			viewHolder_group = (ViewHolder) convertView.getTag();
		}

//        viewHolder_group.txt_email_field.setText(listOfAddress.get(position).get(key));
		
        viewHolder_group.txt_firstname_field.setText(listOfAddress.get(position).get("firstname"));
        viewHolder_group.txt_lastname_field.setText(listOfAddress.get(position).get("lastname"));
        viewHolder_group.txt_street1_field.setText(listOfAddress.get(position).get("street"));
//        viewHolder_group.txt_street2_field.setText(listOfAddress.get(position).get("street"));
        viewHolder_group.txt_city_field.setText(listOfAddress.get(position).get("city"));
        viewHolder_group.txt_postcode_field.setText(listOfAddress.get(position).get("postcode"));
        viewHolder_group.txt_telephone_field.setText(listOfAddress.get(position).get("telephone"));
		
        return convertView ;
	}
	static class ViewHolder {

/*        TextView txt_email ;
        TextView txt_email_field ;
*/        
		TextView txt_firstname ;
        TextView txt_firstname_field ;
        TextView txt_lastname ;
        TextView txt_lastname_field ;
        TextView txt_street1 ;
        TextView txt_street1_field ;
/*        TextView txt_street2 ;
        TextView txt_street2_field ;*/
        TextView txt_city ;
        TextView txt_city_field ;
        TextView txt_postcode ;
        TextView txt_postcode_field ;
        TextView txt_telephone ;
        TextView txt_telephone_field ;

	}

}

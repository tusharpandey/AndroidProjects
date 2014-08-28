package com.little_flora.Activity.Adapter;

import java.util.Collections;
import java.util.Dictionary;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.little_flora.R;
import com.little_flora.Activity.Cart;
import com.little_flora.Activity.Utils.HTTP_POST_urls;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_AddToCart;
import com.little_flora.Dialogs.DialogForUpdateQuantitiy;
import com.little_flora.ProductsSync.CartProductDeleteAsync;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class CartAdapter extends BaseAdapter
{

	private AnimateFirstDisplayListener animateFirstListener;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	
	private String[] images ;
	private String[] productName ;
	private String[] productQuantity ;
	private String[] productId ;
	private String[] productSku ;
	
	
	Cart context ;
	Dictionary<String, Dictionary<String, String>> dictionaryCart ;
	
	public CartAdapter( Cart context , Dictionary<String, Dictionary<String, String>> dictionaryCart )
	{
		this.context = context ;
		this.dictionaryCart = dictionaryCart ;
		
		animateFirstListener = new AnimateFirstDisplayListener();
		imageLoader = ImageLoader.getInstance();

		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.placeholder)
		.showImageForEmptyUri(R.drawable.placeholder)
		.showImageOnFail(R.drawable.placeholder)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(5))
		.build() ;
		
		images = new String [dictionaryCart.size()] ;
		productName = new String [dictionaryCart.size()] ;
		productQuantity = new String [dictionaryCart.size()] ;
		productId = new String [dictionaryCart.size()] ;
		productSku = new String [dictionaryCart.size()] ;
		
		for ( int i = 0 ; i < dictionaryCart.size() ; i++ )
		{
			//images[i] = dictionaryCart.get(Integer.toString(i)).get("productImage"); 
			images[i] = "" ; 
			productName[i] = dictionaryCart.get(Integer.toString(i)).get("name"); 
			productId[i] = dictionaryCart.get(Integer.toString(i)).get("product_id"); 
			productSku[i] = dictionaryCart.get(Integer.toString(i)).get("sku"); 
			//productQuantity[i] = dictionaryCart.get(Integer.toString(i)).get("productQuantity"); 
			productQuantity[i] = "1" ;
		}
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dictionaryCart.size() ;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position ;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position ;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder_group;

		if (convertView == null) {
			viewHolder_group = new ViewHolder();

			LayoutInflater infalInflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.adapter_cart, null);
			
			viewHolder_group.img_remove = (ImageView) convertView.findViewById(R.id.img_remove);
			viewHolder_group.img_itemImage = (ImageView) convertView.findViewById(R.id.img_itemImage);
			viewHolder_group.txt_itemName = (TextView) convertView.findViewById(R.id.txt_itemName);
			viewHolder_group.txt_quantitiy = (TextView) convertView.findViewById(R.id.txt_quantitiy);
			
			convertView.setTag(viewHolder_group);
		}
		else 
		{
			viewHolder_group = (ViewHolder) convertView.getTag();
		}
		
		viewHolder_group.txt_quantitiy.setText(productQuantity[position]);
		viewHolder_group.txt_itemName.setText(productName[position]);

		final String quantity =  viewHolder_group.txt_quantitiy.getText().toString().trim() ; ;
		
		viewHolder_group.img_remove.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				String [] data = new String [1] ;
				
				String quoteId = MySharedPref.getInstance(context).getString(HTTP_RESPONSE_AddToCart.AddToCart_+HTTP_RESPONSE_AddToCart.quoteid);

				String url = HTTP_POST_urls.userDeleteProductFromCart +
						"quoteid="+quoteId+"&"+
						"product_id="+productId[position]+"&"+
						"sku="+productSku[position]+"&"+
						"qty="+1;
				
				String loadingText = context.getResources().getString(R.string.loading_cartdeleteProduct);
				
				CartProductDeleteAsync asyncDeleteProduct = new CartProductDeleteAsync(url, data, context, loadingText) ;
				asyncDeleteProduct.execute("");
			}
		});
		
		viewHolder_group.txt_quantitiy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String sku = dictionaryCart.get(Integer.toString(position)).get("sku");
				String qty = dictionaryCart.get(Integer.toString(position)).get("set");
				
				DialogForUpdateQuantitiy dialogForUpdateQuantity = new DialogForUpdateQuantitiy(sku , qty , context,quantity);
				dialogForUpdateQuantity.setCancelable(false);
				dialogForUpdateQuantity.show() ;
			}
		});

		
		imageLoader.displayImage(images[position], viewHolder_group.img_itemImage, options, animateFirstListener);

		return convertView ;
	}
	

	static class ViewHolder {
		ImageView img_itemImage ;	
		ImageView img_remove ;	
		TextView txt_itemName ;	
		TextView txt_quantitiy ;	
	}
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

}

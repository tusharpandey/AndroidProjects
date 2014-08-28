package com.little_flora.Activity;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.little_flora.R;
import com.little_flora.Activity.Adapter.BlankMainCategoryAdapter;
import com.little_flora.Activity.Async.ProductsInfo_Async;
import com.little_flora.Activity.CallBacks.Products_Callback;
import com.little_flora.Activity.CallBacks.SortAdapter;
import com.little_flora.Activity.Utils.CheckNetwork;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_Products;
import com.little_flora.Activity.Utils.LF_Constants.Con_SpalshScreen;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.Activity.Utils.Toasts_;
import com.little_flora.Activity.Utils.TypeFace_TextView;
import com.little_flora.Dialogs.DialogAsFlipkartSharing;
import com.little_flora.Dialogs.DialogFilterBy;
import com.little_flora.Dialogs.DialogForSharing;
import com.little_flora.Dialogs.DialogSortButton;
import com.little_flora.Utils.ApplicationClass;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ProductList_Grid extends BaseActivity implements    OnClickListener , Products_Callback , OnItemClickListener ,SortAdapter   {
	private Toasts_ mToasts;
	//private ListView productList;
	private TextView txt_pageTitle;
	private GridView gridView;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	private Animation animSideDown;
	private ArrayList<String> navigationDrawerList;
	private Button btn_Products_Filter;
	private Button btn_Products_Sort;
	private Logs_ mLogs;
	private LinearLayout linear_BottomLayout;
	private GridView gridview_temp;
//btn_Products_Sort
//btn_Products_Filter
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
//        View newView = getLayoutInflater().inflate(R.layout.activity_productlist, null);
//        newView.addOnLayoutChangeListener(this);
		
		setContentView(R.layout.activity_productlist);
		

		
		//hideTheKeyBoard();
		
		mToasts = new Toasts_(this) ;
		mLogs = new Logs_("ProductList");
		
		initView();
		
		String productId = getIntent().getStringExtra("ProductId") ;
		
		String [] data = new String [1] ;
		if ( CheckNetwork.isNetworkAvailable(this) )
		{
			ProductsInfo_Async asyncProductInfo = new ProductsInfo_Async(data, this, productId) ;
			asyncProductInfo.execute("");
		}
		else
		{
			mToasts.showImpToast(getResources().getString(R.string.networkNotPresent)) ;
		}
		
		navigationDrawerList = getIntent().getStringArrayListExtra("navigationDrawerList");

		listToSlideDrawerNavigation = navigationDrawerList ;
        
		String [] navigationArray = navigationDrawerList.toArray(new String[navigationDrawerList.size()]);

		navigationDrawerSetup(navigationArray);
		
/*        if (savedInstanceState == null) {
            selectItem(0);
        }
*/        
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.placeholder)
		.showImageForEmptyUri(R.drawable.placeholder)
		.showImageOnFail(R.drawable.placeholder)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		
		linear_BottomLayout = ( LinearLayout ) findViewById(R.id.linear_BottomLayout) ;
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MySharedPref.getInstance(this).setString("MyCurrentActivityOnPage","ProductList_Grid") ;
	}
	
	public void initView () 
	{
		//productList =  ( ListView ) findViewById(R.id.lst_productList);
		//productList.setOnItemClickListener(this) ;
		
		gridView = (GridView) findViewById(R.id.gridview);
		gridview_temp = (GridView) findViewById(R.id.gridview_temp);
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("1");
		list.add("1");
		list.add("1");
		list.add("1");
		list.add("1");
		list.add("1");
		list.add("1");
		
		int sendScreenHeight = Integer.parseInt(MySharedPref.getInstance(ProductList_Grid.this).getString(
				"sendScreenHeight_SPLASHSCREEN"));

		
		BlankMainCategoryAdapter adapter = new BlankMainCategoryAdapter(list, this,sendScreenHeight,"ProductList_Grid");
		gridview_temp.setAdapter(adapter) ;

		//gridView.setOnItemClickListener(this);
		
		
		btn_Products_Filter = ( Button ) findViewById(R.id.btn_Products_Filter);
		TypeFace_TextView.setTypeFace(this, btn_Products_Filter);
		btn_Products_Filter.setOnClickListener(this);
		
		btn_Products_Sort = ( Button ) findViewById(R.id.btn_Products_Sort);
		TypeFace_TextView.setTypeFace(this, btn_Products_Sort);
		btn_Products_Sort.setOnClickListener(this);
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	Dictionary<String, Dictionary<String, String>> productListDict ;
	
	private ImageAdapter imageAdapterGlobal;
	
	@Override
	public void getProductList(
			Dictionary<String, Dictionary<String, String>> productListDict) {
		// TODO Auto-generated method stub
		
		this.productListDict = productListDict ;
		
		ArrayList<CharSequence> name = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> description  = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> small_image = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> is_salable = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> qty = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> status = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> id = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> sku = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> price = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> image = new ArrayList<CharSequence>() ;
		
		if ( productListDict.size() == 0 )
		{
			mToasts.showImpToast("No Products Available Inside this Category") ;
			
			onBackPressed();
		}
		else
		{
			Enumeration<String> enumeration = productListDict.keys() ;
			ArrayList<String> keys = new ArrayList<String>() ;
			ArrayList<String> postionList = new ArrayList<String>() ;
			
			while (enumeration.hasMoreElements()) {
				String string = (String) enumeration.nextElement();
				keys.add(string) ;
			}
			
			for ( int i = 0 ; i < productListDict.size() ; i++ )
			{
				Dictionary<String, String> dictionaryForProductInfo = new Hashtable<String, String>() ;
				dictionaryForProductInfo = productListDict.get(keys.get(i));
				
				name.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.name));
				description.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.description));
				small_image.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.small_image));
				is_salable.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.is_salable));
				qty.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.qty));
				status.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.status));
				id.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.id));
				sku.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.sku));
				price.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.price));
				image.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.image));
				
				postionList.add(Integer.toString(i));
			}
			
			String [] imageUrls = image.toArray(new String[image.size()]);
			
			int sendScreenHeight = Integer.parseInt(MySharedPref.getInstance(ProductList_Grid.this).getString(
					"sendScreenHeight_SPLASHSCREEN"));

			imageAdapterGlobal = new ImageAdapter(postionList,imageUrls,description,id,qty,sku,name,price,small_image,is_salable,status,sendScreenHeight) ;
			if ( ((GridView) gridView).getVisibility() == View.GONE || ((GridView) gridView).getVisibility() == View.VISIBLE )
			{
				gridview_temp.setVisibility(View.GONE);
				gridView.setVisibility(View.VISIBLE);
			}
			
			((GridView) gridView).setAdapter(imageAdapterGlobal);

			gridView.setOnScrollListener(new OnScrollListener() {
				
				@Override
				public void onScrollStateChanged(AbsListView view, int scrollState) {
					// TODO Auto-generated method stub
				    if (scrollState == SCROLL_STATE_IDLE) 
				    {
				    	Log.e("onScrollStateChanged", "Scrolling stopped");
				    	
						if (linear_BottomLayout == null)
							linear_BottomLayout = (LinearLayout) findViewById(R.id.linear_BottomLayout);

						linear_BottomLayout.setVisibility(View.VISIBLE);
						
						Animation animContentDown = AnimationUtils.loadAnimation(
								getApplicationContext(), R.anim.slide_up_service);
						animContentDown.setAnimationListener(new AnimationListener() {
							@Override
							public void onAnimationStart(Animation animation) {
							}

							@Override
							public void onAnimationRepeat(Animation animation) {
							}

							@Override
							public void onAnimationEnd(Animation animation) {
							}
						});

						linear_BottomLayout.startAnimation(animContentDown);
				    }
				    else
				    {
						Animation animContentDown = AnimationUtils.loadAnimation(
								getApplicationContext(), R.anim.slide_down_service);
						animContentDown.setAnimationListener(new AnimationListener() {
							@Override
							public void onAnimationStart(Animation animation) {
							}

							@Override
							public void onAnimationRepeat(Animation animation) {
							}

							@Override
							public void onAnimationEnd(Animation animation) {
								if (linear_BottomLayout != null)
									linear_BottomLayout.setVisibility(View.GONE);
							}
						});						
						linear_BottomLayout.startAnimation(animContentDown);
				    }
				}
				
				@Override
				public void onScroll(AbsListView view, int firstVisibleItem,
						int visibleItemCount, int totalItemCount) {
				}
			});

			//ProductList_Adapter adapter = new ProductList_Adapter(productListDict, this);
			//productList.setAdapter(adapter) ;
		}
	}


	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long idLong ) {
		// TODO Auto-generated method stub
		//onClickGridElement(position) ;
	}
	
	public void onClickGridElement( int position , String productId )
	{
		ArrayList<CharSequence> name = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> description  = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> small_image = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> is_salable = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> qty = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> status = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> id = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> sku = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> price = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> image = new ArrayList<CharSequence>() ;
		
		Enumeration<String> enumeration = productListDict.keys() ;
		ArrayList<String> keys = new ArrayList<String>() ;
		while (enumeration.hasMoreElements()) 
		{
			String string = (String) enumeration.nextElement();
			keys.add(string) ;
		}

		int exactElementPosition = -1 ;
		
		for ( int i = 0 ; i < productListDict.size() ; i++ )
		{
			Dictionary<String, String> dictionaryForProductInfo = new Hashtable<String, String>() ;
			dictionaryForProductInfo = productListDict.get(keys.get(i));
			
			if ( productId.equals(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.id)))
			{
				exactElementPosition = i ;
			}
			
			name.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.name));
			description.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.description));
			small_image.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.small_image));
			is_salable.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.is_salable));
			qty.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.qty));
			status.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.status));
			id.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.id));
			sku.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.sku));
			price.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.price));
			image.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.image));
		}
		
		Intent intent = new Intent  (ProductList_Grid.this,ProductDescription.class);
		
		intent.putExtra(HTTP_RESPONSE_Products.name, name.get(exactElementPosition));
		intent.putExtra(HTTP_RESPONSE_Products.description, description.get(exactElementPosition));
		intent.putExtra(HTTP_RESPONSE_Products.small_image, small_image.get(exactElementPosition));
		intent.putExtra(HTTP_RESPONSE_Products.is_salable, is_salable.get(exactElementPosition));
		intent.putExtra(HTTP_RESPONSE_Products.qty, qty.get(exactElementPosition));
		intent.putExtra(HTTP_RESPONSE_Products.status, status.get(exactElementPosition));
		intent.putExtra(HTTP_RESPONSE_Products.id, id.get(exactElementPosition));
		intent.putExtra(HTTP_RESPONSE_Products.sku, sku.get(exactElementPosition));
		intent.putExtra(HTTP_RESPONSE_Products.price, price.get(exactElementPosition));
		intent.putExtra(HTTP_RESPONSE_Products.image, image.get(exactElementPosition));
		
		intent.putStringArrayListExtra("navigationDrawerList", navigationDrawerList);
		//HTTP_RESPONSE_Search.is_in_stock
		ApplicationClass.dictionaryWholeList = productListDict ;
		
		startActivity(intent) ;	
		
		hideKeyboardEveryTime();
	}
	
	public class ImageAdapter extends BaseAdapter {
		ArrayList<CharSequence> names  ;
		//boolean [][] stateOfWishList ;
		ArrayList<CharSequence> price ;
		ArrayList<CharSequence> productId ;
		ArrayList<CharSequence> productQty ;
		ArrayList<CharSequence> productSku ;
		ArrayList<CharSequence> description ;
		ArrayList<CharSequence> small_image;
		ArrayList<CharSequence> is_salable;
		ArrayList<CharSequence> status;
		ArrayList<String> positonsList ;
		String [] imageUrls ;
		int sendScreenHeight ;
		
		public ImageAdapter ( 
								ArrayList<String> positonsList ,
								String [] imageURLS , 
								ArrayList<CharSequence> description , 
								ArrayList<CharSequence> id,
								ArrayList<CharSequence> qty,
								ArrayList<CharSequence> sku,
								ArrayList<CharSequence> names , 
								ArrayList<CharSequence> price , 
								ArrayList<CharSequence> small_image,
								ArrayList<CharSequence> is_salable,
								ArrayList<CharSequence> status,
								int sendScreenHeight 
							)
		{
			this.positonsList = positonsList ;
			this.price = price ;
			this.names = names ;
			this.productId = id ;
			this.productQty = qty ;
			this.productSku = sku ;
			this.description = description ;
			this.imageUrls = imageURLS ;
			this.small_image = small_image ;
			this.is_salable = is_salable ;
			this.status = status ;
			this.sendScreenHeight = sendScreenHeight ;
			
			for ( int i = 0 ; i < price.size() ; i++ )
			{
				mLogs.showTempLog(i+" : "+price.get(i)+"");
			}
		}
		
		@Override
		public int getCount() {
			return imageUrls.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			View view = convertView;
			if (view == null) {
				view = getLayoutInflater().inflate(R.layout.adapter_grid_productlisting, parent, false);
				holder = new ViewHolder();
				assert view != null;
				holder.imageView = (ImageView) view.findViewById(R.id.image);

				holder.txt_productname = (TextView ) view.findViewById(R.id.txt_productname);	
				holder.txt_productPrice = (TextView ) view.findViewById(R.id.txt_productPrice);	
				
				holder.chk_wishlist = (CheckBox ) view.findViewById(R.id.chk_wishlist);	
				
				holder.img_share = (ImageView ) view.findViewById(R.id.img_share);				
				
				TypeFace_TextView.setTypeFace(ProductList_Grid.this, holder.txt_productname);
				holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);
				view.setTag(holder);
			} 
			else 
			{
				holder = (ViewHolder) view.getTag();
			}
			
			holder.txt_productname.setText(names.get(position));
			holder.txt_productPrice.setText("SR "+price.get(position));
			
			holder.img_share.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					saveBitmapIntoStorage(((BitmapDrawable)holder.imageView.getDrawable()).getBitmap());
					//DialogForSharing_ProductDescription dialogProductDescription = new DialogForSharing_ProductDescription (this,);

					CharSequence skuSend = productSku.get(position);
					CharSequence product_idSend = productId.get(position);
					CharSequence qtySend = productQty.get(position);
					CharSequence store_idSend = MySharedPref.getInstance(ProductList_Grid.this).getString(Con_SpalshScreen.GlobalStored_StoreId);
					
					Dictionary<String, CharSequence> dictionaryToSend = new Hashtable<String, CharSequence>();
					dictionaryToSend.put("skuSend", skuSend);
					dictionaryToSend.put("product_idSend", product_idSend);
					dictionaryToSend.put("qtySend", qtySend);
					dictionaryToSend.put("store_idSend", store_idSend);
					
					productDescription = description.get(position).toString();
					productName = names.get(position).toString();
					
					DialogAsFlipkartSharing dialogAsFlipKart = new DialogAsFlipkartSharing
							(		
									"ProductList_GRID",
									ProductList_Grid.this,
									dictionaryToSend									
									);
					dialogAsFlipKart.setCancelable(true);
					dialogAsFlipKart.show() ;
				}
			});
			
			holder.imageView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					onClickGridElement( position , productId.get(position).toString()) ;
				}
			});

			imageLoader.displayImage(imageUrls[position], holder.imageView, options, new SimpleImageLoadingListener() {
										 @Override
										 public void onLoadingStarted(String imageUri, View view) {
											 holder.progressBar.setProgress(0);
											 holder.progressBar.setVisibility(View.VISIBLE);
										 }

										 @Override
										 public void onLoadingFailed(String imageUri, View view,
												 FailReason failReason) {
											 holder.progressBar.setVisibility(View.GONE);
										 }

										 @Override
										 public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
											 holder.progressBar.setVisibility(View.GONE);
										 }
									 }, new ImageLoadingProgressListener() {
										 @Override
										 public void onProgressUpdate(String imageUri, View view, int current,
												 int total) {
											 holder.progressBar.setProgress(Math.round(100.0f * current / total));
										 }
									 }
			);
			
			view.setMinimumHeight(sendScreenHeight/2);
			
			return view;
		}

		class ViewHolder {
			ImageView imageView;
			ProgressBar progressBar;
			TextView txt_productname ;
			TextView txt_productPrice ;
			CheckBox chk_wishlist ;
			ImageView img_share ;
		}
	}
	
	public int getScreenHeight ()
	{
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();		
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		
		return height ;
	}
	
	public int getScreenWidth ()
	{
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();		
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		
		return width ;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_Products_Sort:
			DialogSortButton cdd = new DialogSortButton(this,productListDict,"ProductList_Grid");
			cdd.show();
			break;
			
		case R.id.btn_Products_Filter:
			DialogFilterBy dialogFilterBy = new DialogFilterBy(productListDict,this,4,400,"ProductList_Grid");
			dialogFilterBy.show();
			break;
		
		default:
			break;
		}
	}

	@Override
	public void sortAdapter(int sortitValue) {
		// TODO Auto-generated method stub
		
		Enumeration<String> enumeration = productListDict.keys() ;
		ArrayList<String> keys = new ArrayList<String>() ;
		ArrayList<String> price = new ArrayList<String>() ;
		ArrayList<String> name = new ArrayList<String>() ;
		ArrayList<String> sku = new ArrayList<String>() ;
		
		while (enumeration.hasMoreElements()) 
		{
			String string = (String) enumeration.nextElement();
			keys.add(string) ;
		}
		
		for ( int i = 0 ; i < productListDict.size() ; i++ )
		{
			Dictionary<String, String> dictionaryForProductInfo = new Hashtable<String, String>() ;
			dictionaryForProductInfo = productListDict.get(keys.get(i));
			
			name.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.name));
			sku.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.sku));
			price.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.price));
		}
		
		ArrayList<String> sortedArray = null ;
		switch (sortitValue) {
		case 1:
			mToasts.showTempToast("1");
			
			sortedArray = sortAccordingToPrice(price,1);
			
			break;
		case 2:
			mToasts.showTempToast("2");

			sortedArray = sortAccordingToPrice(price,2);
			
			break;
		case 3:
			mToasts.showTempToast("3");
			
			sortedArray = sortAccordingToPrice(price,3);
			
			break;
		case 4:
			mToasts.showTempToast("4");
			
			sortedArray = sortAccordingToPrice(price,4);
			
			break;
		case 5:
			mToasts.showTempToast("5");
			sortedArray = sortAccordingToPrice(name,5);
			
			break;
		case 6:
			mToasts.showTempToast("6");
			sortedArray = sortAccordingToPrice(sku,6);
			break;
		default:
			break;
		}
		
//		for ( int i = 0 ; i < sortedArray.size() ; i++ )
//		{
//			mLogs.showTempLog("Flow : "+sortedArray.get(i));
//		}
		
		ArrayList<CharSequence> nameSend = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> descriptionSend  = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> small_imageSend = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> is_salableSend = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> qtySend = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> statusSend = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> idSend = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> skuSend = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> priceSend = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> imageSend = new ArrayList<CharSequence>() ;
		
		Enumeration<String> enumerationSend = productListDict.keys() ;
		ArrayList<String> keysSend = new ArrayList<String>() ;
		
		while (enumerationSend.hasMoreElements()) {
			String string = (String) enumerationSend.nextElement();
			keysSend.add(string) ;
		}
			
		for ( int i = 0 ; i < productListDict.size() ; i++ )
		{
			Dictionary<String, String> dictionaryForProductInfo = new Hashtable<String, String>() ;
			dictionaryForProductInfo = productListDict.get(keys.get(i));
				
			nameSend.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.name));
			descriptionSend.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.description));
			small_imageSend.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.small_image));
			is_salableSend.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.is_salable));
			qtySend.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.qty));
			statusSend.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.status));
			idSend.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.id));
			skuSend.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.sku));
			priceSend.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.price));
			imageSend.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.image));
		}
		
		ArrayList<CharSequence> nameSend_ = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> descriptionSend_  = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> small_imageSend_ = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> is_salableSend_ = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> qtySend_ = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> statusSend_ = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> idSend_ = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> skuSend_ = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> priceSend_ = new ArrayList<CharSequence>() ;
		ArrayList<CharSequence> imageSend_ = new ArrayList<CharSequence>() ;
		
		for ( int i = 0 ; i < sortedArray.size() ; i++ )
		{
			nameSend_.add(nameSend.get(Integer.parseInt(sortedArray.get(i))));
			descriptionSend_.add(descriptionSend.get(Integer.parseInt(sortedArray.get(i))));
			small_imageSend_.add(small_imageSend.get(Integer.parseInt(sortedArray.get(i))));
			is_salableSend_.add(is_salableSend.get(Integer.parseInt(sortedArray.get(i))));
			qtySend_.add(qtySend.get(Integer.parseInt(sortedArray.get(i))));
			statusSend_.add(statusSend.get(Integer.parseInt(sortedArray.get(i))));
			idSend_.add(idSend.get(Integer.parseInt(sortedArray.get(i))));
			skuSend_.add(skuSend.get(Integer.parseInt(sortedArray.get(i))));
			priceSend_.add(priceSend.get(Integer.parseInt(sortedArray.get(i))));
			imageSend_.add(imageSend.get(Integer.parseInt(sortedArray.get(i))));
		}
		
		String [] imageURLS = imageSend_.toArray(new String[imageSend.size()]);
		
		((GridView) gridView).setAdapter(null);
		
		int sendScreenHeight = Integer.parseInt(MySharedPref.getInstance(ProductList_Grid.this).getString(
				"sendScreenHeight_SPLASHSCREEN"));
		
		ImageAdapter imageAdapter = new ImageAdapter(sortedArray, imageURLS, descriptionSend_, idSend_, qtySend_, skuSend_, nameSend_, priceSend_, small_imageSend_, is_salableSend_, statusSend_, sendScreenHeight);
		((GridView) gridView).setAdapter(imageAdapter);
		
		mLogs.showTempLog("View changed") ;
	}


	
	int linear_BottomLayout_height ;
	
	@Override
	public void onWindowFocusChanged (boolean hasFocus) {
	        // the height will be set at this point
		if ( linear_BottomLayout != null )
		{
			linear_BottomLayout_height = linear_BottomLayout.getMeasuredHeight(); 
	        Log.d("linear_BottomLayout bottom=",linear_BottomLayout+"");
		}
	}
	
	public void onDialogAsFlipKartSharing_ShareClicked ()
	{
		//mToasts.showTempToast("onDialogAsFlipKartSharing_ShareClicked clicked") ;
		
		DialogForSharing sharingDialog = new DialogForSharing(this,productName,productDescription,"Sharing_PL");
		sharingDialog.setCancelable(true);
		sharingDialog.show();
	}
	
	String productDescription ;
	String productName ;
}

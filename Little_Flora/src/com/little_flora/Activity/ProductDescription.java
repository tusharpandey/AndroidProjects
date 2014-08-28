package com.little_flora.Activity;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.little_flora.R;
import com.little_flora.Activity.Utils.CheckNetwork;
import com.little_flora.Activity.Utils.HTTP_POST_urls;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_AddToCart;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_Products;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_Search;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_User;
import com.little_flora.Activity.Utils.LF_Constants.Con_SpalshScreen;
import com.little_flora.Activity.Utils.LF_Constants.Con_UserDetails;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.Activity.Utils.Toasts_;
import com.little_flora.Activity.Utils.TypeFace_TextView;
import com.little_flora.Dialogs.DialogBuyNow;
import com.little_flora.Dialogs.DialogForSharing;
import com.little_flora.Dialogs.DialogZoomImage;
import com.little_flora.ProductsSync.AddToCartAsync;
import com.little_flora.User.Login;
import com.little_flora.User.OrderDetails;
import com.little_flora.User.Callback.CartCreateCustomerCallBack;
import com.little_flora.Utils.ApplicationClass;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
//import com.little_flora.Dialogs.DialogZoomImage;
//Dialog
public class ProductDescription extends BaseActivity implements OnClickListener , CartCreateCustomerCallBack {
	private ImageView img_productImage;
	private TextView txt_productName;
	private TextView txt_productPrice;
	private TextView txt_productCode;
	private Button btn_buy;
	private TextView txt_productDescription;
	private Toasts_ mToasts;
	private Logs_ mLogs;
	private AlertDialog dialog;
	private String[] stringArray;
	private int year;
	private int month;
	private int day;
	private TextView txt_pageTitle;
	private ImageView img_addtocart;
	private ImageView img_share;
	private ImageView img_email;
	private TextView txt_availableinStock;
	private UiLifecycleHelper uiHelper;
	private String facebookFeedUrl;
	private String sku;
	private String qty;
	private String productId;
	private ProgressBar progress;
	private DisplayImageOptions options;
	private ViewPager pager;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
//	private CirclePageIndicator mIndicator;
	private SlidingPaneLayout pane;
	private ListView lst_slideProductList;
	private ArrayList<String> navigationDrawerList;
	
	String [] images ;
	private String image;
	private int maxheight;
	private ImageView img_arrowSlider;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_productdescription);
		

		mToasts = new Toasts_(this);
		mLogs = new Logs_("Product Description");

		//hideTheKeyBoard();
		//img_arrowSlider
		pane = (SlidingPaneLayout) findViewById(R.id.sp);
//		pane.setPanelSlideListener(new PaneListener());
		
		pane.setShadowResource(R.drawable.slideoane_drawerbackground);
		pane.setSliderFadeColor(Color.parseColor("#00000000"));		
		
		//Dialog
		
		initView();
		setDatainViews();
		
		navigationDrawerList = getIntent().getStringArrayListExtra("navigationDrawerList");

		listToSlideDrawerNavigation = navigationDrawerList ;
        
		String [] navigationArray = navigationDrawerList.toArray(new String[navigationDrawerList.size()]);

		navigationDrawerSetup(navigationArray);
		
		uiHelper = new UiLifecycleHelper(this, null);
		uiHelper.onCreate(savedInstanceState);
		
		options = new DisplayImageOptions.Builder()
		.showImageForEmptyUri(R.drawable.iclauncher)
		.showImageOnFail(R.drawable.iclauncher)
		.resetViewBeforeLoading(true)
		.cacheOnDisk(true)
		.imageScaleType(ImageScaleType.EXACTLY)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.considerExifParams(true)
		.displayer(new FadeInBitmapDisplayer(300))
		.build();
		
		images = new String [] {getIntent().getStringExtra(HTTP_RESPONSE_Products.image)} ;
		
//		pager = (ViewPager) findViewById(R.id.pager);
//		pager.setAdapter(new ImagePagerAdapter(images));
//		
//		mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
//		mIndicator.setViewPager(pager);
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		MySharedPref.getInstance(this).setString("MyCurrentActivityOnPage","ProductDescription") ;

	}

	public void initView() 
	{
		img_productImage = (ImageView) findViewById(R.id.img_productImage);
		
		String strWidth = MySharedPref.getInstance(ProductDescription.this).getString(
				"sendScreenWidth_SPLASHSCREEN");
		
		int width = Integer.parseInt(strWidth) ;

		FrameLayout.LayoutParams parms = new FrameLayout.LayoutParams(width,width);
		img_productImage.setLayoutParams(parms);					
		
		img_productImage.setOnClickListener(this) ;

		txt_productName = (TextView) findViewById(R.id.txt_productName);
		TypeFace_TextView.setTypeFace(this, txt_productName);

		txt_productPrice = (TextView) findViewById(R.id.txt_productPrice);
		TypeFace_TextView.setTypeFace(this, txt_productPrice);

		txt_productCode = (TextView) findViewById(R.id.txt_productCode);
		TypeFace_TextView.setTypeFace(this, txt_productCode);

		String language = MySharedPref.getInstance(this).getString(
				Con_SpalshScreen.GlobalStoredLanguageForApp);


		btn_buy = (Button) findViewById(R.id.btn_buy);
		TypeFace_TextView.setTypeFace(this, btn_buy);
		btn_buy.setOnClickListener(this) ;

		txt_productDescription = (TextView) findViewById(R.id.txt_productDescription);
		TypeFace_TextView.setTypeFace(this, txt_productDescription);
		
		img_addtocart = ( ImageView ) findViewById(R.id.img_addtocart);
		img_addtocart.setOnClickListener(this);
		
		
		img_share = ( ImageView ) findViewById(R.id.img_share);
		img_share.setOnClickListener(this);
		
		img_email = ( ImageView ) findViewById(R.id.img_email);
		img_email.setOnClickListener(this);
		
		txt_availableinStock = ( TextView ) findViewById(R.id.txt_availableinStock) ;
		TypeFace_TextView.setTypeFace(this, txt_availableinStock);
		
		progress = ( ProgressBar ) findViewById(R.id.progress) ;
		
		lst_slideProductList = ( ListView ) findViewById(R.id.lst_slideProductList) ;
		
		ImageAdapter adapter = new ImageAdapter(ApplicationClass.dictionaryWholeList);
		
		lst_slideProductList.setAdapter(adapter) ;
		
		img_arrowSlider = ( ImageView ) findViewById(R.id.img_arrowSlider) ;
		img_arrowSlider.setOnClickListener(this) ;		
	}

	public void setBuyButtonGravity(int gravity) {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
		params.gravity = gravity ;
		params.topMargin = (int) convertDpToPixel(8, this);
		//btn_buy.setLayoutParams(params);
	}

	public void setDatainViews() {
		String description = getIntent().getStringExtra(
				HTTP_RESPONSE_Products.description);
		String name = getIntent().getStringExtra(HTTP_RESPONSE_Products.name);
		String price = getIntent().getStringExtra(HTTP_RESPONSE_Products.price);
		sku = getIntent().getStringExtra(HTTP_RESPONSE_Products.sku);
		String isSalable = getIntent().getStringExtra(HTTP_RESPONSE_Products.is_salable);
		qty = getIntent().getStringExtra(HTTP_RESPONSE_Products.qty);
		productId = getIntent().getStringExtra(HTTP_RESPONSE_Products.id);
		
		String isInStock = getIntent().getStringExtra(HTTP_RESPONSE_Search.is_in_stock);
		if ( isInStock != null )
		{
			if ( isInStock.equals("1") )
			{
				txt_availableinStock.setText(getResources().getString(R.string.ProductsDescription_isInStock));
			}
			else if ( isInStock.equals("0") )
			{
				txt_availableinStock.setText(getResources().getString(R.string.ProductsDescription_outOfStock));
			}
		}
		
		image = getIntent().getStringExtra(HTTP_RESPONSE_Products.image);
		
		facebookFeedUrl = image ;

		txt_productDescription.setText(description);
		txt_productName.setText(name);
		txt_productPrice.setText(price);
		txt_productCode.setText(sku);
		
/*		if ( Integer.parseInt(isSalable) != null && Integer.parseInt(isSalable) == 1 )
		{
			txt_availableinStock.setText(getResources().getString(R.string.ProductsDescription_isInStock));
		}
		else
		{
			txt_availableinStock.setText(getResources().getString(R.string.ProductsDescription_outOfStock));
		}
*/		
		downloadImage(image) ;
	}
	
	public void downloadImage(String image)
	{
		DownloadImageTask asyncObject = new DownloadImageTask(img_productImage);
		asyncObject.execute(image);
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> 
	{
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progress.setVisibility(View.VISIBLE);
			
			Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.blankimage);
			bmImage.setImageBitmap(image);
			//image
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			image = urldisplay ;
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				mLogs.showTempLog(e.getMessage().toString());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			if ( progress != null )
				progress.setVisibility(View.INVISIBLE);
						
			
			String strWidth = MySharedPref.getInstance(ProductDescription.this).getString(
					"sendScreenWidth_SPLASHSCREEN");
			
			int width = Integer.parseInt(strWidth) ;


			FrameLayout.LayoutParams parms = new FrameLayout.LayoutParams(width,width);
			bmImage.setLayoutParams(parms);					
					
			bmImage.setImageBitmap(result);
			
			saveBitmapIntoStorage(result);
		}
	}
	//Dialog
	
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		
		case R.id.img_arrowSlider:
			if ( pane != null )
			{
				if ( ! pane.isOpen() )
				{
					pane.openPane();
				}
				else
				{
					pane.closePane();
				}
			}
			break;
		
		case R.id.img_productImage:
			
			BitmapDrawable bitmapDrawable = ((BitmapDrawable) img_productImage.getDrawable());
			Bitmap bitmap = bitmapDrawable .getBitmap();
			
			String [] imageArray = new String [] { image } ;

			DialogZoomImage dilaogZoomImage = new DialogZoomImage(imageArray,"ProductDescription", ProductDescription.this, bitmap);
			dilaogZoomImage.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.lightblacktheme)));			
			dilaogZoomImage.setCancelable(true);
			dilaogZoomImage.show();
			
			break ;
		case R.id.btn_buy:
			
			String productId_ = getIntent().getStringExtra(HTTP_RESPONSE_Products.id);

			Dictionary<String, String> dictionary = new Hashtable<String, String>() ;
			dictionary.put("sku", sku);
			dictionary.put("productId", productId_);
			
			if ( MySharedPref.getInstance(this).getString(Con_UserDetails.isUserLogined).equals("true") )
			{
				String firstName = MySharedPref.getInstance(this).getString(HTTP_RESPONSE_User.UserLogined_+"firstname");
				String lastname = MySharedPref.getInstance(this).getString(HTTP_RESPONSE_User.UserLogined_+"lastname");
				String email = MySharedPref.getInstance(this).getString(HTTP_RESPONSE_User.UserLogined_+"email");
				String website_id = MySharedPref.getInstance(this).getString(Con_SpalshScreen.GlobalStored_WebsiteId);
				String store_id = MySharedPref.getInstance(this).getString(Con_SpalshScreen.GlobalStored_StoreId);
				String quoteId = MySharedPref.getInstance(this).getString(HTTP_RESPONSE_AddToCart.AddToCart_+"buynow_quoteId");
				String mode = MySharedPref.getInstance(this).getString(Con_SpalshScreen.GlobalStored_USERtype) ;
				
				String urlPost = "" ;
				
				String urlPre = HTTP_POST_urls.addToCart +
						"sku="+dictionary.get("sku")+
						"&product_id="+dictionary.get("productId")+
						"&qty="+1+
						"&store_id="+store_id;	
				
				String customer_id = MySharedPref.getInstance(this).getString(
						Con_UserDetails.userId) ;
				
				Dictionary<String, String> dictionarySend = new Hashtable<String, String>() ;
				dictionarySend.put("firstname", firstName);
				dictionarySend.put("lastname", lastname);
				dictionarySend.put("website_id", website_id);
				dictionarySend.put("mode", mode);
				dictionarySend.put("email", email);
				dictionarySend.put("sku", sku);
				dictionarySend.put("qty", qty);
				dictionarySend.put("customer_id", customer_id);
				
				String [] data = new String [1] ;
				String loadingText = getResources().getString(R.string.loading_cartCustomerAdd) ;
				
				Intent intentOrderDetails = new Intent (this,OrderDetails.class);
				intentOrderDetails.putStringArrayListExtra("navigationDrawerList",
						listToSlideDrawerNavigation);
				startActivity(intentOrderDetails);
				
//				CartCustomerAddAsync asyncCartCustomerAdd = new CartCustomerAddAsync("customer",dictionarySend,urlPost, urlPre, this, loadingText) ;
//				asyncCartCustomerAdd.execute("");
			}
			else
			{
				DialogBuyNow dialogBuyNow = new DialogBuyNow(this, dictionary);
				dialogBuyNow.setCancelable(true);
				dialogBuyNow.show();
			}
			break;
		case R.id.img_email:
			shareEmailWithHtml(null);
			break;
		case R.id.img_share:
			
//			saveBitmapIntoStorage(((BitmapDrawable)img_productImage.getDrawable()).getBitmap());
			
			DialogForSharing sharingDialog = new DialogForSharing(this,txt_productName.getText().toString(),txt_productDescription.getText().toString(),"Sharing_PD");
			sharingDialog.setCancelable(true);
			sharingDialog.show();
			break;
			
		case R.id.img_addtocart:
			
			if ( CheckNetwork.isNetworkAvailable(this) )
			{
				String loadingText = getResources().getString(R.string.loading_addingIntoTheCart) ;
				String [] data = new String [1] ;
				String store_id = MySharedPref.getInstance(ProductDescription.this).getString(Con_SpalshScreen.GlobalStored_StoreId);

//				String url = HTTP_POST_urls.addToCart +
//						"sku="+sku+
//						"&product_id="+productId+
//						"&qty="+1+
//						"&store_id="+store_id;
				
				String quoteId = MySharedPref.getInstance(this).getString(HTTP_RESPONSE_AddToCart.AddToCart_+HTTP_RESPONSE_AddToCart.quoteid);
				String url = "" ;
				
				if ( quoteId != null && quoteId.length() > 0 )
				{
					url = HTTP_POST_urls.addToCart_secondtime +
							"sku="+sku+
							"&qty="+1+
							"&quoteid="+quoteId;
				}
				else
				{
					url = HTTP_POST_urls.addToCart +
							"sku="+sku+
							"&product_id="+productId+
							"&qty="+1+
							"&store_id="+store_id;					
				}
				
				mLogs.showImpLog(url) ;
				
				AddToCartAsync syncAddToCart = new AddToCartAsync(url, data, this, loadingText,"ProductDescription");
				syncAddToCart.execute("");
			}
			else
			{
				mToasts.showImpToast(getResources().getString(R.string.networkNotPresent)) ;
			}
			
			break;
		default:
			break;
		}

	}
	//Dialog
	public void shareEmailWithHtml(ImageView image) {
		
//		saveBitmapIntoStorage(((BitmapDrawable)image.getDrawable()).getBitmap());
		
		File myFile = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "test.jpg");

		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_TEXT, txt_productDescription.getText().toString().trim());
		i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(myFile));
		i.putExtra(Intent.EXTRA_SUBJECT, txt_productName.getText().toString().trim());
		
		try {
		    this.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			mToasts.showImpToast("There are no email clients installed.");
		}
		
	}
	
	public void newFaceBookImplementation()
	{
		Session.openActiveSession(this, true, new Session.StatusCallback() {
			@Override
			public void call(Session session, SessionState state,
					Exception exception) {
				// TODO Auto-generated method stub
				if (session.isOpened()) {

					// make request to the /me API
					Request.newMeRequest(session,
							new Request.GraphUserCallback() 
					{
								@Override
								public void onCompleted(GraphUser user,
										Response response) {
									// TODO Auto-generated method stub
									publishFeedDialog(txt_productName.getText().toString(),txt_productDescription.getText().toString(),facebookFeedUrl);
								}
					}).executeAsync();
				}
			}
		});
	}
	
	private void publishFeedDialog(String name , String description , String url) {
		Bundle params = new Bundle();
		params.putString("name", name);
		//params.putString("description",method);
		params.putString("description",description+"\n\n"
				/*MySharedPref.getInstance(this).getString("googlePlayLinkOfapp")+"\n\n"+MySharedPref.getInstance(this).getString("appStoreLinkOfapp")*/);
		
		params.putString("link", url);
		params.putString("picture",url);

		WebDialog feedDialog = (new WebDialog.FeedDialogBuilder(ProductDescription.this,
				Session.getActiveSession(), params)).setOnCompleteListener(
				new OnCompleteListener() {


					@Override
					public void onComplete(Bundle values,
							FacebookException error) {
						// TODO Auto-generated method stub
							// TODO Auto-generated method stub

							if (error == null) {
								// When the story is posted, echo the success
								// and the post Id.
								final String postId = values.getString("post_id");
								if (postId != null) {
									Toast.makeText(ProductDescription.this,
											"Posted story"/* + postId*/,
											Toast.LENGTH_SHORT).show();
								} else {
									// User clicked the Cancel button
									Toast.makeText(
											ProductDescription.this.getApplicationContext(),
											"Publish cancelled", Toast.LENGTH_SHORT)
											.show();
								}
							} else if (error instanceof FacebookOperationCanceledException) {
								// User clicked the "x" button
								Toast.makeText(
										ProductDescription.this.getApplicationContext(),
										"Publish cancelled", Toast.LENGTH_SHORT)
										.show();
							} else {
								// Generic, ex: network error
								Toast.makeText(
										ProductDescription.this.getApplicationContext(),
										"Error posting story", Toast.LENGTH_SHORT)
										.show();
							}
						
												
					}}).build();
		feedDialog.show();
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);

		if (Session.getActiveSession().isOpened()) {
			uiHelper.onActivityResult(requestCode, resultCode, data,
					new FacebookDialog.Callback() {
						@Override
						public void onError(
								FacebookDialog.PendingCall pendingCall,
								Exception error, Bundle data) {
							Log.e("Activity", String.format("Error: %s",
									error.toString()));
						}

						@Override
						public void onComplete(
								FacebookDialog.PendingCall pendingCall,
								Bundle data) {
							Log.i("Activity", "Success!");
						}
					});
		}
	}

	@Override
	public void responseCartCreateCustomer(String message, String status,
			String resultCustomerSet) {
		if ( message != null && status != null )
		{
			if ( status.equals("1") )
			{
				mToasts.showImpToast("start Address Activity") ;
				
				if (CheckNetwork.isNetworkAvailable(this)) {
					Intent intentWishlist = new Intent(this,
							OrderDetails.class);
					intentWishlist.putStringArrayListExtra("navigationDrawerList",
							listToSlideDrawerNavigation);
					startActivity(intentWishlist);
					
					hideKeyboardEveryTime();
				} else {
					Toast.makeText(this,
							getResources().getString(R.string.networkNotPresent),
							Toast.LENGTH_LONG).show();

				}
			}	
		}
//		else
//		{
//			Intent intentWishlist = new Intent(this,
//					CustomerAddressList.class);
//			intentWishlist.putStringArrayListExtra("navigationDrawerList",
//					listToSlideDrawerNavigation);
//			startActivity(intentWishlist);
//		}
	}
	
/*	private class ImagePagerAdapter extends PagerAdapter {

		private String[] images;
		private LayoutInflater inflater;

		ImagePagerAdapter(String[] images) {
			this.images = images;
			inflater = getLayoutInflater();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public int getCount() {
			return images.length;
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
			assert imageLayout != null;
			final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
			final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
			
			imageView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) 
				{
					BitmapDrawable bitmapDrawable = ((BitmapDrawable) imageView.getDrawable());
					Bitmap bitmap = bitmapDrawable .getBitmap();

					DialogZoomImage dilaogZoomImage = new DialogZoomImage("ProductDescription", ProductDescription.this, bitmap);
					dilaogZoomImage.setCancelable(true);
					dilaogZoomImage.show();
				}
			});

			imageLoader.displayImage(images[position], imageView, options, new SimpleImageLoadingListener() {
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					spinner.setVisibility(View.VISIBLE);
				}

				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					String message = null;
					switch (failReason.getType()) {
						case IO_ERROR:
							message = "Input/Output error";
							break;
						case DECODING_ERROR:
							message = "Image can't be decoded";
							break;
						case NETWORK_DENIED:
							message = "Downloads are denied";
							break;
						case OUT_OF_MEMORY:
							message = "Out Of Memory error";
							break;
						case UNKNOWN:
							message = "Unknown error";
							break;
					}
					Toast.makeText(ProductDescription.this, message, Toast.LENGTH_SHORT).show();

					spinner.setVisibility(View.GONE);
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					spinner.setVisibility(View.GONE);
				}
			});

			view.addView(imageLayout, 0);
			return imageLayout;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}
	}*/
	
	public class ImageAdapter extends BaseAdapter {
		
		String [] imageUrls ;
		Dictionary<String, Dictionary<String, String>> fullProducts ;

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

		public ImageAdapter (Dictionary<String, Dictionary<String, String>> fullProducts)
		{
			this.fullProducts = fullProducts ;
			
			Enumeration<String> enumeration = fullProducts.keys() ;
			ArrayList<String> keys = new ArrayList<String>() ;
			while (enumeration.hasMoreElements()) 
			{
				String string = (String) enumeration.nextElement();
				keys.add(string) ;
			}

			for ( int i = 0 ; i < fullProducts.size() ; i++ )
			{
				Dictionary<String, String> dictionaryForProductInfo = new Hashtable<String, String>() ;
				dictionaryForProductInfo = fullProducts.get(keys.get(i));
				
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
			
			imageUrls = image.toArray(new String[image.size()]);
			
			for( int i = 0 ; i < name.size() ; i++ )
			{
				Log.e("Names", name.get(i).toString());
			}

		}
		
		@Override
		public int getCount() {
			return fullProducts.size();
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
				view = getLayoutInflater().inflate(R.layout.adapter_slideproductdescription, parent, false);
				holder = new ViewHolder();
				assert view != null;
				holder.imageView = (ImageView) view.findViewById(R.id.img_productImage);
				
				holder.txt_productname = (TextView) view.findViewById(R.id.txt_productName);

				holder.txt_productPrice = (TextView) view.findViewById(R.id.txt_productPrice);
				
				holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);

				view.setTag(holder);
			} 
			else 
			{
				holder = (ViewHolder) view.getTag();
			}
			
			holder.txt_productname.setText(name.get(position).toString());
			holder.txt_productPrice.setText(price.get(position).toString());

			holder.imageView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
//					Toast.makeText(ProductDescription.this, "Clicked", Toast.LENGTH_SHORT).show() ;
					
					onClickGridElement(position) ;
				}
			});
			
			//DialogZoomImage

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
			
			
			return view;
		}

		class ViewHolder {
			ImageView imageView;
			ProgressBar progressBar;
			TextView txt_productname ;
			TextView txt_productPrice ;
		}
	}
	
	public void onClickGridElement( int position )
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
		
		Enumeration<String> enumeration = ApplicationClass.dictionaryWholeList.keys() ;
		ArrayList<String> keys = new ArrayList<String>() ;
		while (enumeration.hasMoreElements()) 
		{
			String string = (String) enumeration.nextElement();
			keys.add(string) ;
		}

		for ( int i = 0 ; i < ApplicationClass.dictionaryWholeList.size() ; i++ )
		{
			Dictionary<String, String> dictionaryForProductInfo = new Hashtable<String, String>() ;
			dictionaryForProductInfo = ApplicationClass.dictionaryWholeList.get(keys.get(i));
			
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
		
//		Intent intent = new Intent  (ProductDescription.this,ProductDescription.class);
//		
//		intent.putExtra(HTTP_RESPONSE_Products.name, name.get(position));
//		intent.putExtra(HTTP_RESPONSE_Products.description, description.get(position));
//		intent.putExtra(HTTP_RESPONSE_Products.small_image, small_image.get(position));
//		intent.putExtra(HTTP_RESPONSE_Products.is_salable, is_salable.get(position));
//		intent.putExtra(HTTP_RESPONSE_Products.qty, qty.get(position));
//		intent.putExtra(HTTP_RESPONSE_Products.status, status.get(position));
//		intent.putExtra(HTTP_RESPONSE_Products.id, id.get(position));
//		intent.putExtra(HTTP_RESPONSE_Products.sku, sku.get(position));
//		intent.putExtra(HTTP_RESPONSE_Products.price, price.get(position));
//		intent.putExtra(HTTP_RESPONSE_Products.image, image.get(position));
//		
//		intent.putStringArrayListExtra("navigationDrawerList", navigationDrawerList);

//		startActivity(intent) ;	
		
		downloadImage(image.get(position).toString()) ;
		
		facebookFeedUrl = image.get(position).toString() ;

		txt_productDescription.setText(description.get(position).toString());
		txt_productName.setText(name.get(position).toString());
		txt_productPrice.setText(price.get(position).toString());
		txt_productCode.setText(sku.get(position).toString());
		
		String isInStock = getIntent().getStringExtra(HTTP_RESPONSE_Search.is_in_stock);
		if ( isInStock != null )
		{
			if ( isInStock.equals("1") )
			{
				txt_availableinStock.setText(getResources().getString(R.string.ProductsDescription_isInStock));
			}
			else if ( isInStock.equals("0") )
			{
				txt_availableinStock.setText(getResources().getString(R.string.ProductsDescription_outOfStock));
			}
		}

		hideKeyboardEveryTime();
	}

}

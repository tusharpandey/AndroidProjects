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
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
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
import com.little_flora.Activity.Async.SearchResultAsync;
import com.little_flora.Activity.CallBacks.SearchCallback;
import com.little_flora.Activity.CallBacks.SortAdapter;
import com.little_flora.Activity.Utils.CheckNetwork;
import com.little_flora.Activity.Utils.HTTP_POST_urls;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_Products;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_Search;
import com.little_flora.Activity.Utils.LF_Constants.Con_SpalshScreen;
import com.little_flora.Activity.Utils.LF_Constants.Con_UserSearch;
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

public class SearchResult extends BaseActivity implements SearchCallback,
		 OnClickListener, SortAdapter {
	private Toasts_ mToast;
	private Logs_ mLogs;
	private DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private GridView gridView;
	private ArrayList<String> navigationDrawerList;
	LinearLayout linear_BottomLayout;
	private GridView gridview_temp;
	private Button btn_Products_Sort;
	private Button btn_Products_Filter;

	// btn_Products_Sort
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

//		View newView = getLayoutInflater().inflate(
//				R.layout.activity_productlist, null);
//		newView.addOnLayoutChangeListener(this);

		setContentView(R.layout.activity_productlist);
		
		MySharedPref.getInstance(this).setString(
				"onPauseStateOfSearchResultClass","false");

		// hideTheKeyBoard();

		initView();

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.placeholder)
				.showImageForEmptyUri(R.drawable.placeholder)
				.showImageOnFail(R.drawable.placeholder).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();

		mToast = new Toasts_(this);
		mLogs = new Logs_("SearchResult");

		navigationDrawerList = getIntent().getStringArrayListExtra(
				"navigationDrawerList");
		if (navigationDrawerList == null) {
			Log.e("NullPointerException : ", "navigationDrawerList is null");
		}
		listToSlideDrawerNavigation = navigationDrawerList;

		String[] navigationArray = navigationDrawerList
				.toArray(new String[navigationDrawerList.size()]);

		navigationDrawerSetup(navigationArray);

		if (CheckNetwork.isNetworkAvailable(this)) {
			callSearchResult();
		} else {
			mToast.showImpToast(getResources().getString(
					R.string.networkNotPresent));
		}
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MySharedPref.getInstance(this).setString(
				"onPauseStateOfSearchResultClass","true");
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);

		// mToast.showTempToast("onWindowFocusChanged") ;
	}

	public void callSearchResult() {
		String[] data = new String[1];
		String loadingText = getResources().getString(
				R.string.loading_searchingResult);

		String lang = null;

		String languageFull = MySharedPref.getInstance(this).getString(
				Con_SpalshScreen.GlobalStoredLanguageForApp);

		if (languageFull.equals("Arabic")) {
			lang = "ar";
		} else if (languageFull.equals("English")) {
			lang = "en";
		}

		String url = HTTP_POST_urls.productSearch
				+ MySharedPref.getInstance(this).getString(
						Con_UserSearch.productSearchString) + "&lang=" + lang;

		mLogs.showTempLog(url);

		SearchResultAsync searchResultAsync = new SearchResultAsync(url, data,
				this, loadingText);
		searchResultAsync.execute("");

		InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
	}

	public void initView() {
		btn_Products_Sort = (Button) findViewById(R.id.btn_Products_Sort);
		btn_Products_Sort.setOnClickListener(this);

		btn_Products_Filter = (Button) findViewById(R.id.btn_Products_Filter);
		btn_Products_Filter.setOnClickListener(this);

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
		
		int sendScreenHeight = Integer.parseInt(MySharedPref.getInstance(SearchResult.this).getString("sendScreenHeight_SPLASHSCREEN"));


		BlankMainCategoryAdapter adapter = new BlankMainCategoryAdapter(list,
				this, sendScreenHeight, "SearchResult");
		gridview_temp.setAdapter(adapter);

		linear_BottomLayout = (LinearLayout) findViewById(R.id.linear_BottomLayout);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// mToast.showTempToast("onStart");
	}

	boolean isItRunning = false;

	public int count = 0;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// mToast.showTempToast("onResume : "+count);

		String currentActivity = MySharedPref.getInstance(this).getString(
				"MyCurrentActivityOnPage");
		
		String pauseState = MySharedPref.getInstance(this).getString(
				"onPauseStateOfSearchResultClass");

		if (currentActivity.equals("SearchActivity") )
		{
//			if ( pauseState != null && pauseState.equals("false") )
				callSearchResult();
		}
		MySharedPref.getInstance(this).setString("MyCurrentActivityOnPage",
				"SearchActivity");

	}

	Dictionary<String, Dictionary<String, String>> searchDataDictionary;
	private LinearLayout linear_noproductsAvailable;

	public void createDataForSearchAdapter(
			Dictionary<String, Dictionary<String, String>> searchDataDictionary) {

		if (searchDataDictionary.size() == 0) {
			mToast.showImpToast(getResources().getString(
					R.string.Search_productsnotFound));
			onBackPressed();
		} else {
			mToast.showImpToast(getResources().getString(
					R.string.Search_productsFound));

			ArrayList<CharSequence> name = new ArrayList<CharSequence>();
			ArrayList<CharSequence> price = new ArrayList<CharSequence>();
			ArrayList<CharSequence> imageURLS_list = new ArrayList<CharSequence>();

			// --

			ArrayList<CharSequence> description = new ArrayList<CharSequence>();
			ArrayList<CharSequence> is_salable = new ArrayList<CharSequence>();
			ArrayList<CharSequence> is_in_stock = new ArrayList<CharSequence>();
			ArrayList<CharSequence> status_q = new ArrayList<CharSequence>();
			ArrayList<CharSequence> entity_id = new ArrayList<CharSequence>();
			ArrayList<CharSequence> sku = new ArrayList<CharSequence>();
			ArrayList<CharSequence> quantity = new ArrayList<CharSequence>();

			ArrayList<String> sortedArray = new ArrayList<String>();
			Enumeration<String> enumeration_ = searchDataDictionary.keys();

			for (int i = 0; i < searchDataDictionary.size(); i++) {
				String key = enumeration_.nextElement();

				name.add(searchDataDictionary.get(key).get("name"));
				price.add(searchDataDictionary.get(key).get("price"));
				imageURLS_list.add(searchDataDictionary.get(key).get("image"));

				// --

				description.add(searchDataDictionary.get(key)
						.get("description"));
				is_salable.add(searchDataDictionary.get(key).get("is_salable"));
				is_in_stock.add(searchDataDictionary.get(key)
						.get("is_in_stock"));
				status_q.add(searchDataDictionary.get(key).get("status_q"));
				entity_id.add(searchDataDictionary.get(key).get("entity_id"));
				sku.add(searchDataDictionary.get(key).get("sku"));
				quantity.add(searchDataDictionary.get(key).get("quantity"));

				sortedArray.add(Integer.toString(i));
			}

			String[] imageUrls = imageURLS_list
					.toArray(new String[imageURLS_list.size()]);

			int sendScreenHeight = Integer.parseInt(MySharedPref.getInstance(SearchResult.this).getString("sendScreenHeight_SPLASHSCREEN"));

			ImageAdapter imageAdapterGlobal = new ImageAdapter(

			sortedArray, name, price, imageURLS_list, description, is_salable,
					is_in_stock, status_q, entity_id, sku, quantity,

					searchDataDictionary, sendScreenHeight);

			if (((GridView) gridView).getVisibility() == View.GONE
					|| ((GridView) gridView).getVisibility() == View.VISIBLE) {
				gridview_temp.setVisibility(View.GONE);
				gridView.setVisibility(View.VISIBLE);
			}

			((GridView) gridView).setAdapter(imageAdapterGlobal);

			gridView.setOnScrollListener(new OnScrollListener() {

				@Override
				public void onScrollStateChanged(AbsListView view,
						int scrollState) {
					// TODO Auto-generated method stub
					if (scrollState == SCROLL_STATE_IDLE) {
						Log.e("onScrollStateChanged", "Scrolling stopped");

						if (linear_BottomLayout == null)
							linear_BottomLayout = (LinearLayout) findViewById(R.id.linear_BottomLayout);

						linear_BottomLayout.setVisibility(View.VISIBLE);

						Animation animContentDown = AnimationUtils
								.loadAnimation(getApplicationContext(),
										R.anim.slide_up_service);
						animContentDown
								.setAnimationListener(new AnimationListener() {
									@Override
									public void onAnimationStart(
											Animation animation) {
									}

									@Override
									public void onAnimationRepeat(
											Animation animation) {
									}

									@Override
									public void onAnimationEnd(
											Animation animation) {
									}
								});

						linear_BottomLayout.startAnimation(animContentDown);
					} else {
						Animation animContentDown = AnimationUtils
								.loadAnimation(getApplicationContext(),
										R.anim.slide_down_service);
						animContentDown
								.setAnimationListener(new AnimationListener() {
									@Override
									public void onAnimationStart(
											Animation animation) {
									}

									@Override
									public void onAnimationRepeat(
											Animation animation) {
									}

									@Override
									public void onAnimationEnd(
											Animation animation) {
										if (linear_BottomLayout != null)
											linear_BottomLayout
													.setVisibility(View.GONE);
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
		}

	}

	@Override
	public void searchResponse(
			Dictionary<String, Dictionary<String, String>> searchDataDictionary,
			String message, String status) {
		// TODO Auto-generated method stub

		this.searchDataDictionary = searchDataDictionary;

		if (message.contains("Error") || status.equals("0")) {
			mToast.showImpToast(getResources().getString(
					R.string.Search_productsnotFound));
			
			if ( gridview_temp != null )
				gridview_temp.setVisibility(View.GONE) ;
			
			if ( gridView != null )
			{
				gridView.setVisibility(View.GONE) ;
			}
			
			linear_noproductsAvailable = ( LinearLayout ) findViewById(R.id.linear_noproductsAvailable) ;
			linear_noproductsAvailable.setVisibility(View.VISIBLE);
//			onBackPressed();
		} else if (status.equals("1")) {
			if ( linear_noproductsAvailable != null )
				linear_noproductsAvailable.setVisibility(View.GONE);
				
			createDataForSearchAdapter(searchDataDictionary);
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	
	

	public void onClickGridElement(int position,String skuString) {
		ArrayList<CharSequence> name = new ArrayList<CharSequence>();
		ArrayList<CharSequence> description = new ArrayList<CharSequence>();
		ArrayList<CharSequence> small_image = new ArrayList<CharSequence>();
		ArrayList<CharSequence> is_salable = new ArrayList<CharSequence>();
		ArrayList<CharSequence> qty = new ArrayList<CharSequence>();
		ArrayList<CharSequence> status = new ArrayList<CharSequence>();
		ArrayList<CharSequence> id = new ArrayList<CharSequence>();
		ArrayList<CharSequence> sku = new ArrayList<CharSequence>();
		ArrayList<CharSequence> price = new ArrayList<CharSequence>();
		ArrayList<CharSequence> image = new ArrayList<CharSequence>();
		ArrayList<CharSequence> isInStock = new ArrayList<CharSequence>();

		Enumeration<String> enumeration = searchDataDictionary.keys();
		ArrayList<String> keys = new ArrayList<String>();
		while (enumeration.hasMoreElements()) {
			String string = (String) enumeration.nextElement();
			keys.add(string);
		}

		int fetchSkuPosition = 0 ;
		
		for (int i = 0; i < searchDataDictionary.size(); i++) {
			Dictionary<String, String> dictionaryForProductInfo = new Hashtable<String, String>();
			dictionaryForProductInfo = searchDataDictionary.get(keys.get(i));

			name.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Search.name));
			description.add(dictionaryForProductInfo
					.get(HTTP_RESPONSE_Search.description));
			small_image.add(dictionaryForProductInfo
					.get(HTTP_RESPONSE_Search.image));
			is_salable.add(dictionaryForProductInfo
					.get(HTTP_RESPONSE_Search.is_salable));
			qty.add(dictionaryForProductInfo
					.get(HTTP_RESPONSE_Search.entity_id));
			status.add(dictionaryForProductInfo
					.get(HTTP_RESPONSE_Search.status));
			id.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Search.entity_id));
			sku.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Search.sku));
			price.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Search.price));
			image.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Search.image));
			isInStock.add(dictionaryForProductInfo
					.get(HTTP_RESPONSE_Search.is_in_stock));
			
			
			if ( skuString.equals(sku.get(i).toString()))
			{
				fetchSkuPosition = i ;
			}
		}

		Intent intent = new Intent(SearchResult.this, ProductDescription.class);

		intent.putExtra(HTTP_RESPONSE_Search.name, name.get(fetchSkuPosition));
		intent.putExtra(HTTP_RESPONSE_Search.description,
				description.get(fetchSkuPosition));
		intent.putExtra(HTTP_RESPONSE_Search.image, small_image.get(fetchSkuPosition));
		intent.putExtra(HTTP_RESPONSE_Search.is_salable,
				is_salable.get(fetchSkuPosition));
		intent.putExtra(HTTP_RESPONSE_Search.entity_id, qty.get(fetchSkuPosition));
		intent.putExtra(HTTP_RESPONSE_Search.status, status.get(fetchSkuPosition));
		intent.putExtra(HTTP_RESPONSE_Search.entity_id, id.get(fetchSkuPosition));
		intent.putExtra(HTTP_RESPONSE_Search.sku, sku.get(fetchSkuPosition));
		intent.putExtra(HTTP_RESPONSE_Search.price, price.get(fetchSkuPosition));
		intent.putExtra(HTTP_RESPONSE_Search.image, image.get(fetchSkuPosition));
		intent.putExtra(HTTP_RESPONSE_Search.is_in_stock,
				isInStock.get(fetchSkuPosition));

		ApplicationClass.dictionaryWholeList = searchDataDictionary;

		intent.putStringArrayListExtra("navigationDrawerList",
				navigationDrawerList);

		startActivity(intent);
	}

	public class ImageAdapter extends BaseAdapter {
		String[] imageUrls;
		int sendScreenHeight;

		ArrayList<CharSequence> name = new ArrayList<CharSequence>();
		ArrayList<CharSequence> image = new ArrayList<CharSequence>();
		ArrayList<CharSequence> description = new ArrayList<CharSequence>();
		ArrayList<CharSequence> small_image = new ArrayList<CharSequence>();
		ArrayList<CharSequence> is_salable = new ArrayList<CharSequence>();
		ArrayList<CharSequence> qty = new ArrayList<CharSequence>();
		ArrayList<CharSequence> status = new ArrayList<CharSequence>();
		ArrayList<CharSequence> id = new ArrayList<CharSequence>();
		ArrayList<CharSequence> sku = new ArrayList<CharSequence>();
		ArrayList<CharSequence> price = new ArrayList<CharSequence>();
		ArrayList<CharSequence> is_in_stock = new ArrayList<CharSequence>();
		ArrayList<String> sortedArray;
		Dictionary<String, Dictionary<String, String>> searchDataDictionary;

		public ImageAdapter(
				ArrayList<String> sortedArray,
				ArrayList<CharSequence> name,
				ArrayList<CharSequence> price,
				ArrayList<CharSequence> imageURLS_list,
				ArrayList<CharSequence> description,
				ArrayList<CharSequence> is_salable,
				ArrayList<CharSequence> is_in_stock,
				ArrayList<CharSequence> status_q,
				ArrayList<CharSequence> entity_id,
				ArrayList<CharSequence> sku,
				ArrayList<CharSequence> quantity,
				Dictionary<String, Dictionary<String, String>> searchDataDictionary,
				int sendScreenHeight) {
			this.sortedArray = sortedArray;
			this.searchDataDictionary = searchDataDictionary;
			this.sendScreenHeight = sendScreenHeight;

			this.name = name;
			this.price = price;
			this.image = imageURLS_list;
			this.description = description;
			this.is_salable = is_salable;
			this.is_in_stock = is_in_stock;
			this.status = status_q;
			this.id = entity_id;
			this.sku = sku;
			this.qty = quantity;

			//
			// Enumeration<String> enumeration_ = searchDataDictionary.keys();
			//
			// for (int i = 0; i < searchDataDictionary.size(); i++) {
			// String key = enumeration_.nextElement();
			//
			// Dictionary<String, String> dictionary = searchDataDictionary
			// .get(key);
			//
			// name.add(dictionary.get("name"));
			// image.add(dictionary.get("image"));
			// description.add(dictionary.get("description"));
			// small_image.add(dictionary.get("image"));
			// is_salable.add(dictionary.get("is_salable"));
			// qty.add(dictionary.get("quantity"));
			// status.add(dictionary.get("status"));
			// id.add(dictionary.get("entity_id"));
			// sku.add(dictionary.get("sku"));
			// price.add(dictionary.get("price"));
			// is_in_stock.add(dictionary.get("is_in_stock"));

			imageUrls = new String[image.size()];
			imageUrls = image.toArray(new String[image.size()]);

			mLogs.showImpLog("Size Of name : " + name.size());
			mLogs.showImpLog("Size Of image : " + image.size());
			mLogs.showImpLog("Size Of description : " + description.size());
			mLogs.showImpLog("Size Of small_image : " + small_image.size());
			mLogs.showImpLog("Size Of is_salable : " + is_salable.size());
			mLogs.showImpLog("Size Of qty : " + qty.size());
			mLogs.showImpLog("Size Of status : " + status.size());
			mLogs.showImpLog("Size Of status : " + status.size());
			mLogs.showImpLog("Size Of id : " + id.size());
			mLogs.showImpLog("Size Of sku : " + sku.size());
			mLogs.showImpLog("Size Of price : " + price.size());
			mLogs.showImpLog("Size Of is_in_stock : " + is_in_stock.size());
		}

		@Override
		public int getCount() {
			return searchDataDictionary.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			final ViewHolder holder;
			View view = convertView;
			if (view == null) {
				view = getLayoutInflater().inflate(
						R.layout.adapter_grid_productlisting, parent, false);
				holder = new ViewHolder();
				assert view != null;
				holder.imageView = (ImageView) view.findViewById(R.id.image);

				holder.txt_productname = (TextView) view
						.findViewById(R.id.txt_productname);
				holder.txt_productPrice = (TextView) view
						.findViewById(R.id.txt_productPrice);

				holder.chk_wishlist = (CheckBox) view
						.findViewById(R.id.chk_wishlist);
				holder.img_share = (ImageView) view
						.findViewById(R.id.img_share);

				TypeFace_TextView.setTypeFace(SearchResult.this,
						holder.txt_productname);
				holder.progressBar = (ProgressBar) view
						.findViewById(R.id.progress);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			//onResume

			holder.txt_productname.setText(name.get(position));
			holder.txt_productPrice.setText("SR " + price.get(position));

			holder.img_share.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					saveBitmapIntoStorage(((BitmapDrawable) holder.imageView
							.getDrawable()).getBitmap());

					productName = name.get(position);
					productDescription = description.get(position);

					CharSequence skuSend = sku.get(position);
					CharSequence product_idSend = id.get(position);
					CharSequence qtySend = qty.get(position);
					CharSequence store_idSend = MySharedPref.getInstance(
							SearchResult.this).getString(
							Con_SpalshScreen.GlobalStored_StoreId);

					Dictionary<String, CharSequence> dictionaryToSend = new Hashtable<String, CharSequence>();
					dictionaryToSend.put("skuSend", skuSend);
					dictionaryToSend.put("product_idSend", product_idSend);
					dictionaryToSend.put("qtySend", qtySend);
					dictionaryToSend.put("store_idSend", store_idSend);

					DialogAsFlipkartSharing dialogAsFlipKart = new DialogAsFlipkartSharing(
							"SearchResult", SearchResult.this, dictionaryToSend);
					dialogAsFlipKart.setCancelable(true);
					dialogAsFlipKart.show();
				}
			});

			holder.imageView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					onClickGridElement(position,sku.get(position).toString());
				}
			});

			imageLoader.displayImage(imageUrls[position], holder.imageView,
					options, new SimpleImageLoadingListener() {
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
						public void onLoadingComplete(String imageUri,
								View view, Bitmap loadedImage) {
							holder.progressBar.setVisibility(View.GONE);
						}
					}, new ImageLoadingProgressListener() {
						@Override
						public void onProgressUpdate(String imageUri,
								View view, int current, int total) {
							holder.progressBar.setProgress(Math.round(100.0f
									* current / total));
						}
					});

			view.setMinimumHeight(sendScreenHeight / 2);

			return view;
		}

		class ViewHolder {
			ImageView imageView;
			ProgressBar progressBar;
			TextView txt_productname;
			TextView txt_productPrice;
			CheckBox chk_wishlist;
			ImageView img_share;
		}
	}

	public int getScreenHeight() {
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;

		return height;
	}

	public int getScreenWidth() {
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;

		return width;
	}

	CharSequence productName = null;
	CharSequence productDescription = null;

	public void onDialogAsFlipKartSharing_ShareClicked() {
		// mToasts.showTempToast("onDialogAsFlipKartSharing_ShareClicked clicked")
		// ;

		DialogForSharing sharingDialog = new DialogForSharing(this,
				productName.toString(), productDescription.toString(),
				"Sharing_SR");
		sharingDialog.setCancelable(true);
		sharingDialog.show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_Products_Filter:

			if ( linear_noproductsAvailable != null && linear_noproductsAvailable.getVisibility() == View.VISIBLE )
			{
			}
			else
			{
				DialogFilterBy dialogFilterBy = new DialogFilterBy(
						searchDataDictionary, this, 4, 400, "SearchResult");
				dialogFilterBy.show();
			}
			break;
		case R.id.btn_Products_Sort:
			if ( linear_noproductsAvailable != null && linear_noproductsAvailable.getVisibility() == View.VISIBLE )
			{
			}
			else
			{
				DialogSortButton cdd = new DialogSortButton(this,
					searchDataDictionary, "SearchResult");
				cdd.show();
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void sortAdapter(int sortitValue) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub

		Enumeration<String> enumeration = searchDataDictionary.keys();
		ArrayList<String> keys = new ArrayList<String>();
		ArrayList<String> price = new ArrayList<String>();
		ArrayList<String> name = new ArrayList<String>();
		ArrayList<String> sku = new ArrayList<String>();

		while (enumeration.hasMoreElements()) {
			String string = (String) enumeration.nextElement();
			keys.add(string);
		}

		for (int i = 0; i < searchDataDictionary.size(); i++) {
			Dictionary<String, String> dictionaryForProductInfo = new Hashtable<String, String>();
			dictionaryForProductInfo = searchDataDictionary.get(keys.get(i));

			name.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.name));
			sku.add(dictionaryForProductInfo.get(HTTP_RESPONSE_Products.sku));
			price.add(dictionaryForProductInfo
					.get(HTTP_RESPONSE_Products.price));
		}

		ArrayList<String> sortedArray = null;
		switch (sortitValue) {
		case 1:
			sortedArray = sortAccordingToPrice(price, 1);

			break;
		case 2:

			sortedArray = sortAccordingToPrice(price, 2);

			break;
		case 3:

			sortedArray = sortAccordingToPrice(price, 3);

			break;
		case 4:

			sortedArray = sortAccordingToPrice(price, 4);

			break;
		case 5:
			sortedArray = sortAccordingToPrice(name, 5);

			break;
		case 6:
			sortedArray = sortAccordingToPrice(sku, 6);
			break;
		default:
			break;
		}

		// for ( int i = 0 ; i < sortedArray.size() ; i++ )
		// {
		// mLogs.showTempLog("Flow : "+sortedArray.get(i));
		// }

		ArrayList<CharSequence> nameSend = new ArrayList<CharSequence>();
		ArrayList<CharSequence> descriptionSend = new ArrayList<CharSequence>();
		ArrayList<CharSequence> small_imageSend = new ArrayList<CharSequence>();
		ArrayList<CharSequence> is_salableSend = new ArrayList<CharSequence>();
		ArrayList<CharSequence> is_in_stockSend = new ArrayList<CharSequence>();
		ArrayList<CharSequence> qtySend = new ArrayList<CharSequence>();
		ArrayList<CharSequence> statusSend = new ArrayList<CharSequence>();
		ArrayList<CharSequence> idSend = new ArrayList<CharSequence>();
		ArrayList<CharSequence> skuSend = new ArrayList<CharSequence>();
		ArrayList<CharSequence> priceSend = new ArrayList<CharSequence>();
		ArrayList<CharSequence> imageSend = new ArrayList<CharSequence>();

		Enumeration<String> enumerationSend = searchDataDictionary.keys();
		ArrayList<String> keysSend = new ArrayList<String>();

		while (enumerationSend.hasMoreElements()) {
			String string = (String) enumerationSend.nextElement();
			keysSend.add(string);
		}

		for (int i = 0; i < searchDataDictionary.size(); i++) {
			Dictionary<String, String> dictionaryForProductInfo = new Hashtable<String, String>();
			dictionaryForProductInfo = searchDataDictionary.get(keys.get(i));

			nameSend.add(dictionaryForProductInfo
					.get(HTTP_RESPONSE_Products.name));
			descriptionSend.add(dictionaryForProductInfo
					.get(HTTP_RESPONSE_Products.description));
			small_imageSend.add(dictionaryForProductInfo
					.get(HTTP_RESPONSE_Products.small_image));
			is_in_stockSend.add(dictionaryForProductInfo
					.get(HTTP_RESPONSE_Search.is_in_stock));
			is_salableSend.add(dictionaryForProductInfo
					.get(HTTP_RESPONSE_Products.is_salable));
			qtySend.add(dictionaryForProductInfo
					.get(HTTP_RESPONSE_Products.qty));
			statusSend.add(dictionaryForProductInfo
					.get(HTTP_RESPONSE_Products.status));
			idSend.add(dictionaryForProductInfo
					.get(HTTP_RESPONSE_Search.entity_id));
			skuSend.add(dictionaryForProductInfo
					.get(HTTP_RESPONSE_Products.sku));
			priceSend.add(dictionaryForProductInfo
					.get(HTTP_RESPONSE_Products.price));
			imageSend.add(dictionaryForProductInfo
					.get(HTTP_RESPONSE_Products.image));
		}

		ArrayList<CharSequence> nameSend_ = new ArrayList<CharSequence>();
		ArrayList<CharSequence> descriptionSend_ = new ArrayList<CharSequence>();
		ArrayList<CharSequence> small_imageSend_ = new ArrayList<CharSequence>();
		ArrayList<CharSequence> is_salableSend_ = new ArrayList<CharSequence>();
		ArrayList<CharSequence> is_in_stockSend_ = new ArrayList<CharSequence>();
		ArrayList<CharSequence> qtySend_ = new ArrayList<CharSequence>();
		ArrayList<CharSequence> statusSend_ = new ArrayList<CharSequence>();
		ArrayList<CharSequence> idSend_ = new ArrayList<CharSequence>();
		ArrayList<CharSequence> skuSend_ = new ArrayList<CharSequence>();
		ArrayList<CharSequence> priceSend_ = new ArrayList<CharSequence>();
		ArrayList<CharSequence> imageSend_ = new ArrayList<CharSequence>();

		for (int i = 0; i < sortedArray.size(); i++) {
			nameSend_.add(nameSend.get(Integer.parseInt(sortedArray.get(i))));
			descriptionSend_.add(descriptionSend.get(Integer
					.parseInt(sortedArray.get(i))));
			small_imageSend_.add(small_imageSend.get(Integer
					.parseInt(sortedArray.get(i))));
			is_salableSend_.add(is_salableSend.get(Integer.parseInt(sortedArray
					.get(i))));
			is_in_stockSend_.add(is_in_stockSend.get(Integer
					.parseInt(sortedArray.get(i))));
			qtySend_.add(qtySend.get(Integer.parseInt(sortedArray.get(i))));
			statusSend_
					.add(statusSend.get(Integer.parseInt(sortedArray.get(i))));
			idSend_.add(idSend.get(Integer.parseInt(sortedArray.get(i))));
			skuSend_.add(skuSend.get(Integer.parseInt(sortedArray.get(i))));
			priceSend_.add(priceSend.get(Integer.parseInt(sortedArray.get(i))));
			imageSend_.add(imageSend.get(Integer.parseInt(sortedArray.get(i))));
		}

		String[] imageURLS = imageSend_.toArray(new String[imageSend.size()]);

		((GridView) gridView).setAdapter(null);
		
		int sendScreenHeight = Integer.parseInt(MySharedPref.getInstance(SearchResult.this).getString(
				"sendScreenHeight_SPLASHSCREEN"));


		ImageAdapter imageAdapter = new ImageAdapter(sortedArray, nameSend_,
				priceSend_, imageSend_, descriptionSend_, is_salableSend_,
				is_in_stockSend_, statusSend_, idSend_, skuSend_, qtySend_,
				searchDataDictionary, sendScreenHeight);
		
		((GridView) gridView).setAdapter(imageAdapter);
		//
		// mLogs.showTempLog("View changed") ;

	}

}

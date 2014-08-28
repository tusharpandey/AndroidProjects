package com.little_flora.Activity;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.little_flora.R;
import com.little_flora.Activity.Adapter.BlankMainCategoryAdapter;
import com.little_flora.Activity.Adapter.ExpandableListAdapter;
import com.little_flora.Activity.Async.MainCategory_Async;
import com.little_flora.Activity.CallBacks.MainCategory_CallBack;
import com.little_flora.Activity.Utils.CheckNetwork;
import com.little_flora.Activity.Utils.HTTP_RESPONSE_PARSE_parameter.HTTP_RESPONSE_MainCategory;
import com.little_flora.Activity.Utils.LF_Constants.Con_SpalshScreen;
import com.little_flora.Activity.Utils.LF_Constants.Con_UserDetails;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.Activity.Utils.MySharedPref;
import com.little_flora.Activity.Utils.Toasts_;
import com.little_flora.User.Login;
import com.little_flora.User.Profile;

public class MainCategory extends BaseActivity implements
		MainCategory_CallBack, OnClickListener {
	private Logs_ mLogs;
	private Toasts_ mToast;
	private String language;
	private TextView txt_pageTitle;
//sendScreenHeight_SPLASHSCREEN
	private ExpandableListView expList_mainCategory;
	private ArrayList<String> listDataHeader;
	private HashMap<String, List<String>> listDataChild;
	private ExpandableListAdapter listAdapter;
	private ArrayList<String> navigationDrawerList;
	private ListView list_mainCategory;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		/*
		 * This Code is added because , in this page we have set "SingleTop" property so , if in this page navigation drawer is opened ,
		 * then clicking in it , navigationDrawer page is not closed .
		 * 
		 * 		So for it , first we have to test , Size of listDataHeader , that some thing is present or not in list , means if you
		 * 		will select some option from navigation drawer from other page like product description then , listSizeHeader size will 
		 * 		be 0 , 
		 * 
		 */
		if ( listDataHeader != null && listDataHeader.size() > 0 )
		{
			String openThisSection = MySharedPref.getInstance(this).getString("navigationDrawerList_openParticularSection_currentPosition");
			if ( openThisSection != null && !openThisSection.equals(""))
			{
				int openThisSectionInt = Integer.parseInt(openThisSection) ;
				if ( openThisSectionInt == 50 )
				{
				
				}
				else
				{
					expList_mainCategory.expandGroup(openThisSectionInt);
					expList_mainCategory.setSelectedGroup(openThisSectionInt);
				}
			}
		}
			
		ArrayList<String> list = new ArrayList<String>() ;
		list.add("1");
		list.add("1");
		list.add("1");
		list.add("1");
		list.add("1");
		list.add("1");
		list.add("1");
		
		int sendScreenHeight = Integer.parseInt(MySharedPref.getInstance(MainCategory.this).getString(
				"sendScreenHeight_SPLASHSCREEN"));

		
		BlankMainCategoryAdapter adapter = new BlankMainCategoryAdapter(list, this,sendScreenHeight,"MainCategory");
		list_mainCategory.setAdapter(adapter) ;

		MySharedPref.getInstance(this).setString("MyCurrentActivityOnPage","MainCategory") ;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
//        View newView = getLayoutInflater().inflate(R.layout.activity_maincategory, null);
//        newView.addOnLayoutChangeListener(this);
        
		setContentView(R.layout.activity_maincategory);

		initView();

		mLogs = new Logs_("MainCategory");
		mToast = new Toasts_(this);

		String data[] = new String[1];

		language = MySharedPref.getInstance(this).getString(
				Con_SpalshScreen.GlobalStoredLanguageForApp);
		// selectItem
		if (CheckNetwork.isNetworkAvailable(this)) {
			MainCategory_Async asyncObject = new MainCategory_Async(data, this,
					language);
			asyncObject.execute("");
		} else {
			mToast.showImpToast(getResources().getString(
					R.string.networkNotPresent));
		}

		navigationDrawerList = getIntent().getStringArrayListExtra(
				"navigationDrawerList");

		listToSlideDrawerNavigation = navigationDrawerList;

		String[] navigationArray = navigationDrawerList
				.toArray(new String[navigationDrawerList.size()]);

		navigationDrawerSetup(navigationArray);

		/*
		 * if (savedInstanceState == null) { selectItem(0); }
		 */
		
		final LinearLayout layout_linearLayout = (LinearLayout) findViewById(R.id.layout_linearLayout);
		layout_linearLayout.post(new Runnable() {
			public void run() {
				int height = layout_linearLayout.getHeight();
				int width = layout_linearLayout.getWidth();
				
				Log.e("Height", height+"");
				
//				Resources resources = MainCategory.this.getResources();
//				int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
//				int navigationBarHeight = 0 ;
//				if (resourceId > 0) {
//					navigationBarHeight = resources.getDimensionPixelSize(resourceId);
//				}
//				
//				height = height - navigationBarHeight ;
				
				MySharedPref.getInstance(MainCategory.this).setString(
						"sendScreenHeight_SPLASHSCREEN", height + "");
				MySharedPref.getInstance(MainCategory.this).setString(
						"sendScreenWidth_SPLASHSCREEN", width + "");
			}
		});
	}
	

	public void initView() {
		expList_mainCategory = (ExpandableListView) findViewById(R.id.expList_mainCategory);
		list_mainCategory = ( ListView ) findViewById(R.id.list_mainCategory) ;

		/*
		 * prepareListData();
		 * 
		 * listAdapter = new ExpandableListAdapter(this, listDataHeader,
		 * listDataChild);
		 * 
		 * // setting list adapter expList_mainCategory.setAdapter(listAdapter);
		 */
		
		expList_mainCategory
				.setOnGroupClickListener(new OnGroupClickListener() {

					@Override
					public boolean onGroupClick(ExpandableListView parent,
							View v, int groupPosition, long id) {
						// Toast.makeText(getApplicationContext(),
						// "Group Clicked " + listDataHeader.get(groupPosition),
						// Toast.LENGTH_SHORT).show();
						return false;
					}
				});

		expList_mainCategory
				.setOnGroupExpandListener(new OnGroupExpandListener() {

					@Override
					public void onGroupExpand(int groupPosition) {
						/*
						 * Toast.makeText(getApplicationContext(),
						 * listDataHeader.get(groupPosition) + " Expanded",
						 * Toast.LENGTH_SHORT).show();
						 */}
				});
		
		expList_mainCategory
				.setOnGroupCollapseListener(new OnGroupCollapseListener() {

					@Override
					public void onGroupCollapse(int groupPosition) {
						/*
						 * Toast.makeText(getApplicationContext(),
						 * listDataHeader.get(groupPosition) + " Collapsed",
						 * Toast.LENGTH_SHORT).show();
						 */
					}
				});

		// Listview on child click listener
		expList_mainCategory
				.setOnChildClickListener(new OnChildClickListener() {

					@Override
					public boolean onChildClick(ExpandableListView parent,
							View v, int groupPosition, int childPosition,
							long id) {
						// TODO Auto-generated method stub
						String idOfWholeDataWhichisClicked = mainCategoryDataList_inMenu_Active
								.get(Integer.toString(groupPosition)).get(
										HTTP_RESPONSE_MainCategory.id);

						// Toast.makeText(
						// getApplicationContext(),
						// idOfWholeDataWhichisClicked+" group position : "+groupPosition+"Child position : "+childPosition,
						// Toast.LENGTH_LONG)
						// .show();

						Dictionary<String, String> dictionary = subCategoryDataWholeDict
								.get(idOfWholeDataWhichisClicked).get(
										Integer.toString(childPosition));

						Intent intent = new Intent(MainCategory.this,
								ProductList_Grid.class);

						navigationDrawerList = getIntent()
								.getStringArrayListExtra("navigationDrawerList");
						String[] navigationArray = navigationDrawerList
								.toArray(new String[navigationDrawerList.size()]);

						intent.putStringArrayListExtra("navigationDrawerList",
								navigationDrawerList);
						intent.putExtra("ProductId", dictionary
								.get(HTTP_RESPONSE_MainCategory.subcatid));
						startActivity(intent);
						
						hideKeyboardEveryTime();

						return false;
					}
				});

	}

	Dictionary<String, Dictionary<String, String>> mainCategoryDataList;
	Dictionary<String, Dictionary<String, Dictionary<String, String>>> subCategoryDataWholeDict;
	ArrayList<String> keyToShare;
	private Hashtable<String, Dictionary<String, String>> mainCategoryDataList_inMenu_Active;
	private ArrayList<String> listDataHeaderImages;

	@Override
	public void responseOfMainCategoryCallBack(
			Dictionary<String, Dictionary<String, String>> mainCategoryDataList,
			Dictionary<String, Dictionary<String, Dictionary<String, String>>> subCategoryDataWholeDict) {
		mainCategoryDataList_inMenu_Active = new Hashtable<String, Dictionary<String, String>>();

		/*
		 * Create Data For Expandable list view .
		 * 
		 */
		int j = 0;
		for (int i = 0; i < mainCategoryDataList.size(); i++) {
			if (mainCategoryDataList.get(Integer.toString(i))
					.get(HTTP_RESPONSE_MainCategory.InMenu).equals("1")
					&& mainCategoryDataList.get(Integer.toString(i))
							.get(HTTP_RESPONSE_MainCategory.Active).equals("1")) {
				mainCategoryDataList_inMenu_Active.put(Integer.toString(j),
						mainCategoryDataList.get(Integer.toString(i)));
				j++;
			}
		}

		this.subCategoryDataWholeDict = subCategoryDataWholeDict;

		listDataHeader = new ArrayList<String>();
		listDataHeaderImages = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		for (int i = 0; i < mainCategoryDataList_inMenu_Active.size(); i++) {
			String elementTOAdd = mainCategoryDataList_inMenu_Active.get(
					Integer.toString(i)).get(HTTP_RESPONSE_MainCategory.title);
			String imageUrl = mainCategoryDataList_inMenu_Active.get(
					Integer.toString(i)).get(HTTP_RESPONSE_MainCategory.image);

			listDataHeader.add(elementTOAdd);
			listDataHeaderImages.add(imageUrl);

			String idOfWholeDataWhichisClicked = mainCategoryDataList_inMenu_Active
					.get(Integer.toString(i))
					.get(HTTP_RESPONSE_MainCategory.id);
			ArrayList<String> titleList = new ArrayList<String>();

			for (int k = 0; k < subCategoryDataWholeDict.get(
					idOfWholeDataWhichisClicked).size(); k++) {
				Dictionary<String, String> dictionary = subCategoryDataWholeDict
						.get(idOfWholeDataWhichisClicked).get(
								Integer.toString(k));

				titleList.add(dictionary
						.get(HTTP_RESPONSE_MainCategory.subcatitle));
			}

			listDataChild.put(elementTOAdd, titleList);
		}

		int sendScreenHeight = Integer.parseInt(MySharedPref.getInstance(MainCategory.this).getString(
				"sendScreenHeight_SPLASHSCREEN"));
		
		listAdapter = new ExpandableListAdapter(this, listDataHeader,
				listDataChild, listDataHeaderImages,sendScreenHeight);
		
		if ( expList_mainCategory.getVisibility() == View.GONE || expList_mainCategory.getVisibility() == View.INVISIBLE  )
		{
			expList_mainCategory.setVisibility(View.VISIBLE) ;
			list_mainCategory.setVisibility(View.GONE) ;
		}
		
		expList_mainCategory.setAdapter(listAdapter);

		/*
		 * This code of section is added for opening some section from the Expandable list view .
		 */
		if (getIntent().getIntExtra(
				"navigationDrawerList_openParticularSection", 100) != 100) {
			int openThisSection = getIntent().getIntExtra(
					"navigationDrawerList_openParticularSection", 100);

			mLogs.showTempLog("Open particular section : " + openThisSection);
			if (openThisSection == 50) {
				
			} else {
				if (expList_mainCategory == null) {
					mLogs.showTempLog("expList_mainCategory is null");
				} else {
					expList_mainCategory.expandGroup(openThisSection);
					expList_mainCategory.setSelectedGroup(openThisSection);
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		default:
			break;
		}
	}

}

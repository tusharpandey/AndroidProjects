package com.little_flora.Activity.Adapter;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.little_flora.R;
import com.little_flora.Activity.MainCategory;
import com.little_flora.Activity.Utils.Logs_;
import com.little_flora.Activity.Utils.TypeFace_TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private MainCategory _context;
	private List<String> _listDataHeader; // header titles
	// child data in format of header title, child title
	private HashMap<String, List<String>> _listDataChild;
	private Logs_ mLogs;
	private DisplayImageOptions options;
	private AnimateFirstDisplayListener animateFirstListener;
	private ImageLoader imageLoader;
	private String[] images;
	int sendScreenHeight ;
	
	public ExpandableListAdapter(MainCategory context, List<String> listDataHeader,
			HashMap<String, List<String>> listChildData , List<String> listDataHeaderImages , int sendScreenHeight) {
		this._context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listChildData;
		this.sendScreenHeight = sendScreenHeight ; 
		
		mLogs = new Logs_("ExpandableListAdapter") ;
		
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
		.build();
		
		images = listDataHeaderImages.toArray(new String[listDataHeaderImages.size()]);
	}
	
	

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		ViewHolderItemChild viewHolder_child;

		final String childText = (String) getChild(groupPosition, childPosition);

		if (convertView == null) {

			viewHolder_child = new ViewHolderItemChild();

			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.adapter_expandable_child, null);

			viewHolder_child.child_txt_productName = (TextView) convertView.findViewById(R.id.lblListItem);
			TypeFace_TextView.setTypeFace(_context, viewHolder_child.child_txt_productName);

			convertView.setTag(viewHolder_child);
		}
		else {
			viewHolder_child = (ViewHolderItemChild) convertView.getTag();
		}

		viewHolder_child.child_txt_productName.setText(childText);

		return convertView;
	}
	
	public int getScreenHeight ()
	{
		WindowManager wm = (WindowManager) _context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();		
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		
		return height ;
	}
	
	public int getScreenWidth ()
	{
		WindowManager wm = (WindowManager) _context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();		
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		
		return width ;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this._listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		
		ViewHolderItemGroup viewHolder_group;

		
		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null) {
			viewHolder_group = new ViewHolderItemGroup();

			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.adapter_expandable_group, null);
			
			viewHolder_group.group_txt_productName = (TextView) convertView.findViewById(R.id.txt_productName);
			viewHolder_group.group_txt_productImage = (ImageView) convertView.findViewById(R.id.group_txt_productImage);
			
			TypeFace_TextView.setTypeFace(_context, viewHolder_group.group_txt_productName);
			
//			mLogs.showTempLog("Screen height : "+getScreenHeight());
			convertView.setMinimumHeight(sendScreenHeight/2);
			
			convertView.setTag(viewHolder_group);
		}
		else {
			viewHolder_group = (ViewHolderItemGroup) convertView.getTag();
		}

		viewHolder_group.group_txt_productName.setText(headerTitle);
		imageLoader.displayImage(images[groupPosition], viewHolder_group.group_txt_productImage, options, animateFirstListener);

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	static class ViewHolderItemGroup {
		TextView group_txt_productName;	
		ImageView group_txt_productImage;	
		
	}
	static class ViewHolderItemChild {
		TextView child_txt_productName;	
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

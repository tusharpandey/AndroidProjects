package com.little_flora.Dialogs;

import java.io.File;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.little_flora.R;
import com.little_flora.Activity.ProductDescription;
import com.little_flora.Activity.ProductList_Grid;
import com.little_flora.Activity.Utils.TypeFace_TextView;

public class DialogForSharing_ProductDescription extends Dialog implements
android.view.View.OnClickListener{

	private TextView txt_sharing_sharewith;
	private TextView txt_sharing_sharewith_facebook;
	private TextView txt_sharing_sharewith_twitter;
	private TextView txt_sharing_sharewith_instagram;
	private TextView txt_sharing_sharewith_pintrest;

	ProductList_Grid context ;
	String productName ;
	String productDescription;
	
	public DialogForSharing_ProductDescription(ProductList_Grid context,String productName , String productDescription) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context ;
		this.productDescription = productDescription ;
		this.productName = productName ;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_sharing_sharewith_facebook:
			//context.newFaceBookImplementation() ;
			this.dismiss();
			break;
		case R.id.txt_sharing_sharewith_twitter:
			sendImageWithSharingMethod("twitter") ;
			this.dismiss();
			break;
		case R.id.txt_sharing_sharewith_instagram:
			sendImageWithSharingMethod("instagram") ;
			this.dismiss();
			break;
		case R.id.txt_sharing_sharewith_pintrest:
			sendImageWithSharingMethod("pinterest") ;
			this.dismiss();
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_sharing);
		

		initView();
	}
	
	public void initView()
	{
		txt_sharing_sharewith = ( TextView ) findViewById(R.id.txt_sharing_sharewith);
		TypeFace_TextView.setTypeFace(context, txt_sharing_sharewith);
		
		txt_sharing_sharewith_facebook = ( TextView ) findViewById(R.id.txt_sharing_sharewith_facebook);
		TypeFace_TextView.setTypeFace(context, txt_sharing_sharewith_facebook);
		txt_sharing_sharewith_facebook.setOnClickListener(this);

		txt_sharing_sharewith_twitter = ( TextView ) findViewById(R.id.txt_sharing_sharewith_twitter);
		TypeFace_TextView.setTypeFace(context, txt_sharing_sharewith_twitter);
		txt_sharing_sharewith_twitter.setOnClickListener(this);

		txt_sharing_sharewith_instagram = ( TextView ) findViewById(R.id.txt_sharing_sharewith_instagram);
		TypeFace_TextView.setTypeFace(context, txt_sharing_sharewith_instagram);
		txt_sharing_sharewith_instagram.setOnClickListener(this);

		txt_sharing_sharewith_pintrest = ( TextView ) findViewById(R.id.txt_sharing_sharewith_pintrest);
		TypeFace_TextView.setTypeFace(context, txt_sharing_sharewith_pintrest);
		txt_sharing_sharewith_pintrest.setOnClickListener(this);
	}
	
	public void sendImageWithSharingMethod(String searchText) {

		try {
			File myFile = new File(Environment.getExternalStorageDirectory()
					+ File.separator + "test.jpg");
			MimeTypeMap mime = MimeTypeMap.getSingleton();
			String ext = myFile.getName().substring(
					myFile.getName().lastIndexOf(".") + 1);
			String type = mime.getMimeTypeFromExtension(ext);

			Intent sharingIntent = new Intent("android.intent.action.SEND");
			sharingIntent.setType(type);
			sharingIntent.putExtra("android.intent.extra.STREAM",
					Uri.fromFile(myFile));
			
			sharingIntent.putExtra(Intent.EXTRA_TEXT   , productName);
			boolean found = false;

			List<ResolveInfo> resInfo = context.getPackageManager()
					.queryIntentActivities(sharingIntent, 0);
			
			if (!resInfo.isEmpty()) 
			{
				for (ResolveInfo info : resInfo) {
					if (info.activityInfo.packageName.toLowerCase().contains(
							searchText)
							|| info.activityInfo.name.toLowerCase().contains(
									searchText)) {
						sharingIntent.setPackage(info.activityInfo.packageName);
						found = true;
						break;
					}
				}
				if (!found) {
					Toast.makeText(context, searchText+" is not present in your system", Toast.LENGTH_SHORT).show();

					return;
				}
				context.startActivity(Intent.createChooser(sharingIntent,
						"Select"));
			}

		} catch (Exception e) {
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	
}

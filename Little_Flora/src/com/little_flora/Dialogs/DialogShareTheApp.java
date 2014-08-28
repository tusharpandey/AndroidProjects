package com.little_flora.Dialogs;


import java.io.File;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import com.little_flora.R;
import com.little_flora.Activity.BaseActivity;
import com.little_flora.Activity.WebViewActivity;
import com.little_flora.Activity.Utils.TypeFace_TextView;

public class DialogShareTheApp extends Dialog implements android.view.View.OnClickListener{

	BaseActivity context ;

	private TextView txt_sharing_sharewith;
	private TextView txt_sharing_sharewith_facebook;
	private TextView txt_sharing_sharewith_twitter;
	private TextView txt_sharing_sharewith_linkedin ;
	private TextView txt_sharing_sharewith_whatsapp ;
	
	public DialogShareTheApp(BaseActivity context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context ;
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_sharetheapp);
		
		initView();
	}
	
	
	public void initView ()
	{
		txt_sharing_sharewith = ( TextView ) this.findViewById(R.id.txt_sharing_sharewith);
		TypeFace_TextView.setTypeFace(context, txt_sharing_sharewith);

		txt_sharing_sharewith_facebook = ( TextView ) this.findViewById(R.id.txt_sharing_sharewith_facebook);
		TypeFace_TextView.setTypeFace(context, txt_sharing_sharewith_facebook);
		txt_sharing_sharewith_facebook.setOnClickListener(this);

		txt_sharing_sharewith_twitter = ( TextView ) this.findViewById(R.id.txt_sharing_sharewith_twitter);
		TypeFace_TextView.setTypeFace(context, txt_sharing_sharewith_twitter);
		txt_sharing_sharewith_twitter.setOnClickListener(this);

		txt_sharing_sharewith_linkedin  = ( TextView ) this.findViewById(R.id.txt_sharing_sharewith_linkedin);
		TypeFace_TextView.setTypeFace(context, txt_sharing_sharewith_linkedin);
		txt_sharing_sharewith_linkedin.setOnClickListener(this);

		txt_sharing_sharewith_whatsapp  = ( TextView ) this.findViewById(R.id.txt_sharing_sharewith_whatsapp);
		TypeFace_TextView.setTypeFace(context, txt_sharing_sharewith_whatsapp);
		txt_sharing_sharewith_whatsapp.setOnClickListener(this);
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_sharing_sharewith_facebook:
			sendImageWithSharingMethod("facebook");
			break;
		case R.id.txt_sharing_sharewith_twitter:
			sendImageWithSharingMethod("twitter");
			break;
		case R.id.txt_sharing_sharewith_linkedin:
			sendImageWithSharingMethod("linkedin");
			break;
		case R.id.txt_sharing_sharewith_whatsapp:
			sendImageWithSharingMethod("whatsapp");
			break;

		default:
			break;
		}
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
/*			sharingIntent.putExtra("android.intent.extra.STREAM",
					Uri.fromFile(myFile));
*/			
			String productName = "App Store : "+"https://www.iconfinder.com/"
									+"\n"+
								 "Google play store : "+"https://www.iconfinder.com/" ;
			
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


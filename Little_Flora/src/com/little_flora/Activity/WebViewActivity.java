package com.little_flora.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.little_flora.R;

public class WebViewActivity extends Activity {
	private WebView webView;
	 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
 
		webView = (WebView) findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(getIntent().getStringExtra("URL"));
		this.finish();
	}
}

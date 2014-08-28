/*package com.little_flora.User;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.little_flora.R;
 
public class TwitterLogin extends Activity {
    // Constants
    *//**
     * Register your here app https://dev.twitter.com/apps/new and get your
     * consumer key and secret
     * *//*
    static String TWITTER_CONSUMER_KEY = "9WFrkZliseqoziyUhRaDaCMf8";
    static String TWITTER_CONSUMER_SECRET = "tcjTKrpZzWIv6rzkeiuC5qkPpnErHwDF77kXM2pS8ZvDC0GFTh";
 
    // Preference Constants
    static String PREFERENCE_NAME = "twitter_oauth";
    static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
    static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
    static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";
 
    static final String TWITTER_CALLBACK_URL = "oauth://t4jsample";
 
    // Twitter oauth urls
    static final String URL_TWITTER_AUTH = "https://api.twitter.com/oauth/authorize";
    static final String URL_TWITTER_OAUTH_VERIFIER = "https://api.twitter.com/oauth/authorize";
    static final String URL_TWITTER_OAUTH_TOKEN = "https://api.twitter.com/oauth/access_token";
 
    // Twitter
    private static Twitter twitter;
    private static RequestToken requestToken;
     
    // Shared Preferences
    private static SharedPreferences mSharedPreferences;

 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
         
 
        // Shared Preferences
        mSharedPreferences = getApplicationContext().getSharedPreferences(
                "MyPref", 0);
 
        loginToTwitter();

        if (!isTwitterLoggedInAlready()) {
            Uri uri = getIntent().getData();
            if (uri != null && uri.toString().startsWith(TWITTER_CALLBACK_URL)) {
                // oAuth verifier
                String verifier = uri
                        .getQueryParameter(URL_TWITTER_OAUTH_VERIFIER);
 
                try {
                    // Get the access token
                    AccessToken accessToken = twitter.getOAuthAccessToken(
                            requestToken, verifier);
 
                    // Shared Preferences
                    Editor e = mSharedPreferences.edit();
 
                    // After getting access token, access token secret
                    // store them in application preferences
                    e.putString(PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
                    e.putString(PREF_KEY_OAUTH_SECRET,
                            accessToken.getTokenSecret());
                    // Store login status - true
                    e.putBoolean(PREF_KEY_TWITTER_LOGIN, true);
                    e.commit(); // save changes
 
                    Log.e("Twitter OAuth Token", "> " + accessToken.getToken());
 
                    long userID = accessToken.getUserId();
                    User user = twitter.showUser(userID);
                    String username = user.getName();
                     
                    Log.e("Twitter OAuth userID", "> " + userID);
                    Log.e("Twitter OAuth username", "> " + username);
                } catch (Exception e) {
                    // Check log for login errors
                    Log.e("Twitter Login Error", "> " + e.getMessage());
                }
            }
        }
 
    }
 
    *//**
     * Function to login twitter
     * *//*
    private void loginToTwitter() {
        
    	new AsyncTask<String, String, String>() {

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
		        if (!isTwitterLoggedInAlready()) {
		            ConfigurationBuilder builder = new ConfigurationBuilder();
		            builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
		            builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
		            Configuration configuration = builder.build();
		             
		            TwitterFactory factory = new TwitterFactory(configuration);
		            twitter = factory.getInstance();
		 
		            try {
		                requestToken = twitter
		                        .getOAuthRequestToken(TWITTER_CALLBACK_URL);
		                TwitterLogin.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri
		                        .parse(requestToken.getAuthenticationURL())));
		            } catch (TwitterException e) {
		                e.printStackTrace();
		            }
		        } else {
		            // user already logged into twitter
		            Toast.makeText(getApplicationContext(),
		                    "Already Logged into twitter", Toast.LENGTH_LONG).show();
		        }
		        
		        return null ;
			}
		}.execute("") ;
    	

    }
 
    private boolean isTwitterLoggedInAlready() {
        // return twitter login status from Shared Preferences
        return mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
    }
 
    protected void onResume() {
        super.onResume();
    }
 
}*/
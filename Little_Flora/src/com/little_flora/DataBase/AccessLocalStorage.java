package com.little_flora.DataBase;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class AccessLocalStorage extends ContentProvider
{

	private LocalStorage db_cc;
	
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		db_cc = new LocalStorage(getContext(), "littleflora");
		db_cc.getWritableDatabase();
		
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		
		String databaseURI = uri.toString() ;
		Cursor cur = null ;
		if ( databaseURI.contains("category_subcategory_menu") )
		{
			
		}
		else if ( databaseURI.contains("category_subcategory_en") )
		{
			
		}
		else if ( databaseURI.contains("category_subcategory_ar") )
		{
			
		}
		
		return cur;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		
		if ( values.getAsString("TableName").equals("category_subcategory_menu") )
		{
			values.remove("TableName") ;
			db_cc.getWritableDatabase().insert("category_subcategory_menu", null, values);
		}
		else if ( values.getAsString("TableName").equals("category_subcategory_en") )
		{
			values.remove("TableName") ;
			db_cc.getWritableDatabase().insert("category_subcategory_en", null, values);
		}
		else if ( values.getAsString("TableName").equals("category_subcategory_ar") )
		{
			values.remove("TableName") ;
			db_cc.getWritableDatabase().insert("category_subcategory_ar", null, values);
		}
		
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}}

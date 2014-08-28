
package com.little_flora.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalStorage extends SQLiteOpenHelper{
	
	private final String category_subcategory_menu = "CREATE TABLE "
			+ "category_subcategory_menu" + 
			"(" 
				+ "no varchar(100) primary key" + "," 
				+ "english varchar(100)" + "," 
				+ "arabic varchar(2000)" + 
			");";
	
	private final String category_subcategory_en = "CREATE TABLE "
			+ "category_subcategory_en" + 
			"(" 
				+ "id varchar(100) primary key" + "," 
				+ "title varchar(100)" + "," 
				+ "Active varchar(2000)" + ","  
				+ "InMenu varchar(2000)" + ","  
				+ "image varchar(2000)" + ","  
				+ "child_image varchar(2000)" + ","  
				+ "child_subcatid varchar(2000)" + ","  
				+ "child_subcatitle varchar(2000)" + ","  
				+ "child_productcount varchar(2000)" +  
			");";

	private final String category_subcategory_ar = "CREATE TABLE "
			+ "category_subcategory_ar" + 
			"(" 
				+ "id varchar(100) primary key" + "," 
				+ "title varchar(100)" + "," 
				+ "Active varchar(2000)" + ","  
				+ "InMenu varchar(2000)" + ","  
				+ "image varchar(2000)" + ","  
				+ "child_image varchar(2000)" + ","  
				+ "child_subcatid varchar(2000)" + ","  
				+ "child_subcatitle varchar(2000)" + ","  
				+ "child_productcount varchar(2000)" +  
			");";

	public LocalStorage(Context context, String name) {
		super(context, name, null, 2);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(category_subcategory_menu);
		db.execSQL(category_subcategory_en);
		db.execSQL(category_subcategory_ar);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}


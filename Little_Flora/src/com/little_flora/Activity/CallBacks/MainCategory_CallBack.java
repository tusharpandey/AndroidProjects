package com.little_flora.Activity.CallBacks;

import java.util.Dictionary;

public interface MainCategory_CallBack {
	/*
	 * Method used for fetching response of Category Data & SubCategory Data .
	 * 
	 * Parameters : 
	 * 				mainCategoryDataList : with its id .
	 * 				subCategoryDataWholeDict : all SubCategory and mainCategory Data present in this .
	 */
	public void responseOfMainCategoryCallBack( Dictionary<String, Dictionary<String, String>> mainCategoryDataList , Dictionary<String, Dictionary<String, Dictionary<String, String>>> subCategoryDataWholeDict  ) ;
}

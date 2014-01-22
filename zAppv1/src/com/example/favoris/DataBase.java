package com.example.favoris;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.favoris.FeedReaderContractFavoris.FeedEntry;

public class DataBase {
	private static final String TAG = "MyActivity";
	public static String id;
	public static boolean favorite;
	public DataBase(){
		
	}
	public static String getId() {
		return id;
	}
	public static void setId(String id) {
		DataBase.id = id;
	}
	public static boolean isFavorite() {
		return favorite;
	}
	public static void setFavorite(boolean favorite) {
		DataBase.favorite = favorite;
	}
	

	
}

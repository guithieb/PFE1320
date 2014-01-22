package com.example.favoris;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.favoris.FeedReaderContractFavoris.FeedEntry;

public class DataBase {
	private static final String TAG = "MyActivity";
	public String id;
	public DataBase(){
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}

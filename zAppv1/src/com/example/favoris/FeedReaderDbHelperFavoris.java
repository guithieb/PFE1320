package com.example.favoris;

import java.util.ArrayList;
import com.example.favoris.FeedReaderContractFavoris.FeedEntry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class FeedReaderDbHelperFavoris extends SQLiteOpenHelper{

	// If you change the database schema, you must increment the database version.
	private static final String TAG = "MyActivity";
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "FeedReader.db";

	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_ENTRIES =
			"CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
					FeedEntry._ID + " INTEGER PRIMARY KEY," +
					FeedEntry.COLUMN_NAME_ID + TEXT_TYPE +
					// Any other options for the CREATE command
					" )";

	private static final String SQL_DELETE_ENTRIES =
			"DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

	public FeedReaderDbHelperFavoris(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// This database is only a cache for online data, so its upgrade policy is
		// to simply to discard the data and start over
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
	}
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}

	public void deleteAllFavoris(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
	}

	/***************************************
	 * 
	 * 
	 * 
	 * @return All favoris in the database
	 */


public boolean checkDataBase() {
	SQLiteDatabase checkDB = null;
	try {
		checkDB = SQLiteDatabase.openDatabase(SQL_CREATE_ENTRIES, null, SQLiteDatabase.OPEN_READONLY);
		checkDB.close();
	} catch (SQLiteException e) {
		// base de données n'existe pas.
	}
	return checkDB != null ? true : false;
}
}
package com.example.recommandation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.recommandation.FeedReaderContractReco.FeedEntry;

public class FeedReaderDbHelperReco extends SQLiteOpenHelper{
	
	// If you change the database schema, you must increment the database version.
	private static final String TAG = "MyActivity";
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "RecoFeedReader.db";

	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	
	private static final String SQL_CREATE_ENTRIES =
			"CREATE TABLE " + FeedEntry.TABLE_NAME + "(" +
					FeedEntry._ID + "INTEGER PRIMARY KEY," +
					FeedEntry.COLUMN_NAME_ID + TEXT_TYPE +
					FeedEntry.COLUMN_NAME_GENRE + TEXT_TYPE +
					" )";

	private static final String SQL_DELETE_ENTRIES =
			"DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
	
	public FeedReaderDbHelperReco(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(SQL_CREATE_ENTRIES);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
	}
	
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}
	
	
	
}

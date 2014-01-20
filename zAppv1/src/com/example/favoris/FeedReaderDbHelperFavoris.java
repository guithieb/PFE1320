package com.example.favoris;

import java.util.ArrayList;

import com.example.cloud.EPGChaine;
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

	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";

	private static final String SQL_CREATE_ENTRIES =
			"CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
					FeedEntry._ID + " INTEGER PRIMARY KEY," +
					FeedEntry.COLUMN_NAME_ID + TEXT_TYPE + COMMA_SEP +
					" )";


	private static final String SQL_DELETE_ENTRIES =
			"DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;


	public FeedReaderDbHelperFavoris(Context context) {
		super(context, FeedEntry.TABLE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// This database is only a cache for online data, so its upgrade policy is
		// to simply to discard the data and start over
		db.execSQL(SQL_DELETE_ENTRIES);
		this.onCreate(db);
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

	public ArrayList<DataBase> getallDataBase(String sorting){

		ArrayList<DataBase> epgchaines = new ArrayList<DataBase>();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
				BaseColumns._ID,
				FeedEntry.COLUMN_NAME_ID,
		};

		// How you want the results sorted in the resulting Cursor
		String sortOrder =
				FeedEntry.COLUMN_NAME_ID + " DESC";

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(
				FeedEntry.TABLE_NAME,  // The table to query
				projection,                               // The columns to return
				null,                                // The columns for the WHERE clause
				null,                            // The values for the WHERE clause
				null,                                     // don't group the rows
				null,                                     // don't filter by row groups
				sortOrder                                 // The sort order
				);

		if(cursor.moveToFirst()){
			do{
				DataBase epgchaine = new DataBase();  //parametre a rentrer
				DataBase.setId(cursor.getString(
						cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_ID)));
				epgchaines.add(epgchaine);
			}while(cursor.moveToNext());
		}
		db.close();
		return epgchaines;
	}

	public void saveFavoris(String channel){
		// Gets the data repository in write mode
		Log.d(TAG,"BDD TRANSFERT" + channel);
		SQLiteDatabase db = this.getWritableDatabase();
		
		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(FeedEntry.COLUMN_NAME_ID , channel );

		// Insert the new row, returning the primary key value of the new row
		long newRowId;
		newRowId = db.insert(
				FeedEntry.TABLE_NAME,
				null,
				values);
		Log.d(TAG,"BDD TRANSFERT");
		db.close();
	}


	public Boolean isInDB(String imdbId){

		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM " + FeedEntry.TABLE_NAME;
		System.out.println("REQUEST DB:"+selectQuery);
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.toString().isEmpty()){
			return false;
		}
		else{
			/*String selectQuery2 = "SELECT * FROM " + FeedEntry.TABLE_NAME + "WHERE "+FeedEntry.COLUMN_NAME_ID + " = '" + imdbId + "'";
			Cursor cursor2 = db.rawQuery(selectQuery2, null);
			if(cursor2.getCount() !=0){
				db.close();
				return true;
			}else{
				db.close();
				return false;			
			}*/
			return true;
		}
	}

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
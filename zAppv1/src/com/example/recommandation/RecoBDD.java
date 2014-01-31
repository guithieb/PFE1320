package com.example.recommandation;

import java.util.ArrayList;

import com.example.recommandation.FeedReaderContractReco.FeedEntry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RecoBDD {

	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "reco.db";
	private static final String TAG = "activity";
	
 
	private SQLiteDatabase bdd;
 
	private FeedReaderDbHelperReco baseReco;
	
	public  RecoBDD(Context context){
		//On crŽer la BDD et sa table
		baseReco = new FeedReaderDbHelperReco(context, NOM_BDD, null, VERSION_BDD);
	}
	
	public void open(){
		//on ouvre la BDD en Žcriture
		bdd = baseReco.getWritableDatabase();
	}
	
	public void delete(Context context){
		new FeedReaderDbHelperReco(context, NOM_BDD, null, VERSION_BDD ).onDelete(bdd);
	}
	
	public void close(){
		//on ferme l'acc�s ˆ la BDD
		bdd.close();
	}
	
	public SQLiteDatabase getBDD(){
		return bdd;
	}
	
	public long insertPref(DataBaseReco dbReco){
		//CrŽation d'un ContentValues (fonctionne comme une HashMap)
		ContentValues values = new ContentValues();
		//on lui ajoute une valeur associŽ ˆ une clŽ (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
		values.put(FeedEntry.COLUMN_NAME_GENRE, dbReco.getGenre());
		values.put(FeedEntry.COLUMN_NAME_ORDREPREF, dbReco.getOrdrepref());
		//on ins�re l'objet dans la BDD via le ContentValues
		return bdd.insert(FeedEntry.TABLE_NAME, null, values);
	}
	
	public int removeLivreWithGenre(String genre){
		//Suppression d'un livre de la BDD gr‰ce ˆ l'ID
		return bdd.delete(FeedEntry.TABLE_NAME, FeedEntry.COLUMN_NAME_GENRE + " = " +genre, null);
	}
	
	public int getCount() {
	    String countQuery = "SELECT  * FROM " + FeedEntry.TABLE_NAME;
	     bdd = baseReco.getReadableDatabase();
	    Cursor cursor = bdd.rawQuery(countQuery, null);
	    int cnt = cursor.getCount();
	    cursor.close();
	    return cnt;
	}
	
	public ArrayList<String> cursorToReco( )
	{
	  ArrayList<String> listGenre = new ArrayList<String>();
	  String getAllQuery = "SELECT * FROM " + FeedEntry.TABLE_NAME;
	  bdd = baseReco.getReadableDatabase();
	  Cursor cursor = bdd.rawQuery(getAllQuery,null);
	  
	  if (cursor.getCount() == 0)
      return null;
	  
	  if (cursor.moveToFirst()) {

      while (cursor.isAfterLast() == false) {
          String genre = cursor.getString(cursor
                  .getColumnIndex(FeedEntry.COLUMN_NAME_GENRE));

          listGenre.add(genre);
          cursor.moveToNext();
      }
  }
	  
	  Log.d(TAG,"GENRELIST"+listGenre.toString());
	  return listGenre;
	}
	
	
}

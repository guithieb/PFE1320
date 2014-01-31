package com.example.recommandation;

import java.util.ArrayList;

import com.example.favoris.DataBase;
import com.example.favoris.FeedReaderDbHelperFavoris;
import com.example.recommandation.FeedReaderContractReco.FeedEntry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

public class RecoBDD {

	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "reco.db";
	private static final String LOG_TAG = "activity";

 
	
 
	private SQLiteDatabase bdd;
 
	private FeedReaderDbHelperReco baseReco;
	
	public  RecoBDD(Context context){
		//On créer la BDD et sa table
		baseReco = new FeedReaderDbHelperReco(context, NOM_BDD, null, VERSION_BDD);
	}
	
	public void open(){
		//on ouvre la BDD en écriture
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
		//Création d'un ContentValues (fonctionne comme une HashMap)
		ContentValues values = new ContentValues();
		//on lui ajoute une valeur associé ˆ une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
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
	
	//récupération de la base de données
	public ArrayList<String> getallDataBase(){
		ArrayList<String> epg = new ArrayList<String>();
		ArrayList<DataBaseReco> datas = new ArrayList<DataBaseReco>();

		
		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
				BaseColumns._ID,
				FeedEntry.COLUMN_NAME_GENRE,
		};

		// How you want the results sorted in the resulting Cursor
		String sortOrder =
				FeedEntry.COLUMN_NAME_ORDREPREF + " ASC";

		bdd = baseReco.getReadableDatabase();

		Cursor cursor = bdd.query(
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
				DataBaseReco chaine = new DataBaseReco();  //parametre a rentrer
				chaine.setId(cursor.getString(
						cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_GENRE)));
				datas.add(chaine);
			}while(cursor.moveToNext());
			//copie de la base de données dans un String à envoyer au thread
			for (int i = 0 ; i < datas.size(); i++)
			{
					epg.add(datas.get(i).getGenre());
			}
		}

		return epg;
	}
	
}

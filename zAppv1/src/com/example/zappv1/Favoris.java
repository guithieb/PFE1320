package com.example.zappv1;

import android.os.Bundle;
import android.provider.BaseColumns;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;

import com.example.cloud.EPGChaine;
import com.example.favoris.DataBase;
import com.example.favoris.FeedReaderDbHelperFavoris;
import com.example.favoris.GetFavorisTask;
import com.example.favoris.favorisadapter;
import com.example.favoris.getFavoriTask;
import com.example.favoris.FeedReaderContractFavoris.FeedEntry;
import com.example.favoris.previewFavoris;
import com.example.zappv1.R;

public class Favoris extends Fragment{
	
	public Favoris(){
		
	}
	
	private ListView listeFavori;
	private String channels;
	TextView NonFavori;
	ArrayList<EPGChaine> epgfavoris = new ArrayList<EPGChaine>();
	EPGChaine favori = new EPGChaine();
	favorisadapter adapter;
	EPGChaine item; 
	private static final String TAG = "MyActivity";
/*	public static Fragment newInstance(Context context){
		Favoris f = new Favoris();
    	 
        return f;
	}*/
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) { 
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.favoris, null);
        listeFavori = (ListView) root.findViewById(R.id.chaines);
        //récupération du contenu de la base de données
        
        channels = getallDataBase();
        Log.d(TAG,"TASK RIGHT OK"+ channels);
        
        //affichage de la liste des favoris en fonction de la base de données
        if (channels.equals("")){
        	AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
            builder1.setMessage("Aucun favoris enregistrés.");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
           

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
        else if (channels.equals("1")||channels.equals("2")||channels.equals("3")||channels.equals("4")||
        		channels.equals("5")||channels.equals("6")||channels.equals("7")||channels.equals("8")||
        		channels.equals("9")||channels.equals("10")||channels.equals("11")||channels.equals("12")||
        		channels.equals("13")||channels.equals("14")||channels.equals("15")||channels.equals("16")||
        		channels.equals("17")||channels.equals("18")||channels.equals("19")){
        	adapter = new favorisadapter(getActivity(), epgfavoris, this);  
        	listeFavori.setAdapter(adapter);
        	refreshFavori();
        }else{
        	adapter = new favorisadapter(getActivity(), epgfavoris, this);  
        	listeFavori.setAdapter(adapter);
        	refreshChaine();
        }
        
      //évenement lorsque que l'on clique sur une chaîne dans la lsite
        listeFavori.setOnItemClickListener(new OnItemClickListener()
        {
          @Override
          public void onItemClick(AdapterView<?> arg0, View v, int position, long id)
          {
            Intent intent = new Intent(getActivity(), previewFavoris.class);
            item = (EPGChaine) arg0.getItemAtPosition(position);
            intent.putExtra("listefavori", channels);
            //Envoi du nom de la chaine à la vue prévisualisation
            intent.putExtra("chaineNom",item.getNom());
            //Envoi de l'id de la chaîne
            intent.putExtra("chaineId", item.getId());
            //envoi de l'id du programme
            intent.putExtra("progid", item.getListeProgrammes().getProgrammes().getId());
            intent.putExtra("progFin", item.getListeProgrammes().getProgrammes().getFin());

            
            // Log.d(LOG_TAG,"PROG "+prog.toString());
             startActivity(intent);
          }
             
        });
        
        
        return root;
    }
    
    private void refreshChaine() {
    	// TODO Auto-generated method stub
    	new GetFavorisTask(epgfavoris, adapter, getActivity(), channels).execute();
    	
    	}
    
    private void refreshFavori() {
    	// TODO Auto-generated method stub
    	new getFavoriTask(epgfavoris, adapter, getActivity(), channels).execute();
    	}
    
	public String getallDataBase(){
		String epg = "";
		ArrayList<DataBase> datas = new ArrayList<DataBase>();
		
		FeedReaderDbHelperFavoris mDbHelper = new FeedReaderDbHelperFavoris(getActivity());
		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
				BaseColumns._ID,
				FeedEntry.COLUMN_NAME_ID,
		};

		// How you want the results sorted in the resulting Cursor
		String sortOrder =
				FeedEntry.COLUMN_NAME_ID + " ASC";

		SQLiteDatabase db = mDbHelper.getReadableDatabase();

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
				DataBase chaine = new DataBase();  //parametre a rentrer
				chaine.setId(cursor.getString(
						cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_ID)));
				datas.add(chaine);
			}while(cursor.moveToNext());
			Log.d(TAG,"FAVORISFA"+ datas.toString());
			for (int i = 0 ; i < datas.size(); i++)
			{
				if (epg.equals("")){
					epg = epg + datas.get(i).getId();
				}
				else {
					if(epg.contains(datas.get(i).getId())){
						epg = epg;
					}
					else epg = epg + "," + datas.get(i).getId();
				}	
			}
		}
		return epg;
	}
	
}


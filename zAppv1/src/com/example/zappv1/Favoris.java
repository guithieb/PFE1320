package com.example.zappv1;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.cloud.EPGChaine;
import com.example.favoris.GetFavorisTask;
import com.example.favoris.favorisadapter;
import com.example.favoris.getFavoriTask;
import com.example.zappv1.R;

public class Favoris extends Fragment{
	
	private ListView listeFavori;
	private String channels;
	TextView NonFavori;
	ArrayList<EPGChaine> epgfavoris = new ArrayList<EPGChaine>();
	EPGChaine favori = new EPGChaine();
	favorisadapter adapter;
	public static Fragment newInstance(Context context){
		Favoris f = new Favoris();
    	 
        return f;
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) { 
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.favoris, null);
        listeFavori = (ListView) root.findViewById(R.id.chaines);
        channels = "";
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
}


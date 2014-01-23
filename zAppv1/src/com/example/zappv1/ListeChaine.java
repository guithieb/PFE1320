package com.example.zappv1;

import java.util.ArrayList;
import java.util.LinkedList;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloud.ChaineAdapter;
import com.example.cloud.EPGChaine;
import com.example.cloud.EPGChaine.ListeProgramme;
import com.example.cloud.EPGChaines;
import com.example.cloud.GetProgramTask;
import com.example.remote.ServerException;
import com.example.zappv1.R;

/**
 * Vue regroupant toutes les chaines disponibles sur la box
 * Permet ensuite d'accéder à la prévisualisation d'une chaîne
 * 
 * 
 */

public class ListeChaine extends Fragment{

  public static final String LOG_TAG = "debug";
  private GridView listeChaine;
  ArrayList<EPGChaine> epgChaines = new ArrayList<EPGChaine>();
  final String ID_CHAINE = "id_chaine";
  EPGChaine item;  
  final String baseurlEPG = "http://openbbox.flex.bouyguesbox.fr:81/V0";
  EPGChaine id;
  ArrayList<EPGChaines> epgs = new ArrayList<EPGChaines>();
 
  //CloudApi epg;
  //final String baseurlEPG = "http://openbbox.flex.bouyguesbox.fr:81/V0";
  ChaineAdapter adapter;


  public static Fragment newInstance(Context context){
    ListeChaine f = new ListeChaine();

    return f;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) { 
    ViewGroup root = (ViewGroup) inflater.inflate(R.layout.liste_chaine, container,false);
    //Initialisation de la List qui regroupe tout les noms des chaines
    listeChaine = (GridView) root.findViewById(R.id.chaines);
    adapter = new ChaineAdapter(getActivity(), epgChaines, this);  
    listeChaine.setAdapter(adapter);
    refreshChaine();
    
    
    //évenement lorsque que l'on clique sur une chaîne dans la lsite
    listeChaine.setOnItemClickListener(new OnItemClickListener()
    {
      @Override
      public void onItemClick(AdapterView<?> arg0, View v, int position, long id)
      {
        Intent intent = new Intent(getActivity(), Preview.class);
        item = (EPGChaine) arg0.getItemAtPosition(position);

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
	new GetProgramTask(epgChaines, adapter, getActivity()).execute();
	
	}



}


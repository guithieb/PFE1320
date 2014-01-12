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
import android.widget.ListView;
import android.widget.TextView;

import com.example.cloud.ChaineAdapter;
import com.example.cloud.CloudApi;
import com.example.cloud.EPGChaine;
import com.example.cloud.GetProgramTask;
import com.example.cloud.SendRequestEPG;
import com.example.remote.ServerException;
import com.example.zappv1.R;

public class ListeChaine extends Fragment{

  public static final String LOG_TAG = "debug";
  private ListView listeChaine;
  ArrayList<EPGChaine> epgChaines = new ArrayList<EPGChaine>();
  final String ID_CHAINE = "id_chaine";
  CloudApi epg;
  final String baseurlEPG = "http://openbbox.flex.bouyguesbox.fr:81/V0";
  ChaineAdapter adapter;

  public static Fragment newInstance(Context context){
    ListeChaine f = new ListeChaine();

    return f;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) { 
    ViewGroup root = (ViewGroup) inflater.inflate(R.layout.liste_chaine, container,false);
    listeChaine = (ListView) root.findViewById(R.id.chaines);
    adapter = new ChaineAdapter(getActivity(), epgChaines, this);
    
    listeChaine.setAdapter(adapter);
    LinkedList item = new LinkedList<String>();
    refreshChaine();
   
    listeChaine.setOnItemClickListener(new OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> arg0, View v, int position, long id)
      {
        Intent intent = new Intent(getActivity(), Preview.class);
       String item = (String) arg0.getItemAtPosition(position);
       intent.putExtra("chaineID",item);
        startActivity(intent);
      }
      
      
     

    });
    //Log.d(LOG_TAG," CHAINES "+epgChaines.get(0).getId());
    return root;
  

    //ListView listdevice;

  }

private void refreshChaine() {
	// TODO Auto-generated method stub
	new GetProgramTask(epgChaines, adapter, getActivity()).execute();
	Log.d(LOG_TAG,"PROGRAM GSON");
	
	
}

}


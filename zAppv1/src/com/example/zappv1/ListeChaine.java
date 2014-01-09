package com.example.zappv1;

import java.util.LinkedList;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.example.zappv1.R;

public class ListeChaine extends Fragment{

  private ListView listeChaine;
  private String listview_array[] = { "TF1", "FR2", "FR3", "CANAL+", "ARTE", "M6"};
  final String ID_CHAINE = "id_chaine";





  public static Fragment newInstance(Context context){
    ListeChaine f = new ListeChaine();

    return f;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) { 
    ViewGroup root = (ViewGroup) inflater.inflate(R.layout.liste_chaine, null);
    listeChaine = (ListView) root.findViewById(R.id.chaines);
    listeChaine.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, listview_array));
    LinkedList item = new LinkedList<String>();

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

    return root;
  

    //ListView listdevice;

  }

}


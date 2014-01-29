package com.example.type;

import com.example.cloud.EPGChaine;
import com.example.favoris.previewFavoris;
import com.example.zappv1.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Type extends Fragment{

	public Type(){

	}

	String[] type = {"Film","Série","Téléfilm", "Magazine","Emission jeunesse", "Jeu", "Divertissement", "Documentaire","Information",
			"Musique", "Feuilleton", "Adulte"};
	private GridView listeType;

	public View onCreateView(LayoutInflater inflater,  ViewGroup container,Bundle savedInstanceState) { 
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.type, null);
		listeType = (GridView) root.findViewById(R.id.list);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_list_item_1, type);
		listeType.setAdapter(adapter);
		
		listeType.setOnItemClickListener(new OnItemClickListener()
        {
          @Override
          public void onItemClick(AdapterView<?> arg0, View v, int position, long id)
          {
            Intent intent = new Intent(getActivity(), DisplayByType.class);
            intent.putExtra("typeProg", type[position]);
                       
            intent.setClass(getActivity(), DisplayByType.class);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
          }
             
        });
		
		return root;
	}
}

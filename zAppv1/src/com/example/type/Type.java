package com.example.type;

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
import android.widget.AdapterView.OnItemClickListener;

public class Type extends Fragment{

	public Type(){

	}
	//liste des diff�rents types de programmes
	String[] type = {"Adulte", "Divertissement", "Documentaire", "Emission jeunesse", "Feuilleton", "Film",
			"Information", "Jeu", "Magazine", "Musique", "S�rie","T�l�film"};
	private GridView listeType;

	public View onCreateView(LayoutInflater inflater,  ViewGroup container,Bundle savedInstanceState) { 
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.type, null);
		listeType = (GridView) root.findViewById(R.id.list);
		//ajout des types au gridview
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
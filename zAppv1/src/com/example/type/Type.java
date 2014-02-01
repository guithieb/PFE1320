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
import android.widget.Button;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class Type extends Fragment{

	public Type(){

	}
	//liste des différents types de programmes
	/*String[] type = {"Divertissement", "Documentaire", "Emission jeunesse", "Feuilleton", "Film",
			"Information", "Jeu", "Magazine", "Musique", "Série", "Sport", "Téléfilm"};*/

	Button divertissement, documentaire, emissionj, feuilleton, film, information, jeu;
	Button magazine, musique, serie, sport, telefilm;

	public View onCreateView(LayoutInflater inflater,  ViewGroup container,Bundle savedInstanceState) { 
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.type, null);
		//ajout des types au gridview
		divertissement = (Button) root.findViewById(R.id.Divertissement);
		documentaire = (Button) root.findViewById(R.id.Documentaire);
		emissionj = (Button) root.findViewById(R.id.EmissionJeunesse);
		feuilleton = (Button) root.findViewById(R.id.Feuilleton);
		film = (Button) root.findViewById(R.id.Film);
		information = (Button) root.findViewById(R.id.Information);
		jeu = (Button) root.findViewById(R.id.Jeu);
		magazine = (Button) root.findViewById(R.id.Magazine);
		musique = (Button) root.findViewById(R.id.Musique);
		serie = (Button) root.findViewById(R.id.Serie);
		sport = (Button) root.findViewById(R.id.Sport);
		telefilm = (Button) root.findViewById(R.id.Telefilm);

		divertissement.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				typeClick(divertissement.getText().toString());
			}
		});
		documentaire.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				typeClick(documentaire.getText().toString());
			}
		});
		emissionj.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				typeClick(emissionj.getText().toString());
			}
		});
		feuilleton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				typeClick(feuilleton.getText().toString());
			}
		});
		film.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				typeClick(film.getText().toString());
			}
		});
		information.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				typeClick(information.getText().toString());
			}
		});
		jeu.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				typeClick(jeu.getText().toString());
			}
		});
		magazine.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				typeClick(magazine.getText().toString());
			}
		});
		musique.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				typeClick(musique.getText().toString());
			}
		});
		serie.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				typeClick(serie.getText().toString());
			}
		});
		sport.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				typeClick(sport.getText().toString());
			}
		});
		telefilm.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				typeClick(telefilm.getText().toString());
			}
		});

		return root;
	}
	
	
	
	public void typeClick (String str){
		Intent intent = new Intent(getActivity(), DisplayByType.class);
		intent.putExtra("typeProg", str);

		intent.setClass(getActivity(), DisplayByType.class);
		startActivity(intent);
		getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
	}
}

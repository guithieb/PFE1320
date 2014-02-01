package com.example.zappv1;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Informations extends Fragment{

	public Informations(){

	}
	ImageView zapp, bouygues, ece;
	TextView contact;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) { 
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.informations, null);
		zapp = (ImageView) root.findViewById(R.id.zapp);
		bouygues = (ImageView) root.findViewById(R.id.bouygues);
		ece = (ImageView) root.findViewById(R.id.ece);
		contact = (TextView) root.findViewById(R.id.contact);
		String mail = "arigno@ece.fr";
		zapp.setImageResource(R.drawable.zapp);
		bouygues.setImageResource(R.drawable.bouygues);
		ece.setImageResource(R.drawable.ic_ece);
		contact.setText("Nous contacter: "+ mail);
		
		

		return root;
	}

}

package com.example.zappv1;

import com.example.recommandation.DataBaseReco;
import com.example.recommandation.RecoBDD;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class GenreForm extends Activity {

	private  Button validate;
	private Spinner list, list2, list3;
	private ArrayAdapter <String> adapter;
	private static String [] genre = new String [] {"Choisissez un type de programme","Film","Série","Téléfilm", "Magazine","Emission jeunesse", "Jeu", "Divertissement", "Documentaire","Information",
		"Musique", "Feuilleton", "Sport"};
	private static final String TAG = "activity";
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.genreform);

		AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
		builder1.setMessage("Bienvenue sur Zapp ! Afin de bénéficier d'une recommandation personnalisée, veuillez renseigner vos 3 types de programmes préférés.");
		builder1.setCancelable(true);
		builder1.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder1.create();
		alert.show();

		list = (Spinner) findViewById(R.id.spinnerForm);
		list2 = (Spinner) findViewById(R.id.spinnerForm2);
		list3 = (Spinner) findViewById(R.id.spinnerForm3);
		adapter = new ArrayAdapter<String> (this,android.R.layout.simple_spinner_item,genre);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		list.setAdapter(adapter);
		list2.setAdapter(adapter);
		list3.setAdapter(adapter);

		validate = (Button) findViewById(R.id.buttonValidateForm);



		validate.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v)
			{
				changeQuestion();
			}
		});


	}

	public void changeQuestion()
	{

		RecoBDD recoBdd  = new RecoBDD(this);;
		//recoBdd.delete(this);

		Intent intent;
		DataBaseReco reco = new DataBaseReco();

		recoBdd.open();

		int i = list.getSelectedItemPosition();
		int j = list2.getSelectedItemPosition();
		int k = list3.getSelectedItemPosition();
		int pref = recoBdd.getCount()+1;

		if((i == 0)||(j == 0)||(k == 0))
		{
			AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
			builder1.setMessage("Vous devez choisir un type de programme avant de valider.");
			builder1.setCancelable(true);
			builder1.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			});
			AlertDialog alert = builder1.create();
			alert.show();
		}

		if((i!=0)&&(j!=0)&&(k!=0))
		{
			if ((i==j)||(j==k)||(i==k)||(i==j&&i==k)){
				AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
				builder1.setMessage("Vous devez choisir 3 types de programme différents.");
				builder1.setCancelable(true);
				builder1.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
				AlertDialog alert = builder1.create();
				alert.show();
			}
			else{
				reco.setGenre(list.getItemAtPosition(i).toString());
				reco.setOrdrepref(Integer.toString(pref));
				recoBdd.insertPref(reco);
				reco.setGenre(list2.getItemAtPosition(j).toString());
				reco.setOrdrepref(Integer.toString(pref+1));
				recoBdd.insertPref(reco);
				reco.setGenre(list3.getItemAtPosition(k).toString());
				reco.setOrdrepref(Integer.toString(pref+2));
				recoBdd.insertPref(reco);
				recoBdd.close();
				finish();
			}

		}

	}

}

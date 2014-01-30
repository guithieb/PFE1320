package com.example.zappv1;

import com.example.recommandation.DataBaseReco;
import com.example.recommandation.RecoBDD;

import android.app.Activity;
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
	private Spinner list;
	private ArrayAdapter <String> adapter;
	private static String [] genre = new String [] {"Film","Série","Téléfilm", "Magazine","Emission jeunesse", "Jeu", "Divertissement", "Documentaire","Information",
		"Musique", "Feuilleton", "Adulte"};
	private static final String TAG = "activity";
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.genreform);
		
		list = (Spinner) findViewById(R.id.spinnerForm);
		adapter = new ArrayAdapter<String> (this,android.R.layout.simple_spinner_item,genre);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		list.setAdapter(adapter);
		
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
		int pref = recoBdd.getCount()+1;
		
		reco.setGenre(list.getItemAtPosition(i).toString());
		reco.setOrdrepref(Integer.toString(pref));
		
		recoBdd.insertPref(reco);
		
		if (recoBdd.getCount() == 1)
		{
			 TextView choix1 = (TextView) findViewById(R.id.type1);
			 choix1.setText("1- "+reco.getGenre());
			
		}
		
		if (recoBdd.getCount() == 2)
		{
			 TextView choix2 = (TextView) findViewById(R.id.type2);
			 choix2.setText("2- "+reco.getGenre());
			
		}
		
		if (recoBdd.getCount() == 3)
		{
			 TextView choix3 = (TextView) findViewById(R.id.type3);
			 choix3.setText("3- "+reco.getGenre());
			 finish();
	        recoBdd.close();
			
		}
	
		
		
        
        
        
        
        	
        
        	

	}
	
}

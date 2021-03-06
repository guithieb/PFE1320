package com.example.type;

import infoprog.BaseProgramme;
import infoprog.BaseProgrammeSerialize;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import com.example.cloud.EPGChaine;
import com.example.cloud.EPGChaineSerialize;
import com.example.cloud.EPGChaines;
import com.example.remote.BaseApi;
import com.example.zappv1.R;
import com.example.zappv1.Telecommande;
import com.google.gson.Gson;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class DisplayByType extends Activity {

	String type;
	private BaseProgramme basePg;
	String chaineId = "";
	EPGChaine item;
	ArrayList<EPGChaine> epgType = new ArrayList<EPGChaine>();
	private static final String LOG_TAG = "activity";
	ArrayList <String> tri = new ArrayList<String>();
	private ListView listeType;
	TypeAdapter adapter;
	private EPGChaine epgChaine;
	int counter = 1;
	ProgressDialog spinner;
	public DisplayByType(){

	}	
	public void onCreate(Bundle savedInstanceState) {
		//cr�ation de la vue et connexion des variables avec leur "clones" xml
		super.onCreate(savedInstanceState);

		setContentView(R.layout.displaybytype);
		/// Code en dur avec la couleur #303030
		
		/*** ACTION BAR ***/
		ActionBar actionbar = getActionBar();
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		actionbar.show();
		getActionBar().setBackgroundDrawable(new ColorDrawable(0xFF303030));
		
		listeType = (ListView) findViewById(R.id.chaines);
		Bundle extra = getIntent().getExtras();
		spinner = new ProgressDialog(this);
		extra = getIntent().getExtras();
		if(extra != null)
		{
			type = extra.getString("typeProg");
			Log.d(LOG_TAG,"PROGRAMMEID"+ extra.getString("typeProg"));
			spinner.setMessage("Chargement");
			spinner.show();
			//for (int i = 1; i < 20; i++){
				new getChannelTask(getApplicationContext()).execute();
			//}
		}

		//�venement lorsque qu'on clique sur une cha�ne dans la liste
		listeType.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id)
			{
				Intent intent = new Intent(getApplicationContext(), PreviewType.class);
				item = (EPGChaine) arg0.getItemAtPosition(position);
				intent.putExtra("listetype", chaineId);
				//Envoi du nom de la chaine � la vue pr�visualisation
				intent.putExtra("chaineNom",item.getNom());
				//Envoi de l'id de la cha�ne
				intent.putExtra("chaineId", item.getId());
				//envoi de l'id du programme
				intent.putExtra("progid", item.getListeProgrammes().getProgrammes().getId());
				intent.putExtra("progFin", item.getListeProgrammes().getProgrammes().getFin());             
				intent.setClass(getApplicationContext(), PreviewType.class);
				startActivity(intent);
				DisplayByType.this.overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}

		});
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(this, Telecommande.class);

		// Handle presses on the action bar items
		switch (item.getItemId()) 
		{
		case R.id.action_remote:
			startActivity(intent);
			break;
		default: 
			break;
		}

		finish();
		return super.onOptionsItemSelected(item);
	}
	

	public void onResume(){
		//mettre � jour la vue quand on revient d'une des vues previewType
		super.onResume();
		//for (int i = 1; i < 20; i++){
			new getChannelTask(getApplicationContext()).execute();
		//}
	}
	private void refreshType(){
		new gettypeTask(epgType, adapter, this, chaineId).execute();
	}

	private void refreshTypes(){
		new GetTypeTasks(epgType, adapter, this, chaineId).execute();
	}
	//r�cup�ration des informations en cours d'une cha�ne
	private class getChannelTask extends AsyncTask<String, Void, String> {

		ArrayList<EPGChaine> chaine = new ArrayList<EPGChaine>();

		BaseAdapter adapter;
		Context context;
		public static final String LOG_TAG = "debug";
		public getChannelTask(Context c) {
			this.context = c;
		}

		//Fonction qui se lance � l'appel de cette classe
		@Override
		protected String doInBackground(String... params){
			//Url de la requ�te permettant d'acc�der au Cloud pour r�cup�rer toutes les cha�nes en temps r�el
			//String url = "http://openbbox.flex.bouyguesbox.fr:81/V0/Media/EPG/Live?period=1";
			//Url de la requete permettant d'acc�der au Cloud pour r�cup�rer toutes les cha�nes en temps r�el
			String url = "http://openbbox.flex.bouyguesbox.fr:81/V0/Media/EPG/Live/";
			try {
				HttpResponse response = BaseApi.executeHttpGet(url);
				HttpEntity entity = response.getEntity();
				if (entity !=null)
				{
					BufferedReader r = new BufferedReader(new InputStreamReader(entity.getContent()));
					StringBuilder total = new StringBuilder();
					String line;
					while ((line = r.readLine()) != null) {
						total.append(line);
					}
					//Log.d(LOG_TAG,"TOTAL "+total.toString());
					return total.toString();
				}

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		//Fonction qui se lance apr�s l'�x�cution de la fonction doInBackground

		protected void onPostExecute(String result){
			super.onPostExecute(result);

			if (result!=null)
			{        
				EPGChaines ch = new Gson().fromJson(result,EPGChaines.class);
				//adapter.notifyDataSetChanged();
				chaine.addAll(ch);
				if(chaine != null){
					for (int i = 0; i < chaine.size(); i++){
					new getBaseProgrammeTask(basePg,getApplicationContext(),chaine.get(i).getListeProgrammes().getProgrammes().getId(),
							chaine.get(i).getId()).execute();
					}
				}
			}
		}



	}
	//r�cup�ration des information d'un programme
	public class getBaseProgrammeTask extends AsyncTask <String,Void,String>
	{
		BaseProgramme bp;
		Context context;
		String id;
		String channel;

		public getBaseProgrammeTask(BaseProgramme b, Context context, String id, String channel)
		{
			this.bp = b;
			this.context = context;
			this.id = id;
			this.channel = channel;
		}

		@Override
		protected String doInBackground(String... params){
			//Url de la requ�te permettant d'acc�der au Cloud pour r�cup�rer toutes les cha�nes en temps r�el
			//String url = "http://openbbox.flex.bouyguesbox.fr:81/V0/Media/EPG/Live?period=1";
			//Url de la requete permettant d'acc�der au Cloud pour r�cup�rer toutes les cha�nes en temps r�el
			String url = "http://openbbox.flex.bouyguesbox.fr:81/V0/Media/EPG/Live?programId="+id;
			try {
				HttpResponse response = BaseApi.executeHttpGet(url);
				HttpEntity entity = response.getEntity();
				if (entity !=null)
				{
					BufferedReader r = new BufferedReader(new InputStreamReader(entity.getContent()));
					StringBuilder total = new StringBuilder();
					String line;
					while ((line = r.readLine()) != null) {
						total.append(line);
					}
					return total.toString();
				}

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String result){
			super.onPostExecute(result);
			if (result!=null)
			{        
				BaseProgrammeSerialize bpz = new Gson().fromJson(result,BaseProgrammeSerialize.class);
				bp = bpz;

				if (bp.getProgramme().getListeGenres().getGenre().equals(type)){
					tri.add(channel);
				}

				chaineId = parsing();
				counter++;
				//on lance cette partie que quand toutes les cha�nes ont �t� analys�es
				if (counter == 19){

					//m�me algorithme que pour les favoris
					if (chaineId.isEmpty()){
						spinner.dismiss();
						AlertDialog.Builder builder1 = new AlertDialog.Builder(DisplayByType.this);
						builder1.setMessage("Aucun programme en cours pour ce type.");
						builder1.setCancelable(true);
						builder1.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});


						AlertDialog alert11 = builder1.create();
						alert11.show();
					}else
					{
						if ((chaineId.length() == 1)||(chaineId.length() == 2)){
							adapter = new TypeAdapter(getApplicationContext(), epgType, this);  
							listeType.setAdapter(adapter);
							
							refreshType();
							spinner.dismiss();
						}
						else{
							adapter = new TypeAdapter(getApplicationContext(), epgType, this);  
							listeType.setAdapter(adapter);
							
							refreshTypes();
							spinner.dismiss();
						}
					}
				}
			}

		}
		//transformation du tableau de cha�nes en un string utilisable dans les tasks d'appel
		public String parsing(){
			String parse ="";
			if (tri.isEmpty()){

				return "";
			}else{
				for (int k = 0; k < tri.size(); k++){
					if (parse.isEmpty()){
						parse = parse + tri.get(k);
					}
					else {
						if (parse.contains(tri.get(k))){

						}
						else{ parse = parse + "," + tri.get(k);}
					}
				}
				return parse;
			}

		}
	}

}

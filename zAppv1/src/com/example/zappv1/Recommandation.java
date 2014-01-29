package com.example.zappv1;

import infoprog.BaseProgramme;
import infoprog.BaseProgrammeSerialize;
import infoprog.ProgrammeFilm;
import infoprog.ProgrammeFilmSerialize;
import infoprog.ProgrammeMag;
import infoprog.ProgrammeMagSerialize;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;

import com.example.cloud.EPGChaine;
import com.example.cloud.EPGChaineSerialize;
import com.example.recommandation.AppRegister;
import com.example.recommandation.GetRecoTasks;
import com.example.recommandation.ObjectReco;
import com.example.recommandation.ObjectRecoSerialize;
import com.example.recommandation.RecommandationAdapter;
import com.example.recommandation.getRecoTask;
import com.example.remote.BaseApi;
import com.google.gson.Gson;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Recommandation extends Fragment {

	public Recommandation(){

	}

	ObjectReco reco;
	boolean database = true; //vérifie qu'il y a des artistes recommandés
	private EPGChaine epgChaine;
	private BaseProgramme basePg;
	private ProgrammeFilm pgFilm;
	private ProgrammeMag pgMag;
	String chaineId = "";
	ArrayList<EPGChaine> epgrecommendes = new ArrayList<EPGChaine>();
	RecommandationAdapter adapter;
	private ListView listeRecommandation;
	public static final String SUFFIXE_URL = "/api.bbox.lan/V0";
	public static String URL_HTTP = "";
	public static final String BOX_PREFERENCES = "boxPrefs";
	ArrayList <String> chainereco = new ArrayList<String>();
	private static final String TAG = "debug";
	private static final String LOG_TAG = "activity";
	int counter = 1;
	String ip;
	String appId;
	String typepref = "Film";
	String typeProg = "Série";
	ArrayList <String> chainetype = new ArrayList<String>();
	ArrayList <String> chainepref = new ArrayList<String>();

	/*
	public static Fragment newInstance(Context context){
		Recommandation f = new Recommandation();

		return f;
	}
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) { 
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.recommandation, null);
		listeRecommandation = (ListView) root.findViewById(R.id.chaines);

		//on récupère la liste des artistes auprès de la webapp
		GetDatabaseTask gdbt = new GetDatabaseTask();
		gdbt.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

		//Récuperation de l'adresse ip de la box grâce aux préférences 
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		ip = prefs.getString(BOX_PREFERENCES,"null");
		URL_HTTP = "http://"+ip+":8080"+SUFFIXE_URL;

		RegisterTask rTask = new RegisterTask();
		rTask.execute(new String[] {URL_HTTP,"zapp"});
		return root;
	}

	private void refreshReco(){
		new getRecoTask(epgrecommendes, adapter, getActivity(), chaineId).execute();
	}

	private void refreshrecos(){
		new GetRecoTasks(epgrecommendes, adapter, getActivity(), chaineId).execute();
	}

	//on récupère la liste des artistes auprès de la webapp
	public class GetDatabaseTask extends AsyncTask<String, Void, String>{

		//Variable connexion BDD
		JSONArray jArray = null;
		String result = null;
		StringBuilder sb = null;
		InputStream is = null;

		public GetDatabaseTask() {

		}


		//Fonction qui se lance à l'appel de cette classe
		@Override
		protected String doInBackground(String... params){



			//http post
			String url = "http://zappwebapp.guinaudin.eu.cloudbees.net/REST/WebService";
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

		//Fonction qui se lance après l'éxécution de la fonction doInBackground

		@Override
		protected void onPostExecute(String result){
			super.onPostExecute(result);
			Log.d(TAG,"RECOSULT"+result);
			if(result!=null)
			{
				ObjectRecoSerialize recoSerialize = new Gson().fromJson(result,ObjectRecoSerialize.class);
				reco = recoSerialize;
				database = true;

			}
			else {database = false;}
			//on récupère les informations en cours des 19 chaînes

			for (int i = 1; i < 20; i++){
				getChannelTask gtc = new getChannelTask(epgChaine, getActivity(),Integer.toString(i));
				gtc.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			}


		}

	}
	//récupération des informations en cours d'une chaîne
	private class getChannelTask extends AsyncTask<String, Void, String> {

		EPGChaine chaine;

		BaseAdapter adapter;
		Context context;
		String id;
		public static final String LOG_TAG = "debug";
		public getChannelTask(EPGChaine chaine, Context c,String id) {
			this.chaine = chaine;
			this.context = c;
			this.id=id;
		}

		//Fonction qui se lance à l'appel de cette classe
		@Override
		protected String doInBackground(String... params){
			//Url de la requête permettant d'accéder au Cloud pour récupérer toutes les chaînes en temps réel
			//String url = "http://openbbox.flex.bouyguesbox.fr:81/V0/Media/EPG/Live?period=1";
			//Url de la requete permettant d'accéder au Cloud pour récupérer toutes les chaînes en temps réel
			String url = "http://openbbox.flex.bouyguesbox.fr:81/V0/Media/EPG/Live/?TVChannelsId="+id;
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

		//Fonction qui se lance après l'éxécution de la fonction doInBackground

		protected void onPostExecute(String result){
			super.onPostExecute(result);

			if (result!=null)
			{        
				EPGChaineSerialize ch = new Gson().fromJson(result,EPGChaineSerialize.class);

				//adapter.notifyDataSetChanged();
				chaine = ch;
				//Log.d(TAG,"PROGRAMMEID"+ chaine.toString());
				if(chaine != null){
					//Log.d(TAG,"PROGRAMMEID"+"1");
					getBaseProgrammeTask gbpt = new getBaseProgrammeTask(basePg,getActivity(),chaine.getListeProgrammes().getProgrammes().getId(),
							chaine.getId());
					gbpt.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				}
			}
		}

	}
	//récupération des information d'un programme
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
			//Url de la requête permettant d'accéder au Cloud pour récupérer toutes les chaînes en temps réel
			//String url = "http://openbbox.flex.bouyguesbox.fr:81/V0/Media/EPG/Live?period=1";
			//Url de la requete permettant d'accéder au Cloud pour récupérer toutes les chaînes en temps réel
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

				if (bp.getProgramme().getListeGenres().getGenre().equals(typepref)){
					chainepref.add(channel);
				}

				if (bp.getProgramme().getListeGenres().getGenre().equals(typeProg)){
					chainetype.add(channel);
				}

				Log.d(TAG,"TVSHOW"+result.toString());
				//on compare la liste des artistes de la webapp et ceux du programme
				if (database){
					if((result.toString().contains("\"firstName\": {}"))||(result.toString().contains("\"ListeArtistes\": {}"))){


					}
					else {
						if (result.toString().contains("[")){
							ProgrammeFilmSerialize pfs = new Gson().fromJson(result,ProgrammeFilmSerialize.class);
							pgFilm = pfs;
							for (int i = 0; i < reco.getArtists().size(); i++){
								for (int j = 0; j < pgFilm.getProgramme().getListeArtistes().getArtiste().size(); j++){
									if ((pgFilm.getProgramme().getListeArtistes().getArtiste().get(j).getLastName().equals(reco.getArtists().get(i).getLastName()))
											&& (pgFilm.getProgramme().getListeArtistes().getArtiste().get(j).getFirstName().equals(reco.getArtists().get(i).getFirstName()))){
										//si ça correspond, on ajoute la chaîne au tableau
										chainereco.add(channel);
									}
								}

							}
						}


						else {
							ProgrammeMagSerialize pms = new Gson().fromJson(result,ProgrammeMagSerialize.class);
							pgMag = pms;
							for (int i = 0; i <reco.getArtists().size(); i++){
								if ((pgMag.getProgramme().getListeArtistes().getArtiste().getLastName().equals(reco.getArtists().get(i).getLastName()))
										&& (pgMag.getProgramme().getListeArtistes().getArtiste().getFirstName().equals(reco.getArtists().get(i).getFirstName()))){
									chainereco.add(channel);
								}
								if (pgMag == null){Log.d(TAG,"PGMAG null");}
								else {Log.d(TAG,"CHaine" + channel);}
							}
						}


					}
				}
			}
			
			counter++;
			Log.d(TAG,"COUNTER"+counter);
			//on lance cette partie que quand toutes les chaînes ont été analysées
			if (counter == 19){
				//on récupére le String comprenant les chaînes à afficher
				chaineId = parsing(chainereco, 0);
				//si vide, on propose les programmes de la catégorie préférée par l'utilisateur
				if (chaineId.isEmpty()){
					chaineId = parsing(chainepref, 0);
					if (chainepref.size() < 4){
						chaineId = chaineId + parsing(chainetype, chainepref.size());
					}
					adapter = new RecommandationAdapter(getActivity(), epgrecommendes, this);  
					listeRecommandation.setAdapter(adapter);
					refreshrecos();
				}else
				{
					if ((chaineId.length() == 1)||(chaineId.length() == 2)){
						chaineId = chaineId + parsing(chainepref, 1);
						if (chainepref.size() + chainereco.size() < 4){
							chaineId = chaineId + parsing(chainetype, chainepref.size() + chainereco.size());
						}
						adapter = new RecommandationAdapter(getActivity(), epgrecommendes, this);  
						listeRecommandation.setAdapter(adapter);
						refreshReco();
					}
					else{
						adapter = new RecommandationAdapter(getActivity(), epgrecommendes, this);  
						listeRecommandation.setAdapter(adapter);
						refreshrecos();
					}
				}
			}

		}
		public String parsing(ArrayList<String> string, int number){
			String parse ="";
			int i = 0;
			if (string.isEmpty()){
				Log.d(TAG,"PROGRAMMEID"+parse);
				return "";
			}else{
				if(number !=0){
					i = 4-number;
				}
				else if (string.size()>=4){
					i = 4;
				}
				else i = string.size();

				for (int k = 0; k < i; k++){
					if (parse.isEmpty()){
						parse = parse + string.get(k);
					}
					else {
						if (parse.contains(string.get(k))){

						}
						else{ parse = parse + "," + string.get(k);}
					}
				}Log.d(TAG,"PROGRAMMEID"+parse);
				return parse;
			}

		}
	}


	//Appel de la fonction SendKey de la classe UserIntefaceApi pour pouvoir envoyer les commande de remote
	public class RegisterTask extends AsyncTask<String, Void, String> {
		//Fonction obligatoire dans un AsynTask, réalise le traitement de manière asynchrone dans un thread séparé
		@Override
		protected String doInBackground(String... params) {


			return AppRegister.register(params[0],params[1]);


		}     

		protected void onPostExecute(String result){
			super.onPostExecute(result);
			if(result != null)
			{
				appId = result;
				Log.d(TAG,"APPID"+appId);
			}
		}

	}

}






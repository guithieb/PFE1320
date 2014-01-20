package com.example.favoris;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.BaseAdapter;

import com.example.cloud.EPGChaine;
import com.example.cloud.EPGChaines;
import com.example.remote.BaseApi;
import com.google.gson.Gson;

public class GetFavorisTask extends AsyncTask<String, Void, String>{

	ArrayList<EPGChaine> chaines;
	BaseAdapter adapter;
	Context context;
	String channels;
	public static final String LOG_TAG = "debug";
	private ProgressDialog spinner;

	public GetFavorisTask(ArrayList<EPGChaine> chaines, BaseAdapter adapter, Context c, String channels) {
		this.chaines = chaines;
		this.adapter = adapter;
		this.context = c;
		this.channels = channels;
		this.spinner = new ProgressDialog(context);
	}
	
	protected void onPreExecute(){
		spinner.setMessage("Chargement de l'EPG");
		spinner.show();
	}
	
	//Fonction qui se lance à l'appel de cette classe
	@Override
	protected String doInBackground(String... params){
	  //Url de la requête permettant d'accéder au Cloud pour récupérer toutes les chaînes en temps réel
		//String url = "http://openbbox.flex.bouyguesbox.fr:81/V0/Media/EPG/Live?period=1";
	  //Url de la requete permettant d'accéder au Cloud pour récupérer toutes les chaînes en temps réel
		String url = "http://openbbox.flex.bouyguesbox.fr:81/V0/Media/EPG/Live/?TVChannelsId="+channels;
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
		spinner.dismiss();
		if (result!=null)
		{	
			EPGChaines ch = new Gson().fromJson(result,EPGChaines.class);
			Log.d(LOG_TAG,"CH "+ch.toString());
			chaines.clear();
			chaines.addAll(ch);
			adapter.notifyDataSetChanged();
			
		}
	}

}

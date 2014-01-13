package com.example.cloud;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import com.example.remote.BaseApi;
import com.google.gson.Gson;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.BaseAdapter;

/**
 * 
 * 
 * Task=Thread permettant de récupérer toute la liste des chaînes pour la box
 * 
 */


public class GetProgramTask extends AsyncTask<String, Void, String>{

	private ArrayList<EPGChaine> chaines;
	BaseAdapter adapter;
	Context context;
	 public static final String LOG_TAG = "debug";

	public GetProgramTask(ArrayList<EPGChaine> chaines, BaseAdapter adapter, Context c) {
		this.chaines = chaines;
		this.adapter = adapter;
		this.context = c;
	}
	
	//Fonction qui se lance à l'appel de cette classe
	@Override
	protected String doInBackground(String... params){
	  //Url de la requete permettant d'accéder au Cloud pour récupérer toutes les chaînes en temps réel
		String url = "http://openbbox.flex.bouyguesbox.fr:81/V0/Media/EPG/Live";
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
				Log.d(LOG_TAG,"TOTAL "+total.toString());
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
		if (result!=null)
		{	Log.d(LOG_TAG,"RESULT "+result);
			EPGChaines ch = new Gson().fromJson(result,EPGChaines.class);
			chaines.clear();
			chaines.addAll(ch);
			adapter.notifyDataSetChanged();
			Log.d(LOG_TAG,"TASK OK");
			
			
		}
	}

}

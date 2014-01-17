package com.example.cloud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import com.example.remote.BaseApi;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.BaseAdapter;

/**
 * 
 * 
 * Task=Thread permettant de r�cup�rer toute la liste des cha�nes pour la box
 * 
 */


public class GetProgramTask extends AsyncTask<String, Void, String>{

	ArrayList<EPGChaine> chaines;
	BaseAdapter adapter;
	Context context;
	public static final String LOG_TAG = "debug";
	private ProgressDialog spinner;

	public GetProgramTask(ArrayList<EPGChaine> chaines, BaseAdapter adapter, Context c) {
		this.chaines = chaines;
		this.adapter = adapter;
		this.context = c;
		this.spinner = new ProgressDialog(context);
	}
	
	protected void onPreExecute(){
		spinner.setMessage("Chargement de l'EPG");
		spinner.show();
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
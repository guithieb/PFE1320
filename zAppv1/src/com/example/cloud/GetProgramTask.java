package com.example.cloud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
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

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.BaseAdapter;

/**
 * 
 * 
 * Task=Thread permettant de récupérer toute la liste des chaînes pour la box
 * 
 */


public class GetProgramTask extends AsyncTask<String, Void, String>{

	ArrayList<EPGChaine> chaines;
	BaseAdapter adapter;
	Context context;
	public static final String LOG_TAG = "debug";
	private ProgressDialog spinner;
	final Handler handler;
	public GetProgramTask(ArrayList<EPGChaine> chaines, BaseAdapter adapter, Context c) {
		this.chaines = chaines;
		this.adapter = adapter;
		this.context = c;
		this.spinner = new ProgressDialog(context);
		this.handler = new Handler();
	}
	
	protected void onPreExecute(){
		spinner.setMessage("Chargement");
		spinner.show();
	}
	
	//Fonction qui se lance à l'appel de cette classe
	@Override
	protected String doInBackground(String... params){
	  //Url de la requête permettant d'accéder au Cloud pour récupérer toutes les chaînes en temps réel
		//String url = "http://openbbox.flex.bouyguesbox.fr:81/V0/Media/EPG/Live?period=1";
	  //Url de la requete permettant d'accéder au Cloud pour récupérer toutes les chaînes en temps réel
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
					//si chargement plus long que 90 secondes, on stoppe le thread
					handler.postDelayed(new Runnable(){

						@Override
						public void run() {
							// TODO Auto-generated method stub
							cancel(true);
						}
						
					}, 90000);
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
			chaines.clear();
			chaines.addAll(ch);
			adapter.notifyDataSetChanged();
			
		}
		else{
			AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
			builder1.setMessage("Connexion à la liste des programmes impossible");
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
	}
	protected void onCancelled(){
		AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
		builder1.setMessage("Connexion à la liste des programmes impossible");
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

}

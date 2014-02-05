package com.example.type;

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
import com.example.cloud.EPGChaineSerialize;
import com.example.remote.BaseApi;
import com.google.gson.Gson;

public class gettypeTask extends AsyncTask<String, Void, String> {

	ArrayList <EPGChaine> chaine;

	BaseAdapter adapter;
	Context context;
	String id;
	private ProgressDialog spinner;
	public static final String LOG_TAG = "debug";
	public gettypeTask(ArrayList <EPGChaine> chaine,BaseAdapter adapter, Context c,String id) {
		this.chaine = chaine;
		this.adapter = adapter;
		this.context = c;
		this.id=id;
		this.spinner = new ProgressDialog(context);
	}
	protected void onPreExecute(){
		spinner.setMessage("Chargement");
		spinner.show();
	}

	//Fonction qui se lance � l'appel de cette classe
	@Override
	protected String doInBackground(String... params){
		//Url de la requ�te permettant d'acc�der au Cloud pour r�cup�rer toutes les cha�nes en temps r�el
		//String url = "http://openbbox.flex.bouyguesbox.fr:81/V0/Media/EPG/Live?period=1";
		//Url de la requete permettant d'acc�der au Cloud pour r�cup�rer toutes les cha�nes en temps r�el
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

	//Fonction qui se lance apr�s l'�x�cution de la fonction doInBackground

	protected void onPostExecute(String result){
		super.onPostExecute(result);
		spinner.dismiss();
		if (result!=null)
		{	
			EPGChaineSerialize ch = new Gson().fromJson(result,EPGChaineSerialize.class);
			//adapter.notifyDataSetChanged();
			chaine.clear();
			chaine.add(ch);
			adapter.notifyDataSetChanged();
		}
	}

}

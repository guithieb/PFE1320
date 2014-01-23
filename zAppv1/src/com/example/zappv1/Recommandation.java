package com.example.zappv1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.cloud.ChaineAdapter;
import com.example.cloud.EPGChaine;
import com.example.cloud.EPGChaineSerialize;
import com.example.cloud.EPGChaines;
import com.example.cloud.GetProgramTask;
import com.example.recommandation.ObjectReco;
import com.example.recommandation.ObjectRecoSerialize;
import com.example.recommandation.ObjectReco.Artist;
import com.example.remote.BaseApi;
import com.google.gson.Gson;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.net.*;

public class Recommandation extends Fragment {

  ObjectReco reco;
  
  private static final String TAG = "debug";
  
  ArrayList <Artist> artists;
  ChaineAdapter adapter;

  public static Fragment newInstance(Context context){
    Recommandation f = new Recommandation();

    return f;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) { 
    ViewGroup root = (ViewGroup) inflater.inflate(R.layout.recommandation, null);


     GetDatabaseTask gdbt = new GetDatabaseTask();
     gdbt.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
     return root;
  }


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
        
        Log.d(TAG,"RECO "+reco.getArtists().get(1).getFirstName());
       
      }

    }

  }
  /*
  private void refreshChaine() {
		// TODO Auto-generated method stub
		new GetProgramTask(artists, adapter, getActivity()).execute();
		
		}
  */

}
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

import com.example.cloud.EPGChaine;
import com.example.cloud.EPGChaines;
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


  private static final String TAG = "debug";

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

      //Connexion BDD
      ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

      //http post
      try{
        HttpClient httpclient = new DefaultHttpClient();
        String url = URLEncoder.encode("http://10.0.2.2/connection.php","UTF_8").replace("+","%20");
        Log.d(TAG,"URLENCORE"+url);
        //Why to use 10.0.2.2
        HttpPost httppost = new HttpPost("http://192.168.0.2/connection.php");
        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        is = entity.getContent();
      }catch(Exception e){
        Log.e("log_tag", "Error in http connection"+e.toString());
      }
      //convert response to string
      try{
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        sb = new StringBuilder();
        //sb.append(reader.readLine() + "\n");

        String line;
        while ((line = reader.readLine()) != null) {
          sb.append(line);
        }
        // is.close();
        return sb.toString();
      }catch(Exception e){
        Log.e("log_tag", "Error converting result "+e.toString());
      }


     

      return null;
    }

    //Fonction qui se lance après l'éxécution de la fonction doInBackground

    @Override
    protected void onPostExecute(String result){
      super.onPostExecute(result);

      
      try{
        Log.d(TAG,"RESULTMYSQL"+result.toString());
        jArray = new JSONArray(result);
        JSONObject json_data=null;
        for(int i=0;i<jArray.length();i++){
          json_data = jArray.getJSONObject(i);
          String ct_name=json_data.getString("userId");//here "Name" is the column name in database
          Log.d(TAG,"CTNAME"+ct_name);
        }
      }
      catch(JSONException e1){
        Toast.makeText(getActivity(), "No Data Found" ,Toast.LENGTH_LONG).show();
      } catch (ParseException e1) {
        e1.printStackTrace();
      }

    }

  }

}
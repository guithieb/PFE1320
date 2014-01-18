package com.example.zappv1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.net.*;

public class Recommandation extends Fragment {

  //Variable connexion BDD
  JSONArray jArray = null;
  String result = null;
  StringBuilder sb = null;
  InputStream is = null;
  private static final String TAG = "debug";
  
  public static Fragment newInstance(Context context){
    Recommandation f = new Recommandation();

    return f;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) { 
    ViewGroup root = (ViewGroup) inflater.inflate(R.layout.recommandation, null);

    //Connexion BDD
    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


    //http post
    try{
      HttpClient httpclient = new DefaultHttpClient();
      String url = URLEncoder.encode("http://10.0.2.2/connexion.php","UTF_8").replace("+","%20");
      Log.d(TAG,"URLENCORE"+url);
      //Why to use 10.0.2.2
      HttpPost httppost = new HttpPost("http://10.0.2.2:800/connexion.php");
      httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
      HttpResponse response = httpclient.execute(httppost);
      HttpEntity entity = response.getEntity();
      is = entity.getContent();
    }catch(Exception e){
      Log.e("log_tag", "Error in http connection"+e.toString());
    }
    //convert response to string
    try{
      BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
      sb = new StringBuilder();
      sb.append(reader.readLine() + "\n");

      String line="0";
      while ((line = reader.readLine()) != null) {
        sb.append(line + "\n");
      }
      is.close();
      result=sb.toString();
    }catch(Exception e){
      Log.e("log_tag", "Error converting result "+e.toString());
    }

    String name;
    try{
      Log.d(TAG,"RESULTMYSQL"+result.toString());
      jArray = new JSONArray(result);
      JSONObject json_data=null;
      for(int i=0;i<jArray.length();i++){
        json_data = jArray.getJSONObject(i);
        String ct_name=json_data.getString("userId");//here "Name" is the column name in database
      }
    }
    catch(JSONException e1){
      Toast.makeText(getActivity(), "No Data Found" ,Toast.LENGTH_LONG).show();
    } catch (ParseException e1) {
      e1.printStackTrace();
    }



    return root;
  }
}

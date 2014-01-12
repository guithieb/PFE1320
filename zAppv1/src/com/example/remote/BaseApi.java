package com.example.remote;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.util.Log;


public class BaseApi {
  private static final String LOG_TAG = BaseApi.class.getSimpleName();
  private static final String JSON_CONTENT_TYPE = "application/json";
  
  /**
   * Vérifie que le code de retour est bon. Sinon une ServerException est levée
   * @param httpStatusCode
   * @throws ServerException
   */
  protected static void checkHttpCode(int httpStatusCode) throws ServerException{
    if(httpStatusCode != HttpStatus.SC_OK && 
        httpStatusCode != HttpStatus.SC_NO_CONTENT) {
      
      Log.e(LOG_TAG, "Server responded with status code: " + httpStatusCode);
      throw new ServerException("error", "bad response code " + httpStatusCode);
    }
  }
  
  protected static HttpResponse executeHttpPut(String url, Object request) 
      throws UnsupportedEncodingException, IOException, ClientProtocolException {

    HttpPut method = new HttpPut(url);

    return executeHttpMethod(request, method);
  }
  
  private static HttpResponse executeHttpMethod(Object request, HttpEntityEnclosingRequestBase method) 
      throws UnsupportedEncodingException, IOException, ClientProtocolException {
    
    HttpClient client = makeJsonHttpClient(method);
    
    buildJsonBody(request, method);
    
    //éxecution de la requête HTTP
    HttpResponse response = client.execute(method);
    return response;
  }
  
  private static HttpClient makeJsonHttpClient(HttpRequestBase method) {
    return makeHttpClient(method, JSON_CONTENT_TYPE);
  }
  
  private static HttpClient makeHttpClient(HttpRequestBase method, String contentType) {
    HttpClient client = new DefaultHttpClient();
    //on veut du json en retour
    method.setHeader("Accept", contentType);
    //on envoit du json au serveur
    method.setHeader("content-type",contentType);
    //éxecution de la requête HTTP
    return client;
  }
  
  private static void buildJsonBody(Object request, HttpEntityEnclosingRequestBase method)
      throws UnsupportedEncodingException {
    
    if (request != null) {
      Gson gson = new GsonBuilder().create();
      String json = gson.toJson(request);
      StringEntity se = new StringEntity(json.toString());  
      se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, JSON_CONTENT_TYPE));
      method.setEntity(se);
    }
  }

  protected static void checkResponseCodeWithoutBody(String toSend, HttpResponse response) 
      throws IOException, ServerException {
    
    int statusCode = response.getStatusLine().getStatusCode();
    
    checkHttpCode(statusCode);

    HttpEntity entity = response.getEntity();

    if( entity != null ) {
      //même si pas de body, consommer quand même le résultat pour libérer les resources 
      entity.consumeContent();
    }
  }
  
	public static HttpResponse executeHttpGet(String url)
			throws IOException, ClientProtocolException {
		
		HttpGet method = new HttpGet(url);
		
		HttpClient client = makeJsonHttpClient(method);
		
		HttpResponse response = client.execute(method);
		return response;
	}

}

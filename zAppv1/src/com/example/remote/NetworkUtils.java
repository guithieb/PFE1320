package com.example.remote;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class NetworkUtils {
	
	public static final String JSON_CONTENT_TYPE = "application/json";
	// freebox melvin
	public static final String DEFAULT_URL = "192.168.1.36";
	// a l'ECE
	//public static final String DEFAULT_URL = "10.10.92.206";
	// pour le simulateur
	//public static final String DEFAULT_URL = "10.0.2.2";
	public static final String BOX_URL = "BOX_URL";
	public static final String SUFFIXE_URL = "/api.bbox.lan/V0";
	public static final String URL_HTTP = "http://%1$s:8080"+SUFFIXE_URL;
	public static final String URL_WS = "ws://%1$s:9090";
	
	
	/**
	 * retourne l'url pour utiliser les API websocket
	 * @param Context c
	 * @return String url
	 */
	public static String getUrlWebSocket(Context c){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(c);
		String box_url = preferences.getString(BOX_URL,DEFAULT_URL);

		return String.format(URL_WS, box_url);
	}

	/**
	 * Retourn l'url pour utiliser les API http
	 * @param Context c
	 * @return String url
	 */
	public static String getUrlHttp(Context c){
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(c);
		String box_url = preferences.getString(BOX_URL,DEFAULT_URL);
		
		return String.format(URL_HTTP, box_url);
	}
	
	public static String getUrl(Context c){
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(c);
		String box_url = preferences.getString(BOX_URL,DEFAULT_URL);
		
		return box_url;
	}
	
	public static void saveUrl(Context c, String url){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(c);
		preferences.edit().putString(BOX_URL, url).commit();
	}

}

package com.example.remote;

import org.apache.http.HttpResponse;

import android.util.Log;

/**
 * 
 * Implémente fonction qui permet d'envoyer une commande à la box (P+,P-,V+,V-...)
 * 
 * 
 */

public class UserInterfaceApi extends BaseApi {

  private static final String LOG_TAG = UserInterfaceApi.class.getSimpleName();
  private static final String URL_SUFFIX_SEND_KEY = "/UserInterface/RemoteController/Key";
  
  public static final String TYPE_KEY_PRESSED = "keypressed";
  public static final String TYPE_KEYP_UP = "keyup";
  public static final String TYPE_KEY_DOWN = "keydown";
  
  public static final  String CHANNEL_DOWN = "P-";
  public static final  String CHANNEL_UP = "P+";
  
  public static void sendKey(String baseUrl, String key, String keyType) throws ServerException {
    Log.d(LOG_TAG, "sendKey() url=" + (baseUrl + URL_SUFFIX_SEND_KEY) + ", key=" + key + ", keyType=" + keyType);
    try {
      SendKeyRequestData request = new SendKeyRequestData(key, keyType);
      
      HttpResponse response = executeHttpPut(baseUrl+ URL_SUFFIX_SEND_KEY, request);
      
      checkResponseCodeWithoutBody(key, response);
    } catch(ServerException ex) {
      throw ex;
    } catch(Exception ex) {
      Log.e(LOG_TAG, "Failed to send key due to: " + ex);
      throw new ServerException(ex.getMessage(), ex.getMessage());
    }
  }
  
}

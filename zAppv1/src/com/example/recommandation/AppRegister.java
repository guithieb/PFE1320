package com.example.recommandation;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import android.util.Log;

import com.example.remote.BaseApi;
import com.example.remote.ServerException;

public class AppRegister extends BaseApi{
  
  private static final String URL_SUFFIX_REGISTER = "/Application/Register/";
  public static final String TAG = "debug ";
  
  public static String register(String baseUrl,String appName)
  {
   
    try {
      RegisterData data = new RegisterData(appName);
      HttpResponse response = executeHttpPost(baseUrl + URL_SUFFIX_REGISTER,data);
      Header locationHeader = response.getFirstHeader("Location");
          
     // String[] splitted = locationHeader.getValue().split("/");
      //String appId = splitted[splitted.length-1];
      checkResponseCodeWithoutBody(appName, response);
      String appId = null;
      return appId;
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ClientProtocolException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ServerException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
   
  
  }

}

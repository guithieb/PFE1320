package com.example.cloud;

import org.apache.http.HttpResponse;

import android.util.Log;

import com.example.remote.BaseApi;
import com.example.remote.ServerException;

public class CloudApi extends BaseApi{

	private static final String LOG_TAG = CloudApi.class.getSimpleName();
	private static final String URL_SUFFIX_SEND_KEY = "/Media/EPG/Live";

	//http://openbbox.flex.bouyguesbox.fr:81/V0/Media/EPG/Live?period=0    pour les programmes actuels

	public static void getProgram(String baseUrl, String livePeriod) throws ServerException {
		Log.d(LOG_TAG, "getProgram() url=" + (baseUrl + URL_SUFFIX_SEND_KEY) + ", livePeriod=" + livePeriod);
		try {
			SendRequestEPG request = new SendRequestEPG();
			request.live.setPeriod(Integer.parseInt(livePeriod));
			HttpResponse response = executeHttpPut(baseUrl+ URL_SUFFIX_SEND_KEY, request);

			checkResponseCodeWithoutBody(livePeriod, response);
		} catch(ServerException ex) {
			throw ex;
		} catch(Exception ex) {
			Log.e(LOG_TAG, "Failed to send key due to: " + ex);
			throw new ServerException(ex.getMessage(), ex.getMessage());
		}
	}

}

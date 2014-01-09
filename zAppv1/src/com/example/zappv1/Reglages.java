package com.example.zappv1;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.zappv1.R;
import com.example.remote.UserInterfaceApi;
import com.example.remote.ServerException;



public class Reglages extends Fragment{

  
  
	public static Fragment newInstance(Context context){
		Reglages f = new Reglages();
    	 
        return f;
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) { 
        View root = (View) inflater.inflate(R.layout.reglages, null);
        
       
        return root;
    }
    
   
      
}
   



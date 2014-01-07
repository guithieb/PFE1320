package com.example.zappv1;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zappv1.R;

public class ListeChaine extends Fragment{

	public static Fragment newInstance(Context context){
		ListeChaine f = new ListeChaine();
    	 
        return f;
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) { 
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.liste_chaine, null);
        return root;
    }
    
    //ListView listdevice;
    
}


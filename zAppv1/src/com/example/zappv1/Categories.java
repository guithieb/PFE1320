package com.example.zappv1;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zappv1.R;

public class Categories extends Fragment{
	
	public Categories(){
		
	}

/*	public static Fragment newInstance(Context context){
		Categories f = new Categories();
    	 
        return f;
	}*/
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) { 
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.categories, null);
        return root;
    }
}


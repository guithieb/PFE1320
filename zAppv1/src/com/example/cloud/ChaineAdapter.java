package com.example.cloud;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.example.zappv1.ListeChaine;
import com.example.zappv1.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChaineAdapter extends BaseAdapter {
	private class ChView{
		TextView idchannel;
		TextView chaineName;
		TextView programname;
		
	}
	
	public static final String LOG_TAG = "debug";
	// constructeur pour ChView ?
	private ArrayList<EPGChaine> datas;
	Context context;
	ListeChaine listeChaine;
	
	private LayoutInflater inflater;
	
	public ChaineAdapter (Context context, ArrayList<EPGChaine> datas,ListeChaine listeChaine){
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.datas = datas;
		this.listeChaine = listeChaine;
	}	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
		
	}
	
	
public boolean isEmpty()
{
	if(datas.size()==0) return true;
	return false;
}

	@Override
	public Object getItem(int position) {

	return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ChView ch;
		
		if(convertView == null)
		{ 
			ch = new ChView();
			convertView = inflater.inflate(R.layout.chaineview, null);
			ch.idchannel =(TextView) convertView.findViewById(R.id.idchannel);
			ch.chaineName = (TextView) convertView.findViewById(R.id.chaineName);
			ch.programname = (TextView) convertView.findViewById(R.id.programname);
			convertView.setTag(ch);
			
	
		} else {
			ch = (ChView) convertView.getTag();
		}
		
		final EPGChaine application = datas.get(position);
		ch.chaineName.setText(application.getNom());
		Log.d(LOG_TAG, "id" +application.getId());
		ch.idchannel.setText(application.getId());
		 
	    /* Drawable drawable = LoadImageFromWebOperations(application.getLogo());
	     ch.logo.setImageDrawable(drawable);*/
		return convertView;
	}
/*
	private Drawable LoadImageFromWebOperations(String url)
	{
	try
	{
	InputStream is = (InputStream) new URL(url).getContent();
	Drawable d = Drawable.createFromStream(is, "src name");
	return d;
	}catch (Exception e) {
	System.out.println("Exc="+e);
	return null;
	}
	}*/
}

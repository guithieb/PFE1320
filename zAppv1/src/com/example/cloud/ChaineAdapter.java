package com.example.cloud;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.example.zappv1.ListeChaine;
import com.example.zappv1.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
		ImageView logo;
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
			//ch.logo =(ImageView) convertView.findViewById(R.id.logo);
			ch.chaineName = (TextView) convertView.findViewById(R.id.chaineName);
			ch.programname = (TextView) convertView.findViewById(R.id.programname);
			convertView.setTag(ch);
	
		} else {
			ch = (ChView) convertView.getTag();
		}
		
		final EPGChaine application = datas.get(position);
		ch.chaineName.setText(application.getNom());
		Log.d(LOG_TAG, "IMAGE" +application.getLogo());
		
		/*if (application.getListeProgrammes() != null)
			{
				Log.d(LOG_TAG, "prg" +application.getListeProgrammes());
				//ch.programname.setText(application.getListeProgrammes().get(0).getProgrammes().get(0).getNom());
			}*/
	  ch.chaineName.setText(application.getNom());
	  
		/*if(application.getListeProgrammes() != null)
		{
		ListeProgramme lp = application.getListeProgrammes();
		Programme p = lp.getProgrammes().get(0);
		}
	*/
		return convertView;
	}
}

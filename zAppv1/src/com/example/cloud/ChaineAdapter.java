package com.example.cloud;

import java.util.ArrayList;

import com.example.zappv1.ListeChaine;



import com.example.zappv1.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ChaineAdapter extends BaseAdapter {
	
	private class ChView{
		TextView chaineName;
		
	}
	
	
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
			
			ch.chaineName = (TextView) convertView.findViewById(R.id.chaineName);
			convertView.setTag(ch);
	
		} else {
			ch = (ChView) convertView.getTag();
		}
		
		final EPGChaine application = datas.get(position);
		ch.chaineName.setText(application.getNom());
		
		
		return convertView;
	}
}

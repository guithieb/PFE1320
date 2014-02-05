package com.example.type;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cloud.EPGChaine;
import com.example.type.DisplayByType.getBaseProgrammeTask;
import com.example.zappv1.R;

public class TypeAdapter extends BaseAdapter {
	private class ChView{
		TextView progName;
		ImageView photo;
		TextView debut;
		ProgressBar duree;
	}

	private ArrayList<EPGChaine> datas;
	Context context;
	getBaseProgrammeTask display;
	String channels;
	public static final String LOG_TAG = "debug";
	private LayoutInflater inflater;

	public TypeAdapter (Context context, ArrayList<EPGChaine> datas,getBaseProgrammeTask display){
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.datas = datas;
		this.display = display;
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
			ch.progName = (TextView) convertView.findViewById(R.id.progName);
			ch.photo = (ImageView) convertView.findViewById(R.id.Picture);
			ch.debut = (TextView) convertView.findViewById(R.id.debut);
			ch.duree = (ProgressBar) convertView.findViewById(R.id.duree);
			convertView.setTag(ch);


		} else {
			ch = (ChView) convertView.getTag();
		}

		final EPGChaine application = datas.get(position);

		if (application.getNom().equals("Canal+")) {
			ch.photo.setImageResource(R.drawable.canal);
		}
		else if (application.getNom().equals("i Télé")){
			ch.photo.setImageResource(R.drawable.itele);
		} else {
			BitmapWorkerTask task = new BitmapWorkerTask(ch.photo);
			task.execute(application.getLogo());
		}


		if(application.getListeProgrammes().getProgrammes().getNom().contains("&#4")){
			String[] parseNom = application.getListeProgrammes().getProgrammes().getNom().split("&");
			if (application.getListeProgrammes().getProgrammes().getNom().contains("&apos;")){
				ch.progName.setText(Html.fromHtml(parseNom[0] + "&" + parseNom[1]));
			}else{
				ch.progName.setText(Html.fromHtml(parseNom[0]));
			}
		}
		else {ch.progName.setText(Html.fromHtml(application.getListeProgrammes().getProgrammes().getNom()));}
		String[] parse = application.getListeProgrammes().getProgrammes().getDebut().split("T");
		String[] debutProg = parse[1].split("Z");

		//analyse pour la progress bar
		//heure de la fin
		String[] parsefin = application.getListeProgrammes().getProgrammes().getFin().split("T");
		String[] finProg = parsefin[1].split("Z");

		//heure de début et fin en minute et heure
		String[] progdebut = debutProg[0].split(":");
		String[] progfin = finProg[0].split(":");

		//affichage de l'heure de début sans les secondes
		ch.debut.setText(progdebut[0]+":"+progdebut[1]);

		int horairedebut = (Integer.parseInt(progdebut[0])*60)+Integer.parseInt(progdebut[1]);
		if (Integer.parseInt(progfin[0])<Integer.parseInt(progdebut[0])){
			progfin[0]=Integer.toString(Integer.parseInt(progfin[0])+24);
		}
		int horairefin = (Integer.parseInt(progfin[0])*60)+Integer.parseInt(progfin[1]);
		int dureetotale = horairefin - horairedebut;
		//heure actuelle en minutes
		Calendar c = Calendar.getInstance(); 
		int heure = c.get(Calendar.HOUR_OF_DAY);
		int minutes = c.get(Calendar.MINUTE);
		//difference entre heure actuelle et debut programme
		int difference = (minutes+heure*60) - horairedebut;
		//ratio pour progress bar
		double ratio = (double) difference/ (double) (dureetotale);
		ch.duree.setProgress((int) (ratio*100));
		return convertView;
	}

	class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {

		private final WeakReference<ImageView> imageViewReference;
		private String data;

		public BitmapWorkerTask(ImageView imageView) {
			// Use a WeakReference to ensure the ImageView can be garbage
			// collected
			imageViewReference = new WeakReference<ImageView>(imageView);
		}

		// Decode image in background.
		protected Bitmap doInBackground(String... params) {
			data = params[0];
			try {
				return BitmapFactory.decodeStream((InputStream) new URL(data)
				.getContent());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		// Once complete, see if ImageView is still around and set bitmap
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if (imageViewReference != null && bitmap != null) {
				final ImageView imageView = imageViewReference.get();
				if (imageView != null) {
					imageView.setImageBitmap(bitmap);
				}
			}
		}

	}
}
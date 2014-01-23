package com.example.recommandation;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

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
import android.widget.TextView;

import com.example.cloud.EPGChaine;
import com.example.zappv1.R;
import com.example.zappv1.Recommandation.getBaseProgrammeTask;

public class RecommandationAdapter extends BaseAdapter {
		private class ChView{
			//TextView chaineName;
			TextView progName;
			ImageView photo;
		}

		private ArrayList<EPGChaine> datas;
		Context context;
		getBaseProgrammeTask baseprogrammetask;
		String channels;
		public static final String LOG_TAG = "debug";
		private LayoutInflater inflater;

		public RecommandationAdapter (Context context, ArrayList<EPGChaine> datas,getBaseProgrammeTask favoris){
			inflater = LayoutInflater.from(context);
			this.context = context;
			this.datas = datas;
			this.baseprogrammetask = favoris;
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
				convertView.setTag(ch);


			} else {
				ch = (ChView) convertView.getTag();
			}

			final EPGChaine application = datas.get(position);

			//ch.chaineName.setText(application.getNom());
			Log.d(LOG_TAG, "id" +application.getId());

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
				ch.progName.setText(Html.fromHtml(parseNom[0]));
			}
			else {ch.progName.setText(Html.fromHtml(application.getListeProgrammes().getProgrammes().getNom()));}
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

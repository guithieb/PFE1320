package com.example.cloud;

import java.io.IOException;
import java.io.InputStream;

import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.example.zappv1.ListeChaine;
import com.example.zappv1.Preview;
import com.example.zappv1.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChaineAdapter extends BaseAdapter {
	private class ChView{
		TextView chaineName;
		TextView progName;
		TextView identifiant;
		ImageView photo;

		
	}
	
	// constructeur pour ChView ?
	private ArrayList<EPGChaine> datas;
	Context context;
	ListeChaine listeChaine;
	public static final String LOG_TAG = "debug";
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
			ch.identifiant =(TextView) convertView.findViewById(R.id.identifiant);
			ch.chaineName = (TextView) convertView.findViewById(R.id.chaineName);
			ch.progName = (TextView) convertView.findViewById(R.id.progName);
			ch.photo = (ImageView) convertView.findViewById(R.id.Picture);
			convertView.setTag(ch);
			
	
		} else {
			ch = (ChView) convertView.getTag();
		}
		
		final EPGChaine application = datas.get(position);

		ch.chaineName.setText(application.getNom());
		Log.d(LOG_TAG, "id" +application.getId());
		 
		BitmapWorkerTask task = new BitmapWorkerTask(ch.photo);
		task.execute(application.getLogo());
		/* Drawable drawable = LoadImageFromWebOperations(application.getLogo());
	     ch.logo.setImageDrawable(drawable);*/
		ch.identifiant.setText(application.getId()+". ");
		ch.chaineName.setText(Html.fromHtml(application.getNom()));
		ch.progName.setText(Html.fromHtml(application.getListeProgrammes().getProgrammes().getNom()));
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

  // Once complete, see if ImageView is still around and set bitmap.
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
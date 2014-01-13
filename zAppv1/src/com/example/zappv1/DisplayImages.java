package com.example.zappv1;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.example.cloud.EPGChaine;
import com.example.zappv1.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;

public class DisplayImages extends Activity {

	private EPGChaine epgchaine;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chaineview);

       // ImageView imgView =(ImageView)findViewById(R.id.ImageView01);

		/*	TacheAffiche nouvelleTache = new TacheAffiche();
    	nouvelleTache.execute();
	}
		private Drawable LoadImageFromWeb(String url)
		{
			try 
			{
				InputStream is = (InputStream) new URL(url).getContent();
				Drawable d = Drawable.createFromStream(is, "src name");
				return d;
			} 
			catch (Exception e)
			{
				e.printStackTrace();
				return null;
			}
		}

		@SuppressWarnings("deprecation")
		private Drawable resize(Drawable image) {
			Display display = getWindowManager().getDefaultDisplay();
			Bitmap b = ((BitmapDrawable)image).getBitmap();
			float scale = (b.getWidth()*3)/display.getWidth();
			int size_x = (int) Math.round(b.getWidth()/scale);
			int size_y = (int) Math.round(b.getHeight()/scale);
			Bitmap bitmapResized = Bitmap.createScaledBitmap(b, size_x, size_y, false);
			return new BitmapDrawable(bitmapResized);
		}

		private class TacheAffiche extends AsyncTask<String,String,Boolean>
		{
			private Drawable myDrawable = null;


			protected Boolean doInBackground (String... args)
			{
				myDrawable = LoadImageFromWeb(epgchaine.getLogo());
				return true;
			}

			protected void onPostExecute(Boolean bool)
			{
				if(myDrawable != null)
				{
					ImageView AfficheView = (ImageView) findViewById(R.id.Picture);
					AfficheView.setImageDrawable(resize(myDrawable));
				}
			}
		 */
		new DownloadImageTask((ImageView) findViewById(R.id.Picture))
		.execute("http://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png");
	}

	public void onClick(View v) {
		startActivity(new Intent(this, DisplayImages.class));
		finish();

	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}
	}

}



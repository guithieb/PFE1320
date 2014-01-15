package com.example.zappv1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import com.example.cloud.ChaineAdapter;
import com.example.cloud.EPGChaine;
import com.example.cloud.EPGChaineSerialize;
import com.example.cloud.getChannelTask;
import com.example.remote.BaseApi;
import com.example.remote.ServerException;
import com.example.remote.UserInterfaceApi;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GestureDetectorCompat;
import android.text.Html;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * Vue servant à afficher les informations sur le programme en cours d'une chaine précise
 * 
 * 
 */

public class Preview extends Activity implements GestureDetector.OnGestureListener {

	/*** IMAGE Melvin ***/
private EPGChaine epgChaine;

	/*** PLAYER ZAPP ***/
	//VideoView playerSurfaceView;
	// besoin du format 3gp != stream
	//String videoSrc = "rtsp://v6.cache1.c.youtube.com/CjYLENy73wIaLQkDsLHya4-Z9hMYDSANFEIJbXYtZ29vZ2xlSARSBXdhdGNoYKX4k4uBjbOiUQw=/0/0/0/video.3gp";


	// *** Melvin Gesture *** //
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private static final String TAG = "MyActivity";
	public static final String BOX_PREFERENCES = "boxPrefs";
	//*** Melvin Gesture *** //
	private String ip;
	private static final String LOG_TAG = Reglages.class.getSimpleName();
	//private static final String DEFAULT_BOX_URL = "http://192.168.0.24:8080/api.bbox.lan/V0";
	public static final String SUFFIXE_URL = "/api.bbox.lan/V0";
	public static String URL_HTTP = "";
	private Button programUp,programDown;
	String channel;
	String description;
	String nom;
	String chaineId;
	String debut, fin;
	TextView textChaine;
	TextView textNom;
	TextView textDescription;
	TextView textDebut,textFin;
	Bundle extra;
	ArrayList<EPGChaine> epg = new ArrayList<EPGChaine>();
	private static final String DEBUG_TAG = "Gestures";
	private ChaineAdapter adapter;
	private GestureDetectorCompat mDetector; 
	int id;
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preview);
		
		/* PLAYER 
		playerSurfaceView = (VideoView)findViewById(R.id.playersurface);

		MediaController mediaController = new MediaController(this); 
		mediaController.setAnchorView(playerSurfaceView);
		playerSurfaceView.setMediaController(mediaController);
		playerSurfaceView.setVideoURI(Uri.parse(videoSrc));
		playerSurfaceView.start();
		 */

		textChaine = (TextView)findViewById(R.id.chaineName);
		textNom = (TextView)findViewById(R.id.progNom);
		textDescription = (TextView)findViewById(R.id.progDescription);
		textDebut = (TextView) findViewById(R.id.progDebut);
		textFin = (TextView) findViewById(R.id.progFin);

		// Instantiate the gesture detector with the
		// application context and an implementation of
		// GestureDetector.OnGestureListener
		mDetector = new GestureDetectorCompat(this,this);


		// Instantiate the gesture detector with the
		// application context and an implementation of
		// GestureDetector.OnGestureListener
		//mDetector = new GestureDetectorCompat(this,this);

		//URL url = new URL("http://213.139.122.233/res/chaines/1.png");
		//Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
		//ImageView imageView = (ImageView) findViewById(R.id.Picture);
		//imageView.setImageBitmap(bmp);
    	
		/*
		 * TextView textView = (TextView) findViewById(R.id.DATE);
			Date date = new Date(location.getTime());
			DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
			textView .setText("Time: " + dateFormat.format(date));
		 */

		//Récuperation du nom de la chaine envoyé dans la vue ListeChaine
		Bundle extra = getIntent().getExtras();
		 extra = getIntent().getExtras();
		if(extra != null)
		{
			channel = extra.getString("chaineNom");
			textChaine.setText(channel);

			chaineId = extra.getString("chaineId");
			getChannelTask gtc = new getChannelTask(epgChaine,getApplicationContext(),chaineId);
			gtc.execute();
			/*channel = extra.getString("chaineNom");
			textChaine.setText(channel);
			
			nom = extra.getString("progNom");
			textNom.setText(nom);

			description = extra.getString("progDescription");
			nom = extra.getString("progNom");
			textNom.setText(Html.fromHtml(nom));
			nom = extra.getString("progNom");
			textNom.setText(nom);			
			description = extra.getString("progDescription");
			textDescription.setText(Html.fromHtml(description));
			textDescription.setText(description);

			
			debut = extra.getString("progDebut");
			Log.d(TAG,"DATE"+debut);
			String[] parse = debut.split("T");
			String[] debutProg = parse[1].split("Z");
			textDebut.setText("Début: "+debutProg[0]+" - ");
			
			fin = extra.getString("progFin");
			Log.d(TAG,"DATE"+fin);
			fin = extra.getString("progFin");
			String[] parse2 = fin.split("T");
			String[] finProg = parse2[1].split("Z");
			textFin.setText("Fin: "+finProg[0]);
*/

			id = Integer.parseInt(chaineId);
		}

		
		//Création des boutons Prog+ et Prog-
		programUp = (Button)findViewById(R.id.programUp);
		programUp.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				sendKeyPressed(UserInterfaceApi.CHANNEL_UP);     
			}

		});

		programDown = (Button)findViewById(R.id.programDown);
		programDown.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				sendKeyPressed(UserInterfaceApi.CHANNEL_DOWN);     
			}

		});

		//Récuperation de l'adresse ip de la box grâce aux préférences 
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		ip = prefs.getString(BOX_PREFERENCES,"null");
		Log.d(TAG,"IP22"+ip);

		URL_HTTP = "http://"+ip+":8080"+SUFFIXE_URL;

		// execution de l'image
		//TacheAffiche nouvelleTache = new TacheAffiche();
		//nouvelleTache.execute();
		
    	

	}


	private void sendKeyPressed(String key){
		new SendKeyPressedTask().execute(
				new String[] { URL_HTTP/*DEFAULT_BOX_URL*/ , key});    
	}

	//Appel de la fonction SendKey de la classe UserIntefaceApi pour pouvoir envoyer les commande de remote
	private class SendKeyPressedTask extends AsyncTask<String, Void, String> {
		private Exception mException = null;

		//Fonction obligatoire dans un AsynTask, réalise le traitement de manière asynchrone dans un thread séparé
		@Override
		protected String doInBackground(String... params) {
			try {
				UserInterfaceApi.sendKey(params[0], params[1], UserInterfaceApi.TYPE_KEY_PRESSED);
				return params[1];
			} catch (ServerException e) {
				mException = e;
				return params[1];
			}
		}     


	}


	//Fonction permettant de detecter différents gestes sur le smartphone (ici le slide pour changer de programme)
	@Override 
	public boolean onTouchEvent(MotionEvent event){ 
		this.mDetector.onTouchEvent(event);
		// Be sure to call the superclass implementation
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		Log.d(DEBUG_TAG, "onScroll: " + e1.toString()+e2.toString());
		return true;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return true;
	}

	/*** modif Melvin ***/
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {  return false;  }
		// TODO Auto-generated method stub


		/* positive value means right to left direction */

		final float distance = e1.getX() - e2.getX();
		final boolean enoughSpeed = Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY;
		if(distance > SWIPE_MIN_DISTANCE && enoughSpeed) {
			// right to left swipe
			onSwipeLeft();
			return true;
		}  else if (distance < -SWIPE_MIN_DISTANCE && enoughSpeed) {
			// left to right swipe
			onSwipeRight();
			
			return true;
		} else {
			// oooou, it didn't qualify; do nothing
			return false;
		}
	}

	protected void onSwipeLeft() { 
		// do your stuff here
		id++;
		if(id>=20) id=id-19;
		getChannelTask gtc = new getChannelTask(epgChaine,getApplicationContext(),Integer.toString(id));
		gtc.execute();
		Log.d(TAG,"TASK OK");
		if(gtc.getStatus() == AsyncTask.Status.RUNNING)
		{
			Log.d(TAG,"TASK RIGHT OK");
		}
		
		
		
		if(gtc.getStatus() == AsyncTask.Status.FINISHED)
		{
			Log.d(TAG,"TASK RIGHT FIN");
		}
		
		if(epgChaine != null)
		{
		Log.d(TAG,"EPGCHAINE"+epgChaine.getId());
		}
		
	}

	protected void onSwipeRight() {   
		// do your stuff here
		//sendKeyPressed(UserInterfaceApi.CHANNEL_UP); 
		
		id--;
		if(id<=0) id=id+20;
		getChannelTask gtc = new getChannelTask(epgChaine,getApplicationContext(),Integer.toString(id));
		gtc.execute();
		Log.d(TAG,"TASK OK");
		if(gtc.getStatus() == AsyncTask.Status.RUNNING)
		{
			Log.d(TAG,"TASK RIGHT OK");
		}
		
		
		
		if(gtc.getStatus() == AsyncTask.Status.FINISHED)
		{
			Log.d(TAG,"TASK RIGHT FIN");
		}
		
		if(epgChaine != null)
		{
		Log.d(TAG,"EPGCHAINE"+epgChaine.getId());
		}
		
		
		
		}
	
	


	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private class getChannelTask extends AsyncTask<String, Void, String> {
		
		EPGChaine chaine;
		
		BaseAdapter adapter;
		Context context;
		String id;
		 public static final String LOG_TAG = "debug";

	public getChannelTask(EPGChaine chaine, Context c,String id) {
			this.chaine = chaine;
			this.context = c;
			this.id=id;
		}
		
		//Fonction qui se lance à l'appel de cette classe
		@Override
		protected String doInBackground(String... params){
		  //Url de la requête permettant d'accéder au Cloud pour récupérer toutes les chaînes en temps réel
			//String url = "http://openbbox.flex.bouyguesbox.fr:81/V0/Media/EPG/Live?period=1";
		  //Url de la requete permettant d'accéder au Cloud pour récupérer toutes les chaînes en temps réel
			String url = "http://openbbox.flex.bouyguesbox.fr:81/V0/Media/EPG/Live/?TVChannelsId="+id;
			try {
				HttpResponse response = BaseApi.executeHttpGet(url);
				HttpEntity entity = response.getEntity();
				if (entity !=null)
				{
					BufferedReader r = new BufferedReader(new InputStreamReader(entity.getContent()));
					StringBuilder total = new StringBuilder();
					String line;
					while ((line = r.readLine()) != null) {
						total.append(line);
					}
					//Log.d(LOG_TAG,"TOTAL "+total.toString());
					return total.toString();
				}

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		//Fonction qui se lance après l'éxécution de la fonction doInBackground
		
		protected void onPostExecute(String result){
			super.onPostExecute(result);
			Log.d(LOG_TAG,"POSTEXECUTE");
			if (result!=null)
			{	Log.d(LOG_TAG,"RESULT "+result);
				EPGChaineSerialize ch = new Gson().fromJson(result,EPGChaineSerialize.class);
				Log.d(LOG_TAG,"CH "+ch.toString());
				//Log.d(LOG_TAG,"CH"+ch.toString());
				Log.d(LOG_TAG,"RESULTCHANNEL"+result);
				//adapter.notifyDataSetChanged();
				chaine = ch;
				if(chaine != null)
				Log.d(LOG_TAG,"CHAINE"+chaine.getListeProgrammes().getProgrammes().getNom());
				textChaine.setText(chaine.getNom());
				textNom.setText(Html.fromHtml(chaine.getListeProgrammes().getProgrammes().getNom()));
				textDescription.setText(Html.fromHtml(chaine.getListeProgrammes().getProgrammes().getDescription()));
				
				String[] parse = chaine.getListeProgrammes().getProgrammes().getDebut().split("T");
				String[] debutProg = parse[1].split("Z");
				textDebut.setText("Début: "+debutProg[0]+" - ");
				
				String[] parse2 = chaine.getListeProgrammes().getProgrammes().getFin().split("T");
				String[] finProg = parse2[1].split("Z");
				textFin.setText("Fin: "+finProg[0]);
				
			}
		}

	}
	
	
}

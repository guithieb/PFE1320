package com.example.type;

import infoprog.BaseProgramme;
import infoprog.BaseProgrammeSerialize;
import infoprog.ProgrammeFilm;
import infoprog.ProgrammeFilmSerialize;
import infoprog.ProgrammeMag;
import infoprog.ProgrammeMagSerialize;
import infoprog.ProgrammeSerie;
import infoprog.ProgrammeSerieSerialize;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GestureDetectorCompat;
import android.text.Html;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloud.ChaineAdapter;
import com.example.cloud.EPGChaine;
import com.example.cloud.EPGChaineSerialize;
import com.example.cloud.EPGNext;
import com.example.cloud.EPGNextSerialize;
import com.example.favoris.FeedReaderDbHelperFavoris;
import com.example.favoris.FeedReaderContractFavoris.FeedEntry;
import com.example.remote.BaseApi;
import com.example.remote.ServerException;
import com.example.remote.UserInterfaceApi;
import com.example.zappv1.R;
import com.example.zappv1.Telecommande;
import com.google.gson.Gson;

public class PreviewType extends Activity implements GestureDetector.OnGestureListener {

	/*** IMAGE Melvin ***/
	private EPGChaine epgChaine;
	private EPGNext nextprog;
	private BaseProgramme basePg;
	private ProgrammeFilm pgFilm;
	private ProgrammeSerie pgSerie;
	private ProgrammeMag pgMag;

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
	private static final String LOG_TAG = "activity";
	//private static final String DEFAULT_BOX_URL = "http://192.168.0.24:8080/api.bbox.lan/V0";
	public static final String SUFFIXE_URL = "/api.bbox.lan/V0";
	public static String URL_HTTP = "";
	private Toast toast;
	int toast_duration;
	String channel;
	String description;
	String nom;
	String chaineId;
	String debutNext, fin;
	TextView textChaine;
	TextView textNom;
	TextView textDescription;
	TextView textDebut,textFin, textNextDebut, textNextFin;
	TextView textDuree, textGenre, textNext, textEpisode;
	ImageView imagette;
	CheckBox checkboxfavoris;
	Button play;
	Bundle extra;
	ArrayList<EPGChaine> epg = new ArrayList<EPGChaine>();
	private static final String DEBUG_TAG = "Gestures";
	private ChaineAdapter adapter;
	private GestureDetectorCompat mDetector; 
	int id;
	String[] parse;
	String progId, listetype;
	ProgressBar mProgressBar;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preview);

		/*** ACTION BAR ***/
		ActionBar actionbar = getActionBar();
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		actionbar.show();
		getActionBar().setBackgroundDrawable(new ColorDrawable(0xFF303030));
		
		textChaine = (TextView)findViewById(R.id.chaineName);
		textNom = (TextView)findViewById(R.id.progNom);
		textDescription = (TextView)findViewById(R.id.progDescription);
		textDebut = (TextView) findViewById(R.id.progDebut);
		textFin = (TextView) findViewById(R.id.progFin);
		textGenre = (TextView) findViewById(R.id.genre);
		textDuree = (TextView) findViewById(R.id.duree);
		mProgressBar = (ProgressBar) findViewById(R.id.progressTest);
		imagette = (ImageView) findViewById(R.id.imagette);
		textNext = (TextView) findViewById(R.id.next);
		textNextDebut = (TextView) findViewById(R.id.progNextDebut);
		textNextFin = (TextView) findViewById(R.id.progNextFin);
		textEpisode = (TextView) findViewById(R.id.episode);
		play = (Button) findViewById(R.id.buttonplay);
		checkboxfavoris = (CheckBox) findViewById(R.id.checkBox1);

		// Instantiate the gesture detector with the
		// application context and an implementation of
		// GestureDetector.OnGestureListener
		mDetector = new GestureDetectorCompat(this,this);


		// Instantiate the gesture detector with the
		// application context and an implementation of
		// GestureDetector.OnGestureListener
		//mDetector = new GestureDetectorCompat(this,this);

		//Récuperation du nom de la chaine envoyé dans la vue ListeChaine
		Bundle extra = getIntent().getExtras();
		extra = getIntent().getExtras();
		if(extra != null)
		{
			listetype = extra.getString("listetype");
			fin = extra.getString("progFin");
			channel = extra.getString("chaineNom");
			textChaine.setText(channel);

			chaineId = extra.getString("chaineId");
			progId= extra.getString("progid");
			getChannelTask gtc = new getChannelTask(epgChaine,getApplicationContext(),chaineId);
			getBaseProgrammeTask gbpt = new getBaseProgrammeTask(basePg,getApplicationContext(),progId);
			getNextProgramTask gnext = new getNextProgramTask(nextprog,getApplicationContext(),chaineId, fin);
			gtc.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			gbpt.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			gnext.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

			parse = listetype.split(",");
			for (int i =0; i< parse.length; i++){
				if (chaineId.equals(parse[i]))
				{
					id = i;
				}
			}

		}

		//Récuperation de l'adresse ip de la box grâce aux préférences 
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		ip = prefs.getString(BOX_PREFERENCES,"null");

		URL_HTTP = "http://"+ip+":8080"+SUFFIXE_URL;


		/*** OPEN DATABASE ***/
		FeedReaderDbHelperFavoris mDbHelper = new FeedReaderDbHelperFavoris(getApplicationContext());
		if (isInDB(parse[id]))
		{
			checkboxfavoris.setChecked(true);
		}
		else checkboxfavoris.setChecked(false);

		play.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				switch(id)
				{

				case 1: sendKeyPressed(UserInterfaceApi.CHANNEL_1);
				break;
				case 2: sendKeyPressed(UserInterfaceApi.CHANNEL_2);
				break;
				case 3: sendKeyPressed(UserInterfaceApi.CHANNEL_3);
				break;
				case 4: sendKeyPressed(UserInterfaceApi.CHANNEL_4);
				break;
				case 5: sendKeyPressed(UserInterfaceApi.CHANNEL_5);
				break;
				case 6: sendKeyPressed(UserInterfaceApi.CHANNEL_6);
				break;
				case 7: sendKeyPressed(UserInterfaceApi.CHANNEL_7);
				break;
				case 8: sendKeyPressed(UserInterfaceApi.CHANNEL_8);
				break;
				case 9: sendKeyPressed(UserInterfaceApi.CHANNEL_9);
				break;
				case 11 : 
					//final CountDownLatch countdownlatch = new CountDownLatch(2);
					sendKeyPressed(UserInterfaceApi.CHANNEL_1);
					sendKeyPressed(UserInterfaceApi.CHANNEL_1);
					break;
				case 12:  sendKeyPressed(UserInterfaceApi.CHANNEL_1);
				sendKeyPressed(UserInterfaceApi.CHANNEL_2);
				break;
				case 13:  sendKeyPressed(UserInterfaceApi.CHANNEL_1);
				sendKeyPressed(UserInterfaceApi.CHANNEL_3);
				break;
				case 14:  sendKeyPressed(UserInterfaceApi.CHANNEL_1);
				sendKeyPressed(UserInterfaceApi.CHANNEL_4);
				break;
				case 15:  sendKeyPressed(UserInterfaceApi.CHANNEL_1);
				sendKeyPressed(UserInterfaceApi.CHANNEL_5);
				break;
				case 16:  sendKeyPressed(UserInterfaceApi.CHANNEL_1);
				sendKeyPressed(UserInterfaceApi.CHANNEL_6);
				break;
				case 17:  sendKeyPressed(UserInterfaceApi.CHANNEL_1);
				sendKeyPressed(UserInterfaceApi.CHANNEL_7);
				break;
				case 18:  sendKeyPressed(UserInterfaceApi.CHANNEL_1);
				sendKeyPressed(UserInterfaceApi.CHANNEL_8);
				break;
				case 19:  sendKeyPressed(UserInterfaceApi.CHANNEL_1);
				sendKeyPressed(UserInterfaceApi.CHANNEL_9);
				break;


				default : break;

				}
				// Perform action on click
			}
		});
	}

	void sendKeyPressed(String key) {
		new SendKeyPressedTask().execute(
				new String[] { URL_HTTP , key});
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
		if(id>=parse.length) id=id-parse.length;
		getChannelTask gtc = new getChannelTask(epgChaine,getApplicationContext(),parse[id]);
		gtc.execute();
		
		
		FeedReaderDbHelperFavoris mDbHelper = new FeedReaderDbHelperFavoris(getApplicationContext());
		if (isInDB(parse[id]))
		{
			checkboxfavoris.setChecked(true);
		}
		else checkboxfavoris.setChecked(false);

	}

	protected void onSwipeRight() {   
		// do your stuff here
		//sendKeyPressed(UserInterfaceApi.CHANNEL_UP); 

		id--;

		if(id<0) id=id+parse.length;
		getChannelTask gtc = new getChannelTask(epgChaine,getApplicationContext(),parse[id]);
		gtc.execute();
		
		FeedReaderDbHelperFavoris mDbHelper = new FeedReaderDbHelperFavoris(getApplicationContext());
		if (isInDB(parse[id]))
		{
			checkboxfavoris.setChecked(true);
		}
		else checkboxfavoris.setChecked(false);
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

			if (result!=null)
			{	
				EPGChaineSerialize ch = new Gson().fromJson(result,EPGChaineSerialize.class);

				//adapter.notifyDataSetChanged();
				chaine = ch;
				if(chaine != null)
				textChaine.setText(chaine.getNom());
				textNom.setText(Html.fromHtml(chaine.getListeProgrammes().getProgrammes().getNom()));

				//adapter.notifyDataSetChanged();
				chaine = ch;
				if(chaine != null)
				textChaine.setText(chaine.getNom());
				if(chaine.getListeProgrammes().getProgrammes().getNom().contains("&#4")){
					String[] parseNom = chaine.getListeProgrammes().getProgrammes().getNom().split("&");
					if (chaine.getListeProgrammes().getProgrammes().getNom().contains("&apos;")){
						textNom.setText(Html.fromHtml(parseNom[0] + "&" + parseNom[1]));
					}else{
						textNom.setText(Html.fromHtml(parseNom[0]));
					}
				}
				else{
					textNom.setText(Html.fromHtml(chaine.getListeProgrammes().getProgrammes().getNom()));
				}
				textDescription.setText(Html.fromHtml(chaine.getListeProgrammes().getProgrammes().getDescription()));


				String[] parse = chaine.getListeProgrammes().getProgrammes().getDebut().split("T");
				String[] debutProg = parse[1].split("Z");
				//enlever les secondes
				String[] debut = debutProg[0].split(":");
				textDebut.setText(debut[0]+":"+debut[1]+" - ");

				String[] parse2 = chaine.getListeProgrammes().getProgrammes().getFin().split("T");
				String[] finProg = parse2[1].split("Z");
				//enlever les secondes
				String[] fin = finProg[0].split(":");
				textFin.setText(fin[0]+":"+fin[1]);
				getBaseProgrammeTask gbpt = new getBaseProgrammeTask(basePg,getApplicationContext(),chaine.getListeProgrammes().getProgrammes().getId());
				getNextProgramTask gnext = new getNextProgramTask(nextprog,getApplicationContext(),chaine.getId(), chaine.getListeProgrammes().getProgrammes().getFin());
				gbpt.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				gnext.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			}
		}

	}
	/*** ACTION MENU ***/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
		//return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(this, Telecommande.class);

		// Handle presses on the action bar items
		switch (item.getItemId()) 
		{
		case R.id.action_alarm:
			startActivity(intent);
			break;
		default: 
			break;
		}

		finish();
		return super.onOptionsItemSelected(item);
	}

	public void makeToast(String message) {
		// with jam obviously
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	/*** END ACTION MENU ***/

	private class getBaseProgrammeTask extends AsyncTask <String,Void,String>
	{
		BaseProgramme bp;
		Context context;
		String id;

		public getBaseProgrammeTask(BaseProgramme b, Context context, String id)
		{
			this.bp = b;
			this.context = context;
			this.id = id;
		}

		@Override
		protected String doInBackground(String... params){
			//Url de la requête permettant d'accéder au Cloud pour récupérer toutes les chaînes en temps réel
			//String url = "http://openbbox.flex.bouyguesbox.fr:81/V0/Media/EPG/Live?period=1";
			//Url de la requete permettant d'accéder au Cloud pour récupérer toutes les chaînes en temps réel
			String url = "http://openbbox.flex.bouyguesbox.fr:81/V0/Media/EPG/Live?programId="+id;
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

		protected void onPostExecute(String result){
			super.onPostExecute(result);
			//Test Guillaume Push
			if (result!=null)
			{	
				BaseProgrammeSerialize bpz = new Gson().fromJson(result,BaseProgrammeSerialize.class);
				bp = bpz;
				if (bp.getProgramme().getListeGenres().getGenre().equals("Série"))
				{
					ProgrammeSerieSerialize pss = new Gson().fromJson(result,ProgrammeSerieSerialize.class);
					pgSerie = pss;
					textEpisode.setText(" - E: "+pgSerie.getProgramme().getSerie().getEpisode() + "\\S: "+pgSerie.getProgramme().getSerie().getSaison());
					
				}
				else{
					textEpisode.setText("");
				}
				if(result.toString().contains("\"firstName\": {}")){
					textGenre.setText(bp.getProgramme().getListeGenres().getGenre());
					String[] parse2 = bp.getProgramme().getDiffusion().getDuree().split("T");
					String[] DureeProg = parse2[1].split("M");
					textDuree.setText(DureeProg[0]);
					//duree du programme en minutes
					String[] duree = DureeProg[0].split("H");
					int dm = (Integer.parseInt(duree[0])*60)+Integer.parseInt(duree[1]);
					//heure actuelle en minutes
					Calendar c = Calendar.getInstance(); 
					int heure = c.get(Calendar.HOUR_OF_DAY);
					int minutes = c.get(Calendar.MINUTE);
					//heure du debut en minutes
					String[] parse3 = bp.getProgramme().getDiffusion().getDebut().split("T");
					String[] debutProg = parse3[1].split("Z");
					String[] debut = debutProg[0].split(":");
					int dd = (Integer.parseInt(debut[0])*60)+Integer.parseInt(debut[1]);

					//difference entre heure actuelle et debut programme
					int difference = (minutes+heure*60) - dd;
					//ratio pour progress bar
					double ratio = (double) difference/ (double) dm;
					mProgressBar.setProgress((int) (ratio*100));

					if (bp.getProgramme().getImagette() != null){ 
						BitmapWorkerTask task = new BitmapWorkerTask(imagette);
						task.execute(bp.getProgramme().getImagette());
					}else{
						imagette.setImageResource(R.drawable.noimage);
					}
				}
				else {
					if (result.toString().contains("[")){
						ProgrammeFilmSerialize pfs = new Gson().fromJson(result,ProgrammeFilmSerialize.class);
						pgFilm = pfs;

						textGenre.setText(pgFilm.getProgramme().getListeGenres().getGenre());
						String[] parse2 = pgFilm.getProgramme().getDiffusion().getDuree().split("T");
						String[] DureeProg = parse2[1].split("M");
						textDuree.setText(DureeProg[0]);
						//duree du programme en minutes
						String[] duree = DureeProg[0].split("H");
						int dm = (Integer.parseInt(duree[0])*60)+Integer.parseInt(duree[1]);
						//heure actuelle en minutes
						Calendar c = Calendar.getInstance(); 
						int heure = c.get(Calendar.HOUR_OF_DAY);
						int minutes = c.get(Calendar.MINUTE);
						//heure du debut en minutes
						String[] parse3 = pgFilm.getProgramme().getDiffusion().getDebut().split("T");
						String[] debutProg = parse3[1].split("Z");
						String[] debut = debutProg[0].split(":");
						int dd = (Integer.parseInt(debut[0])*60)+Integer.parseInt(debut[1]);

						//difference entre heure actuelle et debut programme
						int difference = (minutes+heure*60) - dd;
						//ratio pour progress bar
						double ratio = (double) difference/ (double) dm;
						mProgressBar.setProgress((int) (ratio*100));

						if (pgFilm.getProgramme().getImagette() != null){ 
							BitmapWorkerTask task = new BitmapWorkerTask(imagette);
							task.execute(pgFilm.getProgramme().getImagette());
						}else{
							imagette.setImageResource(R.drawable.noimage);
						}
					}

					else {
						ProgrammeMagSerialize pms = new Gson().fromJson(result,ProgrammeMagSerialize.class);
						pgMag = pms;

						textGenre.setText(pgMag.getProgramme().getListeGenres().getGenre());
						String[] parse2 = pgMag.getProgramme().getDiffusion().getDuree().split("T");
						String[] DureeProg = parse2[1].split("M");
						textDuree.setText(DureeProg[0]);
						//duree du programme en minutes
						String[] duree = DureeProg[0].split("H");
						int dm = (Integer.parseInt(duree[0])*60)+Integer.parseInt(duree[1]);
						//heure actuelle en minutes
						Calendar c = Calendar.getInstance(); 
						int heure = c.get(Calendar.HOUR_OF_DAY);
						int minutes = c.get(Calendar.MINUTE);
						//heure du debut en minutes
						String[] parse3 = pgMag.getProgramme().getDiffusion().getDebut().split("T");
						String[] debutProg = parse3[1].split("Z");
						String[] debut = debutProg[0].split(":");
						int dd = (Integer.parseInt(debut[0])*60)+Integer.parseInt(debut[1]);

						//difference entre heure actuelle et debut programme
						int difference = (minutes+heure*60) - dd;
						//ratio pour progress bar
						double ratio = (double) difference/ (double) dm;
						mProgressBar.setProgress((int) (ratio*100));

						if (pgMag.getProgramme().getImagette() != null){ 
							BitmapWorkerTask task = new BitmapWorkerTask(imagette);
							task.execute(bp.getProgramme().getImagette());
						}else{
							imagette.setImageResource(R.drawable.noimage);
						}
					}
				}
			}
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
	private class getNextProgramTask extends AsyncTask<String, Void, String> {

		EPGNext prog;

		BaseAdapter adapter;
		Context context;
		String id;
		String fin;
		public static final String LOG_TAG = "debug";
		public getNextProgramTask(EPGNext nextprog, Context c,String id, String fin) {
			this.prog = nextprog;
			this.context = c;
			this.id=id;
			this.fin = fin;
		}

		//Fonction qui se lance à l'appel de cette classe
		@Override
		protected String doInBackground(String... params){
			//Url de la requête permettant d'accéder au Cloud pour récupérer toutes les chaînes en temps réel
			//String url = "http://openbbox.flex.bouyguesbox.fr:81/V0/Media/EPG/Live?period=1";
			//Url de la requete permettant d'accéder au Cloud pour récupérer toutes les chaînes en temps réel
			String url = "http://openbbox.flex.bouyguesbox.fr:81/V0/Media/EPG/Live/?TVChannelsId="+id+"&period=1";
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

			if (result!=null)
			{
				EPGNextSerialize next = new Gson().fromJson(result,EPGNextSerialize.class);


				//adapter.notifyDataSetChanged();
				prog = next;
				int j=0;
				if(prog != null)
				for (int i=0; i<prog.getListeProgrammes().getProgrammes().size(); i++){
					if (prog.getListeProgrammes().getProgrammes().get(i).getDebut().equals(fin)){
						j = i;
						if(prog.getListeProgrammes().getProgrammes().get(j).getNom().contains("&#4")){
							String[] parseNom = prog.getListeProgrammes().getProgrammes().get(j).getNom().split("&");

							if (prog.getListeProgrammes().getProgrammes().get(j).getNom().contains("&apos;")){
								textNext.setText(Html.fromHtml(parseNom[0] + "&" + parseNom[1]));
							}else{
								textNext.setText(Html.fromHtml(parseNom[0]));
							}
						}
						else{
							textNext.setText(Html.fromHtml(prog.getListeProgrammes().getProgrammes().get(j).getNom()));
						}

						String[] parse = prog.getListeProgrammes().getProgrammes().get(i).getDebut().split("T");
						String[] debutProg = parse[1].split("Z");
						//enlever les secondes
						String[] debut = debutProg[0].split(":");
						textNextDebut.setText(debut[0]+":"+debut[1]+" - ");
						String[] parse2 = prog.getListeProgrammes().getProgrammes().get(i).getFin().split("T");
						String[] finProg = parse2[1].split("Z");
						//enlever les secondes
						String[] fin = finProg[0].split(":");
						textNextFin.setText(fin[0]+":"+fin[1]);
					}
				}

			}
		}
	}

	public void addFavoristoDB(View view) {
		//delete favori if click and true
		if(isInDB(parse[id]))
		{
			deleteFavoris(parse[id]);
			this.setDeletedToast();
			toast.show();
			checkboxfavoris.setChecked(false);
		}else
		{
			// Do something in response to button

			saveFavoris(parse[id]); // A VOIR !!!!
			this.setAddedToast();
			toast.show();
			//toast.cancel();
			checkboxfavoris.setChecked(true);
		}
	}

	public Boolean isInDB(String imdbId){
		FeedReaderDbHelperFavoris mDbHelper = new FeedReaderDbHelperFavoris(getApplicationContext());
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		String selectQuery = "SELECT "+ FeedEntry.COLUMN_NAME_ID + " FROM " + FeedEntry.TABLE_NAME + " WHERE " + FeedEntry.COLUMN_NAME_ID + " = "+ imdbId;
		System.out.println("REQUEST DB:"+selectQuery);
		Cursor cursor = db.rawQuery(selectQuery, null);
		if(cursor.getCount() !=0){
			db.close();
			return true;
		}else{
			db.close();
			return false;			
		}
	}

	public void deleteFavoris(String channel){
		FeedReaderDbHelperFavoris mDbHelper = new FeedReaderDbHelperFavoris(getApplicationContext());
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		// Define 'where' part of query.
		//String selection = FeedEntry.COLUMN_NAME_ID + " LIKE ?";
		//db.delete(FeedEntry.TABLE_NAME, selection, null);
		db.delete(FeedEntry.TABLE_NAME, FeedEntry.COLUMN_NAME_ID + " = " +channel, null);
		db.close();
	}

	public void saveFavoris(String channel){
		// Gets the data repository in write mode
		FeedReaderDbHelperFavoris mDbHelper = new FeedReaderDbHelperFavoris(getApplicationContext());
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(FeedEntry.COLUMN_NAME_ID , channel );

		// Insert the new row, returning the primary key value of the new row
		long newRowId;
		newRowId = db.insert(
				FeedEntry.TABLE_NAME,
				FeedEntry.COLUMN_NAME_ID,
				values);
		db.close();
	}

	@SuppressLint("ShowToast")
	private void setAddedToast(){
		Context context = getApplicationContext();
		CharSequence text = "Chaîne ajoutée avec succès !";
		toast = Toast.makeText(context, text, toast_duration);
	}
	@SuppressLint("ShowToast")
	private void setDeletedToast(){
		Context context = getApplicationContext();
		CharSequence text = "Chaîne supprimée avec succès !";
		toast = Toast.makeText(context, text, toast_duration);
	}
}

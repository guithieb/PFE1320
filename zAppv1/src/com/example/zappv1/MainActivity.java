package com.example.zappv1;

import com.example.zappv1.NavDrawerListAdapter;
import com.example.zappv1.NavDrawerItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.example.zappv1.MainActivity;
import com.example.zappv1.R;
import com.invities.deviceWatcher.DeviceManager;
import com.invities.deviceWatcher.DeviceWatcher;
import com.invities.deviceWatcher.SocialWatcher.Room;
import com.invities.upnp.Device;
import com.testflightapp.lib.TestFlight;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.preference.PreferenceManager;
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)

/***
 * Première classe qui se lance pour une application android
 *      - sert à identifier la box parmis tout les devices que l'on trouve dans le réseau
 *      - initialiser le drawer
 *      - 
 * 
 * 
 */

public class MainActivity extends Activity {

	int count = 0;
	static ListView listView;
	static TextView t;
	public static String deviceList = "devices are : "; 

	/*** Navigation Drawer Variables ***/	
	private DrawerLayout mDrawerLayout;		// Within which the entire activity is enclosed
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	/*** END Navigation Drawer Variables ***/




	private ActionBarDrawerToggle actionBarDrawerToggle;
	private int selection = 0;
	private int oldSelection = -1;
	final String[] data ={"Liste des chaînes","Favoris","Recommandations"};
	//Liste des différentes vues liées au drawer
	final static String[] fragments ={
		"com.example.zappv1.ListeChaine",
		"com.example.zappv1.Favoris",
	"com.example.zappv1.Recommandation"};
	private static final String TAG = "MyActivity";
	public static final String BOX_PREFERENCES = "boxPrefs";
	private String ip;
	private Toast toast;
	private int toast_duration;
	private static long back_pressed;


	public static Handler mHandler = new Handler(Looper.getMainLooper()) {
		@Override
		public void handleMessage(Message inputMessage) {
			//Log.d("mHandler",">> message to UI thread!");

			// On recoit un hashmap avec les parametres initiaux
			// On supporte de la part de la page HTML quelques ordres, qui vont etre envoyes au thread de l'activitŽ.
			@SuppressWarnings("unchecked")
			Map<String,Object> params = (Map<String,Object>)inputMessage.obj;

			if("setText".equals(params.get("functionName")))
			{
				String newText = (String)params.get("text");
				String which = (String)params.get("which");

				if("1".equals(which)) Log.d(TAG,"HELLO");
			}

			else
			{
				// On est dans setList
				//HashMapAdapter adapter = new HashMapAdapter(params);
				//params.put("adapter", adapter);
				//adapter = (HashMapAdapter) params.get("adapter");
				//listView.setAdapter(adapter);
				int i=0;
				i++;
			}
		}

	};


	//Fonction obligatoire dans une classe qui hérite de la classe Activity, Fragement, FragmentActivity
	// Se lance quand on accède à l'application
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Initialize TestFlight with your app token.
		TestFlight.takeOff(this.getApplication(), "75caa7f3-4e56-473e-aa7c-d3cdc85847ca");

		/*** Navigation Drawer ***/
		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Liste des chaines
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		// Favoris
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		// Recommandations
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		// Code en dur avec la couleur #303030
		getActionBar().setBackgroundDrawable(new ColorDrawable(0xFF303030));

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
				) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
		/*		
		 *//*** ACTION BAR ***//*
		//ActionBar actionbar = getActionBar();
		//actionbar.show();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActionBar().getThemedContext(), android.R.layout.simple_list_item_1, data);

		//Initialisation du drawer
		final DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);

		// DrawerToggle (bouton home pour activer/désactiver le navigation drawer ; avec l'icone du drawer)
		mDrawerToggle = new ActionBarDrawerToggle(this, drawer, R.drawable.ic_list, R.string.drawer_open, R.string.drawer_close);
		drawer.setDrawerListener(mDrawerToggle);
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		//getActionBar().setHomeButtonEnabled(true);
		updateContent();
		final ListView navList = (ListView) findViewById(R.id.drawer);
		navList.setAdapter(adapter);
		navList.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int pos,long id){
				drawer.setDrawerListener( new DrawerLayout.SimpleDrawerListener(){
					@Override
					public void onDrawerClosed(View drawerView){
						super.onDrawerClosed(drawerView);
						FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
						tx.replace(R.id.main, Fragment.instantiate(MainActivity.this, fragments[pos]));
						tx.commit();
					}
				});
				drawer.closeDrawer(navList);
			}
		});

		FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
		tx.replace(R.id.main,Fragment.instantiate(MainActivity.this, fragments[0]));
		tx.commit();




		  *//*** Module ID IP téléphone ***/
		// On lance la librairie qui gère les devices, avec la callback pour les notifications natives
		final DeviceWatcher deviceWatcher = DeviceWatcher.getInstance(getApplicationContext());
		// On crée pour cela un DeviceManager
		DeviceManager deviceManager = new DeviceManager() {
			@Override
			public String searchType() {
				return DeviceWatcher.LOCAL_NETWORK;// + "/post_to/TF1";
			}

			@Override
			public void onDeviceRemove(Device device) {
				Log.d("deviceManager",">> device remove "+device.ip);
				setText("1","Removed device(s)!");
			}

			@Override
			public void onDeviceAdd(Device device) {
				Log.d("deviceManager",">> device add "+device.ip);
				deviceList += device.id+" ("+device.ip+"): "+device.friendlyName;
				setText("1",deviceList);
			}

			@Override
			public void onNewDeviceList(ConcurrentHashMap<Integer,Device> devices) {
				deviceList = "Updated "+(++count)+"\n";
				int i,j=devices.size();Device device;
				for(i=1;i<=j;++i)
				{
					device = devices.get(i);
					// TODO: Ajouter un Toast qui indique que la box est connectée 
					// problème car la fonction tourne en boucle
					if(device != null)

					{

						deviceList += device.id+" ("+device.ip+"): "+device.friendlyName+"\n";//devices.get(i).id+" ";

						//Identification de la box par le nom de son attribut DeviceType
						if(device.deviceType != null){
							if(device.deviceType.contains("urn:schemas-upnp-org:device:MediaRenderer:1")) 
							{
								Log.d(TAG,"TEST REUSSI"+device.friendlyName);

								//Toast.makeText(MainActivity.this, "Open STB correctement détectée",
								//Toast.LENGTH_SHORT).show();   // BUG

								ip=device.ip;
								//deviceWatcher.stop();   BUG -----> FAIRE MAJ BOX
							}
							//On met dans les préférences du téléphone l'adresse ip pour que l'on puisse la retrouver dans 
							//n'importe quelle vue
							SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
							SharedPreferences.Editor prefEditor = settings.edit();
							prefEditor.putString(BOX_PREFERENCES,ip);
							prefEditor.commit();

						}

						else {Log.d(TAG,"TEST RATE");}


					}
				}
				setText("1",deviceList);
				//setAddedToast();
				//toast.show();
			}

			//*** Toast pour la détection Open STB ***//*
			/*			@SuppressLint("ShowToast")
			private void setAddedToast() {
				Context context = getApplicationContext();
				CharSequence text = "Open STB OK";
				toast = Toast.makeText(context, text, toast_duration);
			}*/


		};
		deviceWatcher.search(deviceManager);

	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
	ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// pour l'accés à la télécommande depuis n'importe où
		Intent intent = new Intent(this, Telecommande.class);
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_alarm:
			startActivity(intent);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);

	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}


	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		android.app.Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new ListeChaine();
			break;
		case 1:
			fragment = new Favoris();
			break;
		case 2:
			fragment = new Recommandation();
			break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	/*	private void updateContent() {
		//getActionBar().setTitle(data[selection]); TODO afficher le MenuItem dans l'ActionBar
		if(selection != oldSelection)
		{
			FragmentTransaction tx = getSupportFragmentManager()
					.beginTransaction();
			tx.replace(R.id.main,Fragment.instantiate(MainActivity.this,
					fragments[selection]));
			tx.commit();
			oldSelection = selection;
		}

	}*/
	/*
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		Intent intent = new Intent(this, Telecommande.class);
		if(mDrawerToggle.onOptionsItemSelected(item))
		{
			return true;
		}
		switch (item.getItemId()) 
		{
		case R.id.action_alarm:
			startActivity(intent);
			break;
		default: 
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	 *//*** ACTION MENU ***//*
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


		return true;
	}
	  */
	public static void setText(String Which,String newText) {
		Map<String,String> v_params = new HashMap<String,String>();
		v_params.put("functionName", "setText");
		v_params.put("which", ""+Which);
		v_params.put("text", newText);
		mHandler.sendMessage(mHandler.obtainMessage(1,v_params));		
	}

	/*** Dialog box lorsque l'on quitte l'application ***/
	// TODO: A MODIFIER ----> double tap pour quitter l'appli; simple tap pour display un Toast
	// TODO: faire en sorte d'éviter de quitter l'appli depuis un autre Fragment !!
	/*public void onBackPressed() {

		final Builder builder = new Builder(this);
		builder.setTitle(R.string.app_name);
		builder.setMessage("Voulez-vous vraiment quitter l'application ?");
		builder.setPositiveButton(android.R.string.ok, new OnClickListener() {
			@Override
			public void onClick(final DialogInterface dialog, final int which) {
				MainActivity.this.finish();
				dialog.dismiss();    
			}
		});
		builder.setNegativeButton(android.R.string.cancel,
				new OnClickListener() {
			@Override
			public void onClick(final DialogInterface dialog,
					final int which) {
				dialog.dismiss();
			}
		});
		final AlertDialog dialog = builder.create();
		dialog.show();
	}
	 */
	/*** Double tap pour quitter l'application (sur 2sec) ***/
	@Override
	public void onBackPressed()
	{
		if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
		else Toast.makeText(getBaseContext(), "Appuyer encore pour quitter!", Toast.LENGTH_SHORT).show();
		back_pressed = System.currentTimeMillis();
	}

	/*class HashMapAdapter extends BaseAdapter {
		    private HashMap<String,String > mData = new HashMap<String, String>();
		    private String[] mKeys;
		    public HashMapAdapter(LinkedHashMap<String, String> data){
		        mData  = data;
		        mKeys = mData.keySet().toArray(new String[data.size()]);
		    }

		    @Override
		    public int getCount() {
		        return mData.size();
		    }

		    @Override
		    public String getItem(int position) {
		        return mData.get(mKeys[position]);
		    }

		    @Override
		    public long getItemId(int arg0) {
		        return arg0;
		    }

		    @Override
		    public View getView(int pos, View convertView, ViewGroup parent) {
		        //String key = mKeys[pos];
		        //String value = getItem(pos).getName();

		        //do your view stuff here

	            LayoutInflater inflater = getLayoutInflater();
	            View row = inflater.inflate(R.layout.custom, parent, false);
	            TextView title, detail;
	            //ImageView i1;
	            title = (TextView) row.findViewById(R.id.title);
	            detail = (TextView) row.findViewById(R.id.detail);
	            //i1=(ImageView)row.findViewById(R.id.img);
	           // title.setText(value);
	            detail.setText("Click to enter this room");
	            return (row);
		    }*/

	// Toast pour la détection de la Open STB


}


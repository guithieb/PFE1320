package com.example.zappv1;

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

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
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

public class MainActivity extends FragmentActivity {

	int count = 0;
	static ListView listView;
	static TextView t;
	public static String deviceList = "devices are : "; 
	private String[] drawerListViewItems;	
	private DrawerLayout drawerLayout;		// Within which the entire activity is enclosed
	private ListView drawerListView;
	private ActionBarDrawerToggle drawerToggle;
	private ActionBarDrawerToggle actionBarDrawerToggle;
	private int selection = 0;
	private int oldSelection = -1;
	final String[] data ={"Liste des chaînes","Favoris","Recommandation"};
	//Liste des différentes vues liées au drawer
	final static String[] fragments ={
			"com.example.zappv1.ListeChaine",
			"com.example.zappv1.Favoris",
			"com.example.zappv1.Recommandation"};
	private static final String TAG = "MyActivity";
	public static final String BOX_PREFERENCES = "boxPrefs";
	private String ip;

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



		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActionBar().getThemedContext(), android.R.layout.simple_list_item_1, data);

		//Initialisation du drawer
		final DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
		
		// DrawerToggle (bouton home pour activer/désactiver le navigation drawer ; avec l'icone du drawer)
		drawerToggle = new ActionBarDrawerToggle(this, drawer, R.drawable.ic_list, R.string.drawer_open, R.string.drawer_close);
		drawer.setDrawerListener(drawerToggle);
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
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
		
		/*FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
		tx.replace(R.id.main,Fragment.instantiate(MainActivity.this, fragments[0]));
		tx.commit();
		*/



		/*** Module ID IP téléphone ***/
		// On lance la librairie qui gère les devices, avec la callback pour les notifications natives
		final DeviceWatcher deviceWatcher = DeviceWatcher.getInstance(getApplicationContext()/*,MainActivity.class,R.drawable.ic_launcher*/);
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
					if(device != null)
					{
						deviceList += device.id+" ("+device.ip+"): "+device.friendlyName+"\n";//devices.get(i).id+" ";

						//Identification de la box par le nom de son attribut DeviceType
						if(device.deviceType != null){
							if(device.deviceType.contains("urn:schemas-upnp-org:device:MediaRenderer:1")) 
							{
								Log.d(TAG,"TEST REUSSI"+device.friendlyName);
							ip=device.ip;
							//deviceWatcher.stop();
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
			}
		};
		deviceWatcher.search(deviceManager);

	}
	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
	    super.onPostCreate(savedInstanceState);
	    drawerToggle.syncState();
	}

	private void updateContent() {
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
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
	    if(drawerToggle.onOptionsItemSelected(item))
	    {
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
	

	public static void setText(String Which,String newText) {
		Map<String,String> v_params = new HashMap<String,String>();
		v_params.put("functionName", "setText");
		v_params.put("which", ""+Which);
		v_params.put("text", newText);
		mHandler.sendMessage(mHandler.obtainMessage(1,v_params));		
	}

	/*** Dialog box lorsque l'on quitte l'application ***/
	// TODO: faire en sorte d'éviter de quitter l'appli depuis un autre Fragment !!
	public void onBackPressed() {

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
}


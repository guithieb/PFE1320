package com.example.zappv1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.example.remote.ServerException;
import com.example.remote.UserInterfaceApi;



public class Telecommande extends Activity{

	private SeekBar seekBar1;
	EditText ecran;
	Button button1, button2, button3, button4, button5, button6, button7;
	Button button8, button9, button0, buttonOk,buttonMute;
	private static final String TAG = "MyActivity";
	private String ip;
	public static final String SUFFIXE_URL = "/api.bbox.lan/V0";
	public static String URL_HTTP = "";
	public static final String BOX_PREFERENCES = "boxPrefs";
	Button moins, plus;
	ImageButton back,mute;
	Toast toast;  


	@Override
	public void onCreate(Bundle savedInstanceState) { 
		//initialisation de la vue Télécommande
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telecommande);

		// Retour sur la vue précédente
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);
		getActionBar().setTitle("Télécommande");
		getActionBar().setBackgroundDrawable(new ColorDrawable(0xFF303030));
		
		seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
		button0 = (Button) findViewById(R.id.button0);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);
		button5 = (Button) findViewById(R.id.button5);
		button6 = (Button) findViewById(R.id.button6);
		button7 = (Button) findViewById(R.id.button7);
		button8 = (Button) findViewById(R.id.button8);
		button9 = (Button) findViewById(R.id.button9);
		buttonOk = (Button) findViewById(R.id.buttonOk);
		plus = (Button) findViewById(R.id.plus);
		moins = (Button) findViewById(R.id.moins);
		back = (ImageButton) findViewById(R.id.back);
		mute = (ImageButton) findViewById(R.id.buttonMute);
		ecran = (EditText) findViewById(R.id.EditText01);


		seekBar1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int progressChanged = 0;

			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
				progressChanged = progress;
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}

			public void onStopTrackingTouch(SeekBar seekBar) {
				/*Toast.makeText(Reglages.this,"seek bar progress:"+progressChanged,
                         Toast.LENGTH_SHORT,test).show();*/
				Log.d(TAG,"seek bar progress:"+progressChanged);
			}
		});

		//récupérer l'IP de la box
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		ip = prefs.getString(BOX_PREFERENCES,"null");
		Log.d(TAG,"IP22"+ip);

		URL_HTTP = "http://"+ip+":8080"+SUFFIXE_URL;
		Log.d(TAG,"IP"+ip);

		button0.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				chiffreClick("0");
			}
		});
		button1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				chiffreClick("1");
			}
		});
		button2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				chiffreClick("2");
			}
		});
		button3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				chiffreClick("3");
			}
		});
		button4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				chiffreClick("4");
			}
		});
		button5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				chiffreClick("5");
			}
		});
		button6.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				chiffreClick("6");
			}
		});
		button7.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				chiffreClick("7");
			}
		});
		button8.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				chiffreClick("8");
			}
		});
		button9.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				chiffreClick("9");
			}
		});
		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				DeleteClick();
			}
		});

		buttonOk.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(ecran.getText().toString().isEmpty()){
					//message d'erreur si aucune chaîne est envoyée
					AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
					builder1.setMessage("Renseigner une chaîne");
					builder1.setCancelable(true);
					builder1.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
					AlertDialog alert = builder1.create();
					alert.show();
				}else{

					//envoi du numéro de la chaîne à la télé
					switch(Integer.parseInt(ecran.getText().toString()))
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
				ecran.setText("");
			}
		});
		//change channel (next & preview)
		plus.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				sendKeyPressed(UserInterfaceApi.CHANNEL_UP);
			}
		});

		moins.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				sendKeyPressed(UserInterfaceApi.CHANNEL_DOWN);
			}
		});
		//met le volume à nul
		mute.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				sendKeyPressed(UserInterfaceApi.CHANNEL_MUTE);

			}
		});

	}



	//voici la méthode qui est exécutée lorsque l'on clique sur un bouton chiffre
	public void chiffreClick(String str) {

		if (ecran.getText().toString().isEmpty()){
			ecran.setText(str);
		}
		else if (ecran.getText().toString().length()==2){
			AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
			builder1.setMessage("seulement les chaînes entre 1 et 19");
			builder1.setCancelable(true);
			builder1.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			});


			AlertDialog alert = builder1.create();
			alert.show();
		}else {
			if (ecran.getText().toString().equals("1")){
				str = ecran.getText() + str;
				ecran.setText(str);
			}
			else {
				AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
				builder1.setMessage("seulement les chaînes entre 1 et 19");
				builder1.setCancelable(true);
				builder1.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});


				AlertDialog alert = builder1.create();
				alert.show();
			}

		}
	}
	public void DeleteClick(){
		//suppresion d'un chiffre du numéro dans l'EditText
		String delete = ecran.getText().toString();
		if (ecran.getText().toString().length()==2){
			String[] nouveau = delete.split("");
			ecran.setText(nouveau[1]);
		}else{
			ecran.setText("");
		}

	}

	void sendKeyPressed(String key) {
		new SendKeyPressedTask().execute(
				new String[] { URL_HTTP , key});

	}

	//Appel de la fonction SendKey de la classe UserIntefaceApi pour pouvoir envoyer les commande de remote
	private class SendKeyPressedTask extends AsyncTask<String, Void, String> {
		private Exception mException = null;

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

}

package com.example.zappv1;

import com.example.remote.ServerException;
import com.example.remote.UserInterfaceApi;



import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Preview extends Activity {
//Commentair
	// melvin
  //Guillaume
//coucou test
  private static final String LOG_TAG = Reglages.class.getSimpleName();
  private static final String DEFAULT_BOX_URL = "http://10.0.2.2:8080/api.bbox.lan/V0";
  private Button programUp,programDown;
  String channel;
  TextView textChaine;
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.preview);
    
    textChaine = (TextView)findViewById(R.id.chaineName);
    
    Bundle nomChaine = getIntent().getExtras();
      if(nomChaine != null)
      {
        channel = nomChaine.getString("chaineID");
        textChaine.setText(channel);
      }

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


  }

  private void sendKeyPressed(String key){
    new SendKeyPressedTask().execute(
        new String[] { DEFAULT_BOX_URL , key});    
  }

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
  }
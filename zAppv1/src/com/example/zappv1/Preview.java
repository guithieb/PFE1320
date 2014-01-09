package com.example.zappv1;

import com.example.remote.ServerException;
import com.example.remote.UserInterfaceApi;









import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Preview extends Activity implements GestureDetector.OnGestureListener {
//Commentair
	// melvin
  //Guillaume
//coucou test
  private static final String LOG_TAG = Reglages.class.getSimpleName();
  private static final String DEFAULT_BOX_URL = "http://192.168.0.24:8080/api.bbox.lan/V0";
  private Button programUp,programDown;
  String channel;
  TextView textChaine;
  
  private static final String DEBUG_TAG = "Gestures";
  private GestureDetectorCompat mDetector; 
  
 
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.preview);
    
    textChaine = (TextView)findViewById(R.id.chaineName);
    
 // Instantiate the gesture detector with the
    // application context and an implementation of
    // GestureDetector.OnGestureListener
    mDetector = new GestureDetectorCompat(this,this);

    
 // Instantiate the gesture detector with the
    // application context and an implementation of
    // GestureDetector.OnGestureListener
    mDetector = new GestureDetectorCompat(this,this);
  
    
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


  @Override
  public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
      float velocityY) {
    // TODO Auto-generated method stub
    sendKeyPressed(UserInterfaceApi.CHANNEL_UP); 
    return false;
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
  
 
}
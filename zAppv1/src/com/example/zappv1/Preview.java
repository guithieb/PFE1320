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

	// *** Melvin Gesture *** //
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private static final String TAG = "MyActivity";
	//*** Melvin Gesture *** //

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
		sendKeyPressed(UserInterfaceApi.CHANNEL_DOWN);
		Log.d(TAG,"CHANNEL DOWN");
	}

	protected void onSwipeRight() {   
		// do your stuff here
		sendKeyPressed(UserInterfaceApi.CHANNEL_UP); 
		Log.d(TAG,"CHANNEL UP");
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
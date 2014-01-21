package com.example.zappv1;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;




import com.example.zappv1.R;
import com.example.remote.UserInterfaceApi;
import com.example.remote.ServerException;



public class Reglages extends Fragment{

	private SeekBar volumeControl = null;
	Toast toast;
	private static final String TAG = "Reglages";
  
	public static Fragment newInstance(Context context){
		Reglages f = new Reglages();
    	 
        return f;
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) { 
    	super.onCreate(savedInstanceState);
    	View root = (View) inflater.inflate(R.layout.reglages, null);
    	 volumeControl = (SeekBar) root.findViewById(R.id.volume_bar);
    	 volumeControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
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
       
        return root;
    }
    
   
      
}
   



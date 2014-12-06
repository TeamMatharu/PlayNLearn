package com.example.playnlearn;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class GameSetting extends Activity {
	Switch TB_Sound;
	Switch TB_Vibration; 
	SeekBar sb_Sound;
	TextView tv;
	RatingBar rb;
	private boolean mIsBound = false;
    MusicService mServ;
	public static Boolean Soundon_off;
	public static Boolean Vibrationon_off;
	
	private AudioManager audioManager = null; 
	Context context = GameSetting.this;
	SharedPreferences sharedPref ;
	Intent music = new Intent();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_setting);
		 sharedPref = context.getSharedPreferences(
			        getString(R.string.SharedPref), Context.MODE_PRIVATE);
		 
		 rb=(RatingBar)findViewById(R.id.ratingBar1);
		TB_Sound=(Switch)findViewById(R.id.switch1);

		 TB_Vibration=(Switch) findViewById(R.id.switch2);
		
		sb_Sound=(SeekBar) findViewById(R.id.seekBar1);
		
		
		tv=(TextView)findViewById(R.id.tvcontact);
		tv.setPaintFlags(tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
	    int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	    int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
	    sb_Sound.setMax(maxVolume);
	    sb_Sound.setProgress(curVolume);
	    tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent(GameSetting.this,ContactUs.class);
				startActivity(i);
				
			}
		});
	    rb.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				if (event.getAction() == MotionEvent.ACTION_UP) {
                    float touchPositionX = event.getX();
                    float width = rb.getWidth();
                    float starsf = (touchPositionX / width) * 5.0f;
                    int stars = (int)starsf + 1;
                    rb.setRating(stars);
                    if(rb.getRating()>3.5)
                    {
                    Toast.makeText(GameSetting.this, String.valueOf("Thank you for Liking Us!!!"), Toast.LENGTH_SHORT).show();                   
                    v.setPressed(false);
                    }
                    else
                    {
                    	Toast.makeText(GameSetting.this, String.valueOf("Please provide your Feedback to improve over App!"), Toast.LENGTH_SHORT).show();                   
                        v.setPressed(false);
                    }
               }
               if (event.getAction() == MotionEvent.ACTION_DOWN) {
                   v.setPressed(true);
               }

               if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                   v.setPressed(false);
               }

				
			/*	if(rb.getRating()>3.5)
				{
					Toast.makeText(getApplicationContext(), "Thank you!!!", 500).show();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "We will Improve this App please provide us your Feedback.", 500).show();
				}
				return false;*/
               return true;
			}
		});
        music.setClass(this, MusicService.class);
        checkPreviousState();
        
		TB_Sound.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
		            // The toggle is enabled
			        startService(music);
					Soundon_off=true;
					SharedPreferences.Editor editor = sharedPref.edit();
					editor.putBoolean("on/off", Soundon_off);
					editor.commit();
					Toast.makeText(GameSetting.this, "Sound is turned on", Toast.LENGTH_SHORT).show();
					
		        } else {
		        	
		        	stopService(music);
		        	
		            // The toggle is disabled
		        	Soundon_off=false;
		        	SharedPreferences.Editor editor = sharedPref.edit();
					editor.putBoolean("on/off", Soundon_off);
					editor.commit();
					Toast.makeText(GameSetting.this, "Sound is turned off", Toast.LENGTH_SHORT).show();
		        }
			}
		});

		TB_Vibration.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				
				if(isChecked){
					Vibrationon_off=true;
					SharedPreferences.Editor editor = sharedPref.edit();
					editor.putBoolean("Vibrationon/off", Vibrationon_off);
					editor.commit();
					Toast.makeText(GameSetting.this, "Vibration is turned on", Toast.LENGTH_SHORT).show();
				}else{
					Vibrationon_off=false;
					SharedPreferences.Editor editor = sharedPref.edit();
					editor.putBoolean("Vibrationon/off", Vibrationon_off);
					editor.commit();
					Toast.makeText(GameSetting.this, "Vibration is turned off", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		
		
		
		
		sb_Sound.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
				
			}
		});
		
	}

	
	private ServiceConnection Scon = new ServiceConnection() {
		 
		 
        @Override
		public void onServiceConnected(ComponentName name, IBinder binder) {
            mServ = ((MusicService.ServiceBinder) binder).getService();
        }

        @Override
		public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService() {
        bindService(new Intent(this, MusicService.class), Scon,
                Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService() {
        if (mIsBound) {
            unbindService(Scon);
            mIsBound = false;
        }
    }
	
	public void checkPreviousState(){
		if(sharedPref.getBoolean("on/off", true)==false){
	        TB_Sound.setChecked(false);
	        Log.i("insideb a", "aaaaa");
	        }else{
	        	TB_Sound.setChecked(true);
	        }
	        if(sharedPref.getBoolean("Vibrationon/off", true)==false){
	            TB_Vibration.setChecked(false);
	            Log.i("insideb b", "bbbbb");
	            }else{
	            	TB_Vibration.setChecked(true);
	          }
	}
}

package com.example.playnlearn;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Switch;
import android.widget.ToggleButton;


public class GameSetting extends Activity {
	Switch TB_Sound;
	SeekBar sb_Sound;
	private boolean mIsBound = false;
    MusicService mServ;
	public static Boolean Soundon_off;
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
		TB_Sound=(Switch)findViewById(R.id.switch1);
		sb_Sound=(SeekBar) findViewById(R.id.seekBar1);
		audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
	    int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	    int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
	    sb_Sound.setMax(maxVolume);
	    sb_Sound.setProgress(curVolume);
	    
        music.setClass(this, MusicService.class);
        if(sharedPref.getBoolean("on/off", true)==false){
        TB_Sound.setChecked(false);
        Log.i("insideb a", "aaaaa");
        }else{
        	TB_Sound.setChecked(true);
        }
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
					
		        } else {
		        	
		        	stopService(music);
		        	
		            // The toggle is disabled
		        	Soundon_off=false;
		        	SharedPreferences.Editor editor = sharedPref.edit();
					editor.putBoolean("on/off", Soundon_off);
					editor.commit();
					
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
	
	
}

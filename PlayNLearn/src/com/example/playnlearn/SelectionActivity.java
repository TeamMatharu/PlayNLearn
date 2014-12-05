package com.example.playnlearn;

import com.playnlearn.classes.Setting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SelectionActivity extends Activity {
	int backButtonCount =0;
		private boolean mIsBound = false;

	    MusicService mServ;
	 ImageView imv;
	 SharedPreferences sharedPref;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selection);
		
		Context context = getApplicationContext();
		 sharedPref = context.getSharedPreferences(
		        getString(R.string.SharedPref), Context.MODE_PRIVATE);
		
		Boolean b;
		b=sharedPref.getBoolean("on/off", true);
		if(b){
			doBindService();
			Intent music = new Intent();
	        music.setClass(this, MusicService.class);
	        startService(music);
		}
		
        imv=(ImageView) findViewById(R.id.imageView1);
		ImageButton btnguest, btnprof;
		TextView tv;
		if (getIntent().getBooleanExtra("EXIT", false)) {
			finish();
		}
		tv = (TextView) findViewById(R.id.txtcrtusr);

		tv.setPaintFlags(tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SelectionActivity.this,
						NewProfileActivity.class);
				startActivity(i);
			}
		});
		btnprof = (ImageButton) findViewById(R.id.imgbtnprof);
		btnprof.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SelectionActivity.this,
						UserListActivity.class);
				startActivity(i);
			}
		});

		btnguest = (ImageButton) findViewById(R.id.imgbtnGuest);

		btnguest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(SelectionActivity.this,
						Progressbar2Activity.class);
				startActivity(i);
			}
		});
		
		Button btnins = (Button)findViewById (R.id.btninstruction);
		btnins.setOnClickListener(new OnClickListener() {
			

			@Override
			public void onClick(View v) {

				Intent i = new Intent(SelectionActivity.this, Instruction.class);
				startActivity(i);
			}
		});
	imv.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
	});
	
	}

	
	
	 @Override
	public void onBackPressed() {
		
		 //super.onBackPressed();
		 if (sharedPref.getBoolean("Vibrationon/off", true)) {
				Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				vibe.vibrate(Setting.VibrationIntensity1);
			}

		openAlertDialoug();
		
		//super.onBackPressed();
	}
	 
	   	




	private void openAlertDialoug() {
		AlertDialog.Builder adb=new AlertDialog.Builder(SelectionActivity.this);
		adb.setTitle("Confirmation!");
		adb.setMessage("Are you Sure want to Quit this game?");
		adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				Intent music = new Intent();
		        music.setClass(SelectionActivity.this, MusicService.class);
		        stopService(music);
		    	doBindService();
		    	onDestroy();
				SelectionActivity.this.finish();
			}
		});
		adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				/*Intent ino=new Intent(getApplicationContext(),ProfilequestionActivity.class);
				startActivity(ino);*/
				
				
			}
		});
		AlertDialog adbox=adb.create();
		adbox.show();
		
	

   	
		
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
	    
		@Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			//mServ.pauseMusic();
			//mServ.stopMusic();
			
			doUnbindService();
			Intent music = new Intent();
	        music.setClass(this, MusicService.class);
	        stopService(music);
		}

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
		        getString(R.string.SharedPref), Context.MODE_PRIVATE);
		Boolean b;
		b=sharedPref.getBoolean("on/off", true);
		if(b==false){
			doUnbindService();
			Intent music = new Intent();
	        music.setClass(this, MusicService.class);
	        stopService(music);
		}
	}



		
	    
	    
	    @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// TODO Auto-generated method stub
	    	 MenuInflater inflater = getMenuInflater();
	    	    inflater.inflate(R.menu.selection, menu);
			return super.onCreateOptionsMenu(menu);
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// TODO Auto-generated method stub
			
			switch (item.getItemId()) {
	        case R.id.Game_settings:
	        	Intent i =new Intent(SelectionActivity.this,GameSetting.class);
				startActivity(i);
	            return true;
	        
	        default:
	            return super.onOptionsItemSelected(item);
		}

	    
	    
	    
		}
}

package com.example.playnlearn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProfileProgressbar extends Activity {

	private ProgressBar prog;
	private Handler h=new Handler();
	private TextView tv;
	private int stat=0;
	String str=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_progressbar);
		//Bundle bun=new Bundle();
		str=getIntent().getStringExtra("user");
		prog=(ProgressBar) findViewById(R.id.progressBar1);
		tv = (TextView) findViewById(R.id.List_username);
		// bun=getIntent().getExtras(); 
		  new Thread(new Runnable() {
		     @Override
			public void run() {
		        while (stat < 100) 
		        {
		           stat += 5;
		   
		    h.post(new Runnable() 
		    {
		    @Override
			public void run() {
		       prog.setProgress(stat);
		       tv.setText("Loading... \n "+stat+"% ");
		       if(prog.getProgress()==100)
		       {
		    	   Intent i=new Intent(getApplicationContext(),ProfilequestionActivity.class);
		    	   i.putExtra("user", str);
			        startActivity(i);
		       }
		    }
		        });
		        try {
		           // Sleep for 200 milliseconds. 
		                         //Just to display the progress slowly
		           Thread.sleep(200);
		        } catch (InterruptedException e) {
		           e.printStackTrace();
		        }
		        
		     }
		        
		  }
		  }).start();
	}

	
}

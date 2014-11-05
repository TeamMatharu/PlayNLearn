package com.example.playnlearn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Progressbar2Activity extends Activity {

	private ProgressBar prog;
	private Handler h=new Handler();
	private TextView tv;
	private int stat=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_progressbar2);
		
		prog=(ProgressBar) findViewById(R.id.progressBar1);
		tv = (TextView) findViewById(R.id.textView1);
		  // Start long running operation in a background thread
		  new Thread(new Runnable() {
		     @Override
			public void run() {
		        while (stat < 100) 
		        {
		           stat += 5;
		    // Update the progress bar and display the 
		                         //current value in the text view
		    h.post(new Runnable() 
		    {
		    @Override
			public void run() {
		       prog.setProgress(stat);
		       tv.setText("Loading... \n "+stat+"% ");
		       if(prog.getProgress()==100)
		       {
		    	   Intent i=new Intent(getApplicationContext(),ProfilequestionActivity.class);
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

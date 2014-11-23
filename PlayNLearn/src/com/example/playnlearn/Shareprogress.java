package com.example.playnlearn;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class Shareprogress extends Activity {

	   private ProgressDialog progress;
	   @Override
	   protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.activity_user_list);
	      progress = new ProgressDialog(this);
	   }


	   public void open(View view){
	      progress.setMessage("Share Progress :) ");
	      progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	      progress.setIndeterminate(true);
	      progress.show();

	   final int totalProgressTime = 100;

	   final Thread t = new Thread(){

	   @Override
	   public void run(){
	 
	      int jumpTime = 0;
	      while(jumpTime < totalProgressTime){
	         try {
	            sleep(200);
	            jumpTime += 5;
	            progress.setProgress(jumpTime);
	         } catch (InterruptedException e) {
	           e.printStackTrace();
	         }

	      }

	   }
	   };
	   t.start();

	   }
	   @Override
	   public boolean onCreateOptionsMenu(Menu menu) {
	      // Inflate the menu; this adds items to the action bar if it is present.
	      getMenuInflater().inflate(R.menu.user_list, menu);
	      return true;
	   }
	}

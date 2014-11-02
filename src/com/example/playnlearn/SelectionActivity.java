package com.example.playnlearn;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class SelectionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selection);
		ImageButton btnguest,btnprof;
		TextView tv;
		
		btnguest=(ImageButton)findViewById(R.id.imgbtngst);
		btnprof=(ImageButton)findViewById(R.id.imgbtnprof);
		tv=(TextView)findViewById(R.id.txtcrtusr);
		btnguest.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			Intent i=new Intent(SelectionActivity.this,Progressbar2Activity.class);
			startActivity(i);
			}
		});
		btnprof=(ImageButton)findViewById(R.id.imgbtnprof);
		btnprof.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(SelectionActivity.this,ProfilequestionActivity.class);
				startActivity(i);
			}
		});
		tv.setPaintFlags(tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(SelectionActivity.this,NewProfileActivity.class);
				startActivity(i);
			}
		});
	}

}

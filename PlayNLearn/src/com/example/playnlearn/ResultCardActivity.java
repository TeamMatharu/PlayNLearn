package com.example.playnlearn;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.QuickContactBadge;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class ResultCardActivity extends Activity {
    RatingBar resrat;
    TextView tvpro,tvlvl,tvprof,tvscore;
    Intent i;
    int pro,lvl,strrtg,scr;
    float rt;
    Bundle b;
    String usrnm;
    QuickContactBadge q;
    //int p,l,s;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resultpage);
		Bundle get1=getIntent().getExtras();
		q=(QuickContactBadge)findViewById(R.id.imgProfile);
		resrat=(RatingBar)findViewById(R.id.ratingBar1);
		pro=get1.getInt("pbar");
		lvl=get1.getInt("lvl");
		rt=get1.getFloat("star");
		scr=get1.getInt("score");
		
		
		tvpro=(TextView)findViewById(R.id.tvScore);
		tvlvl=(TextView)findViewById(R.id.tvlvl);
		tvprof=(TextView)findViewById(R.id.tvProfileName);
		tvscore=(TextView)findViewById(R.id.tvscr);
		tvprof.setText(get1.getString("usrnm"));
		tvpro.setText("Your Current progress is : "+pro);
		tvlvl.setText("You are on level : "+lvl);
		tvscore.setText("Your Current Score is : "+scr);
		//Toast.makeText(getApplicationContext(), String.valueOf(rt), Toast.LENGTH_SHORT).show();
		resrat.setRating(Float.valueOf(rt));
		
	}
	@Override
	public void onBackPressed()
	{
		/* Intent intent; = new Intent(ResultCardActivity.this, ResultCardActivity.class);
         intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         intent.putExtra("EXIT", true);*/
         openAlertDialoug();
         //startActivity(intent);
	}
	private void openAlertDialoug() {
		AlertDialog.Builder adb=new AlertDialog.Builder(ResultCardActivity.this);
		adb.setTitle("Confirmation!");
		adb.setMessage("Are you Sure want to Quit?");
		adb.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				/*Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);*/
				Intent intent=new Intent(getApplicationContext(),SelectionActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				//intent.putExtra("EXIT", true);
				//java.lang.System.exit(1);
				startActivity(intent);
				
				
			}
		});
		adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				
			}
		});
		AlertDialog adbox=adb.create();
		adbox.show();
		
	}

		
	

	
}

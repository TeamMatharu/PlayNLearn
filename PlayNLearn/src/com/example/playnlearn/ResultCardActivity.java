package com.example.playnlearn;

import com.playnlearn.classes.Setting;
import com.playnlearn.classes.User_DAO;
import com.playnlearn.classes.User_Profile;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class ResultCardActivity extends Activity {
    RatingBar resrat;
    TextView tvpro,tvlvl,tvprof,tvscore;
    Button btnpa;
    Intent i;
    int pro,lvl,strrtg,scr;
    float rt;
    Bundle b;
    String usrnm;
    QuickContactBadge q;
    SharedPreferences sharedPref;
    User_Profile activityUser;
	User_DAO userdao;
    //int p,l,s;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resultpage);
		sharedPref = this.getSharedPreferences(getString(R.string.SharedPref),
				Context.MODE_PRIVATE);
		userdao = new User_DAO(getApplicationContext());
		userdao.open();
		Bundle get1=getIntent().getExtras();
		btnpa=(Button)findViewById(R.id.btnPlayAgain);
		
		activityUser=userdao.getSingleUser(get1.getString("usrnm"));
		Bitmap bmp=BitmapFactory.decodeByteArray(activityUser.getUser_Image(), 0, activityUser.getUser_Image().length);
		q=(QuickContactBadge)findViewById(R.id.imgProfile);
		q.setImageBitmap(bmp);
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
		btnpa.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent(ResultCardActivity.this,ProfilequestionActivity.class);
				i.putExtra("user", tvprof.getText().toString());
				startActivity(i);
				
			}
		});
		
	}
	@Override
	public void onBackPressed()
	{
		//super.onBackPressed();
		if (sharedPref.getBoolean("Vibrationon/off", true)) {
			Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			vibe.vibrate(Setting.VibrationIntensity1);
		}
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
				Intent intent=new Intent(ResultCardActivity.this,SelectionActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

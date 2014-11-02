package com.example.playnlearn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class ResultCardActivity extends Activity {
    RatingBar resrat;
    TextView tvpro,tvlvl;
    Intent i;
    int pro,lvl,strrtg;
    Bundle b;
    //int p,l,s;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resultpage);
		Bundle get1=getIntent().getExtras();
		
		resrat=(RatingBar)findViewById(R.id.ratingBar1);
		pro=get1.getInt("pbar");
		lvl=get1.getInt("lvl");
		strrtg=get1.getInt("star");
		Toast.makeText(getApplicationContext(), strrtg, Toast.LENGTH_SHORT).show();
		
		tvpro=(TextView)findViewById(R.id.tvScore);
		tvlvl=(TextView)findViewById(R.id.tvlvl);
		tvpro.setText("Your Current progress is : "+pro);
		tvlvl.setText("You are on level : "+lvl);
		resrat.setRating(strrtg);
	}

	
}

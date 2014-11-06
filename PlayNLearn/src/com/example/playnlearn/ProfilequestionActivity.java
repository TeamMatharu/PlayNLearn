package com.example.playnlearn;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class ProfilequestionActivity extends Activity {
int lev=1;
	RadioGroup rgans;
	Button btnsub,btnquit;
	ProgressBar progprofile;
	RatingBar rbar1;
	TextView tv;
	String str,st;
	float rtng;
	CountDownTimer t;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profilequestion);
		rgans = (RadioGroup) findViewById(R.id.rgOptions);
		tv=(TextView)findViewById(R.id.tvlvl);
		btnsub = (Button) findViewById(R.id.btnchk);
		btnquit=(Button)findViewById(R.id.btnquit);
		progprofile = (ProgressBar) findViewById(R.id.progressBar2);
		rbar1=(RatingBar)findViewById(R.id.ratingBar1);
		
		str=tv.getText().toString();
		tv.setText(str +lev);
		addListnerOnButton();
		timermethod();
		
		 
		
	}

	public void timermethod() {
		
	t=new CountDownTimer(30000, 1000) {
			 TextView mTextField=(TextView) findViewById(R.id.mTextField);
			 ProgressBar Pb=(ProgressBar) findViewById(R.id.progressBar1);
			
		     @Override
			public void onTick(long millisUntilFinished) {
		         mTextField.setText(""+ millisUntilFinished / 1000);
		         
		         if (mTextField.getText().toString().equals("10")){
		        	 //mTextField.setBackgroundColor(getResources().getColor(R.color.Red));
		        	 mTextField.setTextColor(getResources().getColor(R.color.Red));
		         }
		     }
		     @Override
			public void onFinish() {
		         mTextField.setText("Time up");
		     }
		  }.start();

	}

	public void addListnerOnButton() {
		btnsub.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).cancel();
				String opt = "c";
				String ans = "c";// Write your query to retrive right answer
									// over here.
				int rg = rgans.getCheckedRadioButtonId();
				switch (rg) {
				case R.id.rb1:
					opt = "a";
					break;
				case R.id.rb2:
					opt = "b";
					break;
				case R.id.rb3:
					opt = "c";
					break;
				case R.id.rb4:
					opt = "d";
					break;

				}

				if (opt.equals(ans)) {
					
					LayoutInflater inflater = ProfilequestionActivity.this.getLayoutInflater();
					View layout = inflater.inflate(R.layout.custom_toast,
							(ViewGroup) findViewById(R.id.custom_toast_layout_id));

					// Create Custom Toast
					
					Toast toast = new Toast(getApplicationContext());
					toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
					toast.setDuration(Toast.LENGTH_SHORT);
					toast.setView(layout);
					toast.show();
					
					
					
					int i = progprofile.getProgress();
					if (i <= 90) {
						i = i + 10;
						progprofile.setProgress(i);
					} else {
						progprofile.setProgress(0);
						// level up or rating up
						rtng=rbar1.getRating();
						if(rtng<5)
						{
						rtng=rtng+1;
						rbar1.setRating(rtng);
						}
						else
						{
							rtng=0;
							rbar1.setRating(rtng);
							lev++;
							
							
						}
						tv.setText(str +lev);
					}
					t.cancel();
					timermethod();
					
				}
			}
		});
		btnquit.setOnClickListener(new OnClickListener() {
			
		@Override
		public void onClick(View v) {
			openAlertDialoug(v);
			
		}

		private void openAlertDialoug(View v) {
			AlertDialog.Builder adb=new AlertDialog.Builder(ProfilequestionActivity.this);
			adb.setTitle("Confirmation!");
			adb.setMessage("Are you Sure want to Quit this game?");
			adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					int pro,lvl,strrtg;
					//String st;
					Intent ino=new Intent(ProfilequestionActivity.this,ResultCardActivity.class);
					pro=progprofile.getProgress();
					ino.putExtra("pbar",pro);
					strrtg=(int) rbar1.getRating();
					st=String.valueOf(rbar1.getRating());
					
					ino.putExtra("star",rtng);
					ino.putExtra("lvl", lev);
					
					startActivity(ino);
					
					
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
	});
	   	
}
	@Override
	public void onBackPressed()
	{
		
         openAlertDialoug();
        
	}
	private void openAlertDialoug() {
		AlertDialog.Builder adb=new AlertDialog.Builder(ProfilequestionActivity.this);
		adb.setTitle("Confirmation!");
		adb.setMessage("Are you Sure want to quit the game??\nYou will Loss your Scores?");
		adb.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
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

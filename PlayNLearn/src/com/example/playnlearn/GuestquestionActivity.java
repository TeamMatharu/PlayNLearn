package com.example.playnlearn;

import java.util.Timer;

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
import android.widget.TextView;
import android.widget.Toast;

public class GuestquestionActivity extends Activity {

	TextView tvonq;
	RadioGroup rgst;
	Button btngsub,btngquit;
	ProgressBar gpbar;
	int question=0;
	CountDownTimer t;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guestquestion);
		gpbar=(ProgressBar)findViewById(R.id.progressBar2);
		rgst=(RadioGroup)findViewById(R.id.rgOptions);
		tvonq=(TextView)findViewById(R.id.tvOnQuestion);
		btngsub=(Button)findViewById(R.id.btngchk);
		btngquit=(Button)findViewById(R.id.btngquit);
		tvonq.setText("0/50");
		timermethod();
		addListnerOnButton();
	}
	public void addListnerOnButton() {
		btngsub.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				question+=1;
				/*Write coading for Guest should create profile after 50 question is 
				 * completed.Provide if condition over here. (rachit)
				
				*/
				String opt = "c";
				String ans = "c";// Write your query to retrive right answer
									// over here.
				int rg = rgst.getCheckedRadioButtonId();
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
				if (opt.equals(ans)) 
				{int i=0;
					i=gpbar.getProgress();
					if(i<=98)
					{
						i=i+2;
						gpbar.setProgress(i);
						
						LayoutInflater inflater = GuestquestionActivity.this.getLayoutInflater();
						View layout = inflater.inflate(R.drawable.custom_toast,
								(ViewGroup) findViewById(R.id.custom_toast_layout_id));

						// Create Custom Toast for right answer
						
						Toast toast = new Toast(getApplicationContext());
						toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
						toast.setDuration(Toast.LENGTH_SHORT);
						toast.setView(layout);
						toast.show();
						
					}
					t.cancel();
					timermethod();
					tvonq.setText(String.valueOf(question)+"/50");
				}
				else
				{
					LayoutInflater inflater = GuestquestionActivity.this.getLayoutInflater();
					View layout = inflater.inflate(R.drawable.custom_wrong,
							(ViewGroup) findViewById(R.id.custom_toast_layout_id));
					// Create Custom Toast for wrong answer
					
					Toast toast = new Toast(getApplicationContext());
					toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
					toast.setDuration(Toast.LENGTH_SHORT);
					toast.setView(layout);
					toast.show();
					t.cancel();
					timermethod();
					tvonq.setText(String.valueOf(question)+"/50");
				}
				
				
			}
		});
		
	}
	@Override
	public void onBackPressed()
	{
		
         openAlertDialoug();
        
	}
	private void openAlertDialoug() {
		AlertDialog.Builder adb=new AlertDialog.Builder(GuestquestionActivity.this);
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
	
}

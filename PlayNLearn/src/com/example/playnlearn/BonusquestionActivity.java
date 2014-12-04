package com.example.playnlearn;

import com.playnlearn.classes.Setting;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class BonusquestionActivity extends Activity {
	TextView tvQ;
	RadioGroup rgoption;
	Button btnsub,btnquit;
	ProgressBar gpbar2;
	int question=0;
	CountDownTimer t;
	SharedPreferences sharedPref;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guestquestion);
		sharedPref = this.getSharedPreferences(getString(R.string.SharedPref),
				Context.MODE_PRIVATE);
		gpbar2=(ProgressBar)findViewById(R.id.progressBar2);
		rgoption=(RadioGroup)findViewById(R.id.rgOptions);
		tvQ=(TextView)findViewById(R.id.tvOnQuestion);
		btnsub=(Button)findViewById(R.id.btnchk);
		btnquit=(Button)findViewById(R.id.btnquit);
		tvQ.setText("0/10");
		timermethod();
		addListnerOnButton();
	}
	public void addListnerOnButton() {
		btnsub.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				question+=1;
				/*
				*/
				String opt = "d";
				String ans = "d";
				int rg = rgoption.getCheckedRadioButtonId();
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
					i=gpbar2.getProgress();
					if(i<=99)
					{
						i=i+1;
						gpbar2.setProgress(i);
						
						LayoutInflater inflater =BonusquestionActivity.this .getLayoutInflater();
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
					tvQ.setText(String.valueOf(question)+"/10");
				}
				else
				{
					LayoutInflater inflater = BonusquestionActivity.this.getLayoutInflater();
					View layout = inflater.inflate(R.drawable.custom_wrong,
							(ViewGroup) findViewById(R.id.custom_toast_layout_id));
					// Create Custom Toast for wrong answer
					if (sharedPref.getBoolean("Vibrationon/off", true)) {
						Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
						vibe.vibrate(Setting.VibrationIntensity1);
					}
					Toast toast = new Toast(getApplicationContext());
					toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
					toast.setDuration(Toast.LENGTH_SHORT);
					toast.setView(layout);
					toast.show();
					t.cancel();
					timermethod();
					tvQ.setText(String.valueOf(question)+"/10");
				}
				
				
			}
		});
		
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
	public Resources getResources() {
		return null;
	}
	@Override
	public void onBackPressed() {
		if (sharedPref.getBoolean("Vibrationon/off", true)) {
			Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			vibe.vibrate(200);
		}

	}

	
}
	


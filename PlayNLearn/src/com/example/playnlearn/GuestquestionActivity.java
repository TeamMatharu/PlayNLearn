package com.example.playnlearn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

import com.playnlearn.classes.Question;
import com.playnlearn.classes.Question_DAO;
import com.playnlearn.classes.Setting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class GuestquestionActivity extends Activity {

	TextView tvonq,tvq;
	RadioGroup rgst;
	RadioButton o1,o2,o3,o4;
	Button btngsub,btngquit;
	ProgressBar gpbar;
	String opt,ans;
	int question=0;
	CountDownTimer t;
	Question_DAO qDao;
	Question qclass;
	List<Long> qno=new ArrayList<Long>();
	SharedPreferences sharedPref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guestquestion);
		instantiateView();
		qDao=new Question_DAO(getApplicationContext());
		qDao.open();
		qno.addAll(qDao.getQuestionID());
		qDao.close();
		
		timermethod();
		generateNewQuestion();
		addListnerOnButton();
	}
	public void generateNewQuestion() {
		try{
			qDao.open();
			Random rand=new Random();
			long qid=rand.nextInt(qno.size());
			qclass=qDao.getSingleQuestion(qid);
			tvq.setText(qclass.getQuestion_Text());
			o1.setText(qclass.getOption1());
			o2.setText(qclass.getOption2());
			o3.setText(qclass.getOption3());
			o4.setText(qclass.getOption4());
			ans=qclass.getAnswer();
			if(ans.equalsIgnoreCase("option1"))
			{
				ans="a";
			}else if(ans.equalsIgnoreCase("option2"))
			{
				ans="b";
			}
			else if(ans.equalsIgnoreCase("option3"))
			{
				ans="c";
			}
			else if(ans.equalsIgnoreCase("option4"))
			{
				ans="d";
			}
			qno.remove(qid);
			if(qno.isEmpty())
			{
				qno.addAll(qDao.getQuestionID());
			}
			qDao.close();
			t.cancel();
			timermethod();
			}catch(Exception ee)
			{
				Toast.makeText(getApplicationContext(), ee.toString(), Toast.LENGTH_SHORT).show();
			}
		
	}
	public void addListnerOnButton() {
		btngsub.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				question+=1;
				
				opt = "c";
				//ans = "c";
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
					
				}
				t.cancel();
				timermethod();
				tvonq.setText(String.valueOf(question)+"/50");
				generateNewQuestion();
				
				if(question>=50)
				{
					AlertDialog.Builder adb=new AlertDialog.Builder(GuestquestionActivity.this);
					adb.setTitle("Alert!");
					adb.setMessage("You have Reach the limit to the question for Guest!\nPlease Create Profile now to enjoy Unlimited fun.\nWould you like to connect now?");
					adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							Intent intent=new Intent(getApplicationContext(),NewProfileActivity.class);
							startActivity(intent);
							
							
						}
					});
					adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							Intent intent=new Intent(getApplicationContext(),SelectionActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(intent);
						}
					});
					AlertDialog adbox=adb.create();
					adbox.show();
					
				}
			}
		});
		btngquit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				AlertDialog.Builder adb = new AlertDialog.Builder(
						GuestquestionActivity.this);
				adb.setTitle("Confirmation!");
				adb.setMessage("Are you Sure want to quit the game??\nYou will Loss your Scores?");
				adb.setPositiveButton("Exit", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						Intent intent = new Intent(getApplicationContext(),
								SelectionActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

						startActivity(intent);

					}
				});
				adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
				AlertDialog adbox = adb.create();
				adbox.show();

			}
		});
	}
	@Override
	public void onBackPressed()
	{
		
			if (sharedPref.getBoolean("Vibrationon/off", true)) {
				Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				vibe.vibrate(Setting.VibrationIntensity1);
			}

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
			         t.cancel();
						mTextField.setTextColor(Color.GREEN);
						timermethod();
			       generateNewQuestion();
			     }
			  }.start();

		}
	
	public void instantiateView(){
		sharedPref = this.getSharedPreferences(getString(R.string.SharedPref),
				Context.MODE_PRIVATE);
		gpbar=(ProgressBar)findViewById(R.id.progressBar2);
		rgst=(RadioGroup)findViewById(R.id.rgOptions);
		tvonq=(TextView)findViewById(R.id.tvOnQuestion);
		btngsub=(Button)findViewById(R.id.btngchk);
		btngquit=(Button)findViewById(R.id.btngquit);
		tvq=(TextView)findViewById(R.id.tvQ);
		o1=(RadioButton)findViewById(R.id.rb1);
		o2=(RadioButton)findViewById(R.id.rb2);
		o3=(RadioButton)findViewById(R.id.rb3);
		o4=(RadioButton)findViewById(R.id.rb4);
		tvonq.setText("0/50");
	}
}

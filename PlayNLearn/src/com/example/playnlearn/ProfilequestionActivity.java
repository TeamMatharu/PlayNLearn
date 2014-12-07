package com.example.playnlearn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.playnlearn.classes.Question;
import com.playnlearn.classes.Question_DAO;
import com.playnlearn.classes.Setting;
import com.playnlearn.classes.User_DAO;
import com.playnlearn.classes.User_Profile;

public class ProfilequestionActivity extends Activity {
	int lev = 1, que, id, score, lvlStatus = 0;
	RadioGroup rgans;
	Button btnsub, btnquit;
	ProgressBar progprofile;
	RatingBar rbar1;
	TextView tv, tv1, tv2,tvq;
	RadioButton o1,o2,o3,o4;
	String str, st,opt,ans;
	float rtng;
	CountDownTimer t;
	User_Profile activityUser;
	User_DAO userdao;
	MediaPlayer mp1, mp2;
	SharedPreferences sharedPref;
	Question_DAO qDao;
	Question qclass;
	List<Long> qno=new ArrayList<Long>();
	Toast toast ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profilequestion);
		toastIntansiation();
		sharedPref = this.getSharedPreferences(getString(R.string.SharedPref),
				Context.MODE_PRIVATE);
		userdao = new User_DAO(getApplicationContext());
		userdao.open();
		Bundle b = new Bundle();
		b = getIntent().getExtras();
		Log.i("my2", "" + b.getString("user"));
		Toast.makeText(getApplicationContext(), "" + b.getString("user"),
				Toast.LENGTH_SHORT).show();

		activityUser = userdao.getSingleUser(b.getString("user"));
		score = Integer.valueOf(activityUser.getUser_score());
		que = Integer.valueOf(activityUser.getUser_qno());
		id = activityUser.getUser_ID();
		rgans = (RadioGroup) findViewById(R.id.rgOptions);
		tv = (TextView) findViewById(R.id.tvlvl);
		tv1 = (TextView) findViewById(R.id.tvProfileName);
		tv2 = (TextView) findViewById(R.id.tvOnQuestion);
		tvq=(TextView)findViewById(R.id.tvQ);
		o1=(RadioButton)findViewById(R.id.rb1);
		o2=(RadioButton)findViewById(R.id.rb2);
		o3=(RadioButton)findViewById(R.id.rb3);
		o4=(RadioButton)findViewById(R.id.rb4);
		btnsub = (Button) findViewById(R.id.btnchk);
		btnquit = (Button) findViewById(R.id.btnquit);
		progprofile = (ProgressBar) findViewById(R.id.progressBar2);
		rbar1 = (RatingBar) findViewById(R.id.ratingBar1);
		tv1.setText(b.getCharSequence("user"));

		progprofile
				.setProgress(Integer.valueOf(activityUser.getUser_Progress()));
		rbar1.setRating(Float.valueOf(activityUser.getUser_Star()));
		//userdao.close();
		que += 1;
		qDao=new Question_DAO(getApplicationContext());
		qDao.open();
		qno.addAll(qDao.getQuestionID());
		qDao.close();
		lev = Integer.valueOf(activityUser.getUser_Level());
		tv2.setText(String.valueOf(que) + "/∞");
		str = tv.getText().toString();
		tv.setText(str + lev);
		addListnerOnButton();
		timermethod();
		generateNewQuestion();

	}

	public void timermethod() {

		t = new CountDownTimer(30000, 1000) {
			TextView mTextField = (TextView) findViewById(R.id.mTextField);
			ProgressBar Pb = (ProgressBar) findViewById(R.id.progressBar1);

			@Override
			public void onTick(long millisUntilFinished) {
				mTextField.setText("" + millisUntilFinished / 1000);

				if (mTextField.getText().toString().equals("10")) {
					// mTextField.setBackgroundColor(getResources().getColor(R.color.Red));
					mTextField.setTextColor(getResources()
							.getColor(R.color.Red));
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

	public void addListnerOnButton() {
		btnsub.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				opt = "c";
				
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
					que++;
					tv2.setTextColor(0xff00ff00);
					score++;

					int i = progprofile.getProgress();
					if (i <= 90) {
						i = i + 10;
						progprofile.setProgress(i);
					} else {
						progprofile.setProgress(0);
						// level up or rating up
						rtng = rbar1.getRating();
						if (rtng < 5) {
							rtng = rtng + 1;
							rbar1.setRating(rtng);
						} else {
							rtng = 0;
							rbar1.setRating(rtng);
							if (sharedPref.getBoolean("Vibrationon/off", true)) {
								Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
								vibe.vibrate(Setting.VibrationIntensity1);
							}
							lev++;
							lvlStatus = 1;
							score = score + 10;

						}
						tv.setText(str + lev);
					}
					
					if (lvlStatus == 0) {
						LayoutInflater inflater = ProfilequestionActivity.this
								.getLayoutInflater();
						View layout = inflater
								.inflate(
										R.drawable.custom_toast,
										(ViewGroup) findViewById(R.id.custom_toast_layout_id));
						
						toast.setView(layout);
						toast.show();
						
						
					} else if (lvlStatus == 1) {
						LayoutInflater inflater = ProfilequestionActivity.this
								.getLayoutInflater();
						View layout = inflater
								.inflate(
										R.drawable.custom_toast_levelup,
										(ViewGroup) findViewById(R.id.custom_toast_layout_id));
						
						toast.setView(layout);
						toast.show();
					}
					lvlStatus = 0;
				} else {

					LayoutInflater inflater = ProfilequestionActivity.this
							.getLayoutInflater();
					View layout = inflater
							.inflate(
									R.drawable.custom_wrong,
									(ViewGroup) findViewById(R.id.custom_toast_layout_id));
					// Create Custom Toast for wrong answer
					Log.i("vibrate",
							"" + sharedPref.getBoolean("Vibrationon/off", true));
					if (sharedPref.getBoolean("Vibrationon/off", true)) {
						Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
						vibe.vibrate(Setting.VibrationIntensity1);
					}

					
					toast.setView(layout);
					toast.show();
					que++;
					tv2.setTextColor(getResources().getColor(R.color.Red));
					
				}
				generateNewQuestion();
				t.cancel();
				timermethod();
				tv2.setText(String.valueOf(que) + "/∞");
			}

			
		});

		btnquit.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				if (sharedPref.getBoolean("Vibrationon/off", true)) {
					Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
					vibe.vibrate(Setting.VibrationIntensity1);
				}
				openAlertDialoug(v);

			}

			private void openAlertDialoug(View v) {
				AlertDialog.Builder adb = new AlertDialog.Builder(
						ProfilequestionActivity.this);
				adb.setTitle("Confirmation!");
				adb.setMessage("Are you Sure want to Quit this game?");
				adb.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								int pro;
								long i = userdao.updUser(id,
										progprofile.getProgress(),
										rbar1.getRating(), lev, score, que);
								Intent ino = new Intent(
										ProfilequestionActivity.this,
										ResultCardActivity.class);
								pro = progprofile.getProgress();
								ino.putExtra("pbar", pro);
								rtng = rbar1.getRating();
								ino.putExtra("usrnm", tv1.getText());
								ino.putExtra("star", rtng);
								ino.putExtra("lvl", lev);
								ino.putExtra("score", score);
								startActivity(ino);

							}
						});
				adb.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								/*
								 * Intent ino=new
								 * Intent(getApplicationContext(),
								 * ProfilequestionActivity.class);
								 * startActivity(ino);
								 */

							}
						});
				AlertDialog adbox = adb.create();
				adbox.show();

			}

		});

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
	@Override
	public void onBackPressed() {
		if (sharedPref.getBoolean("Vibrationon/off", true)) {
			Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			vibe.vibrate(200);
		}
		openAlertDialoug();

	}

	private void openAlertDialoug() {
		AlertDialog.Builder adb = new AlertDialog.Builder(
				ProfilequestionActivity.this);
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
	
	public boolean isRunning(Context ctx) {
	
		
        ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (RunningTaskInfo task : tasks) {
            if (ctx.getPackageName().equalsIgnoreCase(task.baseActivity.getPackageName())) 
                return true;                                  
        }

        return false;
	}
	
	public void toastIntansiation() {
		toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		toast.cancel();
		super.onStop();
	}
	
	
}

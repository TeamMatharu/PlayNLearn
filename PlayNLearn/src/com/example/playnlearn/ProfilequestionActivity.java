package com.example.playnlearn;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

public class ProfilequestionActivity extends Activity {

	RadioGroup rgans;
	Button btnsub;
	ProgressBar progprofile;
	RatingBar rbar1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profilequestion);
		rgans = (RadioGroup) findViewById(R.id.rgOptions);
		btnsub = (Button) findViewById(R.id.btnchk);
		progprofile = (ProgressBar) findViewById(R.id.progressBar1);
		rbar1=(RatingBar)findViewById(R.id.ratingBar1);
		addListnerOnButton();
	}

	public void addListnerOnButton() {
		btnsub.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
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
					int i = progprofile.getProgress();
					if (i <= 90) {
						i = i + 10;
						progprofile.setProgress(i);
					} else {
						progprofile.setProgress(0);
						// level up or rating up
						float rtng=rbar1.getRating();
						if(rtng<5)
						{
						rtng=rtng+1;
						rbar1.setRating(rtng);
						}
						else
						{
							rtng=0;
							rbar1.setRating(rtng);
							Toast.makeText(getApplicationContext(), "Level Up", Toast.LENGTH_SHORT).show();
						}
					}
				}
			}
		});

	}
}

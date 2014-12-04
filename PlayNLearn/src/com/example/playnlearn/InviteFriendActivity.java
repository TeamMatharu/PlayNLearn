/**
 * 
 */
package com.example.playnlearn;

import com.playnlearn.classes.Setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InviteFriendActivity extends Activity {

	Button btninvite;
	EditText id, msg;
	SharedPreferences sharedPref ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invite_friend);
		sharedPref = this.getSharedPreferences(getString(R.string.SharedPref),
				Context.MODE_PRIVATE);
		btninvite = (Button) findViewById(R.id.btninv);
		id = (EditText) findViewById(R.id.etInviteName);
		msg = (EditText) findViewById(R.id.etMsg);
		setonclickListner();

	}

	public void setonclickListner() {
		btninvite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sendEmail();
				// after sending the email, clear the fields
				/*
				 * Intent i=new
				 * Intent(InviteFriendActivity.this,UserListActivity.class);
				 * startActivity(i);
				 */

			}

			public void sendEmail() {
				String[] recipients = { id.getText().toString() };
				Intent email = new Intent(Intent.ACTION_SEND, Uri
						.parse("mailto:"));
				// prompts email clients only
				email.setType("message/rfc822");

				email.putExtra(Intent.EXTRA_EMAIL, recipients);
				email.putExtra(Intent.EXTRA_SUBJECT,
						"Invitation From playNlearn");
				email.putExtra(Intent.EXTRA_TEXT, msg.getText().toString());

				try {
					// the user can choose the email client
					startActivity(Intent.createChooser(email,
							"Choose an email client from..."));

				} catch (android.content.ActivityNotFoundException ex) {
					Toast.makeText(InviteFriendActivity.this,
							"No email client installed.", Toast.LENGTH_LONG)
							.show();
				}

			}

		});

		
		
	}

	@Override
	public void onBackPressed() {
		if (sharedPref.getBoolean("Vibrationon/off", true)) {
			Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			vibe.vibrate(Setting.VibrationIntensity1);
		}
		super.onBackPressed();
	}
	
}

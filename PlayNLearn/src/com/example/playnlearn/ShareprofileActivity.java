package com.example.playnlearn;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ShareprofileActivity extends Activity {

	Button btnshare;
	EditText Id, Msg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);
		 btnshare = (Button) findViewById(R.id.btnshr);
		Id = (EditText) findViewById(R.id.etShareName);
		Msg = (EditText) findViewById(R.id.etMsg2);
		setonclickListner();

	}

	public void setonclickListner() {
		btnshare.setOnClickListener(new OnClickListener() {
         	@Override
			public void onClick(View v) {
				sendEmail();
				// after sending the email, clear the fields
				/*
				 * Intent i=new
				 * Intent(ShareprofileActivity.this,UserListActivity.class);
				 * startActivity(i);
				 */

			}

			public void sendEmail() {
				String[] recipients = { Id.getText().toString() };
				Intent email = new Intent(Intent.ACTION_SEND, Uri
						.parse("mailto:"));
				// prompts email clients only
				email.setType("message/rfc822");
				
				email.putExtra(Intent.EXTRA_EMAIL, recipients);
				email.putExtra(Intent.EXTRA_SUBJECT,
						"Share From playNlearn");
				email.putExtra(Intent.EXTRA_TEXT, Msg.getText().toString());
			

				try {
					// the user can choose the email client
					startActivity(Intent.createChooser(email,
							"Choose an email client from..."));

				} catch (android.content.ActivityNotFoundException ex) {
					Toast.makeText(ShareprofileActivity.this,
							"No email client installed.", Toast.LENGTH_LONG)
							.show();
				}

			}

		});

	}

}

package com.example.playnlearn;

import com.playnlearn.classes.User_DAO;
import com.playnlearn.classes.User_Profile;

import android.app.Activity;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateUsrDetail extends Activity {
	User_Profile activityUser;
	User_DAO userdao; 
	Button btnsave;
	EditText et1, et2, etname;
	int id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);
		
		userdao =new User_DAO(getApplicationContext());
		userdao.open();
			Bundle b=new Bundle();
		 b = getIntent().getExtras();
		Log.i("my2",""+b.getString("user") );
		Toast.makeText(getApplicationContext(),""+b.getString("user") , Toast.LENGTH_SHORT).show();
		
		activityUser=userdao.getSingleUser(b.getString("user"));
		id=activityUser.getUser_ID();
		et1=(EditText)findViewById(R.id.etosts);
		et2=(EditText)findViewById(R.id.etnsts);
		etname=(EditText)findViewById(R.id.etname);
		btnsave=(Button)findViewById(R.id.btnSave);
		etname.setText(b.getCharSequence("user"));
		et1.setText(activityUser.getUser_Title().toString());
		et2.setFocusable(true);
		btnsave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(et2.getText().toString().equals(""))
				{
					Toast.makeText(getApplicationContext(), "Please Update New Status and then try again!", Toast.LENGTH_SHORT).show();
				}
				else
				{
					long j=userdao.updstatus(id, et2.getText().toString());
					if(j>0)
					{
						Toast.makeText(getApplicationContext(), "Status Updated Successfully!", Toast.LENGTH_SHORT).show();
					}
				}
				
			}
		});
		
	}

	
	
}

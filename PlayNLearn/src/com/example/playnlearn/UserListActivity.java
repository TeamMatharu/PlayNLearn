package com.example.playnlearn;

import java.util.ArrayList;
import java.util.List;

import com.playnlearn.classes.User_DAO;
import com.playnlearn.classes.User_Profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class UserListActivity extends Activity {

	ListView list;
	TextView tv1, tv2;
	Bitmap b;
	ImageView img;
	User_DAO userdao;
	User_Profile userprofile;
	List<User_Profile> l;
	Button btn,shr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_list);
		btn=(Button)findViewById(R.id.btninv);
		shr=(Button)findViewById(R.id.btnshr);
			btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent(getApplicationContext(),InviteFriendActivity.class);
				startActivity(i);
				
			}
		});
			shr.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent i=new Intent(getApplicationContext(),ShareprofileActivity.class);
					startActivity(i);
					
				}
			} );

		ArrayList<String> user = new ArrayList<String>();
		ArrayList<String> other = new ArrayList<String>();
		ArrayList<byte []> image=new ArrayList<byte []>();
		userdao=new User_DAO(getApplicationContext());
		userdao.open();
		l=userdao.getAllUser();
		for(int i=0;i<l.size();i++){
			userprofile=l.get(i);
			Log.i("Before",userprofile.getUser_Name() );
			user.add(userprofile.getUser_Name());
			other.add(userprofile.getUser_Title());
			image.add(userprofile.getUser_Image());
			Log.i("After",userprofile.getUser_Name() );
		}	
		Integer imageId = R.drawable.profile;
		list adapter = new list(UserListActivity.this, user,other ,imageId,image);
		list = (ListView) findViewById(R.id.listView1);
		list.setLongClickable(true);
		list.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		userdao.close();
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				TextView textView = (TextView) parent.findViewById(R.id.List_username);
				Intent i=new Intent(UserListActivity.this,ProfilequestionActivity.class);
				
				int itemPosition     = position;
	            String  itemValue    = (String) list.getItemAtPosition(position);
				Log.i("mymy",textView.getText().toString() );
				i.putExtra("user", itemValue);
				startActivity(i);
				
			}
		} );
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                    int pos, long id) {
                // TODO Auto-generated method stub
            	TextView textView = (TextView)findViewById(R.id.List_username);
				Intent i=new Intent(UserListActivity.this,UpdateUsrDetail.class);
				
				int itemPosition     = pos;
	            String  itemValue    = (String) list.getItemAtPosition(pos);
				Log.i("mymy",textView.getText().toString() );
				i.putExtra("user", itemValue);
				startActivity(i);
                Log.v("long clicked","pos: " + pos);

                return true;
            }
        });
		
	}

}

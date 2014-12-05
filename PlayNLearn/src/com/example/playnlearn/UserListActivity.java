package com.example.playnlearn;

import java.util.ArrayList;
import java.util.List;

import com.playnlearn.classes.Setting;
import com.playnlearn.classes.User_DAO;
import com.playnlearn.classes.User_Profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Vibrator;
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
import android.widget.Toast;

public class UserListActivity extends Activity {
	SharedPreferences sharedPref ;
	ListView list;
	TextView tv1, tv2;
	Bitmap b;
	ImageView img;
	User_DAO userdao;
	User_Profile userprofile;
	List<User_Profile> l;
	Button btn, shr;
	Intent i;
	long id1;
	Bundle bund;
	String itemValue;
	list adapter;
	ArrayList<String> user;
	ArrayList<String> other;
	ArrayList<byte[]> image;
	
	private TextView tvEmptyList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_list);
		sharedPref = this.getSharedPreferences(getString(R.string.SharedPref),
				Context.MODE_PRIVATE);
		btn = (Button) findViewById(R.id.btninv);
		shr = (Button) findViewById(R.id.btnshr);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						InviteFriendActivity.class);
				startActivity(i);

			}
		});
		shr.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						ShareprofileActivity.class);
				startActivity(i);

			}
		});

		user = new ArrayList<String>();
		other = new ArrayList<String>();
		image = new ArrayList<byte[]>();
		userdao = new User_DAO(getApplicationContext());
		userdao.open();
		l = userdao.getAllUser();
		for (int i = 0; i < l.size(); i++) {
			userprofile = l.get(i);
			Log.i("Before", userprofile.getUser_Name());
			user.add(userprofile.getUser_Name());
			other.add(userprofile.getUser_Title());
			image.add(userprofile.getUser_Image());
			Log.i("After", userprofile.getUser_Name());
		}
		
		tvEmptyList = (TextView) findViewById(R.id.tvEmptyList);
		
		Integer imageId = R.drawable.profile;
		adapter = new list(UserListActivity.this, user, other, imageId, image);
		list = (ListView) findViewById(R.id.listView1);
		list.setLongClickable(true);
		list.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		userdao.close();
		
		ShowHideList();
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				TextView textView = (TextView) parent
						.findViewById(R.id.List_username);
				Intent itm = new Intent(UserListActivity.this,
						ProfileProgressbar.class);
				itemValue = (String) list.getItemAtPosition(position);
				Log.i("mymy", textView.getText().toString());
				itm.putExtra("user", itemValue);
				startActivity(itm);

			}
		});

		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int pos, long id) {
				TextView textView = (TextView) findViewById(R.id.List_username);
				i = new Intent(UserListActivity.this, UpdateUsrDetail.class);
				userdao.open();
				int itemPosition = pos;

				itemValue = (String) list.getItemAtPosition(pos);
				Log.i("mymy", textView.getText().toString());
				i.putExtra("user", itemValue);
				userprofile = userdao.getSingleUser(itemValue);
				id1 = userprofile.getUser_ID();
				Log.v("long clicked", "pos: " + pos);

				openConfirmation();

				return true;
			}

			public void openConfirmation() {
				AlertDialog.Builder adb = new AlertDialog.Builder(
						UserListActivity.this);
				adb.setTitle("Attention!");
				adb.setMessage("What you want to do?");
				adb.setPositiveButton("Delete Profile",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								
								  AlertDialog.Builder subDialog = new
								  AlertDialog.Builder(UserListActivity.this);
								  subDialog.setTitle(
								  "Sub dialog").setMessage("Are you sure you want to Delete User?\nAll data will be lost!");
								  subDialog.setPositiveButton("OK",
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(DialogInterface dialog,
														int which) {
													
													
													userdao.open();
													int j = userdao.deleteUser(id1);
													if (j > 0) {
														l = userdao.getAllUser();
														
														user.clear();
														
														for (int i = 0; i < l.size(); i++) {
															userprofile = l.get(i);
															Log.i("Before",
																	userprofile.getUser_Name());
															user.add(userprofile.getUser_Name());
															other.add(userprofile.getUser_Title());
															image.add(userprofile.getUser_Image());
															Log.i("After",
																	userprofile.getUser_Name());
														}
														Integer imageId = R.drawable.profile;

														adapter = new list(UserListActivity.this,
																user, other, imageId, image);
														list = (ListView) findViewById(R.id.listView1);
														list.setLongClickable(true);
														list.setAdapter(adapter);
														
														ShowHideList();
													} else {
														Toast.makeText(UserListActivity.this,
																"Unable to delete user.",
																Toast.LENGTH_SHORT).show();
													}
													

												}
											});
								  subDialog.setNegativeButton("Cancel",
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(DialogInterface dialog,
														int which) {
													

												}
											});
								  subDialog.setCancelable(true);
								  
								  subDialog.show();
								 
							}
						});

				adb.setNegativeButton("Update Profile",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								startActivity(i);

							}
						});
				AlertDialog adbox = adb.create();
				adbox.show();

			}

		});

	}

	private void ShowHideList() {
		if(user.size()<=0){
			tvEmptyList.setVisibility(View.VISIBLE);
			list.setVisibility(View.INVISIBLE);
		}else{
			tvEmptyList.setVisibility(View.INVISIBLE);
			list.setVisibility(View.VISIBLE);
		}
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

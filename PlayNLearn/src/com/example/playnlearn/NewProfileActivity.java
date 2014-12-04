package com.example.playnlearn;

import java.io.ByteArrayOutputStream;
import java.util.Random;

import com.playnlearn.classes.Setting;
import com.playnlearn.classes.User_DAO;
import com.playnlearn.classes.User_Profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NewProfileActivity extends Activity {
	SharedPreferences sharedPref ;
	ImageButton user_Image;
	private static int RESULT_LOAD_IMAGE = 1;
	User_Profile user=new User_Profile();
	//ImageView iv;
	DatePicker dp;
	Button btn_submit;
	EditText et1,et2,et3;
	TextView tvdob;
	Bitmap userimage;
	Random r=new Random();
	ImageView iv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_profile);
		sharedPref = this.getSharedPreferences(getString(R.string.SharedPref),
				Context.MODE_PRIVATE);
		user_Image=(ImageButton) findViewById(R.id.UserImage);
		
		 iv=(ImageView) findViewById(R.id.imageView1);
		dp=(DatePicker) findViewById(R.id.dpResult);
		btn_submit=(Button) findViewById(R.id.btnSave);
		et1=(EditText) findViewById(R.id.editText1);
		et2=(EditText) findViewById(R.id.editText2);
		et3=(EditText) findViewById(R.id.editText3);
		
		tvdob=(TextView) findViewById(R.id.tvdob);
		dp.setVisibility(View.GONE);
		
		et3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		      @Override
		      public void onFocusChange(View v, boolean hasFocus) {
		    	  dp.setVisibility(View.VISIBLE);
		    	  et3.setVisibility(View.GONE);
		      }
		    });
		
		
		
		
		user_Image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent i = new Intent(
	                        Intent.ACTION_PICK,
	                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

	                startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});
		btn_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try{
					//user_Image.setDrawingCacheEnabled(true);
					//user_Image.buildDrawingCache();
					//userimage=user_Image.getDrawingCache();
				if(userimage.getByteCount()!=0){
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				Boolean b=userimage.compress(Bitmap.CompressFormat.PNG, 100, stream);
				
				Log.i("tag", b.toString());
				byte[] byteArray = stream.toByteArray();
					user.setUser_Image(byteArray);
				}
				

				String name=et1.getText().toString();
				String date=""+dp.getYear()+"-"+dp.getMonth()+"-"+dp.getDayOfMonth();
				String e_mail=et2.getText().toString();
				user.setUser_ID(r.nextInt());
				user.setUser_Name(name);
				user.setComment(date);
				user.setUser_Email(e_mail);
				user.setUser_Level("0");
				user.setUser_Progress("0");
				user.setUser_Star("0");
				user.setUser_Title("Beginner");
				user.setUser_score("0");
				user.setUser_qno("0");
				
				
				User_DAO udao=new User_DAO(getApplicationContext());
				udao.open();
				udao.createUser(user);
				udao.close();
				Intent i=new Intent(NewProfileActivity.this,SelectionActivity.class);
				startActivity(i);
				
				}catch(Exception e){
					Toast.makeText(NewProfileActivity.this, "Please Select Image", Toast.LENGTH_SHORT).show();
				}
				
				
				
				
			
				
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            Log.i("uri", ""+selectedImage.toString());
          //  Uri selectimage=Uri.parse(selectedImage .getEncodedPath());
            
            String[] filePathColumn = { MediaColumns.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            Log.i("String", picturePath);
            cursor.close();
            Bitmap b=BitmapFactory.decodeFile(picturePath);
            Bitmap resized = Bitmap.createScaledBitmap(b, 150, 150, true);
            userimage=resized;
            //iv.setImageBitmap(resized);
            user_Image.setImageBitmap(resized);
		}
		
	}

    public void instantiateView(){
    	
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

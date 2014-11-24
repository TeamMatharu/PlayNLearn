package com.playnlearn.classes;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.playnlearn.db_utilities.MySQLiteHelper;

public class User_DAO {

	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.User_ID,
			MySQLiteHelper.User_Name, MySQLiteHelper.User_Progress,
			MySQLiteHelper.User_Image, MySQLiteHelper.User_Star,
			MySQLiteHelper.User_Level, MySQLiteHelper.User_Email,
			MySQLiteHelper.User_Title, MySQLiteHelper.User_score,
			MySQLiteHelper.User_qno ,MySQLiteHelper.COMMENT};

	public User_DAO(Context c) {
		dbHelper = new MySQLiteHelper(c);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public long createUser(User_Profile user) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.User_ID, user.getUser_ID());
		values.put(MySQLiteHelper.User_Name, user.getUser_Name());
		values.put(MySQLiteHelper.User_Progress, user.getUser_Progress());
		values.put(MySQLiteHelper.User_Image, user.getUser_Image());
		values.put(MySQLiteHelper.User_Star, user.getUser_Star());
		values.put(MySQLiteHelper.User_Level, user.getUser_Level());
		values.put(MySQLiteHelper.User_Email, user.getUser_Email());
		values.put(MySQLiteHelper.User_Title, user.getUser_Title());
		values.put(MySQLiteHelper.User_score, user.getUser_score());
		values.put(MySQLiteHelper.User_qno, user.getUser_qno());
		values.put(MySQLiteHelper.COMMENT, user.getComment());
		

		long insertId = database
				.insert(MySQLiteHelper.TABLE_User, null, values);
		return insertId;

	}

	public int deleteUser(int User_Id) {
		Log.i("Deleted", "User deleted with id: " + User_Id);
		int i=database.delete(MySQLiteHelper.TABLE_User, MySQLiteHelper.User_ID
				+ " = " + User_Id, null);
		return i;
	}

	public List<User_Profile> getAllUser() {
		List<User_Profile> UserList = new ArrayList<User_Profile>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_User, allColumns,
				null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			User_Profile user = getUser(cursor);
			UserList.add(user);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return UserList;
	}

	public User_Profile getSingleUser(String name) {

		User_Profile user = new User_Profile();
		// Log.i("name", name);

		Cursor cursor = database.rawQuery(
				"select * from User_Profile where User_Name='" + name + "'",
				null);

		// database.query(MySQLiteHelper.TABLE_User,allColumns,
		// MySQLiteHelper.User_Name+"="+name ,null , null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			user = getUser(cursor);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return user;
	}

	private User_Profile getUser(Cursor cursor) {
		User_Profile user = new User_Profile();
		user.setUser_ID(cursor.getInt(0));
		user.setUser_Name(cursor.getString(1));
		user.setUser_Progress(cursor.getString(2));
		user.setUser_Image(cursor.getBlob(3));
		user.setUser_Star(cursor.getString(4));
		user.setUser_Level(cursor.getString(5));
		user.setUser_Email(cursor.getString(6));
		user.setUser_Title(cursor.getString(7));
		user.setUser_score(cursor.getString(8));
		user.setUser_qno(cursor.getString(9));
		user.setComment(cursor.getString(10));
		
		return user;
	}

	public long updUser(int id,int prog,float rating,int lvl,int score,int que)
	{
		User_Profile user=new User_Profile();
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.User_Progress, String.valueOf(prog));
		values.put(MySQLiteHelper.User_Star, String.valueOf(rating));
		values.put(MySQLiteHelper.User_Level, String.valueOf(lvl));
		values.put(MySQLiteHelper.User_score, String.valueOf(score));
		values.put(MySQLiteHelper.User_qno, String.valueOf(que));
		long insertId = database.update(MySQLiteHelper.TABLE_User, values, MySQLiteHelper.User_ID+"=="+id, null);
				
	return insertId;
	}
	
	public long updstatus(int id, String cmt)
	{
		User_Profile user=new User_Profile();
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.User_Title, String.valueOf(cmt));
		long insertId = database.update(MySQLiteHelper.TABLE_User, values, MySQLiteHelper.User_ID+"=="+id, null);
		return insertId;
	}
	
	
	
	
	
}

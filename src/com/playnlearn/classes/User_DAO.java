package com.playnlearn.classes;



import java.util.ArrayList;
import java.util.List;

import com.playnlearn.db_utilities.MySQLiteHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class User_DAO {

	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.User_ID,
			MySQLiteHelper.User_Name, MySQLiteHelper.User_Progress,
			MySQLiteHelper.User_Image, MySQLiteHelper.User_Star,
			MySQLiteHelper.User_Level, MySQLiteHelper.User_Email,
			MySQLiteHelper.User_Title, MySQLiteHelper.COMMENT };

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
		values.put(MySQLiteHelper.COMMENT, user.getComment());

		long insertId = database
				.insert(MySQLiteHelper.TABLE_User, null, values);
		return insertId;

	}

	public void deleteUser(int User_Id) {
		Log.i("Deleted", "User deleted with id: " + User_Id);
		database.delete(MySQLiteHelper.TABLE_User, MySQLiteHelper.User_ID
				+ " = " + User_Id, null);
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

	private User_Profile getUser(Cursor cursor) {
		User_Profile user = new User_Profile();
		user.setUser_ID(cursor.getInt(0));
		user.setUser_Name(cursor.getString(1));
		user.setUser_Progress(cursor.getString(2));
		user.setUser_Image(cursor.getString(3));
		user.setUser_Star(cursor.getString(4));
		user.setUser_Level(cursor.getString(5));
		user.setUser_Email(cursor.getString(6));
		user.setUser_Title(cursor.getString(7));
		user.setComment(cursor.getString(8));
		return user;
	}

}

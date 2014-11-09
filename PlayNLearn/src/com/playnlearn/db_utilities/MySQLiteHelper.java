package com.playnlearn.db_utilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_User = "User_Profile";
	public static final String User_ID = "User_Id";
	public static final String User_Name = "User_Name";
	public static final String User_Progress = "User_Progress";
	public static final String User_Image = "User_Image";
	public static final String User_Star = "User_Star";
	public static final String User_Level = "User_Level";
	public static final String User_Email = "User_Email";
	public static final String User_Title = "User_Title";
	public static final String COMMENT = "comment";

	private static final String DATABASE_NAME = "PlayNLearn.db";
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "create table " + TABLE_User
			+ "(" + User_ID + " integer primary key autoincrement, "
			+ User_Name + " text , " + User_Progress
			+ " text ," + User_Image + " blob not null ," + User_Star
			+ " text ," + User_Level + " text ," + User_Email
			+ " text ," + User_Title + " text ," + COMMENT
			+ " text );";

	public MySQLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_User);
		onCreate(db);
	}

}

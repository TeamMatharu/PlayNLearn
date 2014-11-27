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
	public static final String User_score = "User_score";
	public static final String User_qno = "User_qno";
	public static final String COMMENT = "comment";
	
	
	
	public static final String TABLE_Question = "Question";
	public static final String Question_ID = "Question_ID";
	public static final String Question_Text = "Question_Text";
	public static final String option1 = "option1";
	public static final String option2 = "option2";
	public static final String option3 = "option3";
	public static final String option4 = "option4";
	public static final String Answer= "Answer";
	public static final String Question_Comment = "Question_Comment";

	private static final String DATABASE_NAME = "PlayNLearn.db";
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "create table " + TABLE_User
			+ "(" + User_ID + " integer primary key autoincrement, "
			+ User_Name + " text , " + User_Progress + " text ," + User_Image
			+ " blob not null ," + User_Star + " text ," + User_Level
			+ " text ," + User_Email + " text ," + User_Title + " text ,"+ User_score + " text ,"
			+ User_qno + " text," + COMMENT + " text );";
	
	private static final String DATABASE_CREATE2 = "create table " + TABLE_Question
			+ "(" + Question_ID + " integer primary key autoincrement, "
			+ Question_Text + " text , " + option1 + " text ,"
			+ option2 + " text ," + option3
			+ " text ," + option4 + " text ," 
			+ Answer + " text ,"
			+ Question_Comment + " text );";

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
		db.execSQL(DATABASE_CREATE2);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_User);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Question);
		onCreate(db);
	}

}

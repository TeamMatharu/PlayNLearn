package com.playnlearn.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.playnlearn.db_utilities.MySQLiteHelper;

public class Question_DAO {
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.Question_ID,
			MySQLiteHelper.Question_Text, MySQLiteHelper.option1,
			MySQLiteHelper.option2, MySQLiteHelper.option3,
			MySQLiteHelper.option4, MySQLiteHelper.Question_Comment,
			};
	
	public Question_DAO(Context c) {
		dbHelper = new MySQLiteHelper(c);
	}
	
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public long AddQuestion(Question q) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.Question_ID, q.getQuestion_ID());
		values.put(MySQLiteHelper.Question_Text, q.getQuestion_Text());
		values.put(MySQLiteHelper.option1, q.getOption1());
		values.put(MySQLiteHelper.option2, q.getOption2());
		values.put(MySQLiteHelper.option3,q.getOption3());
		values.put(MySQLiteHelper.option4, q.getOption4());
		values.put(MySQLiteHelper.Answer, q.getAnswer());
		values.put(MySQLiteHelper.Question_Comment, q.getQuestion_Comment());

		long insertId = database
				.insert(MySQLiteHelper.TABLE_Question, null, values);
		return insertId;

	}
	
	
	public Question getSingleQuestion(int Questionid) {

		Question question = new Question();
		// Log.i("name", name);

		Cursor cursor = database.rawQuery(
				"select * from Question where Question_ID='" + Questionid + "'",
				null);

		// database.query(MySQLiteHelper.TABLE_User,allColumns,
		// MySQLiteHelper.User_Name+"="+name ,null , null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			question = getQuestion(cursor);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return question;
	}

	private Question getQuestion(Cursor cursor) {
		Question question = new Question();
		question.setQuestion_ID(cursor.getInt(0));
		question.setQuestion_Text(cursor.getString(1));
		question.setOption1(cursor.getString(2));
		question.setOption2(cursor.getString(3));
		question.setOption3(cursor.getString(4));
		question.setOption4(cursor.getString(5));
		question.setAnswer(cursor.getString(6));
		question.setQuestion_Comment(cursor.getString(7));
		return question;
	}
	
}

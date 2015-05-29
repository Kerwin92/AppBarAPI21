package com.brainstorm.neckup.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class QuestionDao {
	private static final String TAG = "QuestionDao";

	private DBOpenHelper helper;//
	private SQLiteDatabase db;//
	private static final String TABLE_NAME = "tb_question";

	private Cursor cursor;
	private Tb_question tb_question;
	// contentValues
	public static final String KEY_QUESTIONID = "_id";
	public static final String KEY_QUESTIONNAME = "qname";
	public static final String KEY_QUESTIONEXPLAIN = "qexplain";

	private int _id;
	private String qname;
	private String qexplain;

	public QuestionDao(Context context)//
	{
		helper = new DBOpenHelper(context);//
		System.out.println("DBOpenHelper");
	}

	/*
	 * public void add(Tb_question tb_question) { try { db =
	 * helper.getWritableDatabase();// ContentValues insertValues = new
	 * ContentValues(); //
	 * 
	 * // insertValues.put(KEY_CERVICALID, tb_cervical.getid());
	 * insertValues.put(KEY_QUESTIONNAME, tb_question.getQname());
	 * insertValues.put(KEY_QUESTIONEXPLAIN, tb_question.getQexplain());
	 * 
	 * db.insert(TABLE_NAME, null, insertValues); } catch (Exception e) {
	 * 
	 * } finally {
	 * 
	 * cursor = db.rawQuery("select last_insert_rowid() from " + TABLE_NAME,
	 * null); int strid = 0; if (cursor.moveToFirst()) strid = cursor.getInt(0);
	 * Log.i(TAG, "�У� " + strid + "��"); cursor.close(); close(); }
	 * 
	 * }
	 */

	public Cursor showQuestion() {
		// String id = Intege . Parse
		// Tb_question tb_question = new Tb_question();
		try {
			db = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/"
					+ DBManager.DB_NAME, null);
			cursor = db.rawQuery("select * from " + TABLE_NAME, null);
		} catch (Exception e) {
			System.out.println("catch block");
		} finally {
			// cursor.close();
			// close();
		}
		return cursor;
	}

	public void databaseClose() {

		if (db != null) {
			Log.i(TAG, "on close");
			this.db.close();
		}

	}

	public Tb_question showExplain(Tb_question tb_question) {
		// TODO Auto-generated method stub
		_id = tb_question.getid();
		try {
			// db = helper.getWritableDatabase();
			db = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/"
					+ DBManager.DB_NAME, null);
			cursor = db.rawQuery("select * from " + TABLE_NAME
					+ " where _id = " + _id, null);
			if (cursor.moveToFirst()) {
				Log.i(TAG, "cursor.moveToFirst");
				qname = cursor.getString(1);
				qexplain = cursor.getString(2); // psw column is the second
												// column.
				Log.i(TAG, "qexplain:  " + qexplain);

				tb_question.setid(_id);
				tb_question.setQname(qname);
				tb_question.setQexplain(qexplain);
			}

		} catch (Exception e) {
			Log.i(TAG, "catch block");
		} finally {
			cursor.close();
			databaseClose();
		}
		return tb_question;
	}

}

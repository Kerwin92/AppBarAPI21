package com.brainstorm.neckup.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBOpenHelper extends SQLiteOpenHelper {
	private static final String TAG = "DBOpenHelper";

	private static final int VERSION = 1;//
	private static final String DATABASE_NAME = "reg.db";//

	private static final String TABLEUSER_CREATE = "create table if not exists tb_user (_id integer primary key autoincrement, "
			+ "u_id text, psw text,"
			+ "nickname text, sex integer, age_group text, reg_date text,"
			+ "task1 int, task2 int," + "score int, flag int);";

	// create table
	private static final String TABLEUSER_EX_CREATE = "create table if not exists tb_user_ex (_id integer primary key autoincrement, "
			+ "u_id text, nickname text,"
			+ "insist_score int, finish_score int, last_score int);";

	public DBOpenHelper(Context context) {//

		super(context, DATABASE_NAME, null, VERSION);//
		System.out.println("Dbopen");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(TABLEUSER_CREATE);
		db.execSQL(TABLEUSER_EX_CREATE);
		Log.i(TAG, "onCreate");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			db.execSQL("drop table if exists tb_user");
			db.execSQL("drop table if exists tb_user_ex");
			onCreate(db);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void onOpen(SQLiteDatabase db) {
		// db.execSQL("DROP TABLE IF EXISTS tb_user");
		Log.i(TAG, "onOpen");
	}

	public void onDestroy() {

	}

}

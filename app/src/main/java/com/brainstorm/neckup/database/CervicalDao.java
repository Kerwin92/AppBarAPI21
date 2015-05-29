package com.brainstorm.neckup.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CervicalDao {
	private static final String TAG = "CervicalDao";

	private DBOpenHelper helper;//
	private SQLiteDatabase db;//
	private static final String TABLE_NAME = "tb_cervical";
	public DBManager dbHelper;

	private Cursor cursor;

	// contentValues
	public static final String KEY_CERVICALID = "_id";
	public static final String KEY_CERVINAME = "cervi_name";
	public static final String KEY_CERVIZUOFA = "cervi_zuofa";
	public static final String KEY_CERVIZHUYI = "cervi_zhuyi";
	public static final String KEY_CERVIYUANLI = "cervi_yuanli";
	public static final String KEY_CERVIIMG = "cervi_img";
	public static final String KEY_CERVIVIDEO = "cervi_video";

	// private int id;
	private String cervi_name;
	private String cervi_zuofa;
	private String cervi_zhuyi;
	private String cervi_yuanli;
	private String cervi_img;
	private String cervi_video;

	public CervicalDao(Context context)//
	{
		helper = new DBOpenHelper(context);//
		System.out.println("DBOpenHelper");
	}

	public void add(Tb_cervical tb_cervical) {
		try {
			db = helper.getWritableDatabase();//
			ContentValues insertValues = new ContentValues(); //

			// insertValues.put(KEY_CERVICALID, tb_cervical.getid());
			insertValues.put(KEY_CERVINAME, tb_cervical.getCervi_name());
			insertValues.put(KEY_CERVIZUOFA, tb_cervical.getCervi_zuofa());
			insertValues.put(KEY_CERVIZHUYI, tb_cervical.getCervi_zhuyi());
			insertValues.put(KEY_CERVIYUANLI, tb_cervical.getCervi_yuanli());
			insertValues.put(KEY_CERVIIMG, tb_cervical.getCervi_img());
			insertValues.put(KEY_CERVIVIDEO, tb_cervical.getCervi_video());
			db.insert(TABLE_NAME, null, insertValues);
		} catch (Exception e) {

		} finally {

			Cursor cursor = db.rawQuery("select last_insert_rowid() from "
					+ TABLE_NAME, null);
			int strid = 0;
			if (cursor.moveToFirst())
				strid = cursor.getInt(0);
			Log.i(TAG, "�У� " + strid + "��");
			cursor.close();
			databaseClose();
		}
	}

	// cervi_id的值+1，cervical数据库中从1开始编号，其他计数基本从0开始编号。
	public Tb_cervical show(int cervi_id) {
		cervi_id += 1;
		Tb_cervical tb_cervical = new Tb_cervical();
		try {
			// db = helper.getWritableDatabase();
			db = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/"
					+ DBManager.DB_NAME, null);
			cursor = db.rawQuery("select * from " + TABLE_NAME
					+ " where _id = " + cervi_id, null);
			if (cursor.moveToFirst()) {
				Log.i(TAG, "cursor.moveToFirst");
				cervi_name = cursor.getString(cursor
						.getColumnIndex("cervi_name"));
				Log.i(TAG, "cervi_name:  " + cervi_name);
				cervi_zuofa = cursor.getString(cursor
						.getColumnIndex("cervi_zuofa"));
				cervi_zhuyi = cursor.getString(cursor
						.getColumnIndex("cervi_zhuyi"));
				cervi_yuanli = cursor.getString(3);
				cervi_img = cursor
						.getString(cursor.getColumnIndex("cervi_img"));
				cervi_video = cursor.getString(cursor
						.getColumnIndex("cervi_video"));
				tb_cervical.setCervi_name(cervi_name);
				tb_cervical.setCervi_zuofa(cervi_zuofa);
				tb_cervical.setCervi_zhuyi(cervi_zhuyi);
				tb_cervical.setCervi_yuanli(cervi_yuanli);
				tb_cervical.setCervi_img(cervi_img);
				tb_cervical.setCervi_video(cervi_video);
			}

		} catch (Exception e) {
			System.out.println("catch block");
		} finally {
			cursor.close();
			databaseClose();

		}
		return tb_cervical;
	}

	public void databaseClose() {

		if (db != null) {
			Log.i(TAG, "on close");
			this.db.close();
		}

	}

	public String getCerviname(int task) {
		try {
			db = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/"
					+ DBManager.DB_NAME, null);
			cursor = db.rawQuery("select * from " + TABLE_NAME
					+ " where _id = " + task, null);
			if (cursor.moveToFirst()) {
				Log.i(TAG, "cursor.moveToFirst");
				cervi_name = cursor.getString(cursor
						.getColumnIndex("cervi_name"));
				Log.i(TAG, "cervi_name:  " + cervi_name);
			}

		} catch (Exception e) {
			System.out.println("catch block");
		} finally {
			cursor.close();
			databaseClose();

		}
		return cervi_name;
	}

}

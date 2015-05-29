package com.brainstorm.neckup.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.brainstorm.neckup.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class DBManager {
    private final int BUFFER_SIZE = 400000;
    public static final String DB_NAME = "cervical.db";
    public static final String PACKAGE_NAME = "com.brainstorm.neckup";
    public static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"
            + PACKAGE_NAME;
    private SQLiteDatabase database;
    private Context context;

    public DBManager(Context context) {
        this.context = context;
        //        PackageInfo info = null;
        //        try {
        //            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        //        } catch (PackageManager.NameNotFoundException e) {
        //            e.printStackTrace();
        //        }
        //        String packageNames = info.packageName;
    }

    public void openDatabase() {
        this.database = this.openDatabase(DB_PATH + "/" + DB_NAME);
    }

    private SQLiteDatabase openDatabase(String dbfile) {
        try {
            // 这段代码初次写入的时候有问题，what happened
            if (!(new File(dbfile).exists())) {
                InputStream is = this.context.getResources().openRawResource(
                        R.raw.cervical);
                FileOutputStream fos = new FileOutputStream(dbfile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
            System.out.println("dbfile: " + dbfile);
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile,
                    null);
            return db;
        } catch (FileNotFoundException e) {
            Log.e("Database", "File not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Database", "IO exception");
            e.printStackTrace();
        }
        return null;
    }

    public void closeDatabase() {
        this.database.close();
    }
}

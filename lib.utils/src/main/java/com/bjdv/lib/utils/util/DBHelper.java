package com.bjdv.lib.utils.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

@SuppressLint("SimpleDateFormat")
public class DBHelper extends SQLiteOpenHelper {
	private static final String Tag = "DBHelper";

	public static final int DATABASE_VERSION = 1;

	public DBHelper(Context context) {
		super(context, DBManager.DB_NAME, null, DATABASE_VERSION);
		Log.v(Tag, "DBHelper()");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.v(Tag, "SQLitHelper()  onCreate");
		db.execSQL("Create table if not exists CallLog(callId INTEGER primary key autoincrement,woStaffId Text,woNbr TEXT,contactInfo TEXT, callType TEXT,callTime TEXT,duration TEXT,remark TEXT , state INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
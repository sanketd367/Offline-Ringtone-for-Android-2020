package com.invariablestudio.nokiaringtone.Utils;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DataBasHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "RingTone.db";
    public static final String FAV_TABLE = "fav_table";
    public static final String ID = "id";
    public static final String SHOWFILENAME = "showFileName";
    public static final String RAWFILENAME = "rawFileName";
    public static final String MYNAME = "myName";


    public DataBasHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + FAV_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + SHOWFILENAME + " TEXT," + RAWFILENAME + " TEXT," + MYNAME + " TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FAV_TABLE);
        onCreate(db);
    }

    public boolean InsertData(String showFileName, String rawFileName, String myName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SHOWFILENAME, showFileName);
        contentValues.put(RAWFILENAME, rawFileName);
        contentValues.put(MYNAME, myName);
        long result = db.insert(FAV_TABLE, null, contentValues);
        Log.e("database", "insert_data" + result);

        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor retriveData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + FAV_TABLE, null);
        Log.d(TAG, "retriveData: " + res);
        return res;
    }

    public int deleteData(String myName) {
        SQLiteDatabase database = getWritableDatabase();
        int a = database.delete(FAV_TABLE, MYNAME + " = ?", new String[]{myName});
        Log.e(TAG, "deleteData: " + a);
        database.close();
        return a;
    }

    public boolean isAdded(String myname) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        // String query = "SELECT * FROM " + FAV_TABLE + " WHERE " + MYNAME + " = " + myname;
        String query1 = "SELECT " + MYNAME + " FROM " + FAV_TABLE + " WHERE " + MYNAME + "='" + myname + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(query1, null);
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
}

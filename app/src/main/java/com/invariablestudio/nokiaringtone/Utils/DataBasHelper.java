package com.invariablestudio.nokiaringtone.Utils;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.invariablestudio.nokiaringtone.GSRingtoneFAV;
import com.invariablestudio.nokiaringtone.GSRingtoneNEW;
import com.invariablestudio.nokiaringtone.RELRingtone;

import java.util.ArrayList;

public class DataBasHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "RingTone.db";

    public static final String AUDIO_TABLE = "audio_table";
    public static final String AUDIO_ID = "audio_id";
    public static final String AUDIO_RAWFILENAME = "audio_rawfilename";
    public static final String AUDIO_SHOWFILENAME = "audio_showfilename";
    public static final String AUDIO_MYNAME = "audio_myname";
    public static final String AUDIO_FAVOURITE = "audio_favourite";
    public static final String AUDIO_RELCOUNT = "audio_relcount";

    public DataBasHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + AUDIO_TABLE + "(" + AUDIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + AUDIO_RAWFILENAME + " TEXT," + AUDIO_SHOWFILENAME + " TEXT," + AUDIO_MYNAME + " TEXT," + AUDIO_FAVOURITE + " TEXT," + AUDIO_RELCOUNT + " INTEGER )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AUDIO_TABLE);
        onCreate(db);
    }

    public boolean InsertAudioData(String rawFileName, String showFileName, String myName, String favourite, int count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AUDIO_RAWFILENAME, rawFileName);
        contentValues.put(AUDIO_SHOWFILENAME, showFileName);
        contentValues.put(AUDIO_MYNAME, myName);
        contentValues.put(AUDIO_FAVOURITE, favourite);
        contentValues.put(AUDIO_RELCOUNT, count);
        long result = db.insertWithOnConflict(AUDIO_TABLE, null, contentValues, SQLiteDatabase.CONFLICT_NONE);
        //  Log.e("database", "insert_data" + result);
        db.close();
        if (result == -1)
            return false;
        else
            return true;
    }


    public void UpdateRel(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + AUDIO_TABLE + " SET " + AUDIO_RELCOUNT + "=" + AUDIO_RELCOUNT + "+1" + " WHERE " + AUDIO_MYNAME + "=?", new String[]{String.valueOf(name)});
    }

    public void UpdateFav(int name, String action) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + AUDIO_TABLE + " SET " + AUDIO_FAVOURITE + "=" + action + " WHERE " + AUDIO_ID + "=?", new String[]{String.valueOf(name)});
    }


    public ArrayList<GSRingtoneFAV> retriveData() {
        ArrayList<GSRingtoneFAV> array_list = new ArrayList<GSRingtoneFAV>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from audio_table WHERE audio_favourite = 1", null);
        //Log.i(TAG, "retriveData: "+res.getCount());

        while (res.moveToNext()) {

            // Log.i(TAG, "retriveData: "+res.getInt(res.getColumnIndex("audio_id")));
            array_list.add(new GSRingtoneFAV(res.getInt(res.getColumnIndex(AUDIO_ID)),
                    res.getString(res.getColumnIndex(AUDIO_RAWFILENAME)),
                    res.getString(res.getColumnIndex(AUDIO_SHOWFILENAME)),
                    res.getString(res.getColumnIndex(AUDIO_MYNAME)),
                    res.getString(res.getColumnIndex(AUDIO_FAVOURITE)),
                    res.getInt(res.getColumnIndex(AUDIO_RELCOUNT))
            ));

        }
        return array_list;
    }

    public ArrayList getAudio() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<GSRingtoneNEW> array_list = new ArrayList<GSRingtoneNEW>();
        Cursor res = db.rawQuery("select * from " + AUDIO_TABLE + " ORDER BY audio_relcount DESC", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(new GSRingtoneNEW(res.getInt(res.getColumnIndex(AUDIO_ID)),
                    res.getString(res.getColumnIndex(AUDIO_RAWFILENAME)),
                    res.getString(res.getColumnIndex(AUDIO_SHOWFILENAME)),
                    res.getString(res.getColumnIndex(AUDIO_MYNAME)),
                    res.getString(res.getColumnIndex(AUDIO_FAVOURITE)),
                    res.getInt(res.getColumnIndex(AUDIO_RELCOUNT))
            ));
            res.moveToNext();
        }
        return array_list;
    }

    public boolean getFav(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select audio_favourite from " + AUDIO_TABLE + " WHERE audio_id = " + id, null);
        res.moveToFirst();
        Log.i(TAG, "getFav: " + res.getString(res.getColumnIndex(AUDIO_FAVOURITE)));
        if (res.getString(res.getColumnIndex(AUDIO_FAVOURITE)).equals("1")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isTableExists() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='" + AUDIO_TABLE + "'";
        Cursor mCursor = db.rawQuery(sql, null);
        if (mCursor.getCount() > 0) {
            return true;
        }
        mCursor.close();
        return false;
    }
}

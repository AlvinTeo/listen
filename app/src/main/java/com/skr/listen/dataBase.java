package com.skr.listen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class dataBase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Tracks.db";
    public static final String TABLE_NAME = "tracks_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "trackName";
    public static final String COL_3 = "trackPlayCount";
    public static final String COL_4 = "trackListener";
    public static final String COL_5 = "trackUrl";
    public static final String COL_6 = "artistName";
    public static final String COL_7 = "artistImage";

    public dataBase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2 + " TEXT, " + COL_3 + " INTEGER, " + COL_4 + " INTEGER, " + COL_5 + " TEXT,  " + COL_6 + " TEXT,  " + COL_7 + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void insertData(String trackName,String trackPlayCount,String trackListener,String trackUrl,String artistName, String artistImage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,trackName);
        contentValues.put(COL_3,trackPlayCount);
        contentValues.put(COL_4,trackListener);
        contentValues.put(COL_5,trackUrl);
        contentValues.put(COL_6,artistName);
        contentValues.put(COL_7,artistImage);
        db.insert(TABLE_NAME,null ,contentValues);
        db.close();

    }

    public ArrayList<TopTracks> getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_NAME,null);


        ArrayList<TopTracks> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            String track_name = cursor.getString(1);
            String track_play_count = cursor.getString(2);
            String track_listener = cursor.getString(3);
            String track_url = cursor.getString(4);
            String artist_name = cursor.getString(5);
            String artist_image = cursor.getString(6);

            TopTracks current = new TopTracks(track_name,Integer.parseInt(track_play_count),Integer.parseInt(track_listener),track_url,artist_name,artist_image);
            result.add(current);
        }

        cursor.close();
        db.close();
        return result;
    }

}

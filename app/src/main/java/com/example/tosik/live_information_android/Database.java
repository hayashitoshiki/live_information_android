package com.example.tosik.live_information_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "TestDB.db";
    private static final String _ID = "_id";

    //歌詞用TABLE
    private static final String TABLE_NAME = "testdb";
    private static final String TABLE1_COLUMN1 = "title";
    private static final String TABLE1_COLUMN2 = "arthist";
    private static final String TABLE1_COLUMN3 = "lyric";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    TABLE1_COLUMN1 + " TEXT," +
                    TABLE1_COLUMN2 + " TEXT," +
                    TABLE1_COLUMN3 + " TEXT)";

    //画像用TABLE
    private static final String TABLE2_NAME = "picturesdb";
    private static final String TABLE2_COLUMN1 = "title";
    private static final String TABLE2_COLUMN2 = "resId";
    private static final String TABLE2_COLUMN3 ="uri";
    private static final String TABLE2_COLUMN4 ="mood";
    private static final String TABLE2_COLUMN5 = "color";

    private static final String SQL_CREATE_ENTRIES2 =
            "CREATE TABLE " + TABLE2_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    TABLE2_COLUMN1 + " TEXT,"+
                    TABLE2_COLUMN2 + " INT,"+
                    TABLE2_COLUMN3 + " TEXT,"+
                    TABLE2_COLUMN4 + " INT,"+
                    TABLE2_COLUMN5 + " String)";



    Database(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //DBCreate
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES2);
        saveData1(db,"null","null","null");
        saveData2(db,null,0, null,0,"BLACK");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

    //歌詞save
    public void saveData1(SQLiteDatabase db,String title, String arthist, String lyric){
        ContentValues values = new ContentValues();
        values.put(TABLE1_COLUMN1, title);
        values.put(TABLE1_COLUMN2, arthist);
        values.put(TABLE1_COLUMN3, lyric);
        db.insert(TABLE_NAME, null, values);
    }

    //画像save
    public void saveData2(SQLiteDatabase db,String title, int redId, String uri_s, int mood, String color){
        ContentValues values = new ContentValues();
        values.put(TABLE2_COLUMN1, title);
        values.put(TABLE2_COLUMN2, redId);
        values.put(TABLE2_COLUMN3, uri_s);
        values.put(TABLE2_COLUMN4, mood);
        values.put(TABLE2_COLUMN5, color);
        db.insert(TABLE2_NAME, null, values);
    }

}

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

    //サンプル画像用TABLE
    private static final String TABLE2_NAME = "picturesdb";
    private static final String TABLE2_COLUMN1 = "title";
    private static final String TABLE2_COLUMN2 = "picture";
    private static final String TABLE2_COLUMN3 ="mood";
    private static final String TABLE2_COLUMN4 = "color";

    private static final String SQL_CREATE_ENTRIES2 =
            "CREATE TABLE " + TABLE2_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    TABLE2_COLUMN1 + " TEXT,"+
                    TABLE2_COLUMN2 + " INT,"+
                    TABLE2_COLUMN3 + " INT,"+
                    TABLE2_COLUMN4 + " String)";

    //ギャラリー用テーブル
    private static final String TABLE3_NAME = "gallerydb";
    private static final String TABLE3_COLUMN1 ="uri";

    private static final String SQL_CREATE_ENTRIES3 =
            "CREATE TABLE " + TABLE3_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    TABLE3_COLUMN1 + " TEXT)";

    Database(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //DBCreate
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES2);
        db.execSQL(SQL_CREATE_ENTRIES3);
        saveData1(db,"null","null","null");
        saveData2(db,"なし",0,0,"BLACK");
        saveData3(db,"null");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

    //歌詞リストsave
    public void saveData1(SQLiteDatabase db,String title, String arthist, String lyric){
        ContentValues values = new ContentValues();
        values.put(TABLE1_COLUMN1, title);
        values.put(TABLE1_COLUMN2, arthist);
        values.put(TABLE1_COLUMN3, lyric);
        db.insert(TABLE_NAME, null, values);
    }

    //サンプル画像save
    public void saveData2(SQLiteDatabase db,String title, int picture,int mood, String color){
        ContentValues values = new ContentValues();
        values.put(TABLE2_COLUMN1, title);
        values.put(TABLE2_COLUMN2, picture);
        values.put(TABLE2_COLUMN3, mood);
        values.put(TABLE2_COLUMN4, color);
        db.insert(TABLE2_NAME, null, values);
    }

    //ギャラリー画像save
    public void saveData3(SQLiteDatabase db,String uri){
        ContentValues values = new ContentValues();
        values.put(TABLE3_COLUMN1,uri);
        db.insert(TABLE3_NAME, null, values);
    }
}

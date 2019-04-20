package com.example.tosik.live_information_android;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SettingUtil {
    private static String title;
    private static String uri;
    private static int mood;
    private static String colorName;

    private final static int SAMPLE = 0;
    private static int color;

    private SettingUtil(){}

    public static int getColor(){
        return color;
    }
    public static String getTitle(){
        return title;
    }
    public static String getColorName(){
        return colorName;
    }
    public static String getURI(){
        return uri;
    }
    public static int getMood(){
        return mood;
    }

    //背景設定
    public static void set(ArrayList<TextView> textList, ImageView imgView, Context context) {

        Database helper = new Database(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor p_db = db.query(
                "picturesdb",
                new String[]{"title", "resId", "uri", "mood","color"},
                "_id = 1",
                null,
                null,
                null,
                null
        );
        p_db.moveToFirst();
        title = p_db.getString(0);
        uri = p_db.getString(2);
        mood = p_db.getInt(3);
        colorName = p_db.getString(4);

        if(mood == SAMPLE) {
            //サンプル画像
            int image = p_db.getInt(1);
            imgView.setImageResource(image);
        }else {
            //ギャラリー画像
            Log.d("MainActivity","ギャラリー");
            File file = new File(uri);
            try (InputStream inputStream0 = new FileInputStream(file)) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream0);
                imgView.setImageBitmap(bitmap);
                imgView.setColorFilter(Color.parseColor("#4Dffffff"), PorterDuff.Mode.LIGHTEN);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        p_db.close();

        //文字色設定
        if(textList != null){
            color = ColorStack.valueOf(colorName).getColor();
            for(TextView textView:textList){ textView.setTextColor(color); }
        }
    }
}

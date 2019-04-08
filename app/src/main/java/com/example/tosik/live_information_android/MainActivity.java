package com.example.tosik.live_information_android;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {
    private ImageView imgView;
    private Database helper;
    private SQLiteDatabase db;
    private TextView textView;

    private static final int SAMPLE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgView = (ImageView)findViewById(R.id.home_bg);
        textView = (TextView)findViewById(R.id.title);
        helper = new Database(getApplicationContext());
        db = helper.getReadableDatabase();

       set_background();
    }

    public void lyrics_Button(View v){
        Intent i = new Intent(getApplication(),LyricsHome.class);
        startActivity(i);
    }

    public void Setting(View v){
        Intent intent = new Intent(getApplication(),Setting.class);
        startActivity(intent);
    }

    //背景設定
    public void set_background() {

        Cursor p_db = db.query(
                "picturesdb",
                new String[]{"title", "picture", "mood","color"},
                null,
                null,
                null,
                null,
                null
        );
        p_db.moveToFirst();

        //文字色設定
        setColor(ColorStack.valueOf(p_db.getString(3)));

        //サンプル画像 or ギャラリー画像
        if(p_db.getInt(2) == SAMPLE) {
            int image = p_db.getInt(1);
            imgView.setImageResource(image);
        }else {
            Log.d("MainActivity","ギャラリー");
            Cursor g_db = db.query(
                    "gallerydb",
                    new String[]{"uri"},
                    null,
                    null,
                    null,
                    null,
                    null
            );
            g_db.moveToFirst();
            String uri = g_db.getString(0);
            File file = new File(uri);
            try (InputStream inputStream0 =
                         new FileInputStream(file)) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream0);
                imgView.setImageBitmap(bitmap);
                imgView.setColorFilter(Color.parseColor("#4Dffffff"), PorterDuff.Mode.LIGHTEN);
            } catch (IOException e) {
                e.printStackTrace();
            }
            g_db.close();
        }
        p_db.close();
    }

    //文字色セット
    public void setColor(ColorStack color){
        textView.setTextColor(Color.rgb(color.getRed(),color.getGreen(),color.getBlue()));
    }

    @Override
    public void onRestart(){
        super.onRestart();
        set_background();

    }

}

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
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LyricView extends AppCompatActivity {
    private Database helper;
    private Cursor co = null;
    private TextView textView;
    private TextView mtextView;
    private TextView ltextView;
    private ImageView imgView;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyric_view);
        Intent intent = getIntent();
        String art = intent.getStringExtra("arthist");
        String music = intent.getStringExtra("music");
        String lyric = intent.getStringExtra("lyric");
        textView = findViewById(R.id.arthist);
        mtextView = findViewById(R.id.music);
        ltextView = findViewById(R.id.lyric);
        imgView = findViewById(R.id.lyrics_back);
        textView.setText(art);
        mtextView.setText(music);
        ltextView.setText(lyric);
        helper = new Database(getApplicationContext());
        db = helper.getReadableDatabase();
        setBackground();
    }

    //背景セット
    private void setBackground(){
        co = db.query(
                "picturesdb",
                new String[]{"title", "picture","mood","color"},
                null,
                null,
                null,
                null,
                null
        );

        co.moveToFirst();
        setColor(ColorStack.valueOf(co.getString(3)));
        if(co.getInt(2) == 0) {
            imgView.setImageResource(co.getInt(1));
        }else {
            Log.d("LyricView","ギャラリー");
            Cursor cu = db.query(
                    "gallerydb",
                    new String[]{"uri"},
                    null,
                    null,
                    null,
                    null,
                    null
            );
            cu.moveToFirst();
            String uri = cu.getString(0);
            File file = new File(uri);
            try (InputStream inputStream0 =
                         new FileInputStream(file)) {
                Log.d("LyricView","ギャラリーOK");
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream0);
                imgView.setImageBitmap(bitmap);
                imgView.setColorFilter(Color.parseColor("#4Dffffff"), PorterDuff.Mode.LIGHTEN);
            } catch (IOException e) {
                Log.d("LyricView","ギャラリーNG");
                e.printStackTrace();
            }
            cu.close();
        }
        co.close();
    }

    //文字色セット
    private void setColor(ColorStack color){
        textView.setTextColor(Color.rgb(color.getRed(),color.getGreen(),color.getBlue()));
        mtextView.setTextColor(Color.rgb(color.getRed(),color.getGreen(),color.getBlue()));
        ltextView.setTextColor(Color.rgb(color.getRed(),color.getGreen(),color.getBlue()));
    }
}

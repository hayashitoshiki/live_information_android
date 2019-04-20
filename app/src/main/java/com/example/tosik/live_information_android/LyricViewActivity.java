package com.example.tosik.live_information_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class LyricViewActivity extends AppCompatActivity {

    private ImageView imgView;
    private ArrayList<TextView> textList = new ArrayList<TextView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyric_view);
        Intent intent = getIntent();
        String art = intent.getStringExtra("arthist");
        String music = intent.getStringExtra("music");
        String lyric = intent.getStringExtra("lyric");
        textList.add((TextView)findViewById(R.id.arthist));
        textList.add((TextView)findViewById(R.id.music));
        textList.add((TextView)findViewById(R.id.lyric));
        textList.get(0).setText(art);
        textList.get(1).setText(music);
        textList.get(2).setText(lyric);
        imgView = findViewById(R.id.lyrics_back);
        SettingUtil.set(textList, imgView,getApplicationContext());
    }

}

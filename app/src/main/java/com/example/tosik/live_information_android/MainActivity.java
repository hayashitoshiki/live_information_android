package com.example.tosik.live_information_android;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private ArrayList<TextView> textList = new ArrayList<>();
    private ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView = (ImageView)findViewById(R.id.home_bg);
        textList.add((TextView)findViewById(R.id.title));

        //背景、文字色適用
        SettingUtil.set(textList, imgView,getApplicationContext());
    }

    public void lyrics_Button(View v){
        Intent i = new Intent(getApplication(),LyricsHomeActivity.class);
        startActivity(i);
    }

    public void Setting(View v){
        Intent i = new Intent(getApplication(),SettingActivity.class);
        startActivity(i);
    }

    @Override
    public void onRestart(){
        super.onRestart();
        SettingUtil.set(textList, imgView,getApplicationContext());
    }

}

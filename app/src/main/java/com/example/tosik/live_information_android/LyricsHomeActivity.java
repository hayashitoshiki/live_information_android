package com.example.tosik.live_information_android;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LyricsHomeActivity extends AppCompatActivity{

    private ArrayList<ArrayList<String>> musicList = new ArrayList<ArrayList<String>>();
    private ArrayList<TextView> textList = new ArrayList<>();
    private CustomAdapter adapter;
    private ListView listview;
    private ImageView imgView;
    private SQLiteDatabase db;
    private ApiService api;

    private Database helper;
    private Cursor t_db = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics_home);
        helper = new Database(getApplicationContext());
        imgView = (ImageView)findViewById(R.id.syasin2);
        textList.add((TextView)findViewById(R.id.title));
        db = helper.getReadableDatabase();
        SettingUtil.set(textList, imgView,getApplicationContext());
        readData();
        //deleteDatabase("TestDB.db");

    }



    //ListView更新
    private void readData(){
        t_db = db.query(
                "testdb",
                new String[]{"title", "arthist","lyric"},
                null,
                null,
                null,
                null,
                null
        );

        boolean mov = t_db.moveToFirst();
        musicList.clear();
        while(mov){
            ArrayList<String> datas = new ArrayList<String>();
            datas.add(t_db.getString(0));//タイトル
            datas.add(t_db.getString(1));//アーティスト
            datas.add(t_db.getString(2));//歌詞
            musicList.add(datas);
            mov = t_db.moveToNext();

        }
        t_db.close();
        adapter = new CustomAdapter(getApplicationContext(), R.layout.row_item, musicList,SettingUtil.getColor());
        listview = (ListView)findViewById(R.id.listview);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String art = musicList.get(position).get(0);
                        String music = musicList.get(position).get(1);
                        String lyric = musicList.get(position).get(2);
                        Intent i = new Intent(getApplication(),LyricViewActivity.class);
                        i.putExtra("arthist",art);
                        i.putExtra("music",music);
                        i.putExtra("lyric",lyric);
                        startActivity(i);

            }
        });
    }

    //更新データ取得
    public void getData(View v) {
        api= Provider.provideApiService();
        final ArrayList<Music> MusicList = new ArrayList<>();
        Call<List<Music>> call = api.music("api.json");
        //クエリを投げる
        try {
            call.enqueue(new Callback<List<Music>>() {
                @Override                           //取得成功
                public void onResponse(Call<List<Music>> call, Response<List<Music>> response) {
                    Log.d("LyricsHomeActivity", "call onResponse");
                    if(response != null && response.body() != null){
                        MusicList.addAll(response.body());
                        updateDB(MusicList);
                        readData();
                    }else{
                        Toast toast = Toast.makeText(LyricsHomeActivity.this, "通信できません", Toast.LENGTH_LONG);
                        toast.show();
                    }

                }
                @Override                           //取得失敗
                public void onFailure(Call<List<Music>> call, Throwable t) {
                    Toast toast = Toast.makeText(LyricsHomeActivity.this, "通信できません", Toast.LENGTH_LONG);
                    toast.show();
                    Log.d("LyricsHomeActivity", "call onFailure");

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //DBアップデート
    private void updateDB(ArrayList<Music> MusicList) {
        db.delete("testdb", null, null);
        for (Music music : MusicList) {
            helper.saveData1(db, music.getTitle(), music.getArthist(), music.getLyric());
        }
    }

}

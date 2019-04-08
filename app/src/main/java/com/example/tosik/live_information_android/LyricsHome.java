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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LyricsHome extends AppCompatActivity {


    private ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();
    private ListView listview;
    private ApiService api;
    private CustomAdapter adapter;
    private ImageView imgView;
    private TextView textView;
    private SQLiteDatabase db;
    private int SAMPLE = 0;
    private ColorStack color;

    private Database helper;
    private Cursor t_db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics_home);
        helper = new Database(getApplicationContext());
        imgView = (ImageView)findViewById(R.id.syasin2);
        textView = (TextView)findViewById(R.id.title);
        db = helper.getReadableDatabase();
       set_background();
        readData();
    }

    //ListView更新
    public void readData(){
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
        array.clear();
        while(mov){
            ArrayList<String> datas = new ArrayList<String>();
            datas.add(t_db.getString(0));//タイトル
            datas.add(t_db.getString(1));//アーティスト
            datas.add(t_db.getString(2));//歌詞
            array.add(datas);
            mov = t_db.moveToNext();

        }
        t_db.close();
        adapter = new CustomAdapter(getApplicationContext(), R.layout.row_item, array,color);
        listview = (ListView)findViewById(R.id.listview);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String art = array.get(position).get(0);
                        String music = array.get(position).get(1);
                        String lyric = array.get(position).get(2);
                        Intent i = new Intent(getApplication(),LyricView.class);
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
        //クエリを投げる
        Call<List<Music>> call = api.music("api.json");
        try {
            call.enqueue(new Callback<List<Music>>() {
                @Override                           //取得成功
                public void onResponse(Call<List<Music>> call, Response<List<Music>> response) {
                    Log.d("LyricsHome", "call onResponse");
                    MusicList.addAll(response.body());
                    updateDB(MusicList);
                    readData();
                }
                @Override                           //取得失敗
                public void onFailure(Call<List<Music>> call, Throwable t) {
                    Toast toast = Toast.makeText(LyricsHome.this, "通信できません", Toast.LENGTH_LONG);
                    toast.show();
                    Log.d("LyricsHome", "call onFailure");

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
        color = ColorStack.valueOf(p_db.getString(3));
        setColor(color);



        //サンプル画像 or ギャラリー画像
        if(p_db.getInt(2) == SAMPLE) {
            int image = p_db.getInt(1);
            imgView.setImageResource(image);
        }else {
            Log.d("MainActivity","ギャラリー");
            Cursor g_db = db.query(
                    "gallerydb",
                    new String[]{ "uri"},
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

    }
}

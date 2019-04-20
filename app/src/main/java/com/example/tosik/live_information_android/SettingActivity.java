package com.example.tosik.live_information_android;


import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import android.support.design.widget.TabLayout;

import java.io.IOException;


public class SettingActivity extends AppCompatActivity implements BackgroundFragment.OnClickListener ,  TextColorFragment.OnClickListener {

    private String title = null;
    private int resId = 0;
    private String uri_s = null;
    private int mood = 0;
    private String color = "BLACK";

    private Uri uri;
    private final int GALLERY = 1;
    private final int SAMPLE = 0;
    private static final int READ_REQUEST_CODE = 42;

    private Database helper;
    private SQLiteDatabase db;
    private TabLayout tabs;
    private ViewPager pager;
    private ImageView image;
    private CustomPageAdapter adapter;

    private Fragment fragment1;
    private Fragment fragment2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        helper = new Database(getApplicationContext());
        db = helper.getReadableDatabase();
        tabs =  (TabLayout) findViewById(R.id.tabs);
        image = (ImageView)findViewById(R.id.setting_back);
        pager = (ViewPager)findViewById(R.id.pager);

        adapter = new CustomPageAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);
    }


    //背景変更
    @Override
    public void onClickBackImage(String title) {
        if(title != null){
            resId = getResources().getIdentifier(title,"drawable",getApplication().getPackageName());
        }else{
            resId = 0;
        }

        mood = SAMPLE;
        this.title = title;
        image.setImageResource(resId);
        image.setColorFilter(Color.parseColor("#00ffffff"), PorterDuff.Mode.LIGHTEN);
    }

    //文字色変更
    @Override
    public void onClickTextColor(ColorStack color) {
        this.color = color.toString();
        ((BackgroundFragment) fragment1).setColor(color.getColor());
    }

    //初回背景セット
    @Override
    public void setImageFirst(){
        fragment1 = adapter.getCachedFragmentAt(0);
        SettingUtil.set(null, image,getApplicationContext());
        if(SettingUtil.getMood() == SAMPLE) {
            //サンプル画像
            ((BackgroundFragment) fragment1).setRadioButton(SettingUtil.getTitle());
        }else{
            //ギャラリー画像
            uri_s = SettingUtil.getURI();
        }
    }

    //初回文字色セット
    @Override
    public void setColorFirst(){
        fragment2 = adapter.getCachedFragmentAt(1);
        ((TextColorFragment) fragment2).setColor(ColorStack.valueOf(SettingUtil.getColorName()));
    }

    //DB更新
    public void Setting(View v){
        db.delete("picturesdb", null, null);
        helper.saveData2(db, title, resId, uri_s, mood, color);
        Intent intent = new Intent(getApplication(),MainActivity.class);
        startActivity(intent);
    }

    //ギャラリーボタン
    public void getGarary(View v){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/jpeg");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    //ギャラリー設定
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            if (resultData != null) {
                uri = resultData.getData();
                Log.d("SettingActivity","uri："+uri);
                uri_s = getPath(getApplication(),uri);
                Log.d("SettingActivity","uri："+uri_s);
                title = null;
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    mood = GALLERY;
                    image.setImageBitmap(bitmap);
                    image.setColorFilter(Color.parseColor("#4Dffffff"), PorterDuff.Mode.LIGHTEN);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //画像パス取得
    public static String getPath(final Context context, final Uri uri) {
        boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // SDカード
            if (isExternalStorageDocument(uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // ダウンロード
            else if (isDownloadsDocument(uri)) {
                String id = DocumentsContract.getDocumentId(uri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // 端末内
            else if (isMediaDocument(uri)) {
                String Id = DocumentsContract.getDocumentId(uri);
                String[] split = Id.split(":");
                Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                String selection = "_id=?";
                String[] selectionArgs = new String[] {split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    //ファイルパス生成
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        String column = "_data";
        String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    //パス判断
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

}

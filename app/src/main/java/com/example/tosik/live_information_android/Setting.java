package com.example.tosik.live_information_android;


import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
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
import android.view.View;
import android.widget.ImageView;

import android.support.design.widget.TabLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class Setting extends AppCompatActivity implements BackgroundSetting.OnClickListener ,  TextColorSetting.OnClickListener {
    private ImageView image;
    private String title = "null";
    private int resId = 0;
    private Uri uri;
    private String uri_s;
    private String color = "BLACK";
    private int mood = 0;
    private int GALLERY = 1;
    private int SAMPLE = 0;
    private static final int READ_REQUEST_CODE = 42;

    private Database helper;
    private SQLiteDatabase db;
    private TabLayout tabs;
    private CustomPageAdapter adapter;
    private ViewPager pager;
    private Cursor p_db=null;

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
        switch(title){
            case "リラックス":
                resId = getResources().getIdentifier("relax","drawable",getApplication().getPackageName());
                break;
            case "炎":
                resId = getResources().getIdentifier("fire","drawable",getApplication().getPackageName());
                break;
            case "水滴":
                resId = getResources().getIdentifier("wate","drawable",getApplication().getPackageName());
                break;
            case "光":
                resId = getResources().getIdentifier("sun","drawable",getApplication().getPackageName());
                break;
            default:
                resId = 0;
                break;

        }
        mood = SAMPLE;
        this.title = title;
        image.setImageResource(resId);
        image.setColorFilter(Color.parseColor("#00ffffff"), PorterDuff.Mode.LIGHTEN);
    }

    //文字色変更
    @Override
    public void onClickTextColor(ColorStack color) {

        this.color = color.getName();
        ((BackgroundSetting) fragment1).setColor(color);
    }

    //初回背景セット
    @Override
    public void setImageFirst(){

        fragment1 = adapter.getCachedFragmentAt(0);
        p_db = db.query(
                "picturesdb",
                new String[]{"title", "picture", "mood","color"},
                null,
                null,
                null,
                null,
                null
        );
        p_db.moveToFirst();
        title = p_db.getString(0);

        //サンプル背景セット
        if(p_db.getInt(2) == SAMPLE) {
            if (fragment1 != null && fragment1 instanceof BackgroundSetting) {
                ((BackgroundSetting) fragment1).setRadioButton(title);
            }
        }else{//ギャラリー背景セット
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
            uri_s = g_db.getString(0);
            mood = GALLERY;
            File file = new File(uri_s);
            try (InputStream inputStream0 =
                         new FileInputStream(file)) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream0);
                image.setImageBitmap(bitmap);
                image.setColorFilter(Color.parseColor("#4Dffffff"), PorterDuff.Mode.LIGHTEN);
            } catch (IOException e) {
                e.printStackTrace();
            }
            g_db.close();
        }

    }

    //初回文字色セット
    @Override
    public void setColorFirst(){

        fragment2 = adapter.getCachedFragmentAt(1);
        if (fragment2 != null && fragment2 instanceof TextColorSetting) {
            ((TextColorSetting) fragment2).setColor(ColorStack.valueOf(p_db.getString(3)));
        }
        p_db.close();
    }

    //DB更新
    public void Setting(View v){
        db.delete("picturesdb", null, null);
        helper.saveData2(db,title,resId,mood,color);
        if(mood == GALLERY){
            db.delete("gallerydb", null, null);
            helper.saveData3(db,uri_s);
        }
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
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            if (resultData != null) {
                uri = resultData.getData();
                uri_s = getPath(getApplication(),uri);
                title = "ギャラリー";
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


    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

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

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

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

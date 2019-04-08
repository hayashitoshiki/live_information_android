package com.example.tosik.live_information_android;

//HTTP通信のベース設定

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Provider {
    private static ApiService ApiService = null;
    public static ApiService provideApiService() {
        if (ApiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.11.3:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            ApiService = retrofit.create(ApiService.class);
        }
        return ApiService;
    }
}

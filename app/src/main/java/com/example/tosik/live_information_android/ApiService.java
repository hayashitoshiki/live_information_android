package com.example.tosik.live_information_android;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiService {

    @GET
    Call<List<Music>> music(@Url String url);


}

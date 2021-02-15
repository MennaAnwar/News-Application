package com.example.newsapplication;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CallableInterface {

    @GET("/v2/top-headlines?country=us&apiKey=127f768e8dfa453bb496de00cb0c61f6")
    Call<NewsModel> getNews();
}
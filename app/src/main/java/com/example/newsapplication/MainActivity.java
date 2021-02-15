package com.example.newsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.pb);
        checkForConnection();



    }

    private void checkForConnection() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if( networkInfo != null && networkInfo.isConnected())
            getdata();
        else {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Check Internet Conncection", Toast.LENGTH_LONG).show();
        }
    }

    private void getdata(){


        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl("https://newsapi.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CallableInterface callable = retrofit.create(CallableInterface.class);
        Call<NewsModel> newsModelCall = callable.getNews();

        newsModelCall.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                progressBar.setVisibility(View.INVISIBLE);
                NewsModel newsModel =  response.body();
                ArrayList<Article> articles = newsModel.getArticles();
                Log.d("json" , articles.get(0).getUrlToImage());
                showListView(articles);
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Log.d("json" , "Error" + t.getMessage());

            }
        });
    }

    private void showListView (ArrayList<Article> articles){
        CustomAdapter adapter  = new CustomAdapter(this , articles);
        ListView listView = findViewById(R.id.lv) ;
        listView.setAdapter(adapter);

        listView.setOnItemClickListener (new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri link = Uri.parse(articles.get(position).getUrl());
                Intent i = new Intent (Intent.ACTION_VIEW , link);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        ExitDialog dialog = new ExitDialog();
        dialog.show(getSupportFragmentManager() , "");
        dialog.setCancelable(false);
    }

    public void refresh(View view) {
        progressBar.setVisibility(View.VISIBLE);
        checkForConnection();

    }
}
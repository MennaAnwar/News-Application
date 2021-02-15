package com.example.newsapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Article> articles;
    public CustomAdapter(Activity activity , ArrayList<Article> articles) {
        this.activity = activity;
        this . articles = articles;

    }

    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public Object getItem(int position) {
        return articles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(R.layout.custom_item , parent , false);

        TextView textView = convertView.findViewById(R.id.tv);
        ImageView imageView = convertView.findViewById(R.id.iv);
        textView.setText(articles.get(position).getTitle().substring(0,25)+".....");

        Picasso
                .get()
                .load(articles.get(position).getUrlToImage())
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView);
        return convertView;
    }
}

package com.example.abdu.newsfeed;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {

    NewsAdapter(Activity context, ArrayList<News> Items) {
        super(context, R.layout.newslist, Items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // check if the current view is reused else inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news, parent, false);
        }

        News item = getItem(position);
        TextView titleTextView = listItemView.findViewById(R.id.title);
        titleTextView.setText(item.getTitle());
        TextView detailsTextView = listItemView.findViewById(R.id.link);
        detailsTextView.setText(item.getUrl());

        return listItemView;
    }
}

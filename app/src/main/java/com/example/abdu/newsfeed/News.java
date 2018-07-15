package com.example.abdu.newsfeed;


public class News {
    private String title;
    private String url;
    private String date;

    News(String t, String u, String d) {
        title = t;
        url = u;
        date = d;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }
}

package com.example.abdu.newsfeed;


public class News {
    private String title;
    private String url;

    News(String t, String u) {
        title = t;
        url = u;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}

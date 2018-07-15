package com.example.abdu.newsfeed;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class Query {
    public static ArrayList<News> extractNews(String url) {
        URL link = createUrl(url);
        try {

            JSONObject result = new JSONObject(makeHttpRequest(link));
            return extractFeatureFromJson(result);

        } catch (Exception err) {
            return null;
        }

    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);
        } catch (IOException e) {
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {

                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createUrl(String stringUrl) {
        URL url;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            return null;
        }
        return url;
    }

    private static ArrayList<News> extractFeatureFromJson(JSONObject response) {
        ArrayList<News> newsList = new ArrayList<>();
        try {
            JSONObject responseObject = response.getJSONObject("response");

            if (responseObject != null) {

                JSONArray results = responseObject.getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject news = results.getJSONObject(i);
                    String title = news.getString("webTitle");
                    String link = news.getString("webUrl");
                    String date = news.getString("webPublicationDate");
                    newsList.add(new News(title, link, date));
                }

            }
        } catch (JSONException e) {
            return null;
        }
        return newsList;
    }
}

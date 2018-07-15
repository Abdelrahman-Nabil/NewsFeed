package com.example.abdu.newsfeed;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class CultureFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<News>> {
    private static final String _URL = "https://content.guardianapis.com/search?";
    private NewsAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView emptyView;
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.newslist, container, false);
        adapter = new NewsAdapter(getActivity(), new ArrayList<News>());
        // finding the listView and setting the adapter to it
        ListView listView = rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);
        emptyView = rootView.findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News news = adapter.getItem(i);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(news.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
        ConnectivityManager connMgr = (ConnectivityManager)
                getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();


            loaderManager.initLoader(1, null, this);
        } else {

            emptyView.setText(R.string.no_internet_connection);
            emptyView.setTextColor(getResources().getColor(R.color.red));
        }
        return rootView;
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());

        String date = sharedPrefs.getString(
                getString(R.string.from),
                getString(R.string.Default));

        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(_URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Append query parameter and its value. For example, the `format=geojson`
        uriBuilder.appendQueryParameter("q", "culture");
        uriBuilder.appendQueryParameter("from-date", date);
        uriBuilder.appendQueryParameter("api-key", "d15e11ef-a19c-4ee9-9a72-26aba536ec62");
        Log.e("Games", "games URL: " + uriBuilder.toString());
        return new NewsLoader(getContext(), uriBuilder.toString());
    }


    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {

        for (int i = 0; i < adapter.getCount(); i++) {
            Log.e("ADAPTER CONTENT #" + i, adapter.getItem(i).getTitle());
        }
        adapter.clear();

        if (news != null && !news.isEmpty()) {
            adapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        adapter.clear();
    }

}

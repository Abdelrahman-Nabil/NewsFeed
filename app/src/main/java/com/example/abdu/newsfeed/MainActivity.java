package com.example.abdu.newsfeed;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewpager = findViewById(R.id.viewpager);

        FragmentAdapter adapter = new FragmentAdapter(MainActivity.this, getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewpager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewpager);


    }
}

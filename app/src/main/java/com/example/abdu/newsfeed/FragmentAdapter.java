package com.example.abdu.newsfeed;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {
    private Context context;

    FragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new CultureFragment();
        } else if (position == 1) {
            return new GamesFragment();
        } else if (position == 2) {
            return new MusicFragment();
        } else {
            return new SportFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.culture);
        } else if (position == 1) {
            return context.getString(R.string.games);
        } else if (position == 2) {
            return context.getString(R.string.music);
        } else {
            return context.getString(R.string.sports);
        }
    }
}

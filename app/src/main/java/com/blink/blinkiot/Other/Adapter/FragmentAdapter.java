package com.blink.blinkiot.Other.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Soft on 2016/6/23.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> list = null;

    public FragmentAdapter(FragmentManager fm , ArrayList<Fragment> list) {
        super(fm);
        this.list = list;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return list.size();
    }
}

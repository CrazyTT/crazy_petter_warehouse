package com.crazy.petter.warehouse.app.main.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/2/20.
 */

public class FragAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;
    private ArrayList<String> titleArrayList;

    public FragAdapter(FragmentManager fm) {
        super(fm);
    }

    public FragAdapter(FragmentManager fm, ArrayList<Fragment> fragments, ArrayList<String> titleArrayList) {
        super(fm);
        this.fragments = fragments;
        this.titleArrayList = titleArrayList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleArrayList.get(position);
    }
}

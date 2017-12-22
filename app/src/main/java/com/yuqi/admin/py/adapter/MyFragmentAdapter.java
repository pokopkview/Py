package com.yuqi.admin.py.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/6/5.
 */

public class MyFragmentAdapter extends FragmentPagerAdapter {
    List data;

    public MyFragmentAdapter(FragmentManager fm, List data) {
        super(fm);
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {
        return (Fragment) data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }
}

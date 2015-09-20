package com.coffee.messzay.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.coffee.messzay.MainFragment;

import java.util.ArrayList;

/**
 * Created by coffee on 2015/9/19.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<MainFragment> fragmentArr;

    public MainPagerAdapter(FragmentManager fm, ArrayList<MainFragment> fragmentArr) {
        super(fm);
        this.fragmentArr = fragmentArr;
    }

    @Override
    public int getCount() {
        return this.fragmentArr.size();
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragmentArr.get(position);
    }

    @Override
    public void destroyItem(View collection, int position, Object object) {
        View view = (View)object;
        ((ViewPager)collection).removeView(view);
        view = null;
    }

}

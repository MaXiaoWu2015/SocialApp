package com.example.maxiaowu.societyapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.google.common.base.Preconditions;

import java.util.ArrayList;

/**
 * Created by matingting on 2017/4/28.
 */

public class NetMusicViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment>  mFragments;
    private ArrayList<String>   mFragmentTabTitles;
    public NetMusicViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragments=new ArrayList<>();
        mFragmentTabTitles=new ArrayList<>();
    }

    public void addFragment( Fragment fragment,String tag){
        Preconditions.checkNotNull(fragment);
        Preconditions.checkNotNull(tag);
        mFragments.add(fragment);
        mFragmentTabTitles.add(tag);

    }
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {//必须要重写这个函数
        return mFragmentTabTitles.get(position);
    }
}

package com.example.maxiaowu.societyapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.google.common.base.Preconditions;

import java.util.ArrayList;

/**
 * Created by matingting on 2017/4/27.
 *
 * PagerAdapter
 *   ---FragmentPagerAdapter:Fragment比较稳定的并且个数少的,例如 a set of tabs;加载过的Fragment都会保存在内存中
 *   ---FragmentStatePagerAdapter:处理Fragment个数比较多的,当Fragment是不可以见的,Fragment被销毁(类似于listview的处理)
 *                                 ,只会保存Fragment的状态
 *
 */

public class MainContentViewPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> mFragments;
    public MainContentViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragments=new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    public void addFragment(Fragment fragment){
        mFragments.add(Preconditions.checkNotNull(fragment));
    }
}

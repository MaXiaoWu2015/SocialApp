package com.example.maxiaowu.societyapp.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by matingting on 2017/5/31.
 */

public abstract class RecycleAdapter extends PagerAdapter {
    @Override
    public int getCount() {
        return 0;
    }

    /**
     * 参数object是instantiateItem()的返回值
     * */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        return super.instantiateItem(container, position);
    }
}

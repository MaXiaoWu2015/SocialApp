package com.aqy.matingting.basiclibrary.base;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * Created by matingting on 2017/12/12.
 */

public class BaseFragment extends Fragment {

    protected Activity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }
}

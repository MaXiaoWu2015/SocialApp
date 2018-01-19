package com.aqy.matingting.basiclibrary.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by matingting on 2017/12/12.
 */

public class BaseFragment extends Fragment {

    protected AppCompatActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mActivity = (AppCompatActivity) getActivity();

    }
}

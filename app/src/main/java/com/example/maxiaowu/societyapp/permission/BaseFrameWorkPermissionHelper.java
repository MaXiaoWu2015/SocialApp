package com.example.maxiaowu.societyapp.permission;


import android.app.Fragment;
import android.app.FragmentManager;
import android.util.Log;

import com.example.maxiaowu.societyapp.permission.dialog.RationaleDialogFragment;

/**
 * Created by matingting on 2018/2/6.
 *
 * Activity 或 Fragment
 */

public abstract class BaseFrameWorkPermissionHelper<T> extends PermissionHelper<T> {
    private static final String TAG = "BaseFrameWorkPermission";

    public BaseFrameWorkPermissionHelper(T mHost) {
        super(mHost);
    }

    public abstract FragmentManager getFragmentManager();

    @Override
    public void showRequestPermissionRationale(String rationale, String positiviButtonText,
                                                  String negativeButtonText, int theme,
                                                  int requestCode, String[] perms) {
        FragmentManager fm = getFragmentManager();

        Fragment fragment = fm.findFragmentByTag(RationaleDialogFragment.TAG);
        if (fragment instanceof RationaleDialogFragment){
            Log.d(TAG, "Found existing fragment,not showing rationale");
            return;
        }

        RationaleDialogFragment.newInstance(rationale,
                positiviButtonText,negativeButtonText,theme,requestCode,perms)
                .showAllowingStateLoss(fm,RationaleDialogFragment.TAG);
    }
}

package com.example.maxiaowu.societyapp.permission;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.example.maxiaowu.societyapp.permission.dialog.RationaleDialogFragment;
import com.example.maxiaowu.societyapp.permission.dialog.RationaleDialogFragmentCompat;

/**
 * Created by matingting on 2018/2/6.
 *
 * Activity 或 Fragment
 */

public abstract class BaseSupportFrameWorkPermissionHelper<T> extends PermissionHelper<T> {
    private static final String TAG = "BaseFrameWorkPermission";

    public BaseSupportFrameWorkPermissionHelper(T mHost) {
        super(mHost);
    }

    public abstract FragmentManager getFragmentManager();

    @Override
    public void showRequestPermissionRationale(String rationale, String positiviButtonText,
                                                  String negativeButtonText, int theme,
                                                  int requestCode, String[] perms) {
        FragmentManager fm = getFragmentManager();

        Fragment fragment = fm.findFragmentByTag(RationaleDialogFragmentCompat.TAG);
        if (fragment instanceof RationaleDialogFragmentCompat){
            Log.d(TAG, "Found existing fragment,not showing rationale");
            return;
        }

        RationaleDialogFragmentCompat.newInstance(rationale,
                positiviButtonText,negativeButtonText,theme,requestCode,perms)
                .showAllowingStateLoss(fm,RationaleDialogFragment.TAG);
    }
}

package com.example.maxiaowu.societyapp.permission;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by matingting on 2018/2/6.
 */

class ActivityPermissionHelper extends BaseFrameWorkPermissionHelper<Activity> {
    public ActivityPermissionHelper(Activity host) {
        super(host);
    }

    @Override
    public FragmentManager getFragmentManager() {
        return mHost.getFragmentManager();
    }

    @Override
    public void directRequestPermissionRationale(int requestCode, String[] perms) {
        ActivityCompat.requestPermissions(mHost,perms,requestCode);
    }


    @Override
    public boolean shouldShowRequestPermissionRationale(String perm) {
        return ActivityCompat.shouldShowRequestPermissionRationale(mHost,perm);
    }
}

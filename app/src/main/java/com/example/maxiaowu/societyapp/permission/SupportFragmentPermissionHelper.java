package com.example.maxiaowu.societyapp.permission;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by matingting on 2018/2/6.
 */

class SupportFragmentPermissionHelper extends BaseSupportFrameWorkPermissionHelper<Fragment> {
    public SupportFragmentPermissionHelper(Fragment mHost) {
        super(mHost);
    }

    @Override
    public void directRequestPermissionRationale(int requestCode, String[] perms) {
        mHost.requestPermissions(perms,requestCode);
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(String perm) {
        return mHost.shouldShowRequestPermissionRationale(perm);
    }

    @Override
    public FragmentManager getFragmentManager() {
        return mHost.getChildFragmentManager();
    }
}

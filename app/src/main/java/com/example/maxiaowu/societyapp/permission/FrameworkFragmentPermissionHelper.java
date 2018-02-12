package com.example.maxiaowu.societyapp.permission;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by matingting on 2018/2/6.
 */

class FrameworkFragmentPermissionHelper extends BaseFrameWorkPermissionHelper<Fragment> {
    public FrameworkFragmentPermissionHelper(Fragment mHost) {
        super(mHost);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void directRequestPermissionRationale(int requestCode, String[] perms) {
        mHost.requestPermissions(perms,requestCode);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean shouldShowRequestPermissionRationale(String perm) {
        return mHost.shouldShowRequestPermissionRationale(perm);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public FragmentManager getFragmentManager() {
        return mHost.getChildFragmentManager();
    }
}

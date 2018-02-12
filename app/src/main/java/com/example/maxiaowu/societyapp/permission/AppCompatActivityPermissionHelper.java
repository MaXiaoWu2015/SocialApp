package com.example.maxiaowu.societyapp.permission;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by matingting on 2018/2/6.
 */

class AppCompatActivityPermissionHelper extends BaseSupportFrameWorkPermissionHelper<AppCompatActivity> {
    public AppCompatActivityPermissionHelper(AppCompatActivity host) {
        super(host);
    }

    @Override
    public FragmentManager getFragmentManager() {
        return mHost.getSupportFragmentManager();
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

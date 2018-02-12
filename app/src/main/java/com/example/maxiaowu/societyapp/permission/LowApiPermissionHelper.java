package com.example.maxiaowu.societyapp.permission;

import android.app.Activity;

/**
 * Created by matingting on 2018/2/6.
 */

class LowApiPermissionHelper<T> extends PermissionHelper<T> {
    public LowApiPermissionHelper(T host) {
        super(host);
    }

    @Override
    public void directRequestPermissionRationale(int requestCode, String[] perms) {
        throw new IllegalStateException("Should never be requesting permission on API < 23");
    }

    @Override
    public void showRequestPermissionRationale(String rationale, String positiviButtonText, String negativeButtonText, int theme, int requestCode, String[] perms) {
        throw new IllegalStateException("Should never be requesting permission on API < 23");
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(String perm) {
        return false;
    }
}

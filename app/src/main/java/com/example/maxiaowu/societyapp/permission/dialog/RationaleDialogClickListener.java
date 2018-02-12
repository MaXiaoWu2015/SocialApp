package com.example.maxiaowu.societyapp.permission.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;

import com.example.maxiaowu.societyapp.permission.PermissionHelper;

/**
 * Created by matingting on 2018/2/6.
 */

public class RationaleDialogClickListener implements Dialog.OnClickListener {

    private Object mHost;
    private int requestCode;
    private String[] perms;

    public RationaleDialogClickListener(Object mHost, int requestCode, String[] perms) {
        this.mHost = mHost;
        this.requestCode = requestCode;
        this.perms = perms;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == Dialog.BUTTON_POSITIVE){
            if (mHost instanceof Fragment){
                PermissionHelper.newInstance((Fragment)mHost).directRequestPermissionRationale(requestCode,perms);
            }else if (mHost instanceof android.support.v4.app.Fragment){
                PermissionHelper.newInstance((android.support.v4.app.Fragment)mHost).directRequestPermissionRationale(requestCode,perms);
            } else if (mHost instanceof Activity){
                PermissionHelper.newInstance((Activity) mHost).directRequestPermissionRationale(requestCode,perms);
            }else if (mHost instanceof AppCompatActivity){
                PermissionHelper.newInstance((AppCompatActivity)mHost).directRequestPermissionRationale(requestCode,perms);
            } else{
                throw new RuntimeException("Host must be an Activity or Fragment,because there is dialog to show!");
            }
        }else {
            notifyPermissionDenied();
        }


    }

    private void notifyPermissionDenied() {

    }
}

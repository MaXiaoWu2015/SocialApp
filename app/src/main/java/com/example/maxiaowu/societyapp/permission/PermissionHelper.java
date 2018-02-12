package com.example.maxiaowu.societyapp.permission;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by matingting on 2018/2/6.
 */

public abstract class PermissionHelper<T> {

    protected T mHost;


    public PermissionHelper(T mHost) {
        this.mHost = mHost;
    }

    private boolean shouldShowRationale(String... perms) {

        for (String value:perms){
//            if (ActivityCompat.shouldShowRequestPermissionRationale(mHost,value))
        }


        return false;
    }

    public T getmHost() {
        return mHost;
    }

    public static PermissionHelper newInstance(Activity host) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return new LowApiPermissionHelper(host);
        }

        if (host instanceof AppCompatActivity){
            return new AppCompatActivityPermissionHelper((AppCompatActivity)host);
        }else{
            return new ActivityPermissionHelper(host);
        }

    }

    public void requestPermissions(String rationale,String positiviButtonText,String negativeButtonText,
                                   @StyleRes int theme,int requestCode,String... perms){

        if (shouldShowRaitonale(perms)){

            showRequestPermissionRationale(rationale,positiviButtonText,negativeButtonText,theme,requestCode,perms);

        }else{

            directRequestPermissionRationale(requestCode,perms);
        }


    }


    private boolean shouldShowRaitonale(String... perms){
        for (String value :
                perms) {
            if (shouldShowRequestPermissionRationale(value)){
                return true;
            }
        }

        return false;
    }

    public abstract void directRequestPermissionRationale(int requestCode, String[] perms);

    public abstract void showRequestPermissionRationale(String rationale, String positiviButtonText, String negativeButtonText, int theme, int requestCode, String[] perms);

    public abstract boolean shouldShowRequestPermissionRationale(String perm);


    public static PermissionHelper newInstance(Fragment mHost) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return new LowApiPermissionHelper(mHost);
        }


        return  new SupportFragmentPermissionHelper(mHost);
    }

    public static PermissionHelper newInstance(android.app.Fragment mHost) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return new LowApiPermissionHelper(mHost);
        }


        return  new FrameworkFragmentPermissionHelper(mHost);
    }
}

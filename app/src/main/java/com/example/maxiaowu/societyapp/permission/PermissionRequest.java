package com.example.maxiaowu.societyapp.permission;

import android.app.Activity;
import android.text.TextUtils;

/**
 * Created by matingting on 2018/2/6.
 */

public class PermissionRequest {

    private PermissionHelper mHelper;
    private final String[] perms;
    private final int mRequestCode;
    private final String mRationale;
    private final String mPositiveButtonText;
    private final String mNegativeButtonText;
    private final int mTheme;


    private PermissionRequest(PermissionHelper mHelper, String[] perms, int mRequestCode, String mRationale, String mPositiveButtonText, String mNegativeButtonText, int mTheme) {
        this.mHelper = mHelper;
        this.perms = perms;
        this.mRequestCode = mRequestCode;
        this.mRationale = mRationale;
        this.mPositiveButtonText = mPositiveButtonText;
        this.mNegativeButtonText = mNegativeButtonText;
        this.mTheme = mTheme;
    }


    public PermissionHelper getmHelper() {
        return mHelper;
    }

    public String[] getPerms() {
        return perms;
    }

    public int getmRequestCode() {
        return mRequestCode;
    }

    public String getmRationale() {
        return mRationale;
    }

    public String getmPositiveButtonText() {
        return mPositiveButtonText;
    }

    public String getmNegativeButtonText() {
        return mNegativeButtonText;
    }

    public int getmTheme() {
        return mTheme;
    }

    public static class Builder{
        private final PermissionHelper mHelper;
        private Activity activity;
        private final int requestCode;
        private final String[] perms;
       private  String mRationale;
       private  String mPositiveButtonText;
       private  String mNegativeButtonText;
       private  int mTheme;

       public Builder(Activity activity, int requestCode, String[] perms) {
           this.mHelper = PermissionHelper.newInstance(activity);
           this.activity = activity;
           this.requestCode = requestCode;
           this.perms = perms;
       }

       public Builder setRationale(String mRationale) {
           this.mRationale = mRationale;
           return this;
       }

       public Builder setPositiveButtonText(String mPositiveButtonText) {
           this.mPositiveButtonText = mPositiveButtonText;
           return this;
       }

       public Builder setNegativeButtonText(String mNegativeButtonText) {
           this.mNegativeButtonText = mNegativeButtonText;
           return this;
       }

       public Builder setTheme(int mTheme) {
           this.mTheme = mTheme;
           return this;
       }

       public PermissionRequest build(){

           if (TextUtils.isEmpty(mRationale)){
               mRationale = "this app may not work correctly without requested permission";
           }

           if (TextUtils.isEmpty(mPositiveButtonText)){
               mPositiveButtonText = "OK";
           }

           if (TextUtils.isEmpty(mNegativeButtonText)){
               mNegativeButtonText = "Cancel";
           }

           return new PermissionRequest(mHelper, perms,
                   requestCode,mRationale,mPositiveButtonText,mNegativeButtonText,mTheme);
       }
   }




}

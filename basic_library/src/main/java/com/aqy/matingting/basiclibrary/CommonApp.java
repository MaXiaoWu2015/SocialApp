package com.aqy.matingting.basiclibrary;

import android.app.Application;

/**
 * Created by matingting on 2017/12/12.
 */

public class CommonApp {

    public static Application mApplication;

    public static void setApplication(Application mApplication) {
        CommonApp.mApplication = mApplication;
    }

    public static Application getApplicationContext() {
        return mApplication;
    }
}

package com.example.maxiaowu.societyapp;

import android.app.Application;

import com.aqy.matingting.basiclibrary.CommonApp;
import com.facebook.drawee.backends.pipeline.Fresco;

import butterknife.ButterKnife;

/**
 * Created by matingting on 2017/5/2.
 */

public class App extends Application {



    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        CommonApp.setApplication(this);
    }
}

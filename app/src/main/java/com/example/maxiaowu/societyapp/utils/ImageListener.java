package com.example.maxiaowu.societyapp.utils;

import android.graphics.Bitmap;

/**
 * Created by matingting on 2018/3/2.
 */

interface ImageListener {

    void onResponse(Bitmap bitmap, String msg);
    void onError(int code);

}

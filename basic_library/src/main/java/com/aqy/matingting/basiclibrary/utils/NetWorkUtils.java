package com.aqy.matingting.basiclibrary.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by matingting on 2017/12/12.
 */

public class NetWorkUtils {

    public static boolean isConnectInternet(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo.isAvailable();
    }

}

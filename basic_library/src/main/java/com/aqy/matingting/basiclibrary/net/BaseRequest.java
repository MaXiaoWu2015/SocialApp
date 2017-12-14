package com.aqy.matingting.basiclibrary.net;

import android.app.Activity;
import android.content.Context;
import com.aqy.matingting.basiclibrary.CommonApp;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by matingting on 2017/11/20.
 */

public class BaseRequest {

    private static final OkHttpClient mOkHttpClient = new OkHttpClient();

    static {
        File sdcache = CommonApp.getApplicationContext().getCacheDir();
        Cache cache = new Cache(sdcache,1024*1024*30);
        mOkHttpClient.setCache(cache);
        mOkHttpClient.setConnectTimeout(1, TimeUnit.MINUTES);
        mOkHttpClient.setReadTimeout(1,TimeUnit.MINUTES);

    }

    public static void sendRequest(Context context,Request.Builder builder,boolean forceCache,IHttpCallback callback){

        if (forceCache){
            //FIXME:1.应该是指定request从缓存中获取结果还是从服务器端请求
            builder.cacheControl(CacheControl.FORCE_CACHE);
        }
        builder.addHeader("user-agent","Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)");
        Request request = builder.build();
        try {
            Response response = mOkHttpClient.newCall(request).execute();
          if (!isActivityFinishing(context)){
              if (response.isSuccessful()){
                  callback.onResponse(response.body().string());
              }else{
                  callback.onErrorResponse(response.message());
              }
          }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static boolean isActivityFinishing(Context context) {
        if (context == null){
            return false;
        }
        if (context instanceof Activity){
            if (((Activity)context).isFinishing()){
                return true;
            }
        }
        return false;
    }
}

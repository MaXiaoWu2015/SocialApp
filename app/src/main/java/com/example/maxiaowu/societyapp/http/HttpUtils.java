package com.example.maxiaowu.societyapp.http;

import android.content.Context;

import com.aqy.matingting.basiclibrary.net.BaseRequest;
import com.aqy.matingting.basiclibrary.net.IHttpCallback;
import com.squareup.okhttp.Request;

import java.util.Objects;

/**
 * Created by matingting on 2017/11/16.
 */

public class HttpUtils {

    /***
     * 获取{@link com.example.maxiaowu.societyapp.fragment.NewMusicFragment}页面的轮播图
     *
     */
    public static Object getRecommendedSliderPics(String url,Context context,int num){
        return null;
    }

    public static void getRecomendedContentList(String url, Context context, boolean isFromCache, IHttpCallback callback){

        Request.Builder builder = new Request.Builder()
                .url(url);
        BaseRequest.sendRequest(context,builder,isFromCache,callback);
    }

}

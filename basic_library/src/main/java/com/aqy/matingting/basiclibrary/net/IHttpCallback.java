package com.aqy.matingting.basiclibrary.net;

/**
 * Created by matingting on 2017/12/12.
 */

public interface IHttpCallback {
    void onResponse(String response);
    void onErrorResponse(String errorMsg);
}

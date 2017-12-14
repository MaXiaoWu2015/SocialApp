package com.aqy.matingting.basiclibrary.net;

/**
 * Created by matingting on 2017/11/20.
 */

public class ReponseEntity<T> {
    private int code;

    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}

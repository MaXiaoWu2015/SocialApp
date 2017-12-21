package com.example.maxiaowu.societyapp.entity;

import android.text.TextUtils;

import com.aqy.matingting.basiclibrary.utils.GsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by matingting on 2017/12/12.
 */

public class RecommendEntity {

    private ArrayList<RecommendSongListEntity> songListEntities = new ArrayList<>();
    private ArrayList<RecommendNewAlbumEntity> newAlbumEntities = new ArrayList<>();
    private ArrayList<RecommendRadioEntity> radioEntities = new ArrayList<>();


    public  RecommendEntity parse(String json){

        try {
            //getXXX 和 optXXX区别:如果没有对应的name,getXXX会抛JSONException
            //                    optXXX会返回null

            JSONObject object = new JSONObject(json);

            JSONObject result = object.optJSONObject("result");

            if (result != null){
                JSONArray songList = result.optJSONObject("diy").optJSONArray("result");
                JSONArray newAlbum = result.optJSONObject("mix_1").optJSONArray("result");
                JSONArray radioArray = result.optJSONObject("radio").optJSONArray("result");

                iterateJsonArray(songList,songListEntities,RecommendSongListEntity.class);
                iterateJsonArray(newAlbum,newAlbumEntities,RecommendNewAlbumEntity.class);
                iterateJsonArray(radioArray,radioEntities,RecommendRadioEntity.class);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }


        return this;
    }

    //不能再函数中对list进行初始化,因为Java引用的传递知识将地址赋值给了函数的参数,在函数中初始化只是将参数的引用指向了对象,并未修改传入的引用的值
    private <T> void  iterateJsonArray(JSONArray jsonArray,ArrayList<T> list,Class clazz) throws JSONException {
        if (jsonArray!=null){
//            list = new ArrayList();
            for (int i=0;i<jsonArray.length();i++){
                list.add((T) GsonUtils.string2Object(jsonArray.getString(i),clazz));
            }
        }
    }

    public ArrayList<RecommendSongListEntity> getSongListEntities() {
        return songListEntities;
    }

    public ArrayList<RecommendNewAlbumEntity> getNewAlbumEntities() {
        return newAlbumEntities;
    }

    public ArrayList<RecommendRadioEntity> getRadioEntities() {
        return radioEntities;
    }
}

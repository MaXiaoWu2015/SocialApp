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

    private ArrayList<RecommendSongListEntity> songListEntities;
    private ArrayList<RecommendNewAlbumEntity> newAlbumEntities;
    private ArrayList<RecommendRadioEntity> radioEntities;


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

    private void iterateJsonArray(JSONArray jsonArray,ArrayList list,Class clazz) throws JSONException {
        if (jsonArray!=null){
            list = new ArrayList();
            for (int i=0;i<jsonArray.length();i++){
                list.add(GsonUtils.string2Object(jsonArray.getString(i),clazz));
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

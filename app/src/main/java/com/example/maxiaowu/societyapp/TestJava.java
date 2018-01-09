package com.example.maxiaowu.societyapp;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by matingting on 2017/12/28.
 */

public class TestJava {

    public static void main(String[] args){

        ArrayList<String> list =new ArrayList<>(Arrays.asList("a","b","c","d"));

        SparseArray<String> sparseArray = new SparseArray<>(list.size());


        sparseArray.put(1,list.get(1));

        sparseArray.put(3,list.get(3));



        System.out.println(sparseArray);



    }

}

package com.example.maxiaowu.societyapp.widgets;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

public class SmoothScrollLayoutManager extends LinearLayoutManager {

       public SmoothScrollLayoutManager(Context context) {
           super(context);
       }

       @Override
       public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
           LinearSmoothScroller smoothScroller = new LinearSmoothScroller(recyclerView.getContext()){
               @Override
               protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                   return 100f / displayMetrics.densityDpi;
               }
           };

           smoothScroller.setTargetPosition(position);
           startSmoothScroll(smoothScroller);
       }
   }
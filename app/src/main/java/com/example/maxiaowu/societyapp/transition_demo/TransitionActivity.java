package com.example.maxiaowu.societyapp.transition_demo;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.maxiaowu.societyapp.R;

import java.util.Arrays;
import java.util.List;

public class TransitionActivity extends AppCompatActivity {

    private List<Sample> samples;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_transition);
        setupWindowAnimation();
        setToolbar();
        setDemoList();
        setLayout();


        ProgressBar bar = findViewById(R.id.clipProgressbar);
//        bar.getProgressDrawable().setLevel(60/100*10000);
        bar.setProgress(60);//TODO:研究Progress源码,了解progress的实现与ClipDrawable的关系

        ((ImageView)findViewById(R.id.levelImage)).getBackground().setLevel(100);
    }

    private void setDemoList() {
        samples = Arrays.asList(new Sample(ContextCompat.getColor(this,R.color.red),"Transitions")
        ,new Sample(ContextCompat.getColor(this,R.color.blue),"SharedElement")
                ,new Sample(ContextCompat.getColor(this,R.color.green),"View animations")
                ,new Sample(ContextCompat.getColor(this,R.color.yellow),"Circular Reveal Animation"));
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimation() {
        Slide slide = new Slide();
        slide.setDuration(500);
        slide.setSlideEdge(Gravity.LEFT);
        getWindow().setReenterTransition(slide);
        getWindow().setExitTransition(slide);

    }

    private void setLayout() {

        RecyclerView recyclerView = findViewById(R.id.transition_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TransitionAdapter(this,samples));

    }


    class Sample {
        public int color;
        public String name;

        public Sample(int color, String name) {
            this.color = color;
            this.name = name;
        }
    }
}

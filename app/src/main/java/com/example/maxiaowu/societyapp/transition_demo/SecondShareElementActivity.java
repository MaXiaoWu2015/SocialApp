package com.example.maxiaowu.societyapp.transition_demo;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.maxiaowu.societyapp.R;

public class SecondShareElementActivity extends AppCompatActivity {



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_second_share_element);
        setUpWindowTransition();

        Slide slide = new Slide();
        slide.setSlideEdge(Gravity.LEFT);
        slide.setDuration(500);
        SecondShareElementFragment fragment = new SecondShareElementFragment();
        fragment.setReenterTransition(slide);
        fragment.setExitTransition(slide);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_container,fragment)
                .commit();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setUpWindowTransition() {

        Slide slide = new Slide();
        slide.setSlideEdge(Gravity.LEFT);
        slide.setDuration(500);
        getWindow().setEnterTransition(slide);
    }


}

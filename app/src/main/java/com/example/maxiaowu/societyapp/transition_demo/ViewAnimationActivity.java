package com.example.maxiaowu.societyapp.transition_demo;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.maxiaowu.societyapp.R;

public class ViewAnimationActivity extends AppCompatActivity {

    ImageView imageView;
    boolean sizeChanged;
    ViewGroup rootView;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animation);
        rootView = findViewById(R.id.rootView);

        Fade fade = new Fade();
        fade.setDuration(500);
        getWindow().setEnterTransition(fade);
        imageView = findViewById(R.id.ball);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void changeBound(View view) {
        android.support.transition.TransitionManager.beginDelayedTransition(rootView);
        if (sizeChanged){
            findViewById(R.id.text).setVisibility(View.GONE);
        }else {
            findViewById(R.id.text).setVisibility(View.VISIBLE);
        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        if (sizeChanged){
            params.width /=2;
            params.height /=2;
        }else {
            params.width *=2;
            params.height *=2;
        }

        sizeChanged = !sizeChanged;
        imageView.setLayoutParams(params);


    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void transitionToScenesActivity(View view) {

        Pair<View,String>[] pair = TransitionHelper.createTransitionParticipants(this,true);

        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this,pair);

        startActivity(new Intent(this,ScenesActivity.class),optionsCompat.toBundle());

    }
}

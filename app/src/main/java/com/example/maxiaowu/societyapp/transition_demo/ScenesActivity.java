package com.example.maxiaowu.societyapp.transition_demo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.example.maxiaowu.societyapp.R;

import java.util.ArrayList;

public class ScenesActivity extends AppCompatActivity {
    private static final String TAG = "ScenesActivity";
    private ViewGroup rootView;
    private ViewGroup sceneRoot;
    private Scene scene1;
    private Scene scene2;
    private Scene scene0;

    ArrayList<View> buttons = new ArrayList<>();

    @Override
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenes);
        Slide slide = new Slide();
        slide.setDuration(500);
        slide.setSlideEdge(Gravity.BOTTOM);
        getWindow().setEnterTransition(slide);

        getWindow().getEnterTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                TransitionManager.go(scene0);
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });



        rootView = findViewById(R.id.rootView);
        sceneRoot = findViewById(R.id.scene_root);

        scene0 = Scene.getSceneForLayout(sceneRoot,R.layout.scene0,this);
        scene1 = Scene.getSceneForLayout(sceneRoot,R.layout.scene,this);
        scene2 = Scene.getSceneForLayout(sceneRoot,R.layout.scene,this);


        buttons.add(findViewById(R.id.button));
        buttons.add(findViewById(R.id.button2));
        buttons.add(findViewById(R.id.button3));
        buttons.add(findViewById(R.id.button4));

        scene0.setEnterAction(new Runnable() {
            @Override
            public void run() {
                for (int i =0;i<buttons.size();i++){
                        View view = buttons.get(i);
                    view.animate()
                                .setStartDelay(i*100)
                                .scaleX(1).scaleY(1).setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                super.onAnimationStart(animation);
                            }
                        });
                    }

            }
        });

        scene0.setExitAction(new Runnable() {
            @Override
            public void run() {
                TransitionManager.beginDelayedTransition(rootView);
                View title = scene0.getSceneRoot().findViewById(R.id.title);
                title.setScaleX(0);
                title.setScaleY(0);
            }
        });



    }




    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void sceneOne(View view) {
        TransitionManager.go(scene1,new ChangeBounds());
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void sceneTwo(View view) {
        TransitionManager.go(scene2, TransitionInflater.from(this).inflateTransition(R.transition.slide_changebound));
    }

    public void sceneThree(View view) {

    }

    public void sceneFour(View view) {
    }


}

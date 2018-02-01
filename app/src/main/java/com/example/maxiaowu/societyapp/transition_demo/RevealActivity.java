package com.example.maxiaowu.societyapp.transition_demo;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import com.example.maxiaowu.societyapp.R;

public class RevealActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private Interpolator interpolator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal);
        toolbar = findViewById(R.id.toolbar);
        interpolator = AnimationUtils.loadInterpolator(this,android.R.interpolator.linear_out_slow_in);
        setupEnterWindowTransition();
        setupExitWindowTransition();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupExitWindowTransition() {
        final Fade fade = new Fade();
        fade.setDuration(300);
        fade.setStartDelay(300);
        getWindow().setReturnTransition(fade);

        fade.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                fade.removeListener(this);
                animateRevealHide(findViewById(R.id.contentView));
                animationButtonOut();
            }

            @Override
            public void onTransitionEnd(Transition transition) {

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



    }

    private void animationButtonOut() {
        ViewGroup buttonGroup = findViewById(R.id.buttonGroup);
        for (int i=0;i<buttonGroup.getChildCount();i++){
            buttonGroup.getChildAt(i)
                    .animate()
                    .setStartDelay(i)
                    .setInterpolator(interpolator)
                    .scaleX(0)
                    .scaleY(0);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupEnterWindowTransition() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.changebounds_wiht_arcmotion);
        getWindow().setSharedElementEnterTransition(transition);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                findViewById(R.id.share_view).setVisibility(View.GONE);//没有效果,无法隐藏改View
               //TODO:实现Toolbar reveal效果
                animateRevealShow(toolbar);

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



    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animateRevealShow(Toolbar toolbar) {

        int cx = (toolbar.getLeft() + toolbar.getRight())/2;
        int cy = (toolbar.getTop() + toolbar.getBottom())/2;

        float finalRadius = Math.max(toolbar.getWidth(),toolbar.getHeight());
        //通过clip circle实现的,不断绘制半径为[0,finalRadius]的圆,与所给View裁剪得到的reveal效果
        Animator animator =ViewAnimationUtils.createCircularReveal(toolbar,cx,cy,0,finalRadius);
        animator.setDuration(500);
        animator.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void revealGreen(View view) {

        View contentView = findViewById(R.id.contentView);
        contentView.setBackgroundColor(getResources().getColor(R.color.green));
        int cx = (contentView.getLeft()+contentView.getRight())/2;
        int cy = (contentView.getTop()+contentView.getBottom())/2;

        float finalRadius = Math.max(contentView.getWidth(),contentView.getHeight());

        Animator animator = ViewAnimationUtils.createCircularReveal(contentView,cx,cy,0,finalRadius);

        animator.setDuration(500);
        animator.start();

    }





    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void animateRevealHide(View view){
        int cx = (view.getLeft()+view.getRight())/2;
        int cy = (view.getTop()+view.getBottom())/2;

        float finalRadius = Math.max(view.getWidth(),view.getHeight());

        Animator animator = ViewAnimationUtils.createCircularReveal(view,cx,cy,finalRadius,0);
        animator.setDuration(500);
        animator.start();
    }
}

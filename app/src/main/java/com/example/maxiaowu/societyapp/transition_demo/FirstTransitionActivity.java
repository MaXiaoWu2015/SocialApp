package com.example.maxiaowu.societyapp.transition_demo;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.view.Window;

import com.example.maxiaowu.societyapp.R;

public class FirstTransitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_first_transition);
        setUpWindowTransition();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setUpWindowTransition() {
        Fade fade = new Fade();
        fade.setDuration(500);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);
    }

    public void explode(View view) {

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void exitOverridingReturnTransition(View view) {
        finishAfterTransition();
    }

    public void slide(View view) {

    }

    public void exit(View view) {
    }
}

package com.example.maxiaowu.societyapp.transition_demo;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.transition.ChangeBounds;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.maxiaowu.societyapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondShareElementFragment extends Fragment {
    private ImageView ballView;
    private  OverlapFragment fragment;

    public SecondShareElementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_second_share_element, container, false);
        ballView = rootView.findViewById(R.id.ball_view);
        ViewCompat.setTransitionName(ballView,"blueBall");
        fragment = new OverlapFragment();

        rootView.findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OverlapTransitionWithFalse();
            }
        });


        return rootView;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void OverlapTransitionWithFalse() {
        Slide slide = new Slide();
        slide.setSlideEdge(Gravity.LEFT);
        slide.setDuration(500);
        fragment.setEnterTransition(slide);
        fragment.setSharedElementEnterTransition(new ChangeBounds());
        fragment.setAllowEnterTransitionOverlap(true);//下一个fragment是在Transition完成后进入还是transition的同时进入  true:同时; false:先后
        getFragmentManager().beginTransaction()
                .replace(R.id.layout_container,fragment)
                .addToBackStack(null)
                .addSharedElement(ballView,"blueBall")
                .commit();

    }

    public void OverlapTransitionWithTrue(View view) {
    }
}

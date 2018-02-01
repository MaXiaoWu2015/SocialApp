package com.example.maxiaowu.societyapp.transition_demo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.maxiaowu.societyapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OverlapFragment extends Fragment {


    public OverlapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_overlap, container, false);
        ImageView ball = rootView.findViewById(R.id.blueBall);
        ViewCompat.setTransitionName(ball,"blueBall");
        return rootView;
    }

}

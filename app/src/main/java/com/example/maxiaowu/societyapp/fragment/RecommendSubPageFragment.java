package com.example.maxiaowu.societyapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maxiaowu.societyapp.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecommendSubPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecommendSubPageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String HEADER_ICON_URL = "headerIcon";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String headerIconUrl;

    private SimpleDraweeView recommend_sub_page_header_icon;


    public RecommendSubPageFragment() {
        // Required empty public constructor
    }


    public static RecommendSubPageFragment newInstance(String headerIconUrl) {
        RecommendSubPageFragment fragment = new RecommendSubPageFragment();
        Bundle args = new Bundle();
        args.putString(HEADER_ICON_URL, headerIconUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            headerIconUrl = getArguments().getString(HEADER_ICON_URL);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_recommend_sub_page, container, false);
//        recommend_sub_page_header_icon = (SimpleDraweeView) rootView.findViewById(R.id.recommend_sub_page_header_icon);
        ViewCompat.setTransitionName(recommend_sub_page_header_icon,"recommend_songlist_poster_fragment0");

        return rootView;
    }

}

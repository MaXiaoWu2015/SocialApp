package com.example.maxiaowu.societyapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maxiaowu.societyapp.R;

/**
 * Created by matingting on 2017/4/28.
 * 新曲
 */

public class NewMusicFragment extends Fragment {
    public static NewMusicFragment newInstance(){
        return new NewMusicFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.new_music_frag,container,true);
        return view;
    }
}

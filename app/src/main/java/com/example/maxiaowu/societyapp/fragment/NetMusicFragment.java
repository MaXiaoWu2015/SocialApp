package com.example.maxiaowu.societyapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maxiaowu.societyapp.R;
import com.example.maxiaowu.societyapp.adapter.NetMusicViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by matingting on 2017/4/27.
 */

public class NetMusicFragment extends Fragment {
    public  @BindView(R.id.tl_net_music_tabs) TabLayout mTabLayout;
    public @BindView(R.id.vp_net_music) ViewPager mViewPager;
    public static NetMusicFragment newInstance(){
        return new NetMusicFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.net_music_fragment_layout,container,false);
        ButterKnife.bind(this,view);

        mViewPager = (ViewPager) view.findViewById(R.id.vp_net_music);
        mTabLayout = (TabLayout) view.findViewById(R.id.tl_net_music_tabs);

        initView();
        return view;
    }

    private void initView() {
        NetMusicViewPagerAdapter adapter=new NetMusicViewPagerAdapter(getFragmentManager());
        adapter.addFragment(NewMusicFragment.newInstance(),getString(R.string.net_music_new_music));
        adapter.addFragment(SongListFragment.newInstance(),getString(R.string.net_music_song_list));
        adapter.addFragment(BillBoardHotFragment.newInstance(),getString(R.string.net_music_billboard_hot));
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}

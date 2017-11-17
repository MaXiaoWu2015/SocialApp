package com.example.maxiaowu.societyapp.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.bilibili.magicasakura.widgets.TintButton;
import com.bilibili.magicasakura.widgets.TintTextView;
import com.example.maxiaowu.societyapp.R;
import com.example.maxiaowu.societyapp.http.HttpUtils;
import com.example.maxiaowu.societyapp.widgets.autoscrollviewpager.InfiniteIndicatorLayout;

import butterknife.BindView;

/**
 * Created by matingting on 2017/4/28.
 * 新曲
 */

public class NewMusicFragment extends Fragment implements View.OnClickListener{

    @BindView(R.id.loop_pic_view)
    public InfiniteIndicatorLayout loop_pic_view;

    @BindView(R.id.tb_private_FM)
    public TintButton tb_private_FM;

    @BindView(R.id.tt_recommend_count)
    public TintButton tt_recommend_count;

    @BindView(R.id.fl_recommend_daily)
    public FrameLayout fl_recommend_daily;

    @BindView(R.id.tb_new_music_rank)
    public TintButton tb_new_music_rank;

    @BindView(R.id.ll_recommend_content)
    public LinearLayout ll_recommend_content;

    @BindView(R.id.change_item_position)
    public TintTextView change_item_position;

    public static NewMusicFragment newInstance(){
        return new NewMusicFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.new_music_frag,container,false);//FIXME:为什么最后一个参数不能设置为true
        initRecommendContent();

        return view;
    }

    private void initRecommendContent() {
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... params) {

                HttpUtils.getRecomendedContentList("",getActivity());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tb_private_FM:
                break;
            case R.id.fl_recommend_daily:
                break;
            case R.id.tb_new_music_rank:
                break;
            case R.id.change_item_position:
                break;
        }
    }
}

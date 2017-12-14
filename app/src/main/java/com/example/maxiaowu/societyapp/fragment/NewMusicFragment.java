package com.example.maxiaowu.societyapp.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aqy.matingting.basiclibrary.base.BaseFragment;
import com.aqy.matingting.basiclibrary.net.IHttpCallback;
import com.aqy.matingting.basiclibrary.net.Urls;
import com.aqy.matingting.basiclibrary.utils.CollectionUtils;
import com.aqy.matingting.basiclibrary.utils.Cons;
import com.aqy.matingting.basiclibrary.utils.NetWorkUtils;
import com.bilibili.magicasakura.widgets.TintButton;
import com.bilibili.magicasakura.widgets.TintTextView;
import com.example.maxiaowu.societyapp.R;
import com.example.maxiaowu.societyapp.entity.RecommendBaseEntity;
import com.example.maxiaowu.societyapp.entity.RecommendEntity;
import com.example.maxiaowu.societyapp.http.HttpUtils;
import com.example.maxiaowu.societyapp.widgets.autoscrollviewpager.InfiniteIndicatorLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by matingting on 2017/4/28.
 * 新曲
 */

public class NewMusicFragment extends BaseFragment implements View.OnClickListener{

    private View rootView;

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

    private View recommendSongListLayout;

    private View recommendNewAlbumLayout;

    private View recommendRadioLayout;

    private RecommendEntity recommendEntity;

    public static NewMusicFragment newInstance(){
        return new NewMusicFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.new_music_frag,container,false);//FIXME:为什么最后一个参数不能设置为true
        ButterKnife.bind(rootView);
        initView();

        initRecommendContent();

        return rootView;
    }

    private void initView() {

    }

    @SuppressLint("StaticFieldLeak")
    private void initRecommendContent() {
        //TODO:把AsyncTask换成RxAndroid
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... params) {


                boolean isFromCache = !NetWorkUtils.isConnectInternet(mActivity);

                HttpUtils.getRecomendedContentList(Urls.GET_RECOMMENDED_LIST, mActivity
                        , isFromCache, new IHttpCallback() {
                            @Override
                            public void onResponse(String response) {
                                if (TextUtils.isEmpty(response)){
                                    return ;
                                }
                                 recommendEntity = new RecommendEntity();
                                recommendEntity.parse(response);



                            }

                            @Override
                            public void onErrorResponse(String errorMsg) {



                            }
                        });

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                updateRecommendUI();
            }
        }.execute();
    }

    private void updateRecommendUI() {
        if (recommendEntity!=null){
            if (!CollectionUtils.isEmpty(recommendEntity.getSongListEntities())){
                recommendSongListLayout = LayoutInflater.from(mActivity).inflate(R.layout.recommend_list_item,ll_recommend_content);
                RecommendHolder songListHolder = new RecommendHolder(recommendSongListLayout);
                songListHolder.setData2View(Cons.SONG_LIST,);
            }

            recommendNewAlbumLayout = LayoutInflater.from(mActivity).inflate(R.layout.recommend_list_item,ll_recommend_content);
            recommendRadioLayout = LayoutInflater.from(mActivity).inflate(R.layout.recommend_list_item,ll_recommend_content);
        }



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

    class RecommendHolder{

        @BindView(R.id.tv_recommend_title)
        TextView recommendTitle;

        @BindView(R.id.recommend_more)
        TextView recommendMore;

        @BindView(R.id.rv_recommend_list)
        RecyclerView recommendList;

        public RecommendHolder(View itemView) {
            ButterKnife.bind(itemView);
        }

        public void setData2View(String title,int type, RecyclerView.Adapter adapter){
            recommendTitle.setText(title);
            if (type == Cons.SONG_LIST){
                recommendMore.setVisibility(View.VISIBLE);
            }
            recommendList.setAdapter(adapter);
        }


    }

}

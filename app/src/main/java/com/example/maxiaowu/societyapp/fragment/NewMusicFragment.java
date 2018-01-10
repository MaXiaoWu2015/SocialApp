package com.example.maxiaowu.societyapp.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aqy.matingting.basiclibrary.base.BaseFragment;
import com.aqy.matingting.basiclibrary.net.IHttpCallback;
import com.aqy.matingting.basiclibrary.net.Urls;
import com.aqy.matingting.basiclibrary.ui.GridSpacingItemDecoration;
import com.aqy.matingting.basiclibrary.utils.CollectionUtils;
import com.aqy.matingting.basiclibrary.utils.Cons;
import com.aqy.matingting.basiclibrary.utils.NetWorkUtils;
import com.bilibili.magicasakura.widgets.TintButton;
import com.bilibili.magicasakura.widgets.TintImageView;
import com.bilibili.magicasakura.widgets.TintTextView;
import com.example.maxiaowu.societyapp.R;
import com.example.maxiaowu.societyapp.adapter.RecommendNewAlbumAdapter;
import com.example.maxiaowu.societyapp.adapter.RecommendRadioAdapter;
import com.example.maxiaowu.societyapp.adapter.RecommendSongListAdapter;
import com.example.maxiaowu.societyapp.entity.RecommendEntity;
import com.example.maxiaowu.societyapp.http.HttpUtils;
import com.example.maxiaowu.societyapp.widgets.autoscrollviewpager.InfiniteIndicatorLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by matingting on 2017/4/28.
 *
 */

public class NewMusicFragment extends BaseFragment implements View.OnClickListener{

    private View rootView;

    @BindView(R.id.loop_pic_view)
    public InfiniteIndicatorLayout loop_pic_view;

    @BindView(R.id.tb_private_FM)
    public TintButton tb_private_FM;

    @BindView(R.id.tt_recommend_count)
    public TintTextView tt_recommend_count;

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
        //FIXED:为什么最后一个参数不能设置为true:如果设置为true,那么返回的就是container,container是ViewPager,所以要设置为false
        rootView=inflater.inflate(R.layout.new_music_frag,container,false);
        ButterKnife.bind(rootView);
        initView();

        initRecommendContent();

        return rootView;
    }

    private void initView() {
        //TODO:因为ButterKnife在AS3.0不能使用,所以暂时先通过老方法获取
        loop_pic_view = (InfiniteIndicatorLayout) rootView.findViewById(R.id.loop_pic_view);
        tb_private_FM = (TintButton) rootView.findViewById(R.id.tb_private_FM);
        tt_recommend_count = (TintTextView) rootView.findViewById(R.id.tt_recommend_count);
        fl_recommend_daily = (FrameLayout) rootView.findViewById(R.id.fl_recommend_daily);
        tb_new_music_rank = (TintButton) rootView.findViewById(R.id.tb_new_music_rank);
        change_item_position = (TintTextView) rootView.findViewById(R.id.change_item_position);
        ll_recommend_content = (LinearLayout) rootView.findViewById(R.id.ll_recommend_content);
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
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.topMargin = 30;//1080p 1dp = 3px

            if (!CollectionUtils.isEmpty(recommendEntity.getSongListEntities())){
                recommendSongListLayout = LayoutInflater.from(mActivity).inflate(R.layout.recommend_list_item,null);
//                ((ViewGroup)ll_recommend_content.getParent()).removeView(recommendSongListLayout);
                //Fixed:draw()时为什么不能调用addView or removeView;跟addView调用requestLayout()有什么关系;requestlayout与invalidate有什么区别
                ll_recommend_content.addView(recommendSongListLayout,0);
                RecommendHolder songListHolder = new RecommendHolder(recommendSongListLayout);
                songListHolder.setData2View(R.string.recommend_song_list_title,Cons.SONG_LIST,new RecommendSongListAdapter(mActivity,recommendEntity.getSongListEntities()));
            }

            if (!CollectionUtils.isEmpty(recommendEntity.getNewAlbumEntities())){
                recommendNewAlbumLayout = LayoutInflater.from(mActivity).inflate(R.layout.recommend_list_item,null);
                ll_recommend_content.addView(recommendNewAlbumLayout,1);
                recommendNewAlbumLayout.setLayoutParams(params);
                RecommendHolder newAlbumHolder = new RecommendHolder(recommendNewAlbumLayout);
                newAlbumHolder.setData2View(R.string.recommend_new_album_title,Cons.NEW_ALBUM,new RecommendNewAlbumAdapter(mActivity,recommendEntity.getNewAlbumEntities()));
            }

            if (!CollectionUtils.isEmpty(recommendEntity.getRadioEntities())){
                recommendRadioLayout = LayoutInflater.from(mActivity).inflate(R.layout.recommend_list_item,null);
                ll_recommend_content.addView(recommendRadioLayout,2);
                recommendRadioLayout.setLayoutParams(params);
                RecommendHolder radioHolder = new RecommendHolder(recommendRadioLayout);
                radioHolder.setData2View(R.string.recommend_radio_title,Cons.RADIO,new RecommendRadioAdapter(mActivity,recommendEntity.getRadioEntities()));
            }

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

        TintImageView tintImageView;

        @BindView(R.id.tv_recommend_title)
        TextView recommendTitle;

        @BindView(R.id.recommend_more)
        TextView recommendMore;

        @BindView(R.id.rv_recommend_list)
        RecyclerView recommendList;

        public RecommendHolder(View itemView) {
//            ButterKnife.bind(itemView);

            recommendTitle = (TextView) itemView.findViewById(R.id.tv_recommend_title);
            recommendMore = (TextView) itemView.findViewById(R.id.recommend_more);
            recommendList = (RecyclerView) itemView.findViewById(R.id.rv_recommend_list);
            tintImageView = (TintImageView) itemView.findViewById(R.id.ti_recommend_icon);

        }

        public void setData2View(@StringRes int titleId, int type, RecyclerView.Adapter adapter){
            recommendTitle.setText(mActivity.getString(titleId));

            switch (type){
                case Cons.SONG_LIST:
                    recommendMore.setVisibility(View.VISIBLE);
                    tintImageView.setBackgroundResource(R.mipmap.recommend_playlist);
                    break;
                case Cons.NEW_ALBUM:
                    tintImageView.setBackgroundResource(R.mipmap.recommend_newest);
                    break;
                case Cons.RADIO:
                    tintImageView.setBackgroundResource(R.mipmap.recommend_radio);
                    break;
            }


            //之前item布局的高度设置了match_parent,出现了一页只显示一行的情况,将高度改为wrap_content就可以了
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity,3);
            recommendList.setLayoutManager(gridLayoutManager);
            recommendList.addItemDecoration(new GridSpacingItemDecoration(3,20,true,true));
            recommendList.setAdapter(adapter);

        }


    }

}

package com.example.maxiaowu.societyapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maxiaowu.societyapp.R;

/**
 * Created by maxiaowu on 2018/1/28.
 */

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.PlayListItemViewHolder> {
    private Context mContext;

    public PlayListAdapter(Context context){
        mContext = context;
    }

    @Override
    public PlayListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.play_list_item,parent,false);

        return new PlayListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlayListItemViewHolder holder, int position) {
        holder.song_name.setText("歌名"+position);
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class PlayListItemViewHolder extends RecyclerView.ViewHolder{

        public TextView song_name;

        public PlayListItemViewHolder(View itemView) {
            super(itemView);

            song_name = itemView.findViewById(R.id.recommend_sub_page_song_name);
        }
    }
}

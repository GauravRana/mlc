package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.VideoPlayActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoListingAdapter extends RecyclerView.Adapter<VideoListingAdapter.ViewHolder>{


    Context mContext;
    public VideoListingAdapter(Context mContext) {
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.adapter_videos, parent, false);
        final VideoListingAdapter.ViewHolder vh = new VideoListingAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.card_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(mContext, VideoPlayActivity.class);
                //mIntent.putExtra("videoUrl","https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
                mIntent.putExtra("videoUrl","http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4");
                mContext.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        /** ButterKnife Code **/
        @BindView(R.id.iv_thumbnail)
        ImageView iv_thumbnail;
        @BindView(R.id.iv_play)
        ImageView iv_play;
        @BindView(R.id.tv_videoTitle)
        TextView tv_videoTitle;
        @BindView(R.id.card_main)
        CardView card_main;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

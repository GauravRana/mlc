package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.adapters.VideoListingAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class VideoListingActivity extends AppActivity {

    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_dots)
    ImageView tvDots;
    @BindView(R.id.tv_clear_all)
    TextView tvClearAll;
    @BindView(R.id.ll_menuRight)
    FrameLayout llMenuRight;
    @BindView(R.id.rv_videos)
    RecyclerView rvVideos;
    @BindView(R.id.layout)
    RelativeLayout layout;
    VideoListingAdapter videoListingAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_listing);
        ButterKnife.bind(this);
        init();
        setHeader();

    }

    private void init() {
        rvVideos.setLayoutManager(new LinearLayoutManager(this));
        videoListingAdapter=new VideoListingAdapter(this);
        rvVideos.setAdapter(videoListingAdapter);
    }


    @OnClick(R.id.ll_back)
    public void onViewClicked() {
        onBackPressed();
    }



    public void setHeader() {
        tvHeader.setText(getResources().getString(R.string.help)+" "+getResources().getString(R.string.videos));
        tvDots.setVisibility(View.INVISIBLE);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}

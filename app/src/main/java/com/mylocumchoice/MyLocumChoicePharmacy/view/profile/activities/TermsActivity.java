package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TermsActivity extends AppActivity {

    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_dots)
    ImageView tvDots;
    @BindView(R.id.ll_sys)
    LinearLayout llSys;
    @BindView(R.id.tv_sys)
    TextView tvSys;
    @BindView(R.id.iv_sys)
    ImageView ivSys;
    @BindView(R.id.ll_skills)
    LinearLayout llSkills;
    @BindView(R.id.tv_skills)
    TextView tvSkills;
    @BindView(R.id.iv_skills)
    ImageView ivSkills;
    @BindView(R.id.ll_acc)
    LinearLayout llAcc;
    @BindView(R.id.tv_acc)
    TextView tvAcc;
    @BindView(R.id.iv_acc)
    ImageView ivAcc;

    @BindView(R.id.ll_menuRight)
    FrameLayout llMenuRight;

    @BindView(R.id.shadow7)
    View shadow7;
    @BindView(R.id.ll_gp)
    LinearLayout llGp;
    @BindView(R.id.tv_gp)
    TextView tvGp;
    @BindView(R.id.iv_gp)
    ImageView ivGp;
    @BindView(R.id.shadow8)
    View shadow8;

    Bundle bundle = new Bundle();

    String isTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms2);
        ButterKnife.bind(this);
        setHeader();
    }

    @OnClick(R.id.ll_back)
    public void onBackClick(){ finish(); }

    @OnClick(R.id.ll_sys)
    public void onClickSys() {
        bundle = new Bundle();
        bundle.putString("isTo", "terms");
        startActivityWithBundle(TermsWVActivity.class, bundle);
    }

    @OnClick(R.id.ll_skills)
    public void onClickSkills(){
        bundle = new Bundle();
        bundle.putString("isTo", "accept");
        startActivityWithBundle(TermsWVActivity.class, bundle);}

    @OnClick(R.id.ll_acc)
    public void onClickAcc(){
        bundle = new Bundle();
        bundle.putString("isTo", "privacy");
        startActivityWithBundle(TermsWVActivity.class, bundle); }


    @OnClick(R.id.ll_gp)
    public void onClickGoogle(){
        bundle = new Bundle();
        bundle.putString("isTo", "google");
        startActivityWithBundle(TermsWVActivity.class, bundle); }

    public void setHeader() {
        tvHeader.setText("Terms");
        tvDots.setVisibility(View.INVISIBLE);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}

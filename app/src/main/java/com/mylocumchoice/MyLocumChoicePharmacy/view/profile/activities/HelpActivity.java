package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.ActivateAccountPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.ActivateAccountVP;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SignOutAlert;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.signup.activities.AppTourActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HelpActivity extends AppActivity implements ActivateAccountVP.View {

    /**
     * ButterKnife Code
     **/
    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_dots)
    ImageView tvDots;
    @BindView(R.id.shadow1)
    View shadow1;
    @BindView(R.id.ll_sys)
    LinearLayout llSys;
    @BindView(R.id.tv_sys)
    TextView tvSys;
    @BindView(R.id.iv_sys)
    ImageView ivSys;
    @BindView(R.id.shadow2)
    View shadow2;
    @BindView(R.id.shadow3)
    View shadow3;
    @BindView(R.id.shadow4)
    View shadow4;
    @BindView(R.id.shadow10)
    View shadow10;
    @BindView(R.id.shadow5)
    View shadow5;
    @BindView(R.id.ll_acc)
    LinearLayout llAcc;
    @BindView(R.id.tv_acc)
    TextView tvAcc;
    @BindView(R.id.iv_acc)
    ImageView ivAcc;
    @BindView(R.id.shadow6)
    View shadow6;
    @BindView(R.id.shadow50)
    View shadow50;
    @BindView(R.id.ll_deact)
    LinearLayout llDeact;
    @BindView(R.id.tv_deact)
    TextView tvDeact;
    @BindView(R.id.iv_deact)
    ImageView ivDeact;
    @BindView(R.id.shadow24)
    View shadow24;
    @BindView(R.id.tv_clear_all)
    TextView tvClearAll;
    @BindView(R.id.ll_menuRight)
    FrameLayout llMenuRight;
    @BindView(R.id.tv_videos)
    TextView tvVideos;
    @BindView(R.id.iv_videos)
    ImageView ivVideos;
    @BindView(R.id.ll_videos)
    LinearLayout llVideos;

    private Bundle bundle;
    /**
     * ButterKnife Code
     **/
    private ActivateAccountPresenter presenter;
    private SignOutAlert signOutAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO In this activity
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);
        setHeader();
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getBoolean("status")) {
                tvDeact.setText("Activate Account");
                ivDeact.setVisibility(View.GONE);
            } else {
                tvDeact.setText("Deactivate Account");
                ivDeact.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setHeader() {
        tvHeader.setText(getResources().getString(R.string.help));
        tvDots.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @OnClick(R.id.ll_sys)
    public void Sys() {
        bundle = ActivityOptions.makeCustomAnimation(this, R.anim.slide_from_right, R.anim.slide_to_left).toBundle();
        Intent i = new Intent(HelpActivity.this, AppTourActivity.class);
        i.putExtra("isFrom", "Help");
        startActivity(i, bundle);
    }

    @OnClick(R.id.ll_acc)
    public void FAQs() {
        bundle = new Bundle();
        bundle.putString("isTo", "faq");
        startActivityWithBundle(TermsWVActivity.class, bundle);
    }


    @OnClick(R.id.ll_deact)
    public void deact() {
        if (getIntent().getExtras().getBoolean("status")) {
            alert(getResources().getString(R.string.activate_dialog), "Cancel", "Activate");
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("isFrom", "deactivate");
            startActivityWithBundle(OfferActivity.class, bundle);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }
    }


    public void addRequest() {
        presenter = new ActivateAccountPresenter(this, this);
        presenter.activateAccount(this);
    }

    @Override
    public void onActivate(Response<Void> response) {
            if (response.code() == 204) {
                try{
                    Intent intent = new Intent(this, LandingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                    LandingActivity.openFragmentPosition = 5;
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }  else {
                handleAPIErrors(this, response);
            }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //onBackPressed();
    }

    public void alert(String title, String cancel, String accept) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setCancelable(false)
                .create();

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_tc, null);
        TextView tv = dialogView.findViewById(R.id.tvText);
        TextView tvCancel = dialogView.findViewById(R.id.tv_cancel);
        TextView tvAccept = dialogView.findViewById(R.id.tv_clear);
        tv.setText(title);
        tvCancel.setText(accept);
        tvAccept.setText(cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRequest();
                dialog.dismiss();
            }
        });

        tvAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setView(dialogView);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    @OnClick({R.id.ll_back, R.id.ll_videos})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                onBackPressed();
                break;
            case R.id.ll_videos:
                startActivity(VideoListingActivity.class);
                break;
        }
    }
}

package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.AccountVerifyRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.VerificationRes;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.VerificationPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.VerificationVP;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SignOutAlert;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.adapters.VerificationSectionAdapter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class VerificationActivity extends AppActivity implements VerificationVP.View {

    @BindView(R.id.rv_verification)
    RecyclerView rvVerification;

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
    @BindView(R.id.fl_button)
    FrameLayout frameLayout;
    @BindView(R.id.tv_done)
    TextView tvDone;

    @BindView(R.id.tv_unDone)
    TextView tv_unDone;
    @BindView(R.id.ll_frameLayout)
    LinearLayout llFrameLayout;

    private SignOutAlert signOutAlert;

    private VerificationPresenter presenter;
    private VerificationSectionAdapter adapter;

    public int NOTIFICATION_RESULT_CODE=1001;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_steps);
        ButterKnife.bind(this);
        setHeader();
        init();
        getDetails();
    }

    private void setHeader() {
        tvHeader.setText(getResources().getString(R.string.step_verification));
        tvDots.setVisibility(View.INVISIBLE);
    }

    private void init() {
        rvVerification.setLayoutManager(new LinearLayoutManager(this));
        rvVerification.setNestedScrollingEnabled(false);
    }

    @OnClick(R.id.ll_back)
    public void onViewClicked() {
        try {
            if (getIntent().getExtras() != null) {
                try {
                    if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("notification")) {
                        Intent mIntent = new Intent();
                        setResult(NOTIFICATION_RESULT_CODE, mIntent);
                        finish();
                    } else if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("notificationAdapter")) {
                        Intent intent = new Intent(this, LandingActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        LandingActivity.openFragmentPosition = 4;
                        overridePendingTransitionExit();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Intent mIntent = new Intent();
                    setResult(NOTIFICATION_RESULT_CODE, mIntent);
                    finish();
                }
            } else {
                finish();
            }
        }catch (Exception e){
            e.printStackTrace();
            finish();
        }
    }



    @OnClick(R.id.tv_done)
    public void onDoneClicked() {
        presenter = new VerificationPresenter(this, this);
        presenter.getAccountVerification(this);
    }



    public void getDetails(){
        presenter = new VerificationPresenter(this, this);
        try {
            if (getIntent().getExtras() != null) {
                if(getIntent().getExtras().get("from").toString().equalsIgnoreCase("notification")
                        ||getIntent().getExtras().get("from").toString().equalsIgnoreCase("notificationAdapter")){
                    presenter.getVerificationNotification(this, getIntent().getIntExtra("notification_id", 0));

                }
            } else {
                presenter.getVerification(this);
            }
        }catch (Exception e){
            e.printStackTrace();
            presenter.getVerification(this);
        }
    }

    @Override
    public void onVerificationResponse(Context context, Response<VerificationRes> response) {
        if(response.code() == 200){
            if(response.body().getCanRequestVerification()){
                tvDone.setVisibility(View.VISIBLE);
                tv_unDone.setVisibility(View.GONE);
            }else{
                tvDone.setVisibility(View.GONE);
                tv_unDone.setVisibility(View.VISIBLE);
//                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
//                        RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                layoutParams.setMargins(0, 40, 0, 0);
//                rvVerification.setLayoutParams(layoutParams);
            }

            /*if(response.body().getSections().size() == 0){
                onBackPressed();
            }*/

            adapter = new VerificationSectionAdapter(this, this, response);
            rvVerification.setAdapter(adapter);
        } else {
            handleAPIErrors(this, response);
        }
    }

    @Override
    public void onAccountVerificationResponse(Context context, Response<AccountVerifyRes> response) {
        if(response.code() == 200){
            Intent intent = new Intent(this, LandingActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            LandingActivity.openFragmentPosition = 5;
        } else {
            handleAPIErrors(this, response);
        }
    }


    @Override
    public void onBackPressed() {
        try {
            if (getIntent().getExtras() != null) {
                try {
                    if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("notification")) {
                        Intent mIntent = new Intent();
                        setResult(NOTIFICATION_RESULT_CODE, mIntent);
                        finish();
                    } else if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("notificationAdapter")) {
                        Intent intent = new Intent(this, LandingActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        LandingActivity.openFragmentPosition = 4;
                        overridePendingTransitionExit();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Intent mIntent = new Intent();
                    setResult(NOTIFICATION_RESULT_CODE, mIntent);
                    finish();
                }
            } else {
                finish();
            }
        }catch (Exception e){
            e.printStackTrace();
            finish();
        }
    }
}

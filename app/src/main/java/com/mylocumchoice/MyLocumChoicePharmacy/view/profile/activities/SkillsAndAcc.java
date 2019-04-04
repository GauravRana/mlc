package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ProfileRes;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.ProfilePresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.ProfileVP;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SkillsAndAcc extends AppActivity implements ProfileVP.View{


    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_dots)
    ImageView tvDots;
    /** ButterKnife Code **/
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
    @BindView(R.id.ll_skills)
    LinearLayout llSkills;
    @BindView(R.id.tv_skills)
    TextView tvSkills;
    @BindView(R.id.iv_warning_skills)
    ImageView ivWarningSkills;
    @BindView(R.id.iv_warning_system)
    ImageView ivWarningSystem;
    @BindView(R.id.iv_skills)
    ImageView ivSkills;
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
    @BindView(R.id.iv_warning_acc)
    ImageView ivWarningAcc;
    @BindView(R.id.iv_acc)
    ImageView ivAcc;
    @BindView(R.id.shadow6)
    View shadow6;
    @BindView(R.id.shadow12)
    View shadow12;

    private ProfilePresenter presenter;
    /** ButterKnife Code **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills_and_acc);
        ButterKnife.bind(this);
        setHeader();
        getAccount();
    }

    @OnClick(R.id.ll_back)
    public void onBackClick(){
        try {
            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("profileFrag")) {
//                Intent intent = new Intent(this, LandingActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                LandingActivity.openFragmentPosition = 5;
//                overridePendingTransitionExit();
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

    @OnClick(R.id.ll_sys)
    public void onClickSys(){ startActivity(PharmacySys.class); }

    @OnClick(R.id.ll_skills)
    public void onClickSkills(){
        Intent intent = new Intent(this, SkillActivity.class);
        intent.putExtra("from", "SkillnAcc");
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @OnClick(R.id.ll_acc)
    public void onClickAcc(){
        Intent intent = new Intent(this, AccreditationActivity.class);
        intent.putExtra("from", "SkillnAcc");
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void setHeader() {
        tvHeader.setText(getResources().getString(R.string.text_SandA));
        tvDots.setVisibility(View.INVISIBLE);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }




    public void getAccount() {
        presenter = new ProfilePresenter(this, this);
        presenter.getAccount(this);
    }


    @Override
    public void getProfileSuccess(Response<ProfileRes> response) {
        if (response.code() == 200) {
            try {
                if(response.body().getSkillsWarning()){
                    ivWarningSkills.setVisibility(View.VISIBLE);
                }
                if(response.body().getAccreditationsWarning()){
                    ivWarningAcc.setVisibility(View.VISIBLE);
                }

                if(response.body().isSystems_warning()){
                    ivWarningSystem.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else{
            handleAPIErrors(this, response);
        }
    }

    @Override
    public void onSuccessUpload(Response<Void> response) {

    }

    @Override
    public void activateSuccess(Response<Void> response) {

    }

    @Override
    public void onBackPressed() {
        try {
            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("profileFrag")) {
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

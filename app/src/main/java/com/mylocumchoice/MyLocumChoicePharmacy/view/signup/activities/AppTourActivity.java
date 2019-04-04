package com.mylocumchoice.MyLocumChoicePharmacy.view.signup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.HelpActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.signup.adapters.TourAdapter;
import com.ravindu1024.indicatorlib.ViewPagerIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppTourActivity extends BaseActivity {

    @BindView(R.id.iv_cross)
    TextView ivCross;
    @BindView(R.id.tv_desc)
    TextView tvDesc;

    @BindView(R.id.pager_indicator)
    ViewPagerIndicator pagerIndicator;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    String[] screenText = {
            "Click here to set and apply filters/preferences for shifts.",
            "Turn on the best match toggle to show shifts that match your exact preferences.",
            "The red light (bell) indicates an emergency shift. Click to view more information, accept or negotiate a rate.",
            "Click to view booking history/export/reconcile shifts and shift payments.",
            "This allows you to see shifts you have applied for and ones that have been declined. Pending applications can be withdrawn at any time.",
            "Accept or decline shifts from pharmacies who have invited you to work directly.",
            "Keep your availability up to date so you can receive work invitations directly from pharmacies.",
            "Select your working preferences.",
            "Easily upload documents to these sections to become verified so that you can work."
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_tour);
        ButterKnife.bind(this);
        init();


        viewPager.postDelayed(new Runnable() {

            @Override
            public void run() {
                viewPager.setCurrentItem(0);
            }
        }, 100);
    }


    @OnClick(R.id.iv_cross)
        public void onClick(){
        try {
            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().get("isFrom").toString().equalsIgnoreCase("SignUp")
                        || getIntent().getExtras().get("isFrom").toString().equalsIgnoreCase("SignIn")) {
                    Intent i = new Intent(AppTourActivity.this, LandingActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                } else {
                    onBackPressed();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            onBackPressed();
        }

    }

    private void init() {
        viewPager.setAdapter(new TourAdapter(AppTourActivity.this,getSupportFragmentManager()));
        pagerIndicator.setPager(viewPager);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }


            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    tvDesc.setText(screenText[0]);
                }else if(position == 1){
                    ivCross.setText("SKIP");
                    tvDesc.setText(screenText[1]);
                }else if(position == 2){
                    ivCross.setText("SKIP");
                    tvDesc.setText(screenText[2]);
                }else if(position == 3){
                    ivCross.setText("SKIP");
                    tvDesc.setText(screenText[3]);
                }else if(position == 4){
                    ivCross.setText("SKIP");
                    tvDesc.setText(screenText[4]);
                }else if(position == 5){
                    ivCross.setText("SKIP");
                    tvDesc.setText(screenText[5]);
                }else if(position == 6){
                    ivCross.setText("SKIP");
                    tvDesc.setText(screenText[6]);
                }else if(position == 7){
                    tvDesc.setText(screenText[7]);
                    ivCross.setText("SKIP");
                }else if(position == 8){
                    tvDesc.setText(screenText[8]);
                    ivCross.setText("CLOSE");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

//    @OnClick(R.id.iv_cross)
//    public void onViewClicked() {
//        if (SharedPrefManager.getInstance(AppTourActivity.this).getEmail() == null
//                && SharedPrefManager.getInstance(AppTourActivity.this).getAuthToken() == null) {
//            Intent i = new Intent(AppTourActivity.this, SignUpActivity.class);
//            startActivity(i);
//            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//            finish();
//        } else {
//            Intent i = new Intent(AppTourActivity.this, LandingActivity.class);
//            startActivity(i);
//            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//            finish();
//        }
//    }
}

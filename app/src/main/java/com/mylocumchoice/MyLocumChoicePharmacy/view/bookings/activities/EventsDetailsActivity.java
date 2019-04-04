package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.EventList;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters.EventsAdapter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EventsDetailsActivity extends AppActivity {

    @BindView(R.id.shadow3)
    View shadow3;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_free)
    ImageView ivFree;
    @BindView(R.id.tv_lab)
    TextView tvLab;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.shadow5)
    View shadow5;
    @BindView(R.id.shadow4)
    View shadow4;
    @BindView(R.id.tv_place)
    TextView tvPlace;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.shadow2)
    View shadow2;
    @BindView(R.id.tv_war)
    TextView tvWar;
    @BindView(R.id.btn_accept)
    TextView btnAccept;
    @BindView(R.id.ll_frameLayout)
    LinearLayout llFrameLayout;
    private LinearLayoutManager linearLayoutManager;
    private EventsAdapter mAdapter;


    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.ll_menuRight)
    FrameLayout llMenuRight;
    @BindView(R.id.tv_dots)
    ImageView tvDots;
    @BindView(R.id.tv_clear_all)
    TextView tvClearAll;
    /**
     * ButterKnife Code
     **/

    EventList eventList;
    String registrationLink = "";
    Utils mUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_details);
        ButterKnife.bind(this);
        setHeader();
        mUtils = new Utils();
        eventList = EventBus.getDefault().getStickyEvent(EventList.class);
        setData(eventList);
    }

    private void setData(EventList eventList) {
        if(eventList!=null) {
            try {
                if (eventList.getName() != null) {
                    if (eventList.getName() instanceof String) {
                        tvName.setVisibility(View.VISIBLE);
                        tvName.setText((String) eventList.getName());
                    } else {
                        tvName.setVisibility(View.GONE);
                    }
                } else {
                    tvName.setVisibility(View.GONE);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            if (eventList.getPrice() != null
                    && !eventList.getPrice().equalsIgnoreCase("0.0")
                    && !eventList.getPrice().equalsIgnoreCase("free")) {
                ivFree.setVisibility(View.GONE);
                tvLab.setVisibility(View.VISIBLE);
                tvLab.setText(eventList.getPrice());
            } else {
                ivFree.setVisibility(View.VISIBLE);
            }

            if (eventList.getDate() != null && !eventList.getDate().equals("")) {
                tvDate.setText(eventList.getDate());
            } else {
                tvDate.setVisibility(View.GONE);
            }

            if (eventList.getTime() != null && !eventList.getTime().equals("")) {
                tvTime.setText(eventList.getTime());
            } else {
                tvTime.setVisibility(View.GONE);
            }

            if (eventList.getFullAddress() != null && !eventList.getFullAddress().equals("")) {
                tvPlace.setText(eventList.getFullAddress());
            } else {
                tvPlace.setVisibility(View.GONE);
            }

            if (eventList.getBriefDesc() != null && !eventList.getBriefDesc().equals("")) {
                tvDesc.setText(eventList.getBriefDesc());
            } else {
                tvDesc.setVisibility(View.GONE);
            }

            if (eventList.getRegistrationLink() != null) {
                if (eventList.getRegistrationLink() instanceof String) {
                    tvWar.setVisibility(View.GONE);
                    llFrameLayout.setVisibility(View.VISIBLE);
                    registrationLink = (String) eventList.getRegistrationLink();
                } else {
                    tvWar.setVisibility(View.VISIBLE);
                    llFrameLayout.setVisibility(View.GONE);
                }
            } else {
                tvWar.setVisibility(View.VISIBLE);
                llFrameLayout.setVisibility(View.GONE);
            }
        }
    }


    public void setHeader() {
        tvHeader.setText("Events Details");
        tvDots.setVisibility(View.GONE);
    }

    @OnClick(R.id.ll_back)
    public void onBackClick() {
        try {
            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("notification")) {
                    Intent intent = new Intent(this, LandingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    LandingActivity.openFragmentPosition = 4;
                    finish();
                    overridePendingTransitionExit();
                }
                else if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("notification_out")) {
                    Intent intent = new Intent(this, EventsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    overridePendingTransitionExit();
                }
                else {
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @OnClick(R.id.btn_accept)
    public void onViewClicked() {
        if (!registrationLink.equals("")) {
            if(registrationLink.contains("http://")||registrationLink.contains("https://")) {
                mUtils.fn_openUrl(EventsDetailsActivity.this, registrationLink);
            }else{
                showWarning(this,"","Not a valid Link","error");
            }
        }
    }

    @Override
    public void onBackPressed() {
        try {
            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("notification")) {
                    Intent intent = new Intent(this, LandingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    LandingActivity.openFragmentPosition = 4;
                    finish();
                    overridePendingTransitionExit();
                }

                else if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("notification_out")) {
                    Intent intent = new Intent(this, EventsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    overridePendingTransitionExit();
                }

                else {
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

package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.h6ah4i.android.materialshadowninepatch.MaterialShadowContainerView;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.Clients;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.CompletedBooking;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.CompletedBookingRequest;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.CompletedBookingResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.Counties;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShiftResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters.HistoryPager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment.HistoryFilterFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment.HistorySortFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment.HistorySortFragment.isAsc;

public class BookingHistorySortActivity extends AppActivity implements HistorySortFragment.OnFragmentInteractionListener,
        HistoryFilterFragment.OnFragmentInteractionListener {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.ll_shadow)
    MaterialShadowContainerView llShadow;
    @BindView(R.id.btn_accept)
    TextView btnAccept;
    @BindView(R.id.ll_frameLayout)
    LinearLayout llFrameLayout;
    //This is our viewPager
    private ViewPager viewPager;

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_dots)
    ImageView tvDots;

    public static TextView tvClearAll;
    @BindView(R.id.ll_menuRight)
    FrameLayout llMenuRight;

    public String isPaid = "";
    public String startDate="";
    public String endDate="";
    public String sort="";
    int RESULT_CODE_FILTERS = 4;


    public CompletedBooking completedBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history_sort);
        ButterKnife.bind(this);
        tvClearAll=findViewById(R.id.tv_clear_all);
        setHeader();
        if(EventBus.getDefault().getStickyEvent(CompletedBooking.class)!=null){
            completedBooking=EventBus.getDefault().getStickyEvent(CompletedBooking.class);
        }else {
            completedBooking = new CompletedBooking();
        }
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.ic_menu_right_on)));
        tabLayout.addTab(tabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.ic_menu_up_down_off)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);
        HistoryPager adapter = new HistoryPager(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        tvDots.setImageResource(R.drawable.ic_clear_all);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    tabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.ic_menu_right_on));
                    tabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.ic_menu_up_down_off));
                } else {
                    tabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.ic_menu_right_off));
                    tabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.ic_up_down_on));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.setupWithViewPager(viewPager);

    }

    @OnClick(R.id.ll_back)
    public void onBackClick() {
        finish();
    }

    public void setHeader() {
        tvHeader.setText(getResources().getString(R.string.text_sort));
        tvClearAll.setVisibility(View.VISIBLE);
        tvDots.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onFragmentInteraction(int position) {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @OnClick(R.id.btn_accept)
    public void onViewClicked() {



        if(isPaid==null){
            completedBooking.setPaid(null);
        } else if (isPaid.equalsIgnoreCase("true")) {
            completedBooking.setPaid(true);
        } else if (isPaid.equalsIgnoreCase("false")) {
            completedBooking.setPaid(false);
        } else {
            completedBooking.setPaid(null);
        }


        try {
            if (!isAsc) {
                completedBooking.setSortBy("date_desc");
            } else {
                completedBooking.setSortBy(sort);
            }
        }catch (Exception e){
            e.printStackTrace();
            completedBooking.setSortBy("date_desc");
        }

        try {
            completedBooking.setStartDate(HistoryFilterFragment.tvStartDate.getText().toString());
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            completedBooking.setEndDate(HistoryFilterFragment.tvEndDate.getText().toString());
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            List<CompletedBookingResponse.Client> lv_client = new ArrayList<>();
            lv_client = EventBus.getDefault().getStickyEvent(Clients.class).getClients();
            if (lv_client != null) {
                ArrayList<Integer> lv_countyIds = new ArrayList<>();
                if(lv_client.size()>0) {
                    for (int i = 0; i < lv_client.size(); i++) {
                        lv_countyIds.add(lv_client.get(i).getId());
                    }
                    completedBooking.setClientIds(lv_countyIds);
                }else{
                    completedBooking.setClientIds(lv_countyIds);
                }
            }else{
                ArrayList<Integer> lv_countyIds = new ArrayList<>();
                completedBooking.setClientIds(lv_countyIds);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ArrayList<Integer> lv_countyIds = new ArrayList<>();
            completedBooking.setClientIds(lv_countyIds);
        }



        try {
            EventBus.getDefault().removeStickyEvent(Clients.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        EventBus.getDefault().postSticky(completedBooking);
        Intent data = new Intent();
        setResult(RESULT_CODE_FILTERS, data);
        onBackPressed();

    }

    private JsonObject getBookingReq(CompletedBooking completedBooking) {
        CompletedBookingRequest completedBookingRequest = new CompletedBookingRequest();
        return completedBookingRequest.add(completedBooking);
    }
}

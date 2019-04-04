package com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.h6ah4i.android.materialshadowninepatch.MaterialShadowContainerView;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.Counties;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShift;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShiftResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.adapters.Pager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.fragment.SortLeftFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.fragment.SortRightFragment;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SortActivity extends AppActivity implements SortLeftFragment.OnFragmentInteractionListener,
        SortRightFragment.OnFragmentInteractionListener {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.ll_shadow)
    MaterialShadowContainerView llShadow;
    @BindView(R.id.btn_accept)
    TextView btnAccept;
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

    public static TextView tv_clear_all;
    @BindView(R.id.ll_menuRight)
    FrameLayout llMenuRight;

    @BindView(R.id.ll_frameLayout)
    LinearLayout llFrameLayout;

    public static OpenShift openShift;
    public static boolean isCleared=false;

    int RESULT_CODE_FILTERS = 4;
    Pager adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        ButterKnife.bind(this);
        tv_clear_all=findViewById(R.id.tv_clear_all);
        try {
            if (SharedPrefManager.getInstance(SortActivity.this).getOpenShiftData() != null || !SharedPrefManager.getInstance(SortActivity.this).getOpenShiftData().equals("")) {
                openShift = SharedPrefManager.getInstance(SortActivity.this).getOpenShiftData();
                try {
                    if (openShift.getStartDate().equals("") || openShift.getStartDate() == null) {
                        openShift.setStartDate("");
                    }

                    if (openShift.getEndDate().equals("") || openShift.getEndDate() == null) {
                        openShift.setEndDate("");
                    }
                }catch (Exception e ){
                    e.printStackTrace();
                }
            } else {
                openShift = new OpenShift();
            }
        }catch (Exception e){
            e.printStackTrace();
            openShift = new OpenShift();
        }
        setHeader();
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.ic_menu_right_on)));
        tabLayout.addTab(tabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.ic_menu_up_down_off)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());
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

        KeyboardVisibilityEvent.setEventListener(this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if (isOpen) {
                            llFrameLayout.setVisibility(View.GONE);
                            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) viewPager.getLayoutParams();
                            params.setMargins(0, (int) getResources().getDimension(R.dimen._40sdp), 0, 0);
                            viewPager.setLayoutParams(params);

                        } else {
                            llFrameLayout.setVisibility(View.VISIBLE);
                            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) viewPager.getLayoutParams();
                            params.setMargins(0, (int) getResources().getDimension(R.dimen._40sdp), 0, (int) getResources().getDimension(R.dimen._42sdp));
                            viewPager.setLayoutParams(params);
                        }

                    }
                });

    }

    @OnClick(R.id.ll_back)
    public void onBackClick() {
        openShift = null;
        onBackPressed();
    }


    public void setHeader() {
        tvHeader.setText(getResources().getString(R.string.text_sort));
        tv_clear_all.setVisibility(View.VISIBLE);
        tvDots.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onFragmentInteraction(int position) {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @OnClick({R.id.tv_clear_all, R.id.btn_accept})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_clear_all:


                /*try {
                    List<OpenShiftResponse.County> lv_counties = new ArrayList<>();
                    lv_counties = EventBus.getDefault().getStickyEvent(Counties.class).getLv_county();
                    if (lv_counties != null) {
                        ArrayList<Integer> lv_countyIds = new ArrayList<>();
                        for (int i = 0; i < lv_counties.size(); i++) {
                            lv_countyIds.add(lv_counties.get(i).getId());
                        }
                        openShift.setCountyIds(lv_countyIds);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.e("openshift", openShift + "");*/

                try {
                    SharedPrefManager.getInstance(this).clearShiftPref();
                    isCleared = true;

                    ArrayList<Integer> lv_counties=new ArrayList<>();
                    openShift.setCountyIds(lv_counties);
                    openShift.setDistanceId(0);
                    openShift.setStartDate("");
                    openShift.setEndDate("");
                    openShift.setEmergency(false);
                    openShift.setRate(0.0);
                    openShift.setPaceIds(lv_counties);
                    openShift.setSortBy("");

                    Intent data = new Intent();
                    setResult(RESULT_CODE_FILTERS, data);
                    onBackPressed();
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.btn_accept:
                try {
                    List<OpenShiftResponse.County> lv_counties = new ArrayList<>();
                    lv_counties = EventBus.getDefault().getStickyEvent(Counties.class).getLv_county();
                    if (lv_counties != null) {
                        ArrayList<Integer> lv_countyIds = new ArrayList<>();
                        for (int i = 0; i < lv_counties.size(); i++) {
                            lv_countyIds.add(lv_counties.get(i).getId());
                        }
                        openShift.setCountyIds(lv_countyIds);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.e("openshift", openShift + "");
                Intent data1 = new Intent();
                setResult(RESULT_CODE_FILTERS, data1);
                onBackPressed();
                break;
        }
    }



}

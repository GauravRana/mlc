package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.adapters.Pager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.fragment.SortLeftFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.fragment.SortRightFragment;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BookingInvitationSortActivity extends AppActivity implements SortLeftFragment.OnFragmentInteractionListener,
        SortRightFragment.OnFragmentInteractionListener {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
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
    @BindView(R.id.tv_clear_all)
    TextView tvClearAll;
    @BindView(R.id.ll_menuRight)
    FrameLayout llMenuRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_invitation_sort);
        ButterKnife.bind(this);
        setHeader();
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.ic_menu_right_on)));
        tabLayout.addTab(tabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.ic_menu_up_down_off)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        viewPager = (ViewPager) findViewById(R.id.pager);
        Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());
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
    public void onBackClick(){ finish(); }

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
}

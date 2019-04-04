package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.CompletedBooking;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.CustomViewPager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters.BookingHistoryPager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment.CancelledBookHistoryFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment.CompleteBookHistoryFragment;


import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BookingHistoryActivity extends AppActivity implements CompleteBookHistoryFragment.OnFragmentInteractionListener,
        CancelledBookHistoryFragment.OnFragmentInteractionListener{

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    private RecyclerView recyclerView;
    private CustomViewPager viewPager;
    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;


    public static ImageView tvDots;
    public static FrameLayout ll_menuRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void onFragmentInteraction(int position) {

    }

    public void setHeader() {
        tvHeader.setText(getResources().getString(R.string.text_history));
        ll_menuRight.setVisibility(View.VISIBLE);
        tvDots.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.ll_back)
    public void onBackPress(){
        try{
            EventBus.getDefault().removeStickyEvent(CompletedBooking.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try{
            EventBus.getDefault().removeStickyEvent(CompletedBooking.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    private void init(){
        tvDots = findViewById(R.id.tv_dots);
        ll_menuRight = findViewById(R.id.ll_menuRight);
        tvDots.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu));
        setHeader();
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.text_completed)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.text_cancelled)));
        tabLayout.setTabTextColors(ContextCompat.getColor(BookingHistoryActivity.this,R.color.grey),
                ContextCompat.getColor(BookingHistoryActivity.this,R.color.black));
        viewPager = findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(0);
        recyclerView = findViewById(R.id.recyclerView);
        BookingHistoryPager adapter = new BookingHistoryPager(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 0){
                    tvDots.setVisibility(View.VISIBLE);
                    ll_menuRight.setVisibility(View.VISIBLE);
                    tvDots.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu));
                }
                else if(tab.getPosition() == 1){
                    tvDots.setVisibility(View.GONE);
                    ll_menuRight.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                adapter.notifyDataSetChanged();
            }
        });
        //tabLayout.setupWithViewPager(viewPager);



        viewPager.setOnPageChangeListener(new CustomViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setScrollPosition(position,0f,true);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}

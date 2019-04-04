package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.EventList;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.EventListResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.calender.EventListPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.calender.EventListPresenterImpl;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ListItemClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.RecyclerViewClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters.BookingHistoryCompleteAdapter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters.EventsAdapter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment.ShiftDetailAppliedFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.calender.viewinterface.EventListView;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.ShiftDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EventsActivity extends AppActivity implements EventListView, ListItemClickListener{

    private LinearLayoutManager linearLayoutManager;
    private EventsAdapter mAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

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
    @BindView(R.id.scroll_view)
    NestedScrollView scrollView;
    @BindView(R.id.tv_noData)
    TextView tv_noData;
    @BindView(R.id.shadow_up)
    View shadow_up;



    /** ButterKnife Code **/

    EventListPresenter mPresenter;
    List<EventList> lv_events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        ButterKnife.bind(this);
        setHeader();
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        lv_events=new ArrayList<>();
        mPresenter=new EventListPresenterImpl(this,EventsActivity.this);
        mPresenter.onGettingEventList();

        /*mAdapter = new EventsAdapter(1,new BaseFragment(),this, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                startActivity(EventsDetailsActivity.class);
            }
        });*/


    }


    public void setHeader(){
        tvHeader.setText("Events");
        tvDots.setVisibility(View.GONE);
    }

    @OnClick(R.id.ll_back)
    public void onBackClick()
    {
       finish();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onEventListFetched(EventListResponse eventListResponse) {

        if(eventListResponse.getEvents() != null) {


            lv_events.addAll(eventListResponse.getEvents());
//
//            for(int i = 0; i < lv_events.size(); i++){
//
//            }

            if(lv_events.size() == 0){
                tv_noData.setVisibility(View.VISIBLE);
                shadow_up.setVisibility(View.GONE);
            }else {
                tv_noData.setVisibility(View.GONE);
                shadow_up.setVisibility(View.VISIBLE);
            }
            mAdapter = new EventsAdapter(EventsActivity.this, lv_events, this);
            recyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onGettingError() {
        showWarning(EventsActivity.this, "", getResources().getString(R.string.errorTimeOut), "error");
    }

    @Override
    public void onClick(int position) {
        startActivity(EventsDetailsActivity.class);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.Booking;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.BookingDetailResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.CancelBookingRequest;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.CancelBookingResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.CancelReason;
import com.mylocumchoice.MyLocumChoicePharmacy.model.eventmodels.UpcomingBookingData;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.Selection;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings.CancelBookingPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings.CancelBookingPresenterImpl;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ApiListener;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.GlobalConstants;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ListItemClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters.SelectReasonAdapter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface.CancelBookingView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SelectReasonActivity extends AppActivity implements ListItemClickListener,CancelBookingView {

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
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btn_accept)
    TextView btnAccept;
    @BindView(R.id.ll_frameLayout)
    LinearLayout llFrameLayout;

    CancelReason cancelReason;
    List<BookingDetailResponse.CancelReason> lv_reasons;

    SelectReasonAdapter mAdapter;
    CancelBookingPresenter mPresenter;

    int bookingId;
    int selectedPosition;
    int RESULT_CODE=2;

    int OTHER_REQUEST_CODE=3;
    int OTHER_RESULT_CODE=4;

    Utils mUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_select);
        ButterKnife.bind(this);
        mUtils=new Utils();
        bookingId=getIntent().getIntExtra("bookingId",0);
        init();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPresenter=new CancelBookingPresenterImpl(this,this);
    }

    private void init() {
        cancelReason= EventBus.getDefault().getStickyEvent(CancelReason.class);
        lv_reasons=cancelReason.getLv_cancelReason();
        setHeader("Cancellation");


        for(int i=0;i<lv_reasons.size();i++){
            BookingDetailResponse.CancelReason cancelReason=lv_reasons.get(i);
            cancelReason.setSelected(false);
        }

        mAdapter=new SelectReasonAdapter(this,this,lv_reasons,bookingId,this,OTHER_REQUEST_CODE);
        recyclerView.setAdapter(mAdapter);
    }

    public void setHeader(String headerText){
        tvHeader.setText(headerText);
        tvDots.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @OnClick({R.id.tv_back, R.id.ll_back,R.id.btn_accept})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                onBackPressed();
                break;
            case R.id.ll_back:
                onBackPressed();
                break;
                case R.id.btn_accept:
                    mPresenter.onBookingCancel(bookingId,getCancelRequest(lv_reasons.get(selectedPosition).getId(),lv_reasons.get(selectedPosition).getName()));

                    //mUtils.showDialog(SelectReasonActivity.this, "", getResources().getString(R.string.confirm_cancel), GlobalConstants.ConfirmCancel,SelectReasonActivity.this);
                break;
        }
    }

    @Override
    public void onClick(int position) {
        selectedPosition=position;
    }

    private JsonObject getCancelRequest(int id, String reason) {
        CancelBookingRequest openShiftReq = new CancelBookingRequest();
        return openShiftReq.add(id,reason);
    }

    @Override
    public void onCancelledSuccess(CancelBookingResponse response) {
        showWarning(SelectReasonActivity.this, "", response.getSuccess(), "");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                try {
                    UpcomingBookingData upcomingBookingData = EventBus.getDefault().getStickyEvent(UpcomingBookingData.class);
                    List<Booking> saved_bookings = upcomingBookingData.getAl_bookings();

                    for (int i = 0; i < saved_bookings.size(); i++) {
                        if (bookingId == saved_bookings.get(i).getId()) {
                            saved_bookings.remove(i);
                        }
                    }

                    upcomingBookingData.setAl_bookings(null);
                    upcomingBookingData.setAl_bookings(saved_bookings);
                    EventBus.getDefault().removeStickyEvent(UpcomingBookingData.class);
                    EventBus.getDefault().postSticky(upcomingBookingData);
                }catch (Exception e){
                    e.printStackTrace();
                }

                Intent mIntent=new Intent();
                setResult(RESULT_CODE,mIntent);
                finish();
            }
        }, 1500);

    }

    @Override
    public void onCancelledFailed() {
        showWarning(SelectReasonActivity.this, "", getResources().getString(R.string.errorTimeOut), "error");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==OTHER_REQUEST_CODE && resultCode==OTHER_RESULT_CODE){
            Intent mIntent=new Intent();
            setResult(RESULT_CODE,mIntent);
            finish();
        }
    }


}

package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.Booking;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.CancelBookingRequest;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.CancelBookingResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.eventmodels.UpcomingBookingData;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings.CancelBookingPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings.CancelBookingPresenterImpl;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface.CancelBookingView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddOtherReasonActivity extends AppActivity implements CancelBookingView {

    CancelBookingPresenter mPresenter;
    int bookingId;
    int reasonId;

    int OTHER_RESULT_CODE = 4;

    Utils mUtils;
    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_header_sec)
    TextView tvHeaderSec;
    @BindView(R.id.tv_dots)
    ImageView tvDots;
    @BindView(R.id.tv_clear_all)
    TextView tvClearAll;
    @BindView(R.id.ll_menuRight)
    FrameLayout llMenuRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.shadow3)
    View shadow3;
    @BindView(R.id.et_Text)
    EditText etText;
    @BindView(R.id.shadow4)
    View shadow4;
    @BindView(R.id.btn_accept)
    TextView btnAccept;
    @BindView(R.id.ll_frameLayout)
    LinearLayout llFrameLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        ButterKnife.bind(this);
        mUtils = new Utils();
        tvHeader.setText("Other Reasons");
        tvTitle.setText("Input Reason");
        tvDots.setVisibility(View.GONE);
        bookingId = getIntent().getIntExtra("bookingId", 0);
        reasonId = getIntent().getIntExtra("reasonId", 0);
        mPresenter = new CancelBookingPresenterImpl(this, this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @OnClick({R.id.tv_back, R.id.ll_back, R.id.btn_accept})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.ll_back:
                finish();
                break;
            case R.id.btn_accept:
                if (etText.getText().toString().length() == 0) {
                    showWarning(AddOtherReasonActivity.this, "", "Please input a reason.", "error");
                } else {
                    mPresenter.onBookingCancel(bookingId, getCancelRequest(reasonId, etText.getText().toString()));

                    //mUtils.showDialog(AddOtherReasonActivity.this, "", getResources().getString(R.string.confirm_cancel), GlobalConstants.ConfirmCancel,AddOtherReasonActivity.this);
                }
                break;
        }
    }

    private JsonObject getCancelRequest(int id, String reason) {
        CancelBookingRequest openShiftReq = new CancelBookingRequest();
        return openShiftReq.add(id, reason);
    }

    @Override
    public void onCancelledSuccess(CancelBookingResponse response) {
        showWarning(AddOtherReasonActivity.this, "", response.getSuccess(), "");
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
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent mIntent = new Intent();
                setResult(OTHER_RESULT_CODE, mIntent);
                finish();
            }
        }, 1500);
    }

    @Override
    public void onCancelledFailed() {
        showWarning(AddOtherReasonActivity.this, "", getResources().getString(R.string.errorTimeOut), "error");
    }


}

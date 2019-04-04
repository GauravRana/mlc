package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.BookingDetailResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.PaymentDetailsModel;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.RatingRequest;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.RatingResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.eventmodels.CompletedBookingData;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings.CompBookingDetailPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings.CompBookingDetailPresenterImpl;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings.PharmacyRatingPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings.PharmacyRatingPresenterImpl;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.GlobalConstants;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters.BookingDetailSections;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment.CompleteBookHistoryFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface.BookingDetailView;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface.PharmacyRatingView;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class BookingDetailsActivity extends AppActivity implements BookingDetailView,PharmacyRatingView{

    String str_reason = "";
    float int_rating=0;
    Utils mUtils;

    Boolean paidStatus = false;
    int bookingId = 0;
    String rate = "";
    String isFrom = "";
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
    @BindView(R.id.iv_Call)
    ImageView ivCall;
    @BindView(R.id.ll_call)
    LinearLayout llCall;
    @BindView(R.id.iv_Email)
    ImageView ivEmail;
    @BindView(R.id.ll_Email)
    LinearLayout llEmail;
    @BindView(R.id.iv_Emergency)
    ImageView ivEmergency;
    @BindView(R.id.ll_EEmail)
    LinearLayout llEEmail;
    @BindView(R.id.ll_contact)
    LinearLayout llContact;
    @BindView(R.id.shadow1)
    View shadow1;
    @BindView(R.id.tv_pound)
    TextView tvPound;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.tv_perHour)
    TextView tvPerHour;
    @BindView(R.id.ll_rate)
    LinearLayout llRate;
    @BindView(R.id.iv_edit)
    ImageView ivEdit;
    @BindView(R.id.ll_complete)
    LinearLayout llComplete;
    @BindView(R.id.tv_cancelled_by)
    TextView tvCancelledBy;
    @BindView(R.id.ll_cancled_by)
    LinearLayout llCancledBy;
    @BindView(R.id.tv_cancelled_on)
    TextView tvCancelledOn;
    @BindView(R.id.ll_cancled_on)
    LinearLayout llCancledOn;
    @BindView(R.id.tv_reason)
    TextView tvReason;
    @BindView(R.id.ll_reason)
    LinearLayout llReason;
    @BindView(R.id.ll_cancel)
    LinearLayout llCancel;
    @BindView(R.id.shadow2)
    View shadow2;
    @BindView(R.id.lay_upper)
    LinearLayout layUpper;
    @BindView(R.id.tv_shift_details)
    TextView tvShiftDetails;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btn_accept)
    TextView btnAccept;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.tv_bottom)
    TextView tvBottom;
    @BindView(R.id.ll_frameLayout)
    RelativeLayout llFrameLayout;
    @BindView(R.id.rl_rating)
    RelativeLayout rl_rating;
    @BindView(R.id.scroll_View)
    NestedScrollView scrollView;

    int REQUEST_CODE = 1;
    int RESULT_CODE = 2;

    PaymentDetailsModel paymentDetails = new PaymentDetailsModel();
    @BindView(R.id.shadow)
    View shadow;
    @BindView(R.id.iv_star)
    ImageView ivStar;
    @BindView(R.id.ll_ratePharmacy)
    LinearLayout llRatePharmacy;
    @BindView(R.id.shadowBottom)
    View shadowBottom;
    @BindView(R.id.ll_rating)
    LinearLayout llRating;
    @BindView(R.id.tv_rating)
    TextView tvRating;
    @BindView(R.id.ll_afterRate)
    LinearLayout llAfterRate;
    BookingDetailResponse bookingDetailResponse=new BookingDetailResponse();

    private List<BookingDetailResponse.Section> arrayList = new ArrayList<>();
    CompBookingDetailPresenter mPresenter;

    PharmacyRatingPresenter ratingPresenter;

    public int NOTIFICATION_RESULT_CODE=1001;
    public int UPDATE_RESULT_CODE=6;

    String key = "";

    boolean isCompleted=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);
        ButterKnife.bind(this);
        mPresenter = new CompBookingDetailPresenterImpl(this, this);
        ratingPresenter=new PharmacyRatingPresenterImpl(this,this);
        getIntentData();
        setHeader();
        mUtils = new Utils();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void getIntentData() {
        bookingId = getIntent().getIntExtra("id", 0);
        paidStatus = getIntent().getBooleanExtra("status", false);
        rate = getIntent().getStringExtra("rate");
        isFrom = getIntent().getStringExtra("isFrom");
        try{
            key= getIntent().getStringExtra("key");
        }catch (Exception e){
            e.printStackTrace();
        }
        if (isFrom.equalsIgnoreCase("completed")) {
            llComplete.setVisibility(View.VISIBLE);
            llCancel.setVisibility(View.GONE);
            tvShiftDetails.setVisibility(View.GONE);
            mPresenter.onGetBookingDetails(bookingId);
            llFrameLayout.setVisibility(View.VISIBLE);
            isCompleted=true;
        } else if (isFrom.equalsIgnoreCase("cancelled")) {
            llComplete.setVisibility(View.GONE);
            llCancel.setVisibility(View.VISIBLE);
            rl_rating.setVisibility(View.GONE);
            tvReason.setText(underLineText("View reason"));
            tvShiftDetails.setVisibility(View.VISIBLE);
            mPresenter.onGetCancelledBookingDetails(bookingId);
            llFrameLayout.setVisibility(View.GONE);
            isCompleted=false;
            ViewGroup.MarginLayoutParams marginLayoutParams =
                    (ViewGroup.MarginLayoutParams) recyclerView.getLayoutParams();
            marginLayoutParams.setMargins(0, 0, 0, 0);
            recyclerView.setLayoutParams(marginLayoutParams);
        }else if (isFrom.equalsIgnoreCase("notification")&&key.equalsIgnoreCase("cancelled")) {
            llComplete.setVisibility(View.GONE);
            llCancel.setVisibility(View.VISIBLE);
            rl_rating.setVisibility(View.GONE);
            tvReason.setText(underLineText("View reason"));
            tvShiftDetails.setVisibility(View.VISIBLE);
            mPresenter.onGetCancelledBookingDetails(bookingId);
            llFrameLayout.setVisibility(View.GONE);
            isCompleted=false;
            ViewGroup.MarginLayoutParams marginLayoutParams =
                    (ViewGroup.MarginLayoutParams) recyclerView.getLayoutParams();
            marginLayoutParams.setMargins(0, 0, 0, 0);
            recyclerView.setLayoutParams(marginLayoutParams);
        }else if (isFrom.equalsIgnoreCase("notificationAdapter")&&key.equalsIgnoreCase("cancelled")) {
            llComplete.setVisibility(View.GONE);
            llCancel.setVisibility(View.VISIBLE);
            rl_rating.setVisibility(View.GONE);
            tvReason.setText(underLineText("View reason"));
            tvShiftDetails.setVisibility(View.VISIBLE);
            mPresenter.onGetCancelledBookingDetails(bookingId);
            llFrameLayout.setVisibility(View.GONE);
            isCompleted=false;
            ViewGroup.MarginLayoutParams marginLayoutParams =
                    (ViewGroup.MarginLayoutParams) recyclerView.getLayoutParams();
            marginLayoutParams.setMargins(0, 0, 0, 0);
            recyclerView.setLayoutParams(marginLayoutParams);
        }

    }


    public void setHeader() {
        if (isFrom.equalsIgnoreCase("completed")) {
            tvHeader.setText(getResources().getString(R.string.text_book_details));
        } else if (isFrom.equalsIgnoreCase("cancelled")) {
            tvHeader.setText(getResources().getString(R.string.text_Cancle_details));
        }else if (isFrom.equalsIgnoreCase("notification")&&key.equalsIgnoreCase("cancelled")) {
            tvHeader.setText(getResources().getString(R.string.text_Cancle_details));
        }else if (isFrom.equalsIgnoreCase("notificationAdapter")&&key.equalsIgnoreCase("cancelled")) {
            tvHeader.setText(getResources().getString(R.string.text_Cancle_details));
        }

        tvDots.setVisibility(View.GONE);
    }


    @OnClick({R.id.tv_back, R.id.ll_back, R.id.ll_reason, R.id.ll_frameLayout, R.id.iv_edit, R.id.ll_rating})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                try {
                    if (getIntent().getExtras() != null) {
                        if(isFrom.equalsIgnoreCase("notification")&&key.equalsIgnoreCase("cancelled")) {
                            Intent mIntent=new Intent();
                            setResult(NOTIFICATION_RESULT_CODE,mIntent);
                            finish();
                        }else if(isFrom.equalsIgnoreCase("notificationAdapter")&&key.equalsIgnoreCase("cancelled")) {
                            Intent intent = new Intent(this, LandingActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            LandingActivity.openFragmentPosition = 4;
                            overridePendingTransitionExit();
                        } else if(isFrom.equalsIgnoreCase("completed")) {
                            if(CompleteBookHistoryFragment.isUpdated){
                                Intent mIntent=new Intent();
                                setResult(UPDATE_RESULT_CODE,mIntent);
                                finish();
                            }else {
                                finish();
                            }
                        }
                        else{
                            finish();
                        }
                    } else {
                        finish();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.ll_back:
                try {
                    if (getIntent().getExtras() != null) {
                        if(isFrom.equalsIgnoreCase("notification")&&key.equalsIgnoreCase("cancelled")) {
                            Intent mIntent=new Intent();
                            setResult(NOTIFICATION_RESULT_CODE,mIntent);
                            finish();
                        }else if(isFrom.equalsIgnoreCase("notificationAdapter")&&key.equalsIgnoreCase("cancelled")) {
                            Intent intent = new Intent(this, LandingActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            LandingActivity.openFragmentPosition = 4;
                            overridePendingTransitionExit();
                        } else if(isFrom.equalsIgnoreCase("completed")) {
                            if(CompleteBookHistoryFragment.isUpdated){
                                Intent mIntent=new Intent();
                                setResult(UPDATE_RESULT_CODE,mIntent);
                                finish();
                            }else {
                                finish();
                            }
                        }
                        else{
                            finish();
                        }
                    } else {
                        finish();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.ll_reason:
                if (str_reason != null && !str_reason.equals("")) {
                    mUtils.showInfoDialog(BookingDetailsActivity.this, str_reason, GlobalConstants.ShowReason);
                }
                break;
            case R.id.ll_frameLayout:
                EventBus.getDefault().postSticky(paymentDetails);
                Intent mIntent = new Intent(BookingDetailsActivity.this, PaymentDetails.class);
                mIntent.putExtra("bookingId", bookingId);
                startActivityForResult(mIntent, REQUEST_CODE);
                break;
            case R.id.iv_edit:
                EventBus.getDefault().postSticky(paymentDetails);
                Intent mIntent1 = new Intent(BookingDetailsActivity.this, PaymentDetails.class);
                mIntent1.putExtra("bookingId", bookingId);
                startActivityForResult(mIntent1, REQUEST_CODE);
                break;
            case R.id.ll_rating:
                showRatingDialog(BookingDetailsActivity.this);
                break;
        }
    }

    @Override
    public void onDetailsFetched(BookingDetailResponse response) {
        if (response.getShiftDetails().getSections() != null) {
            bookingDetailResponse=response;
            try {
                if (response.getShiftDetails().getRated() != null) {
                    llRating.setVisibility(View.GONE);
                    llAfterRate.setVisibility(View.VISIBLE);
                    if (response.getShiftDetails().getRated() instanceof String) {
                        tvRating.setText("Rated: " + (String) response.getShiftDetails().getRated());
                    }

                } else {
                    if(isFrom.equalsIgnoreCase("notification")&&key.equalsIgnoreCase("cancelled")) {
                        llRating.setVisibility(View.GONE);
                    }else if(isFrom.equalsIgnoreCase("notificationAdapter")&&key.equalsIgnoreCase("cancelled")) {
                        llRating.setVisibility(View.GONE);
                    } else if (isFrom.equalsIgnoreCase("cancelled")){
                        llRating.setVisibility(View.GONE);
                    }else {
                        llRating.setVisibility(View.VISIBLE);
                    }
                    llAfterRate.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (int i = 0; i < response.getShiftDetails().getSections().size(); i++) {
                arrayList = response.getShiftDetails().getSections();
            }
            BookingDetailSections adapter = new BookingDetailSections(this, response, arrayList,isCompleted);
            recyclerView.setAdapter(adapter);
            recyclerView.scrollToPosition(0);

            if (response.getShiftDetails().getPaymentStatus() != null && !response.getShiftDetails().getPaymentStatus().equals("")) {
                editText.setText(response.getShiftDetails().getPaymentStatus());

                if (response.getShiftDetails().getPaymentStatus().equalsIgnoreCase("Unpaid")) {
                    editText.setTextColor(getResources().getColor(R.color.red));
                    ivEdit.setVisibility(View.GONE);
                    llFrameLayout.setVisibility(View.VISIBLE);
                } else {
                    editText.setTextColor(getResources().getColor(R.color.black));
                    ivEdit.setVisibility(View.VISIBLE);
                    llFrameLayout.setVisibility(View.GONE);

                    ViewGroup.MarginLayoutParams marginLayoutParams =
                            (ViewGroup.MarginLayoutParams) recyclerView.getLayoutParams();
                    marginLayoutParams.setMargins(0, 50, 0, 0);
                    recyclerView.setLayoutParams(marginLayoutParams);
                }
            }

            if (isFrom.equalsIgnoreCase("cancelled")) {
                str_reason = response.getShiftDetails().getReason();
                tvCancelledBy.setText(response.getShiftDetails().getCancelledBy());
                tvCancelledOn.setText(response.getShiftDetails().getCancelledOn());
            }

            if(isFrom.equalsIgnoreCase("notification")&&key.equalsIgnoreCase("cancelled")) {
                str_reason = response.getShiftDetails().getReason();
                tvCancelledBy.setText(response.getShiftDetails().getCancelledBy());
                tvCancelledOn.setText(response.getShiftDetails().getCancelledOn());
            }

            if(isFrom.equalsIgnoreCase("notificationAdapter")&&key.equalsIgnoreCase("cancelled")) {
                str_reason = response.getShiftDetails().getReason();
                tvCancelledBy.setText(response.getShiftDetails().getCancelledBy());
                tvCancelledOn.setText(response.getShiftDetails().getCancelledOn());
            }


            if (response.getShiftDetails().getDuration() != null) {
                if (response.getShiftDetails().getDuration() instanceof Double) {
                    paymentDetails.setDuration((double) response.getShiftDetails().getDuration());
                }
            }
            if (response.getShiftDetails().getUnpaidHours() != null) {
                if (response.getShiftDetails().getUnpaidHours() instanceof Double) {
                    paymentDetails.setUnpaid_hours((double) response.getShiftDetails().getUnpaidHours());
                }
            }
            if (response.getShiftDetails().getExtraHours() != null) {
                if (response.getShiftDetails().getExtraHours() instanceof Double) {
                    paymentDetails.setExtra_hours((double) response.getShiftDetails().getExtraHours());
                }
            }
            if (response.getShiftDetails().getOtherPayment() != null) {
                if (response.getShiftDetails().getOtherPayment() instanceof Double) {
                    paymentDetails.setOther_payment((double) response.getShiftDetails().getOtherPayment());
                }
            }
            if (response.getShiftDetails().getTotalHours() != null) {
                if (response.getShiftDetails().getTotalHours() instanceof Double) {
                    paymentDetails.setTotal_hours((double) response.getShiftDetails().getTotalHours());
                }
            }

            if (response.getShiftDetails().getTotalAmount() != null) {
                if (response.getShiftDetails().getTotalAmount() instanceof Double) {
                    paymentDetails.setTotal_amount((double) response.getShiftDetails().getTotalAmount());
                }
            }
            if (response.getShiftDetails().getRate() != null) {
                if (response.getShiftDetails().getRate() instanceof Double) {
                    paymentDetails.setRate((double) response.getShiftDetails().getRate());
                }
            }

           /* try {

                CompletedBookingData completedBookingData = EventBus.getDefault().getStickyEvent(CompletedBookingData.class);
                completedBookingData.getSelectedBooking().setPaid(response.getShiftDetails().getPaid());
                completedBookingData.getSelectedBooking().setTotalAmount(response.getShiftDetails().getTotal_booking_amount().toString());

                completedBookingData.setTotalAmount(response.getTotalAmount());
                completedBookingData.setPaidBookingsCount(response.getPaidBookingsCount());
                completedBookingData.setUnpaidBookingsCount(response.getUnpaidBookingsCount());

                EventBus.getDefault().removeStickyEvent(CompletedBookingData.class);
                EventBus.getDefault().postSticky(completedBookingData);
            }catch (Exception e){
                e.printStackTrace();
            }*/
        }
    }

    @Override
    public void onGettingError() {
        showWarning(BookingDetailsActivity.this, "", getResources().getString(R.string.errorTimeOut), "error");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE) {
            mPresenter.onGetBookingDetails(bookingId);
        }
    }


    public void showRatingDialog(Activity activity) {
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .create();
        View dialogView;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialogView = inflater.inflate(R.layout.dialog_rate_pharmacy, null);

        TextView tv_pharmacyTitle = dialogView.findViewById(R.id.tv_pharmacyTitle);

//        if(tv_pharmacyTitle.getLineCount() == 2){
//            tv_pharmacyTitle.setPadding(0, 10, 0, 10);
//        }
        TextView tv_cancel = dialogView.findViewById(R.id.tv_cancel);
        TextView tv_done = dialogView.findViewById(R.id.tv_done);
        CircleImageView iv_logo = dialogView.findViewById(R.id.iv_logo);
        MaterialRatingBar ratingbar = dialogView.findViewById(R.id.ratingbar);




        for(int i=0;i<bookingDetailResponse.getShiftDetails().getSections().size();i++){
            for(int j=0;j<bookingDetailResponse.getShiftDetails().getSections().get(i).getDetails().size();j++){
                if (bookingDetailResponse.getShiftDetails().getSections().get(i).getDetails().get(j).getType().equalsIgnoreCase("Header")) {
                    tv_pharmacyTitle.setText(bookingDetailResponse.getShiftDetails().getSections().get(i).getDetails().get(j).getValue1());
                    if (bookingDetailResponse.getShiftDetails().getSections().get(i).getDetails().get(j).getImageUrl() != null) {
                        if (bookingDetailResponse.getShiftDetails().getSections().get(i).getDetails().get(j).getImageUrl() instanceof String) {
                            if (!((String) bookingDetailResponse.getShiftDetails().getSections().get(i).getDetails().get(j).getImageUrl()).equals("") && !((String) bookingDetailResponse.getShiftDetails().getSections().get(i).getDetails().get(j).getImageUrl()).equals(null)) {
                                Glide.with(BookingDetailsActivity.this).load((String) bookingDetailResponse.getShiftDetails().getSections().get(i).getDetails().get(j).getImageUrl()).into(iv_logo);
                            } else {
                                Glide.with(BookingDetailsActivity.this).load(R.drawable.pharmacy_placeholder).into(iv_logo);
                            }
                        } else {
                            Glide.with(BookingDetailsActivity.this).load(R.drawable.pharmacy_placeholder).into(iv_logo);
                        }
                    } else {
                        Glide.with(BookingDetailsActivity.this).load(R.drawable.pharmacy_placeholder).into(iv_logo);
                    }
                }

            }

        }


        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Prompt the user once explanation has been shown

                dialog.dismiss();
            }
        });


        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ratingPresenter.onAddRating(new RatingRequest().add(bookingId,int_rating));
            }
        });

        ratingbar.setOnRatingChangeListener(new MaterialRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
                Log.e("current Rating--",rating+"");
                int_rating=rating;
            }
        });

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setView(dialogView);
        dialog.show();
    }


    @Override
    public void onRatingSuccess(RatingResponse response) {
        showWarning(BookingDetailsActivity.this, "", response.getSuccess(), "");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.onGetBookingDetails(bookingId);
            }
        }, 200);

    }

    @Override
    public void onRatingFailure() {
        showWarning(BookingDetailsActivity.this, "", getResources().getString(R.string.errorTimeOut), "error");
    }


    @Override
    public void onBackPressed() {
        try {
            if (getIntent().getExtras() != null) {
                if(isFrom.equalsIgnoreCase("notification")&&key.equalsIgnoreCase("cancelled")) {
                    Intent mIntent=new Intent();
                    setResult(NOTIFICATION_RESULT_CODE,mIntent);
                    finish();
                }else if(isFrom.equalsIgnoreCase("notificationAdapter")&&key.equalsIgnoreCase("cancelled")) {
                    Intent intent = new Intent(this, LandingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    LandingActivity.openFragmentPosition = 4;
                    overridePendingTransitionExit();
                }
                else if(isFrom.equalsIgnoreCase("completed")) {
                    if(CompleteBookHistoryFragment.isUpdated){
                        Intent mIntent=new Intent();
                        setResult(UPDATE_RESULT_CODE,mIntent);
                        finish();
                    }else {
                        finish();
                    }
                }
                else{
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

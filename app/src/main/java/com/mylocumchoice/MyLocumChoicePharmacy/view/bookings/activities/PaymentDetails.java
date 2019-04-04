package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.PaymentDetailsModel;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.UpdatePaymentRequest;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.UpdatePaymentResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings.UpdatePaymentPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings.UpdatePaymentPresenterImpl;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.LockableScrollView;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment.CompleteBookHistoryFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface.UpdatePaymentView;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PaymentDetails extends AppActivity implements UpdatePaymentView {


    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_dots)
    ImageView tvDots;
    /**
     * ButterKnife Code
     **/
    @BindView(R.id.shadow1)
    View shadow1;
    @BindView(R.id.ll_shift)
    LinearLayout llShift;
    @BindView(R.id.et_shifts)
    EditText etShifts;
    @BindView(R.id.shadow2)
    View shadow2;
    @BindView(R.id.shadow3)
    View shadow3;
    @BindView(R.id.ll_unPaidBreaks)
    LinearLayout llUnPaidBreaks;
    @BindView(R.id.et_unPaidBreaks)
    EditText etUnPaidBreaks;
    @BindView(R.id.ll_extraHours)
    LinearLayout llExtraHours;
    @BindView(R.id.et_extraHours)
    EditText extraHours;
    @BindView(R.id.ll_otherPay)
    LinearLayout llOtherPayments;
    @BindView(R.id.et_otherPay)
    EditText etOtherPay;
    @BindView(R.id.shadow4)
    View shadow4;
    @BindView(R.id.shadow5)
    View shadow5;
    @BindView(R.id.shadow6)
    View shadow6;
    @BindView(R.id.btn_accept)
    TextView btnAccept;

    @BindView(R.id.scrollView)
    LockableScrollView scrollView;

    @BindView(R.id.extra)
    LinearLayout llExtra;

    @BindView(R.id.ll_frameLayout)
    LinearLayout llFrameLayout;
    /**
     * ButterKnife Code
     **/


    PaymentDetailsModel paymentDetailsModel = new PaymentDetailsModel();
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_clear_all)
    TextView tvClearAll;
    @BindView(R.id.ll_menuRight)
    FrameLayout llMenuRight;
    @BindView(R.id.tv_totalHours)
    TextView tvTotalHours;
    @BindView(R.id.tv_totalAmount)
    TextView tvTotalAmount;

    JsonObject jsonObject;

    int RESULT_CODE=2;


    double dbl_shifts, dbl_unpaid_breaks, dbl_extra_hours, dbl_other_payment, dbl_total_hours, dbl_total_amount, dbl_rate;

    double amount,extra,other;

    @BindView(R.id.tv_shiftHours)
    TextView tvShiftHours;
    @BindView(R.id.tv_unpaidHours)
    TextView tvUnpaidHours;
    @BindView(R.id.tv_extraHours)
    TextView tvExtraHours;

    int bookingId;


    UpdatePaymentPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);
        ButterKnife.bind(this);
        bookingId=getIntent().getIntExtra("bookingId",0);
        scrollView.setScrollingEnabled(false);
        setHeader();
        mPresenter=new UpdatePaymentPresenterImpl(this,this);
        paymentDetailsModel = EventBus.getDefault().getStickyEvent(PaymentDetailsModel.class);
        setData(paymentDetailsModel);

        KeyboardVisibilityEvent.setEventListener(this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if (isOpen) {
                            scrollView.setScrollingEnabled(true);
                            llExtra.setVisibility(View.VISIBLE);
                            llFrameLayout.setVisibility(View.GONE);
                            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) scrollView.getLayoutParams();
                            params.setMargins(0, 0, 0, 0);
                            scrollView.setLayoutParams(params);
                            DisplayMetrics displayMetrics = new DisplayMetrics();
                            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                        } else {
                            //scrollView.scrollTo(0,0);
                            llExtra.setVisibility(View.GONE);
                            llFrameLayout.setVisibility(View.VISIBLE);
                        }

                    }
                });


        listeners();
    }


    private void setData(PaymentDetailsModel paymentDetailsModel) {

        DecimalFormat df = new DecimalFormat("###.#");
        dbl_shifts = paymentDetailsModel.getDuration();
        dbl_unpaid_breaks = paymentDetailsModel.getUnpaid_hours();
        dbl_extra_hours = paymentDetailsModel.getExtra_hours();
        dbl_other_payment = paymentDetailsModel.getOther_payment();
        dbl_total_hours = paymentDetailsModel.getTotal_hours();
        dbl_total_amount = paymentDetailsModel.getTotal_amount();
        dbl_rate = paymentDetailsModel.getRate();

        etShifts.setText(df.format(dbl_shifts) + "");
        etUnPaidBreaks.setText(df.format(dbl_unpaid_breaks) + "");
        extraHours.setText(df.format(dbl_extra_hours) + "");
        etOtherPay.setText((String.valueOf(dbl_other_payment)));
        tvTotalHours.setText(df.format(dbl_total_hours) + " hrs");
        tvTotalAmount.setText(getResources().getString(R.string.pound) + dbl_total_amount);

        etShifts.setSelection(etShifts.getText().length());
        etUnPaidBreaks.setSelection(etUnPaidBreaks.getText().length());
        extraHours.setSelection(extraHours.getText().length());
        etOtherPay.setSelection(etOtherPay.getText().length());

        amount=dbl_unpaid_breaks;
        extra=dbl_extra_hours;
        other=dbl_other_payment;


    }


    private void listeners() {

        etUnPaidBreaks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    calculatePayment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        extraHours.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    calculatePayment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etOtherPay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    calculatePayment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.tv_back)
    public void onBackPress() {
        finish();
    }


    public void setHeader() {
        tvHeader.setText(getResources().getString(R.string.text_payment_details));
        tvDots.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.ll_shift)
    public void onSClick() {
        etShifts.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etShifts, InputMethodManager.SHOW_IMPLICIT);
    }

    @OnClick(R.id.ll_back)
    public void onBackClick() {
        finish();
    }

    @OnClick(R.id.ll_unPaidBreaks)
    public void onUpClick() {
        etUnPaidBreaks.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etUnPaidBreaks, InputMethodManager.SHOW_IMPLICIT);
    }

    @OnClick(R.id.ll_extraHours)
    public void onEhClick() {
        extraHours.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(extraHours, InputMethodManager.SHOW_IMPLICIT);
    }


    @OnClick(R.id.ll_otherPay)
    public void onOpClick() {
        etOtherPay.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etOtherPay, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    private void calculatePayment() {

        double duration = dbl_shifts;
        double unpaid_hours = dbl_unpaid_breaks;
        double extra_hours = dbl_extra_hours;
        double other_payment = dbl_other_payment;

        if (etUnPaidBreaks.getText() != null && etUnPaidBreaks.getText().toString().length() >= 1 && !etUnPaidBreaks.getText().toString().equals("")) {
            unpaid_hours = Double.valueOf(etUnPaidBreaks.getText().toString());
        } else if (etUnPaidBreaks.getText().toString().equals("")) {
            unpaid_hours = 0.0;
        }


        if (extraHours.getText() != null && extraHours.getText().toString().length() >= 1 && !extraHours.getText().toString().equals("")) {
            extra_hours = Double.valueOf(extraHours.getText().toString());
        } else if (extraHours.getText().toString().equals("")) {
            extra_hours = 0.0;
        }


        if (etOtherPay.getText() != null && etOtherPay.getText().toString().length() >= 1 && !etOtherPay.getText().toString().equals("")) {
            other_payment = Double.valueOf(etOtherPay.getText().toString());
        } else if (etOtherPay.getText().toString().equals("")) {
            other_payment = 0.0;
        }

        double hours = duration - unpaid_hours + extra_hours;

        double totalRate = hours * dbl_rate;
        double totalPayment = totalRate + other_payment;

        DecimalFormat df = new DecimalFormat("###.#");
        tvTotalHours.setText(df.format(hours) + " hrs");
        tvTotalAmount.setText(getResources().getString(R.string.pound) + totalPayment);



        amount=unpaid_hours;
        extra=extra_hours;
        other=other_payment;


    }


    private JsonObject getUpdatePaymentRequest(Double unpaid, Double extra, Double other) {
        UpdatePaymentRequest updatePaymentRequest = new UpdatePaymentRequest();
        return updatePaymentRequest.add(unpaid, extra, other);
    }

    @OnClick(R.id.btn_accept)
    public void onViewClicked() {
        jsonObject = getUpdatePaymentRequest(amount, extra, other);
        mPresenter.onUpdatePayment(bookingId,jsonObject);
    }

    @Override
    public void onPaymentUpdated(UpdatePaymentResponse response) {
        showWarning(PaymentDetails.this, "", response.getSuccess(), "");

        new Handler().postDelayed(new Runnable() {

            public void run() {

                try {
                    CompleteBookHistoryFragment.isUpdated = true;
                }catch (NullPointerException ne){
                    ne.printStackTrace();
                }

                Intent mIntent = new Intent();
                setResult(RESULT_CODE, mIntent);
                finish();
            }
        }, 200);
    }

    @Override
    public void onUpdateFailure() {
        showWarning(PaymentDetails.this, "", getResources().getString(R.string.errorTimeOut), "error");

    }
}

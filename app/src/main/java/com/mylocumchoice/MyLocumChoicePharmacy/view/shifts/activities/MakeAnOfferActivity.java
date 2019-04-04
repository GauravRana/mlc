package com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.MakeOfferRequest;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.ShiftAcceptResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.shifts.MakeOfferPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.shifts.MakeOfferPresenterImpl;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.viewinterface.ShiftOfferView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MakeAnOfferActivity extends AppActivity implements ShiftOfferView {


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
    @BindView(R.id.linearLayout1)
    LinearLayout linearLayout1;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.shadow1)
    View shadow1;
    @BindView(R.id.shadow2)
    View shadow2;
    @BindView(R.id.lay_upper)
    LinearLayout layUpper;
    @BindView(R.id.space2)
    TextView space2;
    @BindView(R.id.shadow3)
    View shadow3;
    @BindView(R.id.shadow4)
    View shadow4;
    @BindView(R.id.btn_accept)
    TextView btnAccept;
    @BindView(R.id.ll_frameLayout)
    LinearLayout llFrameLayout;
    @BindView(R.id.ll_rate)
    LinearLayout ll_rate;

    MakeOfferPresenter makeOfferPresenter;
    String rateValue = "";
    String mileageValue = "";
    int shift_id = -1;
    @BindView(R.id.et_milage)
    EditText etMilage;
    @BindView(R.id.tv_pound)
    TextView tvPound;
    @BindView(R.id.tv_perHour)
    TextView tvPerHour;

    int RESULT_CODE_OFFER = 12;

    /**
     * ButterKnife Code
     **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_an_offer);
        ButterKnife.bind(this);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setHeader();
        iniIntent();
        init();

        makeOfferPresenter = new MakeOfferPresenterImpl(this, MakeAnOfferActivity.this);
    }

    private void init() {
        editText.setText(rateValue);
        etMilage.setText(mileageValue);
        if(editText.getText().toString().equals("")){
            tvPound.setVisibility(View.GONE);
           // tvPerHour.setVisibility(View.GONE);
        }else{
            tvPound.setVisibility(View.VISIBLE);
           // tvPerHour.setVisibility(View.VISIBLE);
            try {
                editText.setSelection(editText.getText().toString().length());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(editText.getText().toString().equals("")){
                    tvPound.setVisibility(View.GONE);
                   // tvPerHour.setVisibility(View.GONE);
                }else{
                    tvPound.setVisibility(View.VISIBLE);
                    //tvPerHour.setVisibility(View.VISIBLE);
                }
                rateValue=editText.getText().toString();

            }
        });


    }

    public void iniIntent() {
        Bundle bundle = getIntent().getExtras();
        rateValue = bundle.getString("rate");
        mileageValue = bundle.getString("mileage");
        shift_id = bundle.getInt("id");
    }

    public void setHeader() {
        tvHeader.setText("Make an Offer");
        tvDots.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.ll_back)
    public void onBackClick() {
        onBackPressed();
    }

    @OnClick(R.id.linearLayout1)
    public void onLayoutClick() {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @OnClick(R.id.btn_accept)
    public void onViewClicked() {
        MakeOfferRequest makeOfferRequest = new MakeOfferRequest();
        makeOfferPresenter.onMakeShiftOffer(shift_id, makeOfferRequest.add(rateValue, etMilage.getText().toString()));
    }

    @Override
    public void onShiftOfferd(ShiftAcceptResponse shiftAcceptResponse) {
        showWarning(MakeAnOfferActivity.this, "", shiftAcceptResponse.getSuccess(), "");
        /*Intent i = new Intent(MakeAnOfferActivity.this, LandingActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);*/
        Intent mIntent=new Intent();
        setResult(RESULT_CODE_OFFER,mIntent);
        onBackPressed();
    }

    @Override
    public void onOfferError() {
        showWarning(MakeAnOfferActivity.this, "", getResources().getString(R.string.errorTimeOut), "error");
    }


}

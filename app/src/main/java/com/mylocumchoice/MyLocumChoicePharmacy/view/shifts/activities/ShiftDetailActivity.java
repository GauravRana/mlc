package com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.h6ah4i.android.materialshadowninepatch.MaterialShadowContainerView;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.AppliedShiftResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.eventmodels.AppliedShiftData;
import com.mylocumchoice.MyLocumChoicePharmacy.model.eventmodels.Openshiftdata;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.HidePharmacyResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShiftResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.ShiftAcceptResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.ShiftDetailsRes;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.shifts.HidePharmacyPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.shifts.HidePharmacyPresenterImpl;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.shifts.ShiftAcceptPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.shifts.ShiftAcceptPresenterImpl;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.shifts.ShiftDetailsPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.shifts.ShiftDetailsPresenterImpl;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ApiListener;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.DialogClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.GlobalConstants;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.CustomDialog;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities.MapActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities.SearchActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment.ShiftDetailAppliedFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment.ShiftDetailInviteFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment.ShiftDetailUpcmingFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.login.activities.TcTerms;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.adapters.OpenShiftAdapterSections;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.viewinterface.HidePharmacyView;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.viewinterface.ShiftAcceptView;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.viewinterface.ShiftDetailView;
import com.mylocumchoice.MyLocumChoicePharmacy.view.signup.activities.AccountDetActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ShiftDetailActivity extends AppActivity implements ShiftDetailUpcmingFragment.OnFragmentInteractionListener,
        ShiftDetailInviteFragment.OnFragmentInteractionListener, ShiftDetailAppliedFragment.OnFragmentInteractionListener, ShiftDetailView, ShiftAcceptView, ApiListener, HidePharmacyView {
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
    @BindView(R.id.scroll_View)
    NestedScrollView scrollView;
    private int state;
    private int kind;
    private int shift_id;
    private String isFrom="";
    @BindView(R.id.ll_contact)
    LinearLayout llContact;
    @BindView(R.id.ll_call)
    LinearLayout llCall;
    @BindView(R.id.iv_Call)
    ImageView ivCall;
    @BindView(R.id.ll_Email)
    LinearLayout llEmail;
    @BindView(R.id.iv_Email)
    ImageView ivEmail;
    @BindView(R.id.ll_EEmail)
    LinearLayout llEEmail;
    @BindView(R.id.iv_Emergency)
    ImageView ivEmergency;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_bottom)
    TextView tvBottom;
    @BindView(R.id.btn_Shadow)
    MaterialShadowContainerView btnShadow;
    @BindView(R.id.tv_SignUp)
    TextView tvSignUp;
    @BindView(R.id.ll_frameLayout)
    LinearLayout llFrameLayout;
    @BindView(R.id.btn_accept)
    TextView btnAccept;
    @BindView(R.id.ll_half)
    LinearLayout llHalf;
    @BindView(R.id.btn_half_acc)
    TextView btnHalfAcc;
    @BindView(R.id.btn_half_offer)
    TextView btnHalfOffer;
    /**
     * ButterKnife Code
     **/

    ShiftDetailsPresenter mPresenter;

    private Response<ShiftDetailsRes> response;

    private ArrayList<ShiftDetailsRes.Section> arrayList = new ArrayList<>();
    private OpenShiftAdapterSections adapter;

    List<ShiftDetailsRes.OtherOption> lv_others;
    Utils mUtils;
    String dist = "";
    String rateValue = "";
    String mileageValue = "";

    ShiftAcceptPresenter mShiftAcceptPresenter;
    HidePharmacyPresenter mHidePharmacyPresenter;

    int REQUEST_CODE_OFFER = 11;
    int RESULT_CODE_OFFER = 12;
    public int NOTIFICATION_RESULT_CODE=1001;

    int notification_id;
    String key="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_detail);
        ButterKnife.bind(this);
        mUtils = new Utils();
        iniIntent();
        setData();
        recyclerView.setNestedScrollingEnabled(false);
        mPresenter = new ShiftDetailsPresenterImpl(this, ShiftDetailActivity.this);

        if(isFrom.equalsIgnoreCase("OpenShiftFragment")) {
            mPresenter.onGetShiftDetails(shift_id);
            setHeader("Shift Details");
        }else if(isFrom.equalsIgnoreCase("ShiftDetailInviteFragment")){
            setHeader("Shift Details");
            mPresenter.onGetInviteShiftDetails(shift_id);
        }else if(isFrom.equalsIgnoreCase("ShiftDetailAppliedFragment")){
            setHeader("Shift Details");
            mPresenter.onGetAppliedShiftDetails(shift_id);
        }else if(isFrom.equalsIgnoreCase("notification")||isFrom.equalsIgnoreCase("notificationAdapter")){
            if(key.equalsIgnoreCase("shift_invitation")){
                setHeader("Shift Details");
                mPresenter.onGetInviteShiftDetailsNotification(shift_id,notification_id);
            }else if(key.equalsIgnoreCase("open_shift")){
                setHeader("Shift Details");
                mPresenter.onGetShiftDetailsNotification(shift_id,notification_id);
            }
        }

        mHidePharmacyPresenter = new HidePharmacyPresenterImpl(this, ShiftDetailActivity.this);
        mShiftAcceptPresenter = new ShiftAcceptPresenterImpl(this, ShiftDetailActivity.this);

    }

    public void setHeader(String title) {
        tvHeader.setText(title);
    }


    private void setData() {
        if (isFrom.equalsIgnoreCase("OpenShiftFragment")||isFrom.equalsIgnoreCase("ShiftDetailInviteFragment")) {
            //layoutCancelBooking.setVisibility(View.GONE);
            tvBottom.setVisibility(View.GONE);
        }else if(isFrom.equalsIgnoreCase("notification")||isFrom.equalsIgnoreCase("notificationAdapter")){
            if(key.equalsIgnoreCase("shift_invitation")){
                tvBottom.setVisibility(View.GONE);
            }else if(key.equalsIgnoreCase("open_shift")){
                tvBottom.setVisibility(View.GONE);
            }
        } else {
            tvBottom.setVisibility(View.VISIBLE);
            //layoutCancelBooking.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.ll_menuRight)
    public void click() {
        try {
            if (lv_others != null) {
                int type = 0;
                if (lv_others.size() == 0) {

                } else if (lv_others.size() == 1) {
                    type = 3;
                } else if (lv_others.size() == 2) {
                    type = 2;
                } else if (lv_others.size() == 3) {
                    type = 1;
                }

                List<String> lv_options = new ArrayList<>();
                for (int i = 0; i < lv_others.size(); i++) {
                    lv_options.add(lv_others.get(i).getTitle());
                }

                CustomDialog customDialog = new CustomDialog(this
                        , type, lv_options, new DialogClickListener() {
                    @Override
                    public void onMapClick(String title) {

                        for (int i = 0; i < lv_others.size(); i++) {
                            if (title.equalsIgnoreCase(lv_others.get(i).getTitle())) {
                                if (title.equalsIgnoreCase("Hide me from this Pharmacy")) {
                                    mUtils.showDialog(ShiftDetailActivity.this, "", getResources().getString(R.string.confirm_hide), GlobalConstants.ConfirmHidePharmacy, ShiftDetailActivity.this);

                                } else if (title.equalsIgnoreCase("Payment Policy")) {
                                    if (lv_others.get(i).getValue() != null) {
                                        if (lv_others.get(i).getValue() instanceof String) {
                                            Bundle bundle = new Bundle();
                                            bundle.putString("isTo", "payment");
                                            bundle.putString("value", (String) lv_others.get(i).getValue());
                                            startActivityWithBundle(TcTerms.class, bundle);
                                        }
                                    }
                                } else if (title.equalsIgnoreCase("Cancellation Policy")) {
                                    if (lv_others.get(i).getValue() != null) {
                                        if (lv_others.get(i).getValue() instanceof String) {
                                            Bundle bundle = new Bundle();
                                            bundle.putString("isTo", "cancel");
                                            bundle.putString("value", (String) lv_others.get(i).getValue());
                                            startActivityWithBundle(TcTerms.class, bundle);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onOpenMapClick(String title) {


                        for (int i = 0; i < lv_others.size(); i++) {
                            if (title.equalsIgnoreCase(lv_others.get(i).getTitle())) {
                                if (title.equalsIgnoreCase("Hide me from this Pharmacy")) {
                                    mUtils.showDialog(ShiftDetailActivity.this, "", getResources().getString(R.string.confirm_hide), GlobalConstants.ConfirmHidePharmacy, ShiftDetailActivity.this);

                                } else if (title.equalsIgnoreCase("Payment Policy")) {
                                    if (lv_others.get(i).getValue() != null) {
                                        if (lv_others.get(i).getValue() instanceof String) {
                                            Bundle bundle = new Bundle();
                                            bundle.putString("isTo", "payment");
                                            bundle.putString("value", (String) lv_others.get(i).getValue());
                                            startActivityWithBundle(TcTerms.class, bundle);
                                        }
                                    }
                                } else if (title.equalsIgnoreCase("Cancellation Policy")) {
                                    if (lv_others.get(i).getValue() != null) {
                                        if (lv_others.get(i).getValue() instanceof String) {
                                            Bundle bundle = new Bundle();
                                            bundle.putString("isTo", "cancel");
                                            bundle.putString("value", (String) lv_others.get(i).getValue());
                                            startActivityWithBundle(TcTerms.class, bundle);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onThirdClick(String title) {
                        for (int i = 0; i < lv_others.size(); i++) {
                            if (title.equalsIgnoreCase(lv_others.get(i).getTitle())) {
                                if (title.equalsIgnoreCase("Hide me from this Pharmacy")) {
                                    mUtils.showDialog(ShiftDetailActivity.this, "", getResources().getString(R.string.confirm_hide), GlobalConstants.ConfirmHidePharmacy, ShiftDetailActivity.this);

                                } else if (title.equalsIgnoreCase("Payment Policy")) {
                                    if (lv_others.get(i).getValue() != null) {
                                        if (lv_others.get(i).getValue() instanceof String) {
                                            Bundle bundle = new Bundle();
                                            bundle.putString("isTo", "payment");
                                            bundle.putString("value", (String) lv_others.get(i).getValue());
                                            startActivityWithBundle(TcTerms.class, bundle);
                                        }
                                    }
                                } else if (title.equalsIgnoreCase("Cancellation Policy")) {
                                    if (lv_others.get(i).getValue() != null) {
                                        if (lv_others.get(i).getValue() instanceof String) {
                                            Bundle bundle = new Bundle();
                                            bundle.putString("isTo", "cancel");
                                            bundle.putString("value", (String) lv_others.get(i).getValue());
                                            startActivityWithBundle(TcTerms.class, bundle);
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
                customDialog.show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @OnClick(R.id.ll_call)
    public void onCallPress() {
        Uri number = Uri.parse("tel:123456789");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }


    @OnClick(R.id.ll_Email)
    public void onEmailPress() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"recipient@example.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
        i.putExtra(Intent.EXTRA_TEXT, "body of email");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (ActivityNotFoundException ex) {
            showWarning(ShiftDetailActivity.this, "", "There are no email clients installed.", "error");
        }

    }

    @OnClick(R.id.ll_EEmail)
    public void onEmerPress() {
        Uri number = Uri.parse("tel:123456789");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);

    }

    @OnClick(R.id.ll_back)
    public void onBackPress() {
        try {
            if (getIntent().getExtras() != null) {
                if (isFrom.equalsIgnoreCase("notification")) {
                    Intent mIntent=new Intent();
                    setResult(NOTIFICATION_RESULT_CODE,mIntent);
                    finish();
                }else if(isFrom.equalsIgnoreCase("notificationAdapter")){
                    Intent intent = new Intent(this, LandingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    LandingActivity.openFragmentPosition = 4;
                    overridePendingTransitionExit();
                } else {
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


    @Override
    public void onFragmentInteraction(int position) {

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public void showLayout() {
        llFrameLayout.setVisibility(View.GONE);
    }

    public void iniIntent() {
        Bundle bundle = getIntent().getExtras();
        isFrom = getIntent().getStringExtra("isFrom");
        if(isFrom.equals("")||isFrom==null){
            isFrom=getIntent().getExtras().get("isFrom").toString();
        }
        if (isFrom.equalsIgnoreCase("notification")||isFrom.equalsIgnoreCase("notificationAdapter")) {
            shift_id = getIntent().getIntExtra("id", 0);
            notification_id = getIntent().getIntExtra("notification_id", 0);
            key = getIntent().getStringExtra("key");
            dist = "0.0";
        } else {
            dist = bundle.getString("distance", "");
            kind = bundle.getInt("isEmergency", 0);
            state = bundle.getInt("state", 0);
            shift_id = bundle.getInt("id", 0);
        }
    }

    @Override
    public void onDetailsFetched(ShiftDetailsRes response) {
        Log.e("Detail Response-- ", response + "");

        if (isFrom.equalsIgnoreCase("OpenShiftFragment") ||
                isFrom.equalsIgnoreCase("ShiftDetailInviteFragment")) {
            //layoutCancelBooking.setVisibility(View.GONE);

            lv_others = response.getShiftDetails().getOtherOptions();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            for (int i = 0; i < response.getShiftDetails().getSections().size(); i++) {
                arrayList = response.getShiftDetails().getSections();
            }
            OpenShiftAdapterSections adapter = new OpenShiftAdapterSections(this, response, arrayList, dist,false);
            recyclerView.setAdapter(adapter);

            if (response.getShiftDetails().getRate_value() != null) {
                if (response.getShiftDetails().getRate_value() instanceof String) {
                    rateValue = (String) response.getShiftDetails().getRate_value();
                }
            }

            for(int i=0;i<response.getShiftDetails().getSections().size();i++){
                for(int j=0;j<response.getShiftDetails().getSections().get(i).getDetails().size();j++){
                    if ((response.getShiftDetails().getSections().get(i).getDetails().get(j).getType()).equalsIgnoreCase("Other")) {
                        if (response.getShiftDetails().getSections().get(i).getDetails().get(j).getValue1() instanceof String) {
                            if(((String) response.getShiftDetails().getSections().get(i).getDetails().get(j).getValue1()).equalsIgnoreCase("Mileage \u0026 Other Perks")
                                    ||((String) response.getShiftDetails().getSections().get(i).getDetails().get(j).getValue1()).equalsIgnoreCase("Mileage & Other Perks")){
                                    mileageValue = response.getShiftDetails().getSections().get(i).getDetails().get(j).getValue2();
                            }
                        }
                    }
                }
            }

            if (!response.getShiftDetails().getEmergency()) {
                btnAccept.setVisibility(View.VISIBLE);
                llHalf.setVisibility(View.GONE);
                if (response.getShiftDetails().getAccept()) {
                    btnAccept.setText(getResources().getString(R.string.accept));
                } else if (response.getShiftDetails().getApply()) {
                    btnAccept.setText(getResources().getString(R.string.apply));
                }
            } else {
                btnAccept.setVisibility(View.GONE);
                llHalf.setVisibility(View.VISIBLE);
                if (response.getShiftDetails().getAccept()) {
                    btnHalfAcc.setText(getResources().getString(R.string.accept));
                } else if (response.getShiftDetails().getApply()) {
                    btnHalfAcc.setText(getResources().getString(R.string.apply));
                }
            }
        } else if (isFrom.equalsIgnoreCase("ShiftDetailAppliedFragment")) {
            if (state == 0) {
                btnAccept.setVisibility(View.VISIBLE);
                llHalf.setVisibility(View.GONE);
                btnAccept.setText(getResources().getString(R.string.cancel));
                tvBottom.setVisibility(View.GONE);
            } else if (state == 1) {
                btnAccept.setVisibility(View.GONE);
                llHalf.setVisibility(View.GONE);
                tvBottom.setVisibility(View.GONE);
                llFrameLayout.setVisibility(View.GONE);
            }


            lv_others = response.getShiftDetails().getOtherOptions();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            for (int i = 0; i < response.getShiftDetails().getSections().size(); i++) {
                arrayList = response.getShiftDetails().getSections();
            }

            boolean isDeclined=false;
            if(state==0){
                isDeclined=false;
            }else{
                isDeclined=true;
            }
            adapter = new OpenShiftAdapterSections(this, response, arrayList, dist,isDeclined);
            recyclerView.setAdapter(adapter);

            if (response.getShiftDetails().getRate_value() != null) {
                if (response.getShiftDetails().getRate_value() instanceof String) {
                    rateValue = (String) response.getShiftDetails().getRate_value();
                }
            }

            for(int i=0;i<response.getShiftDetails().getSections().size();i++){
                for(int j=0;j<response.getShiftDetails().getSections().get(i).getDetails().size();j++){
                    if ((response.getShiftDetails().getSections().get(i).getDetails().get(j).getType()).equalsIgnoreCase("Other")) {
                        if (response.getShiftDetails().getSections().get(i).getDetails().get(j).getValue1() instanceof String) {
                            if(((String) response.getShiftDetails().getSections().get(i).getDetails().get(j).getValue1()).equalsIgnoreCase("Mileage \u0026 Other Perks")
                                    ||((String) response.getShiftDetails().getSections().get(i).getDetails().get(j).getValue1()).equalsIgnoreCase("Mileage & Other Perks")){
                                mileageValue = response.getShiftDetails().getSections().get(i).getDetails().get(j).getValue2();
                            }
                        }
                    }
                }
            }

                /*layoutCancelBooking.setVisibility(View.GONE);
                ll_price.setGravity(Gravity.CENTER|Gravity.RIGHT);
                tv_neg.setVisibility(View.INVISIBLE);*/
        } else if (isFrom.equalsIgnoreCase("notification")||isFrom.equalsIgnoreCase("notificationAdapter")) {
            lv_others = response.getShiftDetails().getOtherOptions();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            for (int i = 0; i < response.getShiftDetails().getSections().size(); i++) {
                arrayList = response.getShiftDetails().getSections();
            }
            OpenShiftAdapterSections adapter = new OpenShiftAdapterSections(this, response, arrayList, dist,false);
            recyclerView.setAdapter(adapter);

            if (response.getShiftDetails().getRate_value() != null) {
                if (response.getShiftDetails().getRate_value() instanceof String) {
                    rateValue = (String) response.getShiftDetails().getRate_value();
                }
            }

            for(int i=0;i<response.getShiftDetails().getSections().size();i++){
                for(int j=0;j<response.getShiftDetails().getSections().get(i).getDetails().size();j++){
                    if ((response.getShiftDetails().getSections().get(i).getDetails().get(j).getType()).equalsIgnoreCase("Other")) {
                        if (response.getShiftDetails().getSections().get(i).getDetails().get(j).getValue1() instanceof String) {
                            if(((String) response.getShiftDetails().getSections().get(i).getDetails().get(j).getValue1()).equalsIgnoreCase("Mileage \u0026 Other Perks")
                                    ||((String) response.getShiftDetails().getSections().get(i).getDetails().get(j).getValue1()).equalsIgnoreCase("Mileage & Other Perks")){
                                mileageValue = response.getShiftDetails().getSections().get(i).getDetails().get(j).getValue2();
                            }
                        }
                    }
                }
            }

            if (!response.getShiftDetails().getEmergency()) {
                btnAccept.setVisibility(View.VISIBLE);
                llHalf.setVisibility(View.GONE);
                if (response.getShiftDetails().getAccept()) {
                    btnAccept.setText(getResources().getString(R.string.accept));
                } else if (response.getShiftDetails().getApply()) {
                    btnAccept.setText(getResources().getString(R.string.apply));
                }
            } else {
                btnAccept.setVisibility(View.GONE);
                llHalf.setVisibility(View.VISIBLE);
                if (response.getShiftDetails().getAccept()) {
                    btnHalfAcc.setText(getResources().getString(R.string.accept));
                } else if (response.getShiftDetails().getApply()) {
                    btnHalfAcc.setText(getResources().getString(R.string.apply));
                }
            }
        }
    }

    @Override
    public void onGettingError() {
        showWarning(ShiftDetailActivity.this, "", getResources().getString(R.string.errorTimeOut), "error");
    }

    @OnClick({R.id.btn_accept, R.id.btn_half_acc, R.id.btn_half_offer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_accept:
                if (btnAccept.getText().toString().equalsIgnoreCase(getResources().getString(R.string.accept))) {
                    mShiftAcceptPresenter.onShiftAccept(shift_id);
                } else if (btnAccept.getText().toString().equalsIgnoreCase(getResources().getString(R.string.apply))) {
                    mShiftAcceptPresenter.onShiftApply(shift_id);
                } else if (btnAccept.getText().toString().equalsIgnoreCase(getResources().getString(R.string.cancel))) {
                    mUtils.showDialog(ShiftDetailActivity.this, "", getResources().getString(R.string.confirm_cancel), GlobalConstants.ConfirmCancel,ShiftDetailActivity.this);
                }
                break;
            case R.id.btn_half_acc:
                if (btnHalfAcc.getText().toString().equalsIgnoreCase(getResources().getString(R.string.accept))) {
                    mShiftAcceptPresenter.onShiftAccept(shift_id);
                } else if (btnHalfAcc.getText().toString().equalsIgnoreCase(getResources().getString(R.string.apply))) {
                    mShiftAcceptPresenter.onShiftApply(shift_id);
                }
                break;
            case R.id.btn_half_offer:
                Bundle bundle = new Bundle();
                bundle.putString("rate", rateValue);
                bundle.putString("mileage", mileageValue);
                bundle.putInt("id", shift_id);
                Intent mIntent = new Intent(this, MakeAnOfferActivity.class);
                mIntent.putExtras(bundle);
                startActivityForResult(mIntent, REQUEST_CODE_OFFER);
                break;
        }
    }

    @Override
    public void onShiftAccepted(ShiftAcceptResponse shiftAcceptResponse) {
        //if (shiftAcceptResponse.getSuccess())
        showWarning(ShiftDetailActivity.this, "", shiftAcceptResponse.getSuccess(), "");
        /*Intent i = new Intent(ShiftDetailActivity.this, LandingActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                try {
                    Openshiftdata openshiftdata = EventBus.getDefault().getStickyEvent(Openshiftdata.class);
                    ArrayList<OpenShiftResponse.Shift> saved_shifts = openshiftdata.getAl_shifts();


                    for (int i = 0; i < saved_shifts.size(); i++) {
                        if (shift_id == saved_shifts.get(i).getId()) {
                            saved_shifts.remove(i);
                        }
                    }

                    openshiftdata.setAl_shifts(null);
                    openshiftdata.setAl_shifts(saved_shifts);
                    EventBus.getDefault().removeStickyEvent(Openshiftdata.class);
                    EventBus.getDefault().postSticky(openshiftdata);
                }catch (NullPointerException ne){
                    ne.printStackTrace();
                }
                finish();
            }
        }, 1000);

    }

    @Override
    public void onAcceptError() {
        showWarning(ShiftDetailActivity.this, "", getResources().getString(R.string.errorTimeOut), "error");
    }

    @Override
    public void onShiftApplied(ShiftAcceptResponse shiftAcceptResponse) {
        showWarning(ShiftDetailActivity.this, "", shiftAcceptResponse.getSuccess(), "");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                try {
                    Openshiftdata openshiftdata = EventBus.getDefault().getStickyEvent(Openshiftdata.class);
                    ArrayList<OpenShiftResponse.Shift> saved_shifts = openshiftdata.getAl_shifts();


                    for (int i = 0; i < saved_shifts.size(); i++) {
                        if (shift_id == saved_shifts.get(i).getId()) {
                            saved_shifts.remove(i);
                        }
                    }

                    openshiftdata.setAl_shifts(null);
                    openshiftdata.setAl_shifts(saved_shifts);
                    EventBus.getDefault().removeStickyEvent(Openshiftdata.class);
                    EventBus.getDefault().postSticky(openshiftdata);
                }catch (NullPointerException ne){
                    ne.printStackTrace();
                }
                finish();
            }
        }, 1000);
//        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onApplyError() {
        showWarning(ShiftDetailActivity.this, "", getResources().getString(R.string.errorTimeOut), "error");
    }

    @Override
    public void onShiftCanceled(ShiftAcceptResponse shiftAcceptResponse) {
        showWarning(ShiftDetailActivity.this, "", shiftAcceptResponse.getSuccess(), "");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                try {
                    AppliedShiftData appliedShiftData = EventBus.getDefault().getStickyEvent(AppliedShiftData.class);
                    List<AppliedShiftResponse.Shift> saved_shifts = appliedShiftData.getAl_shifts();

                    for (int i = 0; i < saved_shifts.size(); i++) {
                        if (shift_id == saved_shifts.get(i).getId()) {
                            saved_shifts.remove(i);
                        }
                    }

                    appliedShiftData.setAl_shifts(null);
                    appliedShiftData.setAl_shifts(saved_shifts);
                    EventBus.getDefault().removeStickyEvent(AppliedShiftData.class);
                    EventBus.getDefault().postSticky(appliedShiftData);
                }catch (NullPointerException ne){
                    ne.printStackTrace();
                }

                finish();
            }
        }, 1000);
    }

    @Override
    public void onCancelError() {
        showWarning(ShiftDetailActivity.this, "", getResources().getString(R.string.errorTimeOut), "error");
    }

    @Override
    public void onHitApi(boolean key) {
            if (key) {
                mHidePharmacyPresenter.onHidePharmacy(shift_id);
            }else{
                mShiftAcceptPresenter.onBookingCancel(shift_id);
            }
    }

    @Override
    public void onSuccessfulHidden(HidePharmacyResponse hidePharmacyResponse) {
        showWarning(ShiftDetailActivity.this, "", hidePharmacyResponse.getSuccess(), "");
        //finish();
    }

    @Override
    public void onHideFailure() {
        showWarning(ShiftDetailActivity.this, "", getResources().getString(R.string.errorTimeOut), "error");
        //finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_CODE_OFFER && resultCode==RESULT_CODE_OFFER){

            try {
                Openshiftdata openshiftdata = EventBus.getDefault().getStickyEvent(Openshiftdata.class);
                ArrayList<OpenShiftResponse.Shift> saved_shifts = openshiftdata.getAl_shifts();


                for (int i = 0; i < saved_shifts.size(); i++) {
                    if (shift_id == saved_shifts.get(i).getId()) {
                        saved_shifts.remove(i);
                    }
                }

                openshiftdata.setAl_shifts(null);
                openshiftdata.setAl_shifts(saved_shifts);
                EventBus.getDefault().removeStickyEvent(Openshiftdata.class);
                EventBus.getDefault().postSticky(openshiftdata);
            }catch (NullPointerException ne){
                ne.printStackTrace();
            }

            finish();
        }
    }

    @Override
    public void onBackPressed() {
        try {
            if (getIntent().getExtras() != null) {
                if (isFrom.equalsIgnoreCase("notification")) {
                    Intent mIntent=new Intent();
                    setResult(NOTIFICATION_RESULT_CODE,mIntent);
                    finish();
                }else if(isFrom.equalsIgnoreCase("notificationAdapter")){
                    Intent intent = new Intent(this, LandingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    LandingActivity.openFragmentPosition = 4;
                    overridePendingTransitionExit();
                } else {
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

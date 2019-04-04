package com.mylocumchoice.MyLocumChoicePharmacy.view.signup.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.utils.SignOutAlert;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.login.activities.LoginActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.model.signup.RegisterReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.signup.RegisterRes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.signup.SignupVP;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.signup.SignupPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.LockableScrollView;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ProcessDialog;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import org.json.JSONObject;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterActivity extends BaseActivity implements SignupVP.View{
    @BindView(R.id.scView)
    RelativeLayout scView;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.tv_gphc)
    TextView tvGphc;
    @BindView(R.id.et_gphc)
    EditText etGphc;
    @BindView(R.id.tv_w_Gphc)
    TextView tvWGphc;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.tv_SignIN)
    TextView tvSignIN;
    @BindView(R.id.ll_Expiry)
    LinearLayout llExpiry;
    @BindView(R.id.tv_Exp)
    TextView tvExp;
    @BindView(R.id.tv_w_exp)
    TextView tvWExp;
    @BindView(R.id.scrollView)
    LockableScrollView scrollView;

    public static TextView etExp;

    private boolean isSignedInClicked = false;

    Utils mUtils;


    @BindView(R.id.extra)
    LinearLayout llExtra;

    private ProcessDialog dialog;

    final Calendar today = Calendar.getInstance();
    public static final String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    SignupPresenter mSignupPresenter;
    RegisterReq registerReq;
    private SignOutAlert signOutAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        mUtils=new Utils();
        mSignupPresenter=new SignupPresenter(this,RegisterActivity.this);
        scrollView.setScrollingEnabled(false);
        etExp = (TextView) findViewById(R.id.et_exp);
        //setupUI(scView);
        etGphc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isSignedInClicked) {
                    if (etGphc.getText().length() != 7
                            || Integer.parseInt(etGphc.getText().toString()) < 2000000
                            || Integer.parseInt(etGphc.getText().toString()) > 3000000) {
                        tvWGphc.setVisibility(View.VISIBLE);
                        tvWGphc.setText(getResources().getString(R.string.text_war_gphc));
                    }else{
                        tvWGphc.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        KeyboardVisibilityEvent.setEventListener(this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if(isOpen){
                            scrollView.setScrollingEnabled(true);
                            llExtra.setVisibility(View.VISIBLE);
                        }else {
                            scrollView.setScrollingEnabled(false);
                            llExtra.setVisibility(View.GONE);
                        }

                    }
                });

        if(etGphc.hasFocus()){
            make_visible(etGphc);
        }
        if(etExp.hasFocus()){
            make_visible(etExp);
        }

    }

    @OnClick(R.id.tv_next)
    public void onButtonClick(View view) {
        isSignedInClicked = true;
        if (etGphc.getText().toString().equalsIgnoreCase("")) {
            tvWGphc.setVisibility(View.VISIBLE);
        }else if(!etGphc.getText().toString().equalsIgnoreCase("")){
            if (etGphc.getText().length() != 7
                    || Integer.parseInt(etGphc.getText().toString()) < 2000000
                    || Integer.parseInt(etGphc.getText().toString()) > 3000000) {
                tvWGphc.setVisibility(View.VISIBLE);
                tvWGphc.setText(getResources().getString(R.string.text_war_gphc));
            }else{
                mSignupPresenter.onSignupRequest(getRegisterReq(etGphc.getText().toString().trim()/*, etExp.getText().toString().trim()*/));
                //callAPI(etGphc.getText().toString().trim(), etExp.getText().toString().trim());
            }
        }
        else {
            mSignupPresenter.onSignupRequest(getRegisterReq(etGphc.getText().toString().trim()/*, etExp.getText().toString().trim()*/));
            //callAPI(etGphc.getText().toString().trim(), etExp.getText().toString().trim());
        }

        hideSoftKeyboard();
    }


    @OnClick(R.id.tv_SignIN)
    public void onSignClick(View view) {
        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }


    @OnClick(R.id.tv_gphc)
    public void onGClick(View view) {
        etGphc.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etGphc, InputMethodManager.SHOW_IMPLICIT);
    }

//    @OnClick(R.id.tv_Exp)
//    public void onEClick(View view) {
//        etExp.requestFocus();
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(etExp, InputMethodManager.SHOW_IMPLICIT);
//    }

    @OnClick(R.id.ll_Expiry)
    public void onExpiryClick() {
        tvWExp.setVisibility(View.GONE);
        /*DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");*/
        hideSoftKeyboard();
       // Utils.DatePickerFragment.newInstance("register",RegisterActivity.this).show(getSupportFragmentManager(),"datePicker");
        //mUtils.fn_showCalendar("register",RegisterActivity.this);
        mUtils.fn_showDatePicker("","register",RegisterActivity.this);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void make_visible(View view)    {
        int vt = view.getTop();
        int vb = view.getBottom();

        View v = view;

        for(;;)
        {
            ViewParent vp = v.getParent();

            if(vp == null || !(vp instanceof ViewGroup))
                break;

            ViewGroup parent = (ViewGroup)vp;

            if(parent instanceof ScrollView)
            {
                ScrollView sv = (ScrollView)parent;

                // Code based on ScrollView.computeScrollDeltaToGetChildRectOnScreen(Rect rect) (Android v5.1.1):

                int height = sv.getHeight();
                int screenTop = sv.getScrollY();
                int screenBottom = screenTop + height;

                int fadingEdge = sv.getVerticalFadingEdgeLength();

                // leave room for top fading edge as long as rect isn't at very top
                if(vt > 0)
                    screenTop += fadingEdge;

                // leave room for bottom fading edge as long as rect isn't at very bottom
                if(vb < sv.getChildAt(0).getHeight())
                    screenBottom -= fadingEdge;

                int scrollYDelta = 0;

                if(vb > screenBottom && vt > screenTop)
                {
                    // need to move down to get it in view: move down just enough so
                    // that the entire rectangle is in view (or at least the first
                    // screen size chunk).

                    if(vb-vt > height) // just enough to get screen size chunk on
                        scrollYDelta += (vt - screenTop);
                    else              // get entire rect at bottom of screen
                        scrollYDelta += (vb - screenBottom);

                    // make sure we aren't scrolling beyond the end of our content
                    int bottom = sv.getChildAt(0).getBottom();
                    int distanceToBottom = bottom - screenBottom;
                    scrollYDelta = Math.min(scrollYDelta, distanceToBottom);
                }
                else if(vt < screenTop && vb < screenBottom)
                {
                    // need to move up to get it in view: move up just enough so that
                    // entire rectangle is in view (or at least the first screen
                    // size chunk of it).

                    if(vb-vt > height)    // screen size chunk
                        scrollYDelta -= (screenBottom - vb);
                    else                  // entire rect at top
                        scrollYDelta -= (screenTop - vt);

                    // make sure we aren't scrolling any further than the top our content
                    scrollYDelta = Math.max(scrollYDelta, -sv.getScrollY());
                }

                sv.smoothScrollBy(0, scrollYDelta);
                break;
            }

            // Transform coordinates to parent:
            int dy = parent.getTop()-parent.getScrollY();
            vt += dy;
            vb += dy;

            v = parent;
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(RegisterActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }


    private RegisterReq getRegisterReq(String gphc/*, String gphc_expiry*/){
        registerReq=new RegisterReq();
        //registerReq.setGphc_expiry_date(gphc_expiry);
        registerReq.setGphcNo(gphc);
        return registerReq;
    }


    @Override
    public void onSignupSuccess(Response<RegisterRes> response) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Log.e("PASSWORD_ACTIVITY", "response: "+gson.toJson(response.body()));
        Log.d("RETROFIT",response.message());
        if(response.code() == 200){
            try {
                String name = response.body().getName().toString();
                String gphc_no = response.body().getGphcNo().toString();
                Bundle bundle = new Bundle();
                bundle.putString("gphc", gphc_no);
                bundle.putString("name", name);
                //bundle.putString("gphc_expiry", registerReq.getGphc_expiry_date());
                bundle.putBoolean("isNameSent", true);
                startActivityWithBundle(AccountDetActivity.class, bundle);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        /*else if(response.code() == 204){
            try {
                Bundle bundle = new Bundle();
                bundle.putString("gphc", etGphc.getText().toString());
                //bundle.putString("gphc_expiry", registerReq.getGphc_expiry_date());
                startActivityWithBundle(AccountDetActivity.class, bundle);
            }catch (Exception e){
                e.printStackTrace();
            }

        }*/else if(response.code() == 422){
            try {
                String errorString;
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                errorString = jObjError.get("error").toString();
                showWarning(RegisterActivity.this, "",   errorString,"error");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else if (response.code() == 401) {
            try {
                signOutAlert = SignOutAlert.newInstance(this,getResources().getString(R.string.text_sign_out), "");
                signOutAlert.setCancelable(false);
                signOutAlert.show(getSupportFragmentManager(), "dialog");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            try{
                showWarning(RegisterActivity.this, "", getResources().getString(R.string.handle_strange_error), "error");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}



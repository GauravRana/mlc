package com.mylocumchoice.MyLocumChoicePharmacy.view.login.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.login.SignInReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.login.SignInRes;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.login.LoginVP;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.login.LoginPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SignOutAlert;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.LockableScrollView;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ProcessDialog;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.BasicDetailsActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.signup.activities.AppTourActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.signup.activities.RegisterActivity;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends BaseActivity implements LoginVP.View {

    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.getStart)
    TextView getStart;
    @BindView(R.id.linearLayout5)
    LinearLayout linearLayout5;
    @BindView(R.id.tv_Email)
    TextView tvEmail;
    @BindView(R.id.et_Email)
    EditText etEmail;
    @BindView(R.id.tv_w_Email)
    TextView tvWEmail;
    @BindView(R.id.tv_pwd)
    TextView tvPwd;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.tv_w_pwd)
    TextView tvWPwd;
    @BindView(R.id.tv_SignIn)
    TextView tvSignIn;
    @BindView(R.id.tv_f_password)
    TextView tvFPassword;
    @BindView(R.id.tvLast)
    TextView tvLast;
    @BindView(R.id.scrollView)
    LockableScrollView scrollView;
    @BindView(R.id.gap)
    LinearLayout gap;
    @BindView(R.id.parent)
    RelativeLayout relativeLayout;
    @BindView(R.id.ll_Pwd)
    LinearLayout ll_Pwd;
    private boolean isSignedInClicked = false;
    private ProcessDialog dialog;

    LoginPresenter loginPresenter;
    private SignOutAlert signOutAlert;

    double lat=0.0;
    double lng=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPresenter = new LoginPresenter(this);
        ButterKnife.bind(this);
        //setupUI(relativeLayout);
        etPwd.setTransformationMethod(new HiddenPassTransformationMethod());
        etPwd.setLetterSpacing(0.2f);
        scrollView.setScrollingEnabled(false);
        //setupUI(relativeLayout);

        String hi = " jkjj ";

        etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isSignedInClicked) {
                    if (etPwd.getText().length() < 8) {
                        tvWPwd.setVisibility(View.VISIBLE);
                        tvWPwd.setText(getResources().getString(R.string.text_war_pwd));
                    } else {
                        tvWPwd.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (isSignedInClicked) {
                    if (!isValidEmail(etEmail.getText().toString().trim())) {
                        tvWEmail.setVisibility(View.VISIBLE);
                        tvWEmail.setText(getResources().getString(R.string.text_war_email));
                    } else {
                        tvWEmail.setVisibility(View.GONE);
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
                        if (isOpen) {
                            scrollView.setScrollingEnabled(true);
                            gap.setVisibility(View.VISIBLE);
                        } else {
                            scrollView.setScrollingEnabled(false);
                            gap.setVisibility(View.GONE);
                        }

                    }
                });


        if (etEmail.hasFocus()) {
            make_visible(etEmail);
        }
        if (etPwd.hasFocus()) {
            make_visible(etPwd);
        }

    }

    @OnClick(R.id.tv_f_password)
    public void onButtonClick(View view) {
        startActivity(PasswordActivity.class);
    }

    @OnClick(R.id.tv_SignIn)
    public void onClick(View view) {
        isSignedInClicked = true;
        if (etEmail.getText().toString().equalsIgnoreCase("") && etPwd.getText().toString().equalsIgnoreCase("")) {
            tvWEmail.setVisibility(View.VISIBLE);
            tvWPwd.setVisibility(View.VISIBLE);
        } else if (etEmail.getText().toString().equalsIgnoreCase("") && !etPwd.getText().toString().equalsIgnoreCase("")) {
            tvWEmail.setVisibility(View.VISIBLE);
            tvWPwd.setVisibility(View.GONE);
        } else if (!etEmail.getText().toString().equalsIgnoreCase("") && etPwd.getText().toString().equalsIgnoreCase("")) {
            tvWEmail.setVisibility(View.GONE);
            tvWPwd.setVisibility(View.VISIBLE);
        } else if (!etEmail.getText().toString().equalsIgnoreCase("") && !etPwd.getText().toString().equalsIgnoreCase("")) {
            if (!isValidEmail(etEmail.getText().toString().trim())) {
                tvWEmail.setVisibility(View.VISIBLE);
                tvWEmail.setText(getResources().getString(R.string.text_war_email));
            } else if (etPwd.getText().length() < 8) {
                tvWPwd.setVisibility(View.VISIBLE);
                tvWPwd.setText(getResources().getString(R.string.text_war_pwd));
            } else {
                tvWPwd.setVisibility(View.GONE);
                //callAPI(etEmail.getText().toString().trim(), etPwd.getText().toString().trim());

                SignInReq signInReq = new SignInReq();
                loginPresenter.onUserLoggedIn(signInReq.add(etEmail.getText().toString().trim(), etPwd.getText().toString().trim(), SharedPrefManager.getInstance(this).getFCMToken()));
            }
        } else {
            //callAPI(etEmail.getText().toString().trim(), etPwd.getText().toString().trim());

            SignInReq signInReq = new SignInReq();
            loginPresenter.onUserLoggedIn(signInReq.add(etEmail.getText().toString().trim(), etPwd.getText().toString().trim(), SharedPrefManager.getInstance(this).getFCMToken()));
        }
        hideSoftKeyboard();
    }

    @OnClick(R.id.tv_Email)
    public void onEmailClick(View view) {
        etEmail.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etEmail, InputMethodManager.SHOW_IMPLICIT);
    }

    @OnClick(R.id.tv_pwd)
    public void onPwdClick(View view) {
        etPwd.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etPwd, InputMethodManager.SHOW_IMPLICIT);
    }

    @OnClick(R.id.tvLast)
    public void onSignupClick(View view) {
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    public void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    public void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);


    }

    public void make_visible(View view) {
        int vt = view.getTop();
        int vb = view.getBottom();

        View v = view;

        for (; ; ) {
            ViewParent vp = v.getParent();

            if (vp == null || !(vp instanceof ViewGroup))
                break;

            ViewGroup parent = (ViewGroup) vp;

            if (parent instanceof ScrollView) {
                ScrollView sv = (ScrollView) parent;

                // Code based on ScrollView.computeScrollDeltaToGetChildRectOnScreen(Rect rect) (Android v5.1.1):

                int height = sv.getHeight();
                int screenTop = sv.getScrollY();
                int screenBottom = screenTop + height;

                int fadingEdge = sv.getVerticalFadingEdgeLength();

                // leave room for top fading edge as long as rect isn't at very top
                if (vt > 0)
                    screenTop += fadingEdge;

                // leave room for bottom fading edge as long as rect isn't at very bottom
                if (vb < sv.getChildAt(0).getHeight())
                    screenBottom -= fadingEdge;

                int scrollYDelta = 0;

                if (vb > screenBottom && vt > screenTop) {
                    // need to move down to get it in view: move down just enough so
                    // that the entire rectangle is in view (or at least the first
                    // screen size chunk).

                    if (vb - vt > height) // just enough to get screen size chunk on
                        scrollYDelta += (vt - screenTop);
                    else              // get entire rect at bottom of screen
                        scrollYDelta += (vb - screenBottom);

                    // make sure we aren't scrolling beyond the end of our content
                    int bottom = sv.getChildAt(0).getBottom();
                    int distanceToBottom = bottom - screenBottom;
                    scrollYDelta = Math.min(scrollYDelta, distanceToBottom);
                } else if (vt < screenTop && vb < screenBottom) {
                    // need to move up to get it in view: move up just enough so that
                    // entire rectangle is in view (or at least the first screen
                    // size chunk of it).

                    if (vb - vt > height)    // screen size chunk
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
            int dy = parent.getTop() - parent.getScrollY();
            vt += dy;
            vb += dy;

            v = parent;
        }
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
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
                    hideSoftKeyboard(LoginActivity.this);
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

    @Override
    public void onLoginSuccess(Response<SignInRes> response) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Log.e("LOGINACTIVITY", "response: " + gson.toJson(response.body()));
        tvSignIn.setEnabled(true);
        if (response.code() == 201) {
            try {
                SharedPrefManager.getInstance(LoginActivity.this).setEmail(response.body().getLocum().getEmail());
                SharedPrefManager.getInstance(LoginActivity.this).saveAuthToken(response.body().getLocum().getAuthenticationToken());

                if(response.body().getLocum().getAddress_latitude()!=null && response.body().getLocum().getAddress_longitude()!=null){
                    if(response.body().getLocum().getAddress_latitude() instanceof String && response.body().getLocum().getAddress_longitude() instanceof  String){
                        SharedPrefManager.getInstance(LoginActivity.this).setLatLng((String)response.body().getLocum().getAddress_latitude(),(String)response.body().getLocum().getAddress_longitude());
                    }
                }else{
                    try{
                        SharedPrefManager.getInstance(LoginActivity.this).setLatLng("0.0","0.0");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                if(response.body().getLocum().getAddress_text()!=null){
                    if(response.body().getLocum().getAddress_text() instanceof String){
                        SharedPrefManager.getInstance(LoginActivity.this).setAddress((String)response.body().getLocum().getAddress_text());
                    }
                }else{
                    try{
                        SharedPrefManager.getInstance(LoginActivity.this).setAddress("");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                if(!response.body().getLocum().isGuided_tour()) {
                    Intent i = new Intent(LoginActivity.this, LandingActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }else{
                    Intent i = new Intent(LoginActivity.this, AppTourActivity.class);
                    i.putExtra("isFrom", "SignIn");
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (response.code() == 422) {
            try {
                String errorString;
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                errorString = jObjError.get("error").toString();
                showWarning(LoginActivity.this, "", errorString, "error");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else if (response.code() == 401) {
            try {
                String errorString;
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                errorString = jObjError.get("error").toString();
                showWarning(LoginActivity.this, "", errorString, "error");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else{
            try{
                showWarning(LoginActivity.this, "", getResources().getString(R.string.handle_strange_error), "error");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLoginfailed() {
        try {
            showWarning(LoginActivity.this, "", getResources().getString(R.string.errorTimeOut), "error");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}


package com.mylocumchoice.MyLocumChoicePharmacy.view.signup.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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

import com.mylocumchoice.MyLocumChoicePharmacy.presenter.signup.CreateUserVP;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.signup.CreateUserReqPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SignOutAlert;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.model.signup.AccountDetReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.signup.AccountDetRes;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.CustomAlertDialog;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ProcessDialog;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.LockableScrollView;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AccountDetActivity extends BaseActivity implements CustomAlertDialog.OnDialogFragmentClickListener, CreateUserVP.View {

    /**
     * ButterKnife Code
     **/
    @BindView(R.id.scrollView)
    LockableScrollView scrollView;
    @BindView(R.id.main)
    LinearLayout main;
    @BindView(R.id.llTop)
    LinearLayout llTop;
    @BindView(R.id.ll_header)
    LinearLayout llHeader;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.linearLayout3)
    LinearLayout linearLayout3;
    @BindView(R.id.tv_gphcNo)
    TextView tvGphcNo;
    @BindView(R.id.et_gphc)
    TextView etGphc;
    @BindView(R.id.tv_expiryOn)
    TextView tvExpiryOn;
    @BindView(R.id.et_expiryOn)
    TextView etExpiryOn;
    @BindView(R.id.ll_NameV)
    LinearLayout llNameV;
    @BindView(R.id.tv_NameV)
    TextView tvNameV;
    @BindView(R.id.llBottom)
    LinearLayout llBottom;
    @BindView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @BindView(R.id.ll_NameField)
    LinearLayout llNameField;
    @BindView(R.id.tv_Name)
    TextView tvName;
    @BindView(R.id.et_Name)
    EditText etName;
    @BindView(R.id.tv_w_Name)
    TextView tvWName;
    @BindView(R.id.space_name)
    View spaceName;
    @BindView(R.id.ll_Email)
    LinearLayout llEmail;
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
    @BindView(R.id.ll_Mobile)
    LinearLayout llMobile;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.et_mob)
    EditText etMob;
    @BindView(R.id.tv_w_Mobile)
    TextView tvWMobile;
    @BindView(R.id.linearLayout4)
    LinearLayout linearLayout4;
    @BindView(R.id.checkbox)
    ImageView checkbox;
    @BindView(R.id.tv_tc)
    TextView tvTc;
    @BindView(R.id.tv_signUp)
    TextView tvSignUp;
    @BindView(R.id.extra)
    LinearLayout extra;
    @BindView(R.id.ll_Gphc)
    LinearLayout llGphc;

    @BindView(R.id.ll_Expire)
    LinearLayout llExpire;

    @BindView(R.id.View)
    View view;

    @BindView(R.id.ll_click)
    LinearLayout llClick;

    /**
     * ButterKnife Code
     **/
    private boolean isFromstart = false;
    private boolean isFirstTimeClicked = false;
    private String gphcNo, name;
    private CustomAlertDialog generalDialogFragment;
    private SignOutAlert signOutAlert;
    private boolean isNameReceive = false;
    private String gphc_expiry;

    private ProcessDialog dialog;

    private int state = 0;

    AccountDetReq accountDetReq;
    CreateUserReqPresenter createUserPresenter;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPrefManager.getInstance(AccountDetActivity.this).clearIntentPref();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_det);
        ButterKnife.bind(this);

        createUserPresenter = new CreateUserReqPresenter(this, AccountDetActivity.this);
        //setupUI(relativeLayout);
        iniIntent();
        scrollView.setScrollingEnabled(true);
        etPwd.setLetterSpacing(0.2f);
        etPwd.setTransformationMethod(new HiddenPassTransformationMethod());
        SharedPrefManager.getInstance(AccountDetActivity.this).setChecked("2");
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvWName.setVisibility(View.GONE);
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
                tvWEmail.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvWPwd.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etMob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvWMobile.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        try{
            Rect scrollBounds = new Rect();
            scrollView.getHitRect(scrollBounds);
            if (main.getLocalVisibleRect(scrollBounds)) {
                // Any portion of the imageView, even a single pixel, is within the visible window
                scrollView.setScrollingEnabled(false);
            } else {
                // NONE of the imageView is within the visible window
                scrollView.setScrollingEnabled(true);
            }
        }catch(Exception e){
            e.printStackTrace();
        }


        KeyboardVisibilityEvent.setEventListener(this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if (isOpen) {
                            scrollView.setScrollingEnabled(true);
                            extra.setVisibility(View.VISIBLE);
                            if (etName.hasFocus()) {
                                float width = getResources().getDimension(R.dimen._250sdp);
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                        (int) width); // or set height to any fixed value you want
                                extra.setLayoutParams(lp);
                            }

                            if (etEmail.hasFocus()) {
                                float width = getResources().getDimension(R.dimen._200sdp);
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                        (int) width); // or set height to any fixed value you want
                                extra.setLayoutParams(lp);
                            }

                            if (etPwd.hasFocus()) {
                                float width = getResources().getDimension(R.dimen._150sdp);
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                        (int) width); // or set height to any fixed value you want
                                extra.setLayoutParams(lp);
                            }

                            if (etMob.hasFocus()) {
                                make_visible(etMob);
                                float width = getResources().getDimension(R.dimen._100sdp);
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                        (int) width); // or set height to any fixed value you want
                                extra.setLayoutParams(lp);
                            }

                        } else {
                            scrollView.scrollTo(0, 0);
                            scrollView.setScrollingEnabled(false);
                            extra.setVisibility(View.GONE);
                        }

                    }
                });


        //keyboardHeight = checkKeyboardHeight(relativeLayout);

        if (etName.hasFocus()) {
            make_visible(etName);
        }
        if (etEmail.hasFocus()) {
            make_visible(etEmail);
        }
        if (etPwd.hasFocus()) {
            make_visible(etPwd);
        }
        if (etMob.hasFocus()) {
            make_visible(etMob);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SharedPrefManager.getInstance(AccountDetActivity.this).getChecked().equalsIgnoreCase("1")) {
            checkbox.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_bc_on));
            state = 1;
        } else {
            checkbox.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_bc_off));
            state = 0;
        }
    }

    @OnClick(R.id.checkbox)
    public void onClick() {
        if (state == 0) {
            generalDialogFragment = CustomAlertDialog.newInstance("I have read and agree to the terms and conditions.", "");
            generalDialogFragment.setCancelable(false);
            generalDialogFragment.show(getSupportFragmentManager(), "dialog");
        } else {
            checkbox.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_bc_off));
            state = 0;
        }
    }

    @OnClick(R.id.tv_signUp)
    public void onButtonClick(View view) {
        boolean isNameInValid = false;
        boolean isEmailInValid = false;
        boolean isPwdInValid = false;
        boolean ismobInValid = false;
        if (etName.getText().toString().equalsIgnoreCase("")) {
            tvWName.setVisibility(View.VISIBLE);
            tvWName.setText("Required");
            isNameInValid = true;
        }
        if (etEmail.getText().toString().equalsIgnoreCase("")) {
            tvWEmail.setVisibility(View.VISIBLE);
            tvWEmail.setText("Required");
            isEmailInValid = true;
        }
        if (etPwd.getText().toString().equalsIgnoreCase("")) {
            tvWPwd.setVisibility(View.VISIBLE);
            tvWPwd.setText("Required");
            isPwdInValid = true;
        }
        if (etMob.getText().toString().equalsIgnoreCase("")) {
            tvWMobile.setVisibility(View.VISIBLE);
            tvWMobile.setText("Required");
            ismobInValid = true;
        }

        if (!etName.getText().toString().equalsIgnoreCase("")) {

        } else {
            isNameInValid = true;
        }

        if (!etEmail.getText().toString().equalsIgnoreCase("")) {
            if (!isValidEmail(etEmail.getText().toString().trim())) {
                tvWEmail.setVisibility(View.VISIBLE);
                tvWEmail.setText(getResources().getString(R.string.text_war_email));
                isEmailInValid = true;
            }
        }
        if (!etPwd.getText().toString().equalsIgnoreCase("")
                && etPwd.getText().length() < 8) {
            tvWPwd.setVisibility(View.VISIBLE);
            tvWPwd.setText(getResources().getString(R.string.text_war_pwd));
            isPwdInValid = true;
        }

        if (!etMob.getText().toString().equalsIgnoreCase("")
                && (etMob.getText().length() < 5 || etMob.getText().length() > 16)) {
            tvWMobile.setVisibility(View.VISIBLE);
            tvWMobile.setText(getResources().getString(R.string.text_war_mobile));
            ismobInValid = true;
        }


        if (isNameReceive) {
            if (!isEmailInValid && !isPwdInValid && !ismobInValid) {
                if (state == 0) {
                    showWarning(AccountDetActivity.this, "", "Please accept Terms & Conditions", "error");
                } else {
                    //callAPI(tvNameV.getText().toString(), gphcNo, etEmail.getText().toString(), etPwd.getText().toString(), etMob.getText().toString(), gphc_expiry);

                    accountDetReq = new AccountDetReq();
                    createUserPresenter.onCreateUserReq(accountDetReq.add(tvNameV.getText().toString(), gphcNo, etEmail.getText().toString(), etPwd.getText().toString(), etMob.getText().toString(),/* gphc_expiry,*/ SharedPrefManager.getInstance(this).getFCMToken()));

                }
            }
        } else {

            if (!isEmailInValid && !isPwdInValid && !ismobInValid && !isNameInValid) {
                if (state == 0) {
                    showWarning(AccountDetActivity.this, "", "Please accept Terms & Conditions", "error");
                } else {
                    //callAPI(etName.getText().toString(), gphcNo, etEmail.getText().toString(), etPwd.getText().toString(), etMob.getText().toString(), gphc_expiry);
                    accountDetReq = new AccountDetReq();
                    createUserPresenter.onCreateUserReq(accountDetReq.add(etName.getText().toString(), gphcNo, etEmail.getText().toString(), etPwd.getText().toString(), etMob.getText().toString(), /*gphc_expiry,*/ SharedPrefManager.getInstance(this).getFCMToken()));
                }
            }
        }
        hideSoftKeyboard();
    }

    @OnClick(R.id.tv_gphcNo)
    public void onGPHCClick(View view) {
        etGphc.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etGphc, InputMethodManager.SHOW_IMPLICIT);
    }

    @OnClick(R.id.ll_NameField)
    public void onNameClick(View view) {
        etName.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etName, InputMethodManager.SHOW_IMPLICIT);
    }


    @OnClick(R.id.ll_Email)
    public void onEmailClick(View view) {
        etEmail.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etEmail, InputMethodManager.SHOW_IMPLICIT);
    }


    @OnClick(R.id.ll_Pwd)
    public void onPwdClick(View view) {
        etPwd.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etPwd, InputMethodManager.SHOW_IMPLICIT);
    }

    @OnClick(R.id.ll_click)
    public void onBackClick(View view) {
        onBackPressed();
        //hideSoftKeyboard();
    }

    @OnClick(R.id.ll_Mobile)
    public void onMobileClick(View view) {
        etMob.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etMob, InputMethodManager.SHOW_IMPLICIT);
    }

    @OnClick(R.id.tv_tc)
    public void onTcClick(View view) {
        SharedPrefManager.getInstance(AccountDetActivity.this).setEditData(etName.getText().toString(),
                etEmail.getText().toString(),
                etPwd.getText().toString(),
                etMob.getText().toString());

        Bundle bundle = new Bundle();
        bundle.putString("email", etEmail.getText().toString());
        startActivityWithBundle(TandCActivity.class, bundle);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @Override
    public void onUserCreated(Response<AccountDetRes> response) {
        Log.d("RETROFIT", response.message());
        tvSignUp.setEnabled(true);
        if (response.code() == 201) {
            try {
                SharedPrefManager.getInstance(AccountDetActivity.this).setEmail(response.body().getLocum().getEmail());
                SharedPrefManager.getInstance(AccountDetActivity.this).saveAuthToken(response.body().getLocum().getAuthenticationToken());
                Intent i = new Intent(AccountDetActivity.this, AppTourActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.putExtra("isFrom", "SignUp");
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }catch (Exception e){
                e.printStackTrace();
            }

        } else if (response.code() == 422) {
            try {
                String errorString;
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                errorString = jObjError.get("error").toString();
                showWarning(AccountDetActivity.this, "", errorString, "error");
                checkbox.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_bc_off));
                state = 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (response.code() == 401) {
            try {
                signOutAlert = SignOutAlert.newInstance(this, getResources().getString(R.string.text_sign_out), "");
                signOutAlert.setCancelable(false);
                signOutAlert.show(getSupportFragmentManager(), "dialog");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else {
            try{
                showWarning(AccountDetActivity.this, "", getResources().getString(R.string.handle_strange_error), "error");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1) {
                if (resultCode == Activity.RESULT_OK) {
                    String result = data.getStringExtra("result");

                }
                if (resultCode == Activity.RESULT_CANCELED) {

                }
            }
        }catch (Exception e){

        }

    }


    public void iniIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            gphcNo = bundle.getString("gphc");
            name = bundle.getString("name");
            isNameReceive = bundle.getBoolean("isNameSent");
            //gphc_expiry = bundle.getString("gphc_expiry");
            SharedPrefManager.getInstance(AccountDetActivity.this).setIntentData(gphcNo, name, isNameReceive, gphc_expiry);

            /*if (gphc_expiry.equalsIgnoreCase("")) {
                llExpire.setVisibility(View.GONE);
                view.setVisibility(View.GONE);
            } else {
                llExpire.setVisibility(View.VISIBLE);
                view.setVisibility(View.VISIBLE);
            }*/


            if (isNameReceive) {
                llNameV.setVisibility(View.VISIBLE);
                llNameField.setVisibility(View.GONE);
                tvNameV.setText(name);
                etGphc.setText(gphcNo);
                etExpiryOn.setText(gphc_expiry);
                spaceName.setVisibility(View.GONE);
                float width = getResources().getDimension(R.dimen._195sdp);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        (int) width); // or set height to any fixed value you want
                llTop.setLayoutParams(lp);

                float width2 = getResources().getDimension(R.dimen._210sdp);
                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        (int) width2); // or set height to any fixed value you want
                llBottom.setLayoutParams(lp2);
            } else {
                llNameV.setVisibility(View.GONE);
                llNameField.setVisibility(View.VISIBLE);
                spaceName.setVisibility(View.VISIBLE);
                etGphc.setText(gphcNo);
                etExpiryOn.setText(gphc_expiry);
                float width = getResources().getDimension(R.dimen._135sdp);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        (int) width); // or set height to any fixed value you want
                llTop.setLayoutParams(lp);


                float width2 = getResources().getDimension(R.dimen._270sdp);
                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        (int) width2); // or set height to any fixed value you want
                llBottom.setLayoutParams(lp2);

            }
        }
    }

    @Override
    public void onOkClicked(CustomAlertDialog dialog) {
        generalDialogFragment.dismiss();
        checkbox.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_bc_on));
        state = 1;
    }

    @Override
    public void onCancelClicked(CustomAlertDialog dialog) {
        generalDialogFragment.dismiss();
        checkbox.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_bc_off));
        state = 0;
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
                    hideSoftKeyboard(AccountDetActivity.this);
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


    private AccountDetReq getAccountDetReq(String name
            , String gphcNo
            , String email
            , String pwd
            , String mob
            , String gphc_expiry) {
        accountDetReq = new AccountDetReq();
        accountDetReq.add(name, gphcNo, email, pwd, mob, /*gphc_expiry,*/ SharedPrefManager.getInstance(this).getFCMToken());
        return accountDetReq;
    }
}

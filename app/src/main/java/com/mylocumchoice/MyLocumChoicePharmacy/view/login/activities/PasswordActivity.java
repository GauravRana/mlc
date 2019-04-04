package com.mylocumchoice.MyLocumChoicePharmacy.view.login.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.api.APIInterface;
import com.mylocumchoice.MyLocumChoicePharmacy.model.login.ForgotPwdReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.login.ForgotPwdRes;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.login.ForgotPwdVP;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.login.ForgotPwdPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SignOutAlert;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.LockableScrollView;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ProcessDialog;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PasswordActivity extends BaseActivity implements ForgotPwdVP.View{

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_SignUp)
    TextView tvSignUp;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.tv_Email)
    TextView tvEmail;
    @BindView(R.id.et_Email)
    EditText etEmail;
    @BindView(R.id.tv_w_Email)
    TextView tvWEmail;
    @BindView(R.id.tv_next)
    TextView tvNext;


    @BindView(R.id.scrollView)
    LockableScrollView scrollView;


    @BindView(R.id.ll_click)
    LinearLayout llClick;

    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;

    private ProcessDialog dialog;


    private APIInterface apiInterface;
    private boolean isSignedInClicked = false;
    String errorString;
    private SignOutAlert signOutAlert;

    ForgotPwdPresenter forgotPwdPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        forgotPwdPresenter=new ForgotPwdPresenter(this);
        ButterKnife.bind(this);
        //setupUI(relativeLayout);
        scrollView.setScrollingEnabled(false);
        //etEmail.setLetterSpacing(0.2f);
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
                        if(isOpen){
                            scrollView.setScrollingEnabled(true);

                        }else {
                            scrollView.scrollTo(0,0);
                            scrollView.setScrollingEnabled(false);
                        }

                    }
                });
    }

    @OnClick(R.id.ll_click)
    public void onButtonClick(View view) {
        onBackPressed();
        //hideSoftKeyboard();
    }

    @OnClick(R.id.tv_next)
    public void onNextClick(View view) {
        isSignedInClicked = true;
       if(etEmail.getText().toString().equalsIgnoreCase("")){
           tvWEmail.setVisibility(View.VISIBLE);
       }else if(!etEmail.getText().toString().equalsIgnoreCase("")){
           if (!isValidEmail(etEmail.getText().toString().trim())) {
               tvWEmail.setVisibility(View.VISIBLE);
               tvWEmail.setText(getResources().getString(R.string.text_war_email));
           } else {
               tvWEmail.setVisibility(View.GONE);


               ForgotPwdReq forgotPwdReq=new ForgotPwdReq();
               forgotPwdPresenter.onForgetPwd(forgotPwdReq.add(etEmail.getText().toString()));

               //callAPI(etEmail.getText().toString());
           }
       }else {

           ForgotPwdReq forgotPwdReq=new ForgotPwdReq();
           forgotPwdPresenter.onForgetPwd(forgotPwdReq.add(etEmail.getText().toString().trim()));
           //callAPI(etEmail.getText().toString().trim());
       }
       hideSoftKeyboard();
    }


    @OnClick(R.id.linearLayout)
    public void onClick(View view) {
        etEmail.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etEmail, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
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
                    hideSoftKeyboard(PasswordActivity.this);
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
    public void onForgetPwdSuccess(Response<ForgotPwdRes> response) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Log.e("PASSWORD_ACTIVITY", "response: "+gson.toJson(response.body()));
        Log.e("RETROFIT",response.message());

        if(response.code() == 201){
            try {
                showWarning(PasswordActivity.this, "",response.body().getSuccess(), "success");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                    }
                }, 500);
            }catch (Exception e){
                e.printStackTrace();
            }
           // showWarning(PasswordActivity.this, "","Reset password instructions sent successfully", "success");

        }
        else if(response.code() == 422){
            try {
                String errorString;
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                errorString = jObjError.get("error").toString();
                showWarning(PasswordActivity.this, "",   errorString,"error");
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if (response.code() == 401) {
            try {
                signOutAlert = SignOutAlert.newInstance(this,getResources().getString(R.string.text_sign_out), "");
                signOutAlert.setCancelable(false);
                signOutAlert.show(getSupportFragmentManager(), "dialog");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            try{
                showWarning(PasswordActivity.this, "", getResources().getString(R.string.handle_strange_error), "error");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onForgetPwdfailed() {
        try {
            showWarning(PasswordActivity.this, "",getResources().getString(R.string.errorTimeOut), "error");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ChangePwdReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ChangePwdRes;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.ChangePasswordVP;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.ChangePasswordPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ProcessDialog;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SignOutAlert;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChangePassword extends AppActivity implements ChangePasswordVP.View {

    @BindView(R.id.tv_dots)
    ImageView tvDots;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.shadow1)
    View shadow1;
    @BindView(R.id.ll_old_pwd)
    LinearLayout llOldPwd;
    @BindView(R.id.tv_old_pwd)
    TextView tvOldPwd;
    @BindView(R.id.et_old_pwd)
    EditText etOldPwd;
    @BindView(R.id.shadow2)
    View shadow2;
    @BindView(R.id.shadow3)
    View shadow3;
    @BindView(R.id.ll_new_pwd)
    LinearLayout llNewPwd;
    @BindView(R.id.tv_new_pwd)
    TextView tvNewPwd;
    @BindView(R.id.new_pwd)
    EditText newPwd;
    @BindView(R.id.ll_confirm_pwd)
    LinearLayout llConfirmPwd;
    @BindView(R.id.tv_confirm_pwd)
    TextView tvConfirmPwd;
    @BindView(R.id.et_confirm_pwd)
    EditText etConfirmPwd;
    @BindView(R.id.shadow4)
    View shadow4;
    @BindView(R.id.ll_frameLayout)
    LinearLayout llFrameLayout;
    @BindView(R.id.btn_accept)
    TextView btnAccept;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.tv_o_Name)
    TextView oldPwdW;
    @BindView(R.id.tv_n_Name)
    TextView newPwdW;
    @BindView(R.id.tv_c_Name)
    TextView confirmPwdW;
    @BindView(R.id.ll_menuRight)
    FrameLayout llMenuRight;
    private SignOutAlert signOutAlert;

    private ChangePasswordPresenter presenter;


    private boolean isSignedInClicked = false;

    private ProcessDialog dialog;

    private boolean isButtonClicked = false;
    String errorString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        tvDots.setVisibility(View.INVISIBLE);
        tvHeader.setText("Change Password");
        etOldPwd.setTransformationMethod(new HiddenPassTransformationMethod());
        newPwd.setTransformationMethod(new HiddenPassTransformationMethod());
        etConfirmPwd.setTransformationMethod(new HiddenPassTransformationMethod());

        etOldPwd.setLetterSpacing(0.2f);
        newPwd.setLetterSpacing(0.2f);
        etConfirmPwd.setLetterSpacing(0.2f);

        etOldPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                oldPwdW.setVisibility(View.GONE);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        newPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newPwdW.setVisibility(View.GONE);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etConfirmPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                confirmPwdW.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @OnClick(R.id.ll_back)
        public void onBackClick(){
        onBackPressed();
    }

    @OnClick(R.id.btn_accept)
        public void onAcceptClick(){
        boolean oldPwdInvalid = false;
        boolean newPwdInvalid = false;
        boolean confirmPwdInvalid = false;
        boolean notMatched = false;

        if(etOldPwd.getText().toString().equalsIgnoreCase("")){
            oldPwdW.setVisibility(View.VISIBLE);
            oldPwdW.setText(getResources().getString(R.string.text_war_pwd));
            oldPwdInvalid = true;
        }
        if(newPwd.getText().toString().equalsIgnoreCase("")){
            newPwdW.setVisibility(View.VISIBLE);
            newPwdW.setText(getResources().getString(R.string.text_war_pwd));
            newPwdInvalid = true;
        }
        if(etConfirmPwd.getText().toString().equalsIgnoreCase("")){
            confirmPwdW.setVisibility(View.VISIBLE);
            confirmPwdInvalid = true;
        }

        if(newPwd.getText().length() < 8){
            newPwdW.setVisibility(View.VISIBLE);
            newPwdW.setText(getResources().getString(R.string.text_war_pwd));
            newPwdInvalid = true;
        }
        if(etOldPwd.getText().length() < 8){
            oldPwdInvalid = true;
            oldPwdW.setVisibility(View.VISIBLE);
            oldPwdW.setText(getResources().getString(R.string.text_war_pwd));
        }
        if(etConfirmPwd.getText().length() < 8){
            confirmPwdInvalid = true;
            confirmPwdW.setVisibility(View.VISIBLE);
            confirmPwdW.setText(getResources().getString(R.string.text_war_pwd));
        }

        if(!newPwd.getText().toString().contentEquals(etConfirmPwd.getText().toString())){
                confirmPwdW.setVisibility(View.VISIBLE);
                confirmPwdW.setText("Passwords do not match");
                notMatched = true;
        }

            if (!oldPwdInvalid && !newPwdInvalid && !confirmPwdInvalid && !notMatched) {
                addRequest(etOldPwd.getText().toString(), newPwd.getText().toString(), etConfirmPwd.getText().toString());
                //callAPI(etOldPwd.getText().toString(), newPwd.getText().toString(), etConfirmPwd.getText().toString());
            }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onSuccess(Response<ChangePwdRes> response) {
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            Log.e("BASIC_DETAIL_ACTIVITY", "response: "+gson.toJson(response.body()));
            if(response.code() == 204){
                showWarning(ChangePassword.this, "",  "Password successfully Changed","");
                finish();
            }
            else {
                handleAPIErrors(this, response);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @OnClick(R.id.ll_old_pwd)
    public void onllClick(){
        etOldPwd.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etOldPwd, InputMethodManager.SHOW_IMPLICIT);
    }


    @OnClick(R.id.ll_new_pwd)
    public void onNewClick(){
        newPwd.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(newPwd, InputMethodManager.SHOW_IMPLICIT);
    }


    @OnClick(R.id.ll_confirm_pwd)
    public void onConfirmClick(){
        etConfirmPwd.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etConfirmPwd, InputMethodManager.SHOW_IMPLICIT);
    }


    public void addRequest(String old_pwd
            ,String n_pwd
            ,String confirm_pwd){
        ChangePwdReq request = new ChangePwdReq();
        presenter = new ChangePasswordPresenter(this, this);
        presenter.updateAPI(this, request.add(old_pwd, n_pwd, confirm_pwd));
    }

}

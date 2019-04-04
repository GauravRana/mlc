package com.mylocumchoice.MyLocumChoicePharmacy.view.signup.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;

import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class WelcomeActivity extends AppActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
//        ButterKnife.bind(this);
    }

    // click event with source view params
    @OnClick(R.id.tv_SignUp)
    public void onsignUpClick(View view) {
        startActivity(SignUpActivity.class);
    }


//    @OnClick(R.id.signInLink)
    public void startSignInActivity(View view) {
        startActivity(SignUpActivity.class);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

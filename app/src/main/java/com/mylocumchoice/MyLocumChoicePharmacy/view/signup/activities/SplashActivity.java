package com.mylocumchoice.MyLocumChoicePharmacy.view.signup.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities.EventsDetailsActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.AccreditationActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.BasicDetailsActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.PreferenceActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.References;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.RightToWorkActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.SkillActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.VerificationActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;

import org.greenrobot.eventbus.EventBus;

public class SplashActivity extends Activity {
    private static int SPLASH_TIME_OUT = 1000;
    private SharedPrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash2);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    if (SharedPrefManager.getInstance(SplashActivity.this).getEmail() == null
                            && SharedPrefManager.getInstance(SplashActivity.this).getAuthToken() == null) {
                        Intent i = new Intent(SplashActivity.this, SignUpActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    } else {
                        Intent i = new Intent(SplashActivity.this, LandingActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    }
            }
        }, SPLASH_TIME_OUT);

    }
}

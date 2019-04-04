package com.mylocumchoice.MyLocumChoicePharmacy.view.signup.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities.EventsDetailsActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.AccreditationActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.BasicDetailsActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.PreferenceActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.References;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.RightToWorkActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.SkillActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;

import org.greenrobot.eventbus.EventBus;

public class NotificationHandleActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = new Intent(NotificationHandleActivity.this, LandingActivity.class);
        startActivity(i);
        finish();

    }
}

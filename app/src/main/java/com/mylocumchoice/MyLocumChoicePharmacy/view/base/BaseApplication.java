package com.mylocumchoice.MyLocumChoicePharmacy.view.base;

import android.app.Application;
import android.os.StrictMode;
import android.support.annotation.NonNull;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.mylocumchoice.MyLocumChoicePharmacy.BuildConfig;
//import com.mylocumchoice.MyLocumChoicePharmacy.api.rest.APIClientShiftDetails;
import com.mylocumchoice.MyLocumChoicePharmacy.api.rest.RestClient;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.CrashHandler;

import io.fabric.sdk.android.services.common.CommonUtils;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class BaseApplication extends Application {

    private static RestClient restClient;
//    private static APIClientShiftDetails restShiftClient;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        //Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(getApplicationContext()));
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/SanFranciscoText_Regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        restClient = new RestClient();
//        restShiftClient = new APIClientShiftDetails();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        //DeviceUtils.setWindowDimensions(this);
        initFirebaseRemoteConfig();
    }

    public static RestClient getRestClient() {
        return restClient;
    }

    /*public static APIClientShiftDetails getAPIClient() {
        return restShiftClient;
    }*/

    private void initFirebaseRemoteConfig() {
        // Get Remote Config instance.
        // [START get_remote_config_instance]
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        // [END get_remote_config_instance]

        // Create a Remote Config Setting to enable developer mode, which you can use to increase
        // the number of fetches available per hour during development. See Best Practices in the
        // README for more information.
        // [START enable_dev_mode]
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        // [END enable_dev_mode]

        // Set default Remote Config parameter values. An app uses the in-app default values, and
        // when you need to adjust those defaults, you set an updated value for only the values you
        // want to change in the Firebase console. See Best Practices in the README for more
        // information.
        // [START set_default_values]
        ////mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
        // [END set_default_values]

        long cacheExpiration = 3600; // 1 hour in seconds.
        // If your app is using developer mode, cacheExpiration is set to 0, so each fetch will
        // retrieve values from the service.
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }

        // [START fetch_config_with_callback]
        // cacheExpirationSeconds is set to cacheExpiration here, indicating the next fetch request
        // will use fetch data from the Remote Config service, rather than cached parameter values,
        // if cached parameter values are more than cacheExpiration seconds old.
        // See Best Practices in the README for more information.
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //CommonUtils.showToast(getApplicationContext(), "Fetch Succeeded");

                            // After config data is successfully fetched, it must be activated before newly fetched
                            // values are returned.
                            mFirebaseRemoteConfig.activateFetched();
                        } else {
                            //CommonUtils.showToast(getApplicationContext(), "Fetch Failed");
                        }
                        //displayWelcomeMessage();
                    }
                });
        // [END fetch_config_with_callback]
    }

}

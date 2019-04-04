package com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.EventList;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.EventListResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.EmailRegReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.LogoutRequest;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShift;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShiftReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShiftResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.calender.EventListPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.calender.EventListPresenterImpl;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.shifts.OpenShiftPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.shifts.OpenShiftPresenterImpl;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ForceUpdateChecker;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.GPSTracker;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.GlobalConstants;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.PermissionUtil;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities.BookingDetailsActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities.EventsDetailsActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities.MapActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment.ShiftDetailAppliedFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment.ShiftDetailInviteFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment.ShiftDetailUpcmingFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.calender.fragment.CalFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.calender.viewinterface.EventListView;
import com.mylocumchoice.MyLocumChoicePharmacy.view.login.activities.LoginActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.login.activities.PasswordActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.notification.fragment.NotifyFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.AccreditationActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.BasicDetailsActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.PharmaComplianceDetails;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.PreferenceActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.References;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.RightToWorkActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.SkillActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.VerificationActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.fragment.ProfileFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.fragment.ListFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.fragment.OpenShiftFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.viewinterface.OpenShiftView;
import com.mylocumchoice.MyLocumChoicePharmacy.view.signup.activities.SplashActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.signup.activities.TandCActivity;

import org.ankit.gpslibrary.ADLocation;
import org.ankit.gpslibrary.MyTracker;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LandingActivity extends AppActivity implements OpenShiftFragment.OnFragmentInteractionListener,
        ListFragment.OnFragmentInteractionListener,
        CalFragment.OnFragmentInteractionListener,
        NotifyFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener,
        ShiftDetailUpcmingFragment.OnFragmentInteractionListener,
        ShiftDetailAppliedFragment.OnFragmentInteractionListener,
        ShiftDetailInviteFragment.OnFragmentInteractionListener, LocationListener, EventListView
        ,ForceUpdateChecker.OnUpdateNeededListener, MyTracker.ADLocationListener {

    @BindView(R.id.click_start)
    ImageView click_start;

    @BindView(R.id.click_list)
    ImageView click_list;


    @BindView(R.id.click_cal)
    ImageView click_cal;

    @BindView(R.id.click_profile)
    ImageView click_profile;

    @BindView(R.id.tv_header)
    TextView header;

    public static ImageView menu_left;


    @BindView(R.id.tvEvents)
    TextView tvEvents;


    public static ImageView menu_right;
    public static LinearLayout llMe;

    public static ImageView menu_left_image;
    public static LinearLayout ll_LeftMenu;
    public static FrameLayout ll_RightMenu;
    public static FrameLayout fl_leftMenu;
    public static ImageView menu_right_plus;
    public static TextView tv_clear_all;
    public static LinearLayout ll_clear_all;
    public static ImageView click_bell;
    public static ImageView menu_right_cal_up;

    private Drawable mDrawableStartOn, mDrawableStartOff;
    private Drawable mDrawableListOn, mDrawableListOff;
    private Drawable mDrawableCalOn, mDrawableBellOff,mDrawableDot;
    private Drawable mDrawableProfileOn, mDrawableCalOff;
    private Drawable mDrawableBellOn, mDrawableProfileOff;
    private Fragment fragment;
    private Utils mUtils;
    public static int openFragmentPosition;
    public int OpenShiftPosition =1;
    public int BookingPosition =2;
    public int CalendarPosition =3;
    public int NotificationPosition =4;
    public int AccountPosition =5;

    EventListPresenter mPresenter;
    private int event_Id;
    List<EventList> lv_events = new ArrayList<>();
    private boolean isFromhandleIntent = false;
    private boolean isFromhandle = false;
    private boolean isFromNotification = false;

    public int notification_id=0;
    String key="";

    public static boolean isNotificationClicked=false;

    boolean isHandleNotification=false;
    public int NOTIFICATION_REQUEST_CODE=1000;
    public int NOTIFICATION_RESULT_CODE=1001;

    boolean isNotifyHandled=false;

    LocationManager mLocationManager;
    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;

    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;

    private static final int REQUEST_CHECK_SETTINGS = 100;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    // location last updated time
    private String mLastUpdateTime;

    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    public static boolean isMaintaned=false;


    @Override
    public void onNewIntent(Intent intent) {
        intent_handler(intent, true);
    }

    public void intent_handler(Intent intent, boolean firebase) {
        /*if(!isNotifyHandled){
            isNotifyHandled=true;*/
            handleNotificationIntent(intent);
        //}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        ButterKnife.bind(this);
        mUtils=new Utils();
        printHashKey(LandingActivity.this);

        try {
            OpenShift openShift = SharedPrefManager.getInstance(LandingActivity.this).getOpenShiftData();
            openShift.setStartDate("");
            openShift.setEndDate("");
            openShift.setEmergency(false);

            SharedPrefManager.getInstance(LandingActivity.this).setShiftData(openShift);
        } catch (Exception e) {
            e.printStackTrace();
        }
       // checkPermissions();
       // updateLocationUI();

        FacebookSdk.setAutoLogAppEventsEnabled(true);
        FacebookSdk.setIsDebugEnabled(true);
        FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS);

        /*if(!isNotifyHandled){
            isNotifyHandled=true;*/
            handleNotifications();
        /*}else {

        }*/
        if(GlobalConstants.BaseAPI.BASE_URL.equalsIgnoreCase("https://pharmacy.mylocumchoice.com")
                ||GlobalConstants.BaseAPI.BASE_URL.equalsIgnoreCase("http://pharmacy.mylocumchoice.com")){
            ForceUpdateChecker.with(this).onUpdateNeeded(this).check();
        }

        click_bell = findViewById(R.id.click_bell);
        menu_right = findViewById(R.id.menu_right);
        menu_right_plus = findViewById(R.id.menu_right_plus);
        menu_right_cal_up = findViewById(R.id.menu_right_cal_up);
        menu_left_image = findViewById(R.id.menu_left_image);
        menu_left = findViewById(R.id.menu_left);
        ll_LeftMenu = findViewById(R.id.ll_leftMenu);
        ll_RightMenu = findViewById(R.id.ll_rightMenu);
        fl_leftMenu = findViewById(R.id.fl_leftMenu);
        tv_clear_all = findViewById(R.id.tv_clear_all);
        ll_clear_all = findViewById(R.id.ll_clear_all);
        mDrawableStartOn = getResources().getDrawable(R.drawable.menu_start_on);
        mDrawableListOn = getResources().getDrawable(R.drawable.menu_cal_on);
        mDrawableCalOn = getResources().getDrawable(R.drawable.menu_calc_on);
        mDrawableBellOn = getResources().getDrawable(R.drawable.menu_bell_on);
        mDrawableProfileOn = getResources().getDrawable(R.drawable.menu_pro_on);
        mDrawableStartOff = getResources().getDrawable(R.drawable.menu_start_off);
        mDrawableListOff = getResources().getDrawable(R.drawable.menu_cal_off);
        mDrawableCalOff = getResources().getDrawable(R.drawable.menu_calc_off);
        mDrawableBellOff = getResources().getDrawable(R.drawable.menu_bell_off);
        mDrawableDot = getResources().getDrawable(R.drawable.notify_dot);
        mDrawableProfileOff = getResources().getDrawable(R.drawable.menu_pro_off);
        fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        SharedPrefManager.getInstance(this).getFCMToken();
        Log.e("fcm token", SharedPrefManager.getInstance(this).getFCMToken());
        menuPlaying(replaceFragment(new OpenShiftFragment()));

//        if(!isFromhandle) {
//            play();
//        }else {
//            isFromhandle = false;
//            menuPlaying(replaceFragment(new ProfileFragment()));
//        }

    }




    private void updateLocationUI() {
        if (mCurrentLocation != null) {

            SharedPrefManager.getInstance(LandingActivity.this).setCurrentLatLng(String.valueOf(mCurrentLocation.getLatitude()), String.valueOf(mCurrentLocation.getLongitude()));

            try {
                if (SharedPrefManager.getInstance(LandingActivity.this).getAddress() != null && !SharedPrefManager.getInstance(LandingActivity.this).getAddress().equals("")) {

                } else {
                    OpenShiftFragment.tvSearch.setText(mUtils.getAddress(LandingActivity.this, mCurrentLocation.getLatitude(),
                            mCurrentLocation.getLongitude()));

                    try {
                        ShiftDetailInviteFragment.tvSearch.setText(mUtils.getAddress(LandingActivity.this, mCurrentLocation.getLatitude(),
                                mCurrentLocation.getLongitude()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }






    public void printHashKey(Context pContext) {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("Hash Key", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("Hash Key Exception", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("Hash Key Exception", "printHashKey()", e);
        }
    }


    @OnClick(R.id.click_start)
    public void onStartClick(View view) {
        menuPlaying(replaceFragment(new OpenShiftFragment()));
    }

    @OnClick(R.id.click_list)
    public void onListClick(View view) {
        menuPlaying(replaceFragment(new ListFragment()));
    }

    @OnClick(R.id.click_cal)
    public void onCalClick(View view) {
        menuPlaying(replaceFragment(new CalFragment()));
    }

    @OnClick(R.id.click_bell)
    public void onBellClick(View view) {
        menuPlaying(replaceFragment(new NotifyFragment()));
    }

    @OnClick(R.id.click_profile)
    public void onProfileClick(View view) {
        menuPlaying(replaceFragment(new ProfileFragment()));
    }


    public void menuPlaying(Fragment fragment) {
        if (fragment instanceof OpenShiftFragment) {
            header.setText(getResources().getString(R.string.text_open_shift));
            ll_RightMenu.setVisibility(View.VISIBLE);
            fl_leftMenu.setVisibility(View.VISIBLE);
            menu_left.setVisibility(View.VISIBLE);
            menu_right.setVisibility(View.VISIBLE);
            tvEvents.setVisibility(View.GONE);
            ll_RightMenu.setVisibility(View.VISIBLE);
            menu_left_image.setVisibility(View.GONE);
            ll_LeftMenu.setVisibility(View.GONE);
            menu_right_plus.setVisibility(View.GONE);
            menu_right_cal_up.setVisibility(View.GONE);
            menu_right.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu));
            click_start.setImageDrawable(mDrawableStartOn);
            click_list.setImageDrawable(mDrawableListOff);
            click_cal.setImageDrawable(mDrawableCalOff);
            if(SharedPrefManager.getInstance(this).getNotifyDot()){
                click_bell.setImageDrawable(mDrawableDot);
            }else {
                click_bell.setImageDrawable(mDrawableBellOff);
            }
            ll_clear_all.setVisibility(View.GONE);
            click_profile.setImageDrawable(mDrawableProfileOff);
        } else if (fragment instanceof ListFragment) {
            header.setText(getResources().getString(R.string.text_booking));
            ll_RightMenu.setVisibility(View.VISIBLE);
            fl_leftMenu.setVisibility(View.VISIBLE);
            menu_left.setVisibility(View.GONE);
            tvEvents.setVisibility(View.GONE);
            menu_right.setVisibility(View.VISIBLE);
            ll_RightMenu.setVisibility(View.VISIBLE);
            menu_left_image.setVisibility(View.VISIBLE);
            ll_LeftMenu.setVisibility(View.VISIBLE);
            menu_right_plus.setVisibility(View.GONE);
            menu_right_cal_up.setVisibility(View.GONE);
            click_start.setImageDrawable(mDrawableStartOff);
            click_list.setImageDrawable(mDrawableListOn);
            click_cal.setImageDrawable(mDrawableCalOff);
            if(SharedPrefManager.getInstance(this).getNotifyDot()){
                click_bell.setImageDrawable(mDrawableDot);
            }else {
                click_bell.setImageDrawable(mDrawableBellOff);
            }
            ll_clear_all.setVisibility(View.GONE);
            click_profile.setImageDrawable(mDrawableProfileOff);
        } else if (fragment instanceof CalFragment) {
            header.setText(getResources().getString(R.string.text_calender));
            ll_RightMenu.setVisibility(View.VISIBLE);
            fl_leftMenu.setVisibility(View.VISIBLE);
            tvEvents.setVisibility(View.VISIBLE);
            menu_left.setVisibility(View.GONE);
            menu_right.setVisibility(View.GONE);
            menu_right_plus.setVisibility(View.VISIBLE);
            menu_right_cal_up.setVisibility(View.GONE);
            ll_RightMenu.setVisibility(View.VISIBLE);
            menu_left_image.setVisibility(View.GONE);
            ll_LeftMenu.setVisibility(View.VISIBLE);
            click_start.setImageDrawable(mDrawableStartOff);
            click_list.setImageDrawable(mDrawableListOff);
            click_cal.setImageDrawable(mDrawableCalOn);

            if(SharedPrefManager.getInstance(this).getNotifyDot()){
                click_bell.setImageDrawable(mDrawableDot);
            }else {
                click_bell.setImageDrawable(mDrawableBellOff);
            }

            ll_clear_all.setVisibility(View.GONE);
            click_profile.setImageDrawable(mDrawableProfileOff);
        } else if (fragment instanceof ProfileFragment) {
            header.setText(getResources().getString(R.string.text_account));
            tvEvents.setVisibility(View.GONE);
            fl_leftMenu.setVisibility(View.GONE);
            menu_left.setVisibility(View.INVISIBLE);
            menu_right.setVisibility(View.GONE);
            ll_LeftMenu.setVisibility(View.INVISIBLE);
            menu_right_plus.setVisibility(View.GONE);
            menu_left_image.setVisibility(View.INVISIBLE);
            ll_RightMenu.setVisibility(View.GONE);
            menu_right_cal_up.setVisibility(View.GONE);
            click_start.setImageDrawable(mDrawableStartOff);
            click_list.setImageDrawable(mDrawableListOff);
            click_cal.setImageDrawable(mDrawableCalOff);
            ll_clear_all.setVisibility(View.GONE);
            if(SharedPrefManager.getInstance(this).getNotifyDot()){
                click_bell.setImageDrawable(mDrawableDot);
            }else {
                click_bell.setImageDrawable(mDrawableBellOff);
            }

            click_profile.setImageDrawable(mDrawableProfileOn);
        } else if (fragment instanceof NotifyFragment) {
            header.setText(getResources().getString(R.string.text_notify));
            tvEvents.setVisibility(View.GONE);
            fl_leftMenu.setVisibility(View.INVISIBLE);
            menu_left.setVisibility(View.INVISIBLE);
            menu_right.setVisibility(View.INVISIBLE);
            ll_LeftMenu.setVisibility(View.INVISIBLE);
            menu_right_cal_up.setVisibility(View.GONE);
            menu_right_plus.setVisibility(View.GONE);
            ll_RightMenu.setVisibility(View.VISIBLE);
            ll_clear_all.setVisibility(View.VISIBLE);
            menu_left_image.setVisibility(View.INVISIBLE);
            click_start.setImageDrawable(mDrawableStartOff);
            click_list.setImageDrawable(mDrawableListOff);
            click_cal.setImageDrawable(mDrawableCalOff);
            click_bell.setImageDrawable(mDrawableBellOn);
            click_profile.setImageDrawable(mDrawableProfileOff);
        } else {

        }
    }

    @Override
    public void onFragmentInteraction(int position) {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void showDialog(Context context, String title, String msg, int requestMode) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .create();

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_tc, null);
        TextView tv = dialogView.findViewById(R.id.tvText);
        TextView tvCancel = dialogView.findViewById(R.id.tv_cancel);
        TextView tvAccept = dialogView.findViewById(R.id.tv_clear);
        tv.setText(msg);
        tvCancel.setText("Cancel");
        tvAccept.setText("Ok");

        tvAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Prompt the user once explanation has been shown
                if (requestMode == 1) {
                    ActivityCompat.requestPermissions(LandingActivity.this,
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            MapActivity.MY_PERMISSIONS_REQUEST_LOCATION);
                }
                if (requestMode == 2) {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(myIntent);
                }
                dialog.dismiss();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Prompt the user once explanation has been shown
                dialog.dismiss();
            }
        });
        dialog.setView(dialogView);
        dialog.show();
    }


    @Override
    protected void onResume() {
        super.onResume();
//        isHandleNotification=false;

        try {
            LandingActivity.ll_RightMenu.setClickable(true);
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            hideProgress();
        }catch (Exception e){
            e.printStackTrace();
        }

        if(isNotificationClicked){
            isNotificationClicked=false;
            String key=getIntent().getExtras().getString("key");
            try {
                if (key.equalsIgnoreCase("account")) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("notification_id", getIntent().getIntExtra("notification_id", 0));
                    ProfileFragment profileFragment = new ProfileFragment();
                    profileFragment.setArguments(bundle);
                    menuPlaying(replaceFragment(profileFragment));
                } else if (key.equalsIgnoreCase("verification_steps")) {
                    Intent mIntent = new Intent(this, VerificationActivity.class);
                    mIntent.putExtra("notification_id", getIntent().getIntExtra("notification_id", 0));
                    mIntent.putExtra("from", "notification");
                    startActivityForResult(mIntent, NOTIFICATION_REQUEST_CODE);
                    // startActivity(mIntent);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }else {
            if (isFromNotification) {
                try {
                    if (!isFromhandle) {
                        play();
                    } else if (isFromhandle && key.equalsIgnoreCase("account")) {
                        isFromhandle = false;
                        Bundle bundle = new Bundle();
                        bundle.putInt("notification_id", notification_id);
                        ProfileFragment profileFragment = new ProfileFragment();
                        profileFragment.setArguments(bundle);
                        menuPlaying(replaceFragment(profileFragment));
                    } else if (isFromhandle && key.equalsIgnoreCase("verification_steps")) {
                        isFromhandle = false;
                        Intent mIntent = new Intent(this, VerificationActivity.class);
                        mIntent.putExtra("notification_id", notification_id);
                        mIntent.putExtra("from", "notification");
                        startActivityForResult(mIntent,NOTIFICATION_REQUEST_CODE);
                        //startActivity(mIntent);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                isFromNotification = false;
            }
        }

    }




    @Override
    protected void onStart() {
        super.onStart();
//        if (getIntent().getExtras().getString("frgToLoad").equalsIgnoreCase("CalFragment")) {
//            if (getSupportFragmentManager().getBackStackEntryCount() > 0)
//                getSupportFragmentManager(). popBackStackImmediate("com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.fragment.CalFragment", 0);
//        }
    }


    @Override
    public void onLocationChanged(Location location) {
        int lat = (int) (location.getLatitude());
        int lng = (int) (location.getLongitude());


        /*latituteField.setText(String.valueOf(lat));
        longitudeField.setText(String.valueOf(lng));*/
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onBackPressed() {
        alert("Are you sure want to exit application ?", "Cancel", "OK");
    }


    public void alert(String title, String cancel, String accept) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setCancelable(false)
                .create();

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_tc, null);
        TextView tv = dialogView.findViewById(R.id.tvText);
        TextView tvCancel = dialogView.findViewById(R.id.tv_cancel);
        TextView tvAccept = dialogView.findViewById(R.id.tv_clear);
        tv.setText(title);
        tvCancel.setText(accept);
        tvAccept.setText(cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setView(dialogView);
        dialog.show();
    }


    //BackGround Killed
    public void handleNotifications(){
        if(!isHandleNotification) {
            isHandleNotification=true;
            try {
                if (getIntent().getExtras() != null) {
                    isFromNotification = true;

                    if (getIntent().getExtras().get("key").toString().equalsIgnoreCase("shift_invitation")
                            || getIntent().getExtras().get("key").toString().equalsIgnoreCase("open_shift")) {
                        try {
                            Intent intent = new Intent(this, ShiftDetailActivity.class);
                            intent.putExtra("isFrom", "notification");
                            intent.putExtra("id", Integer.parseInt(getIntent().getExtras().get("id").toString()));
                            intent.putExtra("notification_id", Integer.parseInt(getIntent().getExtras().get("notification_id").toString()));
                            intent.putExtra("key", getIntent().getExtras().get("key").toString());
                            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivityForResult(intent,NOTIFICATION_REQUEST_CODE);
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    if (getIntent().getExtras().get("key").toString().equalsIgnoreCase("right_to_work")) {
                        try {
                            Intent mIntent = new Intent(this, RightToWorkActivity.class);
                            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            mIntent.putExtra("from", "notification");
                            mIntent.putExtra("notification_id", Integer.parseInt(getIntent().getExtras().get("notification_id").toString()));
                            //startActivity(mIntent);
                            startActivityForResult(mIntent,NOTIFICATION_REQUEST_CODE);
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                            //startActivityClearTop(RightToWorkActivity.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (getIntent().getExtras().get("key").toString().equalsIgnoreCase("account")) {
                        try {
                            isNotificationClicked = false;
                            isFromhandle = true;
                            notification_id = Integer.parseInt(getIntent().getExtras().get("notification_id").toString());
                            key = getIntent().getExtras().get("key").toString();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (getIntent().getExtras().get("key").toString().equalsIgnoreCase("verification_steps")) {
                        try {
                            isNotificationClicked = false;
                            isFromhandle = true;
                            notification_id = Integer.parseInt(getIntent().getExtras().get("notification_id").toString());
                            key = getIntent().getExtras().get("key").toString();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else if (getIntent().getExtras().get("key").toString().equalsIgnoreCase("cancelled_booking")) {
                        try {
                            Intent mIntent=new Intent(this ,BookingDetailsActivity.class);
                            mIntent.putExtra("isFrom","notification");
                            mIntent.putExtra("key","cancelled");
                            mIntent.putExtra("id",Integer.parseInt(getIntent().getExtras().get("id").toString()));
                            //startActivity(mIntent);
                            startActivityForResult(mIntent,NOTIFICATION_REQUEST_CODE);
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (getIntent().getExtras().get("key").toString().equalsIgnoreCase("upcoming_booking")) {
                        try {
                            Intent mIntent = new Intent(this, MapActivity.class);
                            double d = (double) Integer.parseInt(getIntent().getExtras().get("id").toString());
                            int i = (int) d;
                            mIntent.putExtra("id", i);
                            mIntent.putExtra("from", "notification");
                            mIntent.putExtra("key", getIntent().getExtras().get("key").toString());
                            mIntent.putExtra("notification_id", Integer.parseInt(getIntent().getExtras().get("notification_id").toString()));
                           // startActivity(mIntent);
                            startActivityForResult(mIntent,NOTIFICATION_REQUEST_CODE);
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (getIntent().getExtras().get("key").toString().equalsIgnoreCase("event")) {
                        try {
                            event_Id = Integer.parseInt(getIntent().getExtras().get("id").toString());
                            mPresenter = new EventListPresenterImpl(this, this);
                            mPresenter.onGettingEventListNotification(Integer.parseInt(getIntent().getExtras().get("notification_id").toString()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (getIntent().getExtras().get("key").toString().equalsIgnoreCase("basic_details")) {
                        try {
                            Intent mIntent = new Intent(this, BasicDetailsActivity.class);
                            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            mIntent.putExtra("from", "notification");
                            mIntent.putExtra("notification_id", Integer.parseInt(getIntent().getExtras().get("notification_id").toString()));
                           // startActivity(mIntent);
                            startActivityForResult(mIntent,NOTIFICATION_REQUEST_CODE);
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//                            startActivityClearTop(BasicDetailsActivity.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (getIntent().getExtras().get("key").toString().equalsIgnoreCase("preferences")) {
                        try {
                            Intent mIntent = new Intent(this, PreferenceActivity.class);
                            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            mIntent.putExtra("from", "notification");
                            mIntent.putExtra("notification_id", Integer.parseInt(getIntent().getExtras().get("notification_id").toString()));
//                            startActivity(mIntent);
                            startActivityForResult(mIntent,NOTIFICATION_REQUEST_CODE);
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                            //startActivityClearTop(PreferenceActivity.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (getIntent().getExtras().get("key").toString().equalsIgnoreCase("right_to_work")) {
                        try {
                            Intent mIntent = new Intent(this, RightToWorkActivity.class);
                            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            mIntent.putExtra("from", "notification");
                            mIntent.putExtra("notification_id", Integer.parseInt(getIntent().getExtras().get("notification_id").toString()));
                            //startActivity(mIntent);
                            startActivityForResult(mIntent,NOTIFICATION_REQUEST_CODE);
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                            //startActivityClearTop(RightToWorkActivity.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (getIntent().getExtras().get("key").toString().equalsIgnoreCase("skills")) {
                        try {
                            Intent mIntent = new Intent(this, SkillActivity.class);
                            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            mIntent.putExtra("from", "notification");
                            mIntent.putExtra("notification_id", Integer.parseInt(getIntent().getExtras().get("notification_id").toString()));
                            //startActivity(mIntent);
                            startActivityForResult(mIntent,NOTIFICATION_REQUEST_CODE);
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                            //startActivityClearTop(SkillActivity.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (getIntent().getExtras().get("key").toString().equalsIgnoreCase("accreditations")) {
                        try {
                            Intent mIntent = new Intent(this, AccreditationActivity.class);
                            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            mIntent.putExtra("from", "notification");
                            mIntent.putExtra("notification_id", Integer.parseInt(getIntent().getExtras().get("notification_id").toString()));
                            //startActivity(mIntent);
                            startActivityForResult(mIntent,NOTIFICATION_REQUEST_CODE);
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                            // startActivityClearTop(AccreditationActivity.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (getIntent().getExtras().get("key").toString().equalsIgnoreCase("references")) {
                        try {
                            Intent mIntent = new Intent(this, References.class);
                            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            mIntent.putExtra("from", "notification");
                            mIntent.putExtra("notification_id", Integer.parseInt(getIntent().getExtras().get("notification_id").toString()));
                            //startActivity(mIntent);
                            startActivityForResult(mIntent,NOTIFICATION_REQUEST_CODE);
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                            //startActivityClearTop(References.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (getIntent().getExtras().get("key").toString().equalsIgnoreCase("pharmacy_compliance")) {
                        try {
                            Bundle bundle = new Bundle();
                            bundle.putInt("id", Integer.parseInt(getIntent().getExtras().get("id").toString()));
                            Intent intent = new Intent(this, PharmaComplianceDetails.class);
                            intent.putExtras(bundle);
                            intent.putExtra("from", "notification");
                            intent.putExtra("notification_id", Integer.parseInt(getIntent().getExtras().get("notification_id").toString()));
                            /*intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);*/
                            isHandleNotification=true;
                            startActivityForResult(intent,NOTIFICATION_REQUEST_CODE);
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else if (getIntent().getExtras().get("key").toString().equalsIgnoreCase("application_accepted")) {
                        try {
                            Intent mIntent = new Intent(this, MapActivity.class);
                            double d = (double) Integer.parseInt(getIntent().getExtras().get("id").toString());
                            int i = (int) d;
                            mIntent.putExtra("id", i);
                            mIntent.putExtra("from", "notification");
                            mIntent.putExtra("key", getIntent().getExtras().get("key").toString());
                            mIntent.putExtra("notification_id", Integer.parseInt(getIntent().getExtras().get("notification_id").toString()));
                            //startActivity(mIntent);
                            startActivityForResult(mIntent,NOTIFICATION_REQUEST_CODE);
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                            //startActivityForResult(mIntent,NOTIFICATION_REQUEST_CODE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Background minimized
    public void handleNotificationIntent(Intent intent){
        if(!isHandleNotification) {
            isHandleNotification=true;
            try {
                isFromNotification = true;
                /*Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    public void run() {
                        LandingActivity.click_bell.setImageDrawable(getResources().getDrawable(R.drawable.notify_dot));
                    }
                });*/
                if (intent.getExtras().get("key").toString().equalsIgnoreCase("shift_invitation") || intent.getExtras().get("key").toString().equalsIgnoreCase("open_shift")) {
                    try {
                        Intent intent1 = new Intent(this, ShiftDetailActivity.class);
                        intent1.putExtra("isFrom", "notification");
                        intent1.putExtra("id", Integer.parseInt(intent.getExtras().get("id").toString()));
                        intent1.putExtra("notification_id", Integer.parseInt(intent.getExtras().get("notification_id").toString()));
                        intent1.putExtra("key", intent.getExtras().get("key").toString());
//                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivityForResult(intent1,NOTIFICATION_REQUEST_CODE);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                        //startActivity(intent1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (intent.getExtras().get("key").toString().equalsIgnoreCase("right_to_work")) {
                    try {
                        Intent mIntent = new Intent(this, RightToWorkActivity.class);
                        //mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        mIntent.putExtra("from", "notification");
                        mIntent.putExtra("notification_id", Integer.parseInt(intent.getExtras().get("notification_id").toString()));
                       // startActivity(mIntent);
                        startActivityForResult(mIntent,NOTIFICATION_REQUEST_CODE);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                        //startActivityClearTop(RightToWorkActivity.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (intent.getExtras().get("key").toString().equalsIgnoreCase("account")) {
                    try {
                        isNotificationClicked = false;
                        isFromhandle = true;
                        notification_id = Integer.parseInt(intent.getExtras().get("notification_id").toString());
                        key = intent.getExtras().get("key").toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (intent.getExtras().get("key").toString().equalsIgnoreCase("verification_steps")) {
                    try {
                        isNotificationClicked = false;
                        isFromhandle = true;
                        notification_id = Integer.parseInt(intent.getExtras().get("notification_id").toString());
                        key = intent.getExtras().get("key").toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else if (intent.getExtras().get("key").toString().equalsIgnoreCase("cancelled_booking")) {

                    try {
                        Intent mIntent=new Intent(this ,BookingDetailsActivity.class);
                        mIntent.putExtra("isFrom","notification");
                        mIntent.putExtra("key","cancelled");
                        mIntent.putExtra("id",Integer.parseInt(intent.getExtras().get("id").toString()));
                        startActivityForResult(mIntent,NOTIFICATION_REQUEST_CODE);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                        //startActivity(mIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (intent.getExtras().get("key").toString().equalsIgnoreCase("upcoming_booking")) {
                    try {
                        Intent mIntent = new Intent(this, MapActivity.class);
                        double d = (double) Integer.parseInt(intent.getExtras().get("id").toString());
                        int i = (int) d;
                        mIntent.putExtra("id", i);
                        mIntent.putExtra("from", "notification");
                        mIntent.putExtra("key", intent.getExtras().get("key").toString());
                        mIntent.putExtra("notification_id", Integer.parseInt(intent.getExtras().get("notification_id").toString()));
                        //startActivity(mIntent);
                        startActivityForResult(mIntent,NOTIFICATION_REQUEST_CODE);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (intent.getExtras().get("key").toString().equalsIgnoreCase("event")) {
                    try {
                        event_Id = Integer.parseInt(intent.getExtras().get("id").toString());
                        mPresenter = new EventListPresenterImpl(this, this);
                        mPresenter.onGettingEventListNotification(Integer.parseInt(intent.getExtras().get("notification_id").toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (intent.getExtras().get("key").toString().equalsIgnoreCase("basic_details")) {
                    try {
                        Intent mIntent = new Intent(this, BasicDetailsActivity.class);
                        //mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        mIntent.putExtra("from", "notification");
                        mIntent.putExtra("notification_id", Integer.parseInt(intent.getExtras().get("notification_id").toString()));
                       // startActivity(mIntent);
                        startActivityForResult(mIntent,NOTIFICATION_REQUEST_CODE);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                        //startActivityClearTop(BasicDetailsActivity.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (intent.getExtras().get("key").toString().equalsIgnoreCase("preferences")) {
                    try {
                        Intent mIntent = new Intent(this, PreferenceActivity.class);
                        //mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        mIntent.putExtra("from", "notification");
                        mIntent.putExtra("notification_id", Integer.parseInt(intent.getExtras().get("notification_id").toString()));
                        //startActivity(mIntent);
                        startActivityForResult(mIntent,NOTIFICATION_REQUEST_CODE);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                        //startActivityClearTop(PreferenceActivity.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (intent.getExtras().get("key").toString().equalsIgnoreCase("right_to_work")) {
                    try {
                        Intent mIntent = new Intent(this, RightToWorkActivity.class);
                        //mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        mIntent.putExtra("from", "notification");
                        mIntent.putExtra("notification_id", Integer.parseInt(intent.getExtras().get("notification_id").toString()));
                        //startActivity(mIntent);
                        startActivityForResult(mIntent,NOTIFICATION_REQUEST_CODE);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                        // startActivityClearTop(RightToWorkActivity.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (intent.getExtras().get("key").toString().equalsIgnoreCase("skills")) {
                    try {
                        Intent mIntent = new Intent(this, SkillActivity.class);
                        //mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        mIntent.putExtra("from", "notification");
                        mIntent.putExtra("notification_id", Integer.parseInt(intent.getExtras().get("notification_id").toString()));
                        //startActivity(mIntent);
                        startActivityForResult(mIntent,NOTIFICATION_REQUEST_CODE);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                        //startActivityClearTop(SkillActivity.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (intent.getExtras().get("key").toString().equalsIgnoreCase("accreditations")) {
                    try {
                        Intent mIntent = new Intent(this, AccreditationActivity.class);
                       // mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        mIntent.putExtra("from", "notification");
                        mIntent.putExtra("notification_id", Integer.parseInt(intent.getExtras().get("notification_id").toString()));
                        //startActivity(mIntent);
                        startActivityForResult(mIntent,NOTIFICATION_REQUEST_CODE);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                        // startActivityClearTop(AccreditationActivity.class);
                    } catch (Exception e) {

                    }

                } else if (intent.getExtras().get("key").toString().equalsIgnoreCase("references")) {
                    try {
                        Intent mIntent = new Intent(this, References.class);
                        //mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        mIntent.putExtra("from", "notification");
                        mIntent.putExtra("notification_id", Integer.parseInt(intent.getExtras().get("notification_id").toString()));
                        //startActivity(mIntent);
                        startActivityForResult(mIntent,NOTIFICATION_REQUEST_CODE);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                        //startActivityClearTop(References.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (intent.getExtras().get("key").toString().equalsIgnoreCase("pharmacy_compliance")) {
                    try {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", Integer.parseInt(getIntent().getExtras().get("id").toString()));
                        Intent intent1 = new Intent(this, PharmaComplianceDetails.class);
                        intent1.putExtra("from", "notification");
                        intent1.putExtra("notification_id", Integer.parseInt(intent.getExtras().get("notification_id").toString()));
                        intent1.putExtras(bundle);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //startActivity(intent1);
                        isHandleNotification=true;
                        startActivityForResult(intent1,NOTIFICATION_REQUEST_CODE);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else if (intent.getExtras().get("key").toString().equalsIgnoreCase("application_accepted")) {
                    try {
                        Intent mIntent = new Intent(this, MapActivity.class);
                        double d = (double) Integer.parseInt(getIntent().getExtras().get("id").toString());
                        int i = (int) d;
                        mIntent.putExtra("id", i);
                        mIntent.putExtra("from", "notification");
                        mIntent.putExtra("key", getIntent().getExtras().get("key").toString());
                        mIntent.putExtra("notification_id", Integer.parseInt(getIntent().getExtras().get("notification_id").toString()));
                        //startActivity(mIntent);

                        startActivityForResult(mIntent,NOTIFICATION_REQUEST_CODE);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                        //startActivityForResult(mIntent,NOTIFICATION_REQUEST_CODE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onEventListFetched(EventListResponse eventListResponse) {
        try{
            if(eventListResponse.getEvents() != null) {
                lv_events.addAll(eventListResponse.getEvents());
                for(int i = 0; i < lv_events.size(); i++){
                    if(event_Id == lv_events.get(i).getId()){
                        EventBus.getDefault().postSticky(lv_events.get(i));
                        Intent mIntent=new Intent(this,EventsDetailsActivity.class);
                        mIntent.putExtra("from", "notification_out");
                        startActivity(mIntent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                    }else{
                       // showWarning(this, "","Error","error");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onGettingError() {

    }

    @Override
    public void onUpdateNeeded(String updateUrl) {

        AlertDialog.Builder builder = new AlertDialog.Builder(LandingActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_update,null);
        // Specify alert dialog is not cancelable/not ignorable
        builder.setCancelable(false);

        // Set the custom layout as alert dialog view
        builder.setView(dialogView);

        // Get the custom alert dialog view widgets reference
        TextView tv_title = dialogView.findViewById(R.id.tvTitle);
        TextView tvWEmail = dialogView.findViewById(R.id.tv_w_Email);
        TextView btn_positive = (TextView) dialogView.findViewById(R.id.dialog_positive_btn);
        TextView btn_negative = (TextView) dialogView.findViewById(R.id.dialog_negative_btn);
        final TextView et_name = (TextView) dialogView.findViewById(R.id.et_name);

        // Create the alert dialog
        final AlertDialog dialog = builder.create();

        // Set positive/yes button click listener
        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectStore(updateUrl);
            }
        });

        // Set negative/no button click listener
        btn_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // Display the custom alert dialog on interface
        dialog.show();
    }

    private void redirectStore(String updateUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void play(){
        if (openFragmentPosition == CalendarPosition) {
            menuPlaying(replaceFragment(new CalFragment()));
            openFragmentPosition = 0;
        } else if (openFragmentPosition == AccountPosition) {
            menuPlaying(replaceFragment(new ProfileFragment()));
            openFragmentPosition = 0;
        } else if (openFragmentPosition == NotificationPosition) {
            menuPlaying(replaceFragment(new NotifyFragment()));
            openFragmentPosition = 0;
        } else {
            menuPlaying(replaceFragment(new OpenShiftFragment()));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==NOTIFICATION_REQUEST_CODE && resultCode==NOTIFICATION_RESULT_CODE){
            isHandleNotification=true;
           // isNotifyHandled=false;
            openFragmentPosition = NotificationPosition;
            play();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        if(isHandleNotification)
        isHandleNotification=false;

        /*if(isNotifyHandled){
            isNotifyHandled=false;
        }*/
    }


    public void checkPermissions() {
        PermissionUtil.checkPermission(LandingActivity.this, Manifest.permission.ACCESS_FINE_LOCATION,
                new PermissionUtil.PermissionAskListener() {
                    @Override
                    public void onPermissionAsk() {
                        ActivityCompat.requestPermissions(
                                LandingActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_LOCATION
                        );
                    }

                    @Override
                    public void onPermissionPreviouslyDenied() {
                    }

                    @Override
                    public void onPermissionDisabled() {
                    }

                    @Override
                    public void onPermissionGranted() {
                        new MyTracker(getApplicationContext(), LandingActivity.this).track();
                    }
                });
    }

    @Override
    public void whereIAM(ADLocation adLocation) {
        SharedPrefManager.getInstance(LandingActivity.this).setCurrentLatLng(String.valueOf(adLocation.lat), String.valueOf(adLocation.longi));

        try {
            if (SharedPrefManager.getInstance(LandingActivity.this).getAddress() != null && !SharedPrefManager.getInstance(LandingActivity.this).getAddress().equals("")) {

            } else {
                OpenShiftFragment.tvSearch.setText(mUtils.getAddress(LandingActivity.this, adLocation.lat,
                        adLocation.longi));

                try {
                    ShiftDetailInviteFragment.tvSearch.setText(mUtils.getAddress(LandingActivity.this, adLocation.lat,
                            adLocation.longi));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.BookingDetailResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.CancelReason;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.HidePharmacyResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings.BookingDetailPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings.BookingDetailPresenterImpl;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.shifts.HidePharmacyPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.shifts.HidePharmacyPresenterImpl;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ApiListener;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.DialogClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.GlobalConstants;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ItemClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ListItemClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.CustomDialog;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters.BookingDetailSections;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface.BookingDetailView;
import com.mylocumchoice.MyLocumChoicePharmacy.view.login.activities.TcTerms;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.OfferActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.VerificationActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.ShiftDetailActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.viewinterface.HidePharmacyView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MapActivity extends AppActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnMarkerClickListener, BookingDetailView,ApiListener,HidePharmacyView,ItemClickListener {


    GoogleMap map;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_dots)
    ImageView tvDots;
    @BindView(R.id.tv_clear_all)
    TextView tvClearAll;
    @BindView(R.id.ll_menuRight)
    FrameLayout llMenuRight;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.bottom_sheet)
    LinearLayout bottomSheet;
    @BindView(R.id.iv_rect)
    ImageView ivRect;
    @BindView(R.id.btn_accept)
    TextView btnAccept;
    @BindView(R.id.ll_frameLayout)
    RelativeLayout llFrameLayout;
    @BindView(R.id.tv_bottom)
    TextView tvBottom;
    private BottomSheetBehavior mBottomSheetBehavior;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    List<BookingDetailResponse.CancelReason> lv_cancelReasons;

    int bookingId = 0;
    int notification_id;
    BookingDetailPresenter mPresenter;
    List<BookingDetailResponse.OtherOption> lv_others;
    private List<BookingDetailResponse.Section> arrayList = new ArrayList<>();
    String currentLat = "0.0", currentLong = "0.0";

    int REASON_REQUEST_CODE=1;
    int REASON_RESULT_CODE=2;
    int RESULT_DETAIL_CODE=13;
    public int NOTIFICATION_RESULT_CODE=1001;

    Utils mUtils;
    HidePharmacyPresenter mHidePharmacyPresenter;

    String phn_no="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        setHeader();
        mUtils = new Utils();
        try {
            if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("BookingUpcoming")) {
                currentLat = getIntent().getStringExtra("lat");
                currentLong = getIntent().getStringExtra("long");
                bookingId = getIntent().getIntExtra("id", 0);
            } else if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("Calendar")) {
                bookingId = getIntent().getIntExtra("id", 0);
            }else if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("notification")
                    &&getIntent().getStringExtra("key").equalsIgnoreCase("upcoming_booking")) {
                bookingId = getIntent().getIntExtra("id", 0);
                notification_id = getIntent().getIntExtra("notification_id", 0);
            }else if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("notificationAdapter")
                    &&getIntent().getStringExtra("key").equalsIgnoreCase("upcoming_booking")) {
                bookingId = getIntent().getIntExtra("id", 0);
                notification_id = getIntent().getIntExtra("notification_id", 0);
            }

//        else if(getIntent().getExtras().get("from").toString().equalsIgnoreCase("Notifications")){
//            bookingId = getIntent().getIntExtra("id", 0);
//        }
            else {
                bookingId = getIntent().getIntExtra("id", 0);
                currentLat = getIntent().getStringExtra("lat");
                currentLong = getIntent().getStringExtra("long");
            }
        }catch (Exception e){

        }


        if(currentLat==null && currentLong==null){
            currentLat="0.0";
            currentLong="0.0";
        }else {

        }
        mPresenter = new BookingDetailPresenterImpl(this, MapActivity.this);
        mHidePharmacyPresenter = new HidePharmacyPresenterImpl(this, MapActivity.this);

        try{
            if(getIntent().getExtras().get("from").toString().equalsIgnoreCase("notification")){
                mPresenter.onGetBookingDetailsNotification(bookingId,notification_id);
            }else if(getIntent().getExtras().get("from").toString().equalsIgnoreCase("notificationAdapter")){
                mPresenter.onGetBookingDetailsNotification(bookingId,notification_id);
            }else {
                mPresenter.onGetBookingDetails(bookingId);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        //checkLocationPermission();
        //turnGPS(this);
        if (map == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }

        View bottomSheet = findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setPeekHeight(600);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        SpannableString ss = new SpannableString("This booking can no longer be cancelled. Kindly contact the admin in case of an emergency.");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Bundle bundle = new Bundle();
                bundle.putString("isFrom", "Contact");
                startActivityWithBundle(OfferActivity.class, bundle);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ContextCompat.getColor(MapActivity.this, R.color.link));
            }
        };
        ss.setSpan(clickableSpan, 47, 65, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvBottom.setText(ss);

        tvBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("isFrom", "Contact");
                startActivityWithBundle(OfferActivity.class, bundle);
            }
        });
    }

    @OnClick(R.id.ll_back)
    public void onBackPress() {
        try {
            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("notification")) {
                    Intent mIntent = new Intent();
                    setResult(NOTIFICATION_RESULT_CODE, mIntent);
                    finish();
                } else if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("notificationAdapter")) {
                    Intent intent = new Intent(this, LandingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    LandingActivity.openFragmentPosition = 4;
                    overridePendingTransitionExit();
                } else {
                    finish();
                }
            } else {
                finish();
            }
        }catch (Exception e){
            e.printStackTrace();
            finish();
        }
    }


    /*@OnClick(R.id.ll_call)
    public void onCallPress() {
        Uri number = Uri.parse("tel:123456789");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }*/


    /*@OnClick(R.id.ll_Email)
    public void onEmailPress() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"recipient@example.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
        i.putExtra(Intent.EXTRA_TEXT, "body of email");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(MapActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

    }*/

   /* @OnClick(R.id.ll_EEmail)
    public void onEmerPress() {
        Uri number = Uri.parse("tel:123456789");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);

    }*/

    @OnClick(R.id.ll_menuRight)
    public void onMenuPressed() {
        try {
            int type = 0;
            if (lv_others.size() == 0) {

            } else if (lv_others.size() == 1) {
                type = 3;
            } else if (lv_others.size() == 2) {
                type = 2;
            } else if (lv_others.size() == 3) {
                type = 1;
            }

            List<String> lv_options = new ArrayList<>();
            for (int i = 0; i < lv_others.size(); i++) {
                lv_options.add(lv_others.get(i).getTitle());
            }

            CustomDialog customDialog = new CustomDialog(this
                    , type, lv_options, new DialogClickListener() {
                @Override
                public void onMapClick(String title) {
                    for (int i = 0; i < lv_others.size(); i++) {
                        if (title.equalsIgnoreCase(lv_others.get(i).getTitle())) {
                            if (title.equalsIgnoreCase("Hide me from this Pharmacy")) {
                                mUtils.showDialog(MapActivity.this, "", getResources().getString(R.string.confirm_hide), GlobalConstants.ConfirmHidePharmacy, MapActivity.this);

                            } else if (title.equalsIgnoreCase("Payment Policy")) {
                                if (lv_others.get(i).getValue() != null) {
                                    if (lv_others.get(i).getValue() instanceof String) {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("isTo", "payment");
                                        bundle.putString("value", (String) lv_others.get(i).getValue());
                                        startActivityWithBundle(TcTerms.class, bundle);
                                    }
                                }
                            } else if (title.equalsIgnoreCase("Cancellation Policy")) {
                                if (lv_others.get(i).getValue() != null) {
                                    if (lv_others.get(i).getValue() instanceof String) {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("isTo", "cancel");
                                        bundle.putString("value", (String) lv_others.get(i).getValue());
                                        startActivityWithBundle(TcTerms.class, bundle);
                                    }
                                }
                            }
                        }
                    }
                }

                @Override
                public void onOpenMapClick(String title) {
                    for (int i = 0; i < lv_others.size(); i++) {
                        if (title.equalsIgnoreCase(lv_others.get(i).getTitle())) {
                            if (title.equalsIgnoreCase("Hide me from this Pharmacy")) {
                                mUtils.showDialog(MapActivity.this, "", getResources().getString(R.string.confirm_hide), GlobalConstants.ConfirmHidePharmacy, MapActivity.this);
                            } else if (title.equalsIgnoreCase("Payment Policy")) {
                                if (lv_others.get(i).getValue() != null) {
                                    if (lv_others.get(i).getValue() instanceof String) {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("isTo", "payment");
                                        bundle.putString("value", (String) lv_others.get(i).getValue());
                                        startActivityWithBundle(TcTerms.class, bundle);
                                    }
                                }
                            } else if (title.equalsIgnoreCase("Cancellation Policy")) {
                                if (lv_others.get(i).getValue() != null) {
                                    if (lv_others.get(i).getValue() instanceof String) {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("isTo", "cancel");
                                        bundle.putString("value", (String) lv_others.get(i).getValue());
                                        startActivityWithBundle(TcTerms.class, bundle);
                                    }
                                }
                            }
                        }
                    }
                }

                @Override
                public void onThirdClick(String title) {
                    for (int i = 0; i < lv_others.size(); i++) {
                        if (title.equalsIgnoreCase(lv_others.get(i).getTitle())) {
                            if (title.equalsIgnoreCase("Hide me from this Pharmacy")) {
                                mUtils.showDialog(MapActivity.this, "", getResources().getString(R.string.confirm_hide), GlobalConstants.ConfirmHidePharmacy, MapActivity.this);

                            } else if (title.equalsIgnoreCase("Payment Policy")) {
                                if (lv_others.get(i).getValue() != null) {
                                    if (lv_others.get(i).getValue() instanceof String) {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("isTo", "payment");
                                        bundle.putString("value", (String) lv_others.get(i).getValue());
                                        startActivityWithBundle(TcTerms.class, bundle);
                                    }
                                }
                            } else if (title.equalsIgnoreCase("Cancellation Policy")) {
                                if (lv_others.get(i).getValue() != null) {
                                    if (lv_others.get(i).getValue() instanceof String) {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("isTo", "cancel");
                                        bundle.putString("value", (String) lv_others.get(i).getValue());
                                        startActivityWithBundle(TcTerms.class, bundle);
                                    }
                                }
                            }
                        }
                    }
                }
            });
            customDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setIndoorEnabled(false);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                map.setMyLocationEnabled(true);
            } else {
                checkLocationPermission();
            }
        } else {
            buildGoogleApiClient();
            map.setMyLocationEnabled(true);
        }

        try {
            LatLng latLng;
            if ((currentLat.equals("0.0") && currentLong.equals("0.0")) || (currentLat == null && currentLong == null)) {
                latLng = new LatLng(0.0, 0.0);
            } else {
                latLng = new LatLng(Double.parseDouble(currentLat), Double.parseDouble(currentLong));
            }
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_loc));
            mCurrLocationMarker = map.addMarker(markerOptions);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        map.setOnMarkerClickListener(this);
        LatLng latLng;
        try {
            if ((currentLat.equals("0.0") && currentLong.equals("0.0")) || (currentLat == null && currentLong == null)) {
                latLng = new LatLng(0.0, 0.0);
            } else {
                latLng = new LatLng(Double.parseDouble(currentLat), Double.parseDouble(currentLong));
            }
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_loc));
            mCurrLocationMarker = map.addMarker(markerOptions);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                showDialog(this, getResources().getString(R.string.text_permission), getResources().getString(R.string.text_Location), 1);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }


    private void turnGPS(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            showDialog(context, getResources().getString(R.string.text_gps), getResources().getString(R.string.text_Location), 2);
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_LOCATION: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if (ContextCompat.checkSelfPermission(this,
//                            android.Manifest.permission.ACCESS_FINE_LOCATION)
//                            == PackageManager.PERMISSION_GRANTED) {
//                        if (mGoogleApiClient == null) {
//                            buildGoogleApiClient();
//                        }
//                        map.setMyLocationEnabled(true);
//                    }
//
//                } else {
//                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
//                }
//                return;
//            }
//
//        }
//    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }


    public void setHeader() {
        tvHeader.setText(getResources().getString(R.string.text_booking_details));
        tvDots.setVisibility(View.VISIBLE);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public void showDialog(Context context, String title, String msg, int requestMode) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(getResources().getText(R.string.text_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Prompt the user once explanation has been shown
                        if (requestMode == 1) {
                            ActivityCompat.requestPermissions(MapActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_LOCATION);
                        }
                        if (requestMode == 2) {
                            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            context.startActivity(myIntent);
                        }
                    }
                })
                .create()
                .show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        try {
            if (isGoogleMapsInstalled()) {
                String url = "https://www.google.com/maps/dir/?api=1&destination=" + currentLat + "," + currentLong + "&travelmode=driving";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);

//                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", currentLat, currentLong);
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                startActivity(intent);
                return true;
            } else {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getResources().getString(R.string.applicationId))));
                } catch (ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getResources().getString(R.string.applicationId))));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean isGoogleMapsInstalled() {
        try {
            ApplicationInfo info = getPackageManager().getApplicationInfo("com.google.android.apps.maps", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override
    public void onDetailsFetched(BookingDetailResponse response) {
        lv_others = response.getShiftDetails().getOtherOptions();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        for (int i = 0; i < response.getShiftDetails().getSections().size(); i++) {
            arrayList = response.getShiftDetails().getSections();
        }
        BookingDetailSections adapter = new BookingDetailSections(this, response, arrayList,false,this);
        recyclerView.setAdapter(adapter);

        if (response.getShiftDetails().getCanCancel()) {
            btnAccept.setVisibility(View.VISIBLE);
            tvBottom.setVisibility(View.GONE);
        } else {
            btnAccept.setVisibility(View.GONE);
            tvBottom.setVisibility(View.VISIBLE);
        }

        if(response.getShiftDetails().getCancelReasons()!=null){
            lv_cancelReasons=new ArrayList<>();
            lv_cancelReasons.addAll(response.getShiftDetails().getCancelReasons());
        }


        if(getIntent().getExtras().get("from").toString().equalsIgnoreCase("Calendar")||getIntent().getExtras().get("from").toString().equalsIgnoreCase("notification")
                ||getIntent().getExtras().get("from").toString().equalsIgnoreCase("notificationAdapter")) {
            try {
                currentLat = response.getShiftDetails().getAddressLatitude();
                currentLong = response.getShiftDetails().getAddressLongitude();

                if (currentLat != null && currentLong != null) {
                    LatLng latLng = new LatLng(Double.parseDouble(currentLat), Double.parseDouble(currentLong));
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_loc));
                    mCurrLocationMarker = map.addMarker(markerOptions);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onGettingError() {
        showWarning(MapActivity.this, "", getResources().getString(R.string.errorTimeOut), "error");
    }

    @OnClick(R.id.btn_accept)
    public void onViewClicked() {
        CancelReason cancelReason=new CancelReason();
        cancelReason.setLv_cancelReason(lv_cancelReasons);
        EventBus.getDefault().postSticky(cancelReason);

        Intent mIntent=new Intent(MapActivity.this,SelectReasonActivity.class);
        mIntent.putExtra("bookingId",bookingId);
        startActivityForResult(mIntent,REASON_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==REASON_REQUEST_CODE && resultCode == REASON_RESULT_CODE){
            Intent mIntent=new Intent();
            setResult(RESULT_DETAIL_CODE,mIntent);
            finish();
        }
    }

    @Override
    public void onHitApi(boolean key) {
        mHidePharmacyPresenter.onHidePharmacy(bookingId);
    }

    @Override
    public void onSuccessfulHidden(HidePharmacyResponse hidePharmacyResponse) {
        showWarning(MapActivity.this, "", hidePharmacyResponse.getSuccess(), "");
        //finish();
    }

    @Override
    public void onHideFailure() {
        showWarning(MapActivity.this, "", getResources().getString(R.string.errorTimeOut), "error");
    }

    @Override
    public void onBackPressed() {
        try {
            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("notification")) {
                    Intent mIntent = new Intent();
                    setResult(NOTIFICATION_RESULT_CODE, mIntent);
                    finish();
                } else if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("notificationAdapter")) {
                    Intent intent = new Intent(this, LandingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    LandingActivity.openFragmentPosition = 4;
                    overridePendingTransitionExit();
                } else {
                    finish();
                }
            } else {
                finish();
            }
        }catch (Exception e){
            e.printStackTrace();
            finish();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    call_action(phn_no);
                } else {
                    //Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    private void call_action(String phn_no){
        Uri number = Uri.parse("tel:" + phn_no);
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(number);
        startActivity(callIntent);
    }



    @Override
    public void onClick(String data) {
        phn_no=data;
        //if(isPermissionGranted()){
            call_action(phn_no);
        //}
    }
}

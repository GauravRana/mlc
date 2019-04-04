package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.GPSTracker;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.NpaLinearLayoutManager;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.PermissionUtil;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters.PlaceAutoCompleteAdapter;

import org.ankit.gpslibrary.ADLocation;
import org.ankit.gpslibrary.MyTracker;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class SearchActivity extends AppActivity implements PlaceAutoCompleteAdapter.PlaceAutoCompleteInterface, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, OnClickListener, GestureDetector.OnGestureListener,MyTracker.ADLocationListener {
    Context mContext;
    GoogleApiClient mGoogleApiClient;
    GoogleApiClient mGoogleApiClientPlaces;

    LinearLayout mParent;
    @BindView(R.id.tv_noResults)
    TextView tvNoResults;
    @BindView(R.id.parent)
    LinearLayout parent;
    private RecyclerView mRecyclerView;
    NpaLinearLayoutManager llm;
    PlaceAutoCompleteAdapter mAdapter;
    //    List<SavedAddress> mSavedAddressList;
//    PlaceSavedAdapter mSavedAdapter;
    private static final LatLngBounds BOUNDS_INDIA = new LatLngBounds(
            new LatLng(-0, 0), new LatLng(0, 0));

    EditText mSearchEdittext;
    ImageView mClear;
    int RESULT_CODE = 1;
    int RESULT_CODE_PLACES = 2;
    private GestureDetector gestureScanner;

    /**
     * ButterKnife Code
     **/
    @BindView(R.id.down)
    ImageView down;
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.clear)
    ImageView clear;
    @BindView(R.id.list_search)
    RecyclerView listSearch;
    @BindView(R.id.google)
    ImageView google;
    @BindView(R.id.loc)
    TextView etLoc;

    ProgressDialog dialog;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    Utils mUtils;
    double latitude = 0.0, longitude = 0.0;


    /**
     * ButterKnife Code
     **/

    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        mGoogleApiClientPlaces.connect();
        super.onStart();

    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        mGoogleApiClientPlaces.disconnect();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mContext = SearchActivity.this;
        ButterKnife.bind(this);


        /* *
         * to handle search locations
         *
         */
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0 /* clientId */, this)
                .addApi(Places.GEO_DATA_API)
                .build();


        /* *
         * to handle current location
         *
         */

        mGoogleApiClientPlaces = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 1 /* clientId */, this)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        initViews();
        gestureScanner = new GestureDetector(this);
        parent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureScanner.onTouchEvent(event);
            }
        });


    }


    /*
   Initialize Views
    */
    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.list_search);
        mRecyclerView.setHasFixedSize(true);
        llm = new NpaLinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(llm);
        mUtils=new Utils();

        mSearchEdittext = (EditText) findViewById(R.id.search_et);
        mClear = (ImageView) findViewById(R.id.clear);
        mClear.setOnClickListener(this);

        AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(Place.TYPE_COUNTRY)
                .setCountry("UK")
                .build();

        mAdapter = new PlaceAutoCompleteAdapter(this, R.layout.view_placesearch,
                mGoogleApiClient, BOUNDS_INDIA, autocompleteFilter);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mSearchEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    mClear.setVisibility(View.VISIBLE);
                    if (mAdapter != null) {
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }/*else{
                        tvNoResults.setVisibility(View.VISIBLE);
                        etLoc.setVisibility(View.GONE);
                    }*/
                } else {
                    mClear.setVisibility(View.GONE);
                    mRecyclerView.setAdapter(null);
//                    if (mSavedAdapter != null && mSavedAddressList.size() > 0) {
//                        mRecyclerView.setAdapter(mSavedAdapter);
//                    }
                }
                if (s.toString().length() >= 3) {
                    if (!s.toString().equals("") && mGoogleApiClient.isConnected()) {
                        mAdapter.getFilter().filter(s.toString());
                    } else if (!mGoogleApiClient.isConnected()) {
//                    Toast.makeText(getApplicationContext(), Constants.API_NOT_CONNECTED, Toast.LENGTH_SHORT).show();
                        Log.e("", "NOT CONNECTED");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (!searchEt.getText().toString().equalsIgnoreCase("")) {
            clear.setVisibility(View.VISIBLE);
        } else {
            clear.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        if (v == mClear) {
            mSearchEdittext.setText("");
            if (mAdapter != null) {
                mAdapter.clearList();
            }
        }
    }


    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onPlaceClick(ArrayList<PlaceAutoCompleteAdapter.PlaceAutocomplete> mResultList, int position) {
        if (mResultList != null) {
            try {
                final String placeId = String.valueOf(mResultList.get(position).placeId);
                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, placeId);
                placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if (places.getCount() == 1) {
                            //Do the things here on Click.....
                            Intent data = new Intent();
                            data.putExtra("lat", String.valueOf(places.get(0).getLatLng().latitude));
                            data.putExtra("lng", String.valueOf(places.get(0).getLatLng().longitude));
                            data.putExtra("name", String.valueOf(places.get(0).getName()));
                            data.putExtra("from", "select");
                            setResult(RESULT_CODE, data);
                            onBackPressed();
                            overridePendingTransition(R.anim.stay, R.anim.slide_out_down);
                        } else {
                            showWarning(SearchActivity.this, "", "Something went wrong", "error");
                        }
                    }
                });
            } catch (Exception e) {

            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.slide_out_down);
    }

    @OnClick(R.id.down)
    public void click() {
        onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.slide_out_down);
    }

    @OnClick(R.id.clear)
    public void onCrossClick() {
        searchEt.setText("");
    }


    @OnClick(R.id.loc)
    public void onLocClick() {

        try {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkGPS();
    }


    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (e1.getY() < e2.getY()) {
            Log.d("Gesture ", " Scroll Down");
            onBackPressed();
            overridePendingTransition(R.anim.stay, R.anim.slide_out_down);
        }
        return true;
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }


    public void checkGPS() {

        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        } else {
            checkPermissions();
        }
    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public ProgressDialog createProgressDialog(Context context) {
        dialog = new ProgressDialog(context);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {

        }
        dialog.setCancelable(false);
        dialog.getWindow()
                .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialog);

        return dialog;
    }

    public void checkPermissions() {
        PermissionUtil.checkPermission(SearchActivity.this, Manifest.permission.ACCESS_FINE_LOCATION,
                new PermissionUtil.PermissionAskListener() {
                    @Override
                    public void onPermissionAsk() {
                        ActivityCompat.requestPermissions(
                                SearchActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_LOCATION
                        );
                    }

                    @Override
                    public void onPermissionPreviouslyDenied() {
                       /* mUtils = new Utils();
                        mUtils.showDialog(LandingActivity.this, getResources().getString(R.string.text_permission), getResources().getString(R.string.text_Location), 1,null);
*/
                        //show a dialog explaining permission and then request permission

                        ActivityCompat.requestPermissions(
                                SearchActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_LOCATION
                        );
                    }

                    @Override
                    public void onPermissionDisabled() {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                        //Toast.makeText(LandingActivity.this, "Permission Disabled.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionGranted() {
                        createProgressDialog(SearchActivity.this);
                        new MyTracker(getApplicationContext(),SearchActivity.this).track();
                    }
                });
    }


    @Override
    public void whereIAM(ADLocation adLocation) {
        if (null != adLocation) {
            latitude = adLocation.lat;
            longitude = adLocation.longi;

            try {
                try {
                    dialog.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent data = new Intent();

                data.putExtra("name", mUtils.getAddress(SearchActivity.this, latitude, longitude));
                data.putExtra("lat", String.valueOf(latitude));
                data.putExtra("lng", String.valueOf(longitude));
                data.putExtra("from", "unselect");

                setResult(RESULT_CODE_PLACES, data);
                onBackPressed();
                overridePendingTransition(R.anim.stay, R.anim.slide_out_down);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.api.APIInterface;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.BasicDetailRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.UpdateReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.UpdateRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.signup.AccountDetReq;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.BasicDetailsVP;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.BasicDetailsPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.UploadCVPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.UploadCvVP;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.GlobalConstants;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.LockableScrollView;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ProcessDialog;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SignOutAlert;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.signup.activities.AccountDetActivity;
import com.veinhorn.scrollgalleryview.MediaInfo;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BasicDetailsActivity extends AppActivity implements BasicDetailsVP.View, UploadCvVP.View {
    /**
     * ButterKnife Code
     **/
    @BindView(R.id.close)
    TextView close;
    @BindView(R.id.scroll_gallery_view)
    com.veinhorn.scrollgalleryview.ScrollGalleryView scrollGalleryView;
    @BindView(R.id.spin_kit)
    SpinKitView spinKit;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R.id.scrollView)
    LockableScrollView scrollView;
    @BindView(R.id.shadow)
    View shadow;
    @BindView(R.id.ll_upload)
    LinearLayout llUpload;
    @BindView(R.id.tv_upload)
    TextView tvUpload;
    @BindView(R.id.iv_upload)
    ImageView ivUpload;
    @BindView(R.id.shadow2)
    View shadow2;
    @BindView(R.id.shadow3)
    View shadow3;
    @BindView(R.id.ll_gphc)
    LinearLayout llGphc;
    @BindView(R.id.tv_gphc)
    TextView tvGphc;
    @BindView(R.id.et_gphc)
    EditText etGphc;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.tv_Name)
    TextView tvName;
    @BindView(R.id.et_Name)
    EditText etName;
    @BindView(R.id.tv_w_pName)
    TextView tv_w_pName;
    @BindView(R.id.tv_w_pGphc)
    TextView tv_w_pGphc;
    @BindView(R.id.shadow4)
    View shadow4;
    @BindView(R.id.shadow5)
    View shadow5;
    @BindView(R.id.ll_email)
    LinearLayout llEmail;
    @BindView(R.id.tv_Email)
    TextView tvEmail;
    @BindView(R.id.et_Email)
    EditText etEmail;
    @BindView(R.id.tv_w_pEmail)
    TextView tvWPEmail;
    @BindView(R.id.ll_mob)
    LinearLayout llMob;
    @BindView(R.id.tv_Mob)
    TextView tvMob;
    @BindView(R.id.et_mob)
    EditText etMob;
    @BindView(R.id.tv_w_pMob)
    TextView tvWPMob;
    @BindView(R.id.shadow6)
    View shadow6;
    @BindView(R.id.shadow7)
    View shadow7;
    @BindView(R.id.ll_add1)
    LinearLayout llAdd1;
    @BindView(R.id.tv_add1)
    TextView tvAdd1;
    @BindView(R.id.et_add1)
    EditText etAdd1;
    @BindView(R.id.tv_w_pAdd1)
    TextView tvWPAdd1;
    @BindView(R.id.ll_add2)
    LinearLayout llAdd2;
    @BindView(R.id.tv_add2)
    TextView tvAdd2;
    @BindView(R.id.et_add2)
    EditText etAdd2;
    @BindView(R.id.tv_w_pAdd2)
    TextView tvWPAdd2;
    @BindView(R.id.ll_add3)
    LinearLayout llAdd3;
    @BindView(R.id.tv_add3)
    TextView tvAdd3;
    @BindView(R.id.et_add3)
    EditText etAdd3;
    @BindView(R.id.tv_w_pAdd3)
    TextView tvWPAdd3;
    @BindView(R.id.ll_town)
    LinearLayout llTown;
    @BindView(R.id.tv_town)
    TextView tvTown;
    @BindView(R.id.et_town)
    EditText etTown;
    @BindView(R.id.tv_w_town)
    TextView tvWTown;
    @BindView(R.id.ll_county)
    LinearLayout llCounty;
    @BindView(R.id.tv_county)
    TextView tvCounty;
    @BindView(R.id.tv_w_county)
    TextView tvWCounty;
    @BindView(R.id.ll_country)
    LinearLayout llCountry;
    @BindView(R.id.tv_country)
    TextView tvCountry;
    @BindView(R.id.et_country)
    TextView etCountry;
    @BindView(R.id.tv_w_country)
    TextView tvWCountry;
    @BindView(R.id.ll_postCode)
    LinearLayout llPostCode;
    @BindView(R.id.tv_postCode)
    TextView tvPostCode;
    @BindView(R.id.et_postCode)
    EditText etPostCode;
    @BindView(R.id.tv_w_postCode)
    TextView tvWPostCode;
    @BindView(R.id.shadow11)
    View shadow11;
    @BindView(R.id.ll_frameLayout)
    LinearLayout llFrameLayout;
    @BindView(R.id.btn_accept)
    TextView btnAccept;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_dots)
    ImageView tvDots;
    @BindView(R.id.et_county)
    TextView etCounty;
    @BindView(R.id.btn_scroll_accept)
    TextView btnScrollAccept;
    @BindView(R.id.fl_btn_scroll)
    FrameLayout fbBtnScroll;
    @BindView(R.id.tv_pending)
    TextView tvPending;
    @BindView(R.id.ll_upld)
    LinearLayout llUpld;
    @BindView(R.id.tv_upld)
    TextView tvUpld;
    @BindView(R.id.iv_pen)
    ImageView ivPen;

    private String errorString;
    private String countyId = "";

    /**
     * ButterKnife Code
     **/

    public Dialog dialogCountry;
    public Dialog dialogCounty;

    private Utils mUtils;


    public static final int RC_PHOTO_PICKER_PERM = 123;
    public static final int RC_FILE_PICKER_PERM = 321;
    private static final int CUSTOM_REQUEST_CODE = 532;
    private int MAX_ATTACHMENT_COUNT = 10;

    private ArrayList<String> filePaths = new ArrayList<>();
    private SpinKitView spinKitView;
    private APIInterface apiInterface;

    private AlertDialog.Builder builderSingle;
    private AlertDialog.Builder builderDouble;
    private Map map = new HashMap();


    private Map mapId = new HashMap();
    private ProcessDialog dialog;
    private BasicDetailsPresenter presenter;
    private SignOutAlert signOutAlert;

    private UploadCVPresenter presenterUpload;
    private ContentValues values;


    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private Uri imageUri;
    private static final int CAMERA_REQUEST = 1888;
    private String fileTypes[] = {".rtf", ".ppt"};

    public int NOTIFICATION_RESULT_CODE=1001;


    private ArrayList<String> docPaths;
    private ArrayList<String> photoPaths;

    private String filepath = "";
    private String filename = "";
    private String docUrl="", docName="";
    private boolean isDocument = false;

    List<BasicDetailRes.Country> lv_countries=new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_details);
        ButterKnife.bind(this);
        scrollView.setScrollingEnabled(true);
        btnScrollAccept.setVisibility(View.GONE);
        fbBtnScroll.setVisibility(View.GONE);
        getBasicDetailsRequest();
        Resources res = getResources();
        scrollGalleryView = findViewById(R.id.scroll_gallery_view);
        spinKitView = findViewById(R.id.spin_kit);
        String text = String.format(res.getString(R.string.text_upload));
        CharSequence styledText = Html.fromHtml(text);
        tvUpload.setText(styledText);
        tvHeader.setText(getResources().getString(R.string.text_basic_det));
        tvDots.setVisibility(View.INVISIBLE);
        etEmail.clearFocus();
        etEmail.setBackgroundColor(0);
        etMob.clearFocus();
        etMob.setBackgroundColor(0);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                 Log.d("TAG",String.valueOf(scrollGalleryView.getCurrentItem()));
//                 for(int i = 0; i < scrollGalleryView; i++){
//
//                 }
            }
        });
        KeyboardVisibilityEvent.setEventListener(this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if (isOpen) {
                            scrollView.setScrollingEnabled(true);
                            //extra.setVisibility(View.VISIBLE);
                            llFrameLayout.setVisibility(View.GONE);
                            btnScrollAccept.setVisibility(View.VISIBLE);
                            fbBtnScroll.setVisibility(View.VISIBLE);
                            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) scrollView.getLayoutParams();
                            params.setMargins(0, 0, 0, 0);
                            scrollView.setLayoutParams(params);
                            DisplayMetrics displayMetrics = new DisplayMetrics();
                            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                            shadow11.setVisibility(View.GONE);

//                            if(etEmail.hasFocus()){
//                                make_visible(etEmail);
//                            }
//                            if(etMob.hasFocus()){
//                                make_visible(etMob);
//                            }
//                            if(etAdd1.hasFocus()){
//                                make_visible(etAdd1);
//                            }
//                            if(etAdd2.hasFocus()){
//                                make_visible(etAdd2);
//                            }
//                            if(etAdd3.hasFocus()){
//                                make_visible(etAdd3);
//                            }
//                            if(etTown.hasFocus()){
//                                make_visible(etTown);
//                            }
                        } else {
                            //scrollView.scrollTo(0,0);
                            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) scrollView.getLayoutParams();
                            params.setMargins(0, 0, 0, (int) getResources().getDimension(R.dimen._42sdp));
                            //scrollView.setPadding(0, 0, 0, (int) getResources().getDimension(R.dimen._42sdp));
                            scrollView.setLayoutParams(params);
                            shadow11.setVisibility(View.VISIBLE);
                            btnScrollAccept.setVisibility(View.GONE);
                            fbBtnScroll.setVisibility(View.GONE);
                            //extra.setVisibility(View.GONE);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    llFrameLayout.setVisibility(View.VISIBLE);
                                }
                            }, 200);
                        }

                    }
                });

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvWPEmail.setVisibility(View.GONE);
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
                tvWPMob.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        etAdd1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvWPAdd1.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etAdd2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvWPAdd2.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tv_w_pName.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        etGphc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tv_w_pGphc.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        etAdd3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvWPAdd3.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etTown.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvWTown.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        etCounty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etCountry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvWCountry.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etPostCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvWPostCode.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @OnClick(R.id.ll_back)
    public void onBackClick() {
        try {
            if (getIntent().getExtras() != null) {
                try {
                    if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("basic_det")) {
                        Intent intent = new Intent(this, VerificationActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        overridePendingTransitionExit();
                    } else if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("notification")) {
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
                } catch (Exception e) {
                    e.printStackTrace();
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

    @OnClick(R.id.ll_email)
    public void onEmailClick() {
        etEmail.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etEmail, InputMethodManager.SHOW_IMPLICIT);
    }

    @OnClick(R.id.ll_mob)
    public void onMobClick() {
        etMob.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etMob, InputMethodManager.SHOW_IMPLICIT);
    }

    @OnClick(R.id.ll_add2)
    public void onAdd2Click() {
        etAdd2.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etAdd2, InputMethodManager.SHOW_IMPLICIT);
    }

    @OnClick(R.id.ll_add1)
    public void onAdd1Click() {
        etAdd1.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etAdd1, InputMethodManager.SHOW_IMPLICIT);
    }

    @OnClick(R.id.ll_add3)
    public void onAdd3Click() {
        etAdd3.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etAdd3, InputMethodManager.SHOW_IMPLICIT);
    }

    @OnClick(R.id.ll_town)
    public void onTownClick() {
        etTown.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etTown, InputMethodManager.SHOW_IMPLICIT);
    }

    @OnClick(R.id.ll_postCode)
    public void onPostCode() {
        etPostCode.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etPostCode, InputMethodManager.SHOW_IMPLICIT);
    }


    @OnClick(R.id.ll_country)
    public void onCountryClick() {
        try {
            dialogCountry.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.ll_county)
    public void onCountyClick() {
        try {
            dialogCounty.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    @AfterPermissionGranted(RC_PHOTO_PICKER_PERM) @OnClick(R.id.iv_upload)
//    public void pickPhotoClicked() {
////        if (EasyPermissions.hasPermissions(this, FilePickerConst.PERMISSIONS_FILE_PICKER)) {
//            onPickPhoto();
//        } else {
//            EasyPermissions.requestPermissions(this, getString(R.string.select_photo_text),
//                    RC_PHOTO_PICKER_PERM, FilePickerConst.PERMISSIONS_FILE_PICKER);
//        }
//    }


    @OnClick(R.id.iv_upload)
    public void uploadClick() {
        onPickDoc();
    }


    @Override
    public void updateDetailsResponse(Response<UpdateRes> response) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Log.e("BASIC_DETAIL_ACTIVITY", "response: " + gson.toJson(response.body()));
        if (response.code() == 200) {
            if (response.body().getLocum().getAddressLatitude() != null && response.body().getLocum().getAddressLongitude() != null) {
                if (response.body().getLocum().getAddressLatitude() instanceof String && response.body().getLocum().getAddressLongitude() instanceof String) {
                    SharedPrefManager.getInstance(BasicDetailsActivity.this).setLatLng((String) response.body().getLocum().getAddressLatitude(), (String) response.body().getLocum().getAddressLongitude());
                }
            } else {
                try {
                    SharedPrefManager.getInstance(BasicDetailsActivity.this).setLatLng("0.0", "0.0");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            SharedPrefManager.getInstance(BasicDetailsActivity.this).setEmail(response.body().getLocum().getEmail());


            if (response.body().getLocum().getAddress_text() != null) {
                if (response.body().getLocum().getAddress_text() instanceof String) {
                    SharedPrefManager.getInstance(BasicDetailsActivity.this).setAddress((String) response.body().getLocum().getAddress_text());
                }
            } else {
                try {
                    SharedPrefManager.getInstance(BasicDetailsActivity.this).setAddress("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if(GlobalConstants.currentLocation){
                GlobalConstants.isEntered=true;
            }

            showWarning(BasicDetailsActivity.this, "", "Details updated successfully.", "");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//                    Intent intent = new Intent(BasicDetailsActivity.this, LandingActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    LandingActivity.openFragmentPosition = 5;
//                    overridePendingTransitionExit();
                    finish();
                }
            }, 500);
        } else {
            handleAPIErrors(this, response);
        }
    }


    @OnClick(R.id.btn_accept)
    public void onUpdate() {
        boolean isGphcInValid = false;
        boolean isNameInValid = false;
        boolean isEmailInValid = false;
        boolean isMobInValid = false;
        if (etEmail.getText().toString().equalsIgnoreCase("")) {
            tvWPEmail.setVisibility(View.VISIBLE);
            isEmailInValid = true;
        }

        if (!etEmail.getText().toString().equalsIgnoreCase("")) {
            if (!isValidEmail(etEmail.getText().toString().trim())) {
                tvWPEmail.setVisibility(View.VISIBLE);
                tvWPEmail.setText(getResources().getString(R.string.text_war_email));
                isEmailInValid = true;
            }
        }

        if (etMob.getText().toString().equalsIgnoreCase("")) {
            tvWPMob.setVisibility(View.VISIBLE);
            isMobInValid = true;
        }

        if (etGphc.getText().toString().equalsIgnoreCase("")) {
            tv_w_pGphc.setVisibility(View.VISIBLE);
            isGphcInValid = true;
        }

        if (!etGphc.getText().toString().equalsIgnoreCase("")) {
            if (etGphc.getText().length() != 7
                    || Integer.parseInt(etGphc.getText().toString()) < 2000000
                    || Integer.parseInt(etGphc.getText().toString()) > 3000000) {
                tv_w_pGphc.setVisibility(View.VISIBLE);
                tv_w_pGphc.setText(getResources().getString(R.string.text_war_gphc));
                isGphcInValid = true;
            }
        }

        if (etName.getText().toString().equalsIgnoreCase("")) {
            tv_w_pName.setVisibility(View.VISIBLE);
            isNameInValid = true;
        }


        else {
            if (etEmail.getText().toString().equalsIgnoreCase("")) {
                etEmail.setText(null);
            }
            if (etMob.getText().toString().equalsIgnoreCase("")) {
                etMob.setText(null);
            }
            if (etAdd1.getText().toString().equalsIgnoreCase("")) {
                etAdd1.setText(null);
            }
            if (etAdd2.getText().toString().equalsIgnoreCase("")) {
                etAdd2.setText(null);
            }
            if (etAdd3.getText().toString().equalsIgnoreCase("")) {
                etAdd3.setText(null);
            }
            if (etTown.getText().toString().equalsIgnoreCase("")) {
                etTown.setText(null);
            }
            if (etPostCode.getText().toString().equalsIgnoreCase("")) {
                etPostCode.setText(null);
            }


            if (!isEmailInValid && !isNameInValid && !isMobInValid && !isGphcInValid) {
                updateBasicDetailsRequest(
                        etGphc.getText().toString()
                        , etName.getText().toString()
                        , etEmail.getText().toString()
                        , etMob.getText().toString()
                        , etAdd1.getText().toString()
                        , etAdd2.getText().toString()
                        , etAdd3.getText().toString()
                        , SharedPrefManager.getInstance(BasicDetailsActivity.this).getCountyId()
                        , etTown.getText().toString()
                        , etPostCode.getText().toString());


            }


        }
    }


    @OnClick(R.id.btn_scroll_accept)
    public void onUpdateScroll() {

        new Handler().postDelayed(new Runnable() {

            public void run() {
                try {
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, 200);

        boolean isGphcInValid = false;
        boolean isNameInValid = false;
        boolean isEmailInValid = false;
        boolean isMobInValid = false;
        if (etEmail.getText().toString().equalsIgnoreCase("")) {
            tvWPEmail.setVisibility(View.VISIBLE);
            isEmailInValid = true;
        }

        if (!etEmail.getText().toString().equalsIgnoreCase("")) {
            if (!isValidEmail(etEmail.getText().toString().trim())) {
                tvWPEmail.setVisibility(View.VISIBLE);
                tvWPEmail.setText(getResources().getString(R.string.text_war_email));
                isEmailInValid = true;
            }
        }

        if (etMob.getText().toString().equalsIgnoreCase("")) {
            tvWPMob.setVisibility(View.VISIBLE);
            isMobInValid = true;
        }

        if (etGphc.getText().toString().equalsIgnoreCase("")) {
            tv_w_pGphc.setVisibility(View.VISIBLE);
            isGphcInValid = true;
        }

        if (!etGphc.getText().toString().equalsIgnoreCase("")) {
            if (etGphc.getText().length() != 7
                    || Integer.parseInt(etGphc.getText().toString()) < 2000000
                    || Integer.parseInt(etGphc.getText().toString()) > 3000000) {
                tv_w_pGphc.setVisibility(View.VISIBLE);
                tv_w_pGphc.setText(getResources().getString(R.string.text_war_gphc));
                isGphcInValid = true;
            }
        }


        if (etName.getText().toString().equalsIgnoreCase("")) {
            tv_w_pName.setVisibility(View.VISIBLE);
            isNameInValid = true;
        }


        else {

            if (etEmail.getText().toString().equalsIgnoreCase("")) {
                etEmail.setText(null);
            }
            if (etMob.getText().toString().equalsIgnoreCase("")) {
                etMob.setText(null);
            }
            if (etAdd1.getText().toString().equalsIgnoreCase("")) {
                etAdd1.setText(null);
            }
            if (etAdd2.getText().toString().equalsIgnoreCase("")) {
                etAdd2.setText(null);
            }
            if (etAdd3.getText().toString().equalsIgnoreCase("")) {
                etAdd3.setText(null);
            }
            if (etTown.getText().toString().equalsIgnoreCase("")) {
                etTown.setText(null);
            }
            if (etPostCode.getText().toString().equalsIgnoreCase("")) {
                etPostCode.setText(null);
            }

            if (!isEmailInValid && !isNameInValid && !isMobInValid && !isGphcInValid) {
                updateBasicDetailsRequest(etGphc.getText().toString()
                        , etName.getText().toString()
                        , etEmail.getText().toString()
                        , etMob.getText().toString()
                        , etAdd1.getText().toString()
                        , etAdd2.getText().toString()
                        , etAdd3.getText().toString()
                        , SharedPrefManager.getInstance(BasicDetailsActivity.this).getCountyId()
                        , etTown.getText().toString()
                        , etPostCode.getText().toString());
            }
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    private void addThemToView(ArrayList<String> imagePaths) {
        if (imagePaths != null) {
            filePaths.addAll(imagePaths);
        }

        scrollGalleryView.post(new Runnable() {
            @Override
            public void run() {
                scrollGalleryView
                        .setThumbnailSize(200)
                        .setZoom(true)
                        .withHiddenThumbnails(false)
                        .addOnImageClickListener(() -> {
                            Log.i(getClass().getName(), "You have clicked on image");
                        })
                        .setFragmentManager(getSupportFragmentManager());
            }
        });

        Wave wave = new Wave();
        spinKitView.setIndeterminateDrawable(wave);

        ImagesFetcher fetcher = new ImagesFetcher();
        fetcher.execute();

    }


    @Override
    public void getDetailsSuccess(Response<BasicDetailRes> response) {
        try {
            boolean isNameInValid = false;
            boolean isEmailInValid = false;
            boolean isPwdInValid = false;
            boolean ismobInValid = false;
            Gson gson = new GsonBuilder().serializeNulls().create();
            Log.e("BASIC_DETAIL_ACTIVITY", "response: " + gson.toJson(response.body()));
            if (response.code() == 200) {
                response.body().getCountries();
                lv_countries=response.body().getCountries();
                showCountries(response.body().getCountries());
                showCounties(response.body().getCountries(), -1);
                if (response.body().getLocum().getGphcStatus() != null) {
                    if (response.body().getLocum().getGphcStatus().getName() != null) {
                        tvPending.setText(response.body().getLocum().getGphcStatus().getName().toString());
                       /* if (response.body().getLocum().getGphcIssueRaised() != null) {
                            tvPending.setText(underLineText(response.body().getLocum().getGphcStatus().getName().toString()));
                        } else {

                        }*/
                    }
                    if (response.body().getLocum().getGphcStatus().getColour() != null) {
                        tvPending.setTextColor(Color.parseColor(response.body().getLocum().getGphcStatus().getColour()));
                    }
                }
                if (response.body().getLocum().getGphcNo() != null) {
                    /*if (response.body().getLocum().isGphcDetailEditable()) {
                        etGphc.setEnabled(true);
                        etGphc.setTextColor(getResources().getColor(R.color.black));
                        tvGphc.setTextColor(getResources().getColor(R.color.black));
                    } else {*/
                        etGphc.setEnabled(false);
                        etGphc.setTextColor(getResources().getColor(R.color.grey));
                        tvGphc.setTextColor(getResources().getColor(R.color.grey));
                    //}
                    etGphc.setText(response.body().getLocum().getGphcNo().toString());
                }
                if (response.body().getLocum().getName() != null) {
                    /*if (response.body().getLocum().isGphcDetailEditable()) {
                        etName.setEnabled(true);
                        etName.setTextColor(getResources().getColor(R.color.black));
                        tvName.setTextColor(getResources().getColor(R.color.black));
                    } else {*/
                        etName.setEnabled(false);
                        etName.setTextColor(getResources().getColor(R.color.grey));
                        tvName.setTextColor(getResources().getColor(R.color.grey));
                    //}
                    etName.setText(response.body().getLocum().getName().toString());
                }
                if (response.body().getLocum().getGphcNo() != null) {
                    etMob.setText(response.body().getLocum().getMobile().toString());
                }
                if (response.body().getLocum().getEmail() != null) {
                    etEmail.setText(response.body().getLocum().getEmail().toString());
                }
//
                if (response.body().getLocum().getTown() != null) {
                    etTown.setText(response.body().getLocum().getTown().toString());
                }
                if (response.body().getLocum().getCounty() != null) {
                    etCounty.setText(response.body().getLocum().getCounty().toString());
                    Set setId = mapId.entrySet();
                    Iterator itrId = setId.iterator();
                    while (itrId.hasNext()) {
                        Map.Entry entry = (Map.Entry) itrId.next();
                        if (response.body().getLocum().getCounty().toString().equalsIgnoreCase(entry.getValue().toString())) {
                            SharedPrefManager.getInstance(BasicDetailsActivity.this).setCountyId(entry.getKey().toString());
                        }
                    }

                    showCountries(response.body().getCountries());
                }
                if (response.body().getLocum().getCountry() != null) {
                    etCountry.setText(response.body().getLocum().getCountry().toString());

                    int which=0;
                    for(int i=0;i<lv_countries.size();i++){
                        if(lv_countries.get(i).getName().equalsIgnoreCase(response.body().getLocum().getCountry().toString())){
                            which=i;
                        }
                    }
                    showCounties(response.body().getCountries(), which);
                }
                if (response.body().getLocum().getPostcode() != null) {
                    etPostCode.setText(response.body().getLocum().getPostcode().toString());
                }
                if (response.body().getLocum().getAddress1() != null) {
                    etAdd1.setText(response.body().getLocum().getAddress1().toString());
                }
                if (response.body().getLocum().getAddress2() != null) {
                    etAdd2.setText(response.body().getLocum().getAddress2().toString());
                }
                if (response.body().getLocum().getAddress3() != null) {
                    etAdd3.setText(response.body().getLocum().getAddress3().toString());
                }
                if (response.body().getLocum().getCvFileName() != null) {
                    tvUpld.setText(response.body().getLocum().getCvFileName().toString());
                    ivUpload.setVisibility(View.GONE);
                    ivPen.setVisibility(View.VISIBLE);
                    if(response.body().getLocum().getCvUrl()!=null&&!response.body().getLocum().getCvUrl().equals("")){
                        docUrl = response.body().getLocum().getCvUrl().toString();
                    }

                    if(response.body().getLocum().getCvFileName()!=null&&!response.body().getLocum().getCvFileName().equals("")) {
                        docName = response.body().getLocum().getCvFileName().toString();
                    }
                }


                tvPending.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*if (response.body().getLocum().getGphcIssueRaised() != null) {
                            if (!response.body().getLocum().getGphcIssueRaised().toString().equalsIgnoreCase("")) {
                                mUtils = new Utils();
                                mUtils.showInfoDialog(BasicDetailsActivity.this, response.body().getLocum().getGphcIssueRaised().toString(), GlobalConstants.PendingTextShow);
                            }
                        }*/
                    }
                });

            } else {
                handleAPIErrors(this, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(Context context, Response<Void> response) {
        if (response.code() == 200 || response.code() == 204) {
            try {
                showWarning(BasicDetailsActivity.this, "", "CV has been uploaded successfully.", "");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            handleAPIErrors(this, response);
        }
    }


    private class ImagesFetcher extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                return filePaths;
            } catch (Exception e) {
                Log.e(getClass().getName(), "Cannot load image urls", e);
            }
            return Collections.emptyList();
        }

        @Override
        protected void onPostExecute(List<String> imageUrls) {

            for (String imageUrl : imageUrls) {
                scrollGalleryView.addMedia(
                        MediaInfo.mediaLoader(new PicassoImageLoader(new File(imageUrl)))
                );
            }
        }
    }


    public void showCountries(List<BasicDetailRes.Country> countries) {
        ArrayList<String> arrayList = new ArrayList<>();
        String[] lv_arr = {};

        dialogCountry = new Dialog(this);
        //builderSingle = new AlertDialog.Builder(BasicDetailsActivity.this);
        // builderSingle.setTitle("Select Country");

        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.custom, null);
        ListView lv = (ListView) convertView.findViewById(R.id.listView1);
        TextView tv = (TextView) convertView.findViewById(R.id.tv_title);
        TextView tv_cancel = convertView.findViewById(R.id.tv_cancel);
        TextView tv_clear = convertView.findViewById(R.id.tv_clear);
        dialogCountry.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogCountry.setContentView(convertView);
        //builderSingle.setView(dialogView);
        tv.setText("Select Country");

        for (int i = 0; i < countries.size(); i++) {
            arrayList.add(i, countries.get(i).getName().toString());
        }

        lv_arr = arrayList.toArray(new String[arrayList.size()]);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(BasicDetailsActivity.this, R.layout.alert_list, lv_arr);

        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String strName = arrayAdapter.getItem(position);
                etCountry.setText(strName);
                tvWCountry.setVisibility(View.GONE);
                //etCounty.setText("");
                //if(etCountry.getText().toString().equalsIgnoreCase("Eng"))
                Set set = map.entrySet();
                Iterator itr = set.iterator();
                while (itr.hasNext()) {
                    Map.Entry entry = (Map.Entry) itr.next();
                    System.out.println(entry.getKey() + " " + entry.getValue());
                    if (strName.equalsIgnoreCase(entry.getValue().toString())) {
                        etCounty.setText(entry.getKey().toString());
                    } else {
                        etCounty.setText("");
                        SharedPrefManager.getInstance(BasicDetailsActivity.this).setCountyId("");
                    }
                }
                etCounty.setText("");
                showCounties(countries, position);
                dialogCountry.dismiss();
            }
        });


        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCountry.dismiss();
            }
        });

        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etCounty.setText("");
                etCountry.setText("");
                SharedPrefManager.getInstance(BasicDetailsActivity.this).setCountyId("");
                if (etCounty.getText().toString().equalsIgnoreCase("") && etCounty.getText().toString().equalsIgnoreCase("")) {
                    //callAPI(SharedPrefManager.getInstance(BasicDetailsActivity.this).getEmail(), SharedPrefManager.getInstance(BasicDetailsActivity.this).getAuthToken());
                    dialogCountry.dismiss();
                }
                showCountries(countries);
                showCounties(countries, -1);
                dialogCountry.dismiss();
            }
        });

    }

    public void showCounties(List<BasicDetailRes.Country> countries, int which) {
        String[] lv_arr = {};
        String[] name_arr = {};
        map.clear();
        mapId.clear();
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<String> arrayListName = new ArrayList<String>();


        dialogCounty = new Dialog(this);
        //builderSingle = new AlertDialog.Builder(BasicDetailsActivity.this);
        // builderSingle.setTitle("Select Country");

        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.alert_custom_list, null);
        ListView lv = (ListView) convertView.findViewById(R.id.listView1);
        TextView tv = (TextView) convertView.findViewById(R.id.tv_title);
        TextView tv_cancel = convertView.findViewById(R.id.tv_cancel);
        TextView tv_clear = convertView.findViewById(R.id.tv_clear);
        dialogCounty.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogCounty.setContentView(convertView);
        tv.setText("Select County");

        if (which != -1) {
            for (int j = 0; j < countries.get(which).getCounties().size(); j++) {
                arrayList.add(countries.get(which).getCounties().get(j).getName().toString());
                //arrayListID.add(countries.get(which).getCounties().get(j).getId().toString());
                map.put(countries.get(which).getCounties().get(j).getName().toString(), countries.get(which).getName().toString());
                mapId.put(countries.get(which).getCounties().get(j).getId().toString(), countries.get(which).getCounties().get(j).getName().toString());
            }
        } else {
            for (int i = 0; i < countries.size(); i++) {
                for (int j = 0; j < countries.get(i).getCounties().size(); j++) {
                    arrayList.add(countries.get(i).getCounties().get(j).getName().toString());
                    //arrayListID.add(countries.get(i).getCounties().get(j).getId().toString());
                    map.put(countries.get(i).getCounties().get(j).getName().toString(), countries.get(i).getName().toString());
                    mapId.put(countries.get(i).getCounties().get(j).getId().toString(), countries.get(i).getCounties().get(j).getName().toString());
                }
            }
        }
        //List<String> items = Arrays.asList(str.split("\\s*,\\s*"));

        lv_arr = arrayList.toArray(new String[arrayList.size()]);
        for (int i = 0; i < lv_arr.length; i++) {
            String[] parts = lv_arr[i].split("-");
            arrayListName.add(parts[0]);
            //name_arr[i] = name;
        }

        Arrays.sort(lv_arr);

        name_arr = arrayListName.toArray(new String[arrayListName.size()]);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(BasicDetailsActivity.this, R.layout.alert_list, lv_arr);

        lv.setAdapter(arrayAdapter);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCounty.dismiss();
            }
        });

        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etCounty.setText("");
                SharedPrefManager.getInstance(BasicDetailsActivity.this).setCountyId("");
                dialogCounty.dismiss();
                if(etCountry.getText().equals("")) {
                    showCounties(countries, -1);
                }
            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String strName = arrayAdapter.getItem(position);
//                String[] parts = strName.split("-");
//                String part1 = parts[0]; // 004
//                String part2 = parts[1]; // 034556
//                SharedPrefManager.getInstance(BasicDetailsActivity.this).setCountyId(part2);
                etCounty.setText(strName);
                tvWCounty.setVisibility(View.GONE);
                Set set = map.entrySet();
                Iterator itr = set.iterator();
                while (itr.hasNext()) {
                    Map.Entry entry = (Map.Entry) itr.next();
                    System.out.println(entry.getKey() + " " + entry.getValue());
                    if (strName.equalsIgnoreCase(entry.getKey().toString())) {
                        etCountry.setText(entry.getValue().toString());
                    }
                }

                Set setId = mapId.entrySet();
                Iterator itrId = setId.iterator();
                while (itrId.hasNext()) {
                    Map.Entry entry = (Map.Entry) itrId.next();
                    System.out.println(entry.getKey() + " " + entry.getValue());
                    if (strName.equalsIgnoreCase(entry.getValue().toString())) {
                        countyId = entry.getKey().toString();
                        SharedPrefManager.getInstance(BasicDetailsActivity.this).setCountyId(countyId);

                    }
                }
                dialogCounty.dismiss();

                int which=0;
                for(int i=0;i<lv_countries.size();i++){
                    if(lv_countries.get(i).getName().equalsIgnoreCase(etCountry.getText().toString())){
                        which=i;
                    }
                }
                showCountries(lv_countries);
                showCounties(lv_countries, which);

            }
        });

    }


    public void getBasicDetailsRequest() {
        presenter = new BasicDetailsPresenter(this, this);
        try {
            if (getIntent().getExtras().get("from") != null) {
                if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("notification")) {
                    presenter.getBasicDetailsNotification(this, getIntent().getIntExtra("notification_id", 0));
                } else if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("notificationAdapter")) {
                    presenter.getBasicDetailsNotification(this, getIntent().getIntExtra("notification_id", 0));
                }else {
                    presenter.getBasicDetails(this);
                }
            } else {
                presenter.getBasicDetails(this);
            }
        }catch (Exception e){
            e.printStackTrace();
            presenter.getBasicDetails(this);
        }
    }

    public void updateBasicDetailsRequest(String gphcNo
            , String name
            , String textEmail
            , String mobile
            , String address_line_1
            , String address_line_2
            , String address_line_3
            , String county_id
            , String town
            , String postcode) {
        UpdateReq request = new UpdateReq();
        presenter = new BasicDetailsPresenter(this, this);
        presenter.updateBasicDetails(this, request.add(gphcNo, textEmail, name, mobile, address_line_1, address_line_2, address_line_3, county_id, town, postcode));
    }


    public void uploadCV(String filepath) {
        presenterUpload = new UploadCVPresenter(this, this);
        File file;
        file = new File(filepath);
        long fileSizeInBytes = file.length();
        long fileSizeInKB = fileSizeInBytes / 1024;
        long fileSizeInMB = fileSizeInKB / 1024;

        if (fileSizeInMB < 10) {
            RequestBody rDoc = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part mpDoc = MultipartBody.Part.createFormData("locum[cv]", file.getName(), rDoc);
            presenterUpload.uploadCV(this, mpDoc);
        } else {
            showWarning(this, "", "Document must be less than 10MB", "error");
        }

    }

    public void onPickDoc() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //File write logic here
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                            1);
                    mUtils = new Utils();
                    //mUtils.showPermDialog(BasicDetailsActivity.this,"", getResources().getString(R.string.providePerm) );
                } else {
                    FilePickerBuilder.getInstance().setMaxCount(1)
                            .setActivityTheme(R.style.LibAppTheme)
                            .addFileSupport("RTF", fileTypes)
                            .enableDocSupport(true)
                            .pickFile(this);

                }
            } else {
                FilePickerBuilder.getInstance().setMaxCount(1)
                        .setActivityTheme(R.style.LibAppTheme)
                        .addFileSupport("RTF", fileTypes)
                        .enableDocSupport(true)
                        .pickFile(this);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FilePickerConst.REQUEST_CODE_DOC:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    isDocument = true;
                    docPaths = new ArrayList<>();
                    docPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));
                    filepath = data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS).get(0).toString();
                    String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                    File folder = new File(extStorageDirectory, "doc");
                    filename = filepath.substring(filepath.lastIndexOf("/") + 1);
                    folder.mkdir();
                    File file = new File(folder, filename);
                    try {
                        file.createNewFile();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    llUpld.setVisibility(View.VISIBLE);
                    tvUpld.setText(filename);
                    ivUpload.setVisibility(View.GONE);
                    ivPen.setVisibility(View.VISIBLE);
                    uploadCV(filepath);
                }
                break;
        }
    }


    @OnClick(R.id.tv_upld)
    public void onuploadClick() {

        /* *
         *Choosing files from document
         */
        try {
            if (isDocument) {
                if (filepath.toString().contains("pdf")) {
                    openDocument(filepath, "pdf");
                } else if (filepath.toString().contains("doc") || filepath.toString().contains("docx") || filepath.toString().contains("rtf") || filepath.toString().contains("ppt")) {
                    boolean isAppInstalled = appInstalledOrNot("com.microsoft.office.word");
                    if (isAppInstalled) {
                        openDocument(filepath, "ms");
                    } else {
                        showWarning(this, "", "Application is not installed", "error");
                        String packageName = "com.microsoft.office.word";
                        Intent intent1 = getPackageManager().getLaunchIntentForPackage(packageName);
                        try {
                            if (intent1 == null) {
                                intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
                            }
                            startActivity(intent1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }

            }
            /* *
             *files from API
             */
            else if (!docUrl.equalsIgnoreCase("")) {
                if (docUrl.toString().contains("pdf")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(docUrl.toString()), "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(Intent.createChooser(intent, "Choose an Application:"));
                }

                /* *
                 * DOWNLOAD THE FILE FOR MS
                 */

                else {
                    String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                    File folder = new File(extStorageDirectory, "download");
                    folder.mkdir();
                    File file = new File(folder, docUrl.toString());
                    try {
                        file.createNewFile();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    if (!file.exists()) {
                        References.Downloader.DownloadFile(docUrl.toString(), file);
                        File f = new File(Environment.getExternalStorageDirectory() + "/download/" + docName.toString());
                        openDocumentInternal(f);
                    } else {
                        openDocumentInternal(file);
                    }
                }
            }
        }catch (NullPointerException ne){
            ne.printStackTrace();
        }
    }

    @OnClick(R.id.iv_pen)
    public void onUpLClick() {
        onPickDoc();
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }


    public void openDocument(String filepath, String option) {
        Uri fileURI = FileProvider.getUriForFile(this,
                getPackageName() + ".provider", new File(filepath));

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
        File file = new File(filepath);
        String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
        String mimetype = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        if (extension.equalsIgnoreCase("") || mimetype == null) {
            // if there is no extension or there is no definite mimetype, still try to open the file
            if (option.equalsIgnoreCase("pdf")) {
                intent.setDataAndType(fileURI, "application/pdf");
            } else {
                intent.setDataAndType(fileURI, "application/msword");
            }
        } else {
            intent.setDataAndType(fileURI, mimetype);
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(intent, "Choose an Application:"));
    }


    public void openDocumentInternal(File file) {
        Uri fileURI = FileProvider.getUriForFile(this,
                getPackageName() + ".provider", file);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
        String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
        String mimetype = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        if (extension.equalsIgnoreCase("") || mimetype == null) {
            intent.setDataAndType(fileURI, "application/msword");
        } else {
            intent.setDataAndType(fileURI, mimetype);
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(intent, "Choose an Application:"));
    }


    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @Override
    public void onBackPressed() {
        try {
            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("basic_det")) {
                    Intent intent = new Intent(this, VerificationActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransitionExit();
                } else if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("notification")) {
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

}



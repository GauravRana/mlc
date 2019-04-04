package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PrefernceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.Section;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.PolarFieldVP;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.Right2WorkPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.Right2WorkVP;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.UploadDocPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.UploadDocVP;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ApiResponseListener;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.RecyclerViewClickPositionListener;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SignOutAlert;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.adapters.PreferenceAdapterFields;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.adapters.PreferenceAdapterSections;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import droidninja.filepicker.FilePickerConst;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RightToWorkActivity extends AppActivity implements Right2WorkVP.View,
        PreferenceAdapterFields.CallbackInterface,
        UploadDocVP.View ,
        PolarFieldVP.View,
        RecyclerViewClickPositionListener ,ApiResponseListener {


    @BindView(R.id.tv_back)
    ImageView tv_back;
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.tv_header)
    TextView tv_header;
    @BindView(R.id.tv_dots)
    ImageView tv_dots;
    @BindView(R.id.tv_clear_all)
    TextView tv_clear_all;
    @BindView(R.id.ll_menuRight)
    FrameLayout ll_menuRight;
    @BindView(R.id.rv_sections)
    RecyclerView rv_sections;
    @BindView(R.id.layout)
    RelativeLayout layout;
    @BindView(R.id.llSticky)
    LinearLayout llSticky;
    @BindView(R.id.tv_title)
    TextView tv_title;

    private int lastClickedPosition=-1;
    private UploadDocPresenter presenterUpload;
    private String filepath = "";
    private String filename = "";
    private String docUrl, docName;
    private boolean isDocument = false;
    private boolean isCamera = false;
    private boolean isPhoto = false;
    private ArrayList<String> docPaths;
    private ArrayList<String> photoPaths;
    private String fileTypes[] = {".rtf", ".ppt"};
    private String field_id;

    private SignOutAlert signOutAlert;

    private Right2WorkPresenter presenter;
    List<Section> al_sections;
    private Parcelable recyclerViewState;
    private int lastFirstVisiblePosition;
    private PreferenceAdapterSections adapterSections;
    private Response<PrefernceRes> response;
    public static int overallYScroll = 0;
    public static int savedScroll = 0;

    private LinearLayoutManager linearLayoutManager;
    private int currentPosition = 0;

    private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 101;
    private static final int ACTIVITY_REQUEST = 110;
    private Utils mUtils;

    public int NOTIFICATION_RESULT_CODE=1001;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_right_to_work);
        ButterKnife.bind(this);
        showProgress();
        init();
    }

    /**
     * Method to initialize views
     */
    private void init() {
        overallYScroll = 0;
        savedScroll = 0;
        setHeader();
        linearLayoutManager = new LinearLayoutManager(this);
        rv_sections.setLayoutManager(linearLayoutManager);
        rv_sections.setNestedScrollingEnabled(false);
        getDetails();

    }

    @OnClick({R.id.tv_back, R.id.ll_back, R.id.tv_clear_all})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tv_back) {
            onBackPressed();
        } else if (id == R.id.ll_back) {
            onBackPressed();
        } else if (id == R.id.tv_clear_all) {

        }


            try{
                if(getIntent().getExtras() != null) {
                    if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("r2w")) {
                        Intent intent = new Intent(this, VerificationActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        overridePendingTransitionExit();
                    } else if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("profileFrag")) {
                        finish();
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
                }else {
                    finish();
                }
            }catch (Exception e){
                e.printStackTrace();
                finish();
            }

    }

    /**
     * Method to get data from API
     */
    public void getDetails() {
        presenter = new Right2WorkPresenter(this, this);
        try {
            if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("notification")) {
                int notification_id = getIntent().getIntExtra("notification_id", 0);
                presenter.getRight2WorkNotification(this, notification_id);
            } else if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("notificationAdapter")) {
                int notification_id = getIntent().getIntExtra("notification_id", 0);
                presenter.getRight2WorkNotification(this, notification_id);
            } else {
                presenter.getRight2Work(this);
            }
        }catch (Exception e){
            e.printStackTrace();
            presenter.getRight2Work(this);
        }

    }


    public void setHeader() {
        tv_header.setText(getResources().getString(R.string.text_right2work));
        tv_dots.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void uploadDoc(String filepath) {
        showProgress();
        presenterUpload = new UploadDocPresenter(this, this);
        File file;
        file = new File(filepath);

        long fileSizeInBytes = file.length();
        long fileSizeInKB = fileSizeInBytes / 1024;
        long fileSizeInMB = fileSizeInKB / 1024;

        if (fileSizeInMB < 10) {
            RequestBody rDoc = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            RequestBody field = RequestBody.create(MediaType.parse("text/plain"), field_id);
            MultipartBody.Part mpDoc = MultipartBody.Part.createFormData("response[document]", file.getName(), rDoc);
            presenterUpload.uploadDoc(this, mpDoc, field);
        }
        else{
            showWarning(this, "", "Document must be less than 10MB", "error");
        }

    }


    @Override
    public void onHandleSelection(int position, Integer id) {
        field_id = String.valueOf(id);
    }


    @Override
    public void recyclerViewListClicked(View v, int position) {
        //rv_sections.scrollToPosition(position);
        lastClickedPosition=position;
        Log.e("lastClickedPosition",lastClickedPosition+"");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FilePickerConst.REQUEST_CODE_PHOTO:
                try{
                    if(resultCode== Activity.RESULT_OK && data!=null)
                    {
                        isPhoto = true;
                        isCamera = false;
                        isDocument = false;
                        photoPaths = new ArrayList<>();
                        photoPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
                        filepath = data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA).get(0).toString();
                        uploadDoc(filepath);
                    }
                    break;
                }catch (Exception e){
                    e.printStackTrace();
                }


            case FilePickerConst.REQUEST_CODE_DOC:
                try{
                    showProgress();
                    if(resultCode== Activity.RESULT_OK && data!=null)
                    {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                hideProgress();
                            }
                        }, 1000);

                        isDocument = true;
                        isCamera = false;
                        isPhoto = false;
                        if (resultCode == Activity.RESULT_OK && data != null) {
                            docPaths = new ArrayList<>();
                            docPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));
                            filepath = data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS).get(0).toString();
                            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                            File folder = new File(extStorageDirectory, "doc");
                            filename = filepath.substring(filepath.lastIndexOf("/")+1);
                            folder.mkdir();
                            File file = new File(folder, filename);
                            try {
                                file.createNewFile();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            uploadDoc(filepath);
                        }
                    }
                    break;
                }catch (Exception e){
                    e.printStackTrace();
                }


            case CAMERA_REQUEST:
                try{
                    if (resultCode == RESULT_OK) {
                        isCamera = true;
                        isPhoto = false;
                        isDocument = false;
                        filepath = getRealPathFromURI(PreferenceAdapterFields.imageUri);
                        String filename = filepath.substring(filepath.lastIndexOf("/") + 1);
                        Log.d("FilePath", "" + filepath);
                        uploadDoc(filepath);
                    }
                    break;
                }catch (Exception e){
                    e.printStackTrace();
                }

            case ACTIVITY_REQUEST:
                try{
                    if (resultCode == RESULT_OK) {
                        getDetails();
                    }
                    break;
                }catch (Exception e){
                    e.printStackTrace();
                }

        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    //API Responses Handled

    @Override
    public void onGetResponse(Context context, Response<PrefernceRes> response) {
        if (response.code() == 200) {
            Log.e("response--", response.body() + "");
            al_sections = new ArrayList<>();
            adapterSections = new PreferenceAdapterSections(RightToWorkActivity.this, "Right2Work", this, response, null,this,this);
            rv_sections.setAdapter(adapterSections);


        }  else {
            handleAPIErrors(this, response);
        }
    }

    @Override
    public void onFieldResponse(Context context, Response<Void> response) {
        if (response.code() == 204) {

        }
        else {
            handleAPIErrors(this, response);
        }
    }

    @Override
    public void onUploadResponse(Context context, Response<Void> response) {
        hideProgress();
        if(response.code() == 204){
            try {
                getDetails();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            handleAPIErrors(this, response);
        }
    }


    @Override
    public void onUploadComplianceResponse(Context context, Response<Void> response) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try{
            if (requestCode == MY_CAMERA_PERMISSION_CODE) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
//                Intent cameraIntent = new
//                        Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                } else {
                    showWarning(this, "", "Camera permission denied", "error");
                    mUtils = new Utils();
                    mUtils.showPermDialog(this,"", getResources().getString(R.string.providePerm) );
                }

            }


            if (requestCode == 1) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    showWarning(this, "", "Storage permission denied", "error");
                    mUtils = new Utils();
                    mUtils.showPermDialog(this,"", getResources().getString(R.string.providePerm) );
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void iniIntent(){
        if(getIntent().getExtras() != null){

        }
    }

    @Override
    public void onBackPressed() {

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
        try {
            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("r2w")) {
                    Intent intent = new Intent(this, VerificationActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransitionExit();
                } else if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("profileFrag")) {
                    finish();
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

    @Override
    public void onGetResponse(Response<Void> response) {
        handleAPIErrors(this,response);
    }
}
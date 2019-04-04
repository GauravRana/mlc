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
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ComplianceDetailsRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ComplianceEmailRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PharmaComplianceVerificationRes;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.PharmaCompDetailsPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.PharmaComplianceDetailsVP;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.UploadDocPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.UploadDocVP;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SignOutAlert;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.adapters.ComplianceDetFieldAdapter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.adapters.ComplianceDetSectionAdapter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.adapters.PreferenceAdapterFields;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import droidninja.filepicker.FilePickerConst;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PharmaComplianceDetails extends AppActivity implements PharmaComplianceDetailsVP.View, UploadDocVP.View, ComplianceDetFieldAdapter.CallbackInterface {

    /**
     * ButterKnife Code
     **/
    @BindView(R.id.shadow1)
    View shadow1;
    @BindView(R.id.layOut)
    LinearLayout layout;
    @BindView(R.id.tv_form)
    TextView tvForm;
    @BindView(R.id.iv_form)
    ImageView ivForm;
    @BindView(R.id.shadow2)
    View shadow2;
    @BindView(R.id.lv_items)
    RecyclerView lvItems;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tv_dots)
    ImageView tvDots;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.ll_frameLayout)
    LinearLayout llFrameLayout;
    @BindView(R.id.tv_done)
    TextView tvDone;
    @BindView(R.id.scroll_View)
    NestedScrollView scrollView;
    @BindView(R.id.tv_unDone)
    TextView tvUndone;
    @BindView(R.id.tv_clear_all)
    TextView tvClearAll;
    @BindView(R.id.ll_menuRight)
    FrameLayout llMenuRight;

    @BindView(R.id.ll_header)
    LinearLayout llHeader;
    @BindView(R.id.fl_button)
    FrameLayout flButton;


    private ArrayList<String> photoPaths;


    private boolean isDocument = false;
    private boolean isCamera = false;
    private boolean isPhoto = false;

    private static final int CAMERA_REQUEST = 101;

    public int NOTIFICATION_RESULT_CODE=1001;

    private SignOutAlert signOutAlert;

    private UploadDocPresenter presenterUpload;
    private String filepath = "";
    private String filename = "";
    private ArrayList<String> docPaths;
    private String fileTypes[] = {".rtf", ".ppt"};

    private LinearLayoutManager linearLayoutManager;
    private PharmaCompDetailsPresenter presenter;
    private ComplianceDetSectionAdapter adapter;
    private NestedScrollView scroll_View;

    private String field_id;
    private int id = 0;


    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private Utils mUtils;

    /**
     * ButterKnife Code
     **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharma_compliance_details);
        ButterKnife.bind(this);
        scroll_View=findViewById(R.id.scroll_View);
        iniIntent();
        lvItems.setLayoutManager(new LinearLayoutManager(this));
        lvItems.setNestedScrollingEnabled(false);

    }

    @OnClick({R.id.tv_back, R.id.ll_back})
    public void onViewClicked(View view) {
        try {
            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("comp")) {
                    Intent intent = new Intent(this, PharmaComplianceActivity.class);
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


    @OnClick({R.id.tv_form})
    public void onFormClick(View view) {
        presenter = new PharmaCompDetailsPresenter(this, this);
        if (getIntent().getExtras() != null) {
            presenter.emailCompliance(this, (int) getIntent().getExtras().get("id"));
        }

    }

    @OnClick(R.id.tv_done)
    public void oClick(View view) {
        presenter = new PharmaCompDetailsPresenter(this, this);
        if (getIntent().getExtras() != null) {
            presenter.postRequestVerify(this, (int) getIntent().getExtras().get("id"));
        }
    }


    public void iniIntent() {
        if (getIntent().getExtras() != null) {
            getDetails((int) getIntent().getExtras().get("id"));
        }
    }

    public void getDetails(int id) {
        presenter = new PharmaCompDetailsPresenter(this, this);
        id = (int) getIntent().getExtras().get("id");
        try {
            if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("notification")) {
                presenter.getComplianceDetailsNotification(this, id, getIntent().getIntExtra("notification_id", 0));
            } else if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("notificationAdapter")) {
                presenter.getComplianceDetailsNotification(this, id, getIntent().getIntExtra("notification_id", 0));
            } else {
                presenter.getComplianceDetails(this, id);
            }
        }catch (Exception e){
            e.printStackTrace();
            presenter.getComplianceDetails(this, id);
        }
    }


    @Override
    public void onGetDetailsResponse(Context context, Response<ComplianceDetailsRes> response) {

        if (response.code() == 200) {
            scroll_View.setVisibility(View.VISIBLE);
            try {
                list.setLayoutManager(new LinearLayoutManager(this));
                list.setNestedScrollingEnabled(false);

                if (response.body().getDocumentsToSign() != null) {
                    list.setAdapter(new ListAdapter(response));
                } else {
                    layout.setVisibility(View.GONE);
                }

                if (response.body().getName() != null) {
                    setHeader(response.body().getName().toString());
                }


                adapter = new ComplianceDetSectionAdapter(this, this, response);
                lvItems.setAdapter(adapter);


                if (response.body().getCanRequestVerification()) {
                    llFrameLayout.setVisibility(View.VISIBLE);
                    tvUndone.setVisibility(View.GONE);
                    tvDone.setVisibility(View.VISIBLE);
                } else {
                    llFrameLayout.setVisibility(View.VISIBLE);
                    tvDone.setVisibility(View.GONE);
                    tvUndone.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lvItems.getLayoutParams();
                    params.setMargins(0, 0, 0, (int) getResources().getDimension(R.dimen._15sdp));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            scroll_View.setVisibility(View.GONE);
            handleAPIErrors(this, response);
        }
    }

    public void setHeader(String title) {
        tvHeader.setText(title);
        tvDots.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onUploadResponse(Context context, Response<Void> response) {
        if (response.code() == 204) {
            try {
                iniIntent();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            handleAPIErrors(this, response);
        }
    }

    @Override
    public void onUploadComplianceResponse(Context context, Response<Void> response) {
        hideProgress();
        if (response.code() == 204) {
            try {
                iniIntent();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            handleAPIErrors(this, response);
        }

    }


    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
        private Context context;
        private Response<ComplianceDetailsRes> response;


        public ListAdapter(Response<ComplianceDetailsRes> response) {
            // TODO Auto-generated constructor stub
            this.response = response;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView tvDocText;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // create a new view
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.document_list_view, parent, false);
            final ViewHolder vh = new ViewHolder(v);
            vh.tvDocText = v.findViewById(R.id.tvDocText);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.tvDocText.setText("\u2022" + " " + response.body().getDocumentsToSign().get(position).toString());
        }

        @Override
        public int getItemCount() {
            return response.body().getDocumentsToSign().size();
        }
    }

    @Override
    public void onHandleSelection(int position, Integer id) {
        field_id = String.valueOf(id);
    }

    public void uploadDoc(String filepath) {
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
            presenterUpload.uploadComplianceDoc(this, mpDoc, field);
        } else {
            showWarning(this, "", "Document must be less than 10MB", "error");
        }

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onComplianceEmailResponse(Context context, Response<ComplianceEmailRes> response) {
        if (response.code() == 200) {
            try {
                if (response.body().getSuccess() != null) {
                    if (!response.body().getSuccess().toString().equalsIgnoreCase("")) {
                        showWarning(PharmaComplianceDetails.this, "", response.body().getSuccess().toString(), "");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            handleAPIErrors(this, response);
        }
    }

    @Override
    public void postRequestVerifyCompliance(Context context, Response<PharmaComplianceVerificationRes> response) {
        if (response.code() == 200) {
            try {
                showWarning(PharmaComplianceDetails.this, "", response.body().getSuccess().toString(), "");
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            handleAPIErrors(this, response);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            /*case FilePickerConst.REQUEST_CODE_DOC:
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
                    uploadDoc(filepath);

                }
                break;*/

            case FilePickerConst.REQUEST_CODE_PHOTO:
                try {
                    if (resultCode == Activity.RESULT_OK && data != null) {
                        isPhoto = true;
                        isCamera = false;
                        isDocument = false;
                        photoPaths = new ArrayList<>();
                        photoPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
                        filepath = data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA).get(0).toString();
                        uploadDoc(filepath);
                    }
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }


            case FilePickerConst.REQUEST_CODE_DOC:
                try {
                    showProgress();
                    if (resultCode == Activity.RESULT_OK && data != null) {
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
                            filename = filepath.substring(filepath.lastIndexOf("/") + 1);
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
                } catch (Exception e) {
                    e.printStackTrace();
                }


            case CAMERA_REQUEST:
                try {
                    if (resultCode == RESULT_OK) {
                        isCamera = true;
                        isPhoto = false;
                        isDocument = false;
                        filepath = getRealPathFromURI(ComplianceDetFieldAdapter.imageUri);
                        String filename = filepath.substring(filepath.lastIndexOf("/") + 1);
                        Log.d("FilePath", "" + filepath);
                        uploadDoc(filepath);
                    }
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onBackPressed() {
        try {
            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("compliance")) {
                    Intent intent = new Intent(this, PharmaComplianceActivity.class);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            if (requestCode == MY_CAMERA_PERMISSION_CODE) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
//                Intent cameraIntent = new
//                        Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                } else {
                    showWarning(this, "", "Camera permission denied", "error");
                    mUtils = new Utils();
                    mUtils.showPermDialog(this, "", getResources().getString(R.string.providePerm));
                }

            }

            if (requestCode == 1) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    showWarning(this, "", "Storage permission denied", "error");
                    mUtils = new Utils();
                    mUtils.showPermDialog(this, "", getResources().getString(R.string.providePerm));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

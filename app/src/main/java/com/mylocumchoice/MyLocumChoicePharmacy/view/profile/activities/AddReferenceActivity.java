package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.AddReferenceVP;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.AddReferencePresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.DialogClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.LockableScrollView;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ProcessDialog;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SignOutAlert;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.CustomDialog;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.BasicDetailsActivity.RC_PHOTO_PICKER_PERM;


public class AddReferenceActivity extends AppActivity implements AddReferenceVP.View {

    /**
     * ButterKnife Code
     **/

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.ll_menuRight)
    FrameLayout llMenuRight;
    @BindView(R.id.tv_dots)
    ImageView tvDots;
    @BindView(R.id.scrollView)
    LockableScrollView scrollView;
    @BindView(R.id.shadow)
    View shadow;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.ll_Name)
    LinearLayout llName;
    @BindView(R.id.tv_Name)
    TextView tvName;
    @BindView(R.id.et_Name)
    EditText etName;
    @BindView(R.id.tv_w_Name)
    TextView tvWName;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.tv_job)
    TextView tvJob;
    @BindView(R.id.et_job)
    EditText etJob;
    @BindView(R.id.tv_w_jt)
    TextView tvWJt;
    @BindView(R.id.ll_company)
    LinearLayout llCompany;
    @BindView(R.id.tv_Company)
    TextView tvCompany;
    @BindView(R.id.et_Company)
    EditText etCompany;
    @BindView(R.id.tv_w_comp)
    TextView tvWComp;
    @BindView(R.id.ll_Email)
    LinearLayout llEmail;
    @BindView(R.id.tv_Email)
    TextView tvEmail;
    @BindView(R.id.et_Email)
    EditText etEmail;
    @BindView(R.id.tv_w_email)
    TextView tvWEmail;
    @BindView(R.id.shadow1)
    View shadow1;
    @BindView(R.id.shadow8)
    View shadow8;
    @BindView(R.id.ll_upload)
    LinearLayout llUpload;
    @BindView(R.id.tv_upload)
    TextView tvUpload;
    @BindView(R.id.iv_upload)
    ImageView ivUpload;
    @BindView(R.id.shadow2)
    View shadow2;
    @BindView(R.id.extra)
    LinearLayout extra;
    @BindView(R.id.ll_frameLayout)
    LinearLayout llFrameLayout;
    @BindView(R.id.btn_submit)
    TextView btnSubmit;
    @BindView(R.id.tv_upld)
    TextView tvUpld;
    @BindView(R.id.iv_pen)
    ImageView ivPen;

    @BindView(R.id.ll_upld)
    LinearLayout llUpld;

    private ContentValues values;
    private Uri imageUri;
    private Bitmap photo;


    private ArrayList<String> docPaths;
    private ArrayList<String> photoPaths;

    ProcessDialog dialog;
    public String android_id;
    public String doc;
    public Uri selectedDoc;
    private static final int FILE_SELECT_CODE = 0;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int PICTURE_RESULT = 1000;
    private static final int cameraIntent = 1000;
    private String fileTypes[] = {".pdf"};
    private String filepath = "";
    private int referId = 0;

    private boolean isCamera = false;
    private boolean isDocument = false;
    private boolean isPhoto = false;
    private String filename;
    private AddReferencePresenter presenter;
    private SignOutAlert signOutAlert;
    List<String> lv_options = new ArrayList<>();

    private Utils mUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reference);
        ButterKnife.bind(this);
        setHeader();

        lv_options.add(getResources().getString(R.string.choose_camera));
        lv_options.add(getResources().getString(R.string.choose_doc));
        lv_options.add(getResources().getString(R.string.choose_photo));

        iniIntent();
        scrollView.setScrollingEnabled(false);
        KeyboardVisibilityEvent.setEventListener(this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if (isOpen) {
                            scrollView.setScrollingEnabled(true);
                            extra.setVisibility(View.VISIBLE);
                            llFrameLayout.setVisibility(View.GONE);
                            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) scrollView.getLayoutParams();
                            params.setMargins(0, 0, 0, 0);
                            scrollView.setLayoutParams(params);
                            DisplayMetrics displayMetrics = new DisplayMetrics();
                            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

                        } else {
                            //scrollView.scrollTo(0,0);
                            extra.setVisibility(View.GONE);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    llFrameLayout.setVisibility(View.VISIBLE);
                                }
                            }, 200);
                        }
                    }
                });


        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvWName.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        etJob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvWJt.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        etCompany.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvWComp.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvWEmail.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.ll_back)
    public void onBackClick() {
        onBackPressed();
    }

    @OnClick(R.id.ll_Name)
    public void onNameClick() {
        etName.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etName, InputMethodManager.SHOW_IMPLICIT);
    }

    @OnClick(R.id.ll_title)
    public void onTitleClick() {
        etJob.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etJob, InputMethodManager.SHOW_IMPLICIT);
    }

    @OnClick(R.id.ll_company)
    public void onCompanyClick() {
        etCompany.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etCompany, InputMethodManager.SHOW_IMPLICIT);
    }

    @OnClick(R.id.ll_Email)
    public void onEmailClick() {
        etEmail.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etEmail, InputMethodManager.SHOW_IMPLICIT);
    }


    @OnClick(R.id.tv_upld)
    public void onuploadClick() {
        if (getIntent().getExtras() != null) {

            /* *
             *Choosing file from camera or from gallery
             */

            if (isCamera || isPhoto) {
                Bundle bundle = new Bundle();
                bundle.putString("document", filepath.toString());
                startActivityWithBundle(DocViewer.class, bundle);
            }

            /* *
             *Choosing files from document
             */
            else if (isDocument) {
                if (filepath.toString().contains("pdf")) {
                    openDocument(filepath);
                }
            }
            /* *
             *files from API
             */
            else if (getIntent().getExtras().get("thumb") != null) {
                Bundle bundle = new Bundle();
                bundle.putString("document", getIntent().getExtras().get("docUrl").toString());
                startActivityWithBundle(DocViewer.class, bundle);
            }

            /* *
             * files from API
             */
            else {
                try {
                    if (getIntent().getExtras().get("doc").toString().contains("pdf")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(getIntent().getExtras().get("docUrl").toString()), "application/pdf");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } else {
            if (isCamera || isPhoto) {
                Bundle bundle = new Bundle();
                bundle.putString("document", filepath.toString());
                startActivityWithBundle(DocViewer.class, bundle);
            }

            /* *
             *Choosing files from document
             */

            else if (isDocument) {
                if (filepath.toString().contains("pdf")) {
                    openDocument(filepath);
                }
            }
        }
    }


    @OnClick(R.id.iv_upload)
    public void onUpIcClick() {

        CustomDialog customDialog = new CustomDialog(this
                , 1
                , lv_options
                , new DialogClickListener() {
            @Override
            public void onMapClick(String title) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA},
                                    MY_CAMERA_PERMISSION_CODE);
                        } else {
                            try {
                                values = new ContentValues();
                                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                                imageUri = getContentResolver().insert(
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                startActivityForResult(intent, CAMERA_REQUEST);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    } else {
                        try {
                            values = new ContentValues();
                            values.put(MediaStore.Images.Media.TITLE, "New Picture");
                            imageUri = getContentResolver().insert(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            startActivityForResult(intent, CAMERA_REQUEST);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onOpenMapClick(String title) {
                onPickDoc();
            }

            @Override
            public void onThirdClick(String title) {
                onPhotoPick();
            }
        });
        customDialog.show();
    }


    @OnClick(R.id.ll_upload)
    public void onUpClick() {
        CustomDialog customDialog = new CustomDialog(this
                , 1
                , lv_options
                , new DialogClickListener() {
            @Override
            public void onMapClick(String title) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA},
                                    MY_CAMERA_PERMISSION_CODE);
                        } else {
                            values = new ContentValues();
                            values.put(MediaStore.Images.Media.TITLE, "New Picture");
                            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                            imageUri = getContentResolver().insert(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            startActivityForResult(intent, CAMERA_REQUEST);
                        }
                    } else {
                        values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, "New Picture");
                        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                        imageUri = getContentResolver().insert(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, CAMERA_REQUEST);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onOpenMapClick(String title) {
                //File write logic here
                onPickDoc();

            }

            @Override
            public void onThirdClick(String title) {
                onPhotoPick();
            }
        });
        customDialog.show();
    }


    @OnClick(R.id.iv_pen)
    public void onUpLClick() {
        CustomDialog customDialog = new CustomDialog(this
                , 1
                , lv_options
                , new DialogClickListener() {
            @Override
            public void onMapClick(String title) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA},
                                    MY_CAMERA_PERMISSION_CODE);


                            mUtils = new Utils();
                            //mUtils.showPermDialog(AddReferenceActivity.this,"", getResources().getString(R.string.providePerm) );
                        } else {
                            values = new ContentValues();
                            values.put(MediaStore.Images.Media.TITLE, "New Picture");
                            imageUri = getContentResolver().insert(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            startActivityForResult(intent, CAMERA_REQUEST);
                        }
                    } else {
                        values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, "New Picture");
                        imageUri = getContentResolver().insert(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, CAMERA_REQUEST);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onOpenMapClick(String title) {
                onPickDoc();
            }

            @Override
            public void onThirdClick(String title) {
                onPhotoPick();
            }
        });
        customDialog.show();
    }

    @OnClick(R.id.btn_submit)
    public void onSubmitClick() {
        boolean isNameInValid = false;
        boolean isJobInValid = false;
        boolean isCompInValid = false;
        boolean isEmailInValid = false;
        boolean isUploadInValid = false;

        if (etName.getText().toString().equalsIgnoreCase("")) {
            tvWName.setVisibility(View.VISIBLE);
            isNameInValid = true;
        }
        if (etJob.getText().toString().equalsIgnoreCase("")) {
            tvWJt.setVisibility(View.VISIBLE);
            isJobInValid = true;
        }
        if (etCompany.getText().toString().equalsIgnoreCase("")) {
            tvWComp.setVisibility(View.VISIBLE);
            isCompInValid = true;
        }
        if (etEmail.getText().toString().equalsIgnoreCase("")) {
            tvWEmail.setVisibility(View.VISIBLE);
            isEmailInValid = true;
        }
        if (!etEmail.getText().toString().equalsIgnoreCase("")) {
            if (!isValidEmail(etEmail.getText().toString().trim())) {
                tvWEmail.setVisibility(View.VISIBLE);
                tvWEmail.setText(getResources().getString(R.string.text_war_email));
                isEmailInValid = true;
            }
        }
        if (tvUpld.getText().toString().equalsIgnoreCase("")) {
            showWarning(this, "", "Please Select file to upload", "error");
            isUploadInValid = true;
        }
        if (!isNameInValid && !isJobInValid && !isCompInValid && !isEmailInValid && !isUploadInValid) {
            if (referId == 0) {

                addReferences(
                        etName.getText().toString()
                        , etJob.getText().toString()
                        , etEmail.getText().toString()
                        , etCompany.getText().toString()
                        , filepath);
            } else {
                addReferencesID(
                        "/locum_references/" + referId
                        , etName.getText().toString()
                        , etJob.getText().toString()
                        , etEmail.getText().toString()
                        , etCompany.getText().toString()
                        , filepath);
            }

        }
    }

    public void setHeader() {
        tvHeader.setText("Add a Reference");
        tvDots.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @AfterPermissionGranted(RC_PHOTO_PICKER_PERM)
    public void pickPhotoClicked() {
        if (EasyPermissions.hasPermissions(this, FilePickerConst.PERMISSIONS_FILE_PICKER)) {
            showFileChooser();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.select_photo_text),
                    RC_PHOTO_PICKER_PERM, FilePickerConst.PERMISSIONS_FILE_PICKER);
        }
    }

    public void onPickDoc() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //File write logic here
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);

                    mUtils = new Utils();
                    //mUtils.showPermDialog(AddReferenceActivity.this,"", getResources().getString(R.string.providePerm) );
                } else {
                    FilePickerBuilder.getInstance().setMaxCount(1)
                            .setActivityTheme(R.style.LibAppTheme)
                            .addFileSupport("RTF", fileTypes)
                            .enableDocSupport(false)
                            .pickFile(this);

                }
            } else {
                FilePickerBuilder.getInstance().setMaxCount(1)
                        .setActivityTheme(R.style.LibAppTheme)
                        .addFileSupport("RTF", fileTypes)
                        .enableDocSupport(false)
                        .pickFile(this);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onPhotoPick() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //File write logic here
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                            1);

                    mUtils = new Utils();
                    //mUtils.showPermDialog(AddReferenceActivity.this,"", getResources().getString(R.string.providePerm) );
                } else {
                    FilePickerBuilder.getInstance().setMaxCount(1)
                            .setActivityTheme(R.style.LibAppTheme)
                            .pickPhoto(this);
                    FilePickerBuilder.getInstance().enableCameraSupport(false);
                }
            } else {
                FilePickerBuilder.getInstance().setMaxCount(1)
                        .setActivityTheme(R.style.LibAppTheme)
                        .pickPhoto(this);
                FilePickerBuilder.getInstance().enableCameraSupport(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FilePickerConst.REQUEST_CODE_PHOTO:
                try {
                    if (resultCode == Activity.RESULT_OK && data != null) {
                        isPhoto = true;
                        isCamera = false;
                        isDocument = false;
                        photoPaths = new ArrayList<>();
                        photoPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
                        filepath = data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA).get(0).toString();
                        llUpld.setVisibility(View.VISIBLE);
                        String filename = filepath.substring(filepath.lastIndexOf("/") + 1);
                        tvUpld.setText(filename);
                        ivUpload.setVisibility(View.GONE);
                        ivPen.setVisibility(View.VISIBLE);
                    }
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case FilePickerConst.REQUEST_CODE_DOC:
                try {
                    if (resultCode == Activity.RESULT_OK && data != null) {
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
                            llUpld.setVisibility(View.VISIBLE);
                            tvUpld.setText(filename);
                            ivUpload.setVisibility(View.GONE);
                            ivPen.setVisibility(View.VISIBLE);
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
                        filepath = getRealPathFromURI(imageUri);
                        llUpld.setVisibility(View.VISIBLE);
                        String filename = filepath.substring(filepath.lastIndexOf("/") + 1);
                        tvUpld.setText(filename);
                        ivUpload.setVisibility(View.GONE);
                        ivPen.setVisibility(View.VISIBLE);
                        Log.d("FilePath", "" + filepath);
                    }
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 200, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            showWarning(this, "", "Please install a File Manager.", "error");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
//                Intent cameraIntent = new
//                        Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                showWarning(this, "", "Camera permission denied", "error");
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
    }


    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    public void iniIntent() {
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            try {
                etName.setText(bundle.get("name").toString());
                etJob.setText(bundle.get("job").toString());
                etCompany.setText(bundle.get("comp").toString());
                etEmail.setText(bundle.get("email").toString());
                tvUpld.setText(bundle.get("doc").toString());
                referId = bundle.getInt("id");
            } catch (Exception e) {
                e.printStackTrace();
            }
            ivUpload.setVisibility(View.GONE);
            ivPen.setVisibility(View.VISIBLE);
            llUpload.setEnabled(false);
        }
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


    public void addReferences(String name
            , String job
            , String textEmail
            , String company
            , String filepath) {
        presenter = new AddReferencePresenter(this, this);
        File file;
        file = new File(filepath);
        long fileSizeInBytes = file.length();
        long fileSizeInKB = fileSizeInBytes / 1024;
        long fileSizeInMB = fileSizeInKB / 1024;

        if (fileSizeInMB < 10) {
            RequestBody rDoc = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            RequestBody rName = RequestBody.create(MediaType.parse("text/plain"), name);
            RequestBody rTextEmail = RequestBody.create(MediaType.parse("text/plain"), textEmail);
            RequestBody rJob = RequestBody.create(MediaType.parse("text/plain"), job);
            RequestBody rCompany = RequestBody.create(MediaType.parse("text/plain"), company);
            MultipartBody.Part mpDoc = MultipartBody.Part.createFormData("locum_reference[doc]", file.getName(), rDoc);
            presenter.addReference(this, rName, rTextEmail, rJob, rCompany, mpDoc);
        } else {
            showWarning(this, "", "Document must be less than 10MB", "error");
        }

    }


    public void addReferencesID(String referId
            , String name
            , String job
            , String textEmail
            , String company
            , String filepath) {
        presenter = new AddReferencePresenter(this, this);
        File file;
        file = new File(filepath);
        long fileSizeInBytes = file.length();
        long fileSizeInKB = fileSizeInBytes / 1024;
        long fileSizeInMB = fileSizeInKB / 1024;

        if (fileSizeInMB > 10) {

        }

        RequestBody rDoc = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody rName = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody rTextEmail = RequestBody.create(MediaType.parse("text/plain"), textEmail);
        RequestBody rJob = RequestBody.create(MediaType.parse("text/plain"), job);
        RequestBody rCompany = RequestBody.create(MediaType.parse("text/plain"), company);
        MultipartBody.Part mpDoc = MultipartBody.Part.createFormData("locum_reference[doc]", file.getName(), rDoc);

        if (!filepath.equalsIgnoreCase("")) {
            presenter.addReferenceId(this, referId, rName, rTextEmail, rJob, rCompany, mpDoc);
        } else {
            presenter.addReferenceWithoutDoc(this, referId, rName, rTextEmail, rJob, rCompany);
        }
    }

    public void addReferencesWithoutDoc(
            String referId
            , String name
            , String job
            , String textEmail
            , String company) {
        presenter = new AddReferencePresenter(this, this);
        RequestBody rName = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody rTextEmail = RequestBody.create(MediaType.parse("text/plain"), textEmail);
        RequestBody rJob = RequestBody.create(MediaType.parse("text/plain"), job);
        RequestBody rCompany = RequestBody.create(MediaType.parse("text/plain"), company);
        presenter.addReferenceWithoutDoc(this, referId, rName, rTextEmail, rJob, rCompany);
    }


    @Override
    public void addReferenceSuccess(Response<Void> response) {
        String errorString;
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            Log.e("ADD_REFERENCES", "response: " + gson.toJson(response.body()));
            if (response.code() == 201) {
                try {
                    Intent intent = new Intent(AddReferenceActivity.this, References.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                handleAPIErrors(this, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void addReferenceIDSuccess(Response<Void> response) {
        String errorString;
        try {
            if (!filepath.equalsIgnoreCase("")) {
                Gson gson = new GsonBuilder().serializeNulls().create();
                Log.e("REFERENCES", "response: " + gson.toJson(response.body()));
                if (response.code() == 204) {
                    try {
                        Intent intent = new Intent(AddReferenceActivity.this, References.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    handleAPIErrors(this, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void addReferenceWithoutDocSuccess(Response<Void> response) {
        String errorString;
        try {
            dialog.hide();
            Gson gson = new GsonBuilder().serializeNulls().create();
            Log.e("REFERENCES", "response: " + gson.toJson(response.body()));
            if (response.code() == 204) {
                try {
                    Intent intent = new Intent(AddReferenceActivity.this, References.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                handleAPIErrors(this, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void openDocument(String filepath) {
        Uri fileURI = FileProvider.getUriForFile(this,
                getPackageName() + ".provider", new File(filepath));

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
        File file = new File(filepath);
        String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
        String mimetype = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        if (extension.equalsIgnoreCase("") || mimetype == null) {
            intent.setDataAndType(fileURI, "application/pdf");
        } else {
            intent.setDataAndType(fileURI, mimetype);
        }
        // custom message for the intent
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(intent, "Choose an Application:"));
    }


}

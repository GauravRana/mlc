package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.EmailReferRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ReferenceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.ReferencesVP;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.ReferencesPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.GlobalConstants;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ProcessDialog;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SignOutAlert;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class References extends AppActivity implements ReferencesVP.View{

    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_dots)
    ImageView tvDots;
    @BindView(R.id.lv_items)
    RecyclerView lvItems;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.ll_header)
    LinearLayout llHeader;
    @BindView(R.id.tv_form)
    TextView tvForm;
    @BindView(R.id.iv_form)
    ImageView ivForm;
    @BindView(R.id.layout_list)
    LinearLayout layoutList;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.ll_menuRight)
    FrameLayout llMenuRight;
    @BindView(R.id.ll_AddRefer)
    LinearLayout llAddRefer;
    Utils mUtils;

    private ReferencesPresenter presenter;

    private ProcessDialog dialog;
    private SignOutAlert signOutAlert;

    public int NOTIFICATION_RESULT_CODE=1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_references);
        ButterKnife.bind(this);
        mUtils=new Utils();
        setHeader();
        lvItems.setLayoutManager(new LinearLayoutManager(this));
        lvItems.setNestedScrollingEnabled(false);
        //lvItems.setEnabled(false);
        addReferRequest();
        //callAPI(SharedPrefManager.getInstance(this).getEmail(), SharedPrefManager.getInstance(this).getAuthToken());
    }

    public void setHeader(){
        tvHeader.setText(getResources().getString(R.string.text_reference));
        tvDots.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.ll_back)
    public void onBackClick(){
        try {
            if (getIntent().getExtras() != null) {
                try {
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
                }catch (Exception e){
                    e.printStackTrace();
                    Intent mIntent=new Intent();
                    setResult(NOTIFICATION_RESULT_CODE,mIntent);
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

    @OnClick(R.id.layout_list)
    public void onFormClick(){
        startActivity(AddReferenceActivity.class);
    }

    @OnClick(R.id.tv_form)
    public void onFormLinkClick(){
        emailRequest();
    }

    @Override
    public void onEmailSuccess(Response<EmailReferRes> response) {
        try {
                String errorString;
                Gson gson = new GsonBuilder().serializeNulls().create();
                Log.e("REFERENCES", "response: " + gson.toJson(response.body()));
                if (response.code() == 200) {
                    try {
                        showWarning(References.this, "",  response.body().getSuccess(), "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
               else {
                    handleAPIErrors(this, response);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
    }


    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
        private Context context;
        private Response<ReferenceRes> response;


        public ListAdapter(Response<ReferenceRes> response,Context context) {
            // TODO Auto-generated constructor stub
            this.response = response;
            this.context = context;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.shadow)
            View shadow;
            @BindView(R.id.tv_name)
            TextView tvName;
            @BindView(R.id.tv_email)
            TextView tvEmail;
            @BindView(R.id.tv_pharma)
            TextView tvPharma;
            @BindView(R.id.tv_company)
            TextView tvCompany;
            @BindView(R.id.iv_pencil)
            ImageView ivPencil;
            @BindView(R.id.iv_image)
            ImageView ivImage;
            @BindView(R.id.tv_pdf)
            TextView tvPdf;
            @BindView(R.id.ll_status)
            LinearLayout llStatus;
            @BindView(R.id.tv_status)
            TextView tvStatus;
            @BindView(R.id.tv_upLoadText)
            TextView tvUpLoadText;
            @BindView(R.id.shadow2)
            View shadow2;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

        @NonNull
        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // create a new view
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.lv_reference_list, parent, false);
            final ListAdapter.ViewHolder vh = new ListAdapter.ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
            for(int i = 0 ; i < response.body().getLocumReferences().size(); i++) {
                if(position == i) {
                    try {
                        holder.tvName.setText(response.body().getLocumReferences().get(i).getName());
                        holder.tvEmail.setText(response.body().getLocumReferences().get(i).getEmail());
                        holder.tvPharma.setText(response.body().getLocumReferences().get(i).getJobTitle());
                        holder.tvCompany.setText(response.body().getLocumReferences().get(i).getCompany());

                        if(response.body().getLocumReferences().get(i).getDocument().getIssueRaised()!=null){
                            if(response.body().getLocumReferences().get(i).getDocument().getIssueRaised() instanceof String){
                                holder.tvUpLoadText.setText(References.this.underLineText(response.body().getLocumReferences().get(i).getDocument().getStatus().getName()));
                                holder.tvUpLoadText.setEnabled(true);
                            }
                        }else {
                            holder.tvUpLoadText.setText(response.body().getLocumReferences().get(i).getDocument().getStatus().getName());
                            holder.tvUpLoadText.setEnabled(false);
                        }

                        holder.tvUpLoadText.setTextColor(Color.parseColor(response.body().getLocumReferences().get(i).getDocument().getStatus().getColour()));

                        holder.tvPdf.setText(underLineText(response.body().getLocumReferences().get(i).getDocument().getDocFileName()));
                        if(!response.body().getLocumReferences().get(i).getEditable()){
                            holder.ivPencil.setVisibility(View.GONE);
                        }


                        Glide.with(context).load(R.drawable.doc_icon).into(holder.ivImage);


                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            holder.tvUpLoadText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mUtils.showInfoDialog(References.this,(String)response.body().getLocumReferences().get(position).getDocument().getIssueRaised(), GlobalConstants.InfoReference);
                }
            });

            holder.tvPdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(response.body().getLocumReferences().get(position).getDocument().getDocUrl() != null) {
                        if(response.body().getLocumReferences().get(position).getDocument().getDocUrl().contains("jpg")) {
                            Bundle bundle = new Bundle();
                            bundle.putString("document", response.body().getLocumReferences().get(position).getDocument().getDocUrl());
                            bundle.putString("category", (String) response.body().getLocumReferences().get(position).getDocument().getDocThumbnailUrl());
                            startActivityWithBundle(DocViewer.class, bundle);
                        }

                        if(response.body().getLocumReferences().get(position).getDocument().getDocUrl().contains("pdf")) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(Uri.parse(response.body().getLocumReferences().get(position).getDocument().getDocUrl()), "application/pdf");
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                        }


                    }
                }
            });
            holder.ivPencil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     Bundle bundle = new Bundle();
                     bundle.putString("name", response.body().getLocumReferences().get(position).getName());
                     bundle.putString("job", response.body().getLocumReferences().get(position).getJobTitle());
                     bundle.putString("comp", response.body().getLocumReferences().get(position).getCompany());
                     bundle.putString("email", response.body().getLocumReferences().get(position).getEmail());
                     bundle.putString("docUrl", response.body().getLocumReferences().get(position).getDocument().getDocUrl());
                     bundle.putString("doc", response.body().getLocumReferences().get(position).getDocument().getDocFileName());
                     bundle.putString("thumb", (String)response.body().getLocumReferences().get(position).getDocument().getDocThumbnailUrl());
                     bundle.putInt("id", response.body().getLocumReferences().get(position).getId());
                     startActivityWithBundle(AddReferenceActivity.class, bundle);
                }
            });
        }

        @Override
        public int getItemCount() {
            return response.body().getLocumReferences().size();
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public static class Downloader {

        public static void DownloadFile(String fileURL, File directory) {
            new Async(fileURL, directory).execute();
        }
    }



    static class Async extends AsyncTask<Void, Void, Void> {
        private Exception exception;

        private Context context;
        private String fileURL;
        private File directory;
        private static final int  MEGABYTE = 1024 * 1024;

        public Async(String fileURL, File directory) {
            super();
            this.fileURL = fileURL;
            this.directory = directory;
        }

        public Void doInBackground(Void... urls) {
            try {
                URL url = new URL(fileURL);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                //urlConnection.setRequestMethod("GET");
                //urlConnection.setDoOutput(true);
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(directory);
                int totalSize = urlConnection.getContentLength();

                byte[] buffer = new byte[MEGABYTE];
                int bufferLength = 0;
                while((bufferLength = inputStream.read(buffer))>0 ){
                    fileOutputStream.write(buffer, 0, bufferLength);
                }
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Void bmp) {

        }
    }


    public void emailRequest(){
        presenter = new ReferencesPresenter(this, this);
        presenter.emailRefer(this);
    }


    @Override
    public void addReferSuccess(Response<ReferenceRes> response) {
        try {
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    Log.e("REFERENCES", "response: " + gson.toJson(response.body()));
                    if (response.code() == 200) {
                        try {
                            ListAdapter adapter = new ListAdapter(response,References.this);
                            lvItems.setAdapter(adapter);
                            if(response.body().getLocumReferences().size() == 2){
                                llAddRefer.setVisibility(View.GONE);
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                layoutParams.setMargins(0, 0, 0,  (int)getResources().getDimension(R.dimen._7sdp));
                                lvItems.setLayoutParams(layoutParams);
                                //shadow4.setVisibility(View.GONE);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    } else {
                        handleAPIErrors(this, response);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
    }

    public void addReferRequest(){
        try{
            presenter = new ReferencesPresenter(this, this);
                if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("notification")) {
                    presenter.addReferencesNotification(this, getIntent().getIntExtra("notification_id", 0));
                }else if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("notificationAdapter")) {
                    presenter.addReferencesNotification(this, getIntent().getIntExtra("notification_id", 0));
                } else {
                presenter.addReferences(this);
            }
        }catch (Exception e){
            e.printStackTrace();
            presenter.addReferences(this);
        }
    }

    @Override
    public void onBackPressed() {
        try {
            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("notification")) {
                    Intent mIntent=new Intent();
                    setResult(NOTIFICATION_RESULT_CODE,mIntent);
                    finish();
                }else if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("notificationAdapter")) {
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

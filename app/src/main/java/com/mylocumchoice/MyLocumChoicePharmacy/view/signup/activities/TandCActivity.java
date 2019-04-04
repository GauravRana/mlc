package com.mylocumchoice.MyLocumChoicePharmacy.view.signup.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.EmailRegReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.EmailRegRes;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.EmailRegPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.EmailRegVP;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.GlobalConstants;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ProcessDialog;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.login.activities.TcTerms;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.OfferActivity;

import org.json.JSONObject;

import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TandCActivity extends BaseActivity implements EmailRegVP.View{

    private WebView webView;
    public TextView btnSubmit;
    public  TextView btnCAncel;
    public  TextView btnEmail;

    private TextView tc;
    private TextView ap;
    private TextView dp;
    private ProcessDialog dialog;

    private TextView tvWEmail;

    private Bundle bundle;
    private EmailRegPresenter presenter;

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tand_c);
        webView = findViewById(R.id.webView);
        btnSubmit = findViewById(R.id.btn_accept);
        btnCAncel = findViewById(R.id.btnCancel);
        btnEmail = findViewById(R.id.tv_Email);
        webView.getSettings().setJavaScriptEnabled(true);
        presenter = new EmailRegPresenter(this, this);
        tc = findViewById(R.id.tc);
        ap = findViewById(R.id.ap);
        dp = findViewById(R.id.dp);
        iniIntent();
        dialog = new ProcessDialog(this);
        dialog.show();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                offProgress(progressView, progressLayout);
//            }
//        }, 1000);
        webView.loadUrl(GlobalConstants.BaseAPI.REG_URL);
        webView.setHorizontalScrollBarEnabled(false);
        presenter = new EmailRegPresenter(this, this);
        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                dialog.hide();
            }
        });
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(Intent.ACTION_SEND);
//                i.setType("message/rfc822");
//                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
//                i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
//                i.putExtra(Intent.EXTRA_TEXT   , "body of email");
//                try {
//                    startActivity(Intent.createChooser(i, "Send mail..."));
//                } catch (android.content.ActivityNotFoundException ex) {
//                    Toast.makeText(TandCActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
//                }

                AlertDialog.Builder builder = new AlertDialog.Builder(TandCActivity.this);

                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alert_dialog_custom_view,null);

                // Specify alert dialog is not cancelable/not ignorable
                builder.setCancelable(false);

                // Set the custom layout as alert dialog view
                builder.setView(dialogView);

                // Get the custom alert dialog view widgets reference
                TextView tv_title = dialogView.findViewById(R.id.tvTitle);
                TextView tvWEmail = dialogView.findViewById(R.id.tv_w_Email);
                TextView btn_positive = (TextView) dialogView.findViewById(R.id.dialog_positive_btn);
                TextView btn_negative = (TextView) dialogView.findViewById(R.id.dialog_negative_btn);
                final EditText et_name = (EditText) dialogView.findViewById(R.id.et_name);

                // Create the alert dialog
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                et_name.setText(email);

                // Set positive/yes button click listener
                btn_positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Dismiss the alert dialog
                        String name = et_name.getText().toString();

                        if (!isValidEmail(et_name.getText().toString().trim())) {
                            tvWEmail.setVisibility(View.VISIBLE);
                            tvWEmail.setText(getResources().getString(R.string.text_war_email));
                        }else{
                            if(!et_name.getText().toString().equalsIgnoreCase("")) {
                                EmailRegReq request = new EmailRegReq();
                                presenter.getEmailReg(getApplicationContext(), request.add(et_name.getText().toString()));
                                dialog.cancel();
                            }else {
                                showWarning(TandCActivity.this,"", "Please Enter Email", "error");
                            }
                        }
//                        Toast.makeText(getApplication(),
//                                "Submitted name : " + name, Toast.LENGTH_SHORT).show();
                        // Say hello to the submitter
                        //tv_message.setText("Hello " + name + "!");
                    }
                });

                // Set negative/no button click listener
                btn_negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Dismiss/cancel the alert dialog
                        //dialog.cancel();
                        dialog.dismiss();
//                        Toast.makeText(getApplication(),
//                                "No button clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                // Display the custom alert dialog on interface
                dialog.show();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(TandCActivity.this).setChecked("1");
                finish();
//                Intent i = new Intent(TandCActivity.this, AccountDetActivity.class);
//                startActivityForResult(i, 1);
            }
        });

        btnCAncel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(TandCActivity.this).setChecked("0");
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });


        tc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle = new Bundle();
                bundle.putString("isTo", "terms");
                startActivityWithBundle(TcTerms.class, bundle);
            }
        });

        ap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle = new Bundle();
                bundle.putString("isTo", "accept");
                startActivityWithBundle(TcTerms.class, bundle);
            }
        });

        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle = new Bundle();
                bundle.putString("isTo", "privacy");
                startActivityWithBundle(TcTerms.class, bundle);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public void alert(){
//        AlertDialog.Builder alert = new AlertDialog.Builder(mContext, R.style.MyCustomDialogTheme);;
//        final EditText edittext = new EditText(ActivityContext);
//        alert.setMessage("Enter Your Message");
//        alert.setTitle("Enter Your Title");
//
//        alert.setView(edittext);
//
//        alert.setPositiveButton("Yes Option", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                //What ever you want to do with the value
//
//            }
//        });
//
//        alert.setNegativeButton("No Option", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                // what ever you want to do with No option.
//            }
//        });
//
//        alert.show();
    }


    @Override
    public void onEmailRegResponse(Context context, Response<EmailRegRes> response) {
        if(response.code() == 200){
            try {
                showWarning(this,"", response.body().getSuccess().toString(), "");
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(response.code() == 401){
            try {
                String errorString;
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                errorString = jObjError.get("error").toString();
                showWarning(TandCActivity.this, "", errorString, "error");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(response.code() == 422){
            try {
                String errorString;
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                errorString = jObjError.get("error").toString();
                showWarning(TandCActivity.this, "", errorString, "error");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
                try{
                    showWarning(TandCActivity.this, "", getResources().getString(R.string.handle_strange_error), "error");
                }catch (Exception e){
                    e.printStackTrace();
                }
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public void iniIntent(){
        if(getIntent().getExtras() != null){
            email = getIntent().getExtras().get("email").toString();
        }
    }
}

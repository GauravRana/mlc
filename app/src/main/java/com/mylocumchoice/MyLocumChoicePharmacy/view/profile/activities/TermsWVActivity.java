package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.GlobalConstants;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ProcessDialog;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TermsWVActivity extends AppActivity {

    private WebView webView;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_dots)
    ImageView tvDots;
    @BindView(R.id.ll_menuRight)
    FrameLayout llMenuRight;
    @BindView(R.id.toolbar)
    LinearLayout toolbar;
    @BindView(R.id.btn_back)
    TextView btnBack;
    private String isTo;
    private ProcessDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_wv);
        iniIntent();
        ButterKnife.bind(this);

        dialog = new ProcessDialog(this);
        dialog.show();
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);

        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                dialog.hide();
            }
        });

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                offProgress(progressView, progressLayout);
//            }
//        }, 1000);


        if(isTo.equalsIgnoreCase("terms")){
            setHeader("Terms of Use");
            webView.loadUrl(GlobalConstants.BaseAPI.TU);
        } else if(isTo.equalsIgnoreCase("accept")){
            setHeader("Acceptable Use Policy");
            webView.loadUrl(GlobalConstants.BaseAPI.AP);
        }else if(isTo.equalsIgnoreCase("privacy")) {
            setHeader("Data and Privacy Policy");
            webView.loadUrl(GlobalConstants.BaseAPI.PO);
        }else if(isTo.equalsIgnoreCase("google")){
            setHeader("Policy");
            webView.loadUrl(GlobalConstants.BaseAPI.GP);
        }else if(isTo.equalsIgnoreCase("faq")) {
            setHeader("FAQs");
            webView.loadUrl(GlobalConstants.BaseAPI.FAQs);
        }
    }

    public void setHeader(String title){
        tvHeader.setText(title);
        tvDots.setVisibility(View.INVISIBLE);
    }
    @OnClick(R.id.btn_back)
    public void onBackClick(){ finish(); }

    public void iniIntent(){
        Bundle bundle = getIntent().getExtras();
        isTo = bundle.getString("isTo");
    }
}

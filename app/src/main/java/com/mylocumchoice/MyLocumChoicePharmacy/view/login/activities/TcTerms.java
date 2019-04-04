package com.mylocumchoice.MyLocumChoicePharmacy.view.login.activities;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.GlobalConstants;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ProcessDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TcTerms extends BaseActivity {

    private WebView webView;
    @BindView(R.id.btn_back)
    TextView btn_back;
    private String isTo;
    private ProcessDialog dialog;

    String str_url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tc);
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


        if(isTo.equalsIgnoreCase("terms")){
            //setHeader("Terms of Use");
            webView.loadUrl(GlobalConstants.BaseAPI.TU);
        } else if(isTo.equalsIgnoreCase("accept")){
            //setHeader("Acceptable Use Policy");
            webView.loadUrl(GlobalConstants.BaseAPI.AP);
        }else if(isTo.equalsIgnoreCase("cancel")){
            webView.loadUrl(str_url);
        } else if(isTo.equalsIgnoreCase("payment")){
            webView.loadData(str_url, "text/html", null);
        }
        else{
            //setHeader("Data and Privacy Policy");
            webView.loadUrl(GlobalConstants.BaseAPI.PO);
        }
    }

    @OnClick(R.id.btn_back  )
    public void onBackClick(){ finish(); }

    public void iniIntent(){
        Bundle bundle = getIntent().getExtras();
        isTo = bundle.getString("isTo");

        if(isTo.equalsIgnoreCase("cancel") || isTo.equalsIgnoreCase("payment")){
            str_url=bundle.getString("value");
        }
    }
}

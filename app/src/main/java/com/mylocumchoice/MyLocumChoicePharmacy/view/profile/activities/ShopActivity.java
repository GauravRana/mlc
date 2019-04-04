package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.GlobalConstants;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;

import butterknife.ButterKnife;

public class ShopActivity extends AppActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        ButterKnife.bind(this);
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(GlobalConstants.BaseAPI.SHOP_LINK);
        webView.setHorizontalScrollBarEnabled(false);
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        onBackPressed();
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

}

package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ProcessDialog;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DocViewer extends AppActivity {
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    private String document;
    private String category;
    private URL url;
    private Bitmap bmp;
    private ProcessDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_viewer);
        ButterKnife.bind(this);
        dialog = new ProcessDialog(this);
        dialog.show();
        iniIntent();
        imageView.setVisibility(View.VISIBLE);
        Glide.with(DocViewer.this).load(document).into(imageView);
        dialog.hide();
    }

    @OnClick(R.id.ll_back)
        public void onBackPress(){
        onBackPressed();
    }

    public void iniIntent(){
        Bundle bundle = getIntent().getExtras();
        document = bundle.getString("document");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

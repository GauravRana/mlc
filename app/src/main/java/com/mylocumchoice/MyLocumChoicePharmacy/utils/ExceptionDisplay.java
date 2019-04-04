package com.mylocumchoice.MyLocumChoicePharmacy.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.login.activities.LoginActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;

public class ExceptionDisplay extends AppActivity {

    /** Android Views **/
    LinearLayout llBack;
    ImageView tvBack;
    TextView tvHeader;
    FrameLayout llMenuRight;
    ImageView tvDots;
    TextView tvClearAll;
    /** Android Views **/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exception_display_layout);
        llBack =  findViewById(R.id.ll_back);
        tvBack =  findViewById(R.id.tv_back);
        tvHeader =  findViewById(R.id.tv_header);
        llMenuRight =  findViewById(R.id.ll_menuRight);
        tvDots =  findViewById(R.id.tv_dots);
        tvClearAll =  findViewById(R.id.tv_clear_all);
        tvHeader.setText("Sign In Again");
        llBack.setVisibility(View.INVISIBLE);
        tvDots.setVisibility(View.INVISIBLE);
        TextView exception_text = (TextView) findViewById(R.id.exception_text);
        Button btnBack = (Button) findViewById(R.id.btnBack);
        //exception_text.setText(getIntent().getExtras().getString("error"));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentData();
            }
        });
    }

    @Override
    public void onBackPressed() {
        intentData();
    }

    public void intentData() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(ExceptionDisplay.this, LoginActivity.class);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(setIntent);
    }
}

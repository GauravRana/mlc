package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ComplianceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.CompliancePresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.ComplianceVP;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.adapters.PharmacyCompAdapter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.lujun.androidtagview.TagContainerLayout;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PharmaComplianceActivity extends AppActivity implements ComplianceVP.View {
    @BindView(R.id.listView)
    RecyclerView listView;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_dots)
    ImageView tvDots;
    @BindView(R.id.tv_clear_all)
    TextView tvClearAll;
    @BindView(R.id.ll_menuRight)
    FrameLayout llMenuRight;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.tv_noData)
    TextView tv_noData;
    @BindView(R.id.tv_header_sec)
    TextView tv_header_sec;



    List<ComplianceRes.Client> lv_clients;

    private CompliancePresenter presenter;
    private int tapPosition = 0;
    private PharmacyCompAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharma_compliance);
        TagContainerLayout mTagContainerLayout = (TagContainerLayout) findViewById(R.id.tagBar);
        ButterKnife.bind(this);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setHeader();
        listView.setLayoutManager(new LinearLayoutManager(this));


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    adapter.getFilter().filter(s.toString());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDetails();
    }

    @OnClick(R.id.ll_back)
    public void onBackClick() {
        try {
            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("profileFrag")) {
//                    Intent intent = new Intent(this, LandingActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    LandingActivity.openFragmentPosition = 5;
//                    overridePendingTransitionExit();
                    finish();
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
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void setHeader() {
        tvHeader.setVisibility(View.GONE);
        tv_header_sec.setVisibility(View.VISIBLE);
        tv_header_sec.setText(getResources().getString(R.string.pharma_sp));
        //tvHeader.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_custom));
        tvClearAll.setVisibility(View.GONE);
        tvClearAll.setText(getResources().getString(R.string.text_clear));
        tvDots.setVisibility(View.GONE);
    }

    @Override
    public void onGetResponse(Context context, Response<ComplianceRes> response) {
        if (response.code() == 200) {
            lv_clients=new ArrayList<>();
            lv_clients.addAll(response.body().getClients());
            try {
                adapter = new PharmacyCompAdapter(this, lv_clients);
                listView.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(response.body().getClients().size() == 0){
                tv_noData.setVisibility(View.VISIBLE);
                editText.setVisibility(View.GONE);
            }else {
                tv_noData.setVisibility(View.GONE);
                editText.setVisibility(View.VISIBLE);
            }
        } else {
            handleAPIErrors(this, response);
        }
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showWarning(Activity activity, String title, String msg, String error) {

    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        try{
            List<ComplianceRes.Client> filterdNames = new ArrayList<>();

            //looping through existing elements
            for (ComplianceRes.Client s : lv_clients) {
                //if the existing elements contains the search input
                if (s.getName().toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(s);
                }
            }

            //calling a method of the adapter class and passing the filtered list
            adapter.filterList(filterdNames);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void getDetails() {
        presenter = new CompliancePresenter(this, this);
        presenter.getCompliance(this);
    }


    @Override
    public void onBackPressed() {
        try {
            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().get("from").toString().equalsIgnoreCase("profileFrag")) {
//                    Intent intent = new Intent(this, LandingActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    LandingActivity.openFragmentPosition = 5;
//                    overridePendingTransitionExit();
                    finish();
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

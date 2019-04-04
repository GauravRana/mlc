package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PrefernceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.Selection;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.adapters.MultiSelectAdapter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.adapters.PreferenceAdapterRadio;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SingleSelectActivity extends AppActivity {

    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_dots)
    ImageView tvDots;
    @BindView(R.id.recyclerView)
    android.support.v7.widget.RecyclerView recyclerView;
    @BindView(R.id.ll_frameLayout)
    LinearLayout llFrameLayout;
    @BindView(R.id.btn_accept)
    TextView btnAccept;
    private PreferenceAdapterRadio adapterRadio;

    /** ButterKnife Code **/

    String header="";

    Selection mSelection;

    List<PrefernceRes.SelectOption> al_selectOptions;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_select);
        ButterKnife.bind(this);
        init();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterRadio = new PreferenceAdapterRadio(
                mSelection.getActivity(),
                mSelection.getContext(),
                mSelection.getSelectOptionsList(),
                mSelection.getFieldId());

        recyclerView.setAdapter(adapterRadio);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @OnClick({R.id.tv_back, R.id.ll_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                onBackPressed();
                break;
            case R.id.ll_back:
                onBackPressed();
                break;
        }
    }

    @OnClick(R.id.btn_accept)
    public void buttonClicked(View view) {
            onBackPressed();
    }
    /**
     * Method to initialize views
     */
    private void init() {
        mSelection= EventBus.getDefault().getStickyEvent(Selection.class);
        setHeader(mSelection.getType());
    }

    public void setHeader(String headerText){
        tvHeader.setText(headerText);
        tvDots.setVisibility(View.INVISIBLE);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        adapterRadio.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
//            for (int i = 0; i < data.getStringArrayListExtra("result").size(); i++) {
//                if (!data.getStringArrayListExtra("result").get(i).toString().equalsIgnoreCase("")) {
//
//                } else {
//
//                }
//            }
//        }else if(requestCode == 3){
//
//        }
        }
    }


}

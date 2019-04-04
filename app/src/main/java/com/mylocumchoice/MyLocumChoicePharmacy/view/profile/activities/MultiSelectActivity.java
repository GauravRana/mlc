package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.Preference;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.MultiSelectReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PrefernceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.SelectedOptionModel;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.Selection;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.PolarFieldPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.PolarFieldVP;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.adapters.MultiSelectAdapter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MultiSelectActivity extends AppActivity implements PolarFieldVP.View {

    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_dots)
    ImageView tvDots;
    @BindView(R.id.tv_clear_all)
    TextView tvClearAll;
    @BindView(R.id.ll_menuRight)
    FrameLayout llMenuRight;
    @BindView(R.id.rv_sections)
    RecyclerView rvSections;
    @BindView(R.id.layout)
    RelativeLayout layout;

    @BindView(R.id.tvOther)
    TextView tvOther;

    SelectedOptionModel model;

    String header="";

    Selection mSelection;
    MultiSelectAdapter multiSelectAdapter;

    List<PrefernceRes.SelectOption> al_selectOptions;

    List<PrefernceRes.Field> al_fields;
    List<SelectedOptionModel> selectedOptionModels = new ArrayList<>();

    private int id = 0;
    private PolarFieldPresenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_select);
        ButterKnife.bind(this);
        model = new SelectedOptionModel();
        init();
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
        public void done(){
        addModel();
    }

    public void addModel() {
        MultiSelectReq req = new MultiSelectReq();
        presenter = new PolarFieldPresenter(this);
        presenter.postMutiSelect(this, req.add(id,
                multiSelectAdapter.getOptions(),
                multiSelectAdapter.getValues(),
                multiSelectAdapter.getOptionId()));
        multiSelectAdapter.getOptions().clear();
        //multiSelectAdapter.getValues().clear();
    }

    /**
     * Method to initialize views
     */
    private void init() {
        al_selectOptions=new ArrayList<>();
        mSelection= EventBus.getDefault().getStickyEvent(Selection.class);
        setHeader(mSelection.getTitle());
        al_selectOptions = mSelection.getSelectOptions();
        al_fields = mSelection.getSelectedFields();
        id = mSelection.getFieldId();

        rvSections.setLayoutManager(new LinearLayoutManager(this));
        if(al_selectOptions != null) {
            for (int i = 0; i < al_selectOptions.size(); i++) {
                if (al_selectOptions.get(i).getName().equalsIgnoreCase("Other")) {
                    //tvOther.setVisibility(View.VISIBLE);
                    if (al_selectOptions.get(i).getFieldForOption() != null) {
                        if (al_selectOptions.get(i).getFieldForOption().getTextResponse() != null) {
                            //tvOther.setText(al_selectOptions.get(i).getFieldForOption().getTextResponse().toString());
                        }
                    }
                }
            }
        }

        multiSelectAdapter=new MultiSelectAdapter(this, al_selectOptions, al_fields);
        rvSections.setAdapter(multiSelectAdapter);
    }

    public void setHeader(String headerText){
        tvDots.setVisibility(View.INVISIBLE);
        tvHeader.setText(headerText);
    }


    @Override
    public void onFieldResponse(Context context, Response<Void> response) {
        if(response.code() == 204){
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        multiSelectAdapter.onActivityResult(requestCode, resultCode, data);
    }
}

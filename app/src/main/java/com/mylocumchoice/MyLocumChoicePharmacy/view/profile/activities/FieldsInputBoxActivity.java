package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.MultiSelectData;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.SingleSelectReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.StringFieldReq;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.PolarFieldPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.PolarFieldVP;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SignOutAlert;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FieldsInputBoxActivity extends AppActivity implements PolarFieldVP.View {
    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_header_sec)
    TextView tvHeaderSec;
    @BindView(R.id.tv_dots)
    ImageView tvDots;
    @BindView(R.id.tv_clear_all)
    TextView tvClearAll;
    @BindView(R.id.ll_menuRight)
    FrameLayout llMenuRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.shadow3)
    View shadow3;
    @BindView(R.id.et_Text)
    EditText etText;
    @BindView(R.id.shadow4)
    View shadow4;
    @BindView(R.id.btn_accept)
    TextView btnAccept;
    @BindView(R.id.ll_frameLayout)
    LinearLayout llFrameLayout;


    private SignOutAlert signOutAlert;
    private String isFrom;
    private ArrayList<String> valueArrayList = new ArrayList<>();
    private PolarFieldPresenter presenterPolar;
    private int field_id;
    private int select_option;
    private int otherPosition = 0;
    private MultiSelectData mData;
    private boolean isMultiSelect = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        ButterKnife.bind(this);
        tvDots.setVisibility(View.GONE);
        mData = EventBus.getDefault().getStickyEvent(MultiSelectData.class);

        if (mData.getFrom().equalsIgnoreCase("multiselect")) {
            otherPosition = mData.getPosition();
            tvHeader.setText(mData.getTitle());
            tvTitle.setText(mData.getPlaceHolder());
            if (!mData.getOptionalText().equalsIgnoreCase("")) {
                etText.setText(mData.getOptionalText());
            }
        } else if (mData.getFrom().equalsIgnoreCase("singleselect")) {
            tvHeader.setText(mData.getTitle());
            tvTitle.setText(mData.getPlaceHolder());
            field_id = mData.getFieldID();
            select_option = mData.getSelectID();
            etText.setText("");
            if (mData.getText_response() != null) {
                etText.setText(mData.getText_response().toString());
            } else {
                etText.setText("");
            }

        } else if (mData.getFrom().equalsIgnoreCase("text")) {
            tvHeader.setText(mData.getTitle());
            tvTitle.setText(mData.getPlaceHolder());
            etText.setText("");
            if (mData.getText_response() != null) {
                etText.setText(mData.getText_response().toString());
            } else {
                etText.setText("");
            }
        }

    }

    @OnClick(R.id.ll_back)
    public void onBackClick() {
        if (mData.getFrom().equalsIgnoreCase("multiselect")) {
            Intent intent = new Intent();
            intent.putExtra("option_id", mData.getOptionID());
            intent.putExtra("text", "");
            //intent.putStringArrayListExtra("result",valueArrayList);
            setResult(RESULT_OK, intent);
            finish();
        } else if (mData.getFrom().equalsIgnoreCase("singleselect")) {
            Intent intent = new Intent();
            intent.putExtra("result", "");
            intent.putExtra("option_id", mData.getOptionID());
            intent.putExtra("text", "");
            addModel(field_id, select_option, "");
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        } else {
            onBackPressed();
        }
    }


    @OnClick(R.id.btn_accept)
    public void onSubmit() {


        if (mData.getFrom().equalsIgnoreCase("multiselect")) {
            Intent intent = new Intent();
            intent.putExtra("option_id", mData.getOptionID());
            intent.putExtra("text", etText.getText().toString());
            //intent.putStringArrayListExtra("result",valueArrayList);
            setResult(RESULT_OK, intent);
            finish();
        }


        if (mData.getFrom().equalsIgnoreCase("singleselect")) {
            Intent intent = new Intent();
            intent.putExtra("result", etText.getText().toString());
            intent.putExtra("option_id", mData.getOptionID());
            intent.putExtra("text", etText.getText().toString());
            addModel(field_id, select_option, etText.getText().toString());
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }


        if (mData.getFrom().equalsIgnoreCase("text")) {
            Intent intent = new Intent();
            intent.putExtra("result", etText.getText().toString());
            addModelString(mData.getFieldID(), etText.getText().toString());

            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK, returnIntent);

            finish();
        }
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public void addModel(int field_id, int option_id, String value) {
        SingleSelectReq req = new SingleSelectReq();
        presenterPolar = new PolarFieldPresenter(this);
        presenterPolar.postString(this, req.add(field_id, option_id, value));
    }

    public void addModelString(int field_id, String value) {
        StringFieldReq req = new StringFieldReq();
        presenterPolar = new PolarFieldPresenter(this);
        presenterPolar.postString(this, req.add(field_id, value));
    }


    @Override
    public void onFieldResponse(Context context, Response<Void> response) {

    }


    @Override
    public void onBackPressed() {
        if (mData.getFrom().equalsIgnoreCase("multiselect")) {
            Intent intent = new Intent();
            intent.putExtra("option_id", mData.getOptionID());
            intent.putExtra("text", "");
            //intent.putStringArrayListExtra("result",valueArrayList);
            setResult(RESULT_OK, intent);
            finish();
        } else if (mData.getFrom().equalsIgnoreCase("singleselect")) {
            Intent intent = new Intent();
            intent.putExtra("result", "");
            intent.putExtra("option_id", mData.getOptionID());
            intent.putExtra("text", "");
            addModel(field_id, select_option, "");
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        } else {
            super.onBackPressed();
        }

    }
}

package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ContactUsReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ContactUsRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.DeactivateAccountReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.SingleSelectReq;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.ContactUsPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.ContactUsVP;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.DeactivateVP;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.DeactivatePresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.PolarFieldPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.PolarFieldVP;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SignOutAlert;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.adapters.PreferenceAdapterRadio;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class OfferActivity extends AppActivity implements DeactivateVP.View, PolarFieldVP.View, ContactUsVP.View{

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_header_sec)
    TextView tvHeaderSec;
    @BindView(R.id.shadow3)
    View shadow3;
    @BindView(R.id.shadow4)
    View shadow4;
    @BindView(R.id.tv_dots)
    ImageView tvDots;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_Text)
    EditText etText;
    private SignOutAlert signOutAlert;

    private String isFrom;
    private DeactivatePresenter presenter;
    private ArrayList<String> valueArrayList;
    private PolarFieldPresenter presenterPolar;
    private int field_id;
    private int select_option;
    private int otherPosition = 0;

    private ContactUsPresenter presenterContact;

    Utils mUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        presenterContact = new ContactUsPresenter(this, this);
        ButterKnife.bind(this);
        iniIntent();
        mUtils=new Utils();
        tvDots.setVisibility(View.INVISIBLE);
        if(isFrom.equalsIgnoreCase("deactivate")){
            tvHeader.setText("Deactivate Account");
            tvTitle.setText("Tell us why?");
            etText.setText("");
        }
        if(isFrom.equalsIgnoreCase("contact")){
            tvHeader.setText("Contact Us");
            tvTitle.setText("Leave a message");
        }

        if(isFrom.equalsIgnoreCase("accr")){
            tvHeader.setText("Objections");
            tvTitle.setText("Input any other PGDS tht you are accredited in");
            etText.setText("Lorem ipsum");
        }
        if(isFrom.equalsIgnoreCase("skills")){
            tvHeader.setText("Objections");
            tvTitle.setText("Input the medicines that you have objections selling");
            etText.setText("Benzethidine, Bufotenin, Carfentanil, Difenoxin, Etorphine");
        }
        if(isFrom.equalsIgnoreCase("other")){
            tvHeader.setText("Other PGDS");
            tvTitle.setText("Input the medicines that you have objections selling");
            etText.setText("Benzethidine, Bufotenin, Carfentanil, Difenoxin, Etorphine");
        }
        if(isFrom.equalsIgnoreCase("multiselect")){
            if(getIntent().getExtras() != null) {
                otherPosition = Integer.parseInt(getIntent().getExtras().get("position").toString());
                tvHeader.setText(getIntent().getExtras().get("title").toString());
                tvTitle.setText(getIntent().getExtras().get("placeholder").toString());
                etText.setText("");
            }
        }
        if(isFrom.equalsIgnoreCase("singleselect")){
            if(getIntent().getExtras() != null) {
                tvHeader.setText(getIntent().getExtras().get("title").toString());
                tvTitle.setText(getIntent().getExtras().get("placeholder").toString());
                field_id = getIntent().getExtras().getInt("field_id");
                select_option = getIntent().getExtras().getInt("select_id");
                etText.setText("");
            }
        }

        if(isFrom.equalsIgnoreCase("multiselectPGDS")){
            if(getIntent().getExtras() != null) {
                tvHeader.setText(getIntent().getExtras().get("title").toString());
                tvTitle.setText(getIntent().getExtras().get("placeholder").toString());
                etText.setText("");
            }
        }

    }
    @OnClick(R.id.ll_back)
        public void onBackClick(){
        onBackPressed();
    }


    @OnClick(R.id.btn_accept)
        public void onSubmit(){
        if(isFrom.equalsIgnoreCase("deactivate")){
            pwdAlert(this);
        }
        if(isFrom.equalsIgnoreCase("multiselect")){
            if(getIntent().getExtras() != null) {
                valueArrayList = getIntent().getExtras().getStringArrayList("valueList");
                if(valueArrayList.size() != 0) {
                    for (int i = 0; i < valueArrayList.size(); i++) {
                        if (i == otherPosition) {
                            valueArrayList.add(otherPosition, etText.getText().toString());
                        } else {
                            valueArrayList.add(null);
                        }
                    }
                }else {
                    valueArrayList.add(otherPosition, etText.getText().toString());
                }
                Intent intent= new Intent();
                intent.putStringArrayListExtra("result",valueArrayList);
                setResult(RESULT_OK,intent);
                finish();
            }
        }
        if(isFrom.equalsIgnoreCase("singleselect")){
            if(getIntent().getExtras() != null) {
                Intent intent= new Intent();
                intent.putExtra("result",etText.getText().toString());
                PreferenceAdapterRadio.value = etText.getText().toString();
                addModel(field_id, select_option, etText.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
            }
        }

        if(isFrom.equalsIgnoreCase("multiselectPGDS")){
            if(getIntent().getExtras() != null) {
                Intent intent= new Intent();
                intent.putExtra("result",etText.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
            }
        }

        if(isFrom.equalsIgnoreCase("contact")){
            etText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            if(!etText.getText().toString().equalsIgnoreCase("")) {
                ContactUsReq request = new ContactUsReq();
                presenterContact.getContactUs(this, request.add(etText.getText().toString()));
            }else{
                showWarning(this,"","Please enter a message", "error");
            }
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void iniIntent(){
        Bundle bundle = getIntent().getExtras();
        isFrom = bundle.getString("isFrom");
    }


    public void addDeactivateRequest(String pwd, String reason){
        presenter = new DeactivatePresenter(this, this);
        DeactivateAccountReq request = new DeactivateAccountReq();
        presenter.deactivateAccount(this, request.add(pwd,reason));
    }


    public void pwdAlert(Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(OfferActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_custom_view,null);
        builder.setCancelable(false);

        builder.setView(dialogView);

        TextView tv_title = dialogView.findViewById(R.id.tv_title);
        EditText et_name = dialogView.findViewById(R.id.et_name);
        TextView dialog_positive_btn = dialogView.findViewById(R.id.dialog_positive_btn);
        TextView dialog_negative_btn = dialogView.findViewById(R.id.dialog_negative_btn);
        tv_title.setText("Deactivate Account");
        et_name.setHint("Enter Password");

        dialog_positive_btn.setText("Deactivate");
        dialog_negative_btn.setText("Cancel");

        et_name.setTransformationMethod(new HiddenPassTransformationMethod());
        et_name.requestFocus();
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        new Handler().postDelayed(new Runnable() {

            public void run() {
                mUtils.showKeyBoard(activity,et_name);
            }
        }, 200);

        //

        dialog_positive_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etText.getText().toString().equalsIgnoreCase("")){
                    showWarning(activity,"", getResources().getString(R.string.reason),"error");
                }
                if(et_name.getText().toString().equalsIgnoreCase("")){
                    showWarning(activity,"", getResources().getString(R.string.password),"error");
                }
                if(!etText.getText().toString().equalsIgnoreCase("")
                        && !et_name.getText().toString().equalsIgnoreCase("")){
                    addDeactivateRequest(et_name.getText().toString(), etText.getText().toString());
                }
                dialog.cancel();
            }
        });

        dialog_negative_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }


    @Override
    public void onDeactivate(Response<Void> response) {
            if(response.code() == 201){
                try{
                    Intent intent = new Intent(this, LandingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                    LandingActivity.openFragmentPosition = 5;
                }catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                handleAPIErrors(this, response);
            }
    }

    public void addModel(int field_id,int option_id, String value) {
        SingleSelectReq req = new SingleSelectReq();
        presenterPolar = new PolarFieldPresenter(this);
        presenterPolar.postMutiSelect(this, req.add(field_id, option_id, value));
    }

    @Override
    public void onFieldResponse(Context context, Response<Void> response) {

    }

    @Override
    public void onContactResponse(Context context, Response<ContactUsRes> response) {
        if(response.code() == 201){
            try {
                showWarning(this,"",response.body().getSuccess().toString(), "");
                onBackPressed();
            }catch (Exception e){
                e.printStackTrace();
            }

        } else {
            handleAPIErrors(this, response);
        }

    }
}

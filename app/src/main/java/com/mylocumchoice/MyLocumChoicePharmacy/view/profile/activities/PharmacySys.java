package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PharmaSysRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PharmacySysUpdateReq;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.PharmacySysVP;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.PharmacySysPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ProcessDialog;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SignOutAlert;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PharmacySys extends AppActivity implements PharmacySysVP.View {

    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_dots)
    ImageView tvDots;
    @BindView(R.id.tv_head)
    TextView tvHead;
    @BindView(R.id.lv_items)
    RecyclerView lvItems;
    @BindView(R.id.btn_done)
    TextView btnDone;
    @BindView(R.id.ll_menuRight)
    FrameLayout llMenuRight;

    private PharmacySysPresenter presenter;


    private ProcessDialog dialog;
    private ArrayList<Integer> arrayList = new ArrayList<>();
    private PharmacySysUpdateReq request;
    private SignOutAlert signOutAlert;


    private String[] pharmacySys={"Pharmacy manager","Nexphase", "Proscript", "Eclipse", "Mediphase", "Positive Solutions","Pharmasist"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_sys);
        ButterKnife.bind(this);
        setHeader();
        presenter = new PharmacySysPresenter(this, this);
        presenter.callAPI(this);
        lvItems.setLayoutManager(new LinearLayoutManager(this));
        lvItems.setNestedScrollingEnabled(false);
    }

    @OnClick(R.id.ll_back)
    public void onBackClick(){ finish(); }



    @OnClick(R.id.btn_done)
    public void onDoneClick(){
        request = new PharmacySysUpdateReq();
        //removing duplicates
        Set<Integer> hs = new HashSet<>();
        hs.addAll(arrayList);
        arrayList.clear();
        arrayList.addAll(hs);
        request.setSelectedSystemIds(arrayList);
        presenter.updateAPI(SharedPrefManager.getInstance(this).getEmail()
                            ,SharedPrefManager.getInstance(this).getAuthToken()
                            ,request);
    }


    public void setHeader(){
        tvHeader.setText(getResources().getString(R.string.text_system));
        tvDots.setVisibility(View.INVISIBLE);
    }


    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
        private Context context;
        String[] list;

        private Handler handler = new Handler();
        private int lastFocussedPosition = -1;
        private Response<PharmaSysRes> response;


        public ListAdapter(Response<PharmaSysRes> response) {
            this.response = response;
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            /** ButterKnife Code **/
            @BindView(R.id.ll_pharmaManager)
            LinearLayout llPharmaManager;
            @BindView(R.id.tv_title)
            TextView tvTitle;
            @BindView(R.id.toggle)
            ToggleButton toggle;
            @BindView(R.id.ll_toggle)
            LinearLayout llToggle;
            /** ButterKnife Code **/

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

        @NonNull
        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // create a new view
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.lv_pharmacy_sys, parent, false);
            final ListAdapter.ViewHolder vh = new ListAdapter.ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
//            if(position == 6){
//                holder.llPharmaManager.setBackground(getResources().getDrawable(R.drawable.drawable_shadow_normal));
//            }
            holder.tvTitle.setText(response.body().getSystems().get(position).getName());
                if(response.body().getSystems().get(position).getSelected()){
                    arrayList.add(response.body().getSystems().get(position).getId());
                    holder.toggle.setChecked(true);
                }

                holder.llPharmaManager.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.toggle.isChecked()) {
                            arrayList.remove(new Integer(response.body().getSystems().get(position).getId()));
                            holder.toggle.setChecked(false);
                        } else {
                            arrayList.add(response.body().getSystems().get(position).getId());
                            holder.toggle.setChecked(true);
                        }
                    }
                });


                holder.toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (holder.toggle.isChecked()) {
                            arrayList.remove(new Integer(response.body().getSystems().get(position).getId()));
                            holder.toggle.setChecked(false);
                        } else {
                            arrayList.add(response.body().getSystems().get(position).getId());
                            holder.toggle.setChecked(true);
                        }
                    }
                });


                holder.toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (holder.toggle.isChecked()) {
                            arrayList.add(response.body().getSystems().get(position).getId());
                            holder.toggle.setChecked(true);
                        } else {
                            arrayList.remove(new Integer(response.body().getSystems().get(position).getId()));
                            holder.toggle.setChecked(false);

                        }
                    }
                });
        }

        @Override
        public int getItemCount() {
            return response.body().getSystems().size();
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public void startActivity(Class<?> cls) {

    }

    @Override
    public void onSuccess(Response<PharmaSysRes> response) {
        try {
            String errorString;
            Gson gson = new GsonBuilder().serializeNulls().create();
            Log.e("PharmacySys", "response: " + gson.toJson(response.body()));
            if (response.code() == 200) {
                try {

                    ListAdapter adapter = new ListAdapter(response);
                    lvItems.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                handleAPIErrors(this, response);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccessUpdate(Response<PharmaSysRes> response) {
        try {
            String errorString;
            Gson gson = new GsonBuilder().serializeNulls().create();
            Log.e("PharmacySys", "response: " + gson.toJson(response.body()));
            if (response.code() == 204) {
                try {
                    showWarning(PharmacySys.this, "", "Details Updated Successfully", "");
                    arrayList.clear();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            /*Intent intent = new Intent(PharmacySys.this, PharmacySys.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);*/
                            finish();
                        }
                    }, 1000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                handleAPIErrors(this, response);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

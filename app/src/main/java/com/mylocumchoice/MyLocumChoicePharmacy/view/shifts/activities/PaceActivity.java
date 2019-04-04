package com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.PaceModel;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.Counties;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShiftResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.Paces;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ListItemClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.PaceItemClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.PaceReturns;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.adapters.PaceAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaceActivity extends AppActivity implements PaceItemClickListener{


    /**
     * ButterKnife Code
     **/
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.ll_menuRight)
    FrameLayout llMenuRight;
    @BindView(R.id.tv_dots)
    ImageView tvDots;
    @BindView(R.id.tv_clear_all)
    TextView tvClearAll;


    /** ButterKnife Code **/
    /** ButterKnife Code **/


    /*@BindView(R.id.ll_quiet)
    LinearLayout llQuiet;
    @BindView(R.id.tg_quiet)
    ImageView tgQuiet;
    @BindView(R.id.ll_moderate)
    LinearLayout llModerate;
    @BindView(R.id.tg_Mod)
    ImageView tgMod;
    @BindView(R.id.ll_busy)
    LinearLayout llBusy;
    @BindView(R.id.tg_busy)
    ImageView tgBusy;*/
    /**
     * ButterKnife Code
     **/
    public PaceReturns returns;
    public PaceModel paceModel;
    @BindView(R.id.shadow)
    View shadow;
    @BindView(R.id.rv_pace)
    RecyclerView rvPace;
    @BindView(R.id.ll_frameLayout)
    LinearLayout llFrameLayout;

    private OnAboutDataReceivedListener mAboutDataListener;
    @BindView(R.id.btn_accept)
    TextView btnAccept;

    private int quietState = 0;
    private int moderateState = 0;
    private int busyStatte = 0;

    List<OpenShiftResponse.Pace> lv_pace=new ArrayList<>();
    List<OpenShiftResponse.Pace> lv_selectedpace=new ArrayList<>();
    List<Integer> lv_paceId=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pace);
        ButterKnife.bind(this);
        if(SortActivity.openShift!=null) {
            try {
                lv_paceId = SortActivity.openShift.getPaceIds();
                lv_pace = EventBus.getDefault().getStickyEvent(OpenShiftResponse.class).getPaces();
                for (int i = 0; i < lv_pace.size(); i++) {
                    if (lv_paceId == null||lv_paceId.size()==0) {
                        lv_pace.get(i).setSelected(false);
                    }
                }
                for (int i = 0; i < lv_pace.size(); i++) {
                    for (int j = 0; j < lv_paceId.size(); j++) {
                        if (lv_paceId.get(j) == lv_pace.get(i).getId()) {
                            lv_pace.get(i).setSelected(true);
                            lv_selectedpace.add(lv_pace.get(i));
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        init();
    }

    private void init() {
        rvPace.setLayoutManager(new LinearLayoutManager(this));
        tvDots.setVisibility(View.INVISIBLE);
        tvHeader.setText("Select Pace");

        rvPace.setAdapter(new PaceAdapter(this,lv_pace,this));
    }



    @OnClick(R.id.ll_back)
    public void onBackClick() {
        onBackPressed();
    }


    @Override
    public void onPaceSelected(int position, boolean isSelected) {
        for(int i=0;i<lv_selectedpace.size();i++){
            if(lv_selectedpace.get(i).getId()==lv_pace.get(position).getId()){
                lv_selectedpace.remove(i);
            }
        }

        if(isSelected)
        lv_selectedpace.add(lv_pace.get(position));
        else
            lv_selectedpace.remove(lv_pace.get(position));


        for(int i=0;i<lv_selectedpace.size();i++) {
            Log.e("lv_selectedpace", lv_selectedpace.get(i).getName());
        }
    }


    public interface OnAboutDataReceivedListener {
        void onDataReceived(PaceModel model);
    }

    public void setAboutDataListener(OnAboutDataReceivedListener listener) {
        this.mAboutDataListener = listener;
    }


    @OnClick(R.id.btn_accept)
    public void onAcceptClick() {
        Paces paces=new Paces();
        paces.setLv_paces(lv_selectedpace);
        EventBus.getDefault().postSticky(paces);
        finish();

    }


}
package com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.Counties;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShift;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.SortActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SortLeftFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SortLeftFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SortLeftFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    boolean flag = false;
    /** ButterKnife Code **/
    @BindView(R.id.shadow)
    View shadow;
    @BindView(R.id.ll_Newest)
    LinearLayout llNewest;
    @BindView(R.id.tg_New)
    ImageView tgNew;
    @BindView(R.id.shadow2)
    View shadow2;
    @BindView(R.id.space2)
    TextView space2;
    @BindView(R.id.shadow3)
    View shadow3;
    @BindView(R.id.llHighest)
    LinearLayout llHighest;
    @BindView(R.id.tg_High)
    ImageView tgHigh;
    @BindView(R.id.shadow4)
    View shadow4;
    @BindView(R.id.tv_Dist)
    TextView tvDist;
    @BindView(R.id.shadow5)
    View shadow5;
    @BindView(R.id.ll_Least)
    LinearLayout llLeast;
    @BindView(R.id.tg_Least)
    ImageView tgLeast;
    @BindView(R.id.shadow6)
    View shadow6;
    @BindView(R.id.space4)
    TextView space4;
    @BindView(R.id.shadow7)
    View shadow7;
    @BindView(R.id.ll_Asc)
    LinearLayout llAsc;
    @BindView(R.id.tg_asc)
    ImageView tgAsc;
    @BindView(R.id.ll_Desc)
    LinearLayout llDesc;
    @BindView(R.id.tg_desc)
    ImageView tgDesc;
    @BindView(R.id.shadow8)
    View shadow8;
    /** ButterKnife Code **/


    public static SortLeftFragment newInstance(String param1, String param2) {
        SortLeftFragment fragment = new SortLeftFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sort_left, container, false);
        ButterKnife.bind(this, view) ;
        //tgNew.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_on));

        setSortData();

        llNewest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tgNew.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_on));
                tgAsc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                tgDesc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                tgHigh.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                tgLeast.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                SortActivity.openShift.setSortBy(getActivity().getResources().getString(R.string.newest));
            }
        });


       llAsc.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               tgNew.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
               tgAsc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_on));
               tgDesc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
               tgHigh.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
               tgLeast.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
               SortActivity.openShift.setSortBy(getActivity().getResources().getString(R.string.date_asc));
           }
       });

       llDesc.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               tgNew.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
               tgAsc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
               tgDesc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_on));
               tgHigh.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
               tgLeast.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
               SortActivity.openShift.setSortBy(getActivity().getResources().getString(R.string.date_desc));
           }
       });

       llHighest.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               tgNew.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
               tgAsc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
               tgDesc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
               tgHigh.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_on));
               tgLeast.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
               SortActivity.openShift.setSortBy(getActivity().getResources().getString(R.string.hourly_rate));
           }
       });

       llLeast.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               tgNew.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
               tgAsc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
               tgDesc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
               tgHigh.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
               tgLeast.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_on));
               SortActivity.openShift.setSortBy(getActivity().getResources().getString(R.string.distance));
           }
       });


      /* SortActivity.tv_clear_all.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               SharedPrefManager.getInstance(getActivity()).clearShiftPref();
               SortActivity.openShift = new OpenShift();
               ArrayList<Integer> arrayList=new ArrayList<>();
               SortActivity.openShift.setCountyIds(arrayList);
               try {
                   EventBus.getDefault().removeStickyEvent(Counties.class);
               }catch (Exception e){
                   e.printStackTrace();
               }
               tgNew.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
               tgAsc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
               tgDesc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
               tgHigh.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
               tgLeast.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
           }
       });*/
        return view;
    }

    private void setSortData() {
        try {
            if (SortActivity.openShift.getSortBy().equalsIgnoreCase(getActivity().getResources().getString(R.string.newest))) {
                tgNew.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_on));
                tgAsc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                tgDesc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                tgHigh.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                tgLeast.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
            } else if (SortActivity.openShift.getSortBy().equalsIgnoreCase(getActivity().getResources().getString(R.string.date_asc))) {
                tgNew.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                tgAsc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_on));
                tgDesc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                tgHigh.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                tgLeast.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
            } else if (SortActivity.openShift.getSortBy().equalsIgnoreCase(getActivity().getResources().getString(R.string.date_desc))) {
                tgNew.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                tgAsc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                tgDesc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_on));
                tgHigh.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                tgLeast.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
            } else if (SortActivity.openShift.getSortBy().equalsIgnoreCase(getActivity().getResources().getString(R.string.hourly_rate))) {
                tgNew.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                tgAsc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                tgDesc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                tgHigh.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_on));
                tgLeast.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
            } else if (SortActivity.openShift.getSortBy().equalsIgnoreCase(getActivity().getResources().getString(R.string.distance))) {
                tgNew.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                tgAsc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                tgDesc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                tgHigh.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                tgLeast.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_on));
            } else {
                tgNew.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_on));
                tgAsc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                tgDesc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                tgHigh.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                tgLeast.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                SortActivity.openShift.setSortBy(getActivity().getResources().getString(R.string.newest));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int position);
    }

}

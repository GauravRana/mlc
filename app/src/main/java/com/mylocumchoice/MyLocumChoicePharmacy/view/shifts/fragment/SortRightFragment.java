package com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.PaceModel;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.Counties;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShift;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShiftResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.Paces;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ListItemClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.LockableScrollView;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.PaceActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.SelectCountiesActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.SortActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.adapters.DistanceAdapter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.signup.activities.RegisterActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SortRightFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SortRightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SortRightFragment extends BaseFragment implements PaceActivity.OnAboutDataReceivedListener, ListItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.shadow)
    View shadow;
    @BindView(R.id.ll_Emergency)
    LinearLayout llEmergency;
    @BindView(R.id.shadow2)
    View shadow2;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.shadow3)
    View shadow3;
    @BindView(R.id.ll_Countries)
    LinearLayout llCountries;
    @BindView(R.id.tv_Counties)
    TextView tvCounties;
    @BindView(R.id.tv_Counties_sub)
    TextView tvCountiesSub;
    @BindView(R.id.ll_dist)
    LinearLayout llDist;
    @BindView(R.id.tv_Dist)
    TextView tvDist;
    @BindView(R.id.tv_DistSub)
    TextView tvDistSub;
    @BindView(R.id.shadow4)
    View shadow4;
    @BindView(R.id.tv_dateRange)
    TextView tvDateRange;
    @BindView(R.id.shadow5)
    View shadow5;
    @BindView(R.id.ll_StartDate)
    LinearLayout llStartDate;
    @BindView(R.id.ll_EndDate)
    LinearLayout llEndDate;
    @BindView(R.id.shadow6)
    View shadow6;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.shadow7)
    View shadow7;
    @BindView(R.id.ll_Hour)
    LinearLayout llHour;
    @BindView(R.id.ll_Pace)
    LinearLayout llPace;
    @BindView(R.id.iv_emergency)
    ImageView iv_emergency;
    private static TextView tvPace;
    @BindView(R.id.shadow8)
    View shadow8;

    @BindView(R.id.tv_PaceDetails)
    TextView tv_PaceDetails;

    @BindView(R.id.scroll_View)
    LockableScrollView scrollView;

    @BindView(R.id.tv_RateHour)
    EditText tvRateHour;

    Utils mUtils;
    Dialog dialog;


    OpenShiftResponse openShiftResponse;
    public int COUNTY_REQUEST_CODE = 101;
    public int COUNTIES_RESULT_CODE = 103;
    public int PACE_REQUEST_CODE = 102;
   boolean isActivityResult=false;


    public static TextView tvStartDate;
    public static TextView tvEndDate;

    private AlertDialog.Builder builderSingle;
    private AlertDialog.Builder builderDouble;
    private AlertDialog d;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Activity activity;

    boolean isEmergency = false;
    List<OpenShiftResponse.Pace> lv_paces;


    public SortRightFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SortRightFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SortRightFragment newInstance(String param1, String param2) {
        SortRightFragment fragment = new SortRightFragment();
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
//        activity = (PaceActivity) getActivity();
//        activity.setAboutDataListener(this);
    }

    @Override
    public void onDataReceived(PaceModel model) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sort_right, container, false);

        mUtils = new Utils();
        tvStartDate = view.findViewById(R.id.tv_startDate);
        tvEndDate = view.findViewById(R.id.tv_endDate);
        tvPace = view.findViewById(R.id.tv_Pace);
        ButterKnife.bind(this, view);
        showLayout();
        /*try {
            openShiftResponse = EventBus.getDefault().getStickyEvent(OpenShiftResponse.class);
            setShiftData();
        } catch (Exception e) {
            e.printStackTrace();
        }*/


//        String str = "\u00A3 " + "";
//        tvRateHour.setText(str);


        tvRateHour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (tvRateHour.length() == 0) {
//                    tvRateHour.setText("£");
//                    tvRateHour.setSelection(1);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    String str_rate = tvRateHour.getText().toString().replace("£", "");
                    double rate = Double.parseDouble(str_rate);
                    SortActivity.openShift.setRate(rate);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        /*SortActivity.tv_clear_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPrefManager.getInstance(getActivity()).clearShiftPref();
                SortActivity.openShift = null;
                SortActivity.isCleared = true;
                setShiftData();
            }
        });
*/
        return view;
    }

    public void setShiftData() {
        try {
            if (SortActivity.openShift != null) {
                List<OpenShiftResponse.County> lv_county = new ArrayList<>();
                for (int i = 0; i < openShiftResponse.getCounties().size(); i++) {
                    if (openShiftResponse.getCounties().get(i).isSelected()) {
                        lv_county.add(openShiftResponse.getCounties().get(i));
                    }
                }

                ArrayList<Integer> al_counties = new ArrayList<>();
                try {
                    al_counties = SortActivity.openShift.getCountyIds();
                    for (int i = 0; i < openShiftResponse.getCounties().size(); i++) {
                        boolean ismatched = false;
                        for (int j = 0; j < al_counties.size(); j++) {
                            if(openShiftResponse.getCounties().get(i).isSelected()) {
                                if (openShiftResponse.getCounties().get(i).getId() == al_counties.get(j)) {
                                    ismatched = true;
                                }
                            }

                            /*if (al_counties.get(j) == openShiftResponse.getCounties().get(i).getId()) {
                                lv_county.add(openShiftResponse.getCounties().get(i));
                            }*/
                        }
                        if(openShiftResponse.getCounties().get(i).isSelected()) {
                            if (!ismatched) {
                                lv_county.add(openShiftResponse.getCounties().get(i));
                            }
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {

                    for (int i = 0; i < lv_county.size(); i++) {
                        if(al_counties==null||al_counties.size()==0){
                            al_counties=new ArrayList<>();
                            al_counties.add(lv_county.get(i).getId());
                        }else {
                            boolean isMatched = false;
                            for (int j = 0; j < al_counties.size(); j++) {
                                if (al_counties.get(j) == lv_county.get(i).getId()) {
                                    isMatched = true;
                                }
                            }
                            if (!isMatched)
                                al_counties.add(lv_county.get(i).getId());
                        }
                    }


                    SortActivity.openShift.setCountyIds(al_counties);
                }catch (Exception e){
                    e.printStackTrace();
                }


                if (lv_county.size() == 1) {
                    tvCountiesSub.setVisibility(View.VISIBLE);
                    tvCountiesSub.setText(lv_county.get(0).getName());
                } else if (lv_county.size() > 1) {
                    for (int i = 0; i < lv_county.size(); i++) {
                        String str_counties = "";
                        if (tvCountiesSub.getText().toString().equals("")) {
                            str_counties = lv_county.get(i).getName();
                        } else {
                            str_counties = tvCountiesSub.getText().toString() + ", " + lv_county.get(i).getName();
                        }
                        if (str_counties.lastIndexOf(", ") == str_counties.length()) {
                            str_counties = str_counties.replace(", ", "");
                            str_counties = str_counties.substring(0, str_counties.length() - 1);
                        }
                        tvCountiesSub.setVisibility(View.VISIBLE);
                        tvCountiesSub.setText(str_counties);
                    }
                }


                if(SortActivity.openShift.getEmergency()!=null) {
                    if (SortActivity.openShift.getEmergency() == true) {
                        iv_emergency.setImageResource(R.drawable.ic_check_circle_green);
                        isEmergency = true;
                    } else {
                        iv_emergency.setImageResource(R.drawable.ic_uncheck_circle_green);
                        isEmergency = false;
                    }
                }else{
                    iv_emergency.setImageResource(R.drawable.ic_uncheck_circle_green);
                    isEmergency = false;
                }


                if(SortActivity.openShift.getDistanceId()!=0.0) {
                    int dist_id = (int) SortActivity.openShift.getDistanceId();
                    for (int i = 0; i < openShiftResponse.getDistances().size(); i++) {
                        if (openShiftResponse.getDistances().get(i).getId() == dist_id) {
                            tvDistSub.setVisibility(View.VISIBLE);
                            tvDistSub.setText(openShiftResponse.getDistances().get(i).getName());
                        }
                    }
                }

                if(SortActivity.openShift.getStartDate()!=null || !SortActivity.openShift.getStartDate().equals("")) {
                    tvStartDate.setText(SortActivity.openShift.getStartDate());
                }

                if(SortActivity.openShift.getEndDate()!=null || SortActivity.openShift.getEndDate().equals("")) {
                    tvEndDate.setText(SortActivity.openShift.getEndDate());
                }

                if(SortActivity.openShift.getRate()!=0.0)
                    tvRateHour.setText(SortActivity.openShift.getRate() + "");


                if(SortActivity.openShift.getPaceIds()!=null) {
                    lv_paces = new ArrayList<>();
                    ArrayList<Integer> al_paces = new ArrayList<>();
                    al_paces = SortActivity.openShift.getPaceIds();

                    try {
                        for (int i = 0; i < openShiftResponse.getPaces().size(); i++) {
                            for (int j = 0; j < al_paces.size(); j++) {
                                if (al_paces.get(j) == openShiftResponse.getPaces().get(i).getId()) {
                                    lv_paces.add(openShiftResponse.getPaces().get(i));
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    if (lv_paces.size() == 1) {
                        tv_PaceDetails.setVisibility(View.VISIBLE);
                        tv_PaceDetails.setText(lv_paces.get(0).getName());
                    } else if (lv_paces.size() > 1) {
                        tv_PaceDetails.setVisibility(View.VISIBLE);
                        String pacedata = "";
                        for (int i = 0; i < lv_paces.size(); i++) {
                            pacedata = pacedata + lv_paces.get(i).getName();
                            if (i == lv_paces.size() - 1) {

                            } else {
                                pacedata = pacedata + ", ";
                            }
                        }
                        tv_PaceDetails.setText(pacedata);
                    }
                }


            } else {
                iv_emergency.setImageResource(R.drawable.ic_uncheck_circle_green);
                isEmergency = false;
                tvDistSub.setVisibility(View.GONE);
                tvDistSub.setText("");
                tvStartDate.setText("");
                tvEndDate.setText("");
                tvRateHour.setText("");
                tv_PaceDetails.setText("");
                lv_paces.clear();
                tv_PaceDetails.setVisibility(View.GONE);
                tvCountiesSub.setText("");
                tvCountiesSub.setVisibility(View.GONE);
                SortActivity.openShift = new OpenShift();
                ArrayList<Integer> arrayList = new ArrayList<>();
                SortActivity.openShift.setCountyIds(arrayList);
                try {
                    EventBus.getDefault().removeStickyEvent(Counties.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.ll_Countries)
    public void onCountiesClick() {
        Intent mIntent = new Intent(getActivity(), SelectCountiesActivity.class);
        startActivityForResult(mIntent, COUNTY_REQUEST_CODE);
    }

    @OnClick(R.id.ll_Pace)
    public void onPaceClick() {
        Intent i = new Intent(getActivity(), PaceActivity.class);
        startActivityForResult(i, PACE_REQUEST_CODE);
    }

    @OnClick(R.id.ll_StartDate)
    public void onStartDate() {
        //tvStartDate.setText("");
        //Utils.DatePickerFragment.newInstance("Start",getActivity()).show(getChildFragmentManager(),"datePicker");
        tvEndDate.setText("");
        SortActivity.openShift.setEndDate("");
        mUtils.fn_showDatePicker(tvStartDate.getText().toString(), "Start", getActivity());
        // setDate();
    }

    @OnClick(R.id.ll_Emergency)
    public void onCEmerClick() {
        try {
            if (isEmergency) {
                isEmergency = false;
                SortActivity.openShift.setEmergency(false);
                iv_emergency.setImageResource(R.drawable.ic_uncheck_circle_green);
            } else {
                isEmergency = true;
                SortActivity.openShift.setEmergency(true);
                iv_emergency.setImageResource(R.drawable.ic_check_circle_green);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.ll_EndDate)
    public void onEndDate() {
        //tvEndDate.setText("");
        // Utils.DatePickerFragment.newInstance("End",getActivity()).show(getChildFragmentManager(),"datePicker");
        if(tvEndDate.getText().toString().equals("")){
            mUtils.fn_showDatePicker(tvStartDate.getText().toString(), "End", getActivity());
        }else{
            mUtils.fn_showDatePicker(tvEndDate.getText().toString(), "End", getActivity());
        }
    }

    @OnClick(R.id.ll_Hour)
    public void onHourClick() {
        tvRateHour.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(tvRateHour, InputMethodManager.SHOW_IMPLICIT);
    }

    @OnClick(R.id.ll_dist)
    public void distance() {
        ArrayList<String> distance = new ArrayList<>();
        distance.add("Upto 5 miles");
        distance.add("Upto 10 miles");
        showDialog(openShiftResponse.getDistances());
        //builderSingle.show();
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

    @Override
    public void startActivity(Class<?> cls) {
        super.startActivity(cls);
    }

    @Override
    public void onClick(int position) {
        try {
            SortActivity.openShift.setDistanceId(openShiftResponse.getDistances().get(position).getId());
            dialog.cancel();
            tvDistSub.setVisibility(View.VISIBLE);
            tvDistSub.setText(openShiftResponse.getDistances().get(position).getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if(!isActivityResult) {
            try {
                tvCountiesSub.setText("");
                openShiftResponse = EventBus.getDefault().getStickyEvent(OpenShiftResponse.class);
                setShiftData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int position);
    }


    public void showLayout() {
        tvDistSub.setVisibility(View.GONE);
        tvCountiesSub.setVisibility(View.GONE);
    }


    private void showDialog(List<OpenShiftResponse.Distance> lv_distance) {
        String[] lv_arr = {};
        dialog = new Dialog(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.dialog_distance, null);

        RecyclerView rv_distance = (RecyclerView) convertView.findViewById(R.id.rv_distance);
        rv_distance.setLayoutManager(new LinearLayoutManager(getActivity()));
        TextView tv = (TextView) convertView.findViewById(R.id.tv_title);
        TextView tv_cancel = convertView.findViewById(R.id.tv_cancel);
        TextView tv_clear = convertView.findViewById(R.id.tv_clear);

        tv.setText("Select Distance");

        rv_distance.setAdapter(new DistanceAdapter(getActivity(), lv_distance, this));

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDistSub.setText("");
                tvDistSub.setVisibility(View.GONE);
                try {
                    SortActivity.openShift.setDistanceId(0);
                }catch (Exception e){
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(convertView);
        dialog.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == COUNTY_REQUEST_CODE && resultCode==COUNTIES_RESULT_CODE) {
            try {
                isActivityResult=true;
                if(EventBus.getDefault().getStickyEvent(Counties.class)!=null) {
                    tvCountiesSub.setText("");
                    Counties counties = EventBus.getDefault().getStickyEvent(Counties.class);
                    List<OpenShiftResponse.County> lv_counties = counties.getLv_county();
                    ArrayList<Integer> county_ids = new ArrayList<>();
                    for (int i = 0; i < lv_counties.size(); i++) {
                        county_ids.add(lv_counties.get(i).getId());
                        String str_counties = "";
                        if (tvCountiesSub.getText().toString().equals("")) {
                            str_counties = lv_counties.get(i).getName();
                        } else {
                            str_counties = tvCountiesSub.getText().toString() + ", " + lv_counties.get(i).getName();
                        }
                        if (str_counties.lastIndexOf(", ") == str_counties.length()) {
                            str_counties = str_counties.replace(", ", "");
                            str_counties = str_counties.substring(0, str_counties.length() - 1);
                        }
                        tvCountiesSub.setVisibility(View.VISIBLE);
                        tvCountiesSub.setText(str_counties);
                        SortActivity.openShift.setCountyIds(county_ids);
                    }


                    if (lv_counties.size() == 0 || lv_counties == null) {
                        tvCountiesSub.setVisibility(View.GONE);
                        tvCountiesSub.setText("");
                    }

                    try {
                        EventBus.getDefault().removeStickyEvent(Counties.class);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    tvCountiesSub.setText("");
                    setShiftData();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == PACE_REQUEST_CODE) {
            try {
                Paces paces = EventBus.getDefault().getStickyEvent(Paces.class);
                List<OpenShiftResponse.Pace> lv_paces = paces.getLv_paces();
                ArrayList<Integer> al_paces = new ArrayList<>();
                for (int i = 0; i < lv_paces.size(); i++) {
                    al_paces.add(lv_paces.get(i).getId());
                }
                SortActivity.openShift.setPaceIds(al_paces);
                if (lv_paces.size() == 1) {
                    tv_PaceDetails.setVisibility(View.VISIBLE);
                    tv_PaceDetails.setText(lv_paces.get(0).getName());
                }
                else if (lv_paces.size() > 1) {
                    tv_PaceDetails.setVisibility(View.VISIBLE);
                    String pacedata = "";
                    for (int i = 0; i < lv_paces.size(); i++) {
                        pacedata = pacedata + lv_paces.get(i).getName();
                        if (i == lv_paces.size() - 1) {

                        } else {
                            pacedata = pacedata + ", ";
                        }
                    }
                    tv_PaceDetails.setText(pacedata);
                }else {
                    tv_PaceDetails.setVisibility(View.GONE);
                    tv_PaceDetails.setText("");
                }
                EventBus.getDefault().removeStickyEvent(Paces.class);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }//onActivityResult

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
            fragTransaction.detach(this);
            fragTransaction.attach(this);
            fragTransaction.commit();
        }
    }
}

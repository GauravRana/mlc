package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.Clients;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.CompletedBooking;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.CompletedBookingResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities.BookingHistorySortActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities.FilterPharmaActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistoryFilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFilterFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.tv_Dist)
    TextView tvDist;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.shadow20)
    View shadow20;
    @BindView(R.id.tv_Car)
    TextView tvCar;
    @BindView(R.id.shadow10)
    View shadow10;
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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    @BindView(R.id.ll_paid)
    LinearLayout ll_paid;

    @BindView(R.id.ll_unpaid)
    LinearLayout ll_unpaid;

    @BindView(R.id.ll_both)
    LinearLayout ll_both;

    @BindView(R.id.llPharma)
    LinearLayout llPharma;

    @BindView(R.id.ll_dist)
    LinearLayout ll_dist;

    public static TextView tvStartDate;
    public static TextView tvEndDate;
    public static TextView tv_selectedPharmacy;
    public static ImageView rb_paid;
    public static ImageView rb_unpaid;
    public static ImageView rb_both;

    private Utils mUtils;
    private int REQUEST_CLIENT = 5;
    int RESULT_CODE_FILTERS = 10;

    private OnFragmentInteractionListener mListener;

    CompletedBooking completedBookingFilters = null;

    CompletedBookingResponse completedBookingResponse;

    boolean isActivityResult = false;

    public HistoryFilterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFilterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFilterFragment newInstance(String param1, String param2) {
        HistoryFilterFragment fragment = new HistoryFilterFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_filter, container, false);
        ButterKnife.bind(this, view);
        mUtils = new Utils();
        tvStartDate = view.findViewById(R.id.tv_startDate);
        tvEndDate = view.findViewById(R.id.tv_endDate);
        rb_paid = view.findViewById(R.id.rb_paid);
        rb_unpaid = view.findViewById(R.id.rb_unpaid);
        rb_both = view.findViewById(R.id.rb_both);
        tv_selectedPharmacy = view.findViewById(R.id.tv_selectedPharmacy);

        if (EventBus.getDefault().getStickyEvent(CompletedBooking.class) != null) {
            completedBookingFilters = EventBus.getDefault().getStickyEvent(CompletedBooking.class);
        }

        if (completedBookingFilters != null) {
            try {
                if (completedBookingFilters.getPaid() == null) {
                    rb_paid.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rb_off));
                    rb_unpaid.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rb_off));
                    rb_both.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rb_on));
                } else if (completedBookingFilters.getPaid()) {
                    rb_paid.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rb_on));
                    rb_unpaid.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rb_off));
                    rb_both.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rb_off));
                } else if (!completedBookingFilters.getPaid()) {
                    rb_paid.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rb_off));
                    rb_unpaid.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rb_on));
                    rb_both.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rb_off));
                } else {
                    rb_paid.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rb_off));
                    rb_unpaid.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rb_off));
                    rb_both.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rb_on));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (completedBookingFilters.getStartDate() != null) {
                tvStartDate.setText(completedBookingFilters.getStartDate());
            } else {
                tvStartDate.setText("");
            }

            if (completedBookingFilters.getEndDate() != null) {
                tvEndDate.setText(completedBookingFilters.getEndDate());
            } else {
                tvEndDate.setText("");
            }
        } else {
            rb_paid.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rb_off));
            rb_unpaid.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rb_off));
            rb_both.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rb_on));
        }

        if (EventBus.getDefault().getStickyEvent(CompletedBooking.class) != null) {
            BookingHistorySortActivity.tvClearAll.setEnabled(true);
            BookingHistorySortActivity.tvClearAll.setAlpha(1f);
        } else {
            BookingHistorySortActivity.tvClearAll.setEnabled(false);
            BookingHistorySortActivity.tvClearAll.setAlpha(0.8f);
        }


        /*BookingHistorySortActivity.tvClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BookingHistorySortActivity) getActivity()).isPaid = null;
                rb_paid.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rb_off));
                rb_unpaid.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rb_off));
                rb_both.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rb_on));
                tvStartDate.setText("");
                tvEndDate.setText("");
                try {
                    EventBus.getDefault().removeStickyEvent(Clients.class);
                }catch (Exception e){
                    e.printStackTrace();
                }
                tv_selectedPharmacy.setText("");
                tv_selectedPharmacy.setVisibility(View.GONE);

                try {
                    EventBus.getDefault().removeStickyEvent(CompletedBooking.class);
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });
        */return view;
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

    @OnClick(R.id.ll_paid)
    public void onPaid() {
        ((BookingHistorySortActivity) getActivity()).isPaid = "true";
        //((BookingHistorySortActivity)getActivity()).completedBooking.setPaid(true);
        rb_paid.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rb_on));
        rb_unpaid.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rb_off));
        rb_both.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rb_off));
        BookingHistorySortActivity.tvClearAll.setEnabled(true);
        BookingHistorySortActivity.tvClearAll.setAlpha(1f);
    }


    @OnClick(R.id.ll_StartDate)
    public void onStartDate() {
        //tvStartDate.setText("");
        tvEndDate.setText("");
        mUtils.fn_showDatePicker(tvStartDate.getText().toString(), "Start", getActivity());
        BookingHistorySortActivity.tvClearAll.setEnabled(true);
        BookingHistorySortActivity.tvClearAll.setAlpha(1f);
    }


    @OnClick(R.id.ll_EndDate)
    public void onEndDate() {
        //tvEndDate.setText("");
        if(tvEndDate.getText().toString().equals("")){
            mUtils.fn_showDatePicker(tvStartDate.getText().toString(), "End", getActivity());
            BookingHistorySortActivity.tvClearAll.setEnabled(true);
            BookingHistorySortActivity.tvClearAll.setAlpha(1f);
        }else{
            mUtils.fn_showDatePicker(tvEndDate.getText().toString(), "End", getActivity());
            BookingHistorySortActivity.tvClearAll.setEnabled(true);
            BookingHistorySortActivity.tvClearAll.setAlpha(1f);
        }
    }


    @OnClick(R.id.ll_unpaid)
    public void onUnpaid() {
        ((BookingHistorySortActivity) getActivity()).isPaid = "false";
        //((BookingHistorySortActivity)getActivity()).completedBooking.setPaid(false);
        rb_paid.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rb_off));
        rb_unpaid.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rb_on));
        rb_both.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rb_off));
        BookingHistorySortActivity.tvClearAll.setEnabled(true);
        BookingHistorySortActivity.tvClearAll.setAlpha(1f);
    }

    @OnClick(R.id.ll_both)
    public void onOther() {
        ((BookingHistorySortActivity) getActivity()).isPaid = "";
        // ((BookingHistorySortActivity)getActivity()).completedBooking.setPaid(null);
        rb_paid.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rb_off));
        rb_unpaid.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rb_off));
        rb_both.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rb_on));
        BookingHistorySortActivity.tvClearAll.setEnabled(true);
        BookingHistorySortActivity.tvClearAll.setAlpha(1f);
    }

    @OnClick(R.id.ll_dist)
    public void onPharmacy() {
        Intent mIntent = new Intent(getActivity(), FilterPharmaActivity.class);
        startActivityForResult(mIntent, REQUEST_CLIENT);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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


    @Override
    public void onResume() {
        super.onResume();
        if (!isActivityResult) {
            if (EventBus.getDefault().getStickyEvent(CompletedBooking.class) != null) {
                if (!tv_selectedPharmacy.getText().toString().equals("")) {
                    tv_selectedPharmacy.setText("");
                }
                completedBookingResponse = EventBus.getDefault().getStickyEvent(CompletedBookingResponse.class);
                completedBookingFilters = EventBus.getDefault().getStickyEvent(CompletedBooking.class);

                List<Integer> lv_countyId = new ArrayList<>();
                List<CompletedBookingResponse.Client> lv_clients = new ArrayList<>();

                List<CompletedBookingResponse.Client> items = new ArrayList<>();


                lv_clients = EventBus.getDefault().getStickyEvent(CompletedBookingResponse.class).getClients();
                try {
                    lv_countyId = completedBookingFilters.getClientIds();
                    for (int i = 0; i < lv_clients.size(); i++) {
                        for (int j = 0; j < lv_countyId.size(); j++) {
                            if (lv_countyId.get(j) == lv_clients.get(i).getId()) {
                                String str_clients = "";
                                if (tv_selectedPharmacy.getText().toString().equals("")) {
                                    str_clients = lv_clients.get(i).getName();
                                } else {
                                    str_clients = tv_selectedPharmacy.getText().toString() + ", " + lv_clients.get(i).getName();
                                }
                                if (str_clients.lastIndexOf(", ") == str_clients.length()) {
                                    str_clients = str_clients.replace(", ", "");
                                    str_clients = str_clients.substring(0, str_clients.length() - 1);
                                }
                                tv_selectedPharmacy.setVisibility(View.VISIBLE);
                                tv_selectedPharmacy.setText(str_clients);

                                items.add(lv_clients.get(i));

                            }
                        }
                    }

                    Clients clients = new Clients();
                    clients.setClients(items);
                    EventBus.getDefault().postSticky(clients);
                } catch (Exception e) {
                    e.printStackTrace();
                }




            }
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CLIENT && resultCode == RESULT_CODE_FILTERS) {
            isActivityResult = true;
            if (EventBus.getDefault().getStickyEvent(Clients.class) != null) {
                if (EventBus.getDefault().getStickyEvent(Clients.class).getClients().size() > 0) {
                    if (!tv_selectedPharmacy.getText().toString().equals("")) {
                        tv_selectedPharmacy.setText("");
                    }
                    List<CompletedBookingResponse.Client> lv_client = new ArrayList<>();
                    lv_client = EventBus.getDefault().getStickyEvent(Clients.class).getClients();
                    if (lv_client != null) {
                        for (int i = 0; i < lv_client.size(); i++) {

                            String str_clients = "";
                            if (tv_selectedPharmacy.getText().toString().equals("")) {
                                str_clients = lv_client.get(i).getName();
                            } else {
                                str_clients = tv_selectedPharmacy.getText().toString() + "," + lv_client.get(i).getName();
                            }
                            if (str_clients.lastIndexOf(",") == str_clients.length()) {
                                str_clients = str_clients.replace(",", "");
                                str_clients = str_clients.substring(0, str_clients.length() - 1);
                            }
                            tv_selectedPharmacy.setVisibility(View.VISIBLE);
                            tv_selectedPharmacy.setText(str_clients);
                        }
                    }
                }

                BookingHistorySortActivity.tvClearAll.setEnabled(true);
                BookingHistorySortActivity.tvClearAll.setAlpha(1f);


            } /*else if (EventBus.getDefault().getStickyEvent(CompletedBooking.class) != null) {
                if (!tv_selectedPharmacy.getText().toString().equals("")) {
                    tv_selectedPharmacy.setText("");
                }
                completedBookingResponse = EventBus.getDefault().getStickyEvent(CompletedBookingResponse.class);
                completedBookingFilters = EventBus.getDefault().getStickyEvent(CompletedBooking.class);

                List<Integer> lv_countyId = new ArrayList<>();
                List<CompletedBookingResponse.Client> lv_clients = new ArrayList<>();

                lv_clients = EventBus.getDefault().getStickyEvent(CompletedBookingResponse.class).getClients();
                try {
                    lv_countyId = completedBookingFilters.getClientIds();
                    for (int i = 0; i < lv_clients.size(); i++) {
                        for (int j = 0; j < lv_countyId.size(); j++) {
                            if (lv_countyId.get(j) == lv_clients.get(i).getId()) {
                                String str_clients = "";
                                if (tv_selectedPharmacy.getText().toString().equals("")) {
                                    str_clients = lv_clients.get(i).getName();
                                } else {
                                    str_clients = tv_selectedPharmacy.getText().toString() + "," + lv_clients.get(i).getName();
                                }
                                if (str_clients.lastIndexOf(",") == str_clients.length()) {
                                    str_clients = str_clients.replace(",", "");
                                    str_clients = str_clients.substring(0, str_clients.length() - 1);
                                }
                                tv_selectedPharmacy.setVisibility(View.VISIBLE);
                                tv_selectedPharmacy.setText(str_clients);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }*/ else {
                tv_selectedPharmacy.setVisibility(View.GONE);
            }
        }
    }
}

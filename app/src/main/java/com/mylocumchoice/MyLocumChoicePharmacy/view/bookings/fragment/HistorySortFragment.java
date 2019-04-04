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
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities.BookingHistorySortActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistorySortFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistorySortFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistorySortFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /** ButterKnife Code **/
    @BindView(R.id.tv_dateRange)
    TextView tvDateRange;
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

    CompletedBooking completedBookingFilters=null;

    private OnFragmentInteractionListener mListener;
    int RESULT_CODE_FILTERS = 4;

    public static Boolean isAsc=false;

    public HistorySortFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistorySortFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistorySortFragment newInstance(String param1, String param2) {
        HistorySortFragment fragment = new HistorySortFragment();
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
        View view  = inflater.inflate(R.layout.fragment_history_sort, container, false);
        ButterKnife.bind(this, view);
        if(EventBus.getDefault().getStickyEvent(CompletedBooking.class)!=null){
            completedBookingFilters=EventBus.getDefault().getStickyEvent(CompletedBooking.class);
        }

        try {
            if(completedBookingFilters==null){
                tgAsc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                tgDesc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_on));
            }else {
                if (completedBookingFilters.getSortBy() == null || completedBookingFilters.getSortBy().equals("")) {
                    tgAsc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                    tgDesc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_on));
                } else if (completedBookingFilters.getSortBy().equalsIgnoreCase("date_asc")) {
                    tgAsc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_on));
                    tgDesc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                } else if (completedBookingFilters.getSortBy().equalsIgnoreCase("date_desc")) {
                    tgAsc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                    tgDesc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_on));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        llAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tgAsc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_on));
                tgDesc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                ((BookingHistorySortActivity)getActivity()).sort="date_asc";
                isAsc=true;
                BookingHistorySortActivity.tvClearAll.setEnabled(true);
                BookingHistorySortActivity.tvClearAll.setAlpha(1f);
            }
        });


        llDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tgAsc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                tgDesc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_on));
                ((BookingHistorySortActivity)getActivity()).sort="date_desc";
                isAsc=true;
                BookingHistorySortActivity.tvClearAll.setEnabled(true);
                BookingHistorySortActivity.tvClearAll.setAlpha(1f);
            }
        });


        BookingHistorySortActivity.tvClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((BookingHistorySortActivity) getActivity()).isPaid = null;
                HistoryFilterFragment.rb_paid.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rb_off));
                HistoryFilterFragment.rb_unpaid.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rb_off));
                HistoryFilterFragment.rb_both.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rb_on));
                HistoryFilterFragment.tvStartDate.setText("");
                HistoryFilterFragment.tvEndDate.setText("");
                try {
                    EventBus.getDefault().removeStickyEvent(Clients.class);
                }catch (Exception e){
                    e.printStackTrace();
                }
                HistoryFilterFragment.tv_selectedPharmacy.setText("");
                HistoryFilterFragment.tv_selectedPharmacy.setVisibility(View.GONE);

                try {
                    EventBus.getDefault().removeStickyEvent(Clients.class);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    tgAsc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                    tgDesc.setImageDrawable(getResources().getDrawable(R.drawable.ic_cb_off));
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    EventBus.getDefault().removeStickyEvent(CompletedBooking.class);
                }catch (Exception e){
                    e.printStackTrace();
                }


                Intent mIntent=new Intent();
                getActivity().setResult(RESULT_CODE_FILTERS,mIntent);
                getActivity().finish();


            }
        });
        return view;
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
}

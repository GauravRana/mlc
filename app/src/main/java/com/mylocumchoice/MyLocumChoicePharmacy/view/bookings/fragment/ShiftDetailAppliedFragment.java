package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.h6ah4i.android.materialshadowninepatch.MaterialShadowContainerView;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.AppliedShiftRequest;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.AppliedShiftResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.UpcomingBookingRequest;
import com.mylocumchoice.MyLocumChoicePharmacy.model.eventmodels.AppliedShiftData;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings.AppliedShiftPresenterImpl;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings.AppliedShiftsPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings.UpcomingBookingPresenterImpl;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ListItemClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.RecyclerViewClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters.BookingHistoryCompleteAdapter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters.DeclinedShiftsAdapter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters.PendingShiftsAdapter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface.AppliedShiftView;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.ShiftDetailActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.SortActivity;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

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
 * Use the {@link ShiftDetailAppliedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShiftDetailAppliedFragment extends BaseFragment implements AppliedShiftView,ListItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.rv_pending)
    RecyclerView rvPending;
    @BindView(R.id.swipePending)
    SwipeRefreshLayout swipePending;
    @BindView(R.id.rv_declined)
    RecyclerView rvDeclined;
    @BindView(R.id.swipeDeclined)
    SwipeRefreshLayout swipeDeclined;
    @BindView(R.id.tv_pending)
    TextView tvPending;
    @BindView(R.id.tv_noData)
    TextView tv_noData;
    @BindView(R.id.shadow)
    MaterialShadowContainerView shadow;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private LinearLayoutManager pendingLayoutManager;
    private LinearLayoutManager declinedLayoutManager;
    PendingShiftsAdapter pendingShiftsAdapter;
    DeclinedShiftsAdapter declinedShiftsAdapter;

    int pending_page_num=1;
    int declined_page_num=1;

    boolean pendingClicked=true;
    boolean declinedClicked=false;

    boolean isPendingDetailClicked=false;
    boolean isDeclinedDetailClicked=false;

    int totalDeclinedPages=0;
    int totalPendingPages=0;

    private boolean isPendingLoading = false;
    private boolean isPendingLastPage = false;

    private boolean isDeclinedLoading = false;
    private boolean isDeclinedLastPage = false;

    int clickedPosition=0;
    int totalPendingShifts=0;
    int totalDeclinedShifts=0;
    int visiblePendingShifts=0;
    int visibleDeclinedShifts=0;

    @BindView(R.id.progressBar1)
    ProgressBar progressBar1;

    AppliedShiftRequest appliedShiftRequest=new AppliedShiftRequest();
    /**
     * ButterKnife Code
     **/
    @BindView(R.id.tv_applied)
    TextView tvApplied;
    /**
     * ButterKnife Code
     **/

    AppliedShiftsPresenter mPresenter;
    List<AppliedShiftResponse.Shift> lv_pendingShifts = new ArrayList<>();
    List<AppliedShiftResponse.Shift> lv_declinedShifts = new ArrayList<>();

    public ShiftDetailAppliedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShiftDetailAppliedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShiftDetailAppliedFragment newInstance(String param1, String param2) {
        ShiftDetailAppliedFragment fragment = new ShiftDetailAppliedFragment();
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
        View view = inflater.inflate(R.layout.fragment_shift_detail_applied, container, false);

        ButterKnife.bind(this, view);
        mPresenter = new AppliedShiftPresenterImpl(this, getActivity());
        pendingLayoutManager = new LinearLayoutManager(getActivity());
        declinedLayoutManager = new LinearLayoutManager(getActivity());
        rvPending.setLayoutManager(pendingLayoutManager);
        rvDeclined.setLayoutManager(declinedLayoutManager);
        tvPending.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
        tvApplied.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey));
        tvPending.setEnabled(false);
        rvPending.setVisibility(View.VISIBLE);
        rvDeclined.setVisibility(View.GONE);
        swipePending.setVisibility(View.VISIBLE);
        swipeDeclined.setVisibility(View.GONE);

        LandingActivity.menu_right.setVisibility(View.GONE);
        LandingActivity.menu_right_cal_up.setVisibility(View.GONE);
        LandingActivity.menu_right.setClickable(false);
        LandingActivity.ll_RightMenu.setClickable(false);
        LandingActivity.fl_leftMenu.setClickable(false);

        isPendingLastPage=false;
        pending_page_num = 1;
        mPresenter.getPendingBookings(appliedShiftRequest.add(pending_page_num),pending_page_num);

//        LandingActivity.menu_right.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        LandingActivity.ll_RightMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


        swipePending.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isPendingLastPage=false;
                pending_page_num = 1;
                lv_pendingShifts.clear();
                mPresenter.getPendingBookings(appliedShiftRequest.add(pending_page_num),0);
            }
        });

        swipeDeclined.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isDeclinedLastPage=false;
                declined_page_num = 1;
                lv_declinedShifts.clear();
                mPresenter.getDeclinedBookings(appliedShiftRequest.add(declined_page_num),0);
            }
        });


        rvPending.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = pendingLayoutManager.getChildCount();
                int totalItemCount = pendingLayoutManager.getItemCount();
                int firstVisibleItemPosition = pendingLayoutManager.findFirstVisibleItemPosition();

                if (!isPendingLoading && !isPendingLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            ) {
                        loadMorePendingItems();
                    }
                }
            }
        });

        rvDeclined.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = declinedLayoutManager.getChildCount();
                int totalItemCount = declinedLayoutManager.getItemCount();
                int firstVisibleItemPosition = declinedLayoutManager.findFirstVisibleItemPosition();

                if (!isDeclinedLoading && !isDeclinedLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            ) {
                        loadMoreDeclinedItems();
                    }
                }
            }
        });



        return view;
    }

    private void loadMorePendingItems() {
        isPendingLoading = true;
        progressBar1.setVisibility(View.VISIBLE);
        mPresenter.getPendingBookings(appliedShiftRequest.add(pending_page_num),pending_page_num);
    }

    private void loadMoreDeclinedItems() {
        isDeclinedLoading = true;
        progressBar1.setVisibility(View.VISIBLE);
        mPresenter.getDeclinedBookings(appliedShiftRequest.add(declined_page_num),declined_page_num);
    }

    @OnClick(R.id.tv_pending)
    public void onPending() {
        pendingClicked=true;
        declinedClicked=false;
        tvPending.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
        tvApplied.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey));
        rvPending.setVisibility(View.VISIBLE);
        rvDeclined.setVisibility(View.GONE);
        swipePending.setVisibility(View.VISIBLE);
        swipeDeclined.setVisibility(View.GONE);
        pending_page_num = 1;
        isPendingLastPage=false;
        lv_pendingShifts.clear();
        mPresenter.getPendingBookings(appliedShiftRequest.add(pending_page_num),pending_page_num);

    }

    @Override
    public void onResume() {
        super.onResume();

        if(pendingClicked) {
            if (isPendingDetailClicked) {
                try {
                    AppliedShiftData appliedShiftData= EventBus.getDefault().getStickyEvent(AppliedShiftData.class);

                    int listSize=lv_pendingShifts.size();

                    if(listSize==appliedShiftData.getAl_shifts().size()){

                    }else {
                        lv_pendingShifts.clear();
                        lv_pendingShifts=appliedShiftData.getAl_shifts();
                        pendingShiftsAdapter = new PendingShiftsAdapter(getActivity(), lv_pendingShifts,this);
                        rvPending.setAdapter(pendingShiftsAdapter);

                        totalPendingShifts=totalPendingShifts-1;
                        if(totalPendingShifts==lv_pendingShifts.size()){
                            rvPending.scrollToPosition(clickedPosition-1);
                        }else {
                            rvPending.scrollToPosition(clickedPosition);
                        }

                        EventBus.getDefault().removeStickyEvent(AppliedShiftData.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else if(declinedClicked){
            if(isDeclinedDetailClicked){
                try{
                    AppliedShiftData appliedShiftData=EventBus.getDefault().getStickyEvent(AppliedShiftData.class);

                    int listSize=lv_declinedShifts.size();

                    if(listSize==appliedShiftData.getAl_shifts().size()){

                    }else {
                        lv_declinedShifts.clear();
                        lv_declinedShifts=appliedShiftData.getAl_shifts();
                        declinedShiftsAdapter = new DeclinedShiftsAdapter(getActivity(), lv_declinedShifts,this);
                        rvDeclined.setAdapter(declinedShiftsAdapter);
                        totalDeclinedShifts=totalDeclinedShifts-1;
                        if(totalDeclinedShifts==lv_declinedShifts.size()){
                            rvDeclined.scrollToPosition(clickedPosition-1);
                        }else{
                            rvDeclined.scrollToPosition(clickedPosition);
                        }

                        EventBus.getDefault().removeStickyEvent(AppliedShiftData.class);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
        /*if(pendingClicked) {
            isPendingLastPage=false;
            pending_page_num = 1;
            mPresenter.getPendingBookings(appliedShiftRequest.add(pending_page_num),pending_page_num);
        }else if(declinedClicked){
            isDeclinedLastPage=false;
            declined_page_num=1;
            mPresenter.getDeclinedBookings(appliedShiftRequest.add(declined_page_num),declined_page_num);
        }*/
    }

    @OnClick(R.id.tv_applied)
    public void onDecline() {
        pendingClicked=false;
        declinedClicked=true;
        tvPending.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey));
        tvApplied.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
        rvPending.setVisibility(View.GONE);
        rvDeclined.setVisibility(View.VISIBLE);
        swipePending.setVisibility(View.GONE);
        swipeDeclined.setVisibility(View.VISIBLE);
        tvPending.setEnabled(true);
        declined_page_num=1;
        isDeclinedLastPage=false;
        lv_declinedShifts.clear();
        mPresenter.getDeclinedBookings(appliedShiftRequest.add(declined_page_num),declined_page_num);
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
    public void onPendingListFetch(AppliedShiftResponse appliedShiftResponse) {
        isPendingLoading = false;
        progressBar1.setVisibility(View.GONE);
        swipePending.setRefreshing(false);
        swipeDeclined.setRefreshing(false);
        if(appliedShiftResponse.getShifts()!=null && appliedShiftResponse.getShifts().size()!=0) {
            lv_pendingShifts.addAll(appliedShiftResponse.getShifts());
            pendingShiftsAdapter = new PendingShiftsAdapter(getActivity(), lv_pendingShifts,this);
            rvPending.setAdapter(pendingShiftsAdapter);
            tv_noData.setVisibility(View.GONE);
            rvPending.setVisibility(View.VISIBLE);
            rvPending.scrollToPosition(visiblePendingShifts);
            visiblePendingShifts=lv_pendingShifts.size();
            totalPendingShifts=appliedShiftResponse.getTotalShifts();
            totalPendingPages = appliedShiftResponse.getTotalPages();
            if (appliedShiftResponse.getNextPage() != null) {
                double page = (double) appliedShiftResponse.getNextPage();
                pending_page_num = (int) Math.round(page);
            } else {
                pending_page_num++;
                if(pending_page_num>totalPendingPages){
                    isPendingLastPage=true;
                }else{
                    isPendingLastPage=false;
                }
            }
        }else if(appliedShiftResponse.getShifts()==null||appliedShiftResponse.getShifts().size()==0){
            rvPending.setAdapter(null);
            tv_noData.setVisibility(View.VISIBLE);
            rvPending.setVisibility(View.GONE);
            tv_noData.setText(getActivity().getResources().getString(R.string.noPendingBookings));
        }


    }

    @Override
    public void onPendingListError() {
        swipePending.setRefreshing(false);
        swipeDeclined.setRefreshing(false);
        showWarning(getActivity(), "", getResources().getString(R.string.errorTimeOut), "error");
    }

    @Override
    public void onDeclinedListFetch(AppliedShiftResponse appliedShiftResponse) {
        isDeclinedLoading = false;
        swipePending.setRefreshing(false);
        swipeDeclined.setRefreshing(false);
        progressBar1.setVisibility(View.GONE);
        if(appliedShiftResponse.getShifts()!=null && appliedShiftResponse.getShifts().size()!=0) {
            lv_declinedShifts.addAll(appliedShiftResponse.getShifts());
            declinedShiftsAdapter = new DeclinedShiftsAdapter(getActivity(), lv_declinedShifts,this);
            rvDeclined.setAdapter(declinedShiftsAdapter);
            tv_noData.setVisibility(View.GONE);
            rvDeclined.setVisibility(View.VISIBLE);
            rvDeclined.scrollToPosition(visibleDeclinedShifts);
            visibleDeclinedShifts=lv_declinedShifts.size();
            totalDeclinedShifts=appliedShiftResponse.getTotalShifts();
            totalDeclinedPages = appliedShiftResponse.getTotalPages();
            if (appliedShiftResponse.getNextPage() != null) {
                double page = (double) appliedShiftResponse.getNextPage();
                declined_page_num = (int) Math.round(page);
            } else {
                declined_page_num++;
                if(declined_page_num>totalDeclinedPages){
                    isDeclinedLastPage=true;
                }else{
                    isDeclinedLastPage=false;
                }
            }

        }else if(appliedShiftResponse.getShifts()==null||appliedShiftResponse.getShifts().size()==0){
            rvDeclined.setAdapter(null);
            tv_noData.setVisibility(View.VISIBLE);
            rvDeclined.setVisibility(View.GONE);
            tv_noData.setText(getActivity().getResources().getString(R.string.noDeclinedBookings));
        }



    }

    @Override
    public void onDeclinedListError() {
        swipePending.setRefreshing(false);
        swipeDeclined.setRefreshing(false);
        showWarning(getActivity(), "", getResources().getString(R.string.errorTimeOut), "error");
    }

    @Override
    public void onClick(int position) {
        clickedPosition=position;
        Bundle bundle = new Bundle();
        bundle.putString("isFrom", "ShiftDetailAppliedFragment");
        bundle.putInt("id", lv_pendingShifts.get(position).getId());
        if(pendingClicked){
            isPendingDetailClicked=true;
            bundle.putInt("state",0);

            try {
                List<AppliedShiftResponse.Shift> saved_pending = new ArrayList<>();
                saved_pending.addAll(lv_pendingShifts);

                AppliedShiftData appliedShiftData = new AppliedShiftData();
                appliedShiftData.setAl_shifts(saved_pending);

                EventBus.getDefault().postSticky(appliedShiftData);
            }catch (NullPointerException ne){
                ne.printStackTrace();
            }
        }else if(declinedClicked){
            isDeclinedDetailClicked=true;
            bundle.putInt("state",1);

            try {
                List<AppliedShiftResponse.Shift> saved_declined = new ArrayList<>();
                saved_declined.addAll(lv_declinedShifts);

                AppliedShiftData appliedShiftData = new AppliedShiftData();
                appliedShiftData.setAl_shifts(saved_declined);

                EventBus.getDefault().postSticky(appliedShiftData);

            }catch (NullPointerException ne){
                ne.printStackTrace();
            }
        }

        Intent intent = new Intent(getActivity(), ShiftDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
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

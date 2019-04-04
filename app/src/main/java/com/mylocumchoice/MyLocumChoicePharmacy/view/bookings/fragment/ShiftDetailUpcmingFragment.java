package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.Booking;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.UpcomingBookingRequest;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.UpcomingBookingResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.eventmodels.UpcomingBookingData;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings.UpcomingBookingPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings.UpcomingBookingPresenterImpl;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.DialogClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ListItemClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.WrapContentLinearLayoutManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities.MapActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters.BookingUpcomingAdapter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface.UpcomingBookingView;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShiftDetailUpcmingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShiftDetailUpcmingFragment extends BaseFragment implements UpcomingBookingView, ListItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.tv_total)
    TextView tvTotal;

    @BindView(R.id.ll_topBar)
    LinearLayout ll_topBar;
    @BindView(R.id.tv_noData)
    TextView tv_noData;
    Unbinder unbinder;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.shadow)
    View shadow;
    @BindView(R.id.collapse)
    AppBarLayout collapse;
    @BindView(R.id.cordinator)
    CoordinatorLayout cordinator;

    @BindView(R.id.progressBar1)
    ProgressBar progressBar1;

    @BindView(R.id.ll_noData)
    LinearLayout ll_noData;


    private boolean isLoading = false;
    private boolean isLastPage = false;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookingUpcomingAdapter mAdapter;
    LinearLayoutManager linearLayoutManager;
    public DialogClickListener listener;

    public OnFragmentInteractionListener mListener;

    UpcomingBookingPresenter mPresenter;

    int REQUEST_DETAIL_CODE = 12;
    int RESULT_DETAIL_CODE = 13;

    List<Booking> lv_bookings = new ArrayList<>();

    boolean isDetailClicked = false;

    int pageNum = 1;
    int noOfElements = 0;
    int totalPages = 0;
    Boolean isSorted = null;

    SwipyRefreshLayout swipeRefreshLayout;
    SwipeRefreshLayout swipeToRefresh;

    UpcomingBookingRequest upcomingBookingRequest;

    int clickedPosition=0;
    int totalShifts=0;

    public ShiftDetailUpcmingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShiftDetailUpcmingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShiftDetailUpcmingFragment newInstance(String param1, String param2) {
        ShiftDetailUpcmingFragment fragment = new ShiftDetailUpcmingFragment();
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
        View view = inflater.inflate(R.layout.fragment_shift_detail_upcming, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipyRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeToRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh);
        mPresenter = new UpcomingBookingPresenterImpl(this, getActivity());
        upcomingBookingRequest = new UpcomingBookingRequest();
        linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        recyclerView.setLayoutManager(linearLayoutManager);

        //LandingActivity.menu_right_cal_up.setVisibility(View.VISIBLE);
        LandingActivity.menu_right.setVisibility(View.GONE);
        // LandingActivity.ll_RightMenu.setVisibility(View.VISIBLE);
        LandingActivity.menu_right_cal_up.setVisibility(View.VISIBLE);

        LandingActivity.menu_right_cal_up.setImageDrawable(getResources().getDrawable(R.drawable.ic_nav_down));

        mPresenter.getUpcomingBookings(upcomingBookingRequest.add(pageNum, isSorted), pageNum);
        swipeRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {

                LandingActivity.isMaintaned = false;
                isLastPage = false;
                pageNum = 1;
                lv_bookings.clear();
                lv_bookings = new ArrayList<>();
                collapse.setVisibility(View.GONE);
                mPresenter.getUpcomingBookings(upcomingBookingRequest.add(pageNum, isSorted), 0);

            }
        });


        swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                LandingActivity.isMaintaned = false;
                isLastPage = false;
                collapse.setVisibility(View.GONE);
                pageNum = 1;
                lv_bookings.clear();
                lv_bookings = new ArrayList<>();
                collapse.setVisibility(View.GONE);
                mPresenter.getUpcomingBookings(upcomingBookingRequest.add(pageNum, isSorted), 0);
            }
        });


        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            ) {
                        loadMoreItems();
                    }
                }
            }
        });


        LandingActivity.menu_right_cal_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSorted == null) {
                    isSorted = false;
                    LandingActivity.menu_right_cal_up.setImageDrawable(getResources().getDrawable(R.drawable.ic_nav_up));
                } else if (isSorted) {
                    isSorted = false;
                    LandingActivity.menu_right_cal_up.setImageDrawable(getResources().getDrawable(R.drawable.ic_nav_up));
                } else {
                    isSorted = true;
                    LandingActivity.menu_right_cal_up.setImageDrawable(getResources().getDrawable(R.drawable.ic_nav_down));
                }
                try {
                    LandingActivity.isMaintaned = false;
                    pageNum = 1;
                    lv_bookings.clear();
                    isLastPage = false;
                    mPresenter.getUpcomingBookings(upcomingBookingRequest.add(pageNum, isSorted), pageNum);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        LandingActivity.ll_RightMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void loadMoreItems() {
        LandingActivity.isMaintaned = false;
        isLoading = true;
        progressBar1.setVisibility(View.VISIBLE);
        mPresenter.getUpcomingBookings(upcomingBookingRequest.add(pageNum, isSorted), pageNum);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onBookingListFetch(UpcomingBookingResponse upcomingBookingResponse) {
        swipeRefreshLayout.setRefreshing(false);
        swipeToRefresh.setRefreshing(false);
        isLoading = false;
        collapse.setVisibility(View.VISIBLE);
        if (pageNum == 1) {
            recyclerView.scrollToPosition(0);
        }
        progressBar1.setVisibility(View.GONE);
        if (upcomingBookingResponse.getBookings() != null) {
            lv_bookings.addAll(upcomingBookingResponse.getBookings());
            mAdapter = new BookingUpcomingAdapter(getActivity(), lv_bookings, this);
            recyclerView.setAdapter(mAdapter);
            totalShifts=upcomingBookingResponse.getTotalBookings();
            if (upcomingBookingResponse.getTotalBookings() == 0) {
                ll_topBar.setVisibility(View.GONE);
                ll_noData.setVisibility(View.VISIBLE);
                swipeToRefresh.setVisibility(View.VISIBLE);
                tv_noData.setVisibility(View.VISIBLE);
                cordinator.setVisibility(View.GONE);
                recyclerView.setAdapter(null);
            } else if (upcomingBookingResponse.getTotalBookings() == 1) {
                tvTotal.setText(upcomingBookingResponse.getTotalBookings() + " Booking");
                ll_topBar.setVisibility(View.VISIBLE);
                ll_noData.setVisibility(View.GONE);
                swipeToRefresh.setVisibility(View.GONE);
                tv_noData.setVisibility(View.GONE);
                cordinator.setVisibility(View.VISIBLE);
                recyclerView.setAdapter(mAdapter);
                collapse.setExpanded(true);
            } else {
                tvTotal.setText(upcomingBookingResponse.getTotalBookings() + " Bookings");
                ll_topBar.setVisibility(View.VISIBLE);
                tv_noData.setVisibility(View.GONE);
                ll_noData.setVisibility(View.GONE);
                swipeToRefresh.setVisibility(View.GONE);
                cordinator.setVisibility(View.VISIBLE);
                recyclerView.setAdapter(mAdapter);
                collapse.setExpanded(true);
            }
            totalPages = upcomingBookingResponse.getTotalPages();
            if (upcomingBookingResponse.getNextPage() != null) {
                double page = (double) upcomingBookingResponse.getNextPage();
                pageNum = (int) Math.round(page);
            } else {
                pageNum++;
                if (pageNum > totalPages) {
                    isLastPage = true;
                } else {
                    isLastPage = false;
                }
            }
        } else {
            if (pageNum <= 1 && upcomingBookingResponse.getNextPage() == null) {
                ll_topBar.setVisibility(View.GONE);
                tv_noData.setVisibility(View.VISIBLE);
                ll_noData.setVisibility(View.VISIBLE);
                swipeToRefresh.setVisibility(View.VISIBLE);
                cordinator.setVisibility(View.GONE);
                recyclerView.setAdapter(null);
                tvTotal.setText("No upcoming bookings");
            } else {

            }

        }

        if (upcomingBookingResponse.isUnseen_notifications()) {
            LandingActivity.click_bell.setImageDrawable(getResources().getDrawable(R.drawable.notify_dot));
        } else {
            LandingActivity.click_bell.setImageDrawable(getResources().getDrawable(R.drawable.menu_bell_off));
        }

        try {
            SharedPrefManager.getInstance(getActivity()).setNotifyDot(upcomingBookingResponse.isUnseen_notifications());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBookingListError() {
        try {
            swipeToRefresh.setRefreshing(false);
            swipeRefreshLayout.setRefreshing(false);
            showWarning(getActivity(), "", getResources().getString(R.string.errorTimeOut), "error");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(int position) {

        isDetailClicked = true;
        clickedPosition=position;
        try{
            ArrayList<Booking> saved_bookings=new ArrayList<>();
            saved_bookings.addAll(lv_bookings);
            UpcomingBookingData upcomingBookingData=new UpcomingBookingData();
            upcomingBookingData.setAl_bookings(saved_bookings);
            EventBus.getDefault().postSticky(upcomingBookingData);
        }catch (Exception e){
            e.printStackTrace();
        }

        LandingActivity.isMaintaned = false;
        Intent mIntent = new Intent(getActivity(), MapActivity.class);
        mIntent.putExtra("from", "BookingUpcoming");
        if (lv_bookings.get(position).getAddressLatitude() != null && lv_bookings.get(position).getAddressLongitude() != null) {
            mIntent.putExtra("lat", (String) lv_bookings.get(position).getAddressLatitude());
            mIntent.putExtra("long", (String) lv_bookings.get(position).getAddressLongitude());
        }
        mIntent.putExtra("id", lv_bookings.get(position).getId());
        startActivityForResult(mIntent, REQUEST_DETAIL_CODE);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*if (requestCode == REQUEST_DETAIL_CODE && resultCode == RESULT_DETAIL_CODE) {
            //LandingActivity.isMaintaned=false;
            pageNum = 1;
            *//*isSorted = false;
            LandingActivity.menu_right_cal_up.setImageDrawable(getResources().getDrawable(R.drawable.ic_nav_down));*//*
            isLastPage = false;
            lv_bookings.clear();
            lv_bookings = new ArrayList<>();
            mPresenter.getUpcomingBookings(upcomingBookingRequest.add(pageNum, isSorted), pageNum);
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            hideProgress();
        } catch (Exception e) {

        }


        if(isDetailClicked){

            int listSize=lv_bookings.size();

            UpcomingBookingData upcomingBookingData=new UpcomingBookingData();
            if(EventBus.getDefault().getStickyEvent(UpcomingBookingData.class)!=null) {
                upcomingBookingData = EventBus.getDefault().getStickyEvent(UpcomingBookingData.class);
            }

            if(listSize==upcomingBookingData.getAl_bookings().size()){

            }else{
                lv_bookings.clear();
                lv_bookings=upcomingBookingData.getAl_bookings();
                mAdapter = new BookingUpcomingAdapter(getActivity(), lv_bookings, this);
                recyclerView.setAdapter(mAdapter);
                recyclerView.scrollToPosition(clickedPosition);
                isDetailClicked = false;
                EventBus.getDefault().removeStickyEvent(UpcomingBookingData.class);
                totalShifts = totalShifts - 1;
                if (totalShifts == 0) {
                    ll_topBar.setVisibility(View.GONE);
                    ll_noData.setVisibility(View.VISIBLE);
                    swipeToRefresh.setVisibility(View.VISIBLE);
                    tv_noData.setVisibility(View.VISIBLE);
                    cordinator.setVisibility(View.GONE);
                    recyclerView.setAdapter(null);
                } else if (totalShifts == 1) {
                    tvTotal.setText(totalShifts + " Booking");
                    ll_topBar.setVisibility(View.VISIBLE);
                    ll_noData.setVisibility(View.GONE);
                    swipeToRefresh.setVisibility(View.GONE);
                    tv_noData.setVisibility(View.GONE);
                    cordinator.setVisibility(View.VISIBLE);
                } else {
                    tvTotal.setText(totalShifts + " Bookings");
                    ll_topBar.setVisibility(View.VISIBLE);
                    tv_noData.setVisibility(View.GONE);
                    ll_noData.setVisibility(View.GONE);
                    swipeToRefresh.setVisibility(View.GONE);
                    cordinator.setVisibility(View.VISIBLE);
                }

                if(totalShifts==lv_bookings.size()){
                    recyclerView.scrollToPosition(clickedPosition-1);
                }
            }
        }

        /*if (!LandingActivity.isMaintaned) {
            pageNum = 1;
            isLastPage = false;
            lv_bookings.clear();
            lv_bookings = new ArrayList<>();
            mPresenter.getUpcomingBookings(upcomingBookingRequest.add(pageNum, isSorted), pageNum);
        } else {
            pageNum = 1;
            isLastPage = false;
            isSorted = null;
            LandingActivity.menu_right_cal_up.setImageDrawable(getResources().getDrawable(R.drawable.ic_nav_down));
            lv_bookings.clear();
            lv_bookings = new ArrayList<>();
            mPresenter.getUpcomingBookings(upcomingBookingRequest.add(pageNum, isSorted), pageNum);
        }*/

    }
}

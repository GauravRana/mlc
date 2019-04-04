package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.CompletedBooking;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.CompletedBookingRequest;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.CompletedBookingResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings.CancelledBookingPresenterImpl;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings.CancelledBookingsPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities.BookingHistoryActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters.CancelledBookHistoryAdapter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface.CompletedBookingView;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CancelledBookHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CancelledBookHistoryFragment extends BaseFragment implements CompletedBookingView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.shadow)
    View shadow;
    @BindView(R.id.collapse)
    AppBarLayout collapse;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_topBar)
    LinearLayout ll_topBar;

    @BindView(R.id.ll_noData)
    LinearLayout ll_noData;

    @BindView(R.id.swipeNodata)
    SwipeRefreshLayout swipeNodata;

    @BindView(R.id.tv_noData)
    TextView tv_noData;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LinearLayoutManager linearLayoutManager;
    private CancelledBookHistoryAdapter mAdapter;

    private OnFragmentInteractionListener mListener;

    int pageNum = 1;
    int noOfElements = 0;
    int totalPages = 0;

    List<CompletedBookingResponse.Booking> lv_bookings = new ArrayList<>();
    CancelledBookingsPresenter mPresenter;
    CompletedBooking completedBooking;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private boolean isLoading = false;
    private boolean isLastPage = false;

    @BindView(R.id.progressBar1)
    ProgressBar progressBar1;

    View view;

    public CancelledBookHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CancelledBookHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CancelledBookHistoryFragment newInstance(String param1, String param2) {
        CancelledBookHistoryFragment fragment = new CancelledBookHistoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_cancelled_book_history, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        ButterKnife.bind(this, view);
        completedBooking = new CompletedBooking();
        completedBooking.setNextPage(pageNum);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        BookingHistoryActivity.ll_menuRight.setVisibility(View.VISIBLE);
        BookingHistoryActivity.tvDots.setVisibility(View.GONE);
        mPresenter = new CancelledBookingPresenterImpl(this, getActivity());
        mPresenter.onGetBookings(getBookingReq(completedBooking),pageNum);
      /*  mAdapter = new CancelledBookHistoryAdapter(new BaseFragment(),getActivity(), new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
//                Bundle bundle = new Bundle();
//                bundle.putString("isFrom", "CancelledBookHistoryFragment");
//                startActivityWithBundle(ShiftDetailActivity.class, bundle);

            }
        });*/

        //recyclerView.setAdapter(mAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isLastPage=false;
                pageNum = 1;
                lv_bookings.clear();
                collapse.setVisibility(View.GONE);
                completedBooking.setNextPage(pageNum);
                mPresenter.onGetBookings(getBookingReq(completedBooking),0);
            }
        });

        swipeNodata.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isLastPage=false;
                pageNum = 1;
                lv_bookings.clear();
                collapse.setVisibility(View.GONE);
                completedBooking.setNextPage(pageNum);
                mPresenter.onGetBookings(getBookingReq(completedBooking),0);
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

        return view;
    }

    private void loadMoreItems() {
        isLoading = true;
        progressBar1.setVisibility(View.VISIBLE);
        completedBooking.setNextPage(pageNum);
        mPresenter.onGetBookings(getBookingReq(completedBooking),pageNum);
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
    public void onCompletedBookingSuccess(CompletedBookingResponse completedBookingResponse) {
        isLoading = false;
        progressBar1.setVisibility(View.GONE);
        collapse.setVisibility(View.VISIBLE);
        if(pageNum==1){
            collapse.setExpanded(true);
        }
        if (completedBookingResponse.getBookings() != null) {
            lv_bookings.addAll(completedBookingResponse.getBookings());
            mAdapter = new CancelledBookHistoryAdapter(getActivity(), lv_bookings);
            recyclerView.setAdapter(mAdapter);
            swipeRefreshLayout.setRefreshing(false);

            if(completedBookingResponse.getTotalShifts()==0){
                tv_noData.setVisibility(View.VISIBLE);
                ll_noData.setVisibility(View.VISIBLE);
                swipeNodata.setVisibility(View.VISIBLE);
                ll_topBar.setVisibility(View.GONE);
                recyclerView.setAdapter(null);
                recyclerView.setVisibility(View.GONE);
            } else if (lv_bookings.size() == 1) {
                tvTotal.setText(completedBookingResponse.getTotalShifts() + " Booking");
                ll_noData.setVisibility(View.GONE);
                swipeNodata.setVisibility(View.GONE);
                tv_noData.setVisibility(View.GONE);
                ll_topBar.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
            } else {
                tvTotal.setText(completedBookingResponse.getTotalShifts() + " Bookings");
                ll_noData.setVisibility(View.GONE);
                swipeNodata.setVisibility(View.GONE);
                tv_noData.setVisibility(View.GONE);
                ll_topBar.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
            }
            recyclerView.scrollToPosition(noOfElements);
            noOfElements = lv_bookings.size();
            if (completedBookingResponse.getNextPage() != null) {
                double page = (double) completedBookingResponse.getNextPage();
                pageNum = (int) Math.round(page);
            } else {
                pageNum++;
                if(pageNum>totalPages){
                    isLastPage=true;
                }else{
                    isLastPage=false;
                }
            }
        } else {
            if (pageNum <= 1 && completedBookingResponse.getNextPage() == null) {
                recyclerView.setAdapter(null);
                ll_noData.setVisibility(View.VISIBLE);
                swipeNodata.setVisibility(View.VISIBLE);
                tv_noData.setVisibility(View.VISIBLE);
                ll_topBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
            } else {

            }
        }

        totalPages = completedBookingResponse.getTotalPages();

    }

    @Override
    public void onCompletedBookingFailure() {
        showWarning(getActivity(), "", getResources().getString(R.string.errorTimeOut), "error");
        swipeRefreshLayout.setRefreshing(false);
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

    private JsonObject getBookingReq(CompletedBooking completedBooking) {
        CompletedBookingRequest completedBookingRequest = new CompletedBookingRequest();
        return completedBookingRequest.add(completedBooking);
    }
}

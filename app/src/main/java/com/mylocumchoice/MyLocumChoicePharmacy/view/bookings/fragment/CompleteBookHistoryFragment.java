package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.Clients;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.CompletedBooking;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.CompletedBookingRequest;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.CompletedBookingResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.EmailSummaryResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.eventmodels.CompletedBookingData;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings.CompletedBookingPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings.CompletedBookingPresenterImpl;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings.EmailSummaryPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings.EmailSummaryPresenterImpl;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ListItemClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities.BookingDetailsActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities.BookingHistoryActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities.BookingHistorySortActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters.BookingHistoryCompleteAdapter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface.CompletedBookingView;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface.PaymentSummaryView;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CompleteBookHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompleteBookHistoryFragment extends BaseFragment implements CompletedBookingView, PaymentSummaryView,ListItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_price_lab)
    TextView tvPriceLab;
    @BindView(R.id.priceLayout)
    LinearLayout priceLayout;
    @BindView(R.id.tv_paid)
    TextView tvPaid;
    @BindView(R.id.tv_paid_lab)
    TextView tvPaidLab;
    @BindView(R.id.paidLayout)
    LinearLayout paidLayout;
    @BindView(R.id.tv_unpaid)
    TextView tvUnpaid;
    @BindView(R.id.tv_unpaid_lab)
    TextView tvUnpaidLab;
    @BindView(R.id.unPaidLayout)
    LinearLayout unPaidLayout;
    @BindView(R.id.bookingLayout)
    LinearLayout bookingLayout;
    @BindView(R.id.ll_receivePayment)
    LinearLayout llReceivePayment;
    @BindView(R.id.ll_noData)
    LinearLayout ll_noData;
    @BindView(R.id.swipeNodata)
    SwipeRefreshLayout swipeNodata;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_noData)
    TextView tv_noData;
    Unbinder unbinder;
    private int dotscount;
    private ImageView[] dots;

    boolean isActivityResult=false;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public OnFragmentInteractionListener mListener;
    private ImageView _btn1, _btn2, _btn3;

    private LinearLayoutManager linearLayoutManager;
    BookingHistoryCompleteAdapter mAdapter;

    int REQUEST_CODE_FILTERS = 3;
    int RESULT_CODE_FILTERS = 4;

    int REQUEST_UPDATE_CODE=5;
    int RESULT_UPDATE_CODE=6;

    int pageNum = 1;
    int noOfElements = 0;
    int totalPages = 0;

    List<CompletedBookingResponse.Booking> lv_bookings = new ArrayList<>();

    CompletedBooking completedBooking, completedBookingFilters;
    View view;

    CompletedBookingPresenter mPresenter;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    EmailSummaryPresenter mEmailSummaryPresenter;

    boolean isFilterApplied=false;

    private boolean isLoading = false;
    private boolean isLastPage = false;

    ProgressBar progressBar1;

    /*int paid_count=0,unpaid_count=0;
    String totalAmount="";*/

    boolean isDetailClicked = false;
    public static boolean isUpdated=false;

    public CompleteBookHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CompleteBookHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CompleteBookHistoryFragment newInstance(String param1, String param2) {
        CompleteBookHistoryFragment fragment = new CompleteBookHistoryFragment();
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
        view = inflater.inflate(R.layout.fragment_complete_book_history, container, false);
        progressBar1=view.findViewById(R.id.progressBar1);
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Magic here

            }
        }, 1000);*/

        init();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    private void init() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        ButterKnife.bind(this, view);
        completedBooking = new CompletedBooking();
        try {
            if (EventBus.getDefault().getStickyEvent(CompletedBooking.class) != null) {
                completedBooking = EventBus.getDefault().getStickyEvent(CompletedBooking.class);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        completedBooking.setNextPage(pageNum);
        mEmailSummaryPresenter = new EmailSummaryPresenterImpl(this, getActivity());
        mPresenter = new CompletedBookingPresenterImpl(this, getActivity());
        mPresenter.onGetCompletedBookings(getBookingReq(completedBooking), pageNum);
        BookingHistoryActivity.ll_menuRight.setVisibility(View.VISIBLE);
        BookingHistoryActivity.tvDots.setVisibility(View.VISIBLE);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isLastPage=false;
                pageNum = 1;
                lv_bookings.clear();
                completedBooking.setNextPage(pageNum);
                mPresenter.onGetCompletedBookings(getBookingReq(completedBooking),0);
            }
        });

        swipeNodata.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isLastPage=false;
                pageNum = 1;
                lv_bookings.clear();
                completedBooking.setNextPage(pageNum);
                mPresenter.onGetCompletedBookings(getBookingReq(completedBooking),0);
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

        BookingHistoryActivity.ll_menuRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    EventBus.getDefault().removeStickyEvent(Clients.class);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    Intent mIntent = new Intent(getActivity(), BookingHistorySortActivity.class);
                    startActivityForResult(mIntent, REQUEST_CODE_FILTERS);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        });

    }

    private void loadMoreItems() {
        isLoading = true;
        progressBar1.setVisibility(View.VISIBLE);
        completedBooking.setNextPage(pageNum);
        mPresenter.onGetCompletedBookings(getBookingReq(completedBooking),pageNum);
    }

    @Override
    public void onResume() {
        super.onResume();

        if(isDetailClicked ){
            isDetailClicked=false;
            isUpdated=false;
        }
           /* CompletedBookingData completedBookingData=EventBus.getDefault().getStickyEvent(CompletedBookingData.class);

            for(int i=0;i<lv_bookings.size();i++){
                if(lv_bookings.get(i).getId()==completedBookingData.getSelectedBooking().getId()){
                    lv_bookings.get(i).setTotalAmount(completedBookingData.getSelectedBooking().getTotalAmount());
                    lv_bookings.get(i).setPaid(completedBookingData.getSelectedBooking().getPaid());

                    mAdapter.notifyDataSetChanged();
                }
            }

            if (completedBookingData.getTotalAmount() != null) {
                tvPrice.setVisibility(View.VISIBLE);
                tvPrice.setText(completedBookingData.getTotalAmount());
            } else {
                tvPrice.setVisibility(View.GONE);
            }

            if (completedBookingData.getPaidBookingsCount() != null) {
                tvPaid.setText(completedBookingData.getPaidBookingsCount()+"");
                tvPaid.setVisibility(View.VISIBLE);
            } else {
                tvPaid.setVisibility(View.GONE);
                tvPaidLab.setVisibility(View.GONE);
            }

            if (completedBookingData.getUnpaidBookingsCount() != null) {
                tvUnpaid.setText(completedBookingData.getUnpaidBookingsCount()+"");
            } else {
                tvUnpaid.setVisibility(View.GONE);
            }*/



        //new Async(view).execute();
        /*if(!isActivityResult && EventBus.getDefault().getStickyEvent(CompletedBooking.class)!=null) {
            completedBooking = EventBus.getDefault().getStickyEvent(CompletedBooking.class);
            pageNum = 1;
            lv_bookings.clear();
            completedBooking.setNextPage(pageNum);
            mPresenter.onGetCompletedBookings(getBookingReq(completedBooking), pageNum);
        }else if(!isActivityResult && EventBus.getDefault().getStickyEvent(CompletedBooking.class)==null){
            isLastPage = false;
            pageNum = 1;
            lv_bookings.clear();
            completedBooking.setNextPage(pageNum);
            mPresenter.onGetCompletedBookings(getBookingReq(completedBooking), pageNum);
        }*/
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
        EventBus.getDefault().postSticky(completedBookingResponse);
        isLoading = false;
        try {
            HistorySortFragment.isAsc = null;
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            progressBar1.setVisibility(View.GONE);
        }catch (NullPointerException ne){
            ne.printStackTrace();
        }
        if (completedBookingResponse.getBookings() != null) {
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            lv_bookings.addAll(completedBookingResponse.getBookings());
            mAdapter = new BookingHistoryCompleteAdapter(getActivity(), lv_bookings,this);
            recyclerView.setAdapter(mAdapter);
            try {
                swipeRefreshLayout.setRefreshing(false);
            }catch (NullPointerException ne){
                ne.printStackTrace();
            }
            try {
                swipeNodata.setRefreshing(false);
            }catch (NullPointerException ne){
                ne.printStackTrace();
            }
            recyclerView.scrollToPosition(noOfElements);
            noOfElements = lv_bookings.size();
            totalPages = completedBookingResponse.getTotalPages();
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

            if(lv_bookings.size()==0){
                ll_noData.setVisibility(View.VISIBLE);
                swipeNodata.setVisibility(View.VISIBLE);
                tv_noData.setVisibility(View.VISIBLE);
                recyclerView.setAdapter(null);
                swipeRefreshLayout.setVisibility(View.GONE);
            }else{
                try {
                    ll_noData.setVisibility(View.GONE);
                    tv_noData.setVisibility(View.GONE);
                }catch (NullPointerException ne){
                    ne.printStackTrace();
                }

                try {
                    swipeNodata.setVisibility(View.GONE);
                }catch (NullPointerException ne){
                    ne.printStackTrace();
                }

                recyclerView.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);
            }
        } else {
            if (pageNum <= 1 && completedBookingResponse.getNextPage() == null) {
                recyclerView.setAdapter(null);
                swipeRefreshLayout.setVisibility(View.GONE);
                ll_noData.setVisibility(View.VISIBLE);
                swipeNodata.setVisibility(View.VISIBLE);
                tv_noData.setVisibility(View.VISIBLE);
            } else {

            }
        }

        try {
            if (completedBookingResponse.getTotalAmount() != null) {
                tvPrice.setVisibility(View.VISIBLE);
                tvPrice.setText(completedBookingResponse.getTotalAmount());
            } else {
                //tvPrice.setText(getResources().getString(R.string.not_applicable));
                tvPrice.setVisibility(View.GONE);
            }
        }catch (NullPointerException ne){
            ne.printStackTrace();
        }


        try {
            if (completedBookingResponse.getPaidBookingsCount() != null) {
                tvPaid.setText(completedBookingResponse.getPaidBookingsCount().toString());
                tvPaid.setVisibility(View.VISIBLE);
            } else {
                //tvPaid.setText(getActivity().getResources().getString(R.string.not_applicable));
                tvPaid.setVisibility(View.GONE);
                tvPaidLab.setVisibility(View.GONE);
            }
        }catch (NullPointerException ne){
            ne.printStackTrace();
        }

        try {
            if (completedBookingResponse.getUnpaidBookingsCount() != null) {
                tvUnpaid.setVisibility(View.VISIBLE);
                tvUnpaid.setText(completedBookingResponse.getUnpaidBookingsCount().toString());
            } else {
                //tvUnpaid.setText(getActivity().getResources().getString(R.string.not_applicable));
                tvUnpaid.setVisibility(View.GONE);
            }
        }catch (NullPointerException ne){
            ne.printStackTrace();
        }


        if(completedBookingResponse.isFilter_applied()){
            BookingHistoryActivity.tvDots.setImageResource(R.drawable.ic_menu_dot);
        }else{
            BookingHistoryActivity.tvDots.setImageResource(R.drawable.ic_menu);
        }

    }

    @Override
    public void onCompletedBookingFailure() {
        showWarning(getActivity(), "", getResources().getString(R.string.errorTimeOut), "error");
        swipeRefreshLayout.setRefreshing(false);
        swipeNodata.setRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSummarySuccess(EmailSummaryResponse response) {
        showWarning(getActivity(), "", response.getSuccess(), "");
    }

    @Override
    public void onSummaryFailure() {
        showWarning(getActivity(), "", getResources().getString(R.string.errorTimeOut), "error");
    }

    @OnClick(R.id.ll_receivePayment)
    public void onViewClicked() {
        mEmailSummaryPresenter.onGetEmailSummary(getBookingReq(completedBooking));
    }

    @Override
    public void onClick(int position) {
        isDetailClicked=true;
       /* try {
            CompletedBookingData completedBookingData = new CompletedBookingData();
            completedBookingData.setSelectedBooking(lv_bookings.get(position));
            completedBookingData.setTotalAmount(totalAmount);
            completedBookingData.setPaidBookingsCount(paid_count);
            completedBookingData.setUnpaidBookingsCount(unpaid_count);

            EventBus.getDefault().postSticky(completedBookingData);
        }catch (Exception e){
            e.printStackTrace();
        }*/

        Intent mIntent=new Intent(getActivity(),BookingDetailsActivity.class);
        mIntent.putExtra("isFrom","completed");
        mIntent.putExtra("id",lv_bookings.get(position).getId());
        mIntent.putExtra("status",lv_bookings.get(position).getPaid());
        mIntent.putExtra("rate",lv_bookings.get(position).getRate());
        startActivityForResult(mIntent,REQUEST_UPDATE_CODE);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_FILTERS && resultCode == RESULT_CODE_FILTERS) {
            isActivityResult=true;
            pageNum = 1;
            lv_bookings.clear();
            isLastPage=false;
            completedBooking = new CompletedBooking();
            if(EventBus.getDefault().getStickyEvent(CompletedBooking.class)!=null) {
                completedBooking = EventBus.getDefault().getStickyEvent(CompletedBooking.class);
            }
            /*Log.e("isFilterApplied",completedBooking+"");
            try {
                if (completedBooking == null) {
                    isFilterApplied = false;
                } else if ((completedBooking.getEndDate().equals("") || completedBooking.getEndDate() == null)
                        && (completedBooking.getStartDate().equals("") || completedBooking.getStartDate() == null)
                        && (completedBooking.getSortBy().equals("") || completedBooking.getSortBy() == null)
                        && (!completedBooking.getPaid())
                        && (completedBooking.getClientIds().size() == 0 || completedBooking.getClientIds() == null)) {
                    isFilterApplied = false;
                } else {
                    isFilterApplied = true;
                }
            }catch (Exception e){
                e.printStackTrace();
                isFilterApplied = false;
            }*/

            completedBooking.setNextPage(pageNum);
            mPresenter.onGetCompletedBookings(getBookingReq(completedBooking),pageNum);
        }else if(requestCode==REQUEST_UPDATE_CODE && resultCode==RESULT_UPDATE_CODE){
            isLastPage = false;
            pageNum = 1;
            lv_bookings.clear();
            completedBooking.setNextPage(pageNum);
            mPresenter.onGetCompletedBookings(getBookingReq(completedBooking), pageNum);
        }
    }

}

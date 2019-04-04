package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.fragment;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.eventmodels.Openshiftdata;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShift;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShiftReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShiftResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings.InvitationPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings.InvitationPresenterImpl;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.GPSTracker;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.GlobalConstants;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ListItemClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.PermissionUtil;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.WrapContentLinearLayoutManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.RecyclerViewClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities.SearchActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters.BookingInviteAdapter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface.InvitationView;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.ShiftDetailActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.SortActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.adapters.OpenShiftAdapter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.fragment.OpenShiftFragment;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.ankit.gpslibrary.ADLocation;
import org.ankit.gpslibrary.MyTracker;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShiftDetailInviteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShiftDetailInviteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShiftDetailInviteFragment extends BaseFragment implements InvitationView, ListItemClickListener, MyTracker.ADLocationListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private BookingInviteAdapter mAdapter;
    public Context activity;

    int REQUEST_CODE = 1;
    int REQUEST_CODE_PLACES = 2;

    int RESULT_CODE = 1;
    int RESULT_CODE_PLACES = 2;

    /** ButterKnife Code **/
    @BindView(R.id.collapse)
    android.support.design.widget.AppBarLayout collapse;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.shadow)
    com.h6ah4i.android.materialshadowninepatch.MaterialShadowContainerView shadow;
    @BindView(R.id.ll_edtText)
    LinearLayout llEdtText;

    @BindView(R.id.tv_noData)
    TextView tv_noData;
    @BindView(R.id.ll_noData)
    LinearLayout ll_noData;
    @BindView(R.id.swipeNodata)
    SwipeRefreshLayout swipeNodata;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    /** ButterKnife Code **/
    int REQUEST_CODE_FILTERS = 3;
    int RESULT_CODE_FILTERS = 4;

    boolean isDetailClicked=false;

    View view;

    public static TextView tvSearch;

    OpenShift openShift;
    InvitationPresenter mPresenter;

    List<OpenShiftResponse.Shift> lv_shifts = new ArrayList<>();
    int pageNum = 1;
    int noOfElements = 0;
    int totalPages = 0;

    double latitide = 0.0;
    double longitude = 0.0;

    double savedLat=0.0;
    double savedLong=0.0;

    private boolean isLoading = false;
    private boolean isLastPage = false;

    @BindView(R.id.progressBar1)
    ProgressBar progressBar1;

    Utils mUtils;
    LocationManager lm;
    boolean gps_enabled = false;

    @BindView(R.id.iv_clear)
    ImageView iv_clear;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    int clickedPosition=0;
    int totalShifts=0;

    boolean isRefreshing=false;


    public ShiftDetailInviteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShiftDetailInviteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShiftDetailInviteFragment newInstance(Context context, String param1, String param2) {
        ShiftDetailInviteFragment fragment = new ShiftDetailInviteFragment();
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

        view = inflater.inflate(R.layout.fragment_shift_detail_invite, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mUtils=new Utils();
        lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        ButterKnife.bind(this, view);
        tvSearch=view.findViewById(R.id.tv_search);
        tvSearch.setMovementMethod(new ScrollingMovementMethod());
        LandingActivity.menu_right.setVisibility(View.VISIBLE);
        LandingActivity.menu_right_cal_up.setVisibility(View.GONE);

        gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (gps_enabled) {

        }else{
            if(GlobalConstants.currentLocation){
                GlobalConstants.SelectedAddress="";
                GlobalConstants.SelectedLat=0.0;
                GlobalConstants.SelectedLong=0.0;
                SharedPrefManager.getInstance(getActivity()).setCurrentLatLng("0.0","0.0");
            }
        }

        if((GlobalConstants.SelectedAddress!=null||!GlobalConstants.SelectedAddress.equals(""))&&GlobalConstants.SelectedLat!=0.0 && GlobalConstants.SelectedLong!=0.0){
            tvSearch.setText(GlobalConstants.SelectedAddress);

            if(GlobalConstants.isEntered){
                iv_clear.setVisibility(View.VISIBLE);
            }else{
                iv_clear.setVisibility(View.GONE);
            }
        }else {

            if (SharedPrefManager.getInstance(getActivity()).getAddress() != null && !SharedPrefManager.getInstance(getActivity()).getAddress().equals("")) {
                tvSearch.setText(SharedPrefManager.getInstance(getActivity()).getAddress());
            } else {
                if ((ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {


                } else {
                    try {
                        tvSearch.setText(mUtils.getAddress(getActivity(), Double.parseDouble(SharedPrefManager.getInstance(getActivity()).getCurrentLat()),
                                Double.parseDouble(SharedPrefManager.getInstance(getActivity()).getCurrentLong())));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (SharedPrefManager.getInstance(getActivity()).getOpenShiftData() != null) {
            openShift = SharedPrefManager.getInstance(getActivity()).getOpenShiftData();
            try {
                if (openShift.getStartDate().equals("") || openShift.getStartDate() == null) {
                    openShift.setStartDate("");
                }

                if (openShift.getEndDate().equals("") || openShift.getEndDate() == null) {
                    openShift.setEndDate("");
                }

                SharedPrefManager.getInstance(getActivity()).setShiftData(openShift);
            }catch (Exception e ){
                e.printStackTrace();
            }
        } else {
            openShift = new OpenShift();
        }



        if((GlobalConstants.SelectedAddress!=null||!GlobalConstants.SelectedAddress.equals(""))&&GlobalConstants.SelectedLat!=0.0 && GlobalConstants.SelectedLong!=0.0){
            openShift.setLatitude(GlobalConstants.SelectedLat);
            openShift.setLongitude(GlobalConstants.SelectedLong);
        }else {
            if (SharedPrefManager.getInstance(getActivity()).getLat() != null && SharedPrefManager.getInstance(getActivity()).getLong() != null) {
                String str_lat = SharedPrefManager.getInstance(getActivity()).getLat();
                String str_lng = SharedPrefManager.getInstance(getActivity()).getLong();

                if (!str_lat.equalsIgnoreCase("0.0") && !str_lng.equalsIgnoreCase("0.0")) {
                    savedLat = Double.parseDouble(str_lat);
                    openShift.setLatitude(savedLat);

                    savedLong = Double.parseDouble(str_lng);
                    openShift.setLongitude(savedLong);
                } else {
                    gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    if (gps_enabled) {
                        savedLat = Double.parseDouble(SharedPrefManager.getInstance(getActivity()).getCurrentLat());
                        savedLong = Double.parseDouble(SharedPrefManager.getInstance(getActivity()).getCurrentLong());
                        openShift.setLatitude(savedLat);
                        openShift.setLongitude(savedLong);

                    } else {
                        openShift.setLatitude(0.0);
                        openShift.setLongitude(0.0);
                    }

                }
            } else {
                openShift.setLatitude(0.0);
                openShift.setLongitude(0.0);
            }
        }
        openShift.setNextPage(pageNum);
        mPresenter=new InvitationPresenterImpl(this,getActivity());

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        mPresenter.onGetInvitations(getOpenShiftRequest(openShift),pageNum);


        iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSearch.setText("");
                iv_clear.setVisibility(View.GONE);
                GlobalConstants.SelectedAddress="";
                GlobalConstants.SelectedLat=0.0;
                GlobalConstants.SelectedLong=0.0;
                GlobalConstants.isEntered=false;

                if (SharedPrefManager.getInstance(getActivity()).getLat() != null && SharedPrefManager.getInstance(getActivity()).getLong() != null) {
                    String str_lat = SharedPrefManager.getInstance(getActivity()).getLat();
                    String str_lng = SharedPrefManager.getInstance(getActivity()).getLong();

                    if (!str_lat.equalsIgnoreCase("0.0") && !str_lng.equalsIgnoreCase("0.0")) {
                        savedLat = Double.parseDouble(str_lat);
                        openShift.setLatitude(savedLat);

                        savedLong = Double.parseDouble(str_lng);
                        openShift.setLongitude(savedLong);
                    } else {
                        gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                        if (gps_enabled) {
                            savedLat = Double.parseDouble(SharedPrefManager.getInstance(getActivity()).getCurrentLat());
                            savedLong = Double.parseDouble(SharedPrefManager.getInstance(getActivity()).getCurrentLong());
                            openShift.setLatitude(savedLat);
                            openShift.setLongitude(savedLong);

                        } else {
                            if (GlobalConstants.currentLocation) {
                                GlobalConstants.SelectedAddress = "";
                                GlobalConstants.SelectedLat = 0.0;
                                GlobalConstants.SelectedLong = 0.0;
                                SharedPrefManager.getInstance(getActivity()).setCurrentLatLng("0.0", "0.0");
                            }

                            openShift.setLatitude(0.0);
                            openShift.setLongitude(0.0);
                        }

                    }
                } else {
                    gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    if (gps_enabled) {

                    }else{
                        if (GlobalConstants.currentLocation) {
                            GlobalConstants.SelectedAddress = "";
                            GlobalConstants.SelectedLat = 0.0;
                            GlobalConstants.SelectedLong = 0.0;
                            SharedPrefManager.getInstance(getActivity()).setCurrentLatLng("0.0", "0.0");
                        }
                    }

                    openShift.setLatitude(0.0);
                    openShift.setLongitude(0.0);
                }


                if (SharedPrefManager.getInstance(getActivity()).getAddress() != null && !SharedPrefManager.getInstance(getActivity()).getAddress().equals("")) {
                    tvSearch.setText(SharedPrefManager.getInstance(getActivity()).getAddress());
                } else {
                    gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    if (gps_enabled) {
                        try {
                            tvSearch.setText(mUtils.getAddress(getActivity(), Double.parseDouble(SharedPrefManager.getInstance(getActivity()).getCurrentLat()),
                                    Double.parseDouble(SharedPrefManager.getInstance(getActivity()).getCurrentLong())));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else {
                        tvSearch.setText("");
                    }
                }

                isLastPage = false;
                lv_shifts.clear();
                pageNum = 1;
                noOfElements = 0;
                openShift.setNextPage(pageNum);

                mPresenter.onGetInvitations(getOpenShiftRequest(openShift),pageNum);

            }
        });

        LandingActivity.menu_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAdded()) {
                    Intent mIntent = new Intent(getActivity(), SortActivity.class);
                    startActivityForResult(mIntent, REQUEST_CODE_FILTERS);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
//                startActivity(SortActivity.class);
            }
        });

        LandingActivity.ll_RightMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAdded()) {
                    Intent mIntent = new Intent(getActivity(), SortActivity.class);
                    startActivityForResult(mIntent, REQUEST_CODE_FILTERS);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                    //startActivity(SortActivity.class);
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    tvSearch.setText("");
                    isRefreshing=true;
                    if(GlobalConstants.currentLocation)
                        checkPermissions();
                    isLastPage = false;
                    collapse.setVisibility(View.GONE);
                    lv_shifts.clear();
                    pageNum = 1;
                    noOfElements = 0;
                    openShift.setNextPage(pageNum);

                    mPresenter.onGetInvitations(getOpenShiftRequest(openShift), 0);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        swipeNodata.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    tvSearch.setText("");
                    isRefreshing=true;
                    if(GlobalConstants.currentLocation)
                        checkPermissions();
                    isLastPage = false;
                    collapse.setVisibility(View.GONE);
                    lv_shifts.clear();
                    pageNum = 1;
                    noOfElements = 0;
                    openShift.setNextPage(pageNum);

                    mPresenter.onGetInvitations(getOpenShiftRequest(openShift), 0);
                }catch (Exception e){
                    e.printStackTrace();
                }
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

                /*if(firstVisibleItemPosition==0) {
                    collapse.setExpanded(true);
                }*/

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
        openShift.setNextPage(pageNum);
        mPresenter.onGetInvitations(getOpenShiftRequest(openShift),pageNum);
    }


    @Override
    public void onResume() {
        super.onResume();
        if(!GlobalConstants.isACtivityResulted)
            checkPermissions();
        if(isDetailClicked){
            try{
                Openshiftdata openshiftdata=EventBus.getDefault().getStickyEvent(Openshiftdata.class);
                int listSize=lv_shifts.size();
                if(listSize==openshiftdata.getAl_shifts().size()){

                }else {
                    lv_shifts.clear();
                    lv_shifts = openshiftdata.getAl_shifts();
                    //mAdapter.notifyDataSetChanged();
                    mAdapter = new BookingInviteAdapter(getActivity(), lv_shifts, this);
                    recyclerView.setAdapter(mAdapter);
                    recyclerView.scrollToPosition(clickedPosition);
                    isDetailClicked = false;
                    EventBus.getDefault().removeStickyEvent(Openshiftdata.class);

                    totalShifts = totalShifts - 1;
                    if (totalShifts == 0) {
                        tvTotal.setVisibility(View.GONE);
                        ll_noData.setVisibility(View.VISIBLE);
                        swipeNodata.setVisibility(View.VISIBLE);
                        tv_noData.setVisibility(View.VISIBLE);
                        swipeRefreshLayout.setVisibility(View.GONE);
                    } else if (totalShifts == 1) {
                        tvTotal.setText(totalShifts + " Invitation");
                        ll_noData.setVisibility(View.GONE);
                        swipeNodata.setVisibility(View.GONE);
                        tv_noData.setVisibility(View.GONE);
                        swipeRefreshLayout.setVisibility(View.VISIBLE);
                        tvTotal.setVisibility(View.VISIBLE);
                        recyclerView.setAdapter(mAdapter);
                    } else {
                        tvTotal.setText(totalShifts + " Invitations");
                        ll_noData.setVisibility(View.GONE);
                        swipeNodata.setVisibility(View.GONE);
                        tv_noData.setVisibility(View.GONE);
                        swipeRefreshLayout.setVisibility(View.VISIBLE);
                        recyclerView.setAdapter(mAdapter);
                        tvTotal.setVisibility(View.VISIBLE);
                    }

                    if(totalShifts==lv_shifts.size()){
                        recyclerView.scrollToPosition(clickedPosition-1);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity=context;
//        if (context instanceof LandingActivity){
//            activity = (LandingActivity) context;
//        }
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        //}
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onInvitesFetch(OpenShiftResponse openShiftResponse) {
        try {
            isLoading = false;
            collapse.setVisibility(View.VISIBLE);
            if (pageNum == 1) {
                recyclerView.scrollToPosition(0);
            }
            progressBar1.setVisibility(View.GONE);
            EventBus.getDefault().postSticky(openShiftResponse);
            if (openShiftResponse.getShifts() != null) {
                lv_shifts.addAll(openShiftResponse.getShifts());
                mAdapter = new BookingInviteAdapter(getActivity(), lv_shifts, this);
                recyclerView.setAdapter(mAdapter);
                swipeRefreshLayout.setRefreshing(false);
                swipeNodata.setRefreshing(false);
                totalShifts=openShiftResponse.getTotalShifts();
                if(openShiftResponse.getTotalShifts()==0){
                    ll_noData.setVisibility(View.VISIBLE);
                    swipeNodata.setVisibility(View.VISIBLE);
                    tv_noData.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setVisibility(View.GONE);
                    tvTotal.setVisibility(View.GONE);
                } else if(openShiftResponse.getTotalShifts()==1){
                    ll_noData.setVisibility(View.GONE);
                    swipeNodata.setVisibility(View.GONE);
                    tv_noData.setVisibility(View.GONE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    tvTotal.setText(openShiftResponse.getTotalShifts() + " Invitation");
                    tvTotal.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(mAdapter);
                    collapse.setExpanded(true);
                }else {
                    ll_noData.setVisibility(View.GONE);
                    swipeNodata.setVisibility(View.GONE);
                    tv_noData.setVisibility(View.GONE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(mAdapter);
                    tvTotal.setText(openShiftResponse.getTotalShifts() + " Invitations");
                    tvTotal.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(mAdapter);
                    collapse.setExpanded(true);
                }
                //recyclerView.scrollToPosition(noOfElements);
                noOfElements = lv_shifts.size();
                totalPages=openShiftResponse.getTotal_pages();
                if (openShiftResponse.getNextPage() != null) {
                    double page=(double) openShiftResponse.getNextPage();
                    pageNum = (int)Math.round(page);
                    /*if(pageNum==totalPages){
                        isLastPage=true;
                    }else{
                        isLastPage=false;
                    }*/
                }else{
                    pageNum++;
                    if(pageNum>totalPages){
                        isLastPage=true;
                    }else{
                        isLastPage=false;
                    }
                }
            } else {
                if (pageNum <= 1 && openShiftResponse.getNextPage() == null) {
                    recyclerView.setAdapter(null);
                    tvTotal.setText("No Invitations");
                    tvTotal.setVisibility(View.GONE);
                    ll_noData.setVisibility(View.VISIBLE);
                    swipeNodata.setVisibility(View.VISIBLE);
                    tv_noData.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setVisibility(View.GONE);
                }else{

                }
            }

            if (openShiftResponse.getAddressLatitude() != null && openShiftResponse.getAddressLongitude() != null) {
                if (openShiftResponse.getAddressLatitude() instanceof String && openShiftResponse.getAddressLongitude() instanceof String) {
                    SharedPrefManager.getInstance(getActivity()).setLatLng((String) openShiftResponse.getAddressLatitude(), (String) openShiftResponse.getAddressLongitude());
                }
            } else {
                try {
                    SharedPrefManager.getInstance(getActivity()).setLatLng("0.0", "0.0");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            if (openShiftResponse.getAddress_text() != null) {
                if (openShiftResponse.getAddress_text() instanceof String) {
                    SharedPrefManager.getInstance(getActivity()).setAddress((String) openShiftResponse.getAddress_text());
                }
            } else {
                try {
                    SharedPrefManager.getInstance(getActivity()).setAddress("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if((GlobalConstants.SelectedAddress!=null||!GlobalConstants.SelectedAddress.equals(""))&&GlobalConstants.SelectedLat!=0.0 && GlobalConstants.SelectedLong!=0.0){
                tvSearch.setText(GlobalConstants.SelectedAddress);
                if(GlobalConstants.isEntered){
                    iv_clear.setVisibility(View.VISIBLE);
                }else{
                    iv_clear.setVisibility(View.GONE);
                }
            }else {
                if (SharedPrefManager.getInstance(getActivity()).getAddress() != null && !SharedPrefManager.getInstance(getActivity()).getAddress().equals("")) {
                    tvSearch.setText(SharedPrefManager.getInstance(getActivity()).getAddress());
                }
            }


            if(openShiftResponse.isFilter_applied()){
                LandingActivity.menu_right.setImageResource(R.drawable.ic_menu_dot);
            }else{
                LandingActivity.menu_right.setImageResource(R.drawable.ic_menu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onInviteError() {
        showWarning(getActivity(), "", getResources().getString(R.string.errorTimeOut), "error");
        swipeRefreshLayout.setRefreshing(false);
        swipeNodata.setRefreshing(false);
    }

    @Override
    public void onClick(int position) {
        clickedPosition=position;
        Bundle bundle = new Bundle();
        bundle.putString("isFrom", "ShiftDetailInviteFragment");
        bundle.putInt("id", lv_shifts.get(position).getId());
        if(lv_shifts.get(position).getDistance()!=null){
            if(lv_shifts.get(position).getDistance() instanceof String){
                String dist=(String)lv_shifts.get(position).getDistance();
                bundle.putString("distance", dist);
            }else {
                bundle.putString("distance", "");
            }
        }
        isDetailClicked=true;

        try {
            ArrayList<OpenShiftResponse.Shift> saved_shifts = new ArrayList<>();
            saved_shifts.addAll(lv_shifts);
            Openshiftdata openshiftdata = new Openshiftdata();
            openshiftdata.setAl_shifts(saved_shifts);
            EventBus.getDefault().postSticky(openshiftdata);
        }catch (Exception e){
            e.printStackTrace();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            GlobalConstants.isACtivityResulted=true;
            if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE) {
                try {

                    latitide = Double.parseDouble(data.getStringExtra("lat"));
                    longitude = Double.parseDouble(data.getStringExtra("lng"));

                    if(latitide!=0.0||latitide!=0){
                        openShift.setLatitude(latitide);
                        savedLat=latitide;
                    }else{
                        openShift.setLatitude(savedLat);
                    }

                    if(longitude!=0.0||longitude!=0){
                        openShift.setLongitude(longitude);
                        savedLong=longitude;
                    }else{
                        openShift.setLongitude(savedLong);
                    }

                    GlobalConstants.SelectedLat=latitide;
                    GlobalConstants.SelectedLong=longitude;
                    GlobalConstants.isEntered=true;
                    GlobalConstants.currentLocation=false;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    String requiredValue = data.getStringExtra("name");
                    GlobalConstants.SelectedAddress = requiredValue;
                    tvSearch.setText(requiredValue);
                }catch (Exception e){
                    e.printStackTrace();
                }

                iv_clear.setVisibility(View.VISIBLE);

                lv_shifts.clear();
                isLastPage = false;
                pageNum = 1;
                noOfElements = 0;
                openShift.setNextPage(pageNum);

                mPresenter.onGetInvitations(getOpenShiftRequest(openShift),pageNum);
            } else if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE_PLACES) {

                try {

                    latitide = Double.parseDouble(data.getStringExtra("lat"));
                    longitude = Double.parseDouble(data.getStringExtra("lng"));

                    if (latitide != 0.0 || latitide != 0) {
                        openShift.setLatitude(latitide);
                        savedLat=latitide;
                    } else {
                        openShift.setLatitude(savedLat);
                    }

                    if (longitude != 0.0 || longitude != 0) {
                        openShift.setLongitude(longitude);
                        savedLong=longitude;
                    } else {
                        openShift.setLongitude(savedLong);
                    }

                    GlobalConstants.SelectedLat = latitide;
                    GlobalConstants.SelectedLong = longitude;
                    GlobalConstants.currentLocation=true;
                }catch (Exception e){
                    e.printStackTrace();
                }

                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (gps_enabled && (SharedPrefManager.getInstance(getActivity()).getAddress() == null || SharedPrefManager.getInstance(getActivity()).getAddress().equals(""))) {
                    iv_clear.setVisibility(View.GONE);
                    GlobalConstants.isEntered=false;
                }else{
                    iv_clear.setVisibility(View.VISIBLE);
                    GlobalConstants.isEntered=true;
                }

                try {
                    String requiredValue = data.getStringExtra("name");
                    GlobalConstants.SelectedAddress=requiredValue;
                    tvSearch.setText(requiredValue);
                }catch (Exception e){
                    e.printStackTrace();
                }
                isLastPage = false;
                lv_shifts.clear();
                pageNum = 1;
                noOfElements = 0;
                openShift.setNextPage(pageNum);

                mPresenter.onGetInvitations(getOpenShiftRequest(openShift),pageNum);
            } else if (requestCode == REQUEST_CODE_FILTERS && resultCode == RESULT_CODE_FILTERS) {
                try {
                    openShift.setSortBy(SortActivity.openShift.getSortBy());
                    openShift.setPaceIds(SortActivity.openShift.getPaceIds());
                    openShift.setCountyIds(SortActivity.openShift.getCountyIds());
                    openShift.setStartDate(SortActivity.openShift.getStartDate());
                    openShift.setEndDate(SortActivity.openShift.getEndDate());
                    openShift.setDistanceId(SortActivity.openShift.getDistanceId());
                    openShift.setEmergency(SortActivity.openShift.getEmergency());
                    openShift.setLongitude(savedLong);
                    openShift.setLatitude(savedLat);
                    openShift.setRate(SortActivity.openShift.getRate());



                    Log.e("Current open shift:- ", openShift + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SharedPrefManager.getInstance(getActivity()).setShiftData(openShift);

                isLastPage = false;
                lv_shifts.clear();
                pageNum = 1;
                noOfElements = 0;
                openShift.setNextPage(pageNum);
                mPresenter.onGetInvitations(getOpenShiftRequest(openShift),pageNum);

                try {
                    SortActivity.openShift = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception ex) {

            /*Toast.makeText(getActivity().getApplicationContext(), ex.toString(),
                    Toast.LENGTH_SHORT).show();*/

        }


    }

    @OnClick(R.id.tv_search)
    public void click(){
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.slide_in_up, R.anim.stay).toBundle();
        startActivityForResult(intent , REQUEST_CODE, bundle);
    }


    private JsonObject getOpenShiftRequest(OpenShift openShift) {
        OpenShiftReq openShiftReq = new OpenShiftReq();
        return openShiftReq.add(openShift);
    }


    @Override
    public void onInvitesDetailFetch(OpenShiftResponse openShiftResponse) {

    }

    @Override
    public void onInviteDetailError() {

    }

    public void checkPermissions() {
        PermissionUtil.checkPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION,
                new PermissionUtil.PermissionAskListener() {
                    @Override
                    public void onPermissionAsk() {
                        ActivityCompat.requestPermissions(
                                getActivity(),
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_LOCATION
                        );
                    }

                    @Override
                    public void onPermissionPreviouslyDenied() {
                    }

                    @Override
                    public void onPermissionDisabled() {
                    }

                    @Override
                    public void onPermissionGranted() {

                        gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                        if (gps_enabled) {
                            getLocation();
                        }else{
                            SharedPrefManager.getInstance(getActivity()).setCurrentLatLng("0.0", "0.0");

                            try {
                                if (SharedPrefManager.getInstance(getActivity()).getAddress() != null && !SharedPrefManager.getInstance(getActivity()).getAddress().equals("")) {
                                    GlobalConstants.currentLocation=false;
                                    GlobalConstants.SelectedAddress="";
                                    GlobalConstants.SelectedLong=0.0;
                                    GlobalConstants.SelectedLat=0.0;
                                    GlobalConstants.isEntered=false;
                                    iv_clear.setVisibility(View.GONE);
                                    tvSearch.setText(SharedPrefManager.getInstance(getActivity()).getAddress());
                                } else {

                                    if(GlobalConstants.currentLocation){
                                        GlobalConstants.currentLocation=false;
                                        GlobalConstants.SelectedAddress="";
                                        GlobalConstants.SelectedLong=0.0;
                                        GlobalConstants.SelectedLat=0.0;
                                        GlobalConstants.isEntered=false;
                                        OpenShiftFragment.tvSearch.setText("");

                                        try {
                                            ShiftDetailInviteFragment.tvSearch.setText("");
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }


                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    @Override
    public void whereIAM(ADLocation adLocation) {

        if (null != adLocation) {
            SharedPrefManager.getInstance(getActivity()).setCurrentLatLng(String.valueOf(adLocation.lat), String.valueOf(adLocation.longi));

            try {
                if (isRefreshing) {

                    GlobalConstants.currentLocation = true;
                    OpenShiftFragment.tvSearch.setText(mUtils.getAddress(getActivity(), adLocation.lat,
                            adLocation.longi));

                    try {
                        ShiftDetailInviteFragment.tvSearch.setText(mUtils.getAddress(getActivity(), adLocation.lat,
                                adLocation.longi));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (SharedPrefManager.getInstance(getActivity()).getAddress() != null && !SharedPrefManager.getInstance(getActivity()).getAddress().equals("")) {
                        //GlobalConstants.currentLocation=false;
                        tvSearch.setText(SharedPrefManager.getInstance(getActivity()).getAddress());
                    } else {
                        GlobalConstants.currentLocation = true;
                        OpenShiftFragment.tvSearch.setText(mUtils.getAddress(getActivity(), adLocation.lat,
                                adLocation.longi));

                        try {
                            ShiftDetailInviteFragment.tvSearch.setText(mUtils.getAddress(getActivity(), adLocation.lat,
                                    adLocation.longi));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getLocation(){
        new MyTracker(getActivity(), this).track();
    }



}
